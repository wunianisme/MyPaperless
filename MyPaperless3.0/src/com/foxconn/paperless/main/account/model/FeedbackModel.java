package com.foxconn.paperless.main.account.model;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.http.WebServiceUtil.Response;

public interface FeedbackModel extends Response{
	/**
	 * 獲得歷史反饋記錄
	 * @param p
	 */
	void getHistoryFeedback(Params p);
	/**
	 * 添加意見反饋記錄
	 * @param feedbackParam
	 */
	void publishFeedback(Params p);

}
