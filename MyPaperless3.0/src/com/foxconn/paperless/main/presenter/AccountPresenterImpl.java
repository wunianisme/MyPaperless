package com.foxconn.paperless.main.presenter;

import java.io.File;
import org.ksoap2.HeaderProperty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.APK;
import com.foxconn.paperless.constant.MyEnum.Config;
import com.foxconn.paperless.constant.MyEnum.HeadPortrait;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.editimage.EditImageActivity;
import com.foxconn.paperless.helper.LoginPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.main.model.MainModel;
import com.foxconn.paperless.main.model.MainModelImpl;
import com.foxconn.paperless.main.view.AccountView;
import com.foxconn.paperless.util.DownloadManager.OnDownloadListener;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;
import com.foxconn.paperless.util.IntentUtil;
import com.foxconn.paperless.util.PhoneInfo;

/**
 * 賬號Fragment頁面邏輯處理
 *@ClassName AccountPresenterImpl
 *@Author wunian
 *@Date 2017/10/14 上午9:51:31
 */
public class AccountPresenterImpl implements AccountPresenter,OnModelListener,FinalHttpCallback,OnDownloadListener{

	private Context context;
	private AccountView accountView;
	private MainModel mainModel;
	private LoginModel loginModel;
	private Euser user;
	private Params p;
	private File dirFile;//頭像目錄文件
	private File imageFile;//頭像文件
	private String imageFilePath;//頭像文件路徑
	private String downloadUrl;//頭像下載地址
	private String apkUrl;//apk下載地址
	private boolean haveNewVersion;//是否有新版本
	private static final int REQUESTCODE_GALLERY=0x01;
	private static final int REQUESTCODE_CAMERA=0x02;
	private static final int REQUESTCODE_EDITIMAGE=0x03;
	
