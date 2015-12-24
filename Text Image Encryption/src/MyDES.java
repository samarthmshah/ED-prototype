/**
 * 
 */

/**
 * @author Shah, Samarth SONY
 * Dec 21, 2014
 * TrialTwo.java	
 * Trying DES over here, MYSELF!
 */

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;

class TrialTwo {
	byte[] buf = new byte[1024];
	Cipher ecipher;
	Cipher dcipher;

	TrialTwo(SecretKey key) throws Exception {

		//we need an IV since we are using a CBC mode of 8 bytes.
		byte[] iv = new byte[] { (byte) 0x8E, 0x12, 0x39, (byte) 0x9C, 0x07,0x72, 0x6F, 0x5A };
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		
		// CBC mode and YES padding.
		ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		//initialize the cipher with key and iv!
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	}

	//we will encrypt file 1 and file2!
	public void encrypt(InputStream in, OutputStream out) throws Exception {
		out = new CipherOutputStream(out, ecipher);

		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}
	
	public byte[] encrypt1(int w, int h, byte[] plainPixelKeeper) throws Exception
	{
		byte[] cipherPixelKeeper = ecipher.doFinal(plainPixelKeeper);
		int[] cipherPixelKeeperInt = convertToIntArray(cipherPixelKeeper);

		try
		{
			BufferedImage cipherImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = cipherImage.getRaster();
			rast.setDataElements(0, 0, w, h, cipherPixelKeeperInt);
			ImageIO.write(cipherImage,"jpg",new File(System.getProperty("user.dir")+"\\src\\Image1DESCipher.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
		
		return cipherPixelKeeper;
	}

	public void decrypt1(int w, int h, byte[] cipherPixelKeeper) throws Exception
	{
		try
		{
			byte[] plainPixelKeeper = dcipher.doFinal(cipherPixelKeeper);
			
			int[] plainPixelKeeperInt = convertToIntArray(plainPixelKeeper);
			BufferedImage plainImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = plainImage.getRaster();
			rast.setDataElements(0, 0, w, h, plainPixelKeeperInt);
			ImageIO.write(plainImage,"jpg",new File(System.getProperty("user.dir")+"\\src\\Image1DESPlain.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
	}

	public byte[] encrypt2(int w, int h, byte[] plainPixelKeeper) throws Exception
	{
		byte[] cipherPixelKeeper = ecipher.doFinal(plainPixelKeeper);
		int[] cipherPixelKeeperInt = convertToIntArray(cipherPixelKeeper);

		try
		{
			BufferedImage cipherImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = cipherImage.getRaster();
			rast.setDataElements(0, 0, w, h, cipherPixelKeeperInt);
			ImageIO.write(cipherImage,"jpg",new File(System.getProperty("user.dir")+"\\src\\Image2DESCipher.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
		
		return cipherPixelKeeper;
	}
	
	public void decrypt2(int w, int h, byte[] cipherPixelKeeper) throws Exception
	{
		try
		{
			byte[] plainPixelKeeper = dcipher.doFinal(cipherPixelKeeper);
			
			int[] plainPixelKeeperInt = convertToIntArray(plainPixelKeeper);
			BufferedImage plainImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = plainImage.getRaster();
			rast.setDataElements(0, 0, w, h, plainPixelKeeperInt);
			ImageIO.write(plainImage,"jpg",new File(System.getProperty("user.dir")+"\\src\\Image2DESPlain.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
	}

	public int[] convertToIntArray(byte[] input)
	{
	    int[] ret = new int[input.length];
	    for (int i = 0; i < input.length; i++)
	    {
	        ret[i] = input[i];
	    }
	    return ret;
	}

	public void decrypt(InputStream in, OutputStream out) throws Exception {
		in = new CipherInputStream(in, dcipher);

		int numRead = 0;
		while ((numRead = in.read(buf)) >= 0) {
			out.write(buf, 0, numRead);
		}
		out.close();
	}

	public static void main(String[] argv) throws Exception {
		SecretKey key = KeyGenerator.getInstance("DES").generateKey();
		TrialTwo encrypter = new TrialTwo(key);
		File fin = new File(System.getProperty("user.dir")+"\\src\\Textfile2.txt");
		File foutCipher = new File(System.getProperty("user.dir")+"\\src\\Textfile2DESCipher.txt");
		File fout = new File(System.getProperty("user.dir")+"\\src\\Textfile2DESPlain.txt");
		encrypter.encrypt(new FileInputStream(fin),new FileOutputStream(foutCipher));
		encrypter.decrypt(new FileInputStream(foutCipher),new FileOutputStream(fout));
	}
}
