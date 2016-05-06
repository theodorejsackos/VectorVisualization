package render;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;

import structs.ScreenRegion;

/**
 * Class of image processing functions, in particular, image recognition.
 * 
 * @author Theodore
 *
 */
public class Processing {

	public static structs.ScreenRegion locateFiducialScreen(Mat fieldOfView, int threshHold){
		//Convern input image to grayscale
		//Imgproc.cvtColor(fieldOfView, fieldOfView, Imgproc.COLOR_RGB2GRAY);
		
		//Construct BufferedImage from matrix
		BufferedImage toScan = matToBufferedImage(fieldOfView, null);
		//Construct screen region object to bound the FiducialScreen
		ScreenRegion region = new ScreenRegion();
		region.setX(0);
		region.setWidth(toScan.getWidth());
		
		//Control variables for scanning the image
		boolean foundTop = false;
		boolean onDarkBar = false;
		boolean grabSides = false;
		int bars = 0;
		int expectedNumBars = 18;
		int barThickness = 0;
		int averageWidth = 0;
		
		
		for(int i = 0; i < toScan.getHeight(); i += 5){
			bars = 0;
			for(int j = 0; j < toScan.getWidth(); j += 2){
				Color c = new Color(toScan.getRGB(j, i));
				double lum = lum(c.getRed(), c.getGreen(), c.getBlue());
				
				//If we found the top of the Fiducucial Screen we want to find the left side.
				if(grabSides && bars > 5){
					grabSides = false;
					region.setX(j - 80);
				}
				
				//Calculate the width of the region to surround target Screen
				if(bars > expectedNumBars){
					if(averageWidth == 0)
						averageWidth = j - region.getX();
					else
						averageWidth = (averageWidth + (j - region.getX())) / 2;
					//System.out.printf("y = %d, j = %d Average width: %d\n", i, j, averageWidth);
				}
				
				//Count the pixel thickness of a bar (NOT SURE IF THIS IS BENEFICIAL YET.)
				if(lum > threshHold && !onDarkBar)
					barThickness++;
				if(lum < threshHold && onDarkBar)
					barThickness++;
				
				//If we were previously on a dark bar, and now find a light spot, we found a new bar.
				if(lum > threshHold && onDarkBar && barThickness > 3){
					bars++;
					onDarkBar = !onDarkBar;
				}
				
				//If we were previously on a light bar, and now find a dark spot, we found a new bar.
				if(lum < threshHold && !onDarkBar && barThickness > 3){
					bars++;
					onDarkBar = !onDarkBar;
				}
				//System.out.printf("row %d at pixel %d is on a dark bar: %b (lum: %f, bar# %d) \n", i, j, onDarkBar, lum, bars);
			}
			if(bars > expectedNumBars && !foundTop){
				foundTop = true;
				grabSides = true;
				region.setY(i - 15);
			}else if(bars > expectedNumBars && foundTop)
				continue;
			else if(bars < expectedNumBars && foundTop){
				region.setHeight((i - region.getY()) + 15);
				break;
			}
		}
		
		if(averageWidth > 50)
			region.setWidth((int) (averageWidth * .6));
		region.calculateArea();
		
		return region;
	}	
	
	private static double lum(int r, int g, int b){
		return ( 0.299 * r + 0.587 * g + 0.114 * b );
	}
	
	/**
	 * http:// stackoverflow.com/questions/30262834/convert-image-in-mat-to-
	 * bufferedimage
	 */
	public static BufferedImage matToBufferedImage(Mat matrix,
			BufferedImage bimg) {
		if (matrix == null)
			return null;

		int cols = matrix.cols();
		int rows = matrix.rows();
		int elemSize = (int) matrix.elemSize();
		byte[] raw = new byte[cols * rows * elemSize];
		int imageType;
		matrix.get(0, 0, raw);

		int val = matrix.channels();
		if (val == 1)
			imageType = BufferedImage.TYPE_BYTE_GRAY;
		else if (val == 3) {
			imageType = BufferedImage.TYPE_3BYTE_BGR;
			// bgr to rgb
			byte b;
			for (int i = 0; i < raw.length; i = i + 3) {
				b = raw[i];
				raw[i] = raw[i + 2];
				raw[i + 2] = b;
			}
		} else
			return null;

		// Reuse existing BufferedImage if possible
		if (bimg == null || bimg.getWidth() != cols || bimg.getHeight() != rows
				|| bimg.getType() != imageType) {
			bimg = new BufferedImage(cols, rows, imageType);
		}
		bimg.getRaster().setDataElements(0, 0, cols, rows, raw);

		return bimg;
	}
}
