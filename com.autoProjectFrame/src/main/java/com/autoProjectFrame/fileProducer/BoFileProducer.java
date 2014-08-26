package com.autoProjectFrame.fileProducer;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.autoProjectFrame.constrain.Table;



public class BoFileProducer extends FileProducer {

	public BoFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public String createFilePath() {
		return directory+"\\"+ table.getClassName() + "Bo.java";
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
		return "package " + table.getPackageName() + ".bo;\n";
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
			importSet.add("import "+table.getPackageName()+".pojo."+table.getClassName()+";");
			importSet.add("import "+table.getPackageName()+".dao."+table.getClassName()+"Dao;");
			importSet.add( "import com.tingwei.framework.bo.CrudBo;\n");
			importSet.add("import org.springframework.stereotype.Service;\n");
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
		String header = "@Service \n"+"public class " + table.getClassName()+"" +
				"Bo";
		if ("BasePojo".equals(extendsClass)) {
			header = header + " extends " + "CrudBo<"+table.getClassName()+","+table.getClassName()+"Dao"+">";
		}
		header = header + " {";
		return header;
	}


}
