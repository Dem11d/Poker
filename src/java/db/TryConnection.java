/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;
import java.util.logging.Level;
import javax.naming.InitialContext;

/**
 *
 * @author Павлюки
 */
public class TryConnection {
    public static boolean check(){
       try{
    InitialContext ic = new InitialContext();
    DataSource ds = (DataSource) ic.lookup("jdbc/users");
    Connection conn = ds.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users.users");
      
        while (rs.next()){
            rs.getString("UserName");
        }
       }
       catch(Exception ex){
           ex.printStackTrace();
       }
        return false;
       
    }
}
