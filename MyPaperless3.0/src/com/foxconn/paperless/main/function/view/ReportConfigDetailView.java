package com.foxconn.paperless.main.function.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.CheckItem;

public interface ReportConfigDetailView extends BaseView{
	/**
	 * 設置點檢項適配器
	 * @param groupItem
	 * @param childItem
	 */
	void setCheckItemAdapter(List<String> groupItem,
			List<List<CheckItem>> childItem);
	/**
	 * 顯示選擇SBU的選擇框
	 * @param selectsbuTitle
	 * @param btnSaveconfig
	 * @param btnNo
	 * @param sBUList
	 */
	void showSelectSBUDialog(int selectsbuTitle, int btnSaveconfig, int btnNo,
			List<String> sBUList);
	/**
	 * 結束當前頁面
	 */
	void back();

}
