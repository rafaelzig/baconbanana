package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

import com.baconbanana.easysurveyfunctions.parsing.Operations;

/**
 * 
 * Importing and exporting database in .sql files
 * 
 */
public class ImportExport {
	/**
	 * Starts import by executing .bat file and prompts FileChooser window
	 * 
	 * @throws IOException
	 */
	public static void startImport() throws IOException {
		File dbFile = new File(DBController.WORKING_DIRECTORY
				+ DBController.DB_NAME);
		dbFile.delete();

		File f = getFile("Import");
		File sqlFile = null;

		if (f != null) {
			sqlFile = importSql(f.getName());
			Runtime.getRuntime()
					.exec("cmd /c start " + getBatFilepath(sqlFile));
		}

	}

	/**
	 * Starts export into choosen file by executing .bat
	 * 
	 * @throws IOException
	 */
	public static void startExport() throws IOException {
		File f = getFile("Export");
		File sqlFile = null;

		if (f != null) {
			sqlFile = exportSql(f.getName());
			Runtime.getRuntime()
					.exec("cmd /c start " + getBatFilepath(sqlFile));
		}
	}

	/**
	 * Creates .txt file for sqlite3 to execute export
	 * 
	 * @param fileName
	 *            name of the file database would be stored in
	 * @return .txt file for sqlite3 to execute export
	 * @throws IOException
	 */
	private static File exportSql(String fileName) throws IOException {
		File sqlFile = new File(DBController.WORKING_DIRECTORY
				+ DBController.SEPARATOR + "sql.txt");
		String[] commands = new String[3];

		commands[0] = ".open easysurvey.db";
		commands[1] = ".output " + fileName; // This file should be chosen by
												// the user via the filechooser
		commands[2] = ".dump";

		Operations.writeFile(sqlFile.getAbsolutePath(), commands);
		System.out.println(commands[0] + "\n" + commands[1] + "\n"
				+ commands[2]);
		sqlFile.deleteOnExit();

		return sqlFile;
	}

	/**
	 * Creates .txt file for sqlite3 to execute import
	 * 
	 * @param fileName
	 *            name of the file database would be recovered from
	 * @return .txt file for sqlite3 to execute import
	 * @throws IOException
	 */
	private static File importSql(String fileName) throws IOException {
		File sqlFile = new File(DBController.WORKING_DIRECTORY
				+ DBController.SEPARATOR + "sql.txt");
		String[] commands = new String[2];
		commands[0] = ".open " + DBController.DB_NAME;
		commands[1] = ".read " + fileName;
		commands[1] = commands[1].replace(DBController.SEPARATOR, "/");

		Operations.writeFile(sqlFile.getAbsolutePath(), commands);

		sqlFile.deleteOnExit();
		return sqlFile;
	}

	/**
	 * 
	 * @param sqlFile
	 *            .txt file for sqlite3 connacted to bat file
	 * @return windows path for executable bat file
	 * @throws IOException
	 */
	private static String getBatFilepath(File sqlFile) throws IOException {
		File batFile = new File(DBController.WORKING_DIRECTORY
				+ DBController.SEPARATOR + "export.bat");
		String[] commands = new String[3];
		commands[0] = "cd " + DBController.WORKING_DIRECTORY;
		commands[1] = "sqlite3.exe " + DBController.DB_NAME + " < "
				+ sqlFile.getName();
		commands[2] = "exit";

		Operations.writeFile(batFile.getAbsolutePath(), commands);
		batFile.deleteOnExit();
		return batFile.getAbsolutePath();
	}

	/**
	 * Ensuring right extention of file database is stored in and launching
	 * fileChooser
	 * 
	 * @param title
	 *            main button to be named "Export" or "Import"
	 * @return file with right name
	 */
	private static File getFile(String title) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Sql",
				"sql");

		JFileChooser fileCHooCHoo = new JFileChooser(
				DBController.WORKING_DIRECTORY);
		fileCHooCHoo.setFileFilter(filter);

		if (title.equals("Export")) {
			fileCHooCHoo.setFileView(new FileView() {
				@Override
				public Boolean isTraversable(File f) {
					return DBController.WORKING_DIRECTORY.equals(f);
				}
			});
		}
		fileCHooCHoo.showDialog(fileCHooCHoo, title);

		File selectedFile = fileCHooCHoo.getSelectedFile();

		String newName = null;

		if (selectedFile != null) {
			newName = selectedFile.getAbsolutePath();

			System.out.println(newName);
			if (newName.contains(".")) {
				int cut = newName.indexOf('.');
				newName = newName.substring(0, cut);
				newName = newName + ".sql";
			} else
				newName = newName + ".sql";
			return new File(newName);
		}
		return null;
	}

}
