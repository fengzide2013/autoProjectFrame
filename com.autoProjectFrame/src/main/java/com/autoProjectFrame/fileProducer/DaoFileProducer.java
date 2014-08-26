package com.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.autoProjectFrame.constrain.Table;

public class DaoFileProducer extends FileProducer {

	public DaoFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public String createFilePath() {
		return directory +"\\"+ table.getClassName() + "Dao.java";
	}

	@Override
	public List<String> createFileContent() {
		List<String> list = new ArrayList<String>();
		list.add(createPackageStr());
		list.addAll(createImportStr());
		list.add(createHeader());
		list.add("\n");
		list.add(new String("}"));
		return list;
	}

	/**
	 * 创建类的包信息
	 *
	 * @return
	 */
	private String createPackageStr() {
		return "package " + table.getPackageName() + ".dao;\n";
	}

	/**
	 * 创建类的导入信息
	 *
	 * @return
	 */
	private Set<String> createImportStr() {
		Set<String> importSet = new HashSet<String>();
		String extendsClass = table.getExtendsClass();
		if ("BasePojo".equals(extendsClass)) {
			importSet.add("import "  + table.getPackageName() + ".pojo."
					+ table.getClassName() + ";");
			importSet
					.add("import com.tingwei.framework.dao.CrudDao;\n");
		}
		return importSet;
	}

	/**
	 * 创建类的头部的信息（名称、父类）
	 *
	 * @return
	 */
	private String createHeader() {
		String extendsClass = table.getExtendsClass();
		String header = "public interface " + table.getClassName() + "" + "Dao";
		if ("BasePojo".equals(extendsClass)) {
			header = header + " extends " + "CrudDao<"
					+ table.getClassName() + ">";
		}
		header = header + " {";
		return header;
	}

}
