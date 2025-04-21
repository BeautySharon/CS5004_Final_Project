package model;

import model.AudioPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit5 tests for the AudioPlayer class.
 * Verifies graceful handling of audio playback logic.
 */
public class AudioPlayerTest {

  private AudioPlayer player;

  /**
   * Initializes the audio player before each test.
   */
  @BeforeEach
  void setUp() {
    player = new AudioPlayer();
  }

  /**
   * Tests if player executes without crashing,
   * and handles invalid file paths gracefully.
   */
  @Test
  void testPlayLoopHandlesMissingOrInvalidFile() {
    // Use a dummy path that doesn't exist
    assertDoesNotThrow(() -> player.playLoop("assets/nonexistent.wav"),
        "playLoop should handle invalid file without crashing");

    // Try again with different bad path to confirm behavior is consistent
    assertDoesNotThrow(() -> player.playLoop("fakepath/music123.wav"),
        "playLoop should not throw even with different invalid file");
  }

  /**
   * Tests if stop method executes safely before and after playing.
   */
  @Test
  void testStopMethodSafeToCall() {
    assertDoesNotThrow(() -> player.stop(),
        "Calling stop() when nothing is playing should not throw");

    player.playLoop("assets/doesnotexist.wav"); // safe dummy
    assertDoesNotThrow(() -> player.stop(),
        "Calling stop() after playLoop() with invalid file should still be safe");
  }
}
