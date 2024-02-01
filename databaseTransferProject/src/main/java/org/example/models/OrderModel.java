package org.example.models;

public class OrderModel {
//    private int orderNumber;
//    private int itemQuantity;
    private long orderNumber;
    private long accountId;
    private long itemId;

    private long orderQuantity;

    public OrderModel(long orderNumber, long accountId, long itemId, long orderQuantity) {
        this.orderNumber = orderNumber;
        this.accountId = accountId;
        this.itemId = itemId;
        this.orderQuantity = orderQuantity;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "orderNumber=" + orderNumber +
                ", accountId=" + accountId +
                ", itemId=" + itemId +
                ", orderQuantity=" + orderQuantity +
                '}';
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(long orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
