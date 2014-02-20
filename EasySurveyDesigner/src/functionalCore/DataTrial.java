package functionalCore;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class DataTrial {
    public static void main(String[] args) {
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
           DriverManager.getConnection("jdbc:mysql://localhost/ololo?" +
                                       "user=root&password=admin");

        System.out.print("YEA");
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery("SELECT * FROM  table1");
        while (res.next()) {
        String name= res.getString("name");
        String age = res.getString("age");
        System.out.println(name + "\t" + age);
        }
        int val = st.executeUpdate("INSERT INTO `ololo`.`table1` (`name`, `age`) VALUES ('lool2', '55')");
         if (val==1)   System.out.print("Successfully inserted value");
        conn.close();
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

}
