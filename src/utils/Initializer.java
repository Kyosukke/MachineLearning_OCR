package utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Initializer {
	
	static String path = "assets/step1/";
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
	    System.out.println( "mat = " + mat.dump() );
	    
	    Mat out = Imgcodecs.imread(path + "a.bmp", Imgcodecs.CV_LOAD_IMAGE_COLOR);
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
