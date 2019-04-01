package com.foxconn.paperless.main.function.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;

public interface ParameterManageAuditDetailView extends BaseView {
	/**
	 * 顯示基本佈局
	 * @param visible
	 * @param gone
	 * @param gone2
	 * @param btnVisible
	 */
	void showBaseView(int SMTPrinterVisible, int SMTReflowVisible,
			int PTHWSVisible, int btnVisible);
	/**
	 * 顯示SMT印刷機參數
	 */
	void showSMTPrinterParam(List<String> paramList);
	/**
	 * 顯示SMT回焊爐參數
	 */
	void showSMTReflowParam(List<String> paramList);
	/**
	 * 顯示PTH波峰焊參數
	 */
	void showPTHWSParam(List<String> paramList);
	/**
	 * 顯示參數狀態信息
	 * @param updateType
	 * @param checkState
	 */
	void showCheckStatus(String updateType, String checkState);
	/**
	 * 退出當前頁面
	 */
	void back();
	


}
