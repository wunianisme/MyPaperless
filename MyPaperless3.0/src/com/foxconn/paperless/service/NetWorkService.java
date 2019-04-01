package com.foxconn.paperless.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.constant.MyAction.BroadcastAction;
import com.foxconn.paperless.constant.MyEnum.NetWorkType;
import com.foxconn.paperless.login.presenter.LoginPresenter;
import com.foxconn.paperless.util.NetWorkManager;

/**
 * 獲取當前網絡狀態的服務
 *@ClassName NetWorkService
 *@Author wunian
 *@Date 2017/9/11 下午4:30:01
 */
public class NetWorkService extends Service {

	private Intent intent;
	private Context context;
	private LoginPresenter loginPresenter;
	private  IBinder binder;
	private String preNetWork;
	private String nowNetWork;
	
	public class LocalBinder extends Binder{
		/**
		 * 获得服务对象
		 * @return
		 */
		public NetWorkService getService(){
			return NetWorkService.this;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	/**
	 * 监听网络变化的广播
	 */
	private BroadcastReceiver receiver=new  BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			sendBroadcast();
			//ToastHelper.showInfo(getApplicationContext(), "loginView:"+loginView, 0);
			if(loginPresenter!=null) loginPresenter.onNetWorkChanged(preNetWork, nowNetWork);
		}
	};
	
	/**
	 * 獲得loginView對象
	 * @param loginView
	 */
	public void setLoginPresenter(LoginPresenter loginPresenter){
		this.loginPresenter=loginPresenter;
	}
	
	@Override
	public void onCreate() {//第一次创建服务时调用
		super.onCreate();
		context=NetWorkService.this;
		binder=new LocalBinder();
		nowNetWork=NetWorkType.WIFI;
		preNetWork=NetWorkType.WIFI;
		//动态注册广播
		IntentFilter intentFilter=new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);//监听网络变化的Action
		registerReceiver(receiver, intentFilter);
	}
	
	/**
	 * 发送广播 需要开线程
	 */
	public void sendBroadcast(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				//保存上一次的連接狀態
				preNetWork=nowNetWork;
				String connectTo=context.getResources().getString(R.string.connectedto);
				//獲取網絡狀態
				nowNetWork=NetWorkManager.getNetWorkType(context);
				String networkMsg=nowNetWork;
				//拼接網絡提示信息
				if(nowNetWork.equalsIgnoreCase(NetWorkType.WIFI)){
					networkMsg=connectTo+NetWorkManager.getWifiName(context);;
				}
				if(nowNetWork.equalsIgnoreCase(NetWorkType.MOBILE)){
					String mobileNetWork=context.getResources().getString(R.string.mobile_network);
					networkMsg=connectTo+mobileNetWork;
				}
				//发送网络变化的广播
				intent=new Intent();
				intent.putExtra("msg", networkMsg);
				intent.setAction(BroadcastAction.FromNetWorkService);
				sendBroadcast(intent);
			}
		}).start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(receiver!=null)
		unregisterReceiver(receiver);//删除广播
		Log.i("NetWorkService", "The service destroy!");
		stopSelf();
	}
}
