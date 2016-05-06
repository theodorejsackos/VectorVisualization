package render;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import structs.ScreenRegion;

@SuppressWarnings("serial")
public class VideoPane extends JPanel {

	private BufferedImage img;
	private ScreenRegion border;
	private int borderTries = 30;
	
	public VideoPane() {
		// ?
	}

	@Override
	public void paintComponent(Graphics g) {
		if (img != null)
			g.drawImage(img, 0, 0, null);
		
		
		g.setColor(new Color(0, 255, 200));
		if(border != null)
			g.drawRect(border.getX(), border.getY(), border.getWidth(), border.getHeight());

	}

	public void updateImage(BufferedImage img, structs.ScreenRegion border) {
		this.img = img;
		
		//If no new border just update screen image and return.
		if(border == null){
			this.repaint();
			return;
		}
		
		//If the new border ISN'T null and we don't have one yet, update it and return.
		if(this.border == null){
			this.border = border;
			this.repaint();
			return;
		}
		
		//If the new border is an order of magnitude smaller, ignore it. It is likely a misread.
		//If the new border is about the same size, then update it.
		if(validNewBorder(border)){
			this.border = border;
			borderTries = 0;
			this.repaint();
			return;
		}
		
		borderTries++;
		//If we haven't updated the border for 25 frames, we may be on the wrong area, update anyway.
		if(borderTries > 25)
			this.border = border;
		this.repaint();
	}
	
	private boolean validNewBorder(ScreenRegion newRegion){
		if(newRegion.getArea() < (this.border.getArea() / 10))
			return false;
		else 
			return true;
	}

}