package com.flickart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;

public class CartDao {
    public static boolean addToCart(String userId, String productId, int quantity, int price) throws SQLException, ClassNotFoundException {
        Connection con = JDBCUtil.getConnection();
        
        String cartId = getOrCreateCart(con, userId);

        String query = "INSERT INTO CartItems (cartId, productId, quantity, price) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, cartId);
        ps.setString(2, productId);
        ps.setInt(3, quantity);
        ps.setDouble(4, price);

        ps.executeUpdate();

        // Step 3: Update cart total
        updateCartTotal(con, cartId);

        return true;
    }

    private static String getOrCreateCart(Connection con, String userId) throws SQLException {
        String query = "SELECT cartId FROM Cart WHERE userId = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("cartId"); 
            
        }

        String newCartId = UniqueId.getUniqueId();
        String insertCartQuery = "INSERT INTO Cart (cartId, userId) VALUES (?, ?)";
        PreparedStatement insertPs = con.prepareStatement(insertCartQuery);
        insertPs.setString(1, newCartId);
        insertPs.setString(2, userId);
        insertPs.executeUpdate();

        return newCartId;
    }

    private static void updateCartTotal(Connection con, String cartId) throws SQLException {
        String query = "SELECT SUM(quantity * price) FROM CartItems WHERE cartId = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, cartId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            double totalAmount = rs.getDouble(1);
            String updateCartQuery = "UPDATE Cart SET totalAmount = ? WHERE cartId = ?";
            PreparedStatement updatePs = con.prepareStatement(updateCartQuery);
            updatePs.setDouble(1, totalAmount);
            updatePs.setString(2, cartId);
            updatePs.executeUpdate();
        }
    }
}
