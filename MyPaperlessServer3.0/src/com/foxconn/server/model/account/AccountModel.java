package com.foxconn.server.model.account;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface AccountModel {
	/**
	 * 獲得BU下的所有製造處名稱（MFG）
	 * @param p
	 * @return
	 */
	JsonResult getMFGItem(MyParam p);
	/**
	 * 獲得BU下的所有SBU名稱（SBU,MFG）
	 * @param p
	 * @return
	 */
	JsonResult getSBUItem(MyParam p);
	/**
	 * 獲得BU下的所有Team名稱（Team,SBU,MFG）
	 * @param p
	 * @return
	 */
	JsonResult getTeamItem(MyParam p);
	/**
	 * 獲得BU下的所有工站名稱（Section,SBU）
	 * @param p
	 * @return
	 */
	JsonResult getSectionItem(MyParam p);
	/**
	 * 保存修改后的賬號信息
	 * @param p
	 * @return
	 */
	JsonResult saveAccountInfo(MyParam p);
	/**
	 * 獲得同一個製造處、SBU和Team下的員工信息
	 * @param p
	 * @return
	 */
	JsonResult getEmployeeInfo(MyParam p);
	/**
	 * 添加員工賬號
	 * @param p
	 * @return
	 */
	JsonResult saveEmployeeInfo(MyParam p);
}
