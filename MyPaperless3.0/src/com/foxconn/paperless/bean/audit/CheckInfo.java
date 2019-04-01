package com.foxconn.paperless.bean.audit;

import java.io.Serializable;
/**
 * 對應IPQC_RI_BASEINFO 和IPQC_RI_CHECK_REPORT表
 *@ClassName CheckInfo
 *@Author wunian
 *@Date 2017/12/29 上午8:34:06
 */
public class CheckInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String RNO;
	private String checkId;
	private String workorderNo;
	private String skuNo;
	private String qty;
	private String skuVersion;
	private String side;
	private String deviation;
	private String checkFlag;
	private String checkRemark;
	private String checkRemarkReason;
	private String checkType;
	private String createDate;
	private String createBy;
	private String createByName;
	private String checkBy;
	private String checkByName;
	private String reportNum;
	private String reportName;
	private String MFG;
	private String floorName;
	private String lineName;
	private String shiftName;
	private String checkStatus;
	private String checkSeqno;
	private boolean checked;//批量簽核是否選中
	
	public CheckInfo(){
		this.checked=false;
	}

	public CheckInfo(String rNO, String checkId, String workorderNo,
			String skuNo, String qty, String skuVersion, String side,
			String deviation, String checkFlag, String checkRemark,
			String checkRemarkReason, String checkType, String createDate,
			String createBy, String reportNum, String mFG, String floorName,
			String lineName, String shiftName, String checkStatus) {
		RNO = rNO;
		this.checkId = checkId;
		this.workorderNo = workorderNo;
		this.skuNo = skuNo;
		this.qty = qty;
		this.skuVersion = skuVersion;
		this.side = side;
		this.deviation = deviation;
		this.checkFlag = checkFlag;
		this.checkRemark = checkRemark;
		this.checkRemarkReason = checkRemarkReason;
		this.checkType = checkType;
		this.createDate = createDate;
		this.createBy = createBy;
		this.reportNum = reportNum;
		MFG = mFG;
		this.floorName = floorName;
		this.lineName = lineName;
		this.shiftName = shiftName;
		this.checkStatus = checkStatus;
	}

	public String getRNO() {
		return RNO;
	}

	public void setRNO(String rNO) {
		RNO = rNO;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getSkuVersion() {
		return skuVersion;
	}

	public void setSkuVersion(String skuVersion) {
		this.skuVersion = skuVersion;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public String getCheckRemarkReason() {
		return checkRemarkReason;
	}

	public void setCheckRemarkReason(String checkRemarkReason) {
		this.checkRemarkReason = checkRemarkReason;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
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

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCheckByName() {
		return checkByName;
	}

	public void setCheckByName(String checkByName) {
		this.checkByName = checkByName;
	}

	public String getCheckSeqno() {
		return checkSeqno;
	}

	public void setCheckSeqno(String checkSeqno) {
		this.checkSeqno = checkSeqno;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
