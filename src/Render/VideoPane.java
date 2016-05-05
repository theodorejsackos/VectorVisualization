package Render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VideoPane extends JPanel {

	private BufferedImage img;

	public VideoPane() {
		// ?
	}

	@Override
	public void paintComponent(Graphics g) {
		if (img != null)
			g.drawImage(img, 0, 0, null);
		g.setColor(new Color(0, 0, 0));

		// Testing visuals
		// g.fillRect(0, 0, 500, 500);
	}

	public void updateImage(BufferedImage img) {
		this.img = img;
		this.repaint();
	}

}