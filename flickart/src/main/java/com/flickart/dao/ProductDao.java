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

/**

 | brand              | varchar(50)   | YES  |     | NULL                |       |
 | discount           | decimal(5,3)  | YES  |     | NULL                |       |
 | warranty           | varchar(50)   | YES  |     | NULL                |       |
 | rating             | decimal(3,2)  | YES  |     | NULL                |       |
 | ratingCount        | int(11)       | YES  |     | NULL                |       |
 +--------------------+---------------+------+-----+---------------------+-------+
 */
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

		Connection con = JDBCUtil.getConnection();
		PreparedStatement productPs = con.prepareStatement(productQuery);

		String uniqueProductId = UniqueId.getUniqueId();
		productPs.setString(1, uniqueProductId);
		productPs.setString(2,  product.getProductName());
		productPs.setString(3,  product.getProductDescription());
		productPs.setDouble(4,  product.getPrice());
		productPs.setInt(5,  product.getStockCount());
		productPs.setString(6,  product.getImage());
		productPs.setString(7,  product.getCategory());
		productPs.setString(8,  product.getBrand());
		productPs.setDouble(9,  product.getDiscount());
		productPs.setString(10,  product.getWarranty());
		productPs.setDouble(11,  product.getRating());
		productPs.setInt(12,  product.getRatingCount());

		productPs.executeUpdate();
		addImages(uniqueProductId, product.getImages());
		return true;
	}
	public static boolean updateProductCount(String productId, int updateCount) throws SQLException , ClassNotFoundException {
		String query = CreateQuery.getUpdateQuery(TABLE_NAME, PRODUCT_ID_COL ,PRODUCT_STOCK_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		int prevCount = getProductStock(productId);
		if(prevCount < updateCount) {
			throw new SQLException("Given count greater than available count.");
		}
		ps.setInt(1, prevCount + updateCount);
		
		ps.setString(2, productId);
		
		return true;
	}
	public static boolean updateProduct(String productId, Product product) throws Exception {
		// add id at last

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
		Connection con = JDBCUtil.getConnection();
		PreparedStatement productPs = con.prepareStatement(query);

		productPs.setString(1,  product.getProductName());
		productPs.setString(2,  product.getProductDescription());
		productPs.setDouble(3,  product.getPrice());
		productPs.setInt(4,  product.getStockCount());
		productPs.setString(5,  product.getImage());
		productPs.setString(6,  product.getCategory());
		productPs.setString(7,  product.getBrand());
		productPs.setDouble(8,  product.getDiscount());
		productPs.setString(9,  product.getWarranty());
		productPs.setDouble(10,  product.getRating());
		productPs.setInt(11,  product.getRatingCount());


		productPs.setString(12, productId);
		productPs.executeUpdate();
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
	public static boolean deleteProduct(String productId) throws  ClassNotFoundException, SQLException{
		String query = "delete from "+TABLE_NAME+" where productId='"+productId+"'";
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.executeUpdate();
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

	public static int getProductsCount(String searchString) throws ClassNotFoundException, SQLException{
		String query = "";
		if(searchString != null && !searchString.isEmpty()){
			query = "select count(productId) as count from "+TABLE_NAME+" where lower(productName) like '%"+searchString+"%' or productDescription like '%"+searchString+"%'";
		}else{
			query = "select count(productId) as count from "+TABLE_NAME;
		}

		Connection con = JDBCUtil.getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(query);
		rs.next();

		return rs.getInt("count");
	}
	public  static  Product getProduct(String productId) throws Exception {
		String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, productId);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()){
			throw new Exception("Invalid product id");
		}

		List<Review> reviews = ReviewDao.getReviews(productId);
		List<String > imageList = getProductImages(productId);
		return new Product(
				productId,
				rs.getString(PRODUCT_NAME_COL),
				rs.getString(PRODUCT_DESCRIPTION_COL),
				rs.getInt(PRODUCT_PRICE_COL),
				rs.getString(CATEGORY_COL),
				rs.getString(BRAND_COL),
				rs.getDouble(DISCOUNT_COL),
				rs.getString(WARRANTY_COL),
				rs.getDouble(RATING_COL),
				rs.getInt(RATING_COUNT_COL),
				rs.getInt(PRODUCT_STOCK_COL),
				rs.getString(PRODUCT_IMAGE_COL),
				imageList,
				reviews,
				rs.getString("createdAt")
		);
	}
	public static Map<Object, Object> getAllProducts(int limit, int offset, String searchString) throws  ClassNotFoundException, SQLException{
		List<Product> productList = new ArrayList<>();
		String query = "";
		if( searchString == null || searchString.isEmpty()){
			query = "select *, count(*) over() as count from "+TABLE_NAME+" limit "+limit+" offset "+offset;
		}else {
			searchString = searchString.toLowerCase();
			query = "select *, count(*) over() as count from "+TABLE_NAME+" where lower(productName) like '%"+searchString+"%' or productDescription like '%"+searchString+"%'  limit "+limit+" offset "+offset;
		}

		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		int totalCount = 0;
		while(rs.next()) {
			if(totalCount == 0){
				totalCount = rs.getInt("count");
			}
			String productId = rs.getString(PRODUCT_ID_COL);
			List<String > imageList = getProductImages(productId);
			List<Review> reviews = ReviewDao.getReviews(productId);
			productList.add(new Product(
					productId,
					rs.getString(PRODUCT_NAME_COL),
					rs.getString(PRODUCT_DESCRIPTION_COL),
					rs.getInt(PRODUCT_PRICE_COL),
					rs.getString(CATEGORY_COL),
					rs.getString(BRAND_COL),
					rs.getDouble(DISCOUNT_COL),
					rs.getString(WARRANTY_COL),
					rs.getDouble(RATING_COL),
					rs.getInt(RATING_COUNT_COL),
					rs.getInt(PRODUCT_STOCK_COL),
					rs.getString(PRODUCT_IMAGE_COL),
					imageList,
					reviews,
					rs.getString("createdAt")
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
	}
	public  static  Product getProductAdmin(String productId) throws SQLException, ClassNotFoundException {
		String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, productId);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()){
			throw new SQLException("Invalid product id");
		}
		List<String > imageList = getProductImages(productId);
		List<Review> reviews = ReviewDao.getReviews(productId);

		return new Product(
				productId,
				rs.getString(PRODUCT_NAME_COL),
				rs.getString(PRODUCT_DESCRIPTION_COL),
				rs.getInt(PRODUCT_PRICE_COL),
				rs.getString(CATEGORY_COL),
				rs.getString(BRAND_COL),
				rs.getDouble(DISCOUNT_COL),
				rs.getString(WARRANTY_COL),
				rs.getDouble(RATING_COL),
				rs.getInt(RATING_COUNT_COL),
				rs.getInt(PRODUCT_STOCK_COL),
				rs.getString(PRODUCT_IMAGE_COL),
				imageList,
				reviews,
				rs.getString("createdAt")
		);
	}
	public static List<Product> getAllProductsAdmin(int limit, int offset, String searchString) throws  ClassNotFoundException, SQLException{
		List<Product> productList = new ArrayList<>();

		String query = "";
		if(searchString != null && !searchString.isEmpty()){
			searchString = searchString.toLowerCase();
			query = CreateQuery.getSelectQuery(TABLE_NAME)+" where lower(productName) like '%"+searchString+"%' or productDescription like '%"+searchString+"%'  limit "+limit+" offset "+offset;
		}else {
			query = CreateQuery.getSelectQuery(TABLE_NAME)+" limit "+limit+" offset "+offset;
		}

		Connection con = JDBCUtil.getConnection();
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String productId = rs.getString(PRODUCT_ID_COL);
			List<String > imageList = getProductImages(productId);
			List<Review> reviews = ReviewDao.getReviews(productId);

			productList.add(new Product(
					productId,
					rs.getString(PRODUCT_NAME_COL),
					rs.getString(PRODUCT_DESCRIPTION_COL),
					rs.getInt(PRODUCT_PRICE_COL),
					rs.getString(CATEGORY_COL),
					rs.getString(BRAND_COL),
					rs.getDouble(DISCOUNT_COL),
					rs.getString(WARRANTY_COL),
					rs.getDouble(RATING_COL),
					rs.getInt(RATING_COUNT_COL),
					rs.getInt(PRODUCT_STOCK_COL),
					rs.getString(PRODUCT_IMAGE_COL),
					imageList,
					reviews,
					rs.getString("createdAt")
			));
		}
		return productList;
	}
	private static List<String> getProductImages(String productId) throws  ClassNotFoundException, SQLException{
		Connection con = JDBCUtil.getConnection();
		PreparedStatement imagePs = con.prepareStatement(CreateQuery.getSelectQuery(IMAGE_TABLE_NAME, PRODUCT_ID_COL));
		imagePs.setString(1, productId);
		ResultSet imageResultSet = imagePs.executeQuery();
		List<String > imageList = new ArrayList<>();
		while(imageResultSet.next()) {
			imageList.add(imageResultSet.getString(IMAGE_URL_COL));
		}
		return imageList;
	}
	private static boolean addImages(String productId, List<String> images) throws ClassNotFoundException, SQLException{
		Connection con = JDBCUtil.getConnection();
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
		Statement imagePs = con.createStatement();
		imagePs.execute(imageQuery.toString());
		return true;
	}
	public static boolean updateProductRating(String productId, double rating) throws ClassNotFoundException, SQLException {
		String query  = "select ratingCount, rating from "+TABLE_NAME + " where productId = "+productId;
		Connection connection = JDBCUtil.getConnection();
		PreparedStatement ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			double productRating = rs.getDouble("rating");
			int ratingCount = rs.getInt("ratingCount");

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
	}
	public static  List<Product> getProductsByCategory(String category, int limit,int offset) throws ClassNotFoundException, SQLException {
		Connection con = JDBCUtil.getConnection();
		List<Product> productList = new ArrayList<>();
		String query = CreateQuery.getSelectQuery(TABLE_NAME, CATEGORY_COL)+" limit "+limit+" offset "+offset;
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, category);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String productId = rs.getString(PRODUCT_ID_COL);
			List<String > imageList = getProductImages(productId);
			List<Review> reviews = ReviewDao.getReviews(productId);
			productList.add(new Product(
					productId,
					rs.getString(PRODUCT_NAME_COL),
					rs.getString(PRODUCT_DESCRIPTION_COL),
					rs.getInt(PRODUCT_PRICE_COL),
					rs.getString(CATEGORY_COL),
					rs.getString(BRAND_COL),
					rs.getDouble(DISCOUNT_COL),
					rs.getString(WARRANTY_COL),
					rs.getDouble(RATING_COL),
					rs.getInt(RATING_COUNT_COL),
					rs.getInt(PRODUCT_STOCK_COL),
					rs.getString(PRODUCT_IMAGE_COL),
					imageList,
					reviews,
					rs.getString("createdAt")
			));
		}
		return productList;
	}
}
