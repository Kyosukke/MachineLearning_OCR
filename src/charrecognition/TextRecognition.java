package charrecognition;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import preprocessing.ImageCleaner;
import utils.DatasetManager;
import utils.ImageDisplayer;

public class TextRecognition {

	public static String readText(String path) {
		Mat txt = ImageCleaner.CleanImage(path);
		List<Mat> lines;
		List<Rect> rects = new ArrayList<Rect>();
		int[] width = DatasetManager.getWidthFromMat(txt, txt.rows(), 1);

		ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(txt), "text");
		
		int first_y = 0;
		int last_y = 0;
		boolean isPreviousEmpty = true;
		for (int i = 0; i < txt.rows(); i++) {
			if (width[i] == 0 && !isPreviousEmpty) {
					isPreviousEmpty = true;
					System.out.println("x:0 y:" + first_y + " h:" + txt.cols() + " w:" + (last_y-last_y) );
					rects.add(new Rect(0, first_y, txt.cols(), last_y - first_y));
					first_y = last_y;
			}
			else if (width[i] > 0)
				isPreviousEmpty = false;
			last_y++;
			System.out.println("i:" + i + " px:" + width[i]);
		}
		if (last_y - first_y > 0) {
			System.out.println("x:0 y:" + first_y + " h:" + txt.cols() + " w:" + (last_y-last_y));
			rects.add(new Rect(0, first_y, txt.cols(), last_y - first_y));
		}
		
		lines = ImageDisplayer.cutMat(txt, rects);
		
		for (Mat m : lines) {
			ImageDisplayer.displayImage(ImageDisplayer.Mat2BufferedImage(m), "t");
		}
		
		return null;
	}
}
