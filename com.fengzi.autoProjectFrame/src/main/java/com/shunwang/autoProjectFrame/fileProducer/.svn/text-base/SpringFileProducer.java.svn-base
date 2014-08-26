package com.shunwang.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.List;

import com.shunwang.autoProjectFrame.constrain.Table;

public class SpringFileProducer extends FileProducer {

	public SpringFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public List<String> createFileContent() {
		List<String> listString  = new ArrayList<String>();
		listString.add(createHeaderStr());
		listString.add(createBasicCofigStr("Dao"));
		listString.add(createBasicCofigStr("Bo"));
		listString.add(createBasicCofigStr("Action"));
		listString.add("</beans>");
		return listString;
	}

	@Override
	public String createFilePath() {
		return directory+"\\"+ table.getSimpleTableName() + ".xml";
	}

	private String createHeaderStr(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<beans xmlns=\"http://www.springframework.org/schema/beans\"\n"+
				"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"+
				"	xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd\">";
	}
	private String createBasicCofigStr(String type){
		String fullClassName = table.getPackageName()+"."+type.toLowerCase()+"."+table.getClassName()+type;
		String id = table.getSimpleTableName()+type;
		String name = "";
		String ref = "" ;
		if("Dao".equals(type)){
			name = "sqlMapClient";
			ref = "sqlMapClient";
		}else if("Bo".equals(type)){
			name = "dao";
			ref = table.getSimpleTableName()+"Dao";
		}else if("Action".equals(type)){
			fullClassName = table.getPackageName()+".web."+table.getClassName()+type;
			name = "crudbo";
			ref = table.getSimpleTableName()+"Bo";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("	<!-- "+type+" -->\n");
		sb.append("	<bean id=\""+id+"\" class=\""+fullClassName+"\" scope=\"prototype\" >\n");
		sb.append("	<property name=\""+name+"\" ref=\""+ref+"\"></property>\n");
		sb.append("	</bean>\n");
		return sb.toString();
	}

}
