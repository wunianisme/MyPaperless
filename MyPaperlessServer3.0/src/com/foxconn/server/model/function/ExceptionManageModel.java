package com.foxconn.server.model.function;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface ExceptionManageModel {
	/**
	 * 獲得異常信息
	 * @param p
	 * @return
	 */
	JsonResult getExceptionInfo(MyParam p);
	/**
	 * 獲得詳細異常信息
	 * @param p
	 * @return
	 */
	JsonResult getExceptionDetailInfo(MyParam p);
	/**
	 * 更改異常處理結果
	 * @param p
	 * @return
	 */
	JsonResult updateExceptionDealState(MyParam p);

}
