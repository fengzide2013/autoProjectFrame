package com.shunwang.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.List;

import com.shunwang.autoProjectFrame.constrain.Field;
import com.shunwang.autoProjectFrame.constrain.Table;

public class SqlMapFileProducer extends FileProducer {

	public SqlMapFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public List<String> createFileContent() {
		List<String> listString = new ArrayList<String>();
		listString.add(createHeaderStr());
		listString.add(createSqlMapStr());
		listString.add(createResultMapStr());
		listString.add(createFindStr());
		if(table.getPrimaryKeys().size()==1){
			listString.add(createGetStr());
		}
		listString.add(createDeleteStr());
		listString.add(createInsertStr());
		listString.add(createFindCntStr());
		listString.add(StringUpdateStr());

		return listString;
	}

	@Override
	public String createFilePath() {
		return directory+"\\"+ table.getSimpleTableName() + "Mapper.xml";
	}
	private String createFullClassName(){
		return table.getPackageName()+".pojo."+table.getClassName();
	}
	private String createHeaderStr(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >";
	}
	private String createSqlMapStr(){
		return "<mapper namespace=\""+table.getPackageName()+".dao."+table.getClassName()+"Dao"+"\">";
	}
	private String createResultMapStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("<resultMap type=\""+createFullClassName()+"\" id=\"BaseResultMap\">\n");
		List<Field> listField = table.getListField();
		for(Field field:listField){
			String property = field.getProperty();
			String fieldName = field.getFieldName();
			String jdbcType = field.getJdbcType();
			sb.append("	<result property=\""+property+"\" column=\""+fieldName+"\" jdbcType=\""+jdbcType+"\"/>\n");
		}
		sb.append("</resultMap>");
		return sb.toString();
	}
	private String createFindStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<select id=\"query\" resultMap=\"BaseResultMap\" parameterType=\"com.tingwei.framework.mybatis.query.ConditionQuery\" >\n");
		sb.append("		select \n");
		sb.append(""+converAllFieldToStr("t.")+"		from "+table.getTableName()+" t \n");
		sb.append("		<include refid=\"conditionQuery.core\" />\n");
		sb.append("		order by\n");
		sb.append(" 		<choose>\n");
		sb.append(" 			<when test=\"paramMap.orderCol!=null\">\n");
		sb.append(" 				${paramMap.orderCol}\n");
		sb.append(" 			</when>\n");
		sb.append(" 		    <otherwise>\n");
		sb.append(" 			   "+convertPrimaryKeysToStr()+" desc\n");
		sb.append(" 			</otherwise>\n");
		sb.append(" 		</choose>\n");
		sb.append(" 		<if test=\"paramMap.rp > 0\">\n");
		sb.append("				LIMIT #{paramMap.firstResult},#{paramMap.rp}\n");
		sb.append("		</if>\n");
		sb.append("	</select>");

		return sb.toString();
	}
	private String convertPrimaryKeysToStr(){
		List<String> primaryKeys = table.getPrimaryKeys();
		StringBuffer sb = new StringBuffer();
		for(String str:primaryKeys){
			sb.append(str+",");
		}
                if(sb.length()>0)
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	private String converAllFieldToStr(String prex){
		List<Field> listField = table.getListField();
		StringBuffer sb = new StringBuffer();
		for(Field field:listField){
			sb.append("		"+prex+field.getFieldName()+",\n");
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
	private String createGetStr(){
		String fieldName = table.getPrimaryKeys().get(0);
		Field field = getField(fieldName);
		StringBuffer sb = new StringBuffer();
		sb.append("	<select id=\"get\" resultMap=\"BaseResultMap\" parameterType=\""+field.getJavaType()+"\" >\n");
		sb.append("		select\n"+converAllFieldToStr(""));
		sb.append("		from "+table.getTableName()+"\n");
		sb.append("		where "+fieldName+" = #{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}\n");
		sb.append("	 </select>");
		return sb.toString();
	}
	private String createDeleteStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<delete id=\"delete\" parameterType=\""+createFullClassName()+"\" >\n");
		sb.append("		delete from "+table.getTableName()+" where 1=1 \n");
		List<String>  primaryKeys = table.getPrimaryKeys();
		if(primaryKeys.size()!=0){
			for(String str:primaryKeys){
				Field field = getField(str);
				sb.append("		and "+field.getFieldName()+"=#{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}\n");
			}
		}else{
			List<Field> listField = table.getListField();
			for(Field field:listField){
				sb.append("			and "+field.getFieldName()+"=#{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}\n");
			}
		}
		sb.append("	</delete>");
		return sb.toString();
	}
	private Field getField(String fieldName){
		List<Field> listField = table.getListField();
		Field field = null;
		for(Field tempField:listField){
			if(fieldName.equals(tempField.getFieldName()))
				 field=tempField;
		}
		return field;
	}
	private String createInsertStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<insert id=\"save\" parameterType=\""+createFullClassName()+"\" >\n");
		sb.append("		insert into "+table.getTableName()+" (\n");
		String fieldStr =converFieldExceptPrimaryKeyToStr("");
		String valuesSetStr = createValuesSetStr();
