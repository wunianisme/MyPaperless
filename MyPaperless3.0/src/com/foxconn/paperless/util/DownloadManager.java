package com.foxconn.paperless.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.constant.MyEnum.APK;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * APK下載管理
 *@ClassName DownloadManager
 *@Author wunian
 *@Date 2017/9/26 下午4:59:24
 */
public class DownloadManager {

	private Context context;
	private String apkUrl;//下載地址
	private boolean stopDownload;//停止下載標記
	//private AlertDialog dialog;
	private CustomerDialog dialog;
	private float progress;
	private String savePath;//保存路徑
	private OnDownloadListener listener;
	
	private static final int DOWNLOAD_UPDATE=0x01;//正在下載
	private static final int DOWNLOAD_OVER=0x02;//下載完成
	
	public DownloadManager(){}
	
	public DownloadManager(Context context,String apkUrl,OnDownloadListener listener) {
		this.context = context;
		this.apkUrl = apkUrl;
		this.listener=listener;
		stopDownload=false;
		savePath=APK.SAVEPATH+APK.NAME;
	}
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case DOWNLOAD_UPDATE://正在下載
				dialog.setProgress((int) progress);
				break;
			case DOWNLOAD_OVER://下載完成
				if(dialog!=null) dialog.cancel();
				installApk();
				listener.downloadOver();//presenter回調
				break;
			default:
				break;
			}
		};
	};
	/**
	 * 安裝APK
	 */
	private void installApk(){
		context.startActivity(IntentUtil.installAPK(savePath));
	}
	/**
	 * 開始下載
	 */
	public void download(){
		showDownloadDialog();
		Thread thread=new Thread(runnable);
		thread.start();
	}
	/**
	 * 顯示下載框
	 */
	private void showDownloadDialog(){
		dialog=new CustomerDialog(context, R.string.download_apk, false);
		dialog.setSavePath(
				context.getResources().getString(R.string.savepath)+"\n"+savePath);
		/*dialog.setOKBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				stopDownload=true;
			}
		});*/
		dialog.setCancelable(false);
	}
	/**
	 * 下載任務
	 */
	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			try{
				URL url=new URL(apkUrl);
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.connect();
				int length=conn.getContentLength();//獲得文件大小
				if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){//連接成功
					InputStream is=conn.getInputStream();
					File saveDir=new File(APK.SAVEPATH);//保存本地目錄
					if(!saveDir.exists()){//自動創建目錄
						saveDir.mkdirs();
					}
					File saveFile=new File(savePath);//保存文件
					OutputStream os=new FileOutputStream(saveFile);
					int count=0;
					byte[] b=new byte[1024];
					while (!stopDownload) {
						int readLength=is.read(b);
						count+=readLength;
						progress=(count*100/length);//下載進度比率
						handler.sendEmptyMessage(DOWNLOAD_UPDATE);
						//Log.i("count/length", count+"/"+length);
						if(readLength<=0){//下載完成
							handler.sendEmptyMessage(DOWNLOAD_OVER);
							break;
						}
						os.write(b, 0, readLength);//寫入本地文件
					}
					os.close();
					is.close();
					conn.disconnect();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	};
	/**
	 * 下載監聽
	 *@ClassName OnDownloadListener
	 *@Author wunian
	 *@Date 2017/9/26 下午5:21:12
	 */
	public interface OnDownloadListener{
		void downloadOver();//下載完成時調用
	}
}
