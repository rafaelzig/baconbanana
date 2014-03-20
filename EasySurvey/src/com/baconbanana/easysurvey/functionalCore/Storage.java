/**
 * 
 */
package com.baconbanana.easysurvey.functionalCore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.content.Context;
import android.os.Environment;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

/**
 * Auxiliary Class used to perform Android Storage operations like write and
 * read.
 * 
 * @author Rafael da Silva Costa & Team.
 * 
 */
public class Storage
{
	// /**
	// * Constant representing the directory where the json file will be saved.
	// */
	// public static final File ROOT_DIRECTORY = new File(
	// Environment.getExternalStorageDirectory() + "/EasySurvey/");
	// /**
	// * Creates the directory named by this file, creating missing parent
	// * directories if necessary.
	// *
	// * @return true if the directory was created, false on failure or if the
	// * directory already existed.
	// */
	// public static boolean createRootDirectory()
	// {
	// return ROOT_DIRECTORY.mkdirs();
	// }
	//
	// /**
	// * Checks if external storage is available for read and write.
	// *
	// * @return true if the external storage is available for read and write,
	// * false otherwise.
	// */
	//
	// public static boolean isExternalStorageWritable()
	// {
	// String state = Environment.getExternalStorageState();
	// return Environment.MEDIA_MOUNTED.equals(state);
	// }
	//
	// /**
	// * Checks if external storage is available to at least read.
	// *
	// * @return true if the external storage is available for at least read,
	// * false otherwise.
	// */
	// public static boolean isExternalStorageReadable()
	// {
	// String state = Environment.getExternalStorageState();
	// return (Environment.MEDIA_MOUNTED.equals(state) ||
	// Environment.MEDIA_MOUNTED_READ_ONLY
	// .equals(state));
	// }

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
		Operations.writeFile(input, context.openFileOutput(Operations.FILENAME,
				Context.MODE_PRIVATE));
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
		return Operations.readFile(context.openFileInput(Operations.FILENAME));
	}
}