package utils;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;

//import sun.misc.BASE64Encoder;

/*
 * Display image for debugger
 */
public class ImageDisplayer {
/*	public static String encoreToString(BufferedImage image, String type){
    	String imageString = null;
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	
    	try {
    		ImageIO.write(image, type, bos);
    		byte[] imageBytes = bos.toByteArray();
    		
    		BASE64Encoder encoder = new BASE64Encoder();
    		imageString = encoder.encode(imageBytes);
    		bos.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return imageString;
	}*/

	public static BufferedImage Mat2BufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		
		int buffer_size = m.channels() * m.cols() * m.rows();
		
		byte[] b = new byte[buffer_size];
		
		m.get(0, 0, b);
		
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		
		final byte[] target_pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, target_pixels, 0, b.length);
		
		return image;
	}
	
	public static BufferedImage resizeImage(String imagePath, String ext, int x, int y) {

		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(new File(imagePath));	
			
			BufferedImage resizedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = (Graphics2D) resizedImage.createGraphics();
			
			graphic.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			graphic.drawImage(originalImage, 0, 0, x, y, null);

			ImageIO.write(resizedImage, ext, new File(imagePath));

			return resizedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
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
