package com.baconbanana.easysurveydesigner.functionalCore.coms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;
import com.baconbanana.easysurveydesigner.newGUI.Encryption;
import com.baconbanana.easysurveydesigner.newGUI.SendSurvey;

public class DataSender extends Thread {
	String name;
	String date;

	public DataSender(String name, String date) {
		this.name = name;
		this.date = date;
	}

	public void run() {
		PrintStream output = null;
		try {
			System.out.println(name+"*"+date);

			output = new PrintStream(SendSurvey.getClientSocket().getOutputStream());
			Operations.readFile("Survey.json");
			
			try (BufferedReader br = new BufferedReader(new FileReader("Survey.json"))) {

				
			  
				  String sCurrentLine = null;
				  String s=encrypt(name+"     "+ encrypt(date));
				  System.out.println(s);// <--------
				  output.println(s);
				  output.flush();
				while ((sCurrentLine = br.readLine()) != null) {
					String s2=encrypt(sCurrentLine);
					
					System.out.println(s2);
					output.println(s2);
					output.flush();
					System.out.println(sCurrentLine);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidParameterSpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	public String encrypt(String str) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidParameterSpecException {

		//-----------------------
		  byte[] secret = null;
		 SecretKeySpec secretKey = null;
		 
		  try {
			  secret = Hex.decodeHex("25d6c7fe35b9979a161f2136cd13b0ff".toCharArray());
			  secretKey = new SecretKeySpec(secret, "AES");
			} catch (DecoderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		  //------------------------

		try {
            
            byte[] enc = Encryption.encryptMsg(str, secretKey);
           
            
            return  DatatypeConverter.printBase64Binary(enc);
            
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}
