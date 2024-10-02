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
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private static final String TABLE_NAME = "Cart";
    private static final String USER_ID_COL = "userId";
    private static final String TOTAL_AMOUNT_COL = "totalAmount";
    private static final String CART_ID_COL = "cartId";
    public static Cart createCart(String userId) throws SQLException, ClassNotFoundException {
        String uniqueCartId = UniqueId.getUniqueId();
        Connection connection = JDBCUtil.getConnection();
        String query = CreateQuery.getInsertQuery(TABLE_NAME, CART_ID_COL, USER_ID_COL, TOTAL_AMOUNT_COL);
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, uniqueCartId);
        preparedStatement.setString(2, userId);
        preparedStatement.setFloat(3, 0);
        preparedStatement.executeUpdate();
        return new Cart(uniqueCartId, userId, 0, new ArrayList<>());
    }
    public static boolean addToCart(String cartId, String productId) throws SQLException, ClassNotFoundException {
        Product product = ProductDao.getProductAdmin(productId);
        updateCartTotalPrice(cartId, product.getPrice());
        CartItemsDao.addToCart(cartId, product);
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

    public static Cart getCart(String cartId) throws SQLException, ClassNotFoundException {
        if(cartId == null) {
            throw new SQLException("Invalid cart id");
        }
        List<CartItem> cartItems = CartItemsDao.getCartItems(cartId);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("select * from " + TABLE_NAME + " where cartId = ?");
        ps.setString(1, cartId);
        ResultSet rs = ps.executeQuery();

       if(rs.next()){
           return new Cart(cartId, rs.getString(USER_ID_COL), rs.getFloat(TOTAL_AMOUNT_COL), cartItems);
       }
       return null;
    }
    public static boolean removeFromCart(String cartId,int quantity, Product product) throws SQLException, ClassNotFoundException{
        CartItemsDao.removeFromCart(cartId, product.getProductId());
        updateCartTotalPrice(cartId, -(quantity*product.getPrice()));
        return true;
    }
    public static boolean removeFromCart(String cartId, String productId, int quantity) throws SQLException, ClassNotFoundException{
        Product product = ProductDao.getProductAdmin(productId);
        removeFromCart(cartId, quantity, product);
        return true;
    }
    public static  boolean updateCartTotalPrice(String cartId, double productPrice) throws SQLException, ClassNotFoundException {
        Connection connection  = JDBCUtil.getConnection();
        String query = "update "+TABLE_NAME+" set totalAmount = totalAmount + ? where cartId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, productPrice);
        preparedStatement.setString(2, cartId);
        preparedStatement.executeUpdate();
        return true;
    }
    public static boolean updateProductCount(String cartId,int count, String productId) throws SQLException, ClassNotFoundException{
        Product product = ProductDao.getProductAdmin(productId);
        CartItemsDao.updateProductCount(cartId, product.getProductId(), count);
        int prevQuantity = CartItemsDao.getProductCount(cartId, product.getProductId());
        updateCartTotalPrice(cartId, (count - prevQuantity) * product.getPrice());
        return true;
    }
}
