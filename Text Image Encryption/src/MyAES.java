/**
 * @author Shah, Samarth
 * April 21, 2015
 * TrialThree.java	
 */

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;

public class MyAES {
	private final String CURRENT_DIRECTORY = System.getProperty("user.dir");
	
	SecretKeySpec key;
	Cipher aesCipherE, aesCipherD;

	public MyAES() throws GeneralSecurityException {
		// TODO Auto-generated constructor stub
		// create AES shared key cipher
		
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		keygen.init(128);
		SecretKey sk = keygen.generateKey();
		byte[] r = sk.getEncoded();
		
		//THIS IS THE KEY
		key = new SecretKeySpec(r, "AES");
		
		aesCipherE = Cipher.getInstance("AES");
		aesCipherE.init(Cipher.ENCRYPT_MODE, key);
		
		aesCipherD = Cipher.getInstance("AES");
		aesCipherD.init(Cipher.DECRYPT_MODE, key);
	}
	
	public void encrypt(File in, File out) throws IOException,InvalidKeyException {
		
		FileInputStream is = new FileInputStream(in);
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(out), aesCipherE);

		copy(is, os);

		os.close();
	}

	public void decrypt(File in, File out) throws IOException,InvalidKeyException {

		CipherInputStream is = new CipherInputStream(new FileInputStream(in),aesCipherD);
		FileOutputStream os = new FileOutputStream(out);

		copy(is, os);

		is.close();
		os.close();
	}

	private void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while ((i = is.read(b)) != -1) {
			os.write(b, 0, i);
		}
	}

	public byte[] encrypt1(int w, int h, byte[] plainPixelKeeper) throws Exception
	{
		byte[] cipherPixelKeeper = aesCipherE.doFinal(plainPixelKeeper);
		int[] cipherPixelKeeperInt = convertToIntArray(cipherPixelKeeper);

		try
		{
			BufferedImage cipherImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = cipherImage.getRaster();
			rast.setDataElements(0, 0, w, h, cipherPixelKeeperInt);
			ImageIO.write(cipherImage,"jpg",new File(CURRENT_DIRECTORY + File.separator + "src" + File.separator + "Image1AESCipher.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
		
		return cipherPixelKeeper;
	}

	public void decrypt1(int w, int h, byte[] cipherPixelKeeper) throws Exception
	{
		try
		{
			byte[] plainPixelKeeper = aesCipherD.doFinal(cipherPixelKeeper);
			int[] plainPixelKeeperInt = convertToIntArray(plainPixelKeeper);
			
			BufferedImage plainImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = plainImage.getRaster();
			rast.setDataElements(0, 0, w, h, plainPixelKeeperInt);
			ImageIO.write(plainImage,"jpg",new File(CURRENT_DIRECTORY + File.separator + "src" + File.separator + "Image1AESPlain.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
	}

	public byte[] encrypt2(int w, int h, byte[] plainPixelKeeper) throws Exception
	{
		byte[] cipherPixelKeeper = aesCipherE.doFinal(plainPixelKeeper);
		int[] cipherPixelKeeperInt = convertToIntArray(cipherPixelKeeper);

		try
		{
			BufferedImage cipherImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = cipherImage.getRaster();
			rast.setDataElements(0, 0, w, h, cipherPixelKeeperInt);
			ImageIO.write(cipherImage,"jpg",new File(CURRENT_DIRECTORY + File.separator + "src" + File.separator + "Image2AESCipher.jpg"));
		}
		catch(Exception e){e.printStackTrace();}
		
		return cipherPixelKeeper;
	}
	
	public void decrypt2(int w, int h, byte[] cipherPixelKeeper) throws Exception
	{
		try
		{
			byte[] plainPixelKeeper = aesCipherD.doFinal(cipherPixelKeeper);
			
			int[] plainPixelKeeperInt = convertToIntArray(plainPixelKeeper);
			BufferedImage plainImage = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
			WritableRaster rast = plainImage.getRaster();
			rast.setDataElements(0, 0, w, h, plainPixelKeeperInt);
			ImageIO.write(plainImage,"jpg",new File(CURRENT_DIRECTORY + File.separator + "src" + File.separator + "Image2AESPlain.jpg"));
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
	public static void main(String[] args) throws Exception {

		System.out.println("this is my module for AES");
	}
}
