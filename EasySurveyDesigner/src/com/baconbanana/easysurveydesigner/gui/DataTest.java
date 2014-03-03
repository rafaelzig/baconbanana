package com.baconbanana.easysurveydesigner.gui;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DataTest {
	   static ArrayList<String>templateList;
    public static void main(String[] args) {
    	ArrayList<String>list = new ArrayList<String>();
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

          //  Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
    
    Connection conn = null;
    try {
        conn =
           DriverManager.getConnection("jdbc:mysql://localhost/easysurvay?" +
                                       "user=root&password=1111");

        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM  templates");
        while (res.next()) {
        String name= res.getString("id");
        String age = res.getString("title");
        System.out.println(name + "\t" + age);
        list.add(name);
        }
        templateList=list;
        System.out.println(list.toString());
        /*
        int val = st.executeUpdate("INSERT INTO template  VALUES ('Matt', '55')");
         if (val==1)   System.out.print("Successfully inserted value");
        conn.close();*/
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
    public ArrayList<String>getList()
    {
    	return templateList;
    }

}