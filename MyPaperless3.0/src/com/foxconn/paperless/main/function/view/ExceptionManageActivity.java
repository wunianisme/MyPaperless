package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ExceptionFeedback;
import com.foxconn.paperless.constant.MyEnum.Exception;
import com.foxconn.paperless.main.function.presenter.ExceptionManagePresenter;
import com.foxconn.paperless.main.function.presenter.ExceptionManagePresenterImpl;
import com.foxconn.paperless.ui.adapter.ExceptionInfoListViewAdapter;
import com.foxconn.paperless.ui.widget.ToastHelper;
/**
 * 異常管理
 *@ClassName ExceptionManageActivity
 *@Author wunian
 *@Date 2018/1/18 下午4:58:27
 */
public class ExceptionManageActivity extends BaseActivity implements
		ExceptionManageView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	
	@ViewInject(id=R.id.rgMenu)RadioGroup rgMenu;
	@ViewInject(id=R.id.lvExceptionItem)ListView lvExceptionItem;
	
	private Context context;
	private ExceptionManagePresenter presenter;
	private int type;
	private ExceptionInfoListViewAdapter dealAdapter;
	private ExceptionInfoListViewAdapter createAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exceptionmanage);
		init();
	}
	
	@Override
	protected void init() {
		context=ExceptionManageActivity.this;
		presenter=new ExceptionManagePresenterImpl(this, context);
		type=Exception.MYDEAL;
		tvTitle.setText(R.string.exception_manage);
		presenter.getMyDealInfo();
		lvExceptionItem.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				
				presenter.goExceptionDetailPage(pos);
			}
		});
		rgMenu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//ToastHelper.showInfo(context, "type:"+type, 0);
				switch (checkedId) {
				case R.id.rbMyDeal://處理
					type=Exception.MYDEAL;
					presenter.setType(type);
					presenter.getMyDealInfo();
					break;
				case R.id.rbMyCreate://创建
					type=Exception.MYCREATE;
					presenter.setType(type);
					presenter.getMyCreateInfo();
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
	public void setMyDealAdapter(List<ExceptionFeedback> exceptionList) {
		dealAdapter=new ExceptionInfoListViewAdapter(context, exceptionList, type);
		lvExceptionItem.setAdapter(dealAdapter);
		
	}

	@Override
	public void setMyCreateAdapter(List<ExceptionFeedback> exceptionList) {
		createAdapter=new ExceptionInfoListViewAdapter(context, exceptionList, type);
		lvExceptionItem.setAdapter(createAdapter);
	}

	@Override
	public void gotoActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		goActivityForResult(cls, bundle, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		presenter.onActivityResult(requestCode, resultCode, data);
	}

}
