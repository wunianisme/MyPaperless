package com.foxconn.paperless.bean.audit;

import java.io.Serializable;
/**
 * 對應IPQC_REPORT_CHECK表
 *@ClassName ReportCheck
 *@Author wunian
 *@Date 2017/12/29 上午8:37:52
 */
public class ReportCheck implements Serializable {

	private String RNO;
	private String checkId;
	private String checkSeqno;
	private String checkRelease;
	private String checkReject;
	private String remark;
	private String checkBy;
	private String createBy;
	private String checkDate;
	private String createDate;
	
	public ReportCheck(){}
	
	public ReportCheck(String rNO, String checkId, String checkSeqno,
			String checkRelease, String checkReject, String remark,
			String checkBy, String createBy, String checkDate, String createDate) {
		RNO = rNO;
		this.checkId = checkId;
		this.checkSeqno = checkSeqno;
		this.checkRelease = checkRelease;
		this.checkReject = checkReject;
		this.remark = remark;
		this.checkBy = checkBy;
		this.createBy = createBy;
		this.checkDate = checkDate;
		this.createDate = createDate;
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

	public String getCheckSeqno() {
		return checkSeqno;
	}

	public void setCheckSeqno(String checkSeqno) {
		this.checkSeqno = checkSeqno;
	}

	public String getCheckRelease() {
		return checkRelease;
	}

	public void setCheckRelease(String checkRelease) {
		this.checkRelease = checkRelease;
	}

	public String getCheckReject() {
		return checkReject;
	}

	public void setCheckReject(String checkReject) {
		this.checkReject = checkReject;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
