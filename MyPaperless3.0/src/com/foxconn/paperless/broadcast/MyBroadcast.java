package com.foxconn.paperless.broadcast;

import com.foxconn.paperless.constant.MyAction.BroadcastAction;
import com.foxconn.paperless.ui.widget.ToastHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 *廣播接收者
 *@ClassName MyBroadcast
 *@Author wunian
 *@Date 2017/9/11 下午4:03:05
 */
public class MyBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action=intent.getAction();
		if(action.equals(BroadcastAction.FromNetWorkService)){//網絡服務
			String msg=intent.getStringExtra("msg");
			ToastHelper.showInfo(context, msg, 0);
			//listener.onNetWorkChanged();
        }
	}
}
