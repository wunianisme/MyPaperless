package com.foxconn.paperless.main.check.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.main.check.presenter.BUReportPresenter;
import com.foxconn.paperless.main.check.presenter.BUReportPresenterImpl;
import com.foxconn.paperless.ui.adapter.ReportListViewAdapter;
/**
 * 各BU報表編號、名稱信息
 *@ClassName BUReportActivity
 *@Author wunian
 *@Date 2017/11/2 下午5:01:37
 */
public class BUReportActivity extends BaseActivity implements BUReportView{
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.tvCount)TextView tvCount;
	@ViewInject(id=R.id.lvReport)ListView lvReport;
	
	private Context context;
	private BUReportPresenter buReportPresenter;
	private ReportListViewAdapter buReportListViewAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bureport);
		init();
	}
	
	@Override
	protected void init() {
		context=BUReportActivity.this;
		buReportPresenter=new BUReportPresenterImpl(this, context);
		buReportPresenter.getReport(this);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			ScrollTo(0);
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
	public void setReportAdapter(List<ReportInfo> buReportList) {
		buReportListViewAdapter=new ReportListViewAdapter(context, buReportList);
		lvReport.setAdapter(buReportListViewAdapter);
		
	}

	@Override
	public void setTitleName(String title) {
		tvTitle.setText(title);
	}

	@Override
	public void setTotalCount(String totalCount) {
		tvCount.setText(totalCount);
	}

	@Override
	public void ScrollTo(final int pos) {
		lvReport.post(new Runnable() {
			
			public void run() {
				if(buReportListViewAdapter!=null){
					buReportListViewAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					lvReport.setSelection(pos);
				}
			}
		});
		
	}

}
