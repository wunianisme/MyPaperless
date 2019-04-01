package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.main.check.view.ReportCheckActivity;

public interface MTInadvanceView extends BaseView {
	/**
	 * 日期選擇框
	 * @param year
	 * @param month
	 * @param day
	 */
	void setDatePickDialog(int year, int month, int day);
	/**
	 * 時間選擇框
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 */
	void setTimePickDialog(int hour, int minute, int second, int milliSecond);
	/**
	 * 設置日期
	 * @param year
	 * @param month
	 * @param day
	 */
	void setDate(int year, int month, int day);
	/**
	 * 設置時間
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 */
	void setTime(int hour, int minute, int second, int milliSecond);
	/**
	 * 顯示選擇點檢報表的ListView提示框
	 * @param titleId
	 * @param itemList
	 */
	void showSelectReportDialog(int titleId, List<String> itemList);
	/**
	 * Activity跳轉
	 * @param cls
	 * @param bundle
	 */
	void gotoActivity(Class<?> cls, Bundle bundle);
	/**
	 * Activity跳轉，并傳回數據
	 * @param intent
	 * @param requestCode
	 */
	void goActivityForResult(Intent intent, int requestCode);
	/**
	 * 顯示錯誤信息
	 * @param msg
	 */
	void showToastFailedMsg(String msg);

}
