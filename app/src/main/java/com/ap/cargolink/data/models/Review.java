package com.ap.cargolink.data.models;

public class Review {
    private String reviewId;
    private String review;
    private String driverId;
    private String orderId;
    private String offerId;

    public Review(String reviewId, String review, String driverId, String orderId, String offerId) {
        this.reviewId = reviewId;
        this.review = review;
        this.driverId = driverId;
        this.orderId = orderId;
        this.offerId = offerId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public String getReview() {
        return review;
    }

    public String getDriverId() {
        return driverId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
}
