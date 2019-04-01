package com.foxconn.paperless.login.view;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.login.presenter.UpdatePwdPresenter;
import com.foxconn.paperless.login.presenter.UpdatePwdPresenterImpl;
import com.foxconn.paperless.main.view.MainActivity;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 賬號密碼修改
 *@ClassName UpdatePwdActivity
 *@Author wunian
 *@Date 2017/9/20 上午11:05:08
 */
public class UpdatePwdActivity extends BaseActivity implements UpdatePwdView{

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.etOldPwd)EditText etOldPwd;
	@ViewInject(id=R.id.etNewPwd)EditText etNewPwd;
	@ViewInject(id=R.id.etConfirmNewPwd)EditText etConfirmNewPwd;
	@ViewInject(id=R.id.btnUpdatePwd,click="btnClick")Button btnUpdatePwd;
	
	private Context context;
	private UpdatePwdPresenter updatePwdPresenter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepwd);
		init();
	}
	
	@Override
	protected void init() {
		context=UpdatePwdActivity.this;
		updatePwdPresenter=new UpdatePwdPresenterImpl(this,context);
		tvTitle.setText(R.string.updatepwd_title);
		//ToastHelper.showInfo(context, tvTitle.getText().toString(), 0);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.btnUpdatePwd://提交修改密碼
			updatePwdPresenter.checkUpdate(
					etOldPwd.getText().toString(), 
					etNewPwd.getText().toString(), 
					etConfirmNewPwd.getText().toString());
			break;
		default:
			break;
		}
	}
	
	@Override
	public void showSuccessDialog(int titleId,int msgId,int textId) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, msgId,true);
		dialog.setOKBtn(textId, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				//goActivityThenFinish(MainActivity.class, null);
			}
		});
	}

	@Override
	public void showToastSuccessMsg(int strId) {}

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
}
