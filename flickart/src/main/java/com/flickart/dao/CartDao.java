package com.flickart.dao;

import com.flickart.model.Cart;
import com.flickart.model.CartItem;
import com.flickart.model.Product;
import com.flickart.util.CreateQuery;
import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CartDao {
    private static final String TABLE_NAME = "Cart";
    private static final String USER_ID_COL = "userId";
    private static final String TOTAL_AMOUNT_COL = "totalAmount";
    private static final String CART_ID_COL = "cartId";
    public static boolean addToCart(String userId, Product product, int productCount) throws SQLException, ClassNotFoundException {
        String uniqueCartId = UniqueId.getUniqueId();
        Connection connection = JDBCUtil.getConnection();
        String query = CreateQuery.getInsertQuery(TABLE_NAME, CART_ID_COL, USER_ID_COL, TOTAL_AMOUNT_COL);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uniqueCartId);
        preparedStatement.setString(2, userId);
        preparedStatement.setFloat(3, product.getPrice() * productCount);
        preparedStatement.executeUpdate();
        CartItemsDao.addToCart(userId, product, productCount);
        return true;
    }
    private static String getCartId(String userId) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement("select cartId from " + TABLE_NAME + " where userId = ?");
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString(CART_ID_COL);
        }
        throw new SQLException("Can't find cart id");
    }

    public static Cart getCart(String userId) throws SQLException, ClassNotFoundException {
        String cartId = getCartId(userId);
        if(cartId == null) {
            throw new SQLException("Invalid user id");
        }
        List<CartItem> cartItems = CartItemsDao.getCartItems(cartId);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from " + TABLE_NAME + " where userId = ?");
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();

       if(rs.next()){
           return new Cart(cartId, userId, rs.getFloat(TOTAL_AMOUNT_COL), cartItems);
       }
       return null;
    }
    public static boolean removeFromCart(String userId,int quantity, Product product) throws SQLException, ClassNotFoundException{
        String cartId = getCartId(userId);
        CartItemsDao.removeFromCart(userId, product.getProductId());
        updateCartTotalPrice(cartId, -(quantity*product.getPrice()));
        return true;
    }
    public static  boolean updateCartTotalPrice(String userId, float productPrice) throws SQLException, ClassNotFoundException {
        Connection connection  = JDBCUtil.getConnection();
        String query = "update "+TABLE_NAME+" set totalAmount = totalAmount + ? where userId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setFloat(1, productPrice);
        preparedStatement.setString(2, userId);
        preparedStatement.executeUpdate();
        return true;
    }
    public static boolean updateProductCount(String userId,int prevQuantity,int changeInAmount, Product product) throws SQLException, ClassNotFoundException{
        String cartId = getCartId(userId);
        CartItemsDao.updateProductCount(cartId, product.getProductId(), prevQuantity+changeInAmount);
        updateCartTotalPrice(cartId, prevQuantity + (changeInAmount *product.getPrice()));
        return true;
    }
}
