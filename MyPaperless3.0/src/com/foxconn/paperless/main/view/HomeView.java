package com.foxconn.paperless.main.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.main.check.view.BUReportActivity;

public interface HomeView extends BaseView {

	/**
	 * 設置BU的適配器
	 * @param BUList
	 */
	void setBUAdapter(List<String> BUList);
	/**
	 * 顯示廠區未配置報表提示
	 */
	void showNullReportTip();
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<?> class1, Bundle bundle);
	/**
	 * 設置功能管理菜單適配器
	 * @param iconArray
	 * @param itemArray
	 * @param msgNumArray
	 */
	void setFunctionMenuAdapter(int[] iconArray, int[] itemArray,int[] msgNumArray);
	/**
	 * 更新菜單適配器數據
	 */
	void refreshFunctionMenuAdapter();
	/**
	 * 顯示選擇提前維護方式的彈出框
	 * @param titleId
	 * @param wayArray
	 */
	void showSelectWayDialog(int titleId, String[] wayArray);

}
