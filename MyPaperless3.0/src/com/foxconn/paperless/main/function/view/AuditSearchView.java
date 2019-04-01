package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.audit.CheckInfo;

public interface AuditSearchView extends BaseView {
	/**
	 * 設置可查詢報表信息的適配器
	 * @param reportInfoList
	 */
	void setReportAdapter(List<String> reportNameList);
	/**
	 * 顯示選擇報表的彈出選擇框
	 * @param reportNameList
	 */
	void showSelectReportDialog(List<String> reportNameList);
	/**
	 * 設置簽核查詢信息的適配器
	 * @param checkInfoList
	 */
	void setAuditSearchAdapter(List<CheckInfo> checkInfoList);
	/**
	 * 刷新報表信息適配器
	 */
	void refreshReportAdapter();
	/**
	 * 刷新簽核查詢信息適配器
	 */
	void refreshAuditSearchAdapter();
	/**
	 * 顯示日期選擇框
	 * @param year
	 * @param month
	 * @param day
	 */
	void setDatePickDialog(int year, int month, int day);
	/**
	 * Activity跳轉，回傳數據
	 * @param intent
	 * @param requestCode
	 */
	 void gotoActivityForResult(Class<?> cls, Bundle bundle,int requestCode);
	 /**
	  * 在條件框內輸入二維碼信息
	  * @param where
	  */
	void inputWhere(String where);
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<?> class1, Bundle bundle);
	/**
	 * 輸入默認的報表名
	 * @param string
	 */
	void setDefaultReportName(String string);
	
}
