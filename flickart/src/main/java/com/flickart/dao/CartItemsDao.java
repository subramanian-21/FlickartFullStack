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

    static boolean addToCart(String cartId, Product product) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            String query = CreateQuery.getInsertQuery(TABLE_NAME, CART_ID_COL, PRODUCT_ID_COL, QUANTITY_COL, PRICE_COL, CART_ITEM_ID_COL);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cartId);
            preparedStatement.setString(2, product.getProductId());
            preparedStatement.setInt(3, 1);
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setString(5, UniqueId.getUniqueId());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {

            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
    static List<CartItem> getCartItems(Connection connection, String cartId) throws  SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<CartItem> cartItems  = new ArrayList<CartItem>();
            preparedStatement = connection.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME, CART_ID_COL));
            preparedStatement.setString(1, cartId);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {

                String cartItemId = resultSet.getString(CART_ITEM_ID_COL);
                String productId = resultSet.getString(PRODUCT_ID_COL);
                int quantity = resultSet.getInt(QUANTITY_COL);
                float price = resultSet.getFloat(PRICE_COL);
              Product product = ProductDao.getProduct(connection, productId);
                CartItem cartItem = new CartItem(cartItemId,cartId,productId,product,quantity,price);
                cartItems.add(cartItem);
            }
            return cartItems;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            if(resultSet != null) {
                resultSet.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }

        }
    }
    static boolean removeFromCart(String cartItemId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement("delete from " + TABLE_NAME + " where " + CART_ITEM_ID_COL + " = ? ");
            preparedStatement.setString(1, cartItemId);
            return  preparedStatement.executeUpdate() > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
       finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
    static boolean updateProductCount(String cartId, String productId, int quantity) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
       try {
           connection = JDBCUtil.getConnection();
           int availableQuantity = ProductDao.getProductStock(connection, productId);
           if(quantity > availableQuantity) {
               throw new SQLException("Only "+availableQuantity+" is available");
           }
           ProductDao.updateProductCount(productId, quantity);
           preparedStatement = connection.prepareStatement("update " + TABLE_NAME + " set quantity = ? where " + CART_ID_COL + " = ? & "+PRODUCT_ID_COL+" = ?");
           preparedStatement.setInt(1, quantity);
           preparedStatement.setString(2, cartId);
           preparedStatement.setString(3, productId);

           int executeCount = preparedStatement.executeUpdate();
           return  executeCount > 0;
       }catch (SQLException e) {
           e.printStackTrace();
           throw new SQLException(e.getMessage());
       }
       finally {
           if(preparedStatement != null) {
               preparedStatement.close();
           }
           if(connection != null) {
               connection.close();
           }
       }
    }
    static int getProductCount(String cartId, String productId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME,CART_ID_COL ,PRODUCT_ID_COL));
            preparedStatement.setString(1, cartId);
            preparedStatement.setString(2, productId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return rs.getInt(PRODUCT_ID_COL);
            }
            throw new SQLException("No product found");
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }

        }
    }
}
