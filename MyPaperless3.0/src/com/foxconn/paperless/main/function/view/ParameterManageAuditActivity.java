package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.main.function.presenter.ParameterManageAuditPresenter;
import com.foxconn.paperless.main.function.presenter.ParameterManageAuditPresenterImpl;
import com.foxconn.paperless.ui.adapter.ParamAuditInfoListViewAdapter;
/**
 * 參數簽核消息
 *@ClassName ParameterManageAuditActivity
 *@Author wunian
 *@Date 2018/1/11 上午10:07:05
 */
public class ParameterManageAuditActivity extends BaseActivity implements
		ParameterManageAuditView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	
	@ViewInject(id=R.id.rgMenu)RadioGroup rgMenu;
	@ViewInject(id=R.id.lvParamItem)ListView lvParamItem;
	
	private Context context;
	private ParameterManageAuditPresenter presenter;
	private String reportNum;
	private int type;
	private ParamAuditInfoListViewAdapter submitAdapter;
	private ParamAuditInfoListViewAdapter checkAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametermanageaudit);
		init();
	}
	
	@Override
	protected void init() {
		context=ParameterManageAuditActivity.this;
		presenter=new ParameterManageAuditPresenterImpl(this, context);
		tvTitle.setText(R.string.paramaudit_title);
		Bundle bundle=getIntent().getExtras();
		reportNum=bundle.getString("reportNum");
		presenter.init(reportNum);
		presenter.getMyCheckInfo();
		lvParamItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				presenter.goAuditDetailPage(pos);
			}
		});
		rgMenu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbReview://我要簽核的
					type=ParamManage.AUDITTYPE_MYCHECK;
					presenter.setAuditType(type);
					presenter.getMyCheckInfo();
					break;
				case R.id.rbSubmit://我提交的簽核
					type=ParamManage.AUDITTYPE_MYSUBMIT;
					presenter.setAuditType(type);
					presenter.getMySubmitInfo();
					break;
				
				default:
					break;
				}
			}
		});
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			scrollParamItem();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 滾動點檢條目
	 */
	private void scrollParamItem() {
		
		lvParamItem.post(new Runnable() {
			
			@Override
			public void run() {
				BaseAdapter adapter = null;
				if(type==ParamManage.AUDITTYPE_MYCHECK){
					adapter=checkAdapter;
				}
				if(type==ParamManage.AUDITTYPE_MYSUBMIT){
					adapter=submitAdapter;
				}
				if(adapter!=null){
					adapter.notifyDataSetChanged();//必須加上這句才能實現滑動
					if(lvParamItem.getFirstVisiblePosition()!=0){//滾動到頂部
						lvParamItem.setSelection(0);
					}else{
						lvParamItem.setSelection(adapter.getCount()-1);
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
	public void setSubmitInfoAdapter(List<ParamInfo> paramInfoList) {
		submitAdapter=new ParamAuditInfoListViewAdapter(context, paramInfoList,
				ParamManage.AUDITTYPE_MYSUBMIT);
		lvParamItem.setAdapter(submitAdapter);
	}

	@Override
	public void setCheckInfoAdapter(List<ParamInfo> paramInfoList) {
		checkAdapter=new ParamAuditInfoListViewAdapter(context, paramInfoList,
				ParamManage.AUDITTYPE_MYCHECK);
		lvParamItem.setAdapter(checkAdapter);
	}

	@Override
	public void refreshSubmitInfoAdapter() {
		if(submitAdapter!=null) submitAdapter.notifyDataSetChanged();
	}

	@Override
	public void refreshCheckInfoAdapter() {
		if(checkAdapter!=null) checkAdapter.notifyDataSetChanged();
	}

	@Override
	public void gotoActivity(Class<?> cls, Bundle bundle) {
		goActivity(cls, bundle);
	}

	@Override
	public void gotoActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		goActivityForResult(cls, bundle, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResult(requestCode, resultCode, data);
	}

}
