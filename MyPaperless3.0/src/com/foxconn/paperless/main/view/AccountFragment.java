package com.foxconn.paperless.main.view;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.login.view.UpdatePwdActivity;
import com.foxconn.paperless.main.account.view.AccountInfoActivity;
import com.foxconn.paperless.main.account.view.EmployeeInfoActivity;
import com.foxconn.paperless.main.account.view.FeedbackActivity;
import com.foxconn.paperless.main.presenter.AccountPresenter;
import com.foxconn.paperless.main.presenter.AccountPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
/**
 * 賬戶
 *@ClassName AccountFragment
 *@Author wunian
 *@Date 2017/10/10 上午8:49:20
 */
public class AccountFragment extends Fragment implements AccountView,OnClickListener{

	@ViewInject(id=R.id.headLayout,click="btnClick")LinearLayout headLayout;
	@ViewInject(id=R.id.ivHead,click="btnClick")ImageView ivHead;
	@ViewInject(id=R.id.tvName)TextView tvName;
	@ViewInject(id=R.id.tvLogonName)TextView tvLogonName;
	@ViewInject(id=R.id.tvNewVersion)TextView tvNewVersion;
	@ViewInject(id=R.id.employeeInfoLayout,click="btnClick")
	LinearLayout employeeInfoLayout;
	@ViewInject(id=R.id.pwdUpdateLayout,click="btnClick")
	LinearLayout pwdUpdateLayout;
	@ViewInject(id=R.id.versionUpdateLayout,click="btnClick")
	LinearLayout versionUpdateLayout;
	@ViewInject(id=R.id.feedbackLayout,click="btnClick")
	LinearLayout feedbackLayout;
	@ViewInject(id=R.id.contactUsLayout,click="btnClick")
	LinearLayout contactUsLayout;
	@ViewInject(id=R.id.btnLogoff,click="btnClick")Button btnLogoff;
	
	private Context context;
	private MainActivity activity;
	private AccountPresenter accountPresenter;
	private Dialog dialog;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_account, container, false);
		//注入AFinal視圖(必須加上才能使用AFinal註解)
		FinalActivity.initInjectedView(this, view);
		init();
		return view;
	}
	
	public void init() {
		context=getActivity();
		accountPresenter=new AccountPresenterImpl(this, context);
		accountPresenter.getAccountName();//獲取賬號名稱、工號
		//accountPresenter.getHeadPortrait(ivHead);//獲取用戶頭像
		accountPresenter.getHeadPortrait();
		accountPresenter.checkApkVersion();
	}
	
	@Override
	public void showAccountName(String logonName, String name) {
		tvName.setText(name);
		tvLogonName.setText(logonName);
	}
	
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.headLayout://賬號信息
			startActivity(new Intent(getActivity(),AccountInfoActivity.class));
			break;
		case R.id.ivHead://更換頭像
			dialog=DialogHelper.showChoosePictureDialog(context, R.layout.layout_choosepicdialog, this);
			break;
		case R.id.employeeInfoLayout://人員信息
			startActivity(new Intent(getActivity(),EmployeeInfoActivity.class));
			break;
		case R.id.pwdUpdateLayout://修改密碼
			startActivity(new Intent(getActivity(),UpdatePwdActivity.class));
			break;
		case R.id.versionUpdateLayout://版本更新
			accountPresenter.getApkInfo();
			break;
		case R.id.feedbackLayout:
			startActivity(new Intent(getActivity(),FeedbackActivity.class));
			break;
		case R.id.contactUsLayout:
			accountPresenter.contactUs();
			break;
		case R.id.btnLogoff:
			accountPresenter.logoff();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGallery://啟動系統相冊
			accountPresenter.openGallery();
			dialog.cancel();
			break;
		case R.id.btnCamera://啟動系統相機
			accountPresenter.openCamera();
			dialog.cancel();
			break;
		case R.id.btnCancel://取消
			dialog.cancel();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=(MainActivity) activity;
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		ToastHelper.showInfo(context, strId, 0);
	}

	@Override
	public void showToastFailedMsg(int strId) {
		ToastHelper.showError(context, strId, 0);
	}

	@Override
	public void showToastExceptionMsg(String msg) {
		activity.showToastExceptionMsg(msg);
		//ToastHelper.showException(context, msg, 0);
	}

	@Override
	public void showLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dismissLoading() {
		// TODO Auto-generated method stub
	}

	@Override
	public void goActivityForResult(Intent intent,int requestCode) {
		startActivityForResult(intent, requestCode);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		accountPresenter.onActivityResult(requestCode,resultCode, data);
	}

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		ivHead.setImageBitmap(bitmap);
		
	}
	
	@Override
	public void showNewVersion(int visible, String versionMsg) {
		tvNewVersion.setText(versionMsg);
		tvNewVersion.setVisibility(visible);
	}
	
	@Override
	public void showContactUsDialog(int titleId, int msgId) {
		new CustomerDialog(context, titleId, msgId, true);
	}

	@Override
	public void showLogoffDialog(int titleId, int msgId, int btnOk,
			int btnNo) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, msgId, true);
		dialog.setOKBtn(btnOk, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				getActivity().finish();
			}
		});
		dialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	public void showApkUpdateDialog(int titleId, String message,
			int btnOk, int btnNo) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, message, false);
		dialog.setOKBtn(btnOk, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				accountPresenter.downloadApk();
			}
		});
		dialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	public void showVersionInfoDialog(int titleId, String versionMsg) {
		new CustomerDialog(context, titleId, versionMsg, true);
	}
}
