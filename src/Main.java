import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 * Visualizes forces of cars seen passing as if in a 2d plane. (no z shift)
 * 
 * @author Theodore
 */
public class Main {

	/**
	 * Entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Load native library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// initialize video capturing
		VideoCapture videoCapture = new VideoCapture();
		videoCapture.open(0);
		if (!videoCapture.isOpened()) {
			System.out.println("Camera failed to open.");
			System.exit(1);
		}

		// Create window to dispay camera feed
		Mat size = new Mat();
		videoCapture.retrieve(size);
		Render.RenderWindow test = new Render.RenderWindow(size.width(),
				size.height(), "Video Feed");
		// Feed the video stream
		while (true) {
			Mat frame = new Mat();
			videoCapture.retrieve(frame);
			test.update(Render.Proccessing.matToBufferedImage(frame, null));
		}
	}
}