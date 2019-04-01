package com.foxconn.paperless.main.account.model;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;

public interface AccountModel extends Response{
	//賬號信息
	/**
	 * 獲得BU下的所有製造處名稱
	 * @param p
	 */
	void getMFGItem(Params p);
	/**
	 * 獲得BU下的所有SBU、製造處名稱
	 * @param p
	 */
	void getSBUItem(Params p);
	/**
	 * 獲得BU下的所有Team、SBU、製造處名稱
	 * @param p
	 */
	void getTeamItem(Params p);
	/**
	 * 獲得BU下的所有工站、SBU名稱
	 * @param p
	 */
	void getSectionItem(Params p);
	/**
	 * SBU列表數據更新
	 * @param MFG
	 * @return
	 */
	List<String> SBUDataChanged(String MFG);
	/**
	 * team列表數據更新
	 * @param SBU
	 * @param MFG
	 * @return
	 */
	List<String> teamDataChanged(String SBU,String MFG);
	/**
	 * 工站列表數據更新
	 * @param SBU
	 * @return
	 */
	List<String> sectionDataChanged(String SBU);
	/**
	 * 保存修改之後的用戶賬號信息
	 * @param p
	 */
	void saveAccountInfo(Params p);
	
	
	//員工信息
	/**
	 * 獲得同一個製造處、SBU和Team下的員工信息
	 * @param p
	 */
	void getEmployeeInfo(Params p);
	/**
	 * 獲取單個員工的信息
	 * @return
	 */
	Euser getSingleEmployeeInfo(String logonName);
	/**
	 * 添加員工賬號信息到數據庫中
	 * @param p
	 */
	void saveEmployeeInfo(Params p);
}
