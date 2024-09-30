package com.flickart.model;
import java.util.List;

public class Cart {
    private String cartId;
    private String userId;
    private double totalAmount;
    private List<CartItem> cartItems;

    public Cart() {}

    public Cart(String cartId, String userId, double totalAmount, List<CartItem> cartItems) {
        this.cartId = cartId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.cartItems = cartItems;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
