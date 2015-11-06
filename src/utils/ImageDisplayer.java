package utils;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import sun.misc.BASE64Encoder;

/*
 * Display image for debugger
 */
public class ImageDisplayer {
	
	public static BufferedImage resizeImage(String imagePath, String ext, int x, int y, boolean save) {
		BufferedImage originalImage;
		BufferedImage resizedImage;

		try {
			originalImage = ImageIO.read(new File(imagePath));

			resizedImage = resizeImage(originalImage, x, y);
			
			if (save == true)
				ImageIO.write(resizedImage, ext, new File(imagePath));
			
			return resizedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static BufferedImage resizeImage(BufferedImage img, int x, int y) {
		
		BufferedImage resizedImage = new BufferedImage(x, y, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphic = (Graphics2D) resizedImage.createGraphics();
		
		graphic.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		graphic.drawImage(img, 0, 0, x, y, null);

		return resizedImage;
	}
	
	public static void saveImage(String imageName, String imagePath, String ext, byte[] imageBytes) {
		BufferedImage image;
		try {
			InputStream in = new ByteArrayInputStream(imageBytes);
			image = ImageIO.read(in);
			ImageIO.write(image, ext, new File(imagePath + imageName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void displayImage(Image img, String title) {
		
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize((img.getWidth(null) + 50) / 1, (img.getHeight(null) + 50) / 1);

		JLabel lbl = new JLabel(new ImageIcon((Image)img));
//		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
