package com.foxconn.paperless.main.check.model;



import java.io.File;
import java.util.List;

import net.tsz.afinal.FinalDb;

import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;

public interface CheckModel extends Response {

	
	/**
	 * 獲取BU下的報表名稱和編號
	 * @param buReportParam
	 */
	void getBUReportName(Params buReportParam);
	/**
	 * 通過SN帶工單
	 * @param params
	 */
	void getWO(Params params);
	/**
	 * 獲取報表基本信息（機種、機種版本、批量、deviation）
	 * @param params
	 */
	void getReportBaseInfo(Params params);
	/**
	 * //獲得報表配置的點檢項目
	 * @param params
	 */
	void getCheckItem(Params params);
	/**
	 * 獲得班別和節次
	 * @param params
	 */
	void getShiftCheckNode(Params params);
	/**
	 * 獲得面別
	 * @param params
	 */
	void getSide(Params params);
	/**
	 * 獲得標準參數（印刷機、回焊爐、波峰焊）
	 * @param params
	 */
	void getParams(Params params);
	/**
	 * 獲取具有簽核權限的人員信息
	 * @param params
	 */
	void getCheckBy(Params params);
	/**
	 * 獲得上級主管信息
	 * @param params
	 */
	void getMaster(Params params);
	/**
	 * 提交異常信息
	 * @param params
	 */
	void submitException(Params params);
	/**
	 * 上傳多張圖片文件
	 * @param callback
	 * @param uploadUrl
	 * @param imgFileList
	 */
	void uploadExceptionPhoto(
			FinalHttpCallback callback,
			String uploadUrl, List<File> imgFileList);
	/**
	 * 提交點檢信息
	 * @param params
	 */
	void submitCheckInfo(Params params);
	/**
	 * 刪除FinalDB中CheckItem表的數據
	 * @param finalDb
	 * @param checkItemList
	 * @param where
	 */
	void deleteCheckItem(FinalDb finalDb, List<CheckItem> checkItemList);
	/**
	 * 保存CheckItem數據到FinalDB
	 * @param finalDb
	 * @param checkItemList
	 * @param workorderNo
	 * @param reportNum
	 * @param RNO
	 */
	void saveCheckItem(FinalDb finalDb, List<CheckItem> checkItemList,
			String workorderNo,String reportNum,String RNO);
	/**
	 * 查找CheckItem表的數據
	 * @param finalDb
	 * @param checkItemList
	 * @return
	 */
	List<CheckItem> findCheckItem(FinalDb finalDb, List<CheckItem> checkItemList);
	/**
	 * 獲取已配置了要帶參數的報表（除三個帶參數表的報表外）
	 * @param params
	 */
	void getParamsConfigReport(Params params);
	/**
	 * 獲取部分要帶參數的點檢項的參數值
	 * @param params
	 */
	void getCheckItemParam(Params params);
	
	
}
