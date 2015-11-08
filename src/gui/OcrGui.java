package gui;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sun.javafx.tk.Toolkit;

public class OcrGui extends JFrame implements ActionListener{

	JButton getImage;
	String filename;
	JLabel jlabel;
	Dimension size;
	Insets insets;
	
	public OcrGui() {
		// TODO Auto-generated constructor stub
		this.setTitle("MachineLearning- OCR");
		this.setSize(600, 800);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		getImage = new JButton("Rechercher Image");
		JLabel textAnalysisImage = new JLabel("Image en cours d'analyse:");
		JLabel textLabelResult = new JLabel("Texte ou charactère trouvé:");
		JLabel textResult = new JLabel("");
		
		getImage.addActionListener(this);
		this.getContentPane().add(getImage);
		this.getContentPane().add(textAnalysisImage);
		this.getContentPane().add(textLabelResult);
		
		insets = this.getInsets();
		
		size = getImage.getPreferredSize();
		getImage.setBounds(55 + insets.left, 40 + insets.top, size.width, size.height);
		
		size = textAnalysisImage.getPreferredSize();
		textAnalysisImage.setBounds(55 + insets.left, 90 + insets.top, size.width, size.height);
		
		size = textLabelResult.getPreferredSize();
		textLabelResult.setBounds(55 + insets.left, 690 + insets.top, size.width, size.height);
		
		this.setBackground(Color.ORANGE);
		this.setVisible(true);
	}
	
	public void setFile(String name) {
		filename = name;
	}
	
	public String getFile() {
		return filename;
	}
	
	private BufferedImage createResizedCopy(Image originalImage, 
	    int scaledWidth, int scaledHeight, boolean preserveAlpha) {
	    
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
	    BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
	    Graphics2D g = scaledBI.createGraphics();
	    if (preserveAlpha) {
	    	g.setComposite(AlphaComposite.Src);
	    }
	    g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
	    g.dispose();
	    return scaledBI;
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == getImage) {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new java.io.File("."));
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			   File file = fc.getSelectedFile();
			   System.out.print(file);
			   this.setFile(file.toString());
			   try {
				Image image = ImageIO.read(file);
				ImageIcon icon = new ImageIcon(image);
				System.out.print("Image Width" + icon.getIconWidth());
				if (icon.getIconWidth() != 50) {
					image = createResizedCopy(image, 400, 500, true);
				} else if (icon.getIconWidth() == 50){
					image = createResizedCopy(image, 100, 100, true);
				}
				jlabel = new JLabel(new ImageIcon(image));
				this.getContentPane().remove(jlabel);
	
			   this.invalidate();
			   this.validate();
			   this.repaint();
			   this.getContentPane().add(jlabel);
	   
			   size = jlabel.getPreferredSize();
			   if (icon.getIconWidth() != 50) {
				   jlabel.setBounds(70 + insets.left, 150 + insets.top, 400, 500);
			   } else {
				   jlabel.setBounds(70 + insets.left, 150 + insets.top, 100, 100);
			   }
			   icon = null;
			   this.revalidate();
			   this.repaint();
			   } catch (IOException e1) {
				   // TODO Auto-generated catch block
				   e1.printStackTrace();
			   }
	   
			}
		}
	}
}
