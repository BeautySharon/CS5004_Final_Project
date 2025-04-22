import random
import json
import numpy as np

# Define constants
ACTIONS = ['hit', 'stand']
DECK = [1,2,3,4,5,6,7,8,9,10,10,10,10]  # 1 is Ace, 10 includes face cards

# Parameters
EPISODES = 100000000  # number of training episodes
ALPHA = 0.1       # learning rate
GAMMA = 0.9       # discount factor
EPSILON = 0.1     # exploration rate

# Initialize Q-table
# Key: (player_total, dealer_card, usable_ace), Value: {'hit': q1, 'stand': q2}
q_table = {}

def draw_card():
    return random.choice(DECK)

def draw_hand():
    return [draw_card(), draw_card()]

def is_usable_ace(hand):
    return 1 in hand and sum(hand) + 10 <= 21

def hand_value(hand):
    val = sum(hand)
    if is_usable_ace(hand):
        return val + 10, True
    return val, False

def get_state(hand, dealer_card):
    value, usable_ace = hand_value(hand)
    return (value, dealer_card, usable_ace)

def should_bust(hand):
    val, _ = hand_value(hand)
    return val > 21

def dealer_play(dealer_hand):
    while True:
        value, _ = hand_value(dealer_hand)
        if value < 17:
            dealer_hand.append(draw_card())
        else:
            break
    return dealer_hand

def get_reward(player_hand, dealer_hand):
    player_value, _ = hand_value(player_hand)
    dealer_value, _ = hand_value(dealer_hand)
    if player_value > 21:
        return -1
    elif dealer_value > 21 or player_value > dealer_value:
        return 1
    elif player_value == dealer_value:
        return 0
    else:
        return -1

def choose_action(state):
    if state not in q_table:
        q_table[state] = {action: 0.0 for action in ACTIONS}
    if random.random() < EPSILON:
        return random.choice(ACTIONS)
    return max(q_table[state], key=q_table[state].get)

def update_q(state, action, reward, next_state, done):
    if state not in q_table:
        q_table[state] = {a: 0.0 for a in ACTIONS}
    if next_state not in q_table:
        q_table[next_state] = {a: 0.0 for a in ACTIONS}

    current_q = q_table[state][action]
    max_future_q = max(q_table[next_state].values()) if not done else 0
    new_q = current_q + ALPHA * (reward + GAMMA * max_future_q - current_q)
    q_table[state][action] = new_q

def train():
    for episode in range(EPISODES):
        player_hand = draw_hand()
        dealer_hand = draw_hand()
        dealer_card = dealer_hand[0]

        state = get_state(player_hand, dealer_card)

        done = False
        while not done:
            action = choose_action(state)
            if action == 'hit':
                player_hand.append(draw_card())
                next_state = get_state(player_hand, dealer_card)
                if should_bust(player_hand):
                    reward = -1
                    update_q(state, action, reward, next_state, done=True)
                    done = True
                else:
                    update_q(state, action, 0, next_state, done=False)
                    state = next_state
            else:  # 'stand'
                dealer_hand = dealer_play(dealer_hand)
                reward = get_reward(player_hand, dealer_hand)
                next_state = get_state(player_hand, dealer_card)
                update_q(state, action, reward, next_state, done=True)
                done = True

        if (episode + 1) % 100000 == 0:
            print(f"Episode {episode + 1} complete")

def save_q_table():
    serializable_q = {
        str(key): value for key, value in q_table.items()
    }
    with open("q_table.json", "w") as f:
        json.dump(serializable_q, f, indent=2)
    print("Q-table saved to q_table.json")

if __name__ == "__main__":
    print("Training Q-learning agent on Blackjack...")
    train()
    save_q_table()
