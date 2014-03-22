package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ImportExport  {
	String batFilePath = System.getenv("USERPROFILE")+"\\Documents"+"\\SQLite\\"+"export.bat";
	String sqlFilePath = System.getenv("USERPROFILE")+"\\Documents"+"\\SQLite\\"+"CSF.txt";
	File sqlFile = new File(sqlFilePath);
	File batFile = new File(batFilePath);
	JFileChooser fileCHooCHoo = new JFileChooser(System.getenv("USERPROFILE")+"\\Documents"+"\\SQLite\\");
	File renamedFile;
  public ImportExport()
  {
	 
	 
}
  public void writeBat()
  {
	  String command1 = "cd %USERPROFILE%\\Documents\\SQLite";
	  String command2 = "sqlite3.exe easysurvey.db < CSF.txt";
	  String command3 = "exit";

		try {
			batFile.createNewFile();
			PrintWriter print = new PrintWriter(batFile);
			print.println(command1);
			print.println(command2);
			print.println(command3);
			print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  public void exportSQL()
  {
	  String command1 = ".open easysurvey.db";
	  String command2=".output "+getRenamedFile().getName();
	  String command3=".dump";
	  try {
		sqlFile.createNewFile();
		PrintWriter print = new PrintWriter(sqlFile);
		print.println(command1);
		print.println(command2);
		print.println(command3);
		print.close();
		System.out.println(command1+"\n"+command2+"\n"+command3);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	  
	  
  }
  
  public synchronized static void startExport()
  {
	 
	  chooseFile("Export");
	 writeBat();
	 exportSQL();
	try {
		Runtime.getRuntime().exec("cmd /c start "+batFilePath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	moveFile();
  }
  public void startImport()
  {
	  chooseFile("Import");
	  writeBat();
	  importSQL();
	  try {
			Runtime.getRuntime().exec("cmd /c start "+batFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  public void importSQL()
  {
	  String command1 = ".open easysurvey.db";
	  String command2= ".read "+ getRenamedFile().getAbsolutePath() ;
	  command2 = command2.replace("\\", "/");
	  try {
		sqlFile.createNewFile();
		PrintWriter print = new PrintWriter(sqlFile);
		print.println(command1);
		print.println(command2);
		print.close();
		System.out.println(command1+"\n"+command2+"\n");
	} catch (IOException e) {
		
		e.printStackTrace();
	}
  }
  
  public void delete()
  {
	  batFile.delete();
	  sqlFile.delete();
  }
  public void  chooseFile(String whaat)
  { 
	  FileNameExtensionFilter filter = new FileNameExtensionFilter("sql filter", "sql");
	  fileCHooCHoo.setFileFilter(filter);
      fileCHooCHoo.showDialog(fileCHooCHoo,whaat);
      String newName = fileCHooCHoo.getSelectedFile().getAbsolutePath();
      System.out.println(newName);
      if (newName.contains("."))  
      {
    	  int cut = newName.indexOf('.');
    	  newName = newName.substring(0, cut);
    	  newName= newName+".sql";
      }
      else  newName= newName+".sql";
     renamedFile = new File(newName);
  }
  private File getRenamedFile()
  {
	  
	return  renamedFile;
  }
  /*private String filterExtention(File myFile)
  {
	  String choosenFile;
	  choosenFile = myFile.getName();
      if (!choosenFile.contains(".")) return choosenFile+".sql";
      else if (choosenFile.contains("."))
      {
    	  int cut = choosenFile.indexOf('.');
    	  choosenFile = choosenFile.substring(0, cut);
      return choosenFile+".sql";
      }
      else if (choosenFile.endsWith(".sql"))return choosenFile;
  }
      else return "";*/
  private synchronized void moveFile()
  {
	  File annoyingFile = new File(System.getenv("USERPROFILE")+"\\Documents\\SQLite\\"+getRenamedFile().getName());
	  try {
		Files.copy(Paths.get(annoyingFile.getAbsolutePath()),Paths.get(getRenamedFile().getAbsolutePath()),StandardCopyOption.REPLACE_EXISTING);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 
  /*public static void main(String[] args)
  {
	 final ImportExport my =new ImportExport(); 
	  //my.startExport();
	 Thread first = new Thread(new Runnable() {
		
		@Override
		public void run() {
			my.startExport();
			
		}
	});
	 
	 Thread second = new Thread(new Runnable() {
		
		@Override
		public void run() {
			my.moveFile();
		}
	});
	try {
		first.run();
		first.join();
		while(first.isAlive())second.wait();

        second.start();
		 
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  String print = my.getRenamedFile().getName();
	  System.out.println(print+"name!!!!");

	  System.out.println(my.getRenamedFile());
	 
	
      
  }*/
}
