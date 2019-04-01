package com.foxconn.paperless.main.account.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.Euser;

public interface EmployeeDetailOrAddView extends BaseView {

	/**
	 * 獲得前一個Activity傳遞的意圖數據
	 */
	void getIntentData();
	/**
	 * 設置員工信息
	 * @param euser
	 */
	void setEmployeeInfo(Euser euser);
	/**
	 * 顯示添加員工界面控件
	 * @param user
	 */
	void showEmployeeAddView(Euser user);
	
	/**
	 * 設置製造處列表適配器
	 * @param MFGList
	 * @param index
	 */
	void setMFGAdapter(List<String> MFGList);
	/**
	 * 設置SBU列表適配器并選中條目
	 * @param SBUList
	 * @param index
	 */
	void setSBUAdapter(List<String> SBUList,int index);
	/**
	 * 設置Team列表適配器并選中條目
	 * @param TeamList
	 * @param index
	 */
	void setTeamAdapter(List<String> TeamList,int index);
	/**
	 * 設置Section列表適配器并選中條目
	 * @param sectionList
	 * @param index
	 */
	void setSectionAdapter(List<String> sectionList,int index);
	/**
	 * 選中MFG下拉條目
	 * @param index
	 */
	void setMFGSelection(int index);
	/**
	 * 設置用戶等級列表適配器
	 * @param stringArray
	 */
	void setUserlevelAdapter(String[] stringArray);
	/**
	 * 設置職務的適配器
	 * @param stringArray
	 */
	void setJobAdapter(String[] stringArray);
	/**
	 * 顯示添加成功對話框
	 * @param systemTip
	 * @param message
	 * int btnOk
	 */
	void showAddEmployeeSuccessDialog(int systemTip, String message,int btnOK);
	
	
	
	
}
