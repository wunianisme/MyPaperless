package com.foxconn.paperless.main.function.view;

import java.util.List;

import android.R.integer;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.MTLineInfo;

public interface MTInadvanceByLineView extends BaseView {
	/**
	 * 顯示選擇樓層的彈出框
	 * @param titleId
	 * @param floorNameList
	 */
	void showSelectFloorDialog(int titleId, List<String> floorNameList);
	/**
	 * 設置纖體信息的適配器
	 * @param lineInfoList
	 */
	void setLineInfoAdapter(List<MTLineInfo> lineInfoList);
	/**
	 * 顯示選擇班別的彈出框
	 * @param title
	 * @param shiftArray
	 */
	void showSelectShiftDialog(String title, String[] shiftArray);
	/**
	 * 顯示選擇日期的彈出框
	 * @param titleId
	 */
	void showSelectDateDialog(int titleId,int[] date);
	/**
	 * 退出當前界面
	 */
	void back();

}