//		List<String> primaryKeys = table.getPrimaryKeys();
		Field field = null;
//		if(primaryKeys.size()==1){
//		 field = getField(table.getPrimaryKeys().get(0));
//		}
		if(field!=null&&field.isAutoIncrement()){
			fieldStr = fieldStr.replaceAll("		"+field.getFieldName()+"\n", "");
			if(fieldStr.endsWith(",\n"))
			fieldStr = fieldStr.substring(0, fieldStr.lastIndexOf(","));
//			valuesSetStr = valuesSetStr.replaceAll("		#{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}#\n", "");
			if(valuesSetStr.endsWith(",\n"))
			valuesSetStr = valuesSetStr.substring(0, valuesSetStr.lastIndexOf(","));
			sb.append(fieldStr);
			sb.append("		)\n");
			sb.append("		values(\n");
			sb.append(valuesSetStr);
			sb.append("		)\n");
			sb.append("	</insert>");
		}else{
			sb.append(fieldStr);
			sb.append("		)\n");
			sb.append("		values(\n");
			sb.append(valuesSetStr);
			sb.append("		)\n");
			sb.append("	</insert>");
		}
		return sb.toString();
	}
	
	private String converFieldExceptPrimaryKeyToStr(String prex){
		List<Field> listField = table.getListField();
		StringBuffer sb = new StringBuffer();
		List<String> primaryKeys=table.getPrimaryKeys();
		for(Field field:listField){
			for(String primaryKey:primaryKeys){
				if(!field.getFieldName().equals(primaryKey)){
					sb.append("		"+prex+field.getFieldName()+",\n");
				}
			}
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
	
	
	private String createValuesSetStr(){
		List<Field> listField = table.getListField();
		StringBuffer sb = new StringBuffer();
		List<String> primaryKeys=table.getPrimaryKeys();
		for(Field field:listField){
			for(String primaryKey:primaryKeys){
				if(!field.getFieldName().equals(primaryKey)){
					sb.append("		#{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"},\n");
				}
			}
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
	private String createFindCntStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<select id=\"queryCnt\" parameterType=\"com.tingwei.framework.mybatis.query.ConditionQuery\" resultType=\"java.lang.Integer\" >\n");
		sb.append("		select count(*) from "+table.getTableName()+" t\n");
		sb.append("		<include refid=\"conditionQuery.core\" />\n");
		sb.append("	</select>");
		return sb.toString();
	}
	private String StringUpdateStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<update id=\"update\" parameterType=\""+createFullClassName()+"\" >\n");
		sb.append("		update "+table.getTableName()+" set\n");
		String valuesEqualStr = createValuesEqualStr();
		String whereStr = "		where 1=1 ";
		List<String> primaryKeys = table.getPrimaryKeys();
		if(primaryKeys.size()>0){
			for(String fieldName:primaryKeys){
				Field field = getField(fieldName);
//				valuesEqualStr = valuesEqualStr.replaceAll("		"+field.getFieldName()+" = #{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}#\n", "");
				if(valuesEqualStr.endsWith(",\n"))
				valuesEqualStr = valuesEqualStr.substring(0, valuesEqualStr.lastIndexOf(","));
				whereStr = whereStr+" and "+field.getFieldName()+" = #{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"}";
			}
		}
		sb.append(valuesEqualStr);
		sb.append(whereStr+"\n");
		sb.append("	</update>\n");
		sb.append("</mapper>");
		return sb.toString();
	}
	private String createValuesEqualStr(){
		List<Field> listField = table.getListField();
		StringBuffer sb = new StringBuffer();
		for(Field field:listField){
			sb.append("		"+field.getFieldName()+" = #{"+field.getProperty()+",jdbcType="+field.getJdbcType()+"},\n");
		}
		sb.deleteCharAt(sb.length()-2);
		return sb.toString();
	}
}
