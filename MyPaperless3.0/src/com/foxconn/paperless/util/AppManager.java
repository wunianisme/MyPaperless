package com.foxconn.paperless.util;

import java.util.Stack;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
/**
 * Activity管理
 *@ClassName AppManager
 *@Author wunian
 *@Date 2017/9/12 下午2:09:04
 */
public class AppManager {

	private static AppManager manager;
	private static Stack<Activity> activityStack;
	
	private AppManager(){}
	//單例模式,獲得唯一實例
	public static AppManager getInstance(){
		if(manager==null){
			manager=new AppManager();
		}
		return manager;
	}
	//網棧中添加一個Activity
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	//獲得棧頂的Activity
	public Activity getTopActivity(){
		Activity topActivity=activityStack.lastElement();
		return topActivity;
	}
	//殺掉棧頂的Activity
	public void killTopActivity(){
		Activity topActivity=activityStack.lastElement();
		killActivity(topActivity);
	}
	//殺掉指定的Activity
	public void killActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}
	//殺掉指定類名的Activity
	public void killActivity(Class<?> cls){
		for(Activity activity:activityStack){
			if(activity.getClass().equals(cls)){
				killActivity(activity);
			}
		}
	}
	//殺掉所有的Activity
	public void killAllActivity(){
		for(Activity activity:activityStack){
			if(activity!=null){
				activity.finish();
			}
		}
		activityStack.clear();
	}
	//退出APP
	@SuppressWarnings("deprecation")
	public void appExit(Context context){
		killAllActivity();
		try {
			ActivityManager activityManager=(ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityManager.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
