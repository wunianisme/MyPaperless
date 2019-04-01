package com.foxconn.server.bean;

import java.util.List;

public class MyParam {

	
	private String action;
	private String site;
	private String sql;
	private String[] database;
	private List<Object> params;
	
	private String imageDataStr;//上傳圖片BASE64數據
	
	public MyParam(){}
	
	public MyParam(String action, String site,String sql,String[] database, List<Object> params) {
		this.action = action;
		this.site = site;
		this.sql=sql;
		this.database=database;
		this.params = params;
	}
	
	public MyParam(JsonParam json,String sql,String[] database){
		this.action=json.getAction();
		this.site=json.getDBSite();
		this.params=json.getPm();
		this.sql=sql;
		this.database=database;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String[] getDatabase() {
		return database;
	}

	public void setDatabase(String[] database) {
		this.database = database;
	}

	public String getImageDataStr() {
		return imageDataStr;
	}

	public void setImageDataStr(String imageDataStr) {
		this.imageDataStr = imageDataStr;
	}
	
}
