package com.foxconn.paperless.main.function.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.audit.CheckInfo;

public interface AuditBatchView extends BaseView {
	/**
	 * 設置批量簽核條目適配器
	 * @param checkInfoList
	 */
	void setAuditBatchAdapter(List<CheckInfo> checkInfoList);
	/**
	 * 顯示簽核通過的彈出框
	 * @param titleId
	 * @param message
	 * @param btnOk
	 * @param btnNo
	 */
	void showPassDialog(int titleId, String message, int btnOk, int btnNo);
	/**
	 *返回上一個頁面                                                                                                                                                                                                                                                                  
	 */
	void back();

}
