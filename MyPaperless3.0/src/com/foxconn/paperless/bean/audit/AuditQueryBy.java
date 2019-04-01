package com.foxconn.paperless.bean.audit;


/**
 * 簽核查詢條件和相關請求參數對應的bean
 *@ClassName AuditQueryBy
 *@Author wunian
 *@Date 2018/1/2 上午11:08:19
 */
public class AuditQueryBy {

	private String reportNum;
	private String queryBy;
	private String RNO;
	private String createDate;
	private String workorderNo;
	private String lineName;
	private String skuNo;
	private String rowNum;
	
	public AuditQueryBy(String reportNum, String queryBy, String rNO,
			String createDate, String workorderNo, String lineName,
			String skuNo, String rowNum) {
		this.reportNum = reportNum;
		this.queryBy = queryBy;
		RNO = rNO;
		this.createDate = createDate;
		this.workorderNo = workorderNo;
		this.lineName = lineName;
		this.skuNo = skuNo;
		this.rowNum = rowNum;
	}
	
	public AuditQueryBy(){
		init();
	}

	private void init(){
		this.reportNum ="";
		this.queryBy ="";
		this.RNO ="";
		this.createDate ="";
		this.workorderNo = "";
		this.lineName = "";
		this.skuNo = "";
		this.rowNum = "";
	}
	
	public String[] getAuditParam(String checkType,String MFG,String checkBy){
		return new String[]{checkType,queryBy,reportNum,MFG,checkBy,RNO,
				createDate,workorderNo,lineName,skuNo};
	}
	
	public String[] getAuditParam(String MFG,String checkBy){
		return new String[]{queryBy,reportNum,MFG,checkBy,RNO,createDate,workorderNo,
				lineName,skuNo};
	}
	
	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getQueryBy() {
		return queryBy;
	}

	public void setQueryBy(String queryBy) {
		this.queryBy = queryBy;
	}

	public String getRNO() {
		return RNO;
	}

	public void setRNO(String rNO) {
		RNO = rNO;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
}
