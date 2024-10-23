package com.flickart.dao;

import com.flickart.model.AdminUser;
import com.flickart.util.CreateQuery;
import com.flickart.util.HashPassword;
import com.flickart.util.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUserDao {
	private static final String TABLE_NAME = "AdminUser";
	public static AdminUser validateUser(String email, String password) throws ClassNotFoundException, SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME, "email", "password"));

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, HashPassword.hashPassword(password));
			ResultSet rs= preparedStatement.executeQuery();
			if(rs.next()) {
				return new AdminUser(rs.getString("username"),rs.getString("email"), rs.getString("password"), rs.getString("role"));
			}
			return null;
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
}
