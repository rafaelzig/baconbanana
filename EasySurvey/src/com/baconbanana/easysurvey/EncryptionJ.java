package com.baconbanana.easysurvey;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import android.util.Base64;

/**
 * 
 * @author Team 
 *
 */
public class EncryptionJ
{

	public static byte[] secret = null;
	public static SecretKeySpec secretKey = null;

	public static void setKeys() {
		try {
			secret = Hex.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff".toCharArray());
			secretKey = new SecretKeySpec(secret, "AES");
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * This method, using another method in Encryption class, encrypts data.
	 * Then converts it bytes string. Then returns it.
	 * @param message
	 * @return encrypted
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidParameterSpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws DecoderException
	 */
	public static String encryptMsg(String message) 
	{
		byte[] encryptedText;
		setKeys();
		Cipher cipher = null;
		String encoded="";
		
		try {
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		
			cipher.init(Cipher.ENCRYPT_MODE, secretKey );
			
			encryptedText = cipher.doFinal(message.getBytes("UTF-8"));
			encoded = Base64.encodeToString(encryptedText, Base64.DEFAULT);
		
			}catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		return encoded;
	}

	/**
	 * This method converts string into bytes then using method in Encryption
	 * class decrypts data. Then it returns string.
	 * 
	 * @param message
	 * @return decrypted
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidParameterSpecException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 * @throws DecoderException
	 */
	public static String decryptMsg(String message)
	{
		setKeys();
		Cipher cipher = null;
		String decrypt = "";
try{
		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
	byte[] decode = Base64.decode(message, Base64.DEFAULT);;
		decrypt = new String(cipher.doFinal(decode),"UTF-8");

		
}
	catch (IllegalBlockSizeException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (BadPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
return decrypt;

	
	}

}
