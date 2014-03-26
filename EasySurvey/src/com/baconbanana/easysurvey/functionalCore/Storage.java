/**
 * 
 */
package com.baconbanana.easysurvey.functionalCore;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;

import com.baconbanana.easysurveyfunctions.parsing.Operations;





/**
 * Auxiliary Class used to perform Android Storage operations like write and
 * read.
 * 
 * @author Rafael da Silva Costa & Team.
 * 
 */
public class Storage
{
	/**
	 * String object representing the filename used to save the json files.
	 */
	public static final String FILENAME = "Survey.json";
	
	/**
	 * Writes the specified string to the device's internal storage.
	 * 
	 * @param context
	 *            The activity's context.
	 * @param input
	 *            String object to be written to the internal storage.
	 * @throws FileNotFoundException
	 *             Thrown when a file specified by a program cannot be found.
	 * @throws IOException
	 *             Signals a general, I/O-related error.
	 */
	public static void writeToInternal(Context context, String input)
			throws FileNotFoundException, IOException
	{
		Operations.writeFile(context.openFileOutput(FILENAME,
				Context.MODE_PRIVATE), input);
	}

	/**
	 * Reads from the a file in the device's internal storage with the specified
	 * filename.
	 * 
	 * @param context
	 *            The activity's context.
	 * @param filename
	 *            String object representing the filename.
	 * @throws FileNotFoundException
	 *             Thrown when a file specified by a program cannot be found.
	 * @throws IOException
	 *             Signals a general, I/O-related error.
	 */
	public static String readFromInternal(Context context, String filename)
			throws FileNotFoundException, IOException
	{
		return Operations.readFile(context.openFileInput(FILENAME));
	}
}
