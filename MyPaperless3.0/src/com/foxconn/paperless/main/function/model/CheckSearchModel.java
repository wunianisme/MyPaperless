package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
/**
 * 點檢查詢業務處理
 *@ClassName CheckSearchModel
 *@Author wunian
 *@Date 2017/12/28 下午3:24:59
 */
public interface CheckSearchModel extends Response{
	/**
	 * 獲得BU製造處下面的所有樓層
	 * @param params
	 */
	void getFloorName(Params params);
	/**
	 * 獲得製造處下面的所有線別
	 * @param params
	 */
	void getLineName(Params params);
	/**
	 * 獲得點檢狀態
	 * @param params
	 */
	void getCheckStatus(Params params);


}
