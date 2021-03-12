/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.view;

import javax.swing.table.AbstractTableModel;
import nhinh.daos.SupplierDAO;
import nhinh.dtos.SupplierDTO;

/**
 *
 * @author PC
 */
public class SupplierFullModel extends AbstractTableModel{
    SupplierDAO supplierDAO = null;
    
    public SupplierFullModel(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }
    
    public SupplierDAO getSuppliers(){
        return supplierDAO;
    }
    
    @Override
    public int getRowCount() {
        return supplierDAO.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SupplierDTO supplierDTO = supplierDAO.get(rowIndex);
        switch (columnIndex){
            case 0: return supplierDTO.getSupCode();
            case 1: return supplierDTO.getSupName();
            case 2: return supplierDTO.getAddress();
            case 3: return supplierDTO.isColloborating();
        }
        return null;
    }
    
    public String getColumnName(int column){
        switch(column){
            case 0: return "SupCode";
            case 1: return "SupName";
            case 2: return "Address";
            case 3: return "Collaborating";
        }
        return "";
    }
}
