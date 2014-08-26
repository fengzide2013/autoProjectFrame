/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autoProjectFrame.constrain;

/**
 *
 * @author Administrator
 */
public class Field {

    private String fieldName;
    private boolean autoIncrement;
    private String javaType;
    private String jdbcType;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public String getJavaType() {
    	if("[B".equals(javaType))
    		javaType = "Object";
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getProperty(){

    	if(fieldName.contains("_")||fieldName.equals(fieldName.toUpperCase())){
			char[] charArray = fieldName.toLowerCase().toCharArray();
			int length = fieldName.length();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				if ('_' == fieldName.charAt(i)&&i!=length-1) {
					sb.append(new String("" + charArray[i + 1]).toUpperCase());
					i++;
				} else {
					sb.append(charArray[i]);
				}
			}
			return sb.toString();
		}

    	return fieldName.substring(0, 1).toLowerCase()+fieldName.substring(1);
    }

    public String getSimpleClassName(){
    	String className = getJavaType();
        return className.substring(className.lastIndexOf(".")+1);
    }
}
