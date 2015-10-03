package utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import preprocessing.ImageCleaner;

public class Initializer {
	
	static String path = "assets/step2/";
	
	public static void main(String[] args) {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
	    System.out.println( "mat = " + mat.dump() );
	    
	    
	    Mat out = ImageCleaner.CleanImage(path + "paf.bmp");
	    ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(out), "test");
	}
}
