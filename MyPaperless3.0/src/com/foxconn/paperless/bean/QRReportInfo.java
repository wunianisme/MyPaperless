package com.foxconn.paperless.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.foxconn.paperless.constant.MyEnum.Report;
/**
 * 二維碼綁定的報表信息
 *@ClassName QRReportInfo
 *@Author wunian
 *@Date 2017/12/2 下午4:31:28
 */
public class QRReportInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String qrInfo;//二維碼信息
	private String reportType;//報表類型
	private String reportNum;
	private String reportName;
	private String isInputOrder;//是否輸工單
	private String MFG;
	private String SBU;
	private String yeildFlag;//點檢頻率
	
	private String RNO;//點檢編號
	private String floorName;//樓層
	private String equipment;//線別
	private String section;//二維碼對應工站
	private String skuNo;//機種
	private String skuVersion;//機種版本
	private String qty;//批量
	private String side;//面別
	private String checkRemark;//備註
	private String checkRemarkReason;//原因
	private String deviation;
	private String checkType;//點檢類型
	private String checkId;//點檢節次ID
	private String checkNode;//點檢節次
	private String shiftName;//班別
	private String workorderNo;//工單
	private String check;//是否點檢
	private Map<String,String> standardParam;//標準參數
	
	private String createDate;//創建日期
	
	public QRReportInfo(){
		init();
	}
	
	public QRReportInfo(String qrInfo, String reportNum, String reportName,
			String isInputOrder, String mFG, String sBU, String yeildFlag,
			String floorName, String equipment) {
		this.qrInfo = qrInfo;
		this.reportNum = reportNum;
		this.reportName = reportName;
		this.isInputOrder = isInputOrder;
		MFG = mFG;
		SBU = sBU;
		this.yeildFlag = yeildFlag;
		this.floorName = floorName;
		this.equipment = equipment;
	}
	
	private void init(){
		skuNo=Report.DEFAULT_VALUE;
		skuVersion=Report.DEFAULT_VALUE;
		qty=Report.DEFAULT_INTVALUE;
		side=Report.DEFAULT_VALUE;
		deviation=Report.DEFAULT_VALUE;
		workorderNo=Report.DEFAULT_VALUE;
		checkRemark=Report.DEFAULT_VALUE;
		checkRemarkReason=Report.DEFAULT_VALUE;
		checkType=Report.DEFAULT_VALUE;
		checkId=Report.DEFAULT_CHECKID;
		check=Report.CHECK;
		shiftName=Report.DEFAULT_VALUE;
		standardParam=new HashMap<String, String>();
		createDate="";
	}
	
	public String getQrInfo() {
		return qrInfo;
	}
	public void setQrInfo(String qrInfo) {
		this.qrInfo = qrInfo;
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
	public String getIsInputOrder() {
		return isInputOrder;
	}
	public void setIsInputOrder(String isInputOrder) {
		this.isInputOrder = isInputOrder;
	}
	public String getMFG() {
		return MFG;
	}
	public void setMFG(String mFG) {
		MFG = mFG;
	}
	public String getSBU() {
		return SBU;
	}
	public void setSBU(String sBU) {
		SBU = sBU;
	}
	public String getYeildFlag() {
		return yeildFlag;
	}
	public void setYeildFlag(String yeildFlag) {
		this.yeildFlag = yeildFlag;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getRNO() {
		return RNO;
	}

	public void setRNO(String rNO) {
		RNO = rNO;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSkuVersion() {
		return skuVersion;
	}

	public void setSkuVersion(String skuVersion) {
		this.skuVersion = skuVersion;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
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

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckNode() {
		return checkNode;
	}

	public void setCheckNode(String checkNode) {
		this.checkNode = checkNode;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public Map<String, String> getStandardParam() {
		return standardParam;
	}

	public void setStandardParam(Map<String, String> standardParam) {
		this.standardParam = standardParam;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
}
