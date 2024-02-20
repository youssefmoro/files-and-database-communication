package dataTransferCommProject.example.RestApis.Models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
public class InventoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
   @Column
    private String itemName;
    @Column
    private double itemPrice;
    @Column
    private int inventoryQuantity;

    public InventoryModel(long orderId, String itemName, double itemPrice, int inventoryQuantity) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.inventoryQuantity = inventoryQuantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        return "InventoryModel{" +
                "orderId=" + orderId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", inventoryQuantity=" + inventoryQuantity +
                '}';
    }
}
