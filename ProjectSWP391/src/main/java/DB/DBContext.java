
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//NGUYEN THANH PHAT CE180119
public class DBContext {

    public Connection connection;

    public DBContext() {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=db1;encrypt=true;trustServerCertificate=true";
            String username = "sa";
            String password = "123456";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
        }
    }
    
      public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        if (dbContext.connection != null) {
            System.out.println("Success connection");
        } else {
            System.out.println("Fail connection!");
        }
        
        
    }
}


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
