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
        List<ProductCategory> list = new ArrayList<>();
        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
        while (resultSet.next()) {
            list.add(new ProductCategory(resultSet.getString(CATEGORY_COL), resultSet.getString(CATEGORY_NAME_COL), resultSet.getString(CATEGORY_IMAGE_COL)));
        }
        return list;
    }
    public  static  List<ProductCategory> searchByCategory(String category) throws SQLException , ClassNotFoundException{
        if(category == null || category.isEmpty()){
            return getAllCategories();
        }
        List<ProductCategory> list = new ArrayList<>();
        category = category.toLowerCase();
        Connection connection = JDBCUtil.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME +" where lower(categoryName) like '%"+category+"%'");
        while (resultSet.next()) {
            list.add(new ProductCategory(resultSet.getString(CATEGORY_COL), resultSet.getString(CATEGORY_NAME_COL), resultSet.getString(CATEGORY_IMAGE_COL)));
        }
        return list;
    }
}
