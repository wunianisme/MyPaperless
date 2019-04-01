package com.foxconn.paperless.main.function.view;

/**
 * 已簽核列表——詳細信息列表
 */

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.main.function.presenter.AuditBaseInfoPresenter;
import com.foxconn.paperless.main.function.presenter.AuditBaseInfoPresenterImpl;
import com.foxconn.paperless.ui.adapter.audit.AuditBaseInfoListViewAdapter;


public class AuditBaseInfoActivity extends BaseActivity implements AuditBaseInfoView {

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.lvBaseInfo)ListView lvBaseInfo;
	private Context context;
	private AuditBaseInfoPresenter auditBaseInfoPresenter;
	private String checkType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditbaseinfo);
		init();
	}

	@Override
	protected void init() {
		context=AuditBaseInfoActivity.this;
		auditBaseInfoPresenter=new AuditBaseInfoPresenterImpl(this,context);
		tvTitle.setText(R.string.baseinfo_title);
		Bundle bundle=getIntent().getExtras();
		String RNO=bundle.getString("RNO");
		String reportNum=bundle.getString("reportNum");
		String reportName=bundle.getString("reportName");
		checkType=bundle.getString("checkType");
		auditBaseInfoPresenter.getAuditBaseInfo(RNO,reportNum,reportName,checkType);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
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
	public void setAuditBaseInfoAdapter(List<CheckInfo> checkInfoList) {
		AuditBaseInfoListViewAdapter adapter=new AuditBaseInfoListViewAdapter(
				context, checkInfoList,auditBaseInfoPresenter,checkType);
		lvBaseInfo.setAdapter(adapter);
	}
	
	@Override
	public void gotoActivity(Class<?> class1, Bundle bundle) {
		goActivity(class1, bundle);
	}

	@Override
	public void gotoActivityForResult(Class<?> class1,
			Bundle bundle, int requestCode) {
		goActivityForResult(class1, bundle, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		auditBaseInfoPresenter.onActivityResult(requestCode, resultCode, data);
	}

}
