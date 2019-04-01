package com.foxconn.paperless.login.view;

import java.util.List;
import java.util.Map;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.login.presenter.LoginPresenter;
import com.foxconn.paperless.login.presenter.LoginPresenterImpl;
import com.foxconn.paperless.service.NetWorkService;
import com.foxconn.paperless.ui.adapter.AccountListViewAdapter;
import com.foxconn.paperless.ui.adapter.SelectLanguageListViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.AppManager;
/**
 * 用户登陆
 *@ClassName LoginActivity
 *@Author wunian
 *@Date 2017/9/9 上午8:05:12
 */
public class LoginActivity extends BaseActivity implements LoginView,OnItemSelectedListener,TextWatcher,OnTouchListener {

	@ViewInject(id=R.id.etLoginName)EditText etLoginName;
	@ViewInject(id=R.id.etPassword)EditText etPassword;
	@ViewInject(id=R.id.spFactory) Spinner spFactory;
	/*@ViewInject(id=R.id.tvChinese,click="btnClick") TextView tvChinese;*/
	/*@ViewInject(id=R.id.tvEnglish,click="btnClick") TextView tvEnglish;*/
	@ViewInject(id=R.id.tvLanguage,click="btnClick") TextView tvLanguage;
	@ViewInject(id=R.id.tvForgetPwd,click="btnClick")TextView tvForgetPwd;
	@ViewInject(id=R.id.btnLogin,click="btnClick") Button btnLogin;
	@ViewInject(id=R.id.ibMoreLoginName,click="btnClick")ImageButton ibMoreLoginName;
	@ViewInject(id=R.id.ibShowPwd)ImageButton ibShowPwd;
	@ViewInject(id=R.id.ibDropdown,click="btnClick")ImageButton ibDropdown;
	
