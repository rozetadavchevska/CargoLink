package com.ap.cargolink.data.models;

import java.util.Map;

public class User {
    private String userId;
    private String userEmail;
    private String userPassword;
    private String firstName;
    private String lastName;
    private String userType;
    private Map<String, Boolean> orderId;

    public User(){}

    public User(String userId, String userEmail, String userPassword, String firstName, String lastName, String userType, Map<String, Boolean> orderId){
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Map<String, Boolean> getOrderId() {
        return orderId;
    }

    public void setOrderId(Map<String, Boolean> orderId) {
        this.orderId = orderId;
    }
}
