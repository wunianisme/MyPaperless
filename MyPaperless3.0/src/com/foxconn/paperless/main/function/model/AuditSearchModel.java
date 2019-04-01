package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;

public interface AuditSearchModel extends Response {
	//簽核查詢
	/**
	 * 獲取製造處下所有已導入的報表信息
	 * @param params
	 */
	void getMFGReport(Params params);
	/**
	 * 獲取待簽核/已簽核/拒簽報表信息
	 * @param params
	 */
	void getAuditReport(Params params);
	/**
	 * 掃碼獲得簽核記錄
	 * @param params
	 */
	void getAuditInfoByQRCode(Params params);
	/**
	 * 獲得簽核歷史/待簽核/已簽核/拒簽記錄
	 * @param params
	 */
	void getAuditInfo(Params params);
	//簽核基本信息
	/**
	 * 獲得簽核記錄基本信息
	 * @param params
	 */
	void getAuditBaseInfo(Params params);
	
	//簽核詳細點檢信息
	/**
	 * 獲得簽核詳細點檢信息
	 * @param params
	 */
	void getAuditCheckResult(Params params);
	/**
	 * 獲得拒簽原因
	 * @param params
	 */
	void getRejectRemark(Params params);
	/**
	 * 提交簽核結果
	 * @param params
	 */
	void submitAuditResult(Params params);
	/**
	 * 提交簽核通過（批量）
	 * @param params
	 */
	void submitAuditPass(Params params);
	
}
