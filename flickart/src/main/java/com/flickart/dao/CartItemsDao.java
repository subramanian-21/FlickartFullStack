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
            String query = CreateQuery.getInsertQuery(TABLE_NAME, CART_ITEM_ID_COL, CART_ID_COL, PRODUCT_ID_COL, QUANTITY_COL, PRICE_COL);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UniqueId.getUniqueId());
            preparedStatement.setString(2, cartId);
            preparedStatement.setString(3, product.getProductId());
            preparedStatement.setInt(4, 1);
            preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        }finally {

            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
    static List<CartItem> getCartItems(String cartId) throws  SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            List<CartItem> cartItems  = new ArrayList<CartItem>();
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME, CART_ID_COL));
            preparedStatement.setString(1, cartId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String cartItemId = rs.getString(CART_ITEM_ID_COL);
                String productId = rs.getString(PRODUCT_ID_COL);
                int quantity = rs.getInt(QUANTITY_COL);
                float price = rs.getFloat(PRICE_COL);
                CartItem cartItem = new CartItem(cartItemId,cartId,productId,quantity,price);
                cartItems.add(cartItem);
            }
            return cartItems;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
    static boolean removeFromCart(String cartId, String productId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement("delete from " + TABLE_NAME + " where " + CART_ID_COL + " = ? & "+PRODUCT_ID_COL+" = ?");
            preparedStatement.setString(1, cartId);
            preparedStatement.setString(2, productId);
            int executeCount = preparedStatement.executeUpdate();
            return  executeCount > 0;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        }finally {
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
           int availableQuantity = ProductDao.getProductStock(productId);
           if(quantity > availableQuantity) {
               throw new SQLException("Only "+availableQuantity+" is available");
           }
           ProductDao.updateProductCount(productId, quantity);
           connection = JDBCUtil.getConnection();
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
       catch (ClassNotFoundException e) {
           e.printStackTrace();
           throw new ClassNotFoundException(e.getMessage());
       }finally {
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
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException(e.getMessage());
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }
}
