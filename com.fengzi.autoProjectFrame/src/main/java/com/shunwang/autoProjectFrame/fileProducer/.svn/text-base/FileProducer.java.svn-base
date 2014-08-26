package com.shunwang.autoProjectFrame.fileProducer;

import com.shunwang.autoProjectFrame.constrain.Table;
import com.shunwang.autoProjectFrame.util.FileUtil;

import java.util.List;

public abstract class FileProducer {
    protected Table table;
    protected String directory;
    public Table getTable() {
		return table;
	}
	public void setTable(Table table) {
		this.table = table;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public FileProducer(Table table, String directory) {
		super();
		this.table = table;
		this.directory = directory;
	}
	public void createFile(){
		FileUtil.createFile(createFilePath(), createFileContent());
    }
    public abstract String createFilePath();
    public abstract List<String> createFileContent();
}
