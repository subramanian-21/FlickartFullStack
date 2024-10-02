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
	public static void main(String[] args) {
		System.out.println("password".hashCode());
	}
	public static AdminUser validateUser(String email, String password) throws ClassNotFoundException, SQLException{
		Connection con = JDBCUtil.getConnection();
		 PreparedStatement ps = con.prepareStatement(CreateQuery.getSelectQuery(TABLE_NAME, "email", "password"));
		
		ps.setString(1, email);
		ps.setString(2, HashPassword.hashPassword(password));
		System.out.println(HashPassword.hashPassword(password));
		ResultSet rs= ps.executeQuery();
		if(rs.next()) {
			return new AdminUser(rs.getString("username"),rs.getString("email"), rs.getString("password"), rs.getString("role"));
		}
		return null;
	}
}
