package com.foxconn.paperless.main.function.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;

public interface ExceptionManageModel extends Response {
	/**
	 * 获得异常记录
	 * @param params
	 */
	void getMyExceptionInfo(Params params);
	/**
	 * 獲得異常詳細信息
	 * @param params
	 */
	void getExceptionDetail(Params params);
	/**
	 * 提交異常處理結果
	 * @param params
	 */
	void submitDealState(Params params);
	/**
	 * 圖片下載
	 * @param callback
	 * @param downloadUrl
	 * @param savePath
	 */
	void downloadImg(
			FinalHttpCallback callback,String downloadUrl, String savePath);

}
