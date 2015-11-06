package charrecognition;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import preprocessing.ImageCleaner;
import utils.ImageDisplayer;
import utils.MatManager;

public class TextRecognition {

	public static String readText(String path) {
		Mat txt = ImageCleaner.CleanImage(path);
		List<Mat> lines;
		List<Rect> rects = new ArrayList<Rect>();
		int[] width = MatManager.getWidthFromMat(txt);

		ImageDisplayer.displayImage(MatManager.Mat2BufferedImage(txt), "text");
		
		int first_y = 0;
		int last_y = 0;
		boolean isPreviousEmpty = true;
		for (int i = 0; i < txt.rows(); i++) {
			if (width[i] == 0 && !isPreviousEmpty) {
					isPreviousEmpty = true;
					rects.add(new Rect(0, first_y, txt.cols(), last_y - first_y));
					first_y = last_y;
			}
			else if (width[i] > 0)
				isPreviousEmpty = false;
			last_y++;
		}
		if (last_y - first_y > 0) {
			rects.add(new Rect(0, first_y, txt.cols(), last_y - first_y));
		}
		
		lines = MatManager.cutMat(txt, rects);
		
		for (Mat m : lines) {
			ImageDisplayer.displayImage(MatManager.Mat2BufferedImage(m), "t");
		}
		
		return null;
	}
}
