package com.ap.cargolink.data.models;

import java.util.Map;

public class Order {
    private String orderId;
    private String orderReceiver;
    private String orderName;
    private String orderDescription;
    private String addressFrom;
    private String addressTo;
    private String senderNumber;
    private String receiverNumber;
    private double orderWeight;
    private String orderPrice;
    private String senderId;
    private String driverId;
    private String orderStatus;
    private Map<String, Boolean> offersIds;

    public Order(){}

    public Order(String orderId, String orderReceiver, String orderName, String orderDescription, String addressFrom, String addressTo, String senderNumber, String receiverNumber, double orderWeight,String orderPrice, String senderId, String driverId, String orderStatus, Map<String, Boolean> offersIds) {
        this.orderId = orderId;
        this.orderReceiver = orderReceiver;
        this.orderName = orderName;
        this.orderDescription = orderDescription;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.senderNumber = senderNumber;
        this.receiverNumber = receiverNumber;
        this.orderWeight = orderWeight;
        this.orderPrice = orderPrice;
        this.senderId = senderId;
        this.driverId = driverId;
        this.orderStatus = orderStatus;
        this.offersIds = offersIds;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderReceiver() {
        return orderReceiver;
    }

    public void setOrderReceiver(String orderReceiver) {
        this.orderReceiver = orderReceiver;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(String addressFrom) {
        this.addressFrom = addressFrom;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public double getOrderWeight() {
        return orderWeight;
    }

    public void setOrderWeight(double orderWeight) {
        this.orderWeight = orderWeight;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Map<String, Boolean> getOffersIds() {
        return offersIds;
    }

    public void setOffersIds(Map<String, Boolean> offersIds) {
        this.offersIds = offersIds;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }
}
