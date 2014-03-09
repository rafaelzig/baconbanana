/**
 * 
 */
package com.baconbanana.easysurvey.functionalCore;

import java.io.File;

import android.os.Environment;

/**
 * @author Rafael da Silva Costa & Team.
 * 
 */
public class Storage
{
	/**
	 * Constant representing the directory where the json file will be saved.
	 */
	public static final File ROOT_DIRECTORY = new File(
			Environment.getExternalStorageDirectory() + "/EasySurvey/");

	/**
	 * Creates the directory named by this file, creating missing parent
	 * directories if necessary.
	 * 
	 * @return true if the directory was created, false on failure or if the
	 *         directory already existed.
	 */
	public static boolean createRootDirectory()
	{
		return ROOT_DIRECTORY.mkdirs();
	}

	/**
	 * Checks if external storage is available for read and write.
	 * 
	 * @return true if the external storage is available for read and write,
	 *         false otherwise.
	 */
	public static boolean isExternalStorageWritable()
	{
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * Checks if external storage is available to at least read.
	 * 
	 * @return true if the external storage is available for at least read,
	 *         false otherwise.
	 */
	public static boolean isExternalStorageReadable()
	{
		String state = Environment.getExternalStorageState();
		return (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY
				.equals(state));
	}
}
