/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import nhinh.utils.MyConnection;

/**
 *
 * @author PC
 */
public class UserDAO {

    private static final String DIS_UP_AND_LOW = "SQL_Latin1_General_CP1_CS_AS";
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (sm != null) {
            sm.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }
    public static String checkLogin(String username, String password) throws SQLException, ClassNotFoundException {
        String name = "";
        
        String sql = "select fullname "
                + "from tblUsers "
                + "where userID = ? COLLATE " + DIS_UP_AND_LOW
                + " and password = ? COLLATE " + DIS_UP_AND_LOW;
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                name = rs.getString("Fullname");
            } else {
                name = "";
            }

        } finally {
            closeConnection();
        }
        return name;
    }

    public boolean checkEmpty(String username, String password) {
        if (username.trim().equalsIgnoreCase("") || password.trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }
}
