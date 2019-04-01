package com.foxconn.paperless.main.account.view;

import java.util.List;

import android.os.Bundle;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.Euser;

public interface EmployeeInfoView extends BaseView {

	/**
	 * 獲取同一個製造處、SBU和Team下的員工信息
	 */
	void getEmployeeInfo();
	/**
	 * 設置員工信息的適配器
	 * @param employeeInfoList
	 */
	void setEmployeeInfoAdapter(List<Euser> employeeInfoList);
	/**
	 * Activity跳轉
	 * @param class1
	 * @param bundle
	 */
	void gotoActivity(Class<EmployeeDetailOrAddActivity> class1, Bundle bundle);
	/**
	 * 設置搜尋框的適配器
	 * @param employeeLogonNameAndName
	 */
	void setSearchAdapter(List<String> employeeLogonNameAndName);
	/**
	 * 顯示頂部的add菜單
	 * @param visible
	 */
	void showAdd(int visible);
}
