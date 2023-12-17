package com.ap.cargolink.data.models;

public class Offer {
    private String offerId;
    private String orderId;
    private String senderId;
    private String receiver;
    private String driverId;
    private String deliveryDate;
    private String deliveryPrice;
    private String offerStatus;

    public Offer(){}
    public Offer(String offerId, String orderId, String senderId, String receiver, String driverId, String deliveryDate, String deliveryPrice, String offerStatus) {
        this.offerId = offerId;
        this.orderId = orderId;
        this.senderId = senderId;
        this.receiver = receiver;
        this.driverId = driverId;
        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
        this.offerStatus = offerStatus;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }
}
