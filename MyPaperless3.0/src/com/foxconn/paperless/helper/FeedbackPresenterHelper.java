package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.foxconn.paperless.bean.Feedback;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.util.TextUtil;

/**
 * FeedbackPresenter助手
 *@ClassName FeedbackPresenterHelper
 *@Author wunian
 *@Date 2017/10/27 下午5:58:00
 */
public class FeedbackPresenterHelper {
	/**
	 * 獲得意見反饋記錄集合，封裝到Feedback類
	 * @param result
	 * @return
	 */
	public static List<Feedback> getFeedbackList(JsonResult result){
		List<String> data=result.getData();
		List<Feedback> feedbackList=new ArrayList<Feedback>();
		for (int i = 0; i < data.size(); i+=result.getColumnNum()) {
			Feedback feedback=new Feedback();
			feedback.setLogonName(data.get(i));
			feedback.setChineseName(data.get(i+1));
			feedback.setFeedback(data.get(i+2));
			feedback.setLasteditdt(data.get(i+3));
			feedback.setSite(data.get(i+4));
			feedbackList.add(feedback);
		}
		return feedbackList;
	}
	/**
	 * 添加意見反饋記錄，更新集合數據
	 * @param feedbackList
	 * @param feedbackInfo
	 * @return
	 */
	public static List<Feedback> addFeedback(List<Feedback> feedbackList,
			String[] feedbackInfo) {
		if(feedbackInfo.length==7){
			Feedback feedback=new Feedback();
			feedback.setLogonName(feedbackInfo[0]);
			feedback.setChineseName(feedbackInfo[1]);
			feedback.setSite(feedbackInfo[4]);
			feedback.setLasteditdt(TextUtil.dateFormat(new Date()));
			feedback.setFeedback(feedbackInfo[6]);
			feedbackList.add(0, feedback);
		}
		return feedbackList;
	}
}
