package com.foxconn.paperless.bean.audit;

import java.io.Serializable;
/**
 * 對應IPQC_RI_CHECK_RESULT
 *@ClassName CheckResult
 *@Author wunian
 *@Date 2017/12/29 上午8:39:59
 */
public class CheckResult implements Serializable {
	
	private String proId;
	private String checkName;
	private String checkYeild;
	private String checkProName;
	private String checkResult;
	private String checkContent;
	private String IcarNo;
	private String imageData;
	private String ToolNo;
	
	public CheckResult(){}

	public CheckResult(String proId, String checkName, String checkYeild,
			String checkProName, String checkResult, String checkContent,
			String icarNo, String imageData, String toolNo) {
		this.proId = proId;
		this.checkName = checkName;
		this.checkYeild = checkYeild;
		this.checkProName = checkProName;
		this.checkResult = checkResult;
		this.checkContent = checkContent;
		IcarNo = icarNo;
		this.imageData = imageData;
		ToolNo = toolNo;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getIcarNo() {
		return IcarNo;
	}

	public void setIcarNo(String icarNo) {
		IcarNo = icarNo;
	}

	public String getToolNo() {
		return ToolNo;
	}

	public void setToolNo(String toolNo) {
		ToolNo = toolNo;
	}

	public String getImageData() {
		return imageData;
	}

	public void setImageData(String imageData) {
		this.imageData = imageData;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckYeild() {
		return checkYeild;
	}

	public void setCheckYeild(String checkYeild) {
		this.checkYeild = checkYeild;
	}

	public String getCheckProName() {
		return checkProName;
	}

	public void setCheckProName(String checkProName) {
		this.checkProName = checkProName;
	}
}
