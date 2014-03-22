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
 * @author Almira & Team
 * 
 */
public class EncryptionJ
{

	public static byte[] secret = null;
	public static SecretKeySpec secretKey = null;

	public static void setKeys() throws DecoderException
	{
		secret = Hex
				.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff".toCharArray());
		secretKey = new SecretKeySpec(secret, "AES");
	}

	/**
	 * This method, using another method in Encryption class, encrypts data.
	 * Then converts it bytes string. Then returnes it.
	 * 
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
	public static String encryptMsg(String message) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, DecoderException
	{
		byte[] encryptedText;
		setKeys();
		Cipher cipher = null;
		String encrypted = "";

		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		encryptedText = cipher.doFinal(message.getBytes("UTF-8"));

		encrypted = Base64.encodeToString(encryptedText, Base64.DEFAULT);

		return encrypted;
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
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, UnsupportedEncodingException,
			IllegalBlockSizeException, BadPaddingException, DecoderException
	{
		setKeys();
		Cipher cipher = null;
		String decrypt = "";

		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] data = Base64.decode(message, Base64.DEFAULT);
		;

		decrypt = new String(cipher.doFinal(data), "UTF-8");

		return decrypt;
	}

}
