package com.baconbanana.easysurveydesigner.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DataTest {
	   static ArrayList<String>templateList;
	   static String loggin= new String("user=root");
	   static String password = new String("password=admin");
        
	  
	   public DataTest()
	   {
		   password = LoginPage.getPassword();
		  /* loggin=new String("user="+userName);
		   this.password=new String("password="+password);
		   try {
	            // The newInstance() call is a work around for some
	            // broken Java implementations

	            Class.forName("com.mysql.jdbc.Driver").newInstance();
	        } catch (Exception ex) {
	            // handle the error
	        }*/
	   }
	   public static Statement createConnection() throws SQLException
	   {
		   Statement myState = null;
		   Connection conn = null;
			conn =DriverManager.getConnection("jdbc:mysql://localhost/easysurvay?" +
			                                       LoginPage.getUserName()+"&"+LoginPage.getPassword());
			Statement st = conn.createStatement();
			myState = st;
		return myState;

	   }
    public ArrayList<String>getList()
    {
    	return templateList;
    }
    
    public static void FillListOfTemplates()throws SQLException
    {
    	ArrayList<String>list = new ArrayList<String>();
        
    
  
     
            
            ResultSet res = createConnection().executeQuery("SELECT * FROM  template");
            while (res.next()) {
            String name= res.getString("name");
            list.add(name);
            }
            templateList=list;
            
        EasySurveyFrame.myModel1.clear();
        for (String s : list) {
			EasySurveyFrame.myModel1.addElement(s);
		}
		
		EasySurveyFrame.List1.setModel(EasySurveyFrame.myModel1);
    }
    
    public static void SaveTemplate(String nameOfTmeplate) throws SQLException
    {
    	String templateName = new String(); 
    	templateName = "INSERT INTO template  VALUES (" + "'" + nameOfTmeplate + "', '553')";
    	
    	
    	int val = createConnection().executeUpdate(templateName);
        if (val==1)   System.out.print("Successfully inserted value");
    }
    
    public static void DeleteTemplate(String giveTemplateName)  throws SQLException
    {
    	String templateName = new String();
    	templateName = "DELETE FROM `easysurvay`.`template` WHERE `name`='" + giveTemplateName +"'";
    	
	int val = createConnection().executeUpdate(templateName);
    if (val==1)   System.out.print("Successfully deleted value");
    }
   
}
