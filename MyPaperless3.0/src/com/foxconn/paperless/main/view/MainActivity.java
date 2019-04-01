package com.foxconn.paperless.main.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.main.presenter.MainPresenter;
import com.foxconn.paperless.main.presenter.MainPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.AppManager;
/**
 * 主頁
 *@ClassName MainActivity
 *@Author wunian
 *@Date 2017/10/10 上午8:46:29
 */
public class MainActivity extends BaseActivity implements MainView,OnCheckedChangeListener{
	
	@ViewInject(id=R.id.tvLeftTitle)TextView tvLeftTitle;
	@ViewInject(id=R.id.tvLeft)TextView tvLeft;
	@ViewInject(id=R.id.tvRight)TextView tvRight;
	@ViewInject(id=R.id.rgBottomBtn)RadioGroup rgBottomBtn;
	@ViewInject(id=R.id.ivScanqr,click="btnClick")ImageView ivScanqr;

	private Context context;
	private MainPresenter mainPresenter;
	private CustomerDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	@Override
	protected void init() {
		context=MainActivity.this;
		AppManager.getInstance().addActivity(this);
		mainPresenter=new MainPresenterImpl(this, context);
		//初始化Fragment
		getFragmentManager().beginTransaction()
			.replace(R.id.mainLayout, new HomeFragment()).commit();
		setMainTitle(R.string.home_title);
		rgBottomBtn.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		mainPresenter.changeFragment(MainActivity.this, checkedId);
	}
	
	@Override
	public void setMainTitle(int titleId) {
		tvLeftTitle.setVisibility(View.VISIBLE);
		tvLeftTitle.setText(titleId);
		tvLeft.setVisibility(View.GONE);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(MyConstant.SERVERNAME);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.ivScanqr:
			mainPresenter.openScanQRPage();
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
	public void showToastFailedMsg(String msg) {
		ToastHelper.showError(context, msg, 0);
		
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
		dismissLoading();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(exit(context, keyCode, event)){
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void goActivityForResult(Intent intent, int requestCode) {
		startActivityForResult(intent, requestCode);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mainPresenter.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void showSelectReportDialog(int titleId, List<String> itemList) {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(
				context, android.R.layout.simple_list_item_1, itemList);
		dialog = new CustomerDialog(context, titleId, adapter, 
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						dialog.dismiss();
						mainPresenter.selectCheckReport(pos);//選擇點檢報表
					}
		}, true);
	}

	@Override
	public void gotoActivity(Class<?> class1, Bundle bundle) {
		goActivity(class1, bundle);
	}

	
}
