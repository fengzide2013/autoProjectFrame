package com.shunwang.autoProjectFrame.documentProcess;

import com.shunwang.autoProjectFrame.jdbc.DBPassport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * 解析和保存数据库连接信息文件的类
 *
 * @author Administrator
 *
 */
public class PassportTextFileParse {
	// 保存数据库信息文件的路径
	private static String filePath = System.getProperty("java.io.tmpdir")
			+ "dbPassport.txt";

	/**
	 * 解析数据库连接信息文件，获取所有的数据库来接信息（通行证）
	 *
	 * @return
	 */
	public static List<DBPassport> getDBpassports() {
		List<DBPassport> listPassports = new ArrayList<DBPassport>();
		if (!new File(PassportTextFileParse.filePath).exists())
			return listPassports;
		String line = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(PassportTextFileParse.filePath)));
			line = reader.readLine();
			while (line != null) {
				DBPassport passport = new DBPassport();
				passport.setIp(line);
				line = reader.readLine();
				if (line == null)
					break;
				passport.setPort(line);
				line = reader.readLine();
				passport.setDbName(line);
				line = reader.readLine();
				passport.setUsername(line);
				line = reader.readLine();
				passport.setPassword(line);
				listPassports.add(passport);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listPassports;
	}

	/**
	 * 重新生成文件，保存所有的数据库连接信息，覆盖原来的文件
	 *
	 * @param listPassports
	 */
	public static void saveDBPassports(List<DBPassport> listPassports) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(
					new FileOutputStream(PassportTextFileParse.filePath)), true);
			for (DBPassport passport : listPassports) {
				writer.println(passport.getIp());
				writer.println(passport.getPort());
				writer.println(passport.getDbName());
				writer.println(passport.getUsername());
				writer.println(passport.getPassword());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(PassportTextFileParse.filePath);
		List<DBPassport> listPassports = getDBpassports();
		saveDBPassports(listPassports);
	}
}
