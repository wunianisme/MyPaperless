package com.foxconn.paperless.main.function.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.CheckSearch;

public interface CheckSearchView extends BaseView{
	/**
	 * 設置日期
	 * @param dateStr
	 */
	//void setDate(String dateStr);
	/**
	 * 設置日期選擇框
	 * @param year
	 * @param month
	 * @param date
	 */
	void setDatePickDialog(int year, int month, int date);
	/**
	 * 設置樓層的適配器
	 * @param floorNameList
	 */
	void setFloorNameAdapter(List<String> floorNameList);
	/**
	 * 設置線別的適配器
	 * @param lineNameList
	 */
	void setlineNameAdapter(List<String> lineNameList);
	/**
	 * 設置點檢狀態適配器
	 * @param checkStatusList
	 */
	void setCheckStatusAdapter(List<CheckSearch> checkStatusList);

}
