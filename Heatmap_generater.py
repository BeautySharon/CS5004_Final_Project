import json
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

# Load updated Q-table
with open("/Users/maggieshi/Desktop/5004/Final Project/CS5004_Final_Project/q_table.json", "r") as f:
    raw_q = json.load(f)

# Convert keys back to tuples
q_table = {eval(k): v for k, v in raw_q.items()}

# Initialize heatmap structure: rows = player_sum 3-21, columns = dealer card 2-11 (Ace)
strategy_matrix = pd.DataFrame(index=range(3, 22), columns=range(2, 12))

# Fill in strategy matrix with best action: 0 for hit, 1 for stand
for (player_sum, dealer_card, usable_ace), actions in q_table.items():
    if usable_ace:  # Exclude soft hands for now
        continue
    if 3 <= player_sum <= 21:
        dealer_col = 11 if dealer_card == 1 else dealer_card
        best_action = max(actions, key=actions.get)
        strategy_matrix.loc[player_sum, dealer_col] = 1 if best_action == 'stand' else 0

# Fill missing values as -1 (no data)
strategy_matrix.fillna(-1, inplace=True)

# Create heatmap
plt.figure(figsize=(12, 10))
sns.heatmap(strategy_matrix.astype(float), annot=strategy_matrix, fmt='', cmap='coolwarm', cbar=True,
            linewidths=0.5, linecolor='gray')
plt.title("Heatmap: Basic Strategy (Hard Hands only, 3-21 vs Dealer 2-11)\n0 = Hit, 1 = Stand, -1 = No Data")
plt.xlabel("Dealer's Up Card")
plt.ylabel("Player Total")
plt.yticks(rotation=0)
plt.show()
