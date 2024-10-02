package com.flickart.dao;

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
    	if(!validateColumn(EMAIL_COL, email)) {
    		throw new Exception("Username already exists");
    	}
    	String userIdString = UniqueId.getUniqueId();
        String hashedPassword = HashPassword.hashPassword(password);
        
        String query = CreateQuery.getInsertQuery(TABLE_NAME, USER_ID_COL, USER_NAME_COL, EMAIL_COL, PASSWORD_COL, PROFILE_PHOTO_COL);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, userIdString);
        ps.setString(2, userName);
        ps.setString(3, email);
        ps.setString(4, hashedPassword); 
        ps.setString(5, profilePhoto);
        ps.executeUpdate();
        Cart cart = CartDao.createCart(userIdString);

        return new  User(
        		userIdString,
        		userName,
        		email,
        		password,
        		profilePhoto,
                cart
        		);
    }
    public static User createUser(String userName, String email, String password) throws ClassNotFoundException, SQLException {
    	if(!validateColumn(EMAIL_COL, email)) {
    		return null;
    	}
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String userIdString = UniqueId.getUniqueId();
        String query = CreateQuery.getInsertQuery(TABLE_NAME, USER_ID_COL, USER_NAME_COL, EMAIL_COL, PASSWORD_COL);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, userIdString);
        ps.setString(2, userName);
        ps.setString(3, email);
        ps.setString(4, hashedPassword); 
        ps.executeUpdate();
        Cart cart = CartDao.createCart(userIdString);
        return new  User(
        		userIdString,
        		userName,
        		email,
        		password,
        		DEFAULT_PHOTO,
                cart
        		);
    }


    public static User validateUser(String email, String password) throws ClassNotFoundException, SQLException {
        String query = CreateQuery.getSelectQuery(TABLE_NAME, EMAIL_COL);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, email);
        
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String storedHashedPassword = rs.getString(PASSWORD_COL);
            if(HashPassword.validatePassword(password, storedHashedPassword)){
                String userId = rs.getString("userId");
                Cart cart = CartDao.getCart(userId);
                return new User(userId, rs.getString("userName"), rs.getString("email"), rs.getString("password"), rs.getString("profilePhoto"), cart);
            }

        }
        return null;
    }

//    public static User getUserById(String userId) throws ClassNotFoundException, SQLException {
//        String query = CreateQuery.getSelectQuery(TABLE_NAME, USER_ID_COL);
//        Connection con = JDBCUtil.getConnection();
//        PreparedStatement ps = con.prepareStatement(query);
//        ps.setString(1, userId);
//        Cart cart = CartDao.getCart(userId);
//        ResultSet rs = ps.executeQuery();
//        if(rs.next()) {
//            return new User(
//                rs.getString(USER_ID_COL),
//                rs.getString(USER_NAME_COL),
//                rs.getString(EMAIL_COL),
//                "",
//                rs.getString(PROFILE_PHOTO_COL),
//                    cart
//            );
//        }
//        return null;
//    }

    public static User getUserByEmail(String email) throws ClassNotFoundException, SQLException {
        String query = CreateQuery.getSelectQuery(TABLE_NAME, EMAIL_COL);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            Cart cart = CartDao.getCart(rs.getString(USER_ID_COL));
            return new User(
                    rs.getString(USER_ID_COL),
                    rs.getString(USER_NAME_COL),
                    rs.getString(EMAIL_COL),
                    "",
                    rs.getString(PROFILE_PHOTO_COL),
                    cart
            );
        }
        return null;
    }

    public static boolean updateUserProfilePhoto(String userId, String profilePhoto) throws ClassNotFoundException, SQLException {
        String query = CreateQuery.getUpdateQuery(TABLE_NAME, PROFILE_PHOTO_COL, USER_ID_COL);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, profilePhoto);
        ps.setString(2, userId);
        ps.executeUpdate();

        return true;
    }
    private static boolean validateColumn(String colName, String value)throws ClassNotFoundException, SQLException  {
    	String query = CreateQuery.getSelectQuery(TABLE_NAME, colName);
        Connection con = JDBCUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, value);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }
}
