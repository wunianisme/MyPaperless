package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 點檢參數配置表對應的bean
 *@ClassName ParamConfig
 *@Author wunian
 *@Date 2017/12/26 上午11:40:52
 */
public class ParamsConfig implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reportNum;
	private String reportName;
	private String checkName;
	private String checkProName;
	private String proId;
	private String type;
	private String serverAddress;
	private String param;
	private int paramNum;
	private String describe;
	
	public ParamsConfig(){}
	
	public ParamsConfig(String reportNum, String reportName, String checkName,
			String checkProName, String proId, String type, String serverAddress,String param,
			int paramNum,String describe) {
		this.reportNum = reportNum;
		this.reportName = reportName;
		this.checkName = checkName;
		this.checkProName = checkProName;
		this.proId = proId;
		this.type = type;
		this.serverAddress=serverAddress;
		this.param = param;
		this.paramNum = paramNum;
		this.describe=describe;
	}
	public String getReportNum() {
		return reportNum;
	}
	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckProName() {
		return checkProName;
	}
	public void setCheckProName(String checkProName) {
		this.checkProName = checkProName;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public int getParamNum() {
		return paramNum;
	}
	public void setParamNum(int paramNum) {
		this.paramNum = paramNum;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