	private Context context;
	private LoginPresenter loginPresenter;
	private Intent intent;
	private ArrayAdapter<String> adapter;
	private int sitePos;
	private ListView accountListView;
	private PopupWindow pop;
	private AccountListViewAdapter accountAdapter;
	private CustomerDialog languageDialog;
	private CustomerDialog apkUpdateDialog;
	private boolean updateNow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		initLanguage();//初始化語言
		checkApkVersion();//檢測APK版本
		//getServer();//獲得服務器信息（改為在獲得最後一次登錄的賬號密碼之後執行）
		getLastLoginAccout();//獲得最後一次登錄的賬號密碼
	}
	
	@Override
	public void initLanguage() {
		loginPresenter.initLanguage();
	}
	
	@Override
	protected void init() {
		context=LoginActivity.this;
		AppManager.getInstance().addActivity(this);
		loginPresenter=new LoginPresenterImpl(this,context);
		spFactory.setOnItemSelectedListener(this);
		ibShowPwd.setOnTouchListener(this);
		updateNow=false;
		//綁定網絡服務
		intent=new Intent(context,NetWorkService.class);
		bindService(intent,conn,Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onResume() {
		loginPresenter.setAccountListView();
		super.onResume();
	}
	
	@Override
	public void getServer() {
		loginPresenter.getServer();
	}
	
	@Override
	public void getLastLoginAccout() {
		loginPresenter.getLastLoginAccount();
		//為了防止測試賬號連續獲取兩次服務器信息產生報錯（初始化一次，默認輸入最後一次登錄賬號一次），在輸入完成后才綁定文本監聽器
		etLoginName.addTextChangedListener(this);
	}
	
	@Override
	public void checkApkVersion() {
		loginPresenter.checkApkVersion();
	}
	
	@Override
	public void showApkUpdateDialog(int titleId, String message,
			int positiveId, int negativeId) {
		apkUpdateDialog=new CustomerDialog(context, titleId, message, false);
		apkUpdateDialog.setOKBtn(positiveId, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				apkUpdateDialog.dismiss();
				updateNow=true;
				loginPresenter.downloadApk();
			}
		});
		apkUpdateDialog.setNoBtn(negativeId, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				apkUpdateDialog.dismiss();
				LoginActivity.this.finish();
			}
		});
		apkUpdateDialog.setCancelable(false);
		
	}
	
	@Override
	public void setServerAdapter(List<String> serverName) {
		//不能直接将传进来的参数赋给serverName,必须重新add数据，否则下拉框的UI将无法刷新
		if(adapter==null){
			adapter=new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, serverName);
			spFactory.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
	}
	
	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.btnLogin://登陸
			loginPresenter.validateLogin(etLoginName.getText().toString(), 
					etPassword.getText().toString(),sitePos);
			break;
		case R.id.ibMoreLoginName://選擇下拉賬號
			loginPresenter.setAccountPopupWindow();
			break;
		case R.id.ibDropdown://選擇廠區
			spFactory.performClick();//下拉框點擊
			break;
		/*case R.id.tvChinese://中文
			loginPresenter.setLanguage(Language.CHINESE,etLoginName.getText().toString(), 
					etPassword.getText().toString());
			break;
		case R.id.tvEnglish://英文
			loginPresenter.setLanguage(Language.ENGLISH,etLoginName.getText().toString(), 
					etPassword.getText().toString());
			break;*/
		case R.id.tvLanguage://語言
			loginPresenter.selectLanguage();
			break;
		case R.id.tvForgetPwd:
			loginPresenter.goForgetPwdActivity(etLoginName.getText().toString());
			break;
		default:
			break;
		}
	}
	
	@Override
	public void showSelectLanguageDialog(int languageId){
		String[] languageArray=context.getResources()
				.getStringArray(R.array.language_array);
		SelectLanguageListViewAdapter languageAdapter=new 
				SelectLanguageListViewAdapter(context, languageArray, languageId);
		languageDialog=new CustomerDialog(context, 
				R.string.selectlanguage_title, 
				languageAdapter, new OnItemClickListener() {
			
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						languageDialog.dismiss();
						loginPresenter.setLanguage(pos,etLoginName.getText().toString(), 
								etPassword.getText().toString());
					}
		}, true);
	}
	
	
	
	@Override
	public void setAccountListView(Map<String,String> map) {
		accountListView=new ListView(context);
		accountListView.setVerticalScrollBarEnabled(false);
		accountListView.setBackgroundColor(Color.WHITE);
		accountAdapter=new AccountListViewAdapter(context, map, loginPresenter);
		accountListView.setAdapter(accountAdapter);
	}
	
	@Override
	public void setAccountPopupWindow(int size) {
		if(pop==null){
			pop=new PopupWindow(context);
			pop.setWidth(etLoginName.getWidth());
			pop.setHeight(etLoginName.getHeight()*size);
			pop.setContentView(accountListView);
		}
	//	pop.setFocusable(true);//添加这个属性后Activity的onTouch事件将会被屏蔽
		pop.setOutsideTouchable(true);
		pop.showAsDropDown(etLoginName, 0, 0);
	}
	
	@Override
	public void updateAccountPopupWindow(int size) {
		pop.update(etLoginName.getWidth(), etLoginName.getHeight()*size);
	}
	
	@Override
	public void inputAccount(String logonName,String password) {
		etLoginName.setText(logonName);
		etPassword.setText(password);
		etLoginName.setSelection(etLoginName.getText().length());//光標顯示在末尾
		setAccountDropDownIcon(R.drawable.arrow_down);
	}
	
	@Override
	public void gotoActivity(Class<?> cls,Bundle bundle) {
		goActivity(cls,bundle);
	}
	
	
	@Override
	public void showUpdatePwdDialog(int titleId,int msgId,int textId) {
		final CustomerDialog dialog=new CustomerDialog(context, titleId, msgId, true);
		dialog.setOKBtn(textId, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				goActivity(UpdatePwdActivity.class, null);
			}
		});
		
	}
	
	@Override
	public void showPassword() {
		etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		etPassword.setSelection(etPassword.getText().length());//光標顯示在末尾
	}

	@Override
	public void hidePassword() {
		etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		etPassword.setSelection(etPassword.getText().length());//光標顯示在末尾
	}
	
	@Override
	public void setAccountDropDownIcon(int drawable) {
		if(drawable!=0){
			ibMoreLoginName.setBackgroundResource(drawable);
		}else{
			ibMoreLoginName.setBackground(null);
		}
		if(drawable==R.drawable.arrow_down&&pop!=null) pop.dismiss();
	}
	
	@Override
	public void refreshView() {
		refreshActivity(LoginActivity.class);
	}
	
	@Override
	public void selectChineseLanguage() {
		/*tvChinese.setTextColor(getResources().getColor(R.color.blue_text));
		tvEnglish.setTextColor(getResources().getColor(R.color.gray_hint));*/
	}

	@Override
	public void selectEnglishLanguage() {
		/*tvChinese.setTextColor(getResources().getColor(R.color.gray_hint));
		tvEnglish.setTextColor(getResources().getColor(R.color.blue_text));*/
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
	public boolean onTouchEvent(MotionEvent event) {
		loginPresenter.touchToDropUp(event.getAction());
		return super.onTouchEvent(event);
	}
	
	//onItemSelectListener
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		sitePos=pos;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

	//TextWatcher
	@Override
	public void afterTextChanged(Editable s) {}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		loginPresenter.onTextChanged(s.toString());
	}
	//onTouchListener 按住眼睛 密碼可視化
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		loginPresenter.togglePassword(event.getAction());
		return false;
	}
	
	private ServiceConnection conn =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			loginPresenter.getNetWorkService(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {}
	};
	
	protected void onPause() {
		super.onPause();
		if(pop!=null) pop=null;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(conn!=null) unbindService(conn);
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
	public void showToastFailedMsg(String errorMsg) {
		ToastHelper.showError(context, errorMsg, 0);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*if(keyCode == KeyEvent.KEYCODE_BACK 
				&& event.getAction() == KeyEvent.ACTION_DOWN){
			if((apkUpdateDialog!=null&&apkUpdateDialog.isShowing())){
				//apkUpdateDialog.show();
				return false;
			}
		}*/
		
		return super.onKeyDown(keyCode, event);
	}
}
