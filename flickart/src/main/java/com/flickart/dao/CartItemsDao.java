package com.flickart.dao;

import com.flickart.model.CartItem;
import com.flickart.model.Product;
import com.flickart.util.CreateQuery;
import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartItemsDao {
    private static final String TABLE_NAME = "CartItems";
    private static final String CART_ITEM_ID_COL = "cartItemId";
    private static final String CART_ID_COL = "cartId";
    private static final String PRODUCT_ID_COL = "productId";
    private static final String QUANTITY_COL = "quantity";
    private static final String PRICE_COL = "price";

    static boolean addToCart(String cartId, Product product, int quantiy) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCUtil.getConnection();
        String query = CreateQuery.getInsertQuery(TABLE_NAME, CART_ITEM_ID_COL, CART_ID_COL, PRODUCT_ID_COL, QUANTITY_COL, PRICE_COL);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, UniqueId.getUniqueId());
        preparedStatement.setString(2, cartId);
        preparedStatement.setString(3, product.getProductId());
        preparedStatement.setInt(4, quantiy);
        preparedStatement.setFloat(5, product.getPrice());
        preparedStatement.executeUpdate();
        return true;
    }
    static List<CartItem> getCartItems(String cartId) throws  SQLException, ClassNotFoundException {
        List<CartItem> cartItems  = new ArrayList<CartItem>();
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME, CART_ID_COL));
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String cartItemId = rs.getString(CART_ITEM_ID_COL);
            String productId = rs.getString(PRODUCT_ID_COL);
            int quantity = rs.getInt(QUANTITY_COL);
            float price = rs.getFloat(PRICE_COL);
            CartItem cartItem = new CartItem(cartItemId,cartId,productId,quantity,price);
            cartItems.add(cartItem);
        }
        return cartItems;
    }
    static boolean removeFromCart(String cartId, String productId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement("delete from " + TABLE_NAME + " where " + CART_ITEM_ID_COL + " = ? & "+PRODUCT_ID_COL+" = ?");
        return ps.executeUpdate() > 0;
    }
    static boolean updateProductCount(String cartId, String productId, int quantity) throws SQLException, ClassNotFoundException {
        int availableQuantity = ProductDao.getProductStock(productId);
        if(quantity > availableQuantity) {
            throw new SQLException("Only "+availableQuantity+" is available");
        }
        ProductDao.updateProductCount(productId, quantity);
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement("update " + TABLE_NAME + " set quantity = ? where " + CART_ID_COL + " = ? & "+PRODUCT_ID_COL+" = ?");
        ps.setInt(1, quantity);
        ps.setString(2, cartId);
        ps.setString(3, productId);
        return ps.executeUpdate() > 0;
    }
}