	public AccountPresenterImpl(AccountView accountView,Context context){
		this.context=context;
		this.accountView=accountView;
		this.user=(Euser) context.getApplicationContext();
		this.loginModel=new LoginModelImpl(this);
		this.mainModel=new MainModelImpl(this);
		this.p=new Params(loginModel);
		this.dirFile=new File(Environment.getExternalStorageDirectory(), HeadPortrait.DIR_PATH);
		this.imageFile=new File(dirFile, user.getLogonName()+".png");
		this.imageFilePath=HeadPortrait.FILE_URI+user.getLogonName()+".png";
		this.downloadUrl=HeadPortrait.DOWNLOAD_URL+user.getLogonName()+".png";
	}
	/**
	 * 獲取工號和姓名（根據語言環境採用不同語言名稱）
	 */
	@Override
	public void getAccountName() {
		String logonName=context.getResources()
				.getString(R.string.eg_logonname)+user.getLogonName();
		
		int languageId=loginModel.getLanguageId(context);
		//根據語言環境，採用對應語言的姓名
		String name=languageId==Language.CHINESE?
				user.getChineseName():user.getEnglishName();
		accountView.showAccountName(logonName,name);
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_APKINFO)){
			apkUrl=result.getData().get(7)+APK.NAME;//apk的下載路徑
			//服務器APK版本
			MyConstant.SERVERVERSION=Integer.parseInt(result.getData().get(1));
			//更新內容
			String message=context.getResources().getString(
					R.string.APK_update_content)+"\n"+result.getData().get(2);	
			accountView.showApkUpdateDialog(R.string.APK_update, message, 
					R.string.btn_updateAPK, R.string.btn_no);
			p.setDBSite(MyConstant.DBSITE);//重新將廠區設置回去
		}
	}

	@Override
	public void failed(JsonResult result) {}

	@Override
	public void exception(JsonResult result) {
		accountView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲取用戶頭像
	 */
	@Override
	public void getHeadPortrait() {
		if(!dirFile.exists()){//判斷目錄是否存在，不存在自動創建
			dirFile.mkdirs();
		}
		if(imageFile.exists()){//本地存在頭像文件
			accountView.setImageBitmap(BitmapFactory.decodeFile(imageFilePath));
		}else{//不存在從服務器獲取
			mainModel.getHeadPortrait(this,downloadUrl,imageFilePath);//下載頭像
		}
	}
	/**
	 * 打開系統圖庫
	 */
	@Override
	public void openGallery() {
		accountView.goActivityForResult(
				IntentUtil.openGallery(),REQUESTCODE_GALLERY);
	}
	/**
	 * 打開相機
	 */
	@Override
	public void openCamera() {
		accountView.goActivityForResult(IntentUtil.openCamera(Uri.fromFile(imageFile)), 
				REQUESTCODE_CAMERA);
	}
	/**
	 * Activity回傳數據
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent=new Intent(context,EditImageActivity.class);
		switch (requestCode) {
		case  REQUESTCODE_GALLERY://系統相冊回傳
			if(resultCode==Activity.RESULT_OK&&data!=null){
				Uri uri=data.getData();
				if(!TextUtils.isEmpty(uri.getAuthority())){//media
					String path=mainModel.ImageMediaData(context,uri);
					if(path==null){
						accountView.showToastFailedMsg(R.string.picture_notexist);//照片不存在
						return;
					}
					intent.putExtra("path", path);
				}else{
					intent.putExtra("path", uri.getPath());
				}
				intent.putExtra("savePath", HeadPortrait.DIR_PATH);//保存目錄
				intent.putExtra("imgFileName", user.getLogonName());//文件名（默認為png）
				accountView.goActivityForResult(intent, REQUESTCODE_EDITIMAGE);
			}
			break;
		case REQUESTCODE_CAMERA://系統相機回傳
			intent.putExtra("path", imageFile.getAbsolutePath());
			intent.putExtra("savePath", HeadPortrait.DIR_PATH);
			intent.putExtra("imgFileName", user.getLogonName());
			accountView.goActivityForResult(intent, REQUESTCODE_EDITIMAGE);
			break;
		case REQUESTCODE_EDITIMAGE://圖片編輯界面回傳
			if(resultCode==Activity.RESULT_OK&&data!=null){
				String path=data.getStringExtra("path");
				//重新設置頭像
				accountView.setImageBitmap(BitmapFactory.decodeFile(path));
				//上傳頭像到服務器
				mainModel.uploadHeadPortrait(this,HeadPortrait.UPLOAD_URL, new File(path));
			}
			break;
		default:
			break;
		}
	}
	//FinalHttpCallback的回調方法
	/**
	 * 獲取服務器頭像成功
	 */
	@Override
	public void downloadSuccess(File file) {
		accountView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
	}
	/**
	 * 獲取服務器頭像失敗
	 */
	@Override
	public void downloadFailure(int errorNo, String strMsg) {
		Log.i("downloadHeadportraitError", strMsg);
	}
	/**
	 * 開始上傳頭像
	 */
	@Override
	public void uploadStart() {}
	/**
	 * 上傳頭像成功
	 */
	@Override
	public void uploadSuccess(Object t) {
		accountView.showToastSuccessMsg(R.string.uploadheadportrait_success);
	}
	/**
	 * 上傳頭像失敗
	 */
	@Override
	public void uploadFailure(int errorNo, String strMsg) {
		String errMsg=context.getResources().getString(R.string.uploadheadportrait_failed)+"\n"+strMsg;
		accountView.showToastExceptionMsg(errMsg);
	}
	
	/**
	 * 切換賬號
	 */
	@Override
	public void logoff() {
		accountView.showLogoffDialog(
				R.string.logoff,R.string.logoff_message,R.string.btn_ok,R.string.btn_no);
	}
	/**
	 * 聯繫我們
	 */
	@Override
	public void contactUs() {
		accountView.showContactUsDialog(R.string.contactus,R.string.contactus_message);
	}
	/**
	 * 檢查APK版本是否是最新版本
	 */
	@Override
	public void checkApkVersion() {
		try {
			haveNewVersion=loginModel.checkApkVersion(context, MyConstant.SERVERVERSION);
			String versionMsg=context.getResources().getString(R.string.newversion)+
					"(V"+ MyConstant.SERVERVERSION+")";
			if(haveNewVersion){
				accountView.showNewVersion(View.VISIBLE,versionMsg);
			}else{
				accountView.showNewVersion(View.GONE,versionMsg);
			}
		} catch (NameNotFoundException e) {
			accountView.showToastExceptionMsg(e.getMessage());
		}
	}
	
	/**
	 * 獲取APK信息
	 */
	@Override
	public void getApkInfo() {
		if(haveNewVersion){//有新版本
			p=ParamsUtil.getParam(p, Action.GET_APKINFO,Config.InitSite,
					new String[]{APK.NAME});
			loginModel.getApkInfo(p);
		}else{
			try {
				PhoneInfo phone=new PhoneInfo(context);
				String versionMsg = context.getResources().getString(
							R.string.versioncode)+phone.getVerCode()+"\n"+
							context.getResources().getString(
									R.string.versionname)+phone.getVersionName();
				accountView.showVersionInfoDialog(
						R.string.nonewversion,versionMsg );
			} catch (Exception e) {
				accountView.showToastExceptionMsg(e.getMessage());
			}
		}
	}
	/**
	 * 下載APK
	 */
	@Override
	public void downloadApk() {
		loginModel.downloadApk(context, apkUrl, this);
	}
	/**
	 * APK下載完成
	 */
	@Override
	public void downloadOver() {
		try {
			p=ParamsUtil.getParam(p, Action.SAVE_DOWNLOADINFO,
					LoginPresenterHelper.getPhoneInfo(context));
			loginModel.saveDownloadInfo(p);
		} catch (NameNotFoundException e) {
			accountView.showToastExceptionMsg(e.getMessage());
		}
	}
}
