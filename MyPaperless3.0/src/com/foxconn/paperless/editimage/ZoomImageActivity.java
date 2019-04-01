package com.foxconn.paperless.editimage;


import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.util.FileUtil;
import com.foxconn.paperless.util.TextUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
/**
 * 放大、缩小、移动圖片
 *@ClassName ZoomImageActivity
 *@Author wunian
 *@Date 2017/11/21 下午2:37:31
 */
public class ZoomImageActivity extends Activity {

	private ImageView matrixImage;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zoomimage);
		init();
	}
	
	public void init(){
		matrixImage=(ImageView) findViewById(R.id.matrixImage);
		String imageData=getIntent().getExtras().getString("imageData");//獲得照片數據
		context=ZoomImageActivity.this;
		if(!TextUtil.isEmpty(imageData)){//傳遞base64編碼圖片數據
			matrixImage.setImageBitmap(FileUtil.byteStringToBitmap(imageData));//顯示照片
		}else{//傳遞的是圖片在本地的路徑
			String imgPath=getIntent().getExtras().getString("imgPath");
			matrixImage.setImageBitmap(BitmapFactory.decodeFile(imgPath));
		}
		
	}
}
