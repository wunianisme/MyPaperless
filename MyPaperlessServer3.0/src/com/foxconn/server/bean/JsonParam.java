package com.foxconn.server.bean;

import java.util.List;
/**
 * 定义Json传递参数，此参数是指传递到服务端的参数
 *@ClassName JsonParam
 *@Author wunian
 *@Date 2017/9/8 下午3:07:47
 */
public class JsonParam {

	private String action;
	private String dbSite;
	private List<Object> pm;
	
	public JsonParam(){}
	
	public JsonParam(String action, String dbSite, List<Object> pm) {
		this.action = action;
		this.dbSite = dbSite;
		this.pm = pm;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDBSite() {
		return dbSite;
	}
	public void setDBSite(String dbSite) {
		this.dbSite = dbSite;
	}
	public List<Object> getPm() {
		return pm;
	}
	public void setPm(List<Object> pm) {
		this.pm = pm;
	}
}
