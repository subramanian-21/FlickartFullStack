package com.flickart.model;

public class Review {
    private  String reviewId;
    private  String userId;
    private  String productId;
    private  float rating;
    private  String comment;
    private  String createdAt;
    private  User userDetails;

    public Review(String reviewId, String userId, User userDetails,String productId, float rating, String comment, String createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userDetails = userDetails;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
