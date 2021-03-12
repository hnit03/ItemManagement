/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.daos;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import nhinh.dtos.SupplierDTO;
import nhinh.utils.MyConnection;

/**
 *
 * @author PC
 */
public class SupplierDAO extends Vector<SupplierDTO>{
    
    Connection con = null;
    Statement sm = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    
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
    
    public SupplierDAO(){
        super();
    }
    public int find(String supCode){
        for (int i = 0; i < this.size(); i++) {
            if (supCode.equals(this.get(i).getSupCode())) {
                return i;
            }
        }
        return -1;
    }
        
    public SupplierDTO findSupplierDTO(String supCode){
        int i = find(supCode);
        return i < 0? null : this.get(i);
    }
    public void loadFromDB() throws SQLServerException,SQLException, ClassNotFoundException{
        String supCode, supName, address;
        boolean collaborating;
        String sql = "select supcode,supname,address,collaborating "
                + "from tblSuppliers ";
        try {
            con = MyConnection.makeConnection();
            sm = con.createStatement();
            rs = sm.executeQuery(sql);
            while (rs.next()) {                
                supCode = rs.getString(1);
                supName = rs.getString(2);
                address = rs.getString(3);
                collaborating = rs.getBoolean(4);
                SupplierDTO supplierDTO = new SupplierDTO(supCode, supName, address, collaborating);
                this.add(supplierDTO);
            }
        } finally{
            closeConnection();
        }
    }
    /*check Empty*/
    public boolean checkEmpty(String stringCheck){
        if (stringCheck.trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }
    /*insert*/
    public int insert(SupplierDTO sdto) throws SQLServerException,SQLException, ClassNotFoundException{
        String sql ="Insert into tblSuppliers(supCode,supName,address,collaborating) "
                + "values(?,?,?,?)";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdto.getSupCode());
            ps.setString(2, sdto.getSupName());
            ps.setString(3, sdto.getAddress());
            ps.setBoolean(4, sdto.isColloborating());
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }
    public int update(SupplierDTO sdto) throws SQLServerException,ClassNotFoundException, SQLException{
        String sql = "Update tblSuppliers "
                + "set supName = ?, address = ?, collaborating = ? "
                + "where supCode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, sdto.getSupName());
            ps.setString(2, sdto.getAddress());
            ps.setBoolean(3, sdto.isColloborating());
            ps.setString(4, sdto.getSupCode());
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }
    public int delete(String supCode) throws SQLServerException, ClassNotFoundException, SQLException{
        String sql = "Delete from tblSuppliers "
                + "where supcode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1,supCode);
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }
    public boolean checkSupCodeDup(String code) throws SQLServerException, ClassNotFoundException, SQLException{
        String sql = "select supcode "
                + "from tblSuppliers "
                + "where supcode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            closeConnection();
        }
    }
    public boolean checkSupCodeExistInItems(String code) throws SQLServerException,ClassNotFoundException, SQLException{
        String sql = "select supcode "
                + "from tblSuppliers "
                + "where supCode in "
                + "(select supcode "
                + "from tblItems "
                + "where supCode = ?)";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, code);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            closeConnection();
        }
    }
   public boolean checkSupCodeRegExp(String supCode){
       if (!supCode.matches("\\w+")) {
           return false;
       }
       return true;
   }
}
