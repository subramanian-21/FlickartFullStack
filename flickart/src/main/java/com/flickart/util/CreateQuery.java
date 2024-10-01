package com.flickart.util;
public class CreateQuery {
	public static String getSelectQuery(String tableName, String... columns) {
		StringBuilder query = new StringBuilder("select * from ");
		query.append(tableName);
		if(columns.length == 0) {
			return query.toString();
		}

		query.append(" where");
		for(int i = 0;i<columns.length;i++) {
			query.append(" ").append(columns[i]).append("=?");
			if(i != columns.length-1) {
				query.append(" and ");
			}
		}
		
		return query.toString();
	}
	public static String getInsertQuery(String tableName, String... columns) {

		StringBuilder query = new StringBuilder("insert into ");
		query.append(tableName);
		query.append(" ");
		
		query.append("(");
		for(int i = 0;i<columns.length;i++) {
			query.append(columns[i]);
			if(i != columns.length-1) {
				query.append(", ");
			}
		}
		query.append(")");
		query.append(" ");
		query.append("values");
		query.append("(");
		for(int i = 0;i<columns.length;i++) {
			query.append(" ? ");
			if(i != columns.length-1) {
				query.append(", ");
			}
		}
		query.append(")");
		return query.toString();
	}
	
	public static String getUpdateQuery(String tableName, String primaryIdCol, String... setColumn) {

		StringBuilder query = new StringBuilder("update ");
		query.append(tableName).append(" set ");
		for(int i = 0;i<setColumn.length;i++) {
			query.append(setColumn[i]).append("=? ");
			if(i != setColumn.length-1) {
				query.append(",");
			}
		}
		query.append(" where ").append(primaryIdCol).append("=?");
		
		return query.toString();
	}
}
