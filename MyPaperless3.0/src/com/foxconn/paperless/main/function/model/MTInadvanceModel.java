package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;

public interface MTInadvanceModel extends Response {
	/**
	 * 獲得二維碼報表信息
	 * @param params
	 */
	void getQRReportInputType(Params params);
	/**
	 * 獲得點檢記錄
	 * @param params
	 */
	void getCheckRecord(Params params);
	
	/**
	 * 獲得提前維護樓層
	 * @param params
	 */
	void getMTFloor(Params params);
	/**
	 * 獲得提前維護纖體
	 * @param params
	 */
	void getMTLine(Params params);
	/**
	 * 提交提前維護信息
	 * @param params
	 */
	void saveMT(Params params);

}
