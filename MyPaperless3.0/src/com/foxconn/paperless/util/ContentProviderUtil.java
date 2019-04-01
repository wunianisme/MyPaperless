package com.foxconn.paperless.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
/**
 * 內容提供者工具類
 *@ClassName ContentProviderUtil
 *@Author wunian
 *@Date 2017/10/19 上午10:03:32
 */
public class ContentProviderUtil {
	/**
	 * 獲取手機圖片資源路徑數據
	 * @return
	 */
	public static String getImageMediaData(Context context,Uri uri){
		Cursor cursor =context.getContentResolver().query(
				uri, new String[] { MediaStore.Images.Media.DATA },null, null, null);
		if(cursor==null){
			return null;
		}
		cursor.moveToFirst();
		String path = cursor.getString(cursor
				.getColumnIndex(MediaStore.Images.Media.DATA));//獲得圖片在手機上的文件路徑
		cursor.close();
		return path;
	}
}
