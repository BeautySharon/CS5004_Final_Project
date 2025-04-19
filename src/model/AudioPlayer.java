package model;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * AudioPlayer is a utility class for playing background music in a loop.
 * It uses Java's javax.sound.sampled package to handle WAV audio files.
 * This class is commonly used in game UIs to provide ambient background audio.
 */
public class AudioPlayer {

  /** The Clip object that holds and plays the audio data */
  private Clip clip;

  /**
   * Loads an audio file and plays it in an infinite loop.
   * The audio file must be in a supported format (e.g., WAV).
   *
   * @param filePath the path to the audio file
   */
  public void playLoop(String filePath) {
    try {
      File audioFile = new File(filePath);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop playback
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("音乐播放失败: " + e.getMessage());
    }
  }

  /**
   * Stops the currently playing audio if it is running.
   * This is typically called when closing a window or switching scenes.
   */
  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }
}
