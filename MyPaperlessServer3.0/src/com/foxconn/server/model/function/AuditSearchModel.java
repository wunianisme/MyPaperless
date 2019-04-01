package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface AuditSearchModel {
	/**
	 * 獲得功能菜單中消息條數
	 * @param p
	 * @return
	 */
	JsonResult getMessageNum(MyParam p);
	/**
	 * 獲取製造處下所有已導入的報表信息
	 * @param p
	 * @return
	 */
	JsonResult getMFGReport(MyParam p);
	/**
	 * 獲取待簽核/已簽核/拒簽報表信息
	 * @param p
	 * @return
	 */
	JsonResult getAuditReport(MyParam p);
	/**
	 * 掃碼獲得簽核記錄
	 * @param p
	 * @return
	 */
	JsonResult getAuditInfoByQRCode(MyParam p);
	/**
	 *獲得簽核歷史/待簽核/已簽核/拒簽記錄
	 * @param p
	 * @return
	 */
	JsonResult getAuditInfo(MyParam p);
	/**
	 * 獲得簽核記錄基本信息
	 * @param p
	 * @return
	 */
	JsonResult getAuditBaseInfo(MyParam p);
	/**
	 * 獲得簽核詳細點檢信息
	 * @param p
	 * @return
	 */
	JsonResult getAuditCheckResult(MyParam p);
	/**
	 * 獲得拒簽原因
	 * @param p
	 * @return
	 */
	JsonResult getRejectRemark(MyParam p);
	/**
	 * 提交簽核結果
	 * @param p
	 * @return
	 */
	JsonResult updateAuditResult(MyParam p);
	/**
	 * 提交簽核通過（批量）
	 * @param p
	 * @return
	 */
	JsonResult updateAuditPass(MyParam p);
	
	
	
}
