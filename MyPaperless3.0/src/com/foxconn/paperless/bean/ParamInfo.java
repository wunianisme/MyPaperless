package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 參數管理基本信息對應的bean
 *@ClassName ParamInfo
 *@Author wunian
 *@Date 2018/1/10 下午3:24:43
 */
public class ParamInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String parameterNum;
	private String building;
	private String line;
	private String productName;
	private String lastEditDate;
	
	private String createBy;
	private String checkBy;
	private String createDate;
	private String checkDate;
	private String updateType;
	private String checkState;
	
	public ParamInfo(){}
	
	public ParamInfo(String parameterNum, String building, String line,
			String productName, String lastEditDate, String createBy,
			String checkBy, String createDate, String checkDate,
			String updateType, String checkState) {
		this.parameterNum = parameterNum;
		this.building = building;
		this.line = line;
		this.productName = productName;
		this.lastEditDate = lastEditDate;
		this.createBy = createBy;
		this.checkBy = checkBy;
		this.createDate = createDate;
		this.checkDate = checkDate;
		this.updateType = updateType;
		this.checkState = checkState;
	}
	
	public String getParameterNum() {
		return parameterNum;
	}
	public void setParameterNum(String parameterNum) {
		this.parameterNum = parameterNum;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(String lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	public String getCheckState() {
		return checkState;
	}
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}
	
	
	
}
