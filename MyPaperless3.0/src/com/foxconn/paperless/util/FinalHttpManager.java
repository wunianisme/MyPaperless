package com.foxconn.paperless.util;

import java.io.File;
import java.io.FileNotFoundException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
/**
 * FinalHttp上傳下載管理類
 *@ClassName FinalHttpManager
 *@Author wunian
 *@Date 2017/10/17 下午5:33:32
 */
public class FinalHttpManager  {

	private FinalHttp finalHttp;
	private FinalHttpCallback callback;
	
	public FinalHttpManager(FinalHttpCallback callback){
		this.finalHttp=new FinalHttp();
		this.callback=callback;
	}
	/**
	 * 文件下載
	 * @param url
	 * @param target
	 */
	public void download(final String url,final String target){
		
		finalHttp.download(url, target, new AjaxCallBack<File>() {
			
			@Override
			public void onSuccess(File t) {
				super.onSuccess(t);
				callback.downloadSuccess(t);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				callback.downloadFailure(errorNo, strMsg);
			}
		});
	}
	/**
	 * 
	 * @param url
	 * @param file
	 */
	public void uploadHeadPortrait(String url,File file){
		AjaxParams params=new AjaxParams();
		try {
			params.put("File", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		post(url, params);
		
	}
	/**
	 * 文件上傳（POST方式）
	 * @param url
	 * @param params
	 */
	public void post(String url,AjaxParams params){
		finalHttp.post(url, params, new AjaxCallBack<Object>() {
			@Override
			public void onStart() {
				super.onStart();
				callback.uploadStart();
			}
			
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				callback.uploadSuccess(t);
			}
			
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				callback.uploadFailure(errorNo, strMsg);
			}
		});
	}
	
	/**
	 * 回調接口
	 *@ClassName FinalHttpCallback
	 *@Author wunian
	 *@Date 2017/10/17 下午5:34:02
	 */
	public interface FinalHttpCallback{
		void downloadSuccess(File file);
		
		void downloadFailure(int errorNo,String strMsg);
		
		void uploadStart();
		
		void uploadSuccess(Object t);
		
		void uploadFailure(int errorNo,String strMsg);
	} 
}
