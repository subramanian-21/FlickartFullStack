package com.flickart.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.flickart.model.AdminUser;
import com.flickart.model.Product;
import com.flickart.util.CreateQuery;
import com.flickart.util.HashPassword;
import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;


public class ProductDao {
	private static final String TABLE_NAME = "Products";
	private static final String IMAGE_TABLE_NAME = "ProductImages";
	private static final String IMAGE_ID_COL = "imageId";
	private static final String IMAGE_URL_COL = "imageUrl";

	private static final String PRODUCT_ID_COL = "productId";
	private static final String PRODUCT_NAME_COL = "productName";
	private static final String PRODUCT_DESCRIPTION_COL = "productDescription";
	private static final String PRODUCT_PRICE_COL = "price";
	private static final String PRODUCT_STOCK_COL = "stock";
	private static final String PRODUCT_IMAGE_COL = "image";

	public static boolean addProduct(String productName, String productDescription, int price, int stockCount, String image, List<String> images) throws ClassNotFoundException, SQLException {
		String productQuery = CreateQuery.getInsertQuery(TABLE_NAME, PRODUCT_ID_COL, PRODUCT_NAME_COL, PRODUCT_DESCRIPTION_COL, PRODUCT_PRICE_COL,PRODUCT_STOCK_COL, PRODUCT_IMAGE_COL);

		Connection con = JDBCUtil.getConnection();
		PreparedStatement productPs = con.prepareStatement(productQuery);

		String uniqueProductId = UniqueId.getUniqueId();
		productPs.setString(1, uniqueProductId);
		productPs.setString(2,  productName);
		productPs.setString(3,  productDescription);
		productPs.setInt(4,  price);
		productPs.setInt(5,  stockCount);
		productPs.setString(6,  image);

		StringBuilder imageQuery = new StringBuilder("insert into ProductImages (productId,imageUrl) values");
		for(int i = 0;i<images.size();i++) {
			imageQuery.append('(');
			imageQuery.append("'").append(uniqueProductId).append("'");
			imageQuery.append(',');
			imageQuery.append("'").append(images.get(i)).append("'");
			imageQuery.append(')');
			if(i != images.size()-1) {
				imageQuery.append(",");
			}
		}
		Statement imagePs = con.createStatement();
		productPs.executeUpdate();
		imagePs.execute(imageQuery.toString());

		
		return true;
	}
	public static boolean updateProductCount(String productId, int updateCount) throws Exception {
		String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL ,PRODUCT_STOCK_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		int prevCount = getProductStock(productId);
		if(prevCount < updateCount) {
			throw new Exception("Given count greater than available count.");
		}
		ps.setInt(1, prevCount + updateCount);
		
		ps.setString(2, productId);
		
		return true;
	}
	public static boolean updateProduct(String productId, Product product) throws Exception {
		// add id at last
		String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL ,PRODUCT_NAME_COL, PRODUCT_PRICE_COL, PRODUCT_IMAGE_COL, PRODUCT_STOCK_COL, PRODUCT_DESCRIPTION_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setString(1, product.getProductName());
		ps.setInt(2, product.getPrice());
		ps.setString(3, product.getImage());
		ps.setInt(4, product.getStockCount());
		ps.setString(5, product.getProductDescription());
		ps.setString(6, productId);
		return true;
	}
	public static boolean updateProductPrice(String productId, float price) throws Exception {
		// add id at last
		String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL , PRODUCT_PRICE_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setFloat(1, price);
		ps.setString(2, productId);
		return true;
	}

	public static int getProductStock(String productId) throws ClassNotFoundException, SQLException{
		String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, productId);
		
		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = Integer.parseInt(rs.getString(PRODUCT_STOCK_COL));
		
		return count;
	}
	public static List<Product> getAllProducts(int limit, int offset) throws  ClassNotFoundException, SQLException{
		List<Product> productList = new ArrayList<>();
		String query = CreateQuery.getSelectQuery(TABLE_NAME)+" limit "+limit+" offset "+offset;
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String productId = rs.getString(PRODUCT_ID_COL);
			PreparedStatement imagePs = con.prepareStatement(CreateQuery.getSelectQuery(IMAGE_TABLE_NAME, PRODUCT_ID_COL));
			imagePs.setString(1, productId);
			ResultSet imageResultSet = imagePs.executeQuery();
			List<String > imageList = new ArrayList<>();
			while(imageResultSet.next()) {
				imageList.add(imageResultSet.getString(IMAGE_ID_COL));
			}
			productList.add(new Product(productId, rs.getString(PRODUCT_NAME_COL), rs.getString(PRODUCT_DESCRIPTION_COL), (int)(rs.getInt(PRODUCT_PRICE_COL)), 0, rs.getString(PRODUCT_IMAGE_COL), imageList));
		}
		return productList;
	}
	public static int getProductsCount() throws ClassNotFoundException, SQLException{
		String query = "select count(productId) as count from "+TABLE_NAME;
		Connection con = JDBCUtil.getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(query);
		rs.next();

		return (int)rs.getInt("count");
	}
	public  static  Product getProduct(String productId) throws SQLException, ClassNotFoundException {
		String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, productId);
		ResultSet rs = ps.executeQuery();
		rs.next();
		PreparedStatement imagePs = con.prepareStatement(CreateQuery.getSelectQuery(IMAGE_TABLE_NAME, PRODUCT_ID_COL));
		imagePs.setString(1, productId);
		ResultSet imageResultSet = imagePs.executeQuery();
		List<String > imageList = new ArrayList<>();
		while(imageResultSet.next()) {
			imageList.add(imageResultSet.getString(IMAGE_URL_COL));
		}
		return new Product(productId, rs.getString(PRODUCT_NAME_COL), rs.getString(PRODUCT_DESCRIPTION_COL), (int)(rs.getInt(PRODUCT_PRICE_COL)), (int)rs.getInt(PRODUCT_STOCK_COL), rs.getString(PRODUCT_IMAGE_COL), imageList);
	}
	public static List<Product> getAllProductsAdmin(int limit, int offset) throws  ClassNotFoundException, SQLException{
		List<Product> productList = new ArrayList<>();
		String query = CreateQuery.getSelectQuery(TABLE_NAME)+" limit "+limit+" offset "+offset;
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String productId = rs.getString(PRODUCT_ID_COL);
			PreparedStatement imagePs = con.prepareStatement(CreateQuery.getSelectQuery(IMAGE_TABLE_NAME, PRODUCT_ID_COL));
			imagePs.setString(1, productId);
			ResultSet imageResultSet = imagePs.executeQuery();
			List<String > imageList = new ArrayList<>();
			while(imageResultSet.next()) {
				imageList.add(imageResultSet.getString(IMAGE_URL_COL));
			}
			productList.add(new Product(productId, rs.getString(PRODUCT_NAME_COL), rs.getString(PRODUCT_DESCRIPTION_COL), (int)(rs.getInt(PRODUCT_PRICE_COL)), (int)rs.getInt(PRODUCT_STOCK_COL), rs.getString(PRODUCT_IMAGE_COL), imageList));
		}
		return productList;
	}
}
