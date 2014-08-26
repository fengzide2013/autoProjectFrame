package com.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.List;

import com.autoProjectFrame.constrain.Table;
import com.autoProjectFrame.jdbc.DBPassport;
import com.autoProjectFrame.util.FileUtil;
import com.autoProjectFrame.util.StringUtil;
import com.shunwang.autoProjectFrame.frame.ConditionJFrame;

public class ProjectFrameProducer {
	private List<Table> listTable;
	private static String projectDir = "D:\\autoProject";
	private static String projectName;

	public List<Table> getListTable() {
		return listTable;
	}

	public void setListTable(List<Table> listTable) {
		this.listTable = listTable;
	}

	public static String getProjectDir() {
		return projectDir;
	}

	public void setProjectDir(String projectDir) {
		ProjectFrameProducer.projectDir = projectDir;
	}

	public static String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		ProjectFrameProducer.projectName = projectName;
	}

	public void createProjectFrame() {
//		createWebappAndTestDir();
		createResourcesDir();
		for (Table table : listTable) {
			createMvcDir(table);
			createFile(table);
		}
//		copyExampleWhereClauseFile();
//		createSqlMapConfigFile();
//		createServerProperties();
//		copyApplicationContextFile();
//		createStrutsFile();
	}
	private void createWebappAndTestDir(){
		String dir =  projectDir + "\\" + projectName + "\\" + "src\\test\\java";
		FileUtil.createDir(dir);
		dir =  projectDir + "\\" + projectName + "\\" + "src\\test\\resources";
		FileUtil.createDir(dir);
		dir =  projectDir + "\\" + projectName + "\\" + "src\\main\\webapp";
		FileUtil.createDir(dir);
	}

	private void createResourcesDir() {
		String[] folders = new String[] { "mybatis"};
		for (String folderName : folders) {
			String dir = createBasicResourcesDir() + "\\" + folderName;
			FileUtil.createDir(dir);
		}
	}

	private String createBasicResourcesDir() {
		return projectDir + "\\" + projectName + "\\" + "src\\main\\resources";
	}

	private void createMvcDir(Table table) {
		String[] folders = new String[] { "pojo", "dao", "bo", "web" };
		for (String folderName : folders) {
			String dir = cteateBasicMvcDir(table) + "\\" + folderName;
			FileUtil.createDir(dir);
		}
	}

	private String cteateBasicMvcDir(Table table) {
		return projectDir + "\\" + projectName + "\\" + "src\\main\\java\\"
				+ StringUtil.convertPointToSlash(table.getPackageName());
	}

	private void createFile(Table table) {
		String directory = cteateBasicMvcDir(table);
		new PojoFileProducer(table, directory + "\\pojo").createFile();
		new DaoFileProducer(table, directory + "\\dao").createFile();
		new BoFileProducer(table, directory + "\\bo").createFile();
//		new ActionFileProducer(table, directory + "\\web").createFile();
		directory = createBasicResourcesDir();
		new SqlMapFileProducer(table, directory + "\\mybatis").createFile();
//		new SpringFileProducer(table, directory + "\\spring").createFile();
//		new StrutsFileProducer(table, directory + "\\struts").createFile();
	}

	private void copyExampleWhereClauseFile() {
		String oldPath = "Example_Where_Clause.xml";
		String newPath = createBasicResourcesDir()
				+ "\\mybatis\\Example_Where_Clause.xml";
		FileUtil.copyFile(oldPath, newPath);
	}

	private void createSqlMapConfigFile() {
		List<String> fileContent = new ArrayList<String>();
		fileContent.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		fileContent.add("<!DOCTYPE sqlMapConfig 	PUBLIC \"-//ibatis.apache.org//DTD SQL Map Config 2.0//EN\"  \"http://ibatis.apache.org/dtd/sql-map-config-2.dtd\">");
		fileContent.add("<sqlMapConfig>");
		fileContent.add("	<settings cacheModelsEnabled=\"true\"  enhancementEnabled=\"false\" lazyLoadingEnabled=\"false\"   maxRequests=\"3000\" maxSessions=\"3000\" maxTransactions=\"3000\" useStatementNamespaces=\"true\"/>");
		fileContent.add("		<sqlMap resource=\"ibatis/Example_Where_Clause.xml\"/>");
		for (Table table : listTable) {
			String resource = "mybatis/" + table.getSimpleTableName()
					+ "Mapper.xml";
			fileContent.add("		<sqlMap resource=\"" + resource + "\" />");
		}
		fileContent.add("</sqlMapConfig>");
		String filePath = createBasicResourcesDir()
				+ "\\mybatis\\sqlMapConfig.xml";
		FileUtil.createFile(filePath, fileContent);
	}

	private void createServerProperties() {
		List<String> fileContent = new ArrayList<String>();
		DBPassport passport = ConditionJFrame.getPassport();
		fileContent.add("jdbc.driverClassName=" + passport.getDriver());
		fileContent.add("jdbc.maxActive=20");
		fileContent.add("jdbc.maxIdle=30");
		fileContent.add("jdbc.maxWait=3000");
		fileContent.add("jdbc.url=" + passport.getUrl());
		fileContent.add("jdbc.username=" + passport.getUsername());
		fileContent.add("jdbc.password=" + passport.getPassword());
		String filePath = createBasicResourcesDir() + "\\server.properties";
		FileUtil.createFile(filePath, fileContent);
	}
	private void copyApplicationContextFile(){
		String oldPath = "applicationContext.xml";
		String newPath = createBasicResourcesDir()
				+ "\\applicationContext.xml";
		FileUtil.copyFile(oldPath, newPath);
	}

	private void createStrutsFile(){
		List<String> fileContent = new ArrayList<String>();
		fileContent.add(createHeaderStr());
		fileContent.add("<struts>");
		fileContent.add(createConstantStr());
		fileContent.add(createPackageStr());
		fileContent.add(createIncludeStr());
		fileContent.add("</struts>");
		String filePath = createBasicResourcesDir()+"\\struts.xml";
		FileUtil.createFile(filePath, fileContent);
	}
	private String createHeaderStr(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
			"<!DOCTYPE struts PUBLIC \"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\"\n"+
			"	\"http://struts.apache.org/dtds/struts-2.0.dtd\">\n";
	}
	private String createConstantStr(){
		return "	<constant name=\"struts.action.extension\" value=\"do\"/>\n"+
			"	<constant name=\"struts.objectFactory\" value=\"spring\" />\n"+
			"	<constant name=\"struts.devMode\" value=\"true\" />\n"+
			"	<constant name=\"struts.configuration.xml.reload\" value=\"false\" />\n"+
			"	<constant name=\"struts.multipart.maxSize\" value=\"2097152\" />\n";
	}
	private String createPackageStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("	<package name=\"isp\" extends=\"struts-default\">\n");
		sb.append("		<interceptors>\n");
		sb.append("			<interceptor-stack name=\"swDefaultStack\">\n");
		sb.append("				<interceptor-ref name=\"conversionError\"/>\n");
		sb.append("				<interceptor-ref name=\"defaultStack\" />\n");
		sb.append("			</interceptor-stack>\n");
		sb.append("		</interceptors>\n");
		sb.append("		<default-interceptor-ref name=\"swDefaultStack\" />\n");
		sb.append("		<global-results>\n");
		sb.append("			<result name=\"error\">ispError.jsp</result>\n");
		sb.append("		</global-results>\n");
		sb.append("		<global-exception-mappings>\n");
		sb.append("			<exception-mapping result=\"error\" exception=\"java.lang.Exception\"></exception-mapping>\n");
		sb.append("		</global-exception-mappings>\n");
		sb.append("	</package>\n");
		return sb.toString();
	}
	private String createIncludeStr(){
		StringBuffer sb = new StringBuffer();
		for(Table table:listTable){
			sb.append("	<include file=\"struts/"+table.getSimpleTableName()+".xml\" />\n");
		}
		return sb.toString();
	}
	public static void main(String[] args) throws Exception {

		  System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
		  System.out.println(ProjectFrameProducer.class.getClassLoader().getResource(""));
		  System.out.println(ClassLoader.getSystemResource("").getPath());
		  System.out.println(ProjectFrameProducer.class.getResource("").getPath());
		  System.out.println(ProjectFrameProducer.class.getResource("/").getPath()); //Class 文件所在路径
		  System.out.println(System.getProperty("user.dir"));
		 }
}
