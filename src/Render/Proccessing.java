package render;

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
