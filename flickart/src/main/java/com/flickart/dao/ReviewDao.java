package com.flickart.dao;
import com.flickart.model.Review;
import com.flickart.model.User;
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
    private static  String USER_NAME_COL = "userName";
    private static  String PRODUCT_ID_COL = "productId";
    private static  String RATING_COL = "rating";
    private static  String COMMENT_COL = "comment";
    private static  String TIMESTAMPS_COL = "timestamp";

    public static List<Review> getReviews(Connection connection, String productId) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            List<Review> reviews = new ArrayList<Review>();
            String query = CreateQuery.getSelectQuery(TABLE_NAME, PRODUCT_ID_COL);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = UserDao.getUserById(resultSet.getString(USER_ID_COL));
                Review review = new Review(resultSet.getString(REVIEW_ID_COL), resultSet.getString(USER_ID_COL),user, resultSet.getString(PRODUCT_ID_COL) ,resultSet.getFloat(RATING_COL), resultSet.getString(COMMENT_COL), resultSet.getString(TIMESTAMPS_COL));
                reviews.add(review);
            }
            return reviews;
        }
        catch (SQLException e) {
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
    public static boolean addReview(Review review) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(CreateQuery.getInsertQuery(TABLE_NAME, REVIEW_ID_COL, USER_ID_COL, PRODUCT_ID_COL, RATING_COL, COMMENT_COL));
            String reviewId = UniqueId.getUniqueId();
            preparedStatement.setString(1, reviewId);
            preparedStatement.setString(2, review.getUserId());
            preparedStatement.setString(3, review.getProductId());
            preparedStatement.setFloat(4, review.getRating());
            preparedStatement.setString(5, review.getComment());
            preparedStatement.executeUpdate();
            ProductDao.updateProductRating(review.getProductId(), review.getRating());
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
       finally{
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(connection != null) {
                connection.close();
            }
        }

    }
}
