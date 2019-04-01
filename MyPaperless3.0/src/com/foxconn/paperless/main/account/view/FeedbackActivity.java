package com.foxconn.paperless.main.account.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.Feedback;
import com.foxconn.paperless.main.account.presenter.FeedbackPresenter;
import com.foxconn.paperless.main.account.presenter.FeedbackPresenterImpl;
import com.foxconn.paperless.ui.adapter.FeedbackListViewAdapter;
/**
 * 意見反饋
 *@ClassName FeedbackActivity
 *@Author wunian
 *@Date 2017/10/28 上午11:36:17
 */
public class FeedbackActivity extends BaseActivity implements FeedbackView{
	
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.lvFeedback)ListView lvFeedback;
	@ViewInject(id=R.id.etPublish)EditText etPublish;
	@ViewInject(id=R.id.btnPublish,click="btnClick")Button btnPublish;
	
	private Context context;
	private FeedbackPresenter feedbackPresenter;
	private FeedbackListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		solveAndroidBug5497();
		init();
		getHistoryFeedback();
	}
	
	@Override
	protected void init() {
		context=FeedbackActivity.this;
		feedbackPresenter=new FeedbackPresenterImpl(this, context);
		tvTitle.setText(R.string.feedback);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollFeedbackList();
			break;
		case R.id.btnPublish:
			feedbackPresenter.publishFeedback(etPublish.getText().toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		showSuccess(strId);
	}

	@Override
	public void showToastFailedMsg(int strId) {
		showError(strId);
	}

	@Override
	public void showToastExceptionMsg(String msg) {
		showException(msg);
	}

	@Override
	public void showLoading() {
		showLoadingDialog();
	}

	@Override
	public void dismissLoading() {
		dismissLoadingDialog();
	}

	@Override
	public void getHistoryFeedback() {
		feedbackPresenter.getHistoryFeedback();
	}

	@Override
	public void setFeedbackAdapter(List<Feedback> feedbackList) {
		if(adapter==null){
			adapter=new FeedbackListViewAdapter(context, feedbackList, feedbackPresenter);
			lvFeedback.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void setPublishInputEmpty() {
		etPublish.setText("");
		etPublish.clearFocus();//清除焦點
	}

	@Override
	public void scrollFeedbackList() {
		lvFeedback.post(new Runnable() {
			
			@Override
			public void run() {
				if(adapter!=null){
					adapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvFeedback.getFirstVisiblePosition()!=0){//滾動到頂部
						lvFeedback.setSelection(0);
					}else{
						lvFeedback.setSelection(adapter.getCount()-1);
					}
				}
			}
		});
	}
}
