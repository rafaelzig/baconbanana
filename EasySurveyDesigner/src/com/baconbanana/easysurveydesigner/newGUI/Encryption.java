package com.baconbanana.easysurveydesigner.newGUI;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Encryption {
	byte[] encryptedData;
	  byte[] decryptedData;
	  
	  public Encryption(){
	  byte[] secret = null;
		
	  try {
			secret = Hex.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff".toCharArray());
		} catch (DecoderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	    SecretKeySpec secretKey = new SecretKeySpec(secret, "AES");
	
	   //decryptMsg(encryptMsg(message, secretKey), secretKey));
		
	  }
	  
	  public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
			
		    Cipher cipher = null;
		    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, secret);
		    byte[] encryptedText = cipher.doFinal(message.getBytes("UTF-8"));
		    
		    return encryptedText;
		}

		public static String decryptMsg(byte[] encryptedText, SecretKey secret) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

		    Cipher cipher = null;
		    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		   cipher.init(Cipher.DECRYPT_MODE, secret);
		    String decryptString = new String(cipher.doFinal(encryptedText), "UTF-8");
		    
		    return decryptString;
		}
		


}
