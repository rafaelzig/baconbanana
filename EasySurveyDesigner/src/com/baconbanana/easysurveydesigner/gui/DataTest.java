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
        
    public ArrayList<String>getList()
    {
    	return templateList;
    }
    
    public static void FillListOfTemplates()
    {
    	ArrayList<String>list = new ArrayList<String>();
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
    
        Connection conn = null;
        try {
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/easysurvay?" +
                                           "user=root&password=1111");

            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  template");
            while (res.next()) {
            String name= res.getString("name");
            list.add(name);
            }
            templateList=list;
            /*
            int val = st.executeUpdate("INSERT INTO template  VALUES ('Matt3', '553')");
             if (val==1)   System.out.print("Successfully inserted value");
            conn.close();
             */
            } catch (Exception e) {
            e.printStackTrace();
            }
        EasySurveyFrame.myModel1.clear();
        for (String s : list) {
			EasySurveyFrame.myModel1.addElement(s);
		}
		
		EasySurveyFrame.List1.setModel(EasySurveyFrame.myModel1);
    }
    
    public static void SaveTemplate() throws SQLException
    {
    	String templateName = new String(); 
    	templateName = "INSERT INTO template  VALUES (" + "'" + AddNewTemplate.nameOfTemplate.getText() + "', '553')";
    	Connection conn = null;
    	
            conn =
               DriverManager.getConnection("jdbc:mysql://localhost/easysurvay?" +
                                           "user=root&password=1111");

            Statement st = conn.createStatement();
    	
    	int val = st.executeUpdate(templateName);
        if (val==1)   System.out.print("Successfully inserted value");
       conn.close();
    }
    
    public static void DeleteTemplate()  throws SQLException
    {
    	String templateName = new String();
    	templateName = EasySurveyFrame.List1.getSelectedValue();
    	templateName = "DELETE FROM `easysurvay`.`template` WHERE `name`='" + templateName +"'";
    	Connection conn = null;
    	
        conn =
           DriverManager.getConnection("jdbc:mysql://localhost/easysurvay?" +
                                       "user=root&password=1111");

        Statement st = conn.createStatement();
	
	int val = st.executeUpdate(templateName);
    if (val==1)   System.out.print("Successfully deleted value");
   conn.close();
    }
   

}