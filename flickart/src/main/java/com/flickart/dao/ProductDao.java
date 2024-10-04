package com.flickart.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flickart.model.Product;
import com.flickart.model.Review;
import com.flickart.util.CreateQuery;
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
	private static final String CATEGORY_COL = "category";
	private static final String BRAND_COL = "brand";
	private static final String DISCOUNT_COL = "discount";
	private static final String WARRANTY_COL = "warranty";
	private static final String RATING_COL = "rating";
	private static final String RATING_COUNT_COL = "ratingCount";

	public static boolean addProduct(Product product) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String productQuery = CreateQuery.getInsertQuery(
					TABLE_NAME,
					PRODUCT_ID_COL,
					PRODUCT_NAME_COL,
					PRODUCT_DESCRIPTION_COL,
					PRODUCT_PRICE_COL,
					PRODUCT_STOCK_COL,
					PRODUCT_IMAGE_COL,
					CATEGORY_COL,
					BRAND_COL,
					DISCOUNT_COL,
					WARRANTY_COL,
					RATING_COL,
					RATING_COUNT_COL
			);

			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(productQuery);

			String uniqueProductId = UniqueId.getUniqueId();
			preparedStatement.setString(1, uniqueProductId);
			preparedStatement.setString(2,  product.getProductName());
			preparedStatement.setString(3,  product.getProductDescription());
			preparedStatement.setDouble(4,  product.getPrice());
			preparedStatement.setInt(5,  product.getStockCount());
			preparedStatement.setString(6,  product.getImage());
			preparedStatement.setString(7,  product.getCategory());
			preparedStatement.setString(8,  product.getBrand());
			preparedStatement.setDouble(9,  product.getDiscount());
			preparedStatement.setString(10,  product.getWarranty());
			preparedStatement.setDouble(11,  product.getRating());
			preparedStatement.setInt(12,  product.getRatingCount());

			preparedStatement.executeUpdate();
			addImages(uniqueProductId, product.getImages());
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
	public static boolean updateProductCount(String productId, int updateCount) throws SQLException , ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL ,PRODUCT_STOCK_COL);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			int prevCount = getProductStock(productId);
			if(prevCount < updateCount) {
				throw new SQLException("Given count greater than available count.");
			}
			preparedStatement.setInt(1, prevCount + updateCount);

			preparedStatement.setString(2, productId);
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
	public static boolean updateProduct(String productId, Product product) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	try {
		String query = CreateQuery.getUpdateQuery(
				TABLE_NAME,
				PRODUCT_ID_COL ,
				PRODUCT_NAME_COL,
				PRODUCT_DESCRIPTION_COL,
				PRODUCT_PRICE_COL,
				PRODUCT_STOCK_COL,
				PRODUCT_IMAGE_COL,
				CATEGORY_COL,
				BRAND_COL,
				DISCOUNT_COL,
				WARRANTY_COL,
				RATING_COL,
				RATING_COUNT_COL
		);
		connection = JDBCUtil.getConnection();
		preparedStatement = connection.prepareStatement(query);

		preparedStatement.setString(1,  product.getProductName());
		preparedStatement.setString(2,  product.getProductDescription());
		preparedStatement.setDouble(3,  product.getPrice());
		preparedStatement.setInt(4,  product.getStockCount());
		preparedStatement.setString(5,  product.getImage());
		preparedStatement.setString(6,  product.getCategory());
		preparedStatement.setString(7,  product.getBrand());
		preparedStatement.setDouble(8,  product.getDiscount());
		preparedStatement.setString(9,  product.getWarranty());
		preparedStatement.setDouble(10,  product.getRating());
		preparedStatement.setInt(11,  product.getRatingCount());
		preparedStatement.setString(12, productId);
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
	public static boolean updateProductPrice(String productId, float price) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL , PRODUCT_PRICE_COL);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setFloat(1, price);
			preparedStatement.setString(2, productId);
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
	public static boolean deleteProduct(String productId) throws  ClassNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "delete from "+TABLE_NAME+" where productId='"+productId+"'";
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
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
	public static int getProductStock(String productId) throws ClassNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, productId);

			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			int count = Integer.parseInt(resultSet.getString(PRODUCT_STOCK_COL));
			return count;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}
	public static int getProductsCount(String searchString) throws ClassNotFoundException, SQLException{
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			String query = "";
			if(searchString != null && !searchString.isEmpty()){
				query = "select count(productId) as count from "+TABLE_NAME+" where lower(productName) like '%"+searchString+"%' or productDescription like '%"+searchString+"%'";
			}else{
				query = "select count(productId) as count from "+TABLE_NAME;
			}

			connection = JDBCUtil.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			resultSet.next();
			int count = resultSet.getInt("count");
			return count;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
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
	public  static  Product getProduct(String productId) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, productId);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()){
				throw new Exception("Invalid product id");
			}

			List<Review> reviews = ReviewDao.getReviews(productId);
			List<String > imageList = getProductImages(productId);
			String createdAt = resultSet.getString("createdAt");
			return new Product(
					productId,
					resultSet.getString(PRODUCT_NAME_COL),
					resultSet.getString(PRODUCT_DESCRIPTION_COL),
					resultSet.getInt(PRODUCT_PRICE_COL),
					resultSet.getString(CATEGORY_COL),
					resultSet.getString(BRAND_COL),
					resultSet.getDouble(DISCOUNT_COL),
					resultSet.getString(WARRANTY_COL),
					resultSet.getDouble(RATING_COL),
					resultSet.getInt(RATING_COUNT_COL),
					resultSet.getInt(PRODUCT_STOCK_COL),
					resultSet.getString(PRODUCT_IMAGE_COL),
					imageList,
					reviews,
					createdAt
			);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	public static Map<Object, Object> getAllProducts(int limit, int offset, String searchString) throws  ClassNotFoundException, SQLException{
		String query = "";
		if( searchString == null || searchString.isEmpty()){
			query = "select *, count(*) over() as count from "+TABLE_NAME+" limit "+limit+" offset "+offset;
		}else {
			searchString = searchString.toLowerCase();
			query = "select *, count(*) over() as count from "+TABLE_NAME+" where lower(productName) like '%"+searchString+"%' or lower(productDescription) like '%"+searchString+"%'  limit "+limit+" offset "+offset;
		}
		return getAllProductsWithQuery(query, limit, offset);
	}
	public  static  Product getProductAdmin(String productId) throws SQLException, ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, productId);
			resultSet = preparedStatement.executeQuery();
			if(!resultSet.next()){
				throw new SQLException("Invalid product id");
			}
			List<String > imageList = getProductImages(productId);
			List<Review> reviews = ReviewDao.getReviews(productId);

			return new Product(
					productId,
					resultSet.getString(PRODUCT_NAME_COL),
					resultSet.getString(PRODUCT_DESCRIPTION_COL),
					resultSet.getInt(PRODUCT_PRICE_COL),
					resultSet.getString(CATEGORY_COL),
					resultSet.getString(BRAND_COL),
					resultSet.getDouble(DISCOUNT_COL),
					resultSet.getString(WARRANTY_COL),
					resultSet.getDouble(RATING_COL),
					resultSet.getInt(RATING_COUNT_COL),
					resultSet.getInt(PRODUCT_STOCK_COL),
					resultSet.getString(PRODUCT_IMAGE_COL),
					imageList,
					reviews,
					resultSet.getString("createdAt")
			);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
			resultSet.close();
			}
			if(preparedStatement != null) {
			preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	public static List<Product> getAllProductsAdmin(int limit, int offset, String searchString) throws  ClassNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<Product> productList = new ArrayList<>();

			String query = "";
			if(searchString != null && !searchString.isEmpty()){
				searchString = searchString.toLowerCase();
				query = CreateQuery.getSelectQuery(TABLE_NAME)+" where lower(productName) like '%"+searchString+"%' or productDescription like '%"+searchString+"%'  limit "+limit+" offset "+offset;
			}else {
				query = CreateQuery.getSelectQuery(TABLE_NAME)+" limit "+limit+" offset "+offset;
			}

			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				String productId = resultSet.getString(PRODUCT_ID_COL);
				List<String > imageList = getProductImages(productId);
				List<Review> reviews = ReviewDao.getReviews(productId);

				productList.add(new Product(
						productId,
						resultSet.getString(PRODUCT_NAME_COL),
						resultSet.getString(PRODUCT_DESCRIPTION_COL),
						resultSet.getInt(PRODUCT_PRICE_COL),
						resultSet.getString(CATEGORY_COL),
						resultSet.getString(BRAND_COL),
						resultSet.getDouble(DISCOUNT_COL),
						resultSet.getString(WARRANTY_COL),
						resultSet.getDouble(RATING_COL),
						resultSet.getInt(RATING_COUNT_COL),
						resultSet.getInt(PRODUCT_STOCK_COL),
						resultSet.getString(PRODUCT_IMAGE_COL),
						imageList,
						reviews,
						resultSet.getString("createdAt")
				));
			}
			return productList;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	private static List<String> getProductImages(String productId) throws  ClassNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(CreateQuery.getSelectQuery(IMAGE_TABLE_NAME, PRODUCT_ID_COL));
			preparedStatement.setString(1, productId);
			ResultSet imageResultSet = preparedStatement.executeQuery();
			List<String > imageList = new ArrayList<>();
			while(imageResultSet.next()) {
				imageList.add(imageResultSet.getString(IMAGE_URL_COL));
			}
			return imageList;
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
	private static boolean addImages(String productId, List<String> images) throws ClassNotFoundException, SQLException{
		Connection connection = null;
		Statement imagePs  = null;
		try {
			connection = JDBCUtil.getConnection();
			StringBuilder imageQuery = new StringBuilder("insert into ProductImages (productId,imageUrl) values");
			for(int i = 0;i<images.size();i++) {
				imageQuery.append('(');
				imageQuery.append("'").append(productId).append("'");
				imageQuery.append(',');
				imageQuery.append("'").append(images.get(i)).append("'");
				imageQuery.append(')');
				if(i != images.size()-1) {
					imageQuery.append(",");
				}
			}
			imagePs = connection.createStatement();
			imagePs.execute(imageQuery.toString());
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(imagePs != null){
				imagePs.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}
	public static boolean updateProductRating(String productId, double rating) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			String query  = "select ratingCount, rating from "+TABLE_NAME + " where productId = "+productId;
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				double productRating = resultSet.getDouble("rating");
				int ratingCount = resultSet.getInt("ratingCount");

				double totalTemp = productRating * ratingCount;
				double totalSum = totalTemp + rating;
				double newRating = totalSum / ratingCount+1;
				String ratingQuery = "update "+TABLE_NAME+" set rating=?, ratingCount=? where productId=? ";
				PreparedStatement ratingPs = connection.prepareStatement(ratingQuery);
				ratingPs.setDouble(1, newRating);
				ratingPs.setInt(2, ratingCount+1);
				ratingPs.executeUpdate();
				return true;
			}
			throw new SQLException("Product id not found");
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
		
	}
	public static  Map<Object, Object> getProductsByCategory(int limit, int offset, String category) throws ClassNotFoundException, SQLException {
		String query = "select *, count(*) over() as count from "+TABLE_NAME+" where category = '"+category+"' limit "+limit+" offset "+offset;
		return getAllProductsWithQuery(query, limit, offset);
	}

	public static Map<Object, Object> getAllProductsWithQuery(String query, int limit, int offset) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			List<Product> productList = new ArrayList<>();
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			int totalCount = 0;
			while(resultSet.next()) {
				if(totalCount == 0){
					totalCount = resultSet.getInt("count");
				}
				String productId = resultSet.getString(PRODUCT_ID_COL);
				List<String > imageList = getProductImages(productId);
				List<Review> reviews = ReviewDao.getReviews(productId);

				productList.add(new Product(
						productId,
						resultSet.getString(PRODUCT_NAME_COL),
						resultSet.getString(PRODUCT_DESCRIPTION_COL),
						resultSet.getInt(PRODUCT_PRICE_COL),
						resultSet.getString(CATEGORY_COL),
						resultSet.getString(BRAND_COL),
						resultSet.getDouble(DISCOUNT_COL),
						resultSet.getString(WARRANTY_COL),
						resultSet.getDouble(RATING_COL),
						resultSet.getInt(RATING_COUNT_COL),
						resultSet.getInt(PRODUCT_STOCK_COL),
						resultSet.getString(PRODUCT_IMAGE_COL),
						imageList,
						reviews,
						resultSet.getString("createdAt")
				));
			}
			Map<Object, Object> map = new HashMap<>();
			map.put("totalCount", totalCount);
			map.put("products", productList);
			map.put("limit", limit);
			map.put("offset", offset);
			boolean hasNext = false;
			if(offset + limit < totalCount) {
				hasNext = true;
			}
			map.put("hasNext", hasNext);
			return map;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}finally {
			if(resultSet != null){
				resultSet.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
			if(connection != null) {
				connection.close();
			}
		}
	}
}
