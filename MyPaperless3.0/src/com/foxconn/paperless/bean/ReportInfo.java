package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 封裝各BU下配置報表信息
 *@ClassName ReportInfo
 *@Author wunian
 *@Date 2017/11/3 上午9:01:28
 */
public class ReportInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String reportNum;
	private String reportName;
	
	
	
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
}
