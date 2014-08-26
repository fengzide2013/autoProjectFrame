package com.shunwang.autoProjectFrame.fileProducer;

import java.util.ArrayList;
import java.util.List;

import com.shunwang.autoProjectFrame.constrain.Table;

public class StrutsFileProducer extends FileProducer {

	public StrutsFileProducer(Table table, String directory) {
		super(table, directory);
	}

	@Override
	public List<String> createFileContent() {
		List<String> listString  = new ArrayList<String>();
		listString.add(createHeaderStr());
		listString.add(createBasicCofigStr());
		return listString;
	}

	@Override
	public String createFilePath() {
		return directory+"\\"+ table.getSimpleTableName() + ".xml";
	}

	private String createHeaderStr(){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+
				"<!DOCTYPE struts PUBLIC\n"+
				"	\"-//Apache Software Foundation//DTD Struts Configuration 2.0//EN\"\n"+
				"	\"http://struts.apache.org/dtds/struts-2.0.dtd\">";
	}
	private String createBasicCofigStr(){
		StringBuffer sb = new StringBuffer();
		sb.append("<struts>\n");
		sb.append("	<package name=\""+table.getSimpleTableName()+"\" extends=\"struts-default\">\n\n");
		sb.append("	</package>\n");
		sb.append("</struts>");
		return sb.toString();
	}

}
