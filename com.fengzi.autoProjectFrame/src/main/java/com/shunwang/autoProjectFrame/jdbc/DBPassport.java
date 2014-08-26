package com.shunwang.autoProjectFrame.jdbc;

/**
 * 封装连接数据库信息的类（通行证）
 *
 * @author Administrator
 *
 */
public class DBPassport {
//	private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private String driver = "com.mysql.jdbc.Driver";
	private String ip;
	private String port;
	private String dbName;
	private String username;
	private String password = "";
	private boolean isSavePassword = true;

	public boolean isSavePassword() {
		return isSavePassword;
	}

	public void setSavePassword(boolean isSavePassword) {
		this.isSavePassword = isSavePassword;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
//		return "jdbc:sqlserver://" + ip + ":" + port + ";databaseName="
//				+ dbName;
		return "jdbc:mysql://" + ip + ":" + port + "/"+ dbName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof DBPassport))
			return false;
		DBPassport another = (DBPassport) obj;
		if (ip.equals(another.getIp()) && dbName.equals(another.getDbName())
				&& username.equals(another.getUsername()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return ip + "&" + port + "&" + dbName + "&" + username + "&"
				+ (isSavePassword ? password : "");
	}

}
