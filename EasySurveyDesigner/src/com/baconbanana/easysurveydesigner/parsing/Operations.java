/**
 * 
 */
package com.baconbanana.easysurveydesigner.parsing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Zigoto
 * 
 */
public class Operations
{
	/** Read the given binary file, and return its contents as a byte array. */
	public static byte[] readFile(String fileName) throws IOException
	{
		System.out.println("Reading in binary file named : " + fileName);
		File file = new File(fileName);
		System.out.println("File size: " + file.length());

		byte[] result = new byte[(int) file.length()];
		int totalBytesRead = 0;
		InputStream input = new BufferedInputStream(new FileInputStream(file));
		
		while (totalBytesRead < result.length)
		{
			int bytesRemaining = result.length - totalBytesRead;
			int bytesRead = input.read(result, totalBytesRead, bytesRemaining);

			if (bytesRead > 0)
				totalBytesRead = totalBytesRead + bytesRead;
		}

		/*
		 * the above style is a bit tricky: it places bytes into the 'result'
		 * array; 'result' is an output parameter; the while loop usually has a
		 * single iteration only.
		 */
		System.out.println("Num bytes read: " + totalBytesRead);
		input.close();
		return result;
	}

	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 * 
	 * @throws IOException
	 */
	public static void writeFile(byte[] aInput, String aOutputFileName)
			throws IOException
	{
		System.out.println("Writing binary file...");
		OutputStream output = new BufferedOutputStream(new FileOutputStream(
				aOutputFileName));
		output.write(aInput);
		output.close();
	}
}