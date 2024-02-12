package org.example.models;

public class OrderModel {
    private int orderNumber;
    private String accountId;
    private String itemId;

    private String orderQuantity;

    public OrderModel(int orderNumber, String accountId, String itemId, String orderQuantity) {
        this.orderNumber = orderNumber;
        this.accountId = accountId;
        this.itemId = itemId;
        this.orderQuantity = orderQuantity;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
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
