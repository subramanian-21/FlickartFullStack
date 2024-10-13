package com.flickart.model;

public class CartItem {
    private String cartItemId;
    private String cartId;
    private String productId;
    private Product product;
    private int quantity;
    private float price;

    public CartItem() {}

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem(String cartItemId, String cartId, String productId, Product product, int quantity, float price) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
