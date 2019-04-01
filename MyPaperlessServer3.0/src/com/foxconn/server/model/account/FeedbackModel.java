package com.foxconn.server.model.account;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;

public interface FeedbackModel {

	/**
	 * 獲得意見反饋記錄
	 * @param p
	 * @return
	 */
	JsonResult getFeedback(MyParam p);
	/**
	 * 添加意見反饋記錄
	 * @param p
	 * @return
	 */
	JsonResult saveFeedback(MyParam p);
	
	

}
