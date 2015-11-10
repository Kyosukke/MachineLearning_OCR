package charrecognition;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import preprocessing.ImageCleaner;
import utils.ImageDisplayer;
import utils.MatManager;

public class TextRecognition {

	public static String readText(String path, List<utils.Character> dataset, int k) {
		String text = "";
		Mat txt = ImageCleaner.CleanImage(path);
		List<Mat> lines;
		List<Mat> letters;
		List<Rect> rects = new ArrayList<Rect>();
		int[] width = MatManager.getWidthFromMat(txt);

		//ImageDisplayer.displayImage(MatManager.Mat2BufferedImage(txt), "text");
		
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
		
		/* Add last square */
		/*
		if (last_y - first_y > 0) {
			rects.add(new Rect(0, first_y, txt.cols(), last_y - first_y));
		}*/
		
		lines = MatManager.cutMat(txt, rects);

		// decoupage en mot de chaque ligne
		
		rects.clear();

		for (Mat m : lines)
		{
			int[] height = MatManager.getLengthFromMat(m);
			
			int first_x = 0;
			int last_x = 0;
			isPreviousEmpty = true;
			
			for (int i = 0; i < m.cols();  i++)
			{
				if (height[i] <= 0 && !isPreviousEmpty) {
					isPreviousEmpty = true;
					rects.add(new Rect(first_x, 0, last_x - first_x, m.rows()));
					first_x = last_x;
				}
				
				else if (height[i] > 0)
					isPreviousEmpty = false;
				last_x++;
			}
			
			/* Add last square */
			
			/*
			if (last_x - first_x > 0) {
				rects.add(new Rect(first_x, 0, last_x - first_x, m.rows()));
			}*/
			
			
			/* Clean all Mat and use OCR */
			
			letters = MatManager.cutMat(txt, rects);
			for (Mat l : letters) {
				if (!l.empty()) {
					l = MatManager.cropMat(l);
					l = MatManager.resizeMat(l, 10);
					l = ImageCleaner.CleanImage(l, false);
				}
				text += CharacterRecognition.getCharacter(l, dataset, k);
			}
		}
		
		return text;
	}
}
