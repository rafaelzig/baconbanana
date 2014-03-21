package com.baconbanana.easysurveydesigner.installer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class MySQLInstaller {
	public static void main(String[] args){
		String osName = System.getProperty("os.name");
		System.out.println(osName);
		String workingDir = "";
		File scrFolder = null;
		if(osName.contains("Mac")){
			workingDir = System.getProperty("user.dir");
			scrFolder = new File(workingDir + "/DatabaseMac");
		}else if(osName.contains("Windows")){
			workingDir = System.getProperty("user.dir");
			scrFolder = new File(workingDir + "/Database");
		}
		//check that system is 64bit or not
		File destFolder = null;
		//get location of my documents
		String docLoc = "";
		if(osName.contains("Mac")){
			docLoc = System.getenv("HOME") + "/Documents";
		}else if(osName.contains("Windows")){
			docLoc = System.getenv("USERPROFILE") + "/Documents";
		}
		try{
			destFolder = new File(docLoc);
		}catch(Exception e){
			e.printStackTrace();
		}
		MySQLInstaller sqlLite = new MySQLInstaller();
		sqlLite.copyFolder(scrFolder, destFolder);
		//sqlLite.installSQLLite(docLoc);
	}
	
	public void copyFolder(File scr, File dest){
		//Create file directories
		
		if(scr.isDirectory()){
			if(!dest.exists()){
				dest.mkdirs();
			}
			//get child dir/files names
			String files[] = scr.list();
			//iterrate though folder structure copying and creating
			for(String file : files){
				File scrFile = new File(scr, file);
				File destFile = new File(dest, file);
				copyFolder(scrFile, destFile);
			}
		}else{
			//create files in directories
			InputStream in = null;
			OutputStream out = null;
			//set up input and outputs to copy files
			try{
			in = new FileInputStream(scr);
			out = new FileOutputStream(dest);
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
			//create buffer so that any type of file can be copied
			byte[] buffer = new byte[1024];
			int length;
			//copy file across
			try {
				while((length = in.read(buffer)) > 0){
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public void installSQLLite(String loc){
		//Get the command propt
		Runtime rt = Runtime.getRuntime();
		Process p = null;
		String comand = loc + "/SQLite/sqlite3";
		//Can also create database by apending it to the end of staement.
		try{
			System.out.println(comand);
			p = rt.exec(comand);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
