package com.foxconn.paperless.main.check.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.ReportInfo;

public interface BUReportView extends BaseView {
	/**
	 * 設置報表信息列表的適配器
	 * @param buReport
	 */
	void setReportAdapter(List<ReportInfo> buReport);
	/**
	 * 設置標題
	 * @param title
	 */
	void setTitleName(String title);
	/**
	 * 設置報表總數
	 * @param totalCount
	 */
	void setTotalCount(String totalCount);
	/**
	 * ListView滑動到指定位置
	 * @param pos
	 */
	void ScrollTo(int pos);

}
