package com.shunwang.autoProjectFrame.exception;

/**
 * 数据库异常类
 *
 * @author Administrator
 */
public class DBConnectException extends Exception {

	public DBConnectException() {
		super();
	}

	public DBConnectException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBConnectException(String message) {
		super(message);
	}

	public DBConnectException(Throwable cause) {
		super(cause);
	}

}
