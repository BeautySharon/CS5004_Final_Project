import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
  private Clip clip;

  public void playLoop(String filePath) {
    try {
      File audioFile = new File(filePath);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

      clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY);  // 无限循环播放
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      System.err.println("❌ 音乐播放失败: " + e.getMessage());
    }
  }

  public void stop() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
    }
  }
}
