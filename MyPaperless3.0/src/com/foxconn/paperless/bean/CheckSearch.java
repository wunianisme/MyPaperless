package com.foxconn.paperless.bean;


/**
 * 點檢狀態查詢的bean
 * @author Administrator
 *
 */
public class CheckSearch {
	//private int index;
	
	private String reportNum;//報表編號
	private String reportName;//報表名
	private String MFG;//製造處
	private String floorName;//樓層
	private String equipment;//設備編號
	private String lineName;//線別
	
	private String qrInfo;//二維碼編號
	private String BU;//BU 
	private String yieldName;//點檢頻率
	private String createBy;//點檢人工號（負責人）
	private String Section;//工站
	private String checkFlag;//節次編號（PD：0，IPQC：根據時間計算節次）
	private String chineseName;//中文名
	
	public CheckSearch(){}
	
	public CheckSearch(String reportNum, String reportName, String mFG,
			String floorName, String equipment, String lineName, String qrInfo,
			String bU, String yieldName, String createBy, String section,
			String check_flag, String chineseName) {
		this.reportNum = reportNum;
		this.reportName = reportName;
		MFG = mFG;
		this.floorName = floorName;
		this.equipment = equipment;
		this.lineName = lineName;
		this.qrInfo = qrInfo;
		BU = bU;
		this.yieldName = yieldName;
		this.createBy = createBy;
		Section = section;
		this.checkFlag = check_flag;
		this.chineseName = chineseName;
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

	public String getMFG() {
		return MFG;
	}

	public void setMFG(String mFG) {
		MFG = mFG;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getQrInfo() {
		return qrInfo;
	}

	public void setQrInfo(String qrInfo) {
		this.qrInfo = qrInfo;
	}

	public String getBU() {
		return BU;
	}

	public void setBU(String bU) {
		BU = bU;
	}

	public String getYieldName() {
		return yieldName;
	}

	public void setYieldName(String yieldName) {
		this.yieldName = yieldName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getSection() {
		return Section;
	}

	public void setSection(String section) {
		Section = section;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	
}
