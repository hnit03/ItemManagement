/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhinh.dtos;

/**
 *
 * @author PC
 */
public class ItemDTO {
    String itemCode = "", itemName ="";
    String unit = "";
    float price;
    boolean supplying = false;
    SupplierDTO s = null;

    public ItemDTO(String itemCode, String itemName, SupplierDTO supplier,
            String unit, float price, boolean supplying) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.s = supplier;
        this.unit = unit;
        this.price = price;
        this.supplying = supplying;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isSupplying() {
        return supplying;
    }

    public void setSupplying(boolean supplying) {
        this.supplying = supplying;
    }

    public SupplierDTO getSupplier() {
        return s;
    }

    public void setSupplier(SupplierDTO s) {
        this.s = s;
    }
}
