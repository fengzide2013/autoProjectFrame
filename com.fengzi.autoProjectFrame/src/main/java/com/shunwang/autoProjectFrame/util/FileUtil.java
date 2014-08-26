/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.shunwang.autoProjectFrame.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class FileUtil {
	public static void createDir(String directory) {
		File file = new File(directory);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void createFile(String filePath, List<String> fileContent) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
					filePath)), true);
			for (String str : fileContent) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			InputStream fis = ClassLoader.getSystemResourceAsStream(oldPath);
			FileOutputStream fos = new FileOutputStream(newPath);
			byte[] buffer = new byte[1024];
			while ((byteread = fis.read(buffer)) != -1) {
				fos.write(buffer, 0, byteread);
			}
			fis.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
