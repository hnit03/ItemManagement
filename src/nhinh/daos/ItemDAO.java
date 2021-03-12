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
import nhinh.dtos.ItemDTO;
import nhinh.dtos.SupplierDTO;
import nhinh.utils.MyConnection;

/**
 *
 * @author PC
 */
public class ItemDAO extends Vector<ItemDTO> {

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

    public ItemDAO() {
        super();
    }

    public int find(String itemCode) {
        for (int i = 0; i < this.size(); i++) {
            if (itemCode.equals(this.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    public ItemDTO findItemDTO(String itemCode) {
        int i = find(itemCode);
        return i < 0 ? null : this.get(i);
    }

    public void loadFromDB(SupplierDAO supplierDAO) throws SQLServerException, SQLException, ClassNotFoundException {

        String sql = "select itemCode, itemName, unit, price, supplying, supCode "
                + "from tblItems ";
        try {
            con = MyConnection.makeConnection();
            sm = con.createStatement();
            rs = sm.executeQuery(sql);
            while (rs.next()) {
                String itemCode = rs.getString(1);
                String itemName = rs.getString(2);
                String unit = rs.getString(3);
                float price = rs.getFloat(4);
                boolean supplying = rs.getBoolean(5);
                String supCode = rs.getString(6);
                SupplierDTO sdto = supplierDAO.findSupplierDTO(supCode);
                ItemDTO itemDTO = new ItemDTO(itemCode, itemName, sdto, unit, price, supplying);
                this.add(itemDTO);
            }
        } finally {
            closeConnection();
        }
    }

    public boolean checkEmpty(String stringCheck) {
        if (stringCheck.trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public boolean checkItemCodeRegExp(String itemCode) {
        if (!itemCode.matches("\\w+")) {
            return false;
        }
        return true;
    }

    public boolean checkItemCodeDup(String itemCode) throws SQLServerException, ClassNotFoundException, SQLException {
        String sql = "select itemCode "
                + "from tblItems "
                + "where itemCode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            return rs.next();
        } finally {
            closeConnection();
        }
    }

    public boolean checkPriceIsANum(String priceStr) {
        if (!priceStr.matches("\\d+") && !priceStr.matches("[0-9]+\\.[0-9]+")) {
            return false;
        }
        return true;
    }

    public boolean checkPricePositive(String priceStr) {
        if (!(Float.parseFloat(priceStr) < 0)) {
            return false;
        }
        return true;
    }

    public int insert(ItemDTO idto) throws SQLServerException, ClassNotFoundException, SQLException {
        String sql = "insert into tblItems(itemCode, itemName, unit, price, supplying, supCode) "
                + "values(?,?,?,?,?,?)";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idto.getItemCode());
            ps.setString(2, idto.getItemName());
            ps.setString(3, idto.getUnit());
            ps.setFloat(4, idto.getPrice());
            ps.setBoolean(5, idto.isSupplying());
            ps.setString(6, idto.getSupplier().getSupCode());
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int update(ItemDTO idto) throws SQLServerException, SQLException, ClassNotFoundException {
        String sql = "update tblItems "
                + "set itemName = ?, unit = ?, price = ?, supplying = ?, supCode = ? "
                + "where itemCode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idto.getItemName());
            ps.setString(2, idto.getUnit());
            ps.setFloat(3, idto.getPrice());
            ps.setBoolean(4, idto.isSupplying());
            ps.setString(5, idto.getSupplier().getSupCode());
            ps.setString(6, idto.getItemCode());
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    public int delete(String itemCode) throws SQLServerException, ClassNotFoundException, SQLException {
        String sql = "delete from tblItems "
                + "where itemcode = ?";
        try {
            con = MyConnection.makeConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, itemCode);
            return ps.executeUpdate();
        } finally {
            closeConnection();
        }
    }
}
