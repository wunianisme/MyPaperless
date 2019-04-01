package com.foxconn.paperless.login.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.login.presenter.ForgetPwdPresenter;
import com.foxconn.paperless.login.presenter.ForgetPwdPresenterImpl;
/**
 * 忘記密碼
 *@ClassName ForgetPwdActivity
 *@Author wunian
 *@Date 2017/9/22 下午5:20:11
 */
public class ForgetPwdActivity extends BaseActivity implements ForgetPwdView,OnItemSelectedListener{

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle; 
	@ViewInject(id=R.id.etLoginName)EditText etLoginName;
	@ViewInject(id=R.id.etEmail)EditText etEmail;
	@ViewInject(id=R.id.spFactory)Spinner spFactory;
	@ViewInject(id=R.id.ibDropdown,click="btnClick")ImageButton ibDropdown;
	@ViewInject(id=R.id.btnGetEmail,click="btnClick")Button btnGetEmail;
	@ViewInject(id=R.id.btnFindPwd,click="btnClick")Button btnFindPwd;
	
	private Context context;
	private ForgetPwdPresenter forgetPwdPresenter;
	private ArrayAdapter<String> adapter;
	private int pos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgetpwd);
		init();
		getServer();
	}
	
	@Override
	protected void init() {
		context=ForgetPwdActivity.this;
		forgetPwdPresenter=new ForgetPwdPresenterImpl(this,context);
		spFactory.setOnItemSelectedListener(this);
		tvTitle.setText(R.string.forgetpwd_title);
		etLoginName.setText(getIntent().getExtras().getString("logonName"));//獲取用戶名
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void getServer() {//從登錄頁面獲取傳遞參數：服務器信息、語言ID
		List<ServerConfig> serverList=(List<ServerConfig>) getIntent().getExtras().getSerializable("serverList");
		int languageId=getIntent().getExtras().getInt("languageId");
		forgetPwdPresenter.setFactoryAdapter(serverList,languageId);
	}

	@Override
	public void setServerAdapter(List<String> serverName) {
		if(adapter==null){
			adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, serverName);
			spFactory.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.ibDropdown://服務器下拉選擇
			spFactory.performClick();
			break;
		case R.id.btnGetEmail://獲得郵箱地址
			forgetPwdPresenter.getUserEmail(etLoginName.getText().toString(),pos);
			break;
		case R.id.btnFindPwd://發送密碼到用戶郵箱
			forgetPwdPresenter.sendPasswordToEmail(etLoginName.getText().toString(),
					etEmail.getText().toString(), pos);
			break;
		default:
			break;
		}
	}

	@Override
	public void inputEmail(String email) {
		etEmail.setText(email);
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
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		this.pos=pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}
	
}
