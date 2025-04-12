// BackgroundPanel.java
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
  private final Image background;

  public BackgroundPanel(String imagePath) {
    background = new ImageIcon(imagePath).getImage();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
  }
}
