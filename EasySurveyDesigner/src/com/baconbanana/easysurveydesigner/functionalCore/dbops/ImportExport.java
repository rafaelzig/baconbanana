package com.baconbanana.easysurveydesigner.functionalCore.dbops;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class ImportExport {
	String batFilePath = System.getenv("USERPROFILE")+"\\Documents"+"\\SQLite\\"+"export.bat";
	String sqlFilePath = System.getenv("USERPROFILE")+"\\Documents"+"\\SQLite\\"+"CSF.txt";
	File sqlFile = new File(sqlFilePath);
	File batFile = new File(batFilePath);
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
  public void writeSQLCom(String name)
  {
	  String command1 = ".open easysurvey.db";
	  String command2=".output "+name.trim()+".txt";
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
  public void startExport(String name)
  {
	 
	 writeBat();
	 writeSQLCom(name);
	try {
		Runtime.getRuntime().exec("cmd /c start "+batFilePath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	
  }
  public void delete()
  {
	  batFile.delete();
	  sqlFile.delete();
  }
  public void startImport(String name)
  {
	  
  }
  public static void main(String[] args)
  {
	  ImportExport my =new ImportExport();
	  my.startExport("FunnyDB");
	 
  }
}
