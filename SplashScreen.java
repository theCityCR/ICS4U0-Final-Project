package culminating;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Raymond Ouyang
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the splash screen.
 */

@SuppressWarnings("serial")
public class SplashScreen extends GamePanel {

	private static BufferedImage logo;

	private static ImageIcon bg;

	private int count;

	public SplashScreen() {
		bg = new ImageIcon("src//culminating//MenuBG.jpg"); // TODO change
		try {
			logo = ImageIO.read(new File("src//culminating//CulminatingLogo.jpg")); // TODO change
		} catch (IOException e) {
			e.printStackTrace();
		}
		count = 0;
	}

	@Override
	public State display() {
		repaint();
		count++;
		return count > 1000 ? State.MENU : State.SPLASH;
	}

	public void paintComponent(Graphics g) {
		bg.paintIcon(this, g, 0, 0);

		// O&L presents
		g.setColor(Color.RED);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		g.drawString("O&L Design Firms presents...", 900 - count * 3, 110);

		// Title
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
		g.setColor(Color.red);
		g.drawString("FAIR PLAY FRONTIER", Math.max(215, 1900 - count * 3), 110);
		
		//Logo
		g.drawImage(SplashScreen.getLogo(), 41, Math.min(50, -1900 + count * 3), 170, Math.min(150, -1800 + count * 3), 0, 0, 861, 668, null);

		// Menu Button
		g.setColor(Color.BLUE);
		g.fillRoundRect(190, Math.max(170, 2500 - 3 * count), 400, 50, 20, 20);
		g.fillRoundRect(190, Math.max(230, 2800 - 3 * count), 400, 50, 20, 20);
		g.fillRoundRect(190, Math.max(290, 3100 - 3 * count), 400, 50, 20, 20);
		g.fillRoundRect(190, Math.max(350, 3300 - 3 * count), 400, 50, 20, 20);

		// Menu text
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		g.drawString("LEARN", 360, Math.max(200, 2530 - 3 * count));
		g.drawString("MAZE", 360, Math.max(260, 2830 - 3 * count));
		g.drawString("ACTION", 360, Math.max(320, 3130 - 3 * count));
		g.drawString("EXIT", 360, Math.max(380, 3330 - 3 * count));
	}

	public static void setLogo(BufferedImage logo) {
		SplashScreen.logo = logo;
	}

	public static ImageIcon getBg() {
		return bg;
	}

	public static BufferedImage getLogo() {
		return logo;
	}

}
