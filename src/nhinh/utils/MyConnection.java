/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.utils;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class MyConnection {

    static final String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static final String url = "jdbc:sqlserver://localhost:1433; databaseName = ItemManagement;" + "user=sa;password=hoangnhi";

    public static Connection makeConnection() throws SQLServerException,ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName(driver);
        con = DriverManager.getConnection(url);
        return con;
    }
}
