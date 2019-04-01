package com.foxconn.paperless.util;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
/**
 * 意圖工具類
 *@ClassName IntentUtil
 *@Author wunian
 *@Date 2017/10/19 上午10:13:46
 */
public class IntentUtil {
	/**
	 * 打開系統相機並且保存圖片到特定的路徑下
	 * @param uri
	 * @return
	 */
	public static Intent openCamera(Uri uri){
		Intent intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
		return intent;
	}
	/**
	 * 打開圖庫
	 * @return
	 */
	public static Intent openGallery(){
		Intent intent=new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_PICK);
		return intent;
	}
	/**
	 * APK自動安裝
	 * @param savePath
	 * @return
	 */
	public static Intent installAPK(String savePath){
		Intent intent=new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		//Android实现应用下载并自动安装apk包
		intent.setDataAndType(Uri.parse("file://"+savePath), "application/vnd.android.package-archive");
		return intent;
	}
}
