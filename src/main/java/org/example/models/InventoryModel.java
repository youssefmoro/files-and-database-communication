package org.example.models;

public class InventoryModel {
    private String itemName;
    private double itemPrice;
    private int inventoryQuantity;

    public InventoryModel(String itemName, double itemPrice, int inventoryQuantity) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.inventoryQuantity = inventoryQuantity;
    }

    @Override
    public String toString() {
        return "InventoryModel{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", inventoryQuantity=" + inventoryQuantity +
                '}';
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }
}
