package com.foxconn.paperless.editimage;

import net.tsz.afinal.annotation.view.ViewInject;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.Euser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * 圖片編輯
 * 
 * @ClassName CropImageActivity
 * @Author wunian
 * @Date 2017/10/19 上午10:50:43
 */
public class EditImageActivity extends BaseActivity {

	@ViewInject(id = R.id.viewImg)
	EditImageView mImageView;
	@ViewInject(id = R.id.btnSave, click = "btnClick")
	Button mSave;
	@ViewInject(id = R.id.btnCancel, click = "btnClick")
	Button mCancel;
	@ViewInject(id = R.id.btnTurnLeft, click = "btnClick")
	Button rotateLeft;
	@ViewInject(id = R.id.btnTurnRight, click = "btnClick")
	Button rotateRight;

	private Bitmap mBitmap;
	private Euser userBean;
	private EditImage mCrop;
	private String mPath = "CropImageActivity";
	public int screenWidth = 0;
	public int screenHeight = 0;
	public static final int SHOW_PROGRESS = 2000;
	public static final int REMOVE_PROGRESS = 2001;
	
	
	private String savePath;//圖片保存目錄
	private String imgFileName;//文件名（不含擴展名）
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case SHOW_PROGRESS:
				showLoadingDialog();
				break;
			case REMOVE_PROGRESS:
				mHandler.removeMessages(SHOW_PROGRESS);
				dismissLoadingDialog();
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editimage);
		init();
	}

	@Override
	protected void init() {
		userBean = (Euser) getApplicationContext();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		//獲取傳遞過來的參數
		mPath = getIntent().getStringExtra("path");//圖片源路徑
		savePath=getIntent().getStringExtra("savePath");//目標保存路徑
		imgFileName=getIntent().getStringExtra("imgFileName");//文件名（不含擴展名）
		try {
			mBitmap = createBitmap(mPath, screenWidth, screenHeight);
			if (mBitmap == null) {
				finish();
			} else {
				resetImageView(mBitmap);
			}
		} catch (Exception e) {
			finish();
		}
	}
	/**
	 * 重新設置圖像
	 * @param b
	 */
	private void resetImageView(Bitmap b) {
		mImageView.clear();
		mImageView.setImageBitmap(b);
		mImageView.setImageBitmapResetBase(b, true);
		mCrop = new EditImage(this, mImageView, mHandler);
		mCrop.crop(b);
	}

	public void btnClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel://取消
			mCrop.cropCancel();
			Intent intent1 = new Intent();
			setResult(RESULT_CANCELED, intent1);
			finish();
			break;
		case R.id.btnSave://保存
			/*String path = mCrop.saveToLocal(mCrop.cropAndSave(),
					userBean.getLogonName());*/
			String path=mCrop.saveToLocal(mCrop.cropAndSave(), 
					savePath, imgFileName);
			Intent intent = new Intent();
			intent.putExtra("path", path);
			setResult(RESULT_OK, intent);
			finish();
			break;
		case R.id.btnTurnLeft://左轉
			mCrop.startRotate(270.f);
			break;
		case R.id.btnTurnRight://右轉
			mCrop.startRotate(90.f);
			break;

		}
	}
	/**
	 * 創建圖像
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public Bitmap createBitmap(String path, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inSampleSize = (int) ratio + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			return BitmapFactory.decodeFile(path, newOpts);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBitmap != null) {
			mBitmap = null;
		}
	}
}
