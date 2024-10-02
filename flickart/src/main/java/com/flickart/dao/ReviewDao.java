package com.flickart.dao;
import com.flickart.model.Review;
import com.flickart.util.CreateQuery;
import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private static  String TABLE_NAME = "Reviews";
    private static  String REVIEW_ID_COL = "reviewId";
    private static  String USER_ID_COL = "userId";
    private static  String PRODUCT_ID_COL = "productId";
    private static  String RATING_COL = "rating";
    private static  String COMMENT_COL = "comment";
    private static  String TIMESTAMPS_COL = "timestamp";


    public static List<Review> getReviews(String productId) throws SQLException, ClassNotFoundException {
        List<Review> reviews = new ArrayList<Review>();
        Connection connection = JDBCUtil.getConnection();
        String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, productId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            reviews.add(new Review(rs.getString(REVIEW_ID_COL), rs.getString(USER_ID_COL), rs.getString(PRODUCT_ID_COL) ,rs.getFloat(RATING_COL), rs.getString(COMMENT_COL), rs.getString(TIMESTAMPS_COL)));
        }
        return reviews;
    }
    public static boolean addReview(Review review) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCUtil.getConnection();
        PreparedStatement ps = connection.prepareStatement(CreateQuery.getInsertQuery(TABLE_NAME, REVIEW_ID_COL, USER_ID_COL, PRODUCT_ID_COL, RATING_COL, COMMENT_COL));
        String reviewId = UniqueId.getUniqueId();
        ps.setString(1, reviewId);
        ps.setString(2, review.getUserId());
        ps.setString(3, review.getProductId());
        ps.setFloat(4, review.getRating());
        ps.setString(5, review.getComment());
        ps.executeUpdate();
        return true;
    }
}
