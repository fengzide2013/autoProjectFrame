/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shunwang.autoProjectFrame.constrain;

import java.util.List;

import org.junit.Test;

/**
 *
 * @author Administrator
 */
public class Table {

	private String tableName;
	private String extendsClass;
	private String packageName;
	private List<String> primaryKeys;
	private List<Field> listField;

	public String getExtendsClass() {
		return extendsClass;
	}

	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}

	public List<Field> getListField() {
		return listField;
	}

	public void setListField(List<Field> listField) {
		this.listField = listField;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	public String getSimpleTableName(){
		String temp = tableName.substring(tableName.lastIndexOf(".")+1);
		if(temp.contains("_")||temp.equals(temp.toUpperCase())){
			char[] charArray = temp.toLowerCase().toCharArray();
			int length = temp.length();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				if ('_' == temp.charAt(i)&&i!=length-1) {
					sb.append(new String("" + charArray[i + 1]).toUpperCase());
					i++;
				} else {
					sb.append(charArray[i]);
				}
			}
			return sb.toString();
		}
		return temp.substring(0, 1).toLowerCase()+temp.substring(1);
	}

	public String getClassName() {
		String simpleTableName = getSimpleTableName();
		return simpleTableName.substring(0, 1).toUpperCase()+simpleTableName.substring(1);
	}
	@Test
	public void test(){
		tableName = "AAbbCC";
		System.out.println(getSimpleTableName());
	}
}
