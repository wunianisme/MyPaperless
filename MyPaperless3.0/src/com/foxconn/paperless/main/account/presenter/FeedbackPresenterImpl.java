package com.foxconn.paperless.main.account.presenter;

import java.util.List;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.Feedback;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.helper.FeedbackPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.main.account.model.FeedbackModel;
import com.foxconn.paperless.main.account.model.FeedbackModelImpl;
import com.foxconn.paperless.main.account.view.FeedbackView;
import com.foxconn.paperless.util.TextUtil;

/**
 * 意見反饋邏輯處理
 * 
 * @ClassName FeedbackPresenterImpl
 * @Author wunian
 * @Date 2017/10/27 下午3:10:40
 */
public class FeedbackPresenterImpl implements FeedbackPresenter,
		OnModelListener {

	private Context context;
	private FeedbackView feedbackView;
	private FeedbackModel feedbackModel;
	private LoginModel loginModel;
	private Params feedbackParam;
	//private Params loginParam;
	private Euser user;
	private String[] feedbackInfo;//用戶發表的反饋信息數據
	private List<Feedback> feedbackList;

	public FeedbackPresenterImpl(FeedbackView feedbackView, Context context) {
		this.context = context;
		this.feedbackView = feedbackView;
		this.feedbackModel = new FeedbackModelImpl(this);
		this.loginModel = new LoginModelImpl(this);
		this.user = (Euser) context.getApplicationContext();
		this.feedbackParam = new Params(feedbackModel);
		//this.loginParam = new Params(loginModel);
	}

	@Override
	public void success(JsonResult result) {
		if (result.getAction().equals(Action.GET_FEEDBACK)) {
			feedbackView.dismissLoading();
			// 獲得意見反饋集合列表
			feedbackList = FeedbackPresenterHelper.getFeedbackList(result);
			feedbackView.setFeedbackAdapter(feedbackList);
		}
		if(result.getAction().equals(Action.SAVE_FEEDBACK)){
			feedbackView.showToastSuccessMsg(R.string.publish_success);
			//更新適配器
			feedbackList=FeedbackPresenterHelper.addFeedback(feedbackList,feedbackInfo);
			feedbackView.setFeedbackAdapter(feedbackList);
			feedbackView.setPublishInputEmpty();//發表完成清空輸入內容并失去焦點
		}
	}

	@Override
	public void failed(JsonResult result) {
		if (result.getAction().equals(Action.GET_FEEDBACK)) {
			feedbackView.showToastFailedMsg(R.string.getfeedback_failed);
		}
		if(result.getAction().equals(Action.SAVE_FEEDBACK)){
			feedbackView.showToastFailedMsg(R.string.publish_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		feedbackView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲得歷史意見反饋
	 */
	@Override
	public void getHistoryFeedback() {
		feedbackParam = ParamsUtil.getParam(
				feedbackParam, Action.GET_FEEDBACK,new String[] { "" });
		feedbackModel.getHistoryFeedback(feedbackParam);
		feedbackView.showLoading();
	}
	/**
	 * 提交意見反饋
	 */
	@Override
	public void publishFeedback(String feedback) {
		if (TextUtil.isEmpty(feedback)) {
			feedbackView.showToastFailedMsg(R.string.publish_empty);
		} else {
			//如果在英文環境下用戶的姓名將採用英文名
			String name=loginModel.getLanguageId(context)==Language.CHINESE?
					user.getChineseName():user.getEnglishName();
			feedback = TextUtil.simpleToTradition(feedback);
			feedbackInfo = new String[] { user.getLogonName(), name,
					user.getExt(), user.getEmail(), user.getSite(),
					user.getBg(), feedback };

			feedbackParam = ParamsUtil.getParam(feedbackParam,
					Action.SAVE_FEEDBACK, feedbackInfo);
			feedbackModel.publishFeedback(feedbackParam);
			feedbackView.showLoading();
		}
	}
}
