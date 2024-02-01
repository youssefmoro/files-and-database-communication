package org.example.models;

import java.io.BufferedReader;

import static org.example.validation.Validation.*;

public class AccountModel {


    private long accountId;
    private String mobileNumber;
    private String fullName;
    private String email;
    private String address;
    private String cardNumber;


    public AccountModel(long accountId, String mobileNumber, String fullName, String email, String address, String cardNumber) {
        this.accountId = accountId;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "accountId=" + accountId +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}