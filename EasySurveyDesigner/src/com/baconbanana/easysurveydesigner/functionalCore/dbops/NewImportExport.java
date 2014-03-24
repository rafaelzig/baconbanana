package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

import com.baconbanana.easysurveydesigner.functionalCore.parsing.Operations;

public class NewImportExport
{
	public static void startImport() throws IOException
	{
		File dbFile = new File(DBController.WORKING_DIRECTORY+DBController.DB_NAME);
		dbFile.delete();
		File sqlFile = importSql(getFile("Import").getName());
		Runtime.getRuntime().exec("cmd /c start " + getBatFilepath(sqlFile));
	}

	public static void startExport() throws IOException
	{
		File sqlFile = exportSql(getFile("Export").getName());
		Runtime.getRuntime().exec("cmd /c start " + getBatFilepath(sqlFile));
	}

	private static File exportSql(String fileName) throws IOException
	{
		File sqlFile = new File(DBController.WORKING_DIRECTORY + DBController.SEPARATOR + "sql.txt");
		String[] commands = new String[3];

		commands[0] = ".open easysurvey.db";
		commands[1] = ".output " + fileName; // This file should be chosen by the user via the filechooser
		commands[2] = ".dump";

		Operations.writeFile(sqlFile.getAbsolutePath(), commands);
		System.out.println(commands[0] + "\n" + commands[1] + "\n"
				+ commands[2]);
		sqlFile.deleteOnExit();

		return sqlFile;
	}

	private static File importSql(String fileName) throws IOException
	{
		File sqlFile = new File(DBController.WORKING_DIRECTORY + DBController.SEPARATOR + "sql.txt");
		String[] commands = new String[2];
		commands[0] = ".open " + DBController.DB_NAME;
		commands[1] = ".read " + fileName;
		commands[1] = commands[1].replace(DBController.SEPARATOR, "/");

		Operations.writeFile(sqlFile.getAbsolutePath(), commands);

		sqlFile.deleteOnExit();
		return sqlFile;
	}

	private static String getBatFilepath(File sqlFile) throws IOException
	{
		File batFile = new File(DBController.WORKING_DIRECTORY + DBController.SEPARATOR + "export.bat");
		String[] commands = new String[3];
		commands[0] = "cd " + DBController.WORKING_DIRECTORY;
		commands[1] = "sqlite3.exe " + DBController.DB_NAME + " < "
				+ sqlFile.getName();
		commands[2] = "exit";

		Operations.writeFile(batFile.getAbsolutePath(), commands);
		batFile.deleteOnExit();
		return batFile.getAbsolutePath();
	}

	// private void delete()
	// {
	// batFile.delete();
	// sqlFile.delete();
	// }

	private static File getFile(String title)
	{
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Sql", "sql");
		
		JFileChooser fileCHooCHoo = new JFileChooser(DBController.WORKING_DIRECTORY);
		fileCHooCHoo.setFileFilter(filter);

		if (title.equals("Export")){
		fileCHooCHoo.setFileView(new FileView() {
		    @Override
		    public Boolean isTraversable(File f) {
		         return DBController.WORKING_DIRECTORY.equals(f);
		    }
		});
		}
		fileCHooCHoo.showDialog(fileCHooCHoo, title);

		String newName = fileCHooCHoo.getSelectedFile().getAbsolutePath();
		System.out.println(newName);
		if (newName.contains("."))
		{
			int cut = newName.indexOf('.');
			newName = newName.substring(0, cut);
			newName = newName + ".sql";
		}
		else
			newName = newName + ".sql";

		return new File(newName);
	}
	

	
	/*
	 * private String filterExtention(File myFile) { String choosenFile;
	 * choosenFile = myFile.getName(); if (!choosenFile.contains(".")) return
	 * choosenFile+".sql"; else if (choosenFile.contains(".")) { int cut =
	 * choosenFile.indexOf('.'); choosenFile = choosenFile.substring(0, cut);
	 * return choosenFile+".sql"; } else if (choosenFile.endsWith(".sql"))return
	 * choosenFile; } else return "";
	 */

	// private synchronized void moveFile()
	// {
	// File annoyingFile = new File(System.getenv("USERPROFILE")
	// + "\\Documents\\SQLite\\" + getRenamedFile().getName());
	// try
	// {
	// Files.copy(Paths.get(annoyingFile.getAbsolutePath()),
	// Paths.get(getRenamedFile().getAbsolutePath()),
	// StandardCopyOption.REPLACE_EXISTING);
	// }
	// catch (IOException e)
	// {
	// TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/*
	 * public static void main(String[] args) { final ImportExport my =new
	 * ImportExport(); my.startExport(); Thread first = new Thread(new
	 * Runnable() {
	 * 
	 * @Override public void run() { my.startExport();
	 * 
	 * } });
	 * 
	 * Thread second = new Thread(new Runnable() {
	 * 
	 * @Override public void run() { my.moveFile(); } }); try { first.run();
	 * first.join(); while(first.isAlive())second.wait();
	 * 
	 * second.start();
	 * 
	 * } catch (InterruptedException e) { TODO Auto-generated catch block
	 * e.printStackTrace(); } String print = my.getRenamedFile().getName();
	 * System.out.println(print+"name!!!!");
	 * 
	 * System.out.println(my.getRenamedFile());
	 * 
	 * 
	 * 
	 * }
	 */
}
