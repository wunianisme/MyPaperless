package com.foxconn.paperless.main.view;


import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.main.check.view.ReportCheckActivity;

public interface MainView extends BaseView{
	/**
	 * 設置頂部的標題欄
	 * @param titleId
	 */
	void setMainTitle(int titleId);
	/**
	 * 跳轉到另一個Activity，并返回數據
	 * @param intent
	 * @param requestcodeScan
	 */
	void goActivityForResult(Intent intent, int requestcodeScan);
	/**
	 * 顯示錯誤提示
	 * @param msg
	 */
	void showToastFailedMsg(String msg);
	/**
	 * 顯示選擇點檢報表的ListView提示框
	 * @param titleId
	 * @param itemList
	 */
	void showSelectReportDialog(int titleId, List<String> itemList);
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<?> class1, Bundle bundle);
}
