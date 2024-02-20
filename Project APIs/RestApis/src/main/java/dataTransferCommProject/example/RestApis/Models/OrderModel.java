package dataTransferCommProject.example.RestApis.Models;

import jakarta.persistence.*;

@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;
    @Column
    private String accountId;
    @Column
    private String itemId;
    @Column
    private String orderQuantity;

    public OrderModel(Long orderNumber, String accountId, String itemId, String orderQuantity) {
        this.orderNumber = orderNumber;
        this.accountId = accountId;
        this.itemId = itemId;
        this.orderQuantity = orderQuantity;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderNumber=" + orderNumber +
                ", accountId='" + accountId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", orderQuantity='" + orderQuantity + '\'' +
                '}';
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
