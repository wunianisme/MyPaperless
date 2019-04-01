package com.foxconn.paperless.base;

import net.tsz.afinal.FinalActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.ui.widget.LoadingProgressDialog;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.AndroidBug5497Workaround;
import com.foxconn.paperless.util.AppManager;

/**
 *Activity父类
 *继承自FinalActivity,使用Afinal注解进行UI绑定和事件绑定
 *@ClassName BaseActivity
 *@Author wunian
 *@Date 2017/9/1 上午11:01:14
 */
public class BaseActivity extends FinalActivity {
	
	private LoadingProgressDialog loadingDialog;
	private long delay;
	private static final int OVERTIME=0x01;
	
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case OVERTIME://超時關閉加載框
				dismissLoadingDialog();
				ToastHelper.showError(BaseActivity.this, R.string.connect_overtime, 0);
				break;
			default:
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);
		if (Build.VERSION.SDK_INT >= 19) {//android 4.4 以上 沉浸式状态栏
			Window window =getWindow();
			window.addFlags(67108864);
		}
		loadingDialog=new LoadingProgressDialog(this);//進度框
	}
	
	protected void init(){}
	/**
	 * 解決沉浸式状态栏產生的bug
	 */
	protected void solveAndroidBug5497(){
		AndroidBug5497Workaround.assistActivity(this);//解决沉浸式状态栏下产生的一些bug
	}
	
	/**
	 * 顯示進度框
	 */
	protected void showLoadingDialog() {
		loadingDialog.show();
		//20秒后還在loading自動關閉加載框      add in 2018/04/13
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if(loadingDialog.isShowing()){
					Message msg=handler.obtainMessage(OVERTIME);
					handler.sendMessage(msg);
				}
			}
		}, 20000);
	}
	/**
	 * 關閉進度框
	 */
	protected void dismissLoadingDialog() {
		if(loadingDialog.isShowing()) loadingDialog.dismiss();
	}
	
	/**
	 * Activity跳转
	 * @param cls 目标Activity
	 * @param bundle 打包传递参数
	 */
	protected void goActivity(Class<?> cls,Bundle bundle){
		Intent intent =new Intent(this,cls);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
		//finish();
	}
	/**
	 * Activity跳转并且关闭当前Activity
	 * @param cls 目标Activity
	 * @param bundle 打包传递参数
	 */
	protected void goActivityThenFinish(Class<?> cls,Bundle bundle){
		goActivity(cls, bundle);
		finish();
	}
	/**
	 * 刷新Activity
	 * @param cls
	 */
	protected void refreshActivity(Class<?> cls){
		Intent intent=new Intent(this,cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	/**
	 * Activity跳转到另一个页面，退出时返回数据
	 * @param cls 目标Activity
	 * @param bundle 打包传递参数
	 * @param requestCode 请求码
	 */
	protected void goActivityForResult(Class<?> cls,Bundle bundle,int requestCode){
		Intent intent =new Intent(this,cls);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 顯示成功信息
	 * @param strId
	 */
	protected void showSuccess(int strId){
		dismissLoadingDialog();
		ToastHelper.showInfo(this, strId, 0);
	}
	/**
	 * 顯示錯誤信息
	 * @param strId
	 */
	protected void showError(int strId){
		dismissLoadingDialog();
		ToastHelper.showError(this, strId, 0);
	}
	/**
	 * 顯示異常信息
	 * @param errMsg
	 */
	protected void showException(String msg){
		dismissLoadingDialog();
		String errorMsg=getResources().getString(
				R.string.network_exception)+msg;
		ToastHelper.showException(this, errorMsg, 0);
	}
	/**
	 * 退出APP
	 * @param context
	 * @param keyCode
	 * @param event
	 * @return
	 */
	protected boolean exit(Context context,int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK 
				&& event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-delay) > 2000){  
	        	ToastHelper.showInfo(context, R.string.pressagain_exit, 0);
	            delay = System.currentTimeMillis();
	        } 
	        else {
	          AppManager.getInstance().appExit(context);
	        }
	        return true;   
	    }
		return false;
	}
	/**
	 * 退出當前頁面
	 */
	protected void cancel(Context context,int keyCode,KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK 
				&& event.getAction() == KeyEvent.ACTION_DOWN){
			//退出對話框，防止用戶誤觸退出鍵
			final CustomerDialog dialog=new CustomerDialog(context,R.string.cancel_tip,R.string.cancel_message,true);
			dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					finish();
				}
			});
			dialog.setNoBtn(R.string.btn_no, new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}   
	}
}
