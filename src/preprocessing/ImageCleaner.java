package preprocessing;

import java.util.LinkedList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageCleaner {
	
	public static Mat CleanImage(String path) {
		Mat img = Imgcodecs.imread(path, 0);
		if (img == null) {
			System.err.println("Error: File not found.");
			return null;
		}
		Size size = new Size(3, 3);
		Imgproc.GaussianBlur(img, img, size, 0);
		Imgproc.adaptiveThreshold(img, img, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 75, 10);
		Core.bitwise_not(img, img);
		
		return img;
	}
	
	public static Mat deskew(Mat img) {
		
		/*
		Mat elem = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 3));
		Imgproc.erode(img, img, elem);
		*/
		
		LinkedList<Point> points = new LinkedList<Point>();

		for (int y = 0; y < img.cols(); y++) {
			for (int x = 0; x < img.rows(); x++) {
				if (img.get(y, x) != null && img.get(y, x)[0] == 255) {
					points.addLast(new Point(x, y));
				}
			}
		}

		MatOfPoint2f obj = new MatOfPoint2f();
		obj.fromList(points);
		RotatedRect box = Imgproc.minAreaRect(obj);
		
		double angle = box.angle;
		if (angle < -45.)
			angle += 90.;

		System.out.println(angle);
		/*
		Mat rotMat = Imgproc.getRotationMatrix2D(box.center, angle, 1);
		Mat rotated = new Mat();
		Imgproc.warpAffine(img, rotated, rotMat, img.size(), Imgproc.INTER_CUBIC);
		*/
		Size size = box.size;
		
		if (box.angle < -45.) {
			double tmp = size.width;
			size.width = size.height;
			size.height = tmp;
		}
		
		Mat cropped = new Mat();

		Imgproc.getRectSubPix(img, size, box.center, cropped);
		return cropped;
	}
}
