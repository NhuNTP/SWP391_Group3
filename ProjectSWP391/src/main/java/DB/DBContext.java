/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class DBContext {

    /***
     * Tao doi tuong ket noi database tra ve doi tuong connection;
     */
    public static Connection conn = null;

    public static Connection getConnection() {
        try {
            //Buoc 1: Khai bao driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //Buoc 2: Khai bao thong tin server
            String url = "jdbc:sqlserver://localhost:1433;databaseName=db1;user=sa;password=123456;encrypt=true;trustServerCertificate=true;";
            // Buoc 3: tao doi tuong ket noi
            conn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }   
    public static void closeConnection(){
        try {
            if(!conn.isClosed()){
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
