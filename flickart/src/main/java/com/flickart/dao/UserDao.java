package com.flickart.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flickart.model.Cart;
import com.flickart.model.User;
import com.flickart.util.CreateQuery;
import com.flickart.util.HashPassword;
import com.flickart.util.JDBCUtil;
import com.flickart.util.UniqueId;
import org.mindrot.jbcrypt.BCrypt;

public class UserDao {
    private static final String TABLE_NAME = "Users";
    private static final String USER_ID_COL = "userId";
    private static final String USER_NAME_COL = "userName";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "password";
    private static final String PROFILE_PHOTO_COL = "profilePhoto";
    private static final String DEFAULT_PHOTO = "https://icons.veryicon.com/png/o/miscellaneous/rookie-official-icon-gallery/225-default-avatar.png";
   

    public static User createUser(String userName, String email, String password, String profilePhoto) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            if(!validateColumn(EMAIL_COL, email)) {
                throw new Exception("Email already exists");
            }
            String userIdString = UniqueId.getUniqueId();
            String hashedPassword = HashPassword.hashPassword(password);

            String query = CreateQuery.getInsertQuery(TABLE_NAME, USER_ID_COL, USER_NAME_COL, EMAIL_COL, PASSWORD_COL, PROFILE_PHOTO_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userIdString);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setString(5, profilePhoto);
            preparedStatement.executeUpdate();
            Cart cart = CartDao.createCart(userIdString);

            return new User(
                    userIdString,
                    userName,
                    email,
                    password,
                    profilePhoto,
                    cart
            );
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
    public static User createUser(String userName, String email, String password) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            if(!validateColumn(EMAIL_COL, email)) {
                throw new Exception("Email already exists");
            }
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            String userIdString = UniqueId.getUniqueId();
            String query = CreateQuery.getInsertQuery(TABLE_NAME, USER_ID_COL, USER_NAME_COL, EMAIL_COL, PASSWORD_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userIdString);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.executeUpdate();
            Cart cart = CartDao.createCart(userIdString);
            return new  User(
                    userIdString,
                    userName,
                    email,
                    password,
                    DEFAULT_PHOTO,
                    cart
            );
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


    public static User validateUser(String email, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = CreateQuery.getSelectQuery(TABLE_NAME, EMAIL_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString(PASSWORD_COL);
                if(HashPassword.validatePassword(password, storedHashedPassword)){
                    String userId = resultSet.getString("userId");
                    Cart cart = CartDao.getCart(connection, userId);
                    return new User(userId, resultSet.getString("userName"), email, resultSet.getString("password"), resultSet.getString("profilePhoto"), cart);
                }
            }
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        finally {
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

    public static User getUserById(String userId) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String query = CreateQuery.getSelectQuery(TABLE_NAME, USER_ID_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
//            Cart cart = CartDao.getCart(connection, userId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return new User(
                        resultSet.getString(USER_ID_COL),
                        resultSet.getString(USER_NAME_COL),
                        resultSet.getString(EMAIL_COL),
                        null,
                        resultSet.getString(PROFILE_PHOTO_COL),
                        null
                );
            }
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
        finally {
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

    public static User getUserByEmail(String email) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            String query = CreateQuery.getSelectQuery(TABLE_NAME, EMAIL_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Cart cart = CartDao.getCart(connection , resultSet.getString(USER_ID_COL));
                return new User(
                        resultSet.getString(USER_ID_COL),
                        resultSet.getString(USER_NAME_COL),
                        resultSet.getString(EMAIL_COL),
                        "",
                        resultSet.getString(PROFILE_PHOTO_COL),
                        cart
                );
            }
            return null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        finally {
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

    public static boolean updateUserProfilePhoto(String userId, String profilePhoto) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            String query = CreateQuery.getUpdateQuery(TABLE_NAME, PROFILE_PHOTO_COL, USER_ID_COL);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, profilePhoto);
            preparedStatement.setString(2, userId);
            preparedStatement.executeUpdate();

            return true;
        }
        catch (SQLException e) {
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
    private static boolean validateColumn(String colName, String value)throws ClassNotFoundException, SQLException  {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            String query = CreateQuery.getSelectQuery(TABLE_NAME, colName);
            connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();

            return !resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }

        finally {
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
