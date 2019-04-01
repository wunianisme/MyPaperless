package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.main.function.presenter.ReportConfigPresenter;
import com.foxconn.paperless.main.function.presenter.ReportConfigPresenterImpl;
import com.foxconn.paperless.ui.adapter.ReportListViewAdapter;
/**
 * 報表配置
 *@ClassName ReportConfigActivity
 *@Author wunian
 *@Date 2018/1/5 下午6:20:26
 */
public class ReportConfigActivity extends BaseActivity implements
		ReportConfigView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.actvReport)AutoCompleteTextView actvReport;
	@ViewInject(id=R.id.ibSearch,click="btnClick")ImageButton ibSearch;
	@ViewInject(id=R.id.tvCount)TextView tvCount;
	@ViewInject(id=R.id.lvReport)ListView lvReport;
	
	private Context context;
	private ReportConfigPresenter reportConfigPresenter;
	private ReportListViewAdapter reportAdapter;
	private ArrayAdapter<String> reportNameAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportconfig);
		init();
	}
	
	@Override
	protected void init() {
		context=ReportConfigActivity.this;
		reportConfigPresenter=new ReportConfigPresenterImpl(this, context);
		tvTitle.setText(R.string.report_config);
		reportConfigPresenter.getMFGReport();
		lvReport.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				reportConfigPresenter.goReportConfigDetailPage(pos);
			}
		});
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollReportItem();
			break;
		case R.id.ibSearch:
			reportConfigPresenter.search(actvReport.getText().toString());
			break;
		default:
			break;
		}
	}

	/**
	 * 滾動報表條目
	 */
	private void scrollReportItem() {
		lvReport.post(new Runnable() {
			
			@Override
			public void run() {
				if(reportAdapter!=null){
					reportAdapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvReport.getFirstVisiblePosition()!=0){//滾動到頂部
						lvReport.setSelection(0);
					}else{
						lvReport.setSelection(reportAdapter.getCount()-1);
					}
				}
			}
		});
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
	public void setReportAdapter(List<ReportInfo> reportInfoList) {
		reportAdapter=new ReportListViewAdapter(context, reportInfoList);
		lvReport.setAdapter(reportAdapter);
		
	}

	@Override
	public void showCountInfo(String countInfo) {
		tvCount.setText(countInfo);
	}

	@Override
	public void gotoActivity(Class<?> cls, Bundle bundle) {
		goActivity(cls, bundle);
	}

	@Override
	public void setReportNameAdapter(List<String> reportNameList) {
		reportNameAdapter=new ArrayAdapter<String>(context,
				R.layout.layout_spinner_item, reportNameList);
		actvReport.setAdapter(reportNameAdapter);
	}

}
