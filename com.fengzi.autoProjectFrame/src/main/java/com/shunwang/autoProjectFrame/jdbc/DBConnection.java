package com.shunwang.autoProjectFrame.jdbc;

import com.shunwang.autoProjectFrame.constrain.Field;
import com.shunwang.autoProjectFrame.exception.DBConnectException;
import com.shunwang.autoProjectFrame.util.JdbcTypeNameTranslator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据库连接及数据库中表信息的类
 *
 * @author Administrator
 *
 */
public class DBConnection {

	/**
	 * 通过一个数据库通行证获取数据库连接
	 *
	 * @param passport
	 * @return
	 * @throws DBConnectException
	 */
	public static Connection getConnection(DBPassport passport)
			throws DBConnectException {
		Connection conn = null;
		try {
			Class.forName(passport.getDriver());
			conn = DriverManager.getConnection(passport.getUrl(), passport
					.getUsername(), passport.getPassword());
		} catch (ClassNotFoundException e) {
			throw new DBConnectException("数据库连接失败！");
		} catch (SQLException e) {
			throw new DBConnectException("数据库连接失败！");
		}
		return conn;
	}

	/**
	 * 关闭连接
	 *
	 * @param conn
	 */
	public static void colseConnection(Connection conn, Statement st,
			ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (st != null) {
				st.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取数据库中所有的表名
	 *
	 * @param passport
	 * @return
	 * @throws DBConnectException
	 */
	public static List<String> getAllTableNames(DBPassport passport)
			throws DBConnectException {
//		String sql = "select s.name as schemaName, t.name as tableName from sys.tables as t, sys.schemas as s"
//				+ " where t.schema_id = s.schema_id order by schemaName,tableName";
		String sql="select s.SCHEMA_NAME  as schemaName, t.table_name as tableName  " +
				"from information_schema.tables as t, INFORMATION_SCHEMA.SCHEMATA as s where t.table_schema = s.schema_name order by schemaName,tableName";
		List<String> listTableName = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection(passport);
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			while (rs.next()) {
				String tableName = rs.getString(1) + "." + rs.getString(2);
				listTableName.add(tableName);
			}
		} catch (Exception e1) {
			throw new DBConnectException("数据库连接失败！");
		} finally {
			colseConnection(conn, statement, rs);
		}
		return listTableName;
	}

	/**
	 * 获取数据库中指定表的结构
	 *
	 * @param passport
	 * @param table
	 * @return
	 * @throws DBConnectException
	 */
	public static List<Field> getTableStructure(DBPassport passport,
			String table) throws DBConnectException {
		String[] array = table.split("\\.");
//		table = "[" + array[0] + "].[" + array[1] + "]";
		table = array[0] + "." + array[1] ;
		String sql = "select * from " + table;
		List<Field> list = new ArrayList<Field>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = getConnection(passport);
			statement = conn.prepareStatement(sql);
			rs = statement.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				Field field = new Field();
				String columnName = metaData.getColumnName(i);
				String jdbcType = JdbcTypeNameTranslator.getJdbcTypeName(metaData.getColumnType(i));
				String javaType = metaData.getColumnClassName(i);
				if ("java.math.BigDecimal".equals(javaType)) {
					javaType = "java.lang.Double";
				}
				boolean autoIncrement = metaData.isAutoIncrement(i);
				field.setFieldName(columnName);
				field.setJavaType(javaType);
				field.setJdbcType(jdbcType);
				field.setAutoIncrement(autoIncrement);
				list.add(field);
			}
		} catch (Exception e1) {
			throw new DBConnectException("数据库连接异常！");
		} finally {
			colseConnection(conn, statement, rs);
		}
		return list;
	}

	/**
	 * 获取指定数据库中指定表的主键
	 *
	 * @param passport
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public static List<String> getPrimaryKeys(DBPassport passport, String table)
			throws DBConnectException {
		Connection conn = null;
		ResultSet rs = null;
		try {
			String[] array = table.split("\\.");
			List<String> list = new ArrayList<String>();
			conn = getConnection(passport);
			DatabaseMetaData dmd = conn.getMetaData();
			rs = dmd.getPrimaryKeys(null, array[0], array[1]);
			while (rs.next()) {
				list.add(rs.getString("COLUMN_NAME"));
			}
			return list;
		} catch (Exception ex) {
			throw new DBConnectException("数据库连接异常！");
		} finally {
			colseConnection(conn, null, rs);
		}

	}

	public static void main(String[] args) {
		DBPassport passport = new DBPassport();
		passport.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		passport.setIp("localhost");
		passport.setPort("1433");
		passport.setDbName("demo");
		passport.setUsername("sa");
		passport.setPassword("111");
		try {
			System.out.println(DBConnection.getAllTableNames(passport));
			System.out.println(DBConnection.getTableStructure(passport,
					"dbo.car"));
			System.out
					.println(DBConnection.getPrimaryKeys(passport, "dbo.Car"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
