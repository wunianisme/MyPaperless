package com.foxconn.paperless.bean;

import java.io.Serializable;
import java.util.Map;

import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.http.WebServiceUtil.Response;
/**
 * 連接webservice的參數bean
 *@ClassName Params
 *@Author wunian
 *@Date 2017/9/6 下午5:23:32
 */
public class Params implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String wsdl;
	private String nameSpace;
	private String methodName;
	private String actionName;
	private String dbSite;
	private Map<String,Object> params;
	private Response response;
	
	public Params(){}
	
	public Params(String wsdl, String nameSpace, String methodName,
			String actionName,String dbSite, Map<String,Object> params, Response response) {
		this.wsdl = wsdl;
		this.nameSpace = nameSpace;
		this.methodName = methodName;
		this.actionName = actionName;
		this.dbSite=dbSite;
		this.params = params;
		this.response = response;
	}
	
	public Params(Response response){
		init(response);
	}
	
	public void init(Response response){
		this.wsdl=MyConstant.WSDL;
		this.nameSpace=MyConstant.NAMESPACE;
		this.methodName=MyConstant.METHODNAME;
		//this.actionName="";
		this.dbSite=MyConstant.DBSITE;
		//this.params = params;
		this.response = response;
		
	}
	
	public String getWsdl() {
		return wsdl;
	}
	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public Map<String,Object> getParams() {
		return params;
	}
	public void setParams(Map<String,Object> params) {
		this.params = params;
	}
	public Response getResponse() {
		return response;
	}
	public void setResponse(Response response) {
		this.response = response;
	}

	public String getDBSite() {
		return dbSite;
	}

	public void setDBSite(String dbSite) {
		this.dbSite = dbSite;
	}
}
