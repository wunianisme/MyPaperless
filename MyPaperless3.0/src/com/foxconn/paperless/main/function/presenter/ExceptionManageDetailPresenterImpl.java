package com.foxconn.paperless.main.function.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.ExceptionFeedback;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Exception;
import com.foxconn.paperless.helper.ExceptionManagePresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ExceptionManageModel;
import com.foxconn.paperless.main.function.model.ExceptionManageModelImpl;
import com.foxconn.paperless.main.function.view.ExceptionManageDetailView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.FileUtil;
import com.foxconn.paperless.util.TextUtil;
import com.foxconn.paperless.util.DownloadManager.OnDownloadListener;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;
/**
 * 異常管理異常詳細信息邏輯處理
 *@ClassName ExceptionManageDetailPresenterImpl
 *@Author wunian
 *@Date 2018/1/19 下午5:54:53
 */
public class ExceptionManageDetailPresenterImpl implements
		ExceptionManageDetailPresenter, OnModelListener,FinalHttpCallback,OnDownloadListener {
	
	private ExceptionManageDetailView view;
	private ExceptionManageModel model;
	private Context context;
	private Euser user;
	private Params params;
	private ExceptionFeedback exception;
	private int type;
	private String id;
	private String[] imgNameArray;//上傳圖片名稱集合
	private int index;//下載圖片對應的索引

	public ExceptionManageDetailPresenterImpl(ExceptionManageDetailView view,Context context){
		this.view =view;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.model=new ExceptionManageModelImpl(this);
		this.params=new Params(model);
		this.exception=new ExceptionFeedback();
		this.type=Exception.MYDEAL;
	}


	@Override
	public void success(JsonResult result) {
		view.dismissLoading();
		if(result.getAction().equals(Action.GET_EXCEPTION_DETAILINFO)){
			exception=ExceptionManagePresenterHelper.getExceptionFeedbackDetail(result,type);
			showExceptionDetail();
			if(!TextUtil.isEmpty(exception.getPictureUrl())){//有上傳圖片，從服務器下載圖片
				downloadImg();
			}
		}
		if(result.getAction().equals(Action.UPDATE_EXCEPTION_DEALSTATE)){
			view.showToastSuccessMsg(R.string.submitdealstate_success);
			view.back();
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_EXCEPTION_DETAILINFO)){
			view.showToastFailedMsg(R.string.getexceptiondetail_failed);
		}
		if(result.getAction().equals(Action.UPDATE_EXCEPTION_DEALSTATE)){
			view.showToastFailedMsg(R.string.submitdealstate_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		view.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 下載圖片
	 */
	private void downloadImg(){
		File dir=FileUtil.createLocalDir(Exception.SAVE_DIR);
		imgNameArray=exception.getPictureUrl().split(Exception.SPLIT);
		for (int i = 0; i < imgNameArray.length; i++) {
			String downloadUrl=Exception.DOWNLOAD_URL+imgNameArray[i];
			String filePath=Exception.SAVE_PATH+imgNameArray[i];
			File file=new File(filePath);
			if(!file.exists()){//判斷本地是否存在該文件，不存在則從服務器獲取，否則直接加載
				model.downloadImg(this,downloadUrl,filePath);
			}else{
				view.loadingImage(file,i);
			}
		}
	}
	/**
	 * 顯示異常詳細信息
	 */
	private void showExceptionDetail(){
		int dealStateId=R.string.dealstate_review;
		//獲得處理狀態
		if(exception.getDealState().equals(Exception.DEALSTATE_REVIEW)){//待處理
			dealStateId=R.string.dealstate_review;
		}
		if(exception.getDealState().equals(Exception.DEALSTATE_OK)){//處理完成
			dealStateId=R.string.dealstate_ok;
		}
		if(exception.getDealState().equals(Exception.DEALSTATE_REJECT)){//駁回
			dealStateId=R.string.dealstate_reject;
		}
		//獲得處理或提交人
		String userName=exception.getFromUser();
		int btnLayoutVisible=View.GONE;//是否顯示底部按鈕，默認不顯示
		int picLayoutVisible=View.VISIBLE;//是否顯示上傳圖片項，默認顯示
		
		if(TextUtil.isEmpty(exception.getPictureUrl())){
			picLayoutVisible=View.GONE;
		}
		if(type==Exception.MYDEAL){
			if(exception.getDealState().equals(Exception.DEALSTATE_REVIEW)){//待處理
				btnLayoutVisible=View.VISIBLE;
			}
			userName=exception.getFromUser();
			view.showBtnLayout(btnLayoutVisible,picLayoutVisible,R.string.ec_fromuser);
		}
		if(type==Exception.MYCREATE){
			view.showBtnLayout(btnLayoutVisible,picLayoutVisible,R.string.ec_touser);
			userName=exception.getToUser();
		}
		view.showExceptionDetail(exception,dealStateId,userName);
	}


	@Override
	public void init(int type, String id) {
		this.type=type;
		this.id=id;
	}

	/**
	 * 獲得異常詳細信息
	 */
	@Override
	public void getExceptionDetail() {
		params=ParamsUtil.getParam(params, Action.GET_EXCEPTION_DETAILINFO, new String[]{
			type+"",id	
		});
		model.getExceptionDetail(params);
		view.showLoading();
	}
	/**
	 * 通過
	 */
	@Override
	public void pass(String backContent) {
		if(TextUtil.isEmpty(backContent)){
			view.showToastFailedMsg(R.string.backcontent_empty);
			return;
		}
		submitDealState(backContent, Exception.DEALSTATE_OK);
	}
	/**
	 * 駁回
	 */
	@Override
	public void reject(String backContent) {
		if(TextUtil.isEmpty(backContent)){
			view.showToastFailedMsg(R.string.rejectcontent_empty);
			return;
		}
		submitDealState(backContent, Exception.DEALSTATE_REJECT);
	}

	/**
	 * 提交異常處理結果
	 */
	private void submitDealState(String backContent,String dealState){
		backContent=TextUtil.simpleToTradition(backContent);
		params=ParamsUtil.getParam(params, Action.UPDATE_EXCEPTION_DEALSTATE, new String[]{
				dealState,backContent,id
		});
		model.submitDealState(params);
		view.showLoading();
	}


	@Override
	public void downloadOver() {}

	
	@Override
	public void downloadSuccess(File file) {
		view.loadingImage(file,index);//加載圖片
		index++;
	}


	@Override
	public void downloadFailure(int errorNo, String strMsg) {
		if(index==0)//只顯示第一次加載失敗信息
			view.showToastFailedMsg(R.string.loadingimg_failed);
		index++;
	}

	@Override
	public void uploadStart() {}

	@Override
	public void uploadSuccess(Object t) {}

	@Override
	public void uploadFailure(int errorNo, String strMsg) {}
}
