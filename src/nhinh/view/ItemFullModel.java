/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.view;

import javax.swing.table.AbstractTableModel;
import nhinh.daos.ItemDAO;
import nhinh.dtos.ItemDTO;

/**
 *
 * @author PC
 */
public class ItemFullModel extends AbstractTableModel{

    ItemDAO itemDAO = null;
    public ItemFullModel(ItemDAO itemDAO){
        this.itemDAO = itemDAO;
    }

    public ItemDAO getItems(){
        return itemDAO;
    }
    
    @Override
    public int getRowCount() {
        return itemDAO.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemDTO itemDTO = itemDAO.get(rowIndex);
        switch (columnIndex){
            case 0: return itemDTO.getItemCode();
            case 1: return itemDTO.getItemName();
            case 2: return itemDTO.getSupplier();
            case 3: return itemDTO.getUnit();
            case 4: return itemDTO.getPrice();
            case 5: return itemDTO.isSupplying();
        }
        return null;
    }
    public String getColumnName(int column){
        switch(column){
            case 0: return "Code";
            case 1: return "Name";
            case 2: return "Supplier";
            case 3: return "Unit";
            case 4: return "Price";
            case 5: return "Supplying";
        }
        return "";
    }
}
