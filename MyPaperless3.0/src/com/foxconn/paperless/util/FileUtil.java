package com.foxconn.paperless.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
/**
 * 操作文件的工具類
 *@ClassName FileUtil
 *@Author wunian
 *@Date 2017/12/19 下午2:46:13
 */
public class FileUtil {

	/**
	 * 將圖片保存于本地
	 * @param bm
	 * @param dirPath
	 * @param photoname
	 * @return
	 */
	public static String saveToLocal(Bitmap bm, String dirPath, String photoname) {

		String fileName = photoname + ".png";
		File dir = new File(Environment.getExternalStorageDirectory(), dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File f = new File(dir, fileName);
		String path = f.getAbsolutePath();
		try {
			FileOutputStream fos = new FileOutputStream(path);
			bm.compress(CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return path;
	}
	/**
	 * 獲取本地文件對象
	 * @param path
	 * @return
	 */
	public static File getLocalFile(String path){
		File file=new File(Environment.getExternalStorageDirectory(),path);
		return file;
	}
	
	public static File createLocalDir(String path){
		File dir=new File(Environment.getExternalStorageDirectory(),path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		return dir;
	}
	
	
	/**
	 * 圖片轉換為base64編碼字符串
	 * @param bitmap
	 * @return
	 */
	public static String bitmapToByteString(Bitmap bitmap){
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);//壓縮圖片格式
		InputStream is=new ByteArrayInputStream(bos.toByteArray());
		//使用HTTP提交參數時Flag必須為 Base64.URL_SAFE
		return Base64.encodeToString(bos.toByteArray(), Base64.URL_SAFE);
	}
	/**
	 * base64編碼字符串轉換為圖片
	 * @param byteString
	 * @return
	 */
	public static Bitmap byteStringToBitmap(String byteString){
		Bitmap bitmap = null;
		try {
			byte[] buffer=Base64.decode(byteString, Base64.URL_SAFE);
			bitmap=BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
			Log.i("bitmap",byteString.length()+"");
			Log.i("buffer",buffer.length+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	/**
	 * 縮放圖片為指定寬度和高度
	 * @param imagePath
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	@SuppressLint("NewApi")
	public static Bitmap compressBitmap(String imagePath,float reqWidth,float reqHeight){
		BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);//載入圖片
        int width= options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1; 
        if(height>reqHeight||width>reqWidth){//計算壓損比例（原圖大小比要求的寬、高大時才要縮小）
        	int heightRatio=Math.round((float)height/reqHeight);
        	int widthRatio=Math.round((float)width/reqWidth);
        	inSampleSize=Math.min(heightRatio, widthRatio);//選擇縮小比例更小的作為最終壓縮比例
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件
        Log.i("inSampleSize", inSampleSize+"");
        Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
	}
	/**
	 * 刪除文件夾下的所有文件
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File file) {  
	    if (!file.exists())  
	        return;  
	    if (file.isFile()) {  
	    	file.delete();  
	        return;  
	    }  
	    File[] files = file.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        deleteAllFilesOfDir(files[i]);  
	    }  
	    file.delete();  
	}  
}
