package com.flickart.dao;

import com.flickart.model.ProductCategory;
import com.flickart.util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDao {
    private static final String TABLE_NAME = "Categories";
    private static final String CATEGORY_COL = "category";
    private static final String CATEGORY_NAME_COL = "categoryName";
    private static final String CATEGORY_IMAGE_COL = "categoryImage";


    public static List<ProductCategory> getAllCategories() throws SQLException , ClassNotFoundException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            List<ProductCategory> list = new ArrayList<>();
            connection = JDBCUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            while (resultSet.next()) {
                list.add(new ProductCategory(resultSet.getString(CATEGORY_COL), resultSet.getString(CATEGORY_NAME_COL), resultSet.getString(CATEGORY_IMAGE_COL)));
            }
            return list;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }

    }
    public  static  List<ProductCategory> searchByCategory(String category) throws SQLException , ClassNotFoundException{
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            if(category == null || category.isEmpty()){
                return getAllCategories();
            }
            List<ProductCategory> list = new ArrayList<>();
            category = category.toLowerCase();
            connection = JDBCUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME +" where lower(categoryName) like '%"+category+"%'");
            while (resultSet.next()) {
                list.add(new ProductCategory(resultSet.getString(CATEGORY_COL), resultSet.getString(CATEGORY_NAME_COL), resultSet.getString(CATEGORY_IMAGE_COL)));
            }
            return list;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }

    }
}
