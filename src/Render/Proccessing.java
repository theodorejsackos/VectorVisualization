package Render;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

/**
 * Class of image processing functions, in particular, image recognition.
 * 
 * @author Theodore
 *
 */
public class Proccessing {

	/**
	 * This code was written by medloh
	 * (http://stackoverflow.com/users/2917835/medloh) and was taken from
	 * http:// stackoverflow.com/questions/30262834/convert-image-in-mat-to-
	 * bufferedimage
	 */
	public static BufferedImage matToBufferedImage(Mat matrix,
			BufferedImage bimg) {
		if (matrix != null) {
			int cols = matrix.cols();
			int rows = matrix.rows();
			int elemSize = (int) matrix.elemSize();
			byte[] data = new byte[cols * rows * elemSize];
			int type;
			matrix.get(0, 0, data);
			switch (matrix.channels()) {
			case 1:
				type = BufferedImage.TYPE_BYTE_GRAY;
				break;
			case 3:
				type = BufferedImage.TYPE_3BYTE_BGR;
				// bgr to rgb
				byte b;
				for (int i = 0; i < data.length; i = i + 3) {
					b = data[i];
					data[i] = data[i + 2];
					data[i + 2] = b;
				}
				break;
			default:
				return null;
			}

			// Reuse existing BufferedImage if possible
			if (bimg == null || bimg.getWidth() != cols
					|| bimg.getHeight() != rows || bimg.getType() != type) {
				bimg = new BufferedImage(cols, rows, type);
			}
			bimg.getRaster().setDataElements(0, 0, cols, rows, data);
		} else { // mat was null
			bimg = null;
		}
		return bimg;
	}
}
