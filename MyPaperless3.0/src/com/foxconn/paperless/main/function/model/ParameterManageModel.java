package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;

public interface ParameterManageModel extends Response {
	/**
	 * 獲得製造處的樓層
	 * @param params
	 */
	void getFloorName(Params params);
	/**
	 * 獲得樓層下的線體
	 * @param params
	 */
	void getLineName(Params params);
	/**
	 * 獲得參數基本信息
	 * @param params
	 */
	void getParamInfo(Params params);
	/**
	 * 獲得參數詳細信息
	 * @param params
	 */
	void getParamDetail(Params params);
	/**
	 * 獲得簽核主管信息
	 * @param params
	 */
	void getCheckMaster(Params params);
	/**
	 * 修改參數
	 * @param params
	 */
	void updateParam(Params params);
	/**
	 * 獲得我提交的參數內容
	 * @param params
	 */
	void getMySubmitInfo(Params params);
	/**
	 * 獲得我簽核的參數內容
	 * @param params
	 */
	void getMyCheckInfo(Params params);
	/**
	 * 獲得參數簽核詳細信息
	 * @param params
	 */
	void getAuditDetailInfo(Params params);
	/**
	 * 提交參數簽核通過
	 * @param params
	 */
	void checkPass(Params params);
	/**
	 * 提交參數簽核駁回
	 * @param params
	 */
	void checkFailed(Params params);

}
