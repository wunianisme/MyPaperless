package com.foxconn.server.model.check;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface CheckModel {

	/**
	 * 獲得廠區和事業群下的所有BU
	 * @param p
	 * @return
	 */
	JsonResult getBU(MyParam p);
	/**
	 * 獲取BU下的報表名稱和編號
	 * @param p
	 * @return
	 */
	JsonResult getBUReportName(MyParam p);
	/**
	 * 获得二维码对应的报表输入类型（输工单/不输工单）
	 * @param p
	 * @return
	 */
	JsonResult getQRReportInputType(MyParam p);
	/**
	 * 獲得該時段的點檢記錄（有則表示已被點檢）
	 * @param p
	 * @return
	 */
	JsonResult getCheckRecord(MyParam p);
	/**
	 * 通過SN帶出工單
	 * @param p
	 * @return
	 */
	JsonResult getWO(MyParam p);
	/**
	 * 獲得點檢報表的基本信息（機種、機種版本、批量、deviation）
	 * @param p
	 * @return
	 */
	JsonResult getReportBaseInfo(MyParam p);
	/**
	 * 獲得報表配置的點檢項目
	 * @param p
	 * @return
	 */
	JsonResult getCheckItem(MyParam p);
	/**
	 * 獲得班別和節次
	 * @param p
	 * @return
	 */
	JsonResult getShiftCheckNode(MyParam p);
	/**
	 * 獲得面別
	 * @param p
	 * @return
	 */
	JsonResult getSide(MyParam p);
	/**
	 * 獲得標準參數（印刷機、回焊爐、波峰焊）
	 * @param p
	 * @return
	 */
	JsonResult getParams(MyParam p);
	/**
	 * 獲取具有簽核權限的人員信息（工號和中文名）
	 * @param p
	 * @return
	 */
	JsonResult getCheckBy(MyParam p);
	/**
	 * 獲取上級主管
	 * @param p
	 * @return
	 */
	JsonResult getMaster(MyParam p);
	/**
	 * 保存提交的異常點檢信息
	 * @param p
	 * @return
	 */
	JsonResult saveException(MyParam p);
	/**
	 * 提交點檢信息（四合一）
	 * @param p
	 * @return
	 */
	JsonResult saveCheckInfo(MyParam p);
	/**
	 * 獲取已配置了要帶參數的報表（除三個帶參數表的報表外）
	 * @param p
	 * @return
	 */
	JsonResult getParamsConfigReport(MyParam p);
	/**
	 * 獲取部分要帶參數的點檢項的參數值
	 * @param p
	 * @return
	 */
	JsonResult getCheckItemParam(MyParam p);
	

}
