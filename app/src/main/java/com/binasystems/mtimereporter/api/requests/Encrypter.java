package com.binasystems.mtimereporter.api.requests;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
//import javax.xml.bind.DatatypeConverter;


/**
 * @author      Serghei Mazur <mazur.serghei @ gmail.com>
 * @version     1.0
 * @since       2013-03-23 
 */

public class Encrypter {
	/**
	 * 16 bite key "1234567891123456".
	 */
	static private String key = "1234567891123456";

	/**
	 * 
	 * This method for encryption String (Encryption/Base64).
	 * @param  inputText for encryption<br>Sample: "FreeTrial"
	 * @return String encryption<br>Sample: "97YswmhiD6plWcwHiJ6aPw=="
	 */
	public static String encrypt(String inputText) {
		byte[] keyBytes = null;
		try {
			keyBytes = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] plainBytes = null;
		try {
			plainBytes = inputText.getBytes("UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		byte[] iv = new byte[16];
		try {
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		String decodString = "";
		try {
			byte[] encrypted = cipher.doFinal(plainBytes);
//			decodString = new String(DatatypeConverter.printBase64Binary(encrypted));
			decodString=new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decodString;
	}
	
	
	/**
	 * This method for decrypt (Base64/Decryption).
	 *
	 * @param  inputText for decrypt<br>Sample: "97YswmhiD6plWcwHiJ6aPw=="
	 * @return String decrypt  <br>Sample: "FreeTrial"
	 */
	public static String decrypt(String inputText) {
		byte[] keyBytes = null;
		try {
			keyBytes = key.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

//		byte [] plainBytes = DatatypeConverter.parseBase64Binary(inputText);
		byte[] inputText_byte=null;
		try {
			inputText_byte = inputText.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		byte [] plainBytes=Base64.decode(inputText_byte, Base64.DEFAULT);
		
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		byte[] iv = new byte[16];
		try {
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		String decodString = "";
		try {
			byte[] encrypted = cipher.doFinal(plainBytes);
			decodString = new String(encrypted);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return decodString;
	}
}
