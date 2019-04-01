package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 對應IPQC_Exception_Feedback表
 *@ClassName ExceptionFeedback
 *@Author wunian
 *@Date 2018/1/18 下午4:35:44
 */
public class ExceptionFeedback implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String MFG;
	private String Equipment;
	private String floor;
	private String reportNum;
	private String reportName;
	private String RNO;
	private String proId;
	private String checkName;
	private String checkProName;
	private String checkId;
	private String fromUser;
	private String content;
	private String toUser;
	private String pictureUrl;
	private String commitTime;
	private String dealState;
	private String backContent;
	private String completeDate;
	
	public ExceptionFeedback(){}
	
	public ExceptionFeedback(String id, String mFG, String equipment,
			String floor, String reportNum, String rNO, String proId,
			String checkId, String fromUser, String content, String toUser,
			String pictureUrl, String commitTime, String dealState,
			String backContent, String completeDate) {
		this.id = id;
		MFG = mFG;
		Equipment = equipment;
		this.floor = floor;
		this.reportNum = reportNum;
		RNO = rNO;
		this.proId = proId;
		this.checkId = checkId;
		this.fromUser = fromUser;
		this.content = content;
		this.toUser = toUser;
		this.pictureUrl = pictureUrl;
		this.commitTime = commitTime;
		this.dealState = dealState;
		this.backContent = backContent;
		this.completeDate = completeDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMFG() {
		return MFG;
	}

	public void setMFG(String mFG) {
		MFG = mFG;
	}

	public String getEquipment() {
		return Equipment;
	}

	public void setEquipment(String equipment) {
		Equipment = equipment;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getRNO() {
		return RNO;
	}

	public void setRNO(String rNO) {
		RNO = rNO;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}

	public String getDealState() {
		return dealState;
	}

	public void setDealState(String dealState) {
		this.dealState = dealState;
	}

	public String getBackContent() {
		return backContent;
	}

	public void setBackContent(String backContent) {
		this.backContent = backContent;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
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
	
}
