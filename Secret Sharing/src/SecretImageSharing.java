/**
 * @author Shah, Samarth
 * Dec 05, 2014
 * SecretImageSharing.java	
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SecretImageSharing extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	final String aboutMe = "Samarth Shah \n \n ICSI 660: Security and Privacy \n sshah4@albany.edu \n \nUniversity at Albany, NY";

	// Globals..
	Random rand = new Random();
	BufferedImage reconstruct, image, tempBI, tempo;

	JButton loadImage = new JButton("Load Image");
	JButton create = new JButton("Create 5 Shares");
	JButton scale = new JButton("Scale Shares");
	JButton reconstructB = new JButton("Reconstruct Image");
	JButton aboutDev = new JButton("About Dev");
	JButton quit = new JButton("Quit");
	JCheckBox share1check, share2check, share3check, share4check, share5check;
	// Use the following JLabels and ImageIcons in the last to update picture in
	// GUI.
	ImageIcon i1, i2, i3, i4, i5, i6, i7, i8, i9, i10;
	JLabel img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;

	BufferedImage reconstrct, retrieved; // do lagrange on this!
	JPanel originalImage, share1Pan, share2Pan, share3Pan, share4Pan,
	share5Pan, scale1Pan, scale2Pan, scale3Pan, scale4Pan, scale5Pan,
	rePan;

	int width, height, a1, a2, a3, a4, a5, total1, total2, total3, total4,
	total5, new_pixel, new_pixel2, new_pixel3, new_pixel4, new_pixel5;
	int q = 251;
	String globalName = null;
	final double factor = 1.0;

	// CONSTRUCTOR
	// #################################################################################################################################

	public SecretImageSharing() {

		// TODO Auto-generated constructor stub
		Container c = getContentPane();
		// c.setLayout(new FlowLayout());

		JPanel menu = new JPanel();

		originalImage = new JPanel();

		share1Pan = new JPanel();
		share2Pan = new JPanel();
		share3Pan = new JPanel();
		share4Pan = new JPanel();
		share5Pan = new JPanel();
		scale1Pan = new JPanel();
		share1check = new JCheckBox();
		scale2Pan = new JPanel();
		share2check = new JCheckBox();
		scale3Pan = new JPanel();
		share3check = new JCheckBox();
		scale4Pan = new JPanel();
		share4check = new JCheckBox();
		scale5Pan = new JPanel();
		share5check = new JCheckBox();
		rePan = new JPanel();

		menu.setBorder(BorderFactory.createTitledBorder("Options:"));
		menu.setPreferredSize(new Dimension(150, 225));
		originalImage.setBorder(BorderFactory
				.createTitledBorder("Original Image:"));
		originalImage.setPreferredSize(new Dimension(150, 150));
		share1Pan.setBorder(BorderFactory.createTitledBorder("Image Share 1:"));
		share1Pan.setPreferredSize(new Dimension(150, 150));
		share2Pan.setBorder(BorderFactory.createTitledBorder("Image Share 2:"));
		share2Pan.setPreferredSize(new Dimension(150, 150));
		share3Pan.setBorder(BorderFactory.createTitledBorder("Image Share 3:"));
		share3Pan.setPreferredSize(new Dimension(150, 150));
		share4Pan.setBorder(BorderFactory.createTitledBorder("Image Share 4:"));
		share4Pan.setPreferredSize(new Dimension(150, 150));
		share5Pan.setBorder(BorderFactory.createTitledBorder("Image Share 5:"));
		share5Pan.setPreferredSize(new Dimension(150, 150));
		scale1Pan
		.setBorder(BorderFactory.createTitledBorder("Scaled Share 1:"));
		scale1Pan.setPreferredSize(new Dimension(150, 150));
		share1check.setPreferredSize(new Dimension(20, 20));
		scale2Pan
		.setBorder(BorderFactory.createTitledBorder("Scaled Share 2:"));
		scale2Pan.setPreferredSize(new Dimension(150, 150));
		share2check.setPreferredSize(new Dimension(20, 20));
		scale3Pan
		.setBorder(BorderFactory.createTitledBorder("Scaled Share 3:"));
		scale3Pan.setPreferredSize(new Dimension(150, 150));
		share3check.setPreferredSize(new Dimension(20, 20));
		scale4Pan
		.setBorder(BorderFactory.createTitledBorder("Scaled Share 4:"));
		scale4Pan.setPreferredSize(new Dimension(150, 150));
		share4check.setPreferredSize(new Dimension(20, 20));
		scale5Pan
		.setBorder(BorderFactory.createTitledBorder("Scaled Share 5:"));
		scale5Pan.setPreferredSize(new Dimension(150, 150));
		share5check.setPreferredSize(new Dimension(20, 20));

		rePan.setBorder(BorderFactory
				.createTitledBorder("Reconstructed Image:"));
		rePan.setPreferredSize(new Dimension(150, 150));

		c.setLayout(new FlowLayout());

		menu.add(loadImage);
		menu.add(create);
		menu.add(scale);
		menu.add(reconstructB);
		menu.add(aboutDev);
		menu.add(quit);

		// adding everything in the container
		c.add(originalImage);
		c.add(share1Pan);
		c.add(share2Pan);
		c.add(share3Pan);
		c.add(share4Pan);
		c.add(share5Pan);
		c.add(scale1Pan);
		c.add(share1check);
		c.add(scale2Pan);
		c.add(share2check);
		c.add(scale3Pan);
		c.add(share3check);
		c.add(scale4Pan);
		c.add(share4check);
		c.add(scale5Pan);
		c.add(share5check);
		c.add(rePan);
		c.add(menu);

		// adding listeners
		share1check.addActionListener(this);
		share2check.addActionListener(this);
		share3check.addActionListener(this);
		share4check.addActionListener(this);
		share5check.addActionListener(this);
		loadImage.addActionListener(this);
		create.addActionListener(this);
		scale.addActionListener(this);
		reconstructB.addActionListener(this);
		aboutDev.addActionListener(this);
		quit.addActionListener(this);

		setTitle("Security Project : Image Secret Sharing");
		setSize(1200, 500);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public String getSecretImage() {
		String name = null;
		JFileChooser jfc = new JFileChooser();
		// jfc.setFileFilter(ext);
		int buttonClicked = jfc.showOpenDialog(null);

		if (buttonClicked == JFileChooser.APPROVE_OPTION) {
			name = jfc.getSelectedFile().getName();
			try {
				// set tempBI to point to the image loaded!
				tempBI = ImageIO.read(getClass().getResource("/" + name));

				ImageIcon tempII = new ImageIcon(getClass().getResource("/" + name));
				// setting the image in the JLabel
				JLabel tempLabel = new JLabel(tempII);
				originalImage.add(tempLabel);
				originalImage.setVisible(true);
				originalImage.revalidate();
				System.out.println("Image Picked: " +name);

			} catch (Exception e) {
				e.getMessage();
			}
		}
		return name;
	}

	public void marchThroughImage(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		System.out.println("width, height: " + w + ", " + h);

		// pixel arrays for 5 shares. New pixel values will be stored!
		int[] new_pixelarr1 = new int[w * h];
		int[] new_pixelarr2 = new int[w * h];
		int[] new_pixelarr3 = new int[w * h];
		int[] new_pixelarr4 = new int[w * h];
		int[] new_pixelarr5 = new int[w * h];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel = image.getRGB(j, i);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				new_pixelarr1[j + (i * w)] = share1(red, green, blue);
				new_pixelarr2[j + (i * w)] = share2(red, green, blue);
				new_pixelarr3[j + (i * w)] = share3(red, green, blue);
				new_pixelarr4[j + (i * w)] = share4(red, green, blue);
				new_pixelarr5[j + (i * w)] = share5(red, green, blue);
			}
		}

		try {
			// image_shares should be given array with packed_RGB value format.
			BufferedImage image_share1 = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = image_share1.getRaster();
			rast.setDataElements(0, 0, w, h, new_pixelarr1);
			ImageIO.write(image_share1,"jpg",new File("Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share1.jpg"));
			ImageIO.write(
					image_share1,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/src/share1.jpg"));

			BufferedImage image_share2 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			WritableRaster rast2 = image_share2.getRaster();
			rast2.setDataElements(0, 0, w, h, new_pixelarr2);
			ImageIO.write(image_share2,"jpg",new File("Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share2.jpg"));
			ImageIO.write(
					image_share2,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/src/share2.jpg"));

			BufferedImage image_share3 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			WritableRaster rast3 = image_share3.getRaster();
			rast3.setDataElements(0, 0, w, h, new_pixelarr3);
			ImageIO.write(
					image_share3,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share3.jpg"));
			ImageIO.write(
					image_share3,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/src/share3.jpg"));

			BufferedImage image_share4 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			WritableRaster rast4 = image_share4.getRaster();
			rast4.setDataElements(0, 0, w, h, new_pixelarr4);
			ImageIO.write(
					image_share4,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share4.jpg"));
			ImageIO.write(
					image_share4,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/src/share4.jpg"));

			BufferedImage image_share5 = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			WritableRaster rast5 = image_share5.getRaster();
			rast5.setDataElements(0, 0, w, h, new_pixelarr5);
			ImageIO.write(
					image_share5,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share5.jpg"));
			ImageIO.write(
					image_share5,
					"jpg",
					new File(
							"Z:/UALBANY/SECURITY/Project/Secret Sharing/src/share5.jpg"));

		} catch (Exception io) {
			io.printStackTrace();
		}

		i1 = new ImageIcon(
				"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share1.jpg");
		img1 = new JLabel(i1);
		share1Pan.add(img1);
		share1Pan.setVisible(true);
		share1Pan.revalidate();

		i2 = new ImageIcon(
				"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share2.jpg");
		img2 = new JLabel(i2);
		share2Pan.add(img2);
		share2Pan.setVisible(true);
		share2Pan.revalidate();

		i3 = new ImageIcon(
				"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share3.jpg");
		img3 = new JLabel(i3);
		share3Pan.add(img3);
		share3Pan.setVisible(true);
		share3Pan.revalidate();

		i4 = new ImageIcon(
				"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share4.jpg");
		img4 = new JLabel(i4);
		share4Pan.add(img4);
		share4Pan.setVisible(true);
		share4Pan.revalidate();

		i5 = new ImageIcon(
				"Z:/UALBANY/SECURITY/Project/Secret Sharing/bin/share5.jpg");
		img5 = new JLabel(i5);
		share5Pan.add(img5);
		share5Pan.setVisible(true);
		share5Pan.revalidate();

	}

	public int share1(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;

		a1 = rand.nextInt(q);
		total1 = r + g + b + a1; // with randomization
		// total1 = r+g+b; //without randominzation
		new_pixel = total1 % q;
		int newPixelValue = 0xff000000 | (new_pixel & 0xff) << 16
				| (new_pixel & 0xff) << 8 | (new_pixel & 0xff);

		return newPixelValue;
	}

	public int share2(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;

		a2 = rand.nextInt(q);
		total2 = r + (g * 2) + (b * 4) + (a2 * 8);
		// total2 = r+(g*2)+(b*4);
		new_pixel2 = total2 % q;
		int newPixelValue2 = 0xff000000 | (new_pixel2 & 0xff) << 16
				| (new_pixel2 & 0xff) << 8 | (new_pixel2 & 0xff);

		return newPixelValue2;
	}

	public int share3(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;

		a3 = rand.nextInt(q);
		total3 = r + (g * 3) + (b * 9) + (a3 * 27);
		// total3 = r+(g*3)+(b*9);
		new_pixel3 = total3 % q;
		int newPixelValue3 = 0xff000000 | (new_pixel3 & 0xff) << 16
				| (new_pixel3 & 0xff) << 8 | (new_pixel3 & 0xff);

		return newPixelValue3;
	}

	public int share4(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;

		a4 = rand.nextInt(q);
		total4 = r + (g * 4) + (b * 16) + (a3 * 64);
		// total4 = r+(g*4)+(b*16);
		new_pixel4 = total4 % q;
		int newPixelValue4 = 0xff000000 | (new_pixel4 & 0xff) << 16
				| (new_pixel4 & 0xff) << 8 | (new_pixel4 & 0xff);

		return newPixelValue4;
	}

	public int share5(int red, int green, int blue) {
		int r = red;
		int g = green;
		int b = blue;

		a5 = rand.nextInt(q);
		total5 = r + (g * 5) + (b * 25) + (a3 * 125);
		// total5 = r+(g*5)+(b*25);
		new_pixel5 = total5 % q;
		int newPixelValue5 = 0xff000000 | (new_pixel5 & 0xff) << 16
				| (new_pixel5 & 0xff) << 8 | (new_pixel5 & 0xff);

		return newPixelValue5;
	}

	public BufferedImage reconstruct(BufferedImage tempo, double factor) {
		try {
			int rw = tempo.getWidth();
			int rh = tempo.getHeight();

			System.out.println("factor " + factor);
			Double retrievedWD = rw * factor;
			Double retrievedHD = rw * factor;
			int retrievedW = retrievedWD.intValue();
			int retrievedH = retrievedHD.intValue();

			retrieved = new BufferedImage(retrievedW, retrievedH,
					tempo.getType());
			Graphics2D g = retrieved.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(tempo, 0, 0, retrievedW, retrievedH, 0, 0, rw, rh, null);
			g.dispose();
			ImageIO.write(retrieved, "jpg", new File("retrievedImage.jpg"));
			// RESCALED SECRET WILL BE STORED AS retrievedImage.jpg
			// System.out.println("height and width of resized secret is "+retrieved.getWidth()+" "+retrieved.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrieved;
	}

	public void scaleShares(double factor) {
		try {

			// String filename =
			// "Z\\UALBANY\\SECURITY\\Project\\Secret Sharing\\share1.jpg";
			// InputStream in = getClass().getResourceAsStream(filename);
			//
			//
			BufferedImage retrievedShare1 = ImageIO.read(getClass().getResourceAsStream("/share1.jpg"));

			BufferedImage retrievedShare2 = ImageIO.read(getClass()
					.getResource("/share2.jpg"));
			BufferedImage retrievedShare3 = ImageIO.read(getClass()
					.getResource("/share3.jpg"));
			BufferedImage retrievedShare4 = ImageIO.read(getClass()
					.getResource("/share4.jpg"));
			BufferedImage retrievedShare5 = ImageIO.read(getClass()
					.getResource("/share5.jpg"));

			int sw = retrievedShare1.getWidth();
			int sh = retrievedShare1.getHeight();

			Double scaledWD = sw * factor;
			Double scaledHD = sw * factor;
			int scaledW = scaledWD.intValue();
			int scaledH = scaledHD.intValue();

			// THIS IS WHERE I DO BILINEAR INTERPOLATION
			BufferedImage resized = new BufferedImage(scaledW, scaledH,
					retrievedShare1.getType());
			Graphics2D g = resized.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(retrievedShare1, 0, 0, scaledW, scaledH, 0, 0, sw, sh,
					null);
			g.dispose();
			ImageIO.write(resized, "jpg", new File("rescaledShare1.jpg"));
			// RESCALED SHARE 1 WILL BE STORED AS rescaledShare1.jpg
			// System.out.println("height and width of resized 1 "+resized.getWidth()+" "+resized.getHeight());

			BufferedImage resized2 = new BufferedImage(scaledW, scaledH,
					retrievedShare2.getType());
			Graphics2D g2 = resized2.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(retrievedShare2, 0, 0, scaledW, scaledH, 0, 0, sw, sh,
					null);
			g2.dispose();
			ImageIO.write(resized2, "jpg", new File("rescaledShare2.jpg"));
			// RESCALED SHARE 2 WILL BE STORED AS rescaledShare2.jpg
			// System.out.println("height and width of resized 2 "+resized2.getWidth()+" "+resized2.getHeight());

			BufferedImage resized3 = new BufferedImage(scaledW, scaledH,
					retrievedShare3.getType());
			Graphics2D g3 = resized3.createGraphics();
			g3.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g3.drawImage(retrievedShare3, 0, 0, scaledW, scaledH, 0, 0, sw, sh,
					null);
			g3.dispose();
			ImageIO.write(resized3, "jpg", new File("rescaledShare3.jpg"));
			// RESCALED SHARE 3 WILL BE STORED AS rescaledShare3.jpg
			// System.out.println("height and width of resized 3 "+resized3.getWidth()+" "+resized3.getHeight());

			BufferedImage resized4 = new BufferedImage(scaledW, scaledH,
					retrievedShare4.getType());
			Graphics2D g4 = resized4.createGraphics();
			g4.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g4.drawImage(retrievedShare4, 0, 0, scaledW, scaledH, 0, 0, sw, sh,
					null);
			g4.dispose();
			ImageIO.write(resized4, "jpg", new File("rescaledShare4.jpg"));
			// RESCALED SHARE 4 WILL BE STORED AS rescaledShare4.jpg
			// System.out.println("height and width of resized 4 "+resized4.getWidth()+" "+resized4.getHeight());

			BufferedImage resized5 = new BufferedImage(scaledW, scaledH,
					retrievedShare5.getType());
			Graphics2D g5 = resized5.createGraphics();
			g5.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g5.drawImage(retrievedShare5, 0, 0, scaledW, scaledH, 0, 0, sw, sh,
					null);
			g5.dispose();
			ImageIO.write(resized5, "jpg", new File("rescaledShare5.jpg"));
			// RESCALED SHARE 5 WILL BE STORED AS rescaledShare5.jpg
			// System.out.println("height and width of resized 5"+resized5.getWidth()+" "+resized5.getHeight());

			i6 = new ImageIcon(
					"Z:/UALBANY/SECURITY/Project/Secret Sharing/rescaledShare1.jpg");
			img6 = new JLabel(i6);
			scale1Pan.add(img6);
			scale1Pan.setVisible(true);
			scale1Pan.revalidate();

			i7 = new ImageIcon(
					"Z:/UALBANY/SECURITY/Project/Secret Sharing/rescaledShare2.jpg");
			img7 = new JLabel(i7);
			scale2Pan.add(img7);
			scale2Pan.setVisible(true);
			scale2Pan.revalidate();

			i8 = new ImageIcon(
					"Z:/UALBANY/SECURITY/Project/Secret Sharing/rescaledShare3.jpg");
			img8 = new JLabel(i8);
			scale3Pan.add(img8);
			scale3Pan.setVisible(true);
			scale3Pan.revalidate();

			i9 = new ImageIcon("Z:/UALBANY/SECURITY/Project/Secret Sharing/rescaledShare4.jpg");
			img9 = new JLabel(i9);
			scale4Pan.add(img9);
			scale4Pan.setVisible(true);
			scale4Pan.revalidate();

			i10 = new ImageIcon("Z:/UALBANY/SECURITY/Project/Secret Sharing/rescaledShare5.jpg");
			img10 = new JLabel(i10);
			scale5Pan.add(img10);
			scale5Pan.setVisible(true);
			scale5Pan.revalidate();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent ae) {
		// loading the image
		if (ae.getSource() == loadImage) {
			globalName = getSecretImage();
		}

		// code for creating 5 shares here
		else if (ae.getSource() == create) {
			long tic = System.nanoTime();
			try {
				// save(tempBI);
				marchThroughImage(tempBI);
			} catch (Exception e) {
				e.printStackTrace();
			}

			long toc = System.nanoTime();
			long nano = toc - tic;
			double seconds = (double) nano / 1000000000.0;
			System.out.print("Time taken to construct shares(sec): " + seconds);
		}

		// reconstruct image using Lagrange Interpolation
		else if (ae.getSource() == scale) {
			scaleShares(factor);
		} 
		else if (ae.getSource() == reconstructB) {
			int reconstructCheck[] = { 0, 0, 0, 0, 0 };
			int count = 0;

			if (share1check.getModel().isSelected()) {
				reconstructCheck[0] = 1;
			}
			if (share2check.getModel().isSelected()) {
				reconstructCheck[1] = 1;
			}
			if (share3check.getModel().isSelected()) {
				reconstructCheck[2] = 1;
			}
			if (share4check.getModel().isSelected()) {
				reconstructCheck[3] = 1;
			}
			if (share5check.getModel().isSelected()) {
				reconstructCheck[4] = 1;
			}

			for (int iter = 0; iter < 5; iter++) {
				if (reconstructCheck[iter] == 1) {
					count++;
				}
			}

			// only 4 images can recreate the secret in (3, 4, 5) scheme
			if (!(count == 4)) {
				JOptionPane.showMessageDialog(null,
						"Choose 4 shares to reconstruct secret!");
			}

			else {
				try {

					tempo = ImageIO.read(getClass().getResource("/" + globalName));
					BufferedImage who = reconstruct(tempo, factor);
					ImageIcon tempo2 = new ImageIcon("Z:/UALBANY/SECURITY/Project/Secret Sharing/retrievedImage.jpg");
					// setting the image in the JLabel
					JLabel tempLabel = new JLabel(tempo2);
					rePan.add(tempLabel);
					rePan.setVisible(true);
					rePan.revalidate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		else if (ae.getSource() == aboutDev) {
			JOptionPane.showMessageDialog(null, aboutMe);
		} else if (ae.getSource() == quit) {
			System.exit(0);
		}
	}

	public static void main(String[] foo) {
		SecretImageSharing f = new SecretImageSharing();
		f.setVisible(true);
		f.setTitle("Security Project by Samarth Shah");
	}
};