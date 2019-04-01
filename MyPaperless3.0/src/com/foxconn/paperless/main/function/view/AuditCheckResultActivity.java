package com.foxconn.paperless.main.function.view;


import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.main.function.presenter.AuditCheckResultPresenter;
import com.foxconn.paperless.main.function.presenter.AuditCheckResultPresenterImpl;
import com.foxconn.paperless.ui.adapter.audit.AuditCheckResultListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 已簽核列表——詳細點檢信息
 *@ClassName AuditCheckResultActivity
 *@Author wunian
 *@Date 2018/1/4 上午11:15:46
 */
public class AuditCheckResultActivity extends BaseActivity implements AuditCheckResultView {

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.lvCheckResult)ListView lvCheckResult;
	//無點檢數據相關顯示控件
	@ViewInject(id=R.id.noDataLayout)LinearLayout noDataLayout;
	@ViewInject(id=R.id.tvReportName)TextView tvReportName;
	@ViewInject(id=R.id.tvCheckId)TextView tvCheckId;
	@ViewInject(id=R.id.tvReason)TextView tvReason;
	
	//底部控件
	@ViewInject(id=R.id.btnLayout)LinearLayout btnLayout;
	@ViewInject(id=R.id.remarkLayout)LinearLayout remarkLayout;
	@ViewInject(id=R.id.btnPass,click="btnClick")Button btnPass;
	@ViewInject(id=R.id.btnReject,click="btnClick")Button btnReject;
	@ViewInject(id=R.id.tvCheckBy)TextView tvCheckBy;
	@ViewInject(id=R.id.tvCheckStatus)TextView tvCheckStatus;
	@ViewInject(id=R.id.tvRemark)TextView tvRemark;
	
	
	private Context context;
	private AuditCheckResultPresenter auditCheckResultPresenter;
	private String checkType;
	private CustomerDialog rejectDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditcheckresult);
		init();
	}
	
	@Override
	protected void init() {
		context=AuditCheckResultActivity.this;
		auditCheckResultPresenter=new AuditCheckResultPresenterImpl(this, context);
		tvTitle.setText(R.string.checkresult_title);
		//獲取上一個頁面傳遞過來的參數
		Bundle bundle=getIntent().getExtras();
		checkType=bundle.getString("checkType");
		String reportNum=bundle.getString("reportNum");
		String reportName=bundle.getString("reportName");
		String RNO=bundle.getString("RNO");
		String checkId=bundle.getString("checkId");
		String checkSeqno=bundle.getString("checkSeqno");
		String checkRemark=bundle.getString("checkRemark");
		String checkRemarkReason=bundle.getString("checkRemarkReason");
		String checkBy=bundle.getString("checkBy");
		String checkByName=bundle.getString("checkByName");
		String checkStatus=bundle.getString("checkStatus");
		String preCheckStatus=bundle.getString("preCheckStatus");//前一個簽核人的簽核狀態
		String preCheckId=bundle.getString("preCheckId");//前一個簽核人的簽核節次
		//獲取詳細點檢信息
		auditCheckResultPresenter.getCheckResult(checkType,reportNum,reportName,RNO,checkId,
				checkSeqno,checkRemark,checkRemarkReason,checkBy,checkByName,
				checkStatus,preCheckStatus,preCheckId);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.btnPass:
			showSubmitPassDialog();
			break;
		case R.id.btnReject:
			showSubmitRejectDialog();
			break;
		default:
			break;
		}
	}
	
	private void showSubmitPassDialog(){
		final CustomerDialog dialog=new CustomerDialog(context, R.string.audit_tip,
				R.string.auditpass_msg, false);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				auditCheckResultPresenter.submitPass();
			}
		});
		dialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
	private void showSubmitRejectDialog(){
		String title=context.getResources().getString(R.string.audit_tip);
		String hint=context.getResources().getString(R.string.auditreject_hint);
		rejectDialog=new CustomerDialog(context, title, hint, null, false);
		rejectDialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				auditCheckResultPresenter.submitReject(rejectDialog.getContent());
			}
		});
		rejectDialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rejectDialog.dismiss();
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
	public void setCheckResultAdapter(List<CheckResult> checkResultList) {
		AuditCheckResultListViewAdapter adapter=new AuditCheckResultListViewAdapter(
				context, checkResultList, auditCheckResultPresenter, checkType);
		lvCheckResult.setAdapter(adapter);
		
	}

	@Override
	public void showCheckByName(String checkByName,String checkStatus,int textColor) {
		tvCheckBy.setText(checkByName);
		tvCheckStatus.setText(checkStatus);
		tvCheckStatus.setTextColor(textColor);
	}

	@Override
	public void showNoDataLayout(String reportName, String checkId,
			String reason) {
		lvCheckResult.setVisibility(View.GONE);
		noDataLayout.setVisibility(View.VISIBLE);
		tvReportName.setText(reportName);
		tvCheckId.setText(checkId);
		tvReason.setText(reason);
	}

	@Override
	public void showSubmitBtn() {
		btnLayout.setVisibility(View.VISIBLE);
	}

	@Override
	public void showRejectRemark(String remark) {
		remarkLayout.setVisibility(View.VISIBLE);
		tvRemark.setText(remark);
	}

	@Override
	public void back() {
		if(rejectDialog!=null&&rejectDialog.isShowing()) rejectDialog.dismiss();
		setResult(RESULT_OK);
		AuditCheckResultActivity.this.finish();
	}

	@Override
	public void gotoActivity(Class<?> class1, Bundle bundle) {
		goActivity(class1, bundle);
	}

}
