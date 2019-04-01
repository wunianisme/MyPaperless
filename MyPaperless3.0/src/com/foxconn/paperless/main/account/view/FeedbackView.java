package com.foxconn.paperless.main.account.view;

import java.util.List;

import com.foxconn.paperless.base.BaseView;
import com.foxconn.paperless.bean.Feedback;

public interface FeedbackView extends BaseView {
	/**
	 * 獲得歷史意見反饋記錄
	 */
	void getHistoryFeedback();
	/**
	 * 設置意見反饋記錄的適配器
	 * @param feedbackList
	 */
	void setFeedbackAdapter(List<Feedback> feedbackList);
	/**
	 * 清空輸入內容
	 */
	void setPublishInputEmpty();
	
	/**
	 * 滑動List條目到指定位置
	 * 
	 */
	void scrollFeedbackList();
}
