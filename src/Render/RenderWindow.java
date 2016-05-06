package render;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * This class represents the window that will draw the video feed.
 * 
 * @author Theodore
 */
@SuppressWarnings("serial")
public class RenderWindow extends JFrame {

	private final int EXTRA_BORDER_WIDTH = 6, EXTRA_BOARDER_HEIGHT = 28;

	/** Inner video JPanel */
	private VideoPane feed;

	/**
	 * Default constructor, window sizes and the title to appear at the top of
	 * the window.
	 * 
	 * @param windowLength
	 * @param windowHeight
	 * @param title
	 */
	public RenderWindow(int windowLength, int windowHeight, String title) {
		// Set up frame
		renderWindowToFit(windowLength + EXTRA_BORDER_WIDTH, windowHeight
				+ EXTRA_BOARDER_HEIGHT, title);

		// Add a video feed to center of the window
		feed = new VideoPane();
		this.add(feed, BorderLayout.CENTER);

		this.setVisible(true);
	}

	/**
	 * Sets window size, operation, and location on screen.
	 * 
	 * @param x
	 * @param y
	 * @param title
	 * 
	 * @author Theodore Sackos
	 */
	public void renderWindowToFit(int x, int y, String title) {
		System.out.printf("Setting window size: (%d, %d)", x, y);

		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(x, y);
		this.repaint();
		this.revalidate();
		this.setResizable(false);

		// Center window on screen
		this.setLocationRelativeTo(null);
	}

	/**
	 * This image will be rendered in the JPanel contained in this window.
	 * 
	 * @param newImg
	 */
	public void update(BufferedImage newImg, structs.ScreenRegion frame) {
		feed.updateImage(newImg, frame);
	}
}
