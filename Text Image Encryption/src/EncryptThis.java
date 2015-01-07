import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Shah, Samarth SONY
 * Dec 21, 2014
 * EncryptThis.java	
 */
public class EncryptThis extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	final String aboutMe = "Samarth Shah \n \n ICSI 660: Security and Privacy \n sshah4@albany.edu \n \n University at Albany, NY";
	JButton loadImage1RSA = new JButton("E/D Image 1 RSA");
	JButton loadImage2RSA = new JButton("E/D Image 2 RSA");
	JButton loadText1RSA = new JButton("E/D Text 1 RSA");
	JButton loadText2RSA = new JButton("E/D Text 2 RSA");
	JButton loadImage1DES = new JButton("E/D Image 1 DES");
	JButton loadImage2DES = new JButton("E/D Image 2 DES");
	JButton loadText1DES = new JButton("E/D Text 1 DES");
	JButton loadText2DES = new JButton("E/D Text 2 DES");
	JButton loadImage1AES = new JButton("E/D Image 1 AES");
	JButton loadImage2AES = new JButton("E/D Image 2 AES");
	JButton loadText1AES = new JButton("E/D Text 1 AES");
	JButton loadText2AES = new JButton("E/D Text 2 AES");
	JButton aboutDev = new JButton("About Dev");
	JButton quit = new JButton("Quit");
	private final int BITLEN = 512;
	BufferedImage temp1, temp2;
	private BigInteger n, d, e;
	byte[] buf = new byte[1024];

	public EncryptThis() {
		// TODO Auto-generated constructor stub
		Container c = getContentPane();
		JPanel menu1 = new JPanel();
		menu1.setBorder(BorderFactory.createTitledBorder("RSA:"));
		menu1.setPreferredSize(new Dimension(150, 225));
		JPanel menu2 = new JPanel();
		menu2.setBorder(BorderFactory.createTitledBorder("DES:"));
		menu2.setPreferredSize(new Dimension(150, 225));
		JPanel menu3 = new JPanel();
		menu3.setBorder(BorderFactory.createTitledBorder("AES:"));
		menu3.setPreferredSize(new Dimension(150, 225));
		c.setLayout(new FlowLayout());

		menu1.add(loadImage1RSA);
		menu1.add(loadImage2RSA);
		menu1.add(loadText1RSA);
		menu1.add(loadText2RSA);
		menu2.add(loadImage1DES);
		menu2.add(loadImage2DES);
		menu2.add(loadText1DES);
		menu2.add(loadText2DES);
		menu3.add(loadImage1AES);
		menu3.add(loadImage2AES);
		menu3.add(loadText1AES);
		menu3.add(loadText2AES);
		menu3.add(aboutDev);
		menu3.add(quit);

		c.add(menu1);
		c.add(menu2);
		c.add(menu3);

		loadImage1RSA.addActionListener(this);
		loadImage2RSA.addActionListener(this);
		loadText1RSA.addActionListener(this);
		loadText2RSA.addActionListener(this);
		loadImage1DES.addActionListener(this);
		loadImage2DES.addActionListener(this);
		loadText1DES.addActionListener(this);
		loadText2DES.addActionListener(this);
		loadImage1AES.addActionListener(this);
		loadImage2AES.addActionListener(this);
		loadText1AES.addActionListener(this);
		loadText2AES.addActionListener(this);
		aboutDev.addActionListener(this);
		quit.addActionListener(this);

		setTitle("Security Assignment : RSA, DES & AES");
		setSize(500, 300);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public EncryptThis(int bits) {
		// TODO Auto-generated constructor stub

		// p and q are 512 bits in length and truly random.
		BigInteger p = new BigInteger(BITLEN, 100, new SecureRandom());
		BigInteger q = new BigInteger(BITLEN, 100, new SecureRandom());
		// n = p*q
		n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		// m = p-1 * q-1
		// we obtain e = gcd(e,m) = 1! Initialize by 3.
		// we try all the odd numbers and keep checking until gcd m,e = 1!
		e = new BigInteger("3");
		while (m.gcd(e).intValue() > 1) {
			e = e.add(new BigInteger("2"));
		}
		// we simply find d as d.e.modm = 1 as inverse of e, mod m
		d = e.modInverse(m);
		// NOW WE KNOW WHAT n, e and d IS.
	}

	public BigInteger encrypt(BigInteger message) {
		// modPow is an inbuilt method in BigInteger class , which does this^exp
		// mod m.
		return message.modPow(e, n);
	}

	/** Decrypt the given ciphertext message. */
	public BigInteger decrypt(BigInteger message) {
		return message.modPow(d, n);
	}

	public int[] convertToIntArray(byte[] input) {
		int[] ret = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			ret[i] = input[i];
		}
		return ret;
	}

	public BufferedImage getImage1() {
		String name = "Image1.JPG";

		try {
			temp1 = ImageIO.read(getClass().getResource("/" + name));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return temp1;
	}

	public BufferedImage getImage2() {
		String name2 = "Image2.JPG";

		try {
			temp2 = ImageIO.read(getClass().getResource("/" + name2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return temp2;
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == loadImage1RSA) {
			EncryptThis et = new EncryptThis(512);
			long tic = System.nanoTime();

			try {
				BufferedImage temporary = getImage1();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				int[] cipherPixelKeeper = new int[w * h];
				int[] plainPixelKeeper = new int[w * h];

				System.out.println(w + "x" + h + " Resolution of Image1");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {

						Integer pixel = new Integer(temporary.getRGB(j, i));

						BigInteger plaintext = new BigInteger(pixel.toString());
						BigInteger ciphertext = et.encrypt(plaintext);

						cipherPixelKeeper[j + (i * w)] = ciphertext.intValue();
						plainPixelKeeper[j + (i * w)] = (et.decrypt(ciphertext)).intValue();
					}
				}

				BufferedImage cipherImage = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				WritableRaster rast = cipherImage.getRaster();
				rast.setDataElements(0, 0, w, h, cipherPixelKeeper);
				ImageIO.write(cipherImage, "jpg",
						new File(System.getProperty("user.dir")
								+ "\\src\\Image1RSACipher.jpg"));

				BufferedImage plainImage = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				WritableRaster rast1 = plainImage.getRaster();
				rast1.setDataElements(0, 0, w, h, plainPixelKeeper);
				ImageIO.write(plainImage, "jpg",
						new File(System.getProperty("user.dir")
								+ "\\src\\Image1RSAPlain.jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long nano = toc - tic;
			long ms = TimeUnit.NANOSECONDS.toMillis(nano);
			System.out
					.println("Time taken to encrypt/decrypt image 1 using RSA: "
							+ ms + " ms");

		} else if (ae.getSource() == loadImage2RSA) {
			EncryptThis et = new EncryptThis(512);
			long tic = System.nanoTime();

			try {
				BufferedImage temporary = getImage2();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				int cipherPixelKeeper[] = new int[w * h];
				int plainPixelKeeper[] = new int[w * h];
				System.out.println(w + "x" + h + " Resolution of Image 2");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						Integer pixel = new Integer(temporary.getRGB(j, i));
						BigInteger plaintext = new BigInteger(pixel.toString());
						BigInteger ciphertext = et.encrypt(plaintext);
						cipherPixelKeeper[j + (i * w)] = ciphertext.intValue();
						plainPixelKeeper[j + (i * w)] = (et.decrypt(ciphertext)).intValue();
					}
				}
				BufferedImage cipherImage = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				WritableRaster rast = cipherImage.getRaster();
				rast.setDataElements(0, 0, w, h, cipherPixelKeeper);
				ImageIO.write(cipherImage, "jpg",
						new File(System.getProperty("user.dir")
								+ "\\src\\Image2RSACipher.jpg"));

				BufferedImage plainImage = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				WritableRaster rast1 = plainImage.getRaster();
				rast1.setDataElements(0, 0, w, h, plainPixelKeeper);
				ImageIO.write(plainImage, "jpg",
						new File(System.getProperty("user.dir")
								+ "\\src\\Image2RSAPlain.jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long nano = toc - tic;
			long ms = TimeUnit.NANOSECONDS.toMillis(nano);
			System.out.println("Time taken to encrypt/decrypt image 2 using RSA: "
							+ ms + " ms");

		} else if (ae.getSource() == loadText1RSA) {
			File fin = new File(System.getProperty("user.dir")+ "\\src\\Textfile1.txt");
			File foutCipher = new File(System.getProperty("user.dir")+ "\\src\\Textfile1RSACipher.txt");
			File fout = new File(System.getProperty("user.dir")+ "\\src\\Textfile1RSAPlain.txt");
			long tic = System.nanoTime();
			EncryptThis et = new EncryptThis(512);

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fin)));
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(foutCipher, true)));
				PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(fout, true)));

				String line = null;
				while ((line = br.readLine()) != null) {
					line.trim();
					if (!line.equals("")) {
						BigInteger plaintext = new BigInteger(line.getBytes());
						BigInteger ciphertext = et.encrypt(plaintext);
						out.println(new String(ciphertext.toByteArray()));
						plaintext = et.decrypt(ciphertext);
						out1.println(new String(plaintext.toByteArray()));
					} else
						continue;
				}
				br.close();
				out.close();
				out1.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long nano = toc - tic;
			long ms = TimeUnit.NANOSECONDS.toMillis(nano);
			System.out.println("Time taken to encrypt/decrypt text 1 using RSA: "+ms+ " ms");
		}

		else if (ae.getSource() == loadText2RSA) {
			File fin = new File(System.getProperty("user.dir")+ "\\src\\Textfile2.txt");
			File foutCipher = new File(System.getProperty("user.dir")+ "\\src\\Textfile2RSACipher.txt");
			File fout = new File(System.getProperty("user.dir")+ "\\src\\Textfile2RSAPlain.txt");
			long tic = System.nanoTime();
			EncryptThis et = new EncryptThis(512);

			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fin)));
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(foutCipher, true)));
				PrintWriter out1 = new PrintWriter(new BufferedWriter(new FileWriter(fout, true)));

				String line = null;
				while ((line = br.readLine()) != null) {
					line.trim();
					if (!line.equals("")) {
						BigInteger plaintext = new BigInteger(line.getBytes());
						BigInteger ciphertext = et.encrypt(plaintext);
						out.println(new String(ciphertext.toByteArray()));
						plaintext = et.decrypt(ciphertext);
						out1.println(new String(plaintext.toByteArray()));
					} 
					else continue;
				}
				br.close();
				out.close();
				out1.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long nano = toc - tic;
			long ms = TimeUnit.NANOSECONDS.toMillis(nano);
			System.out.println("Time taken to encrypt/decrypt text 2 using RSA: "+ ms + " ms");
		}

		else if (ae.getSource() == loadImage1DES) {
			long tic = System.nanoTime();
			try {
				BufferedImage temporary = getImage1();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				byte plainPixelKeeper[] = new byte[w * h];
				byte cipherPixelKeeper[] = new byte[w * h];
				System.out.println(w + "x" + h + " Resolution of Image1");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						plainPixelKeeper[j + (i * w)] = new Integer(temporary.getRGB(j, i)).byteValue();
					}
				}
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				TrialTwo encrypter = new TrialTwo(key);
				cipherPixelKeeper = encrypter.encrypt1(w, h, plainPixelKeeper);
				encrypter.decrypt1(w, h, cipherPixelKeeper);
				long toc = System.nanoTime();
				long nano = toc - tic;
				long ms = TimeUnit.NANOSECONDS.toMillis(nano);
				System.out.println("Time taken to encrypt/decrypt image 1 using DES: "+ ms + " ms");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == loadImage2DES) {
			long tic = System.nanoTime();

			try {
				BufferedImage temporary = getImage2();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				byte plainPixelKeeper[] = new byte[w * h];
				byte cipherPixelKeeper[] = new byte[w * h];
				System.out.println(w + "x" + h + " Resolution of Image2");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						plainPixelKeeper[j + (i * w)] = new Integer(
								temporary.getRGB(j, i)).byteValue();
					}
				}
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				TrialTwo encrypter = new TrialTwo(key);
				cipherPixelKeeper = encrypter.encrypt2(w, h, plainPixelKeeper);
				encrypter.decrypt2(w, h, cipherPixelKeeper);
				long toc = System.nanoTime();
				long nano = toc - tic;
				long ms = TimeUnit.NANOSECONDS.toMillis(nano);
				System.out
						.println("Time taken to encrypt/decrypt image 2 using DES: "
								+ ms + " ms");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == loadText1DES) {
			try {
				long tic = System.nanoTime();
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();
				TrialTwo encrypter = new TrialTwo(key);
				File fin = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1.txt");
				File foutCipher = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1DESCipher.txt");
				File fout = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1DESPlain.txt");
				encrypter.encrypt(new FileInputStream(fin),
						new FileOutputStream(foutCipher));
				encrypter.decrypt(new FileInputStream(foutCipher),
						new FileOutputStream(fout));
				long toc = System.nanoTime();
				long ms = TimeUnit.NANOSECONDS.toMillis(toc - tic);
				System.out
						.println("Time taken to encrypt/decrypt text 1 using DES: "
								+ ms + " ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == loadText2DES) {
			try {
				long tic = System.nanoTime();
				SecretKey key2 = KeyGenerator.getInstance("DES").generateKey();
				TrialTwo encrypter2 = new TrialTwo(key2);
				File fin2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2.txt");
				File foutCipher2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2DESCipher.txt");
				File fout2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2DESPlain.txt");
				encrypter2.encrypt(new FileInputStream(fin2),
						new FileOutputStream(foutCipher2));
				encrypter2.decrypt(new FileInputStream(foutCipher2),
						new FileOutputStream(fout2));
				long toc = System.nanoTime();
				long nano = toc - tic;
				long ms = TimeUnit.NANOSECONDS.toMillis(nano);
				System.out
						.println("Time taken to encrypt/decrypt text 2 using DES: "
								+ ms + " ms");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == loadImage1AES) {
			long tic = System.nanoTime();
			try {
				BufferedImage temporary = getImage1();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				byte plainPixelKeeper[] = new byte[w * h];
				byte cipherPixelKeeper[] = new byte[w * h];
				System.out.println(w + "x" + h + " Resolution of Image1");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						plainPixelKeeper[j + (i * w)] = new Integer(temporary.getRGB(j, i)).byteValue();
					}
				}
				TrialThree aesencryption = new TrialThree();
				cipherPixelKeeper = aesencryption.encrypt1(w, h,plainPixelKeeper);
				aesencryption.decrypt1(w, h, cipherPixelKeeper);

				long toc = System.nanoTime();
				long nano = toc - tic;
				long ms = TimeUnit.NANOSECONDS.toMillis(nano);
				System.out.println("Time taken to encrypt/decrypt image 1 using AES: "+ ms + " ms");
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (ae.getSource() == loadImage2AES) {
			long tic = System.nanoTime();
			try {
				BufferedImage temporary = getImage2();
				int w = temporary.getWidth();
				int h = temporary.getHeight();
				byte plainPixelKeeper[] = new byte[w * h];
				byte cipherPixelKeeper[] = new byte[w * h];
				System.out.println(w + "x" + h + " Resolution of Image2");

				for (int i = 0; i < h; i++) {
					for (int j = 0; j < w; j++) {
						plainPixelKeeper[j + (i * w)] = new Integer(
								temporary.getRGB(j, i)).byteValue();
					}
				}
				TrialThree aesencryption2 = new TrialThree();
				cipherPixelKeeper = aesencryption2.encrypt2(w, h,
						plainPixelKeeper);
				aesencryption2.decrypt2(w, h, cipherPixelKeeper);

				long toc = System.nanoTime();
				long nano = toc - tic;
				long ms = TimeUnit.NANOSECONDS.toMillis(nano);
				System.out
						.println("Time taken to encrypt/decrypt image 2 using AES: "
								+ ms + " ms");
			}

			catch (Exception e) {
				e.printStackTrace();
			}

		}

		else if (ae.getSource() == loadText1AES) {
			long tic = System.nanoTime();
			try {
				TrialThree tt = new TrialThree();
				File fin = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1.txt");
				File foutCipher = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1AESCipher.txt");
				File fout = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile1AESPlain.txt");

				tt.encrypt(fin, foutCipher);
				tt.decrypt(foutCipher, fout);
			} catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long milli = TimeUnit.NANOSECONDS.toMillis(toc - tic);
			System.out
					.println("Time taken to encrypt/decrypt text 2 using AES: "
							+ milli + " ms");
		}

		else if (ae.getSource() == loadText2AES) {
			long tic = System.nanoTime();
			try {
				TrialThree tt2 = new TrialThree();
				File fin2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2.txt");
				File foutCipher2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2AESCipher.txt");
				File fout2 = new File(System.getProperty("user.dir")
						+ "\\src\\Textfile2AESPlain.txt");

				tt2.encrypt(fin2, foutCipher2);
				tt2.decrypt(foutCipher2, fout2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			long toc = System.nanoTime();
			long milli = TimeUnit.NANOSECONDS.toMillis(toc - tic);
			System.out
					.println("Time taken to encrypt/decrypt text 2 using AES: "
							+ milli + " ms");
		}

		else if (ae.getSource() == aboutDev) {
			JOptionPane.showMessageDialog(null, aboutMe);
		} else if (ae.getSource() == quit) {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		EncryptThis ee = new EncryptThis();
		ee.setVisible(true);
		ee.setTitle("Assignment by Samarth Shah");
	}
};
