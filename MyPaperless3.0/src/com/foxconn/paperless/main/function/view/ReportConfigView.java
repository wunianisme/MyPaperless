package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ReportInfo;

public interface ReportConfigView extends BaseView {
	/**
	 * 設置報表信息適配器
	 * @param reportInfoList
	 */
	void setReportAdapter(List<ReportInfo> reportInfoList);
	/**
	 * 顯示報表個數
	 * @param countInfo
	 */
	void showCountInfo(String countInfo);
	/**
	 * Activity跳轉
	 * @param cls
	 * @param bundle
	 */
	void gotoActivity(Class<?> cls, Bundle bundle);
	/**
	 * 設置報表名稱適配器
	 * @param reportNameList
	 */
	void setReportNameAdapter(List<String> reportNameList);

}
