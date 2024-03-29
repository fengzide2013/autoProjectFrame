package com.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.autoProjectFrame.constrain.Field;
import com.autoProjectFrame.constrain.Table;

public class PojoFileProducer extends FileProducer {

	public PojoFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public List<String> createFileContent() {
		List<String> listString = new ArrayList<String>();
		listString.add(createPackageStr());
		listString.addAll(createImportStr());
		listString.add(createHeader());
		listString.add("	private static final long serialVersionUID = 1L;");
		listString.addAll(createFieldStr());
		listString.addAll(cereteGetAndSetStr());
		listString.add("}");
		return listString;
	}

	@Override
	public String createFilePath() {
		return directory+"\\"+table.getClassName()+".java";
	}

	/**
	 * 创建类的包信息
	 *
	 * @return
	 */
	private String createPackageStr() {
		return "package " + table.getPackageName() + ".pojo;\n";
	}

	/**
	 * 创建类的导入信息
	 *
	 * @return
	 */
	private Set<String> createImportStr() {
		Set<String> importSet = createFieldImportStr();
		importSet.add(createExtendsClassImportStr());
		return importSet;
	}
	/**
	 * 创建父类的导入信息
	 * @return
	 */
	private String createExtendsClassImportStr(){
		String importString = "";
		String extendsClass = table.getExtendsClass();
		if ("BasePojo".equals(extendsClass)) {
			importString = "import com.tingwei.framework.pojo.BasePojo;";
		}else if(extendsClass!=null&&!"".equals(extendsClass)){
			importString = "import "+extendsClass;
		}
		return importString;
	}
	/**
	 * 创建字段的导入信息
	 * @return
	 */
	private Set<String> createFieldImportStr(){
		Set<String> importSet = new HashSet<String>();
		List<Field> listfield = table.getListField();
		for(Field field:listfield){
			String javaType = field.getJavaType();
			if(!"Object".equals(javaType)){
				String temp = javaType.substring(0,javaType.lastIndexOf("."));
				if(!"java.lang".equals(temp)){
					importSet.add("import "+javaType+";");
				}
			}
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
		String header = "public class " + table.getClassName();
		if ("BasePojo".equals(extendsClass)) {
			header = header + " extends " + "BasePojo";
		}else if(extendsClass!=null&&!"".equals(extendsClass)){
			header = header + " extends " + extendsClass.substring(extendsClass.lastIndexOf(".")+1);
		}
		header = header + " {";
		return header;
	}

	private List<String> createFieldStr(){
		List<String> listString = new ArrayList<String>();
		List<Field> listfield = table.getListField();
		for(Field field:listfield){
			String fieldName = field.getProperty();
			String simpleType = field.getSimpleClassName();
			String temp = "	private "+simpleType+" "+fieldName+";";
			listString.add(temp);
		}
		return listString;
	}
	private List<String> cereteGetAndSetStr(){
		List<String> listString = new ArrayList<String>();
		List<Field> listfield = table.getListField();
		for(Field field:listfield){
			String fieldName = field.getProperty();
			String simpleType = field.getSimpleClassName();
			String getStr = "	public "+simpleType+" get"+firstCharUp(fieldName)+"() {\n"+
				"		return "+fieldName+";\n	}"	;
			String setStr = "	public void set"+firstCharUp(fieldName)+"("+simpleType+" "+fieldName+") {\n"+
				"		this."+fieldName+" = "+fieldName+";\n	}";
			listString.add(getStr);
			listString.add(setStr);
		}
		return listString;
	}
	private String firstCharUp(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}

}
