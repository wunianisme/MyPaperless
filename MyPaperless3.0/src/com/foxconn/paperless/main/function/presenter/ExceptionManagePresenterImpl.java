package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
import com.foxconn.paperless.main.function.view.ExceptionManageDeatilActivity;
import com.foxconn.paperless.main.function.view.ExceptionManageView;
/**
 * 異常管理邏輯處理
 *@ClassName ExceptionManagePresenterImpl
 *@Author wunian
 *@Date 2018/1/18 下午5:13:00
 */
public class ExceptionManagePresenterImpl implements ExceptionManagePresenter,
		OnModelListener {
	
	private ExceptionManageView view;
	private ExceptionManageModel model;
	private Context context;
	private Euser user;
	private Params params;
	private List<ExceptionFeedback> exceptionList;
	private int type;
	
	private final static int REQUESTCODE_DETAIL=1;

	public ExceptionManagePresenterImpl(ExceptionManageView view,Context context){
		this.view =view;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.model=new ExceptionManageModelImpl(this);
		this.params=new Params(model);
		this.exceptionList=new ArrayList<ExceptionFeedback>();
		this.type=Exception.MYDEAL;
	}

	@Override
	public void success(JsonResult result) {
		view.dismissLoading();
		if(result.getAction().equals(Action.GET_EXCEPTIONINFO)){
			exceptionList=ExceptionManagePresenterHelper
					.getExceptionFeedback(result,type);
			if(type==Exception.MYDEAL){
				view.setMyDealAdapter(exceptionList);
			}
			if(type==Exception.MYCREATE){
				view.setMyCreateAdapter(exceptionList);
			}
			
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_EXCEPTIONINFO)){//暫無異常信息
			view.showToastFailedMsg(R.string.getexceptioninfo_failed);
			exceptionList.clear();
			if(type==Exception.MYDEAL){
				view.setMyDealAdapter(exceptionList);
			}
			if(type==Exception.MYCREATE){
				view.setMyCreateAdapter(exceptionList);
			}
		}
	}
	
	@Override
	public void getMyDealInfo() {
		getExceptionInfo();
	}

	@Override
	public void getMyCreateInfo() {
		getExceptionInfo();
	}
	
	private void getExceptionInfo(){
		params=ParamsUtil.getParam(params, Action.GET_EXCEPTIONINFO, new String[]{
				type+"",
				user.getLogonName()	
		});
		model.getMyExceptionInfo(params);
		view.showLoading();
	}

	@Override
	public void exception(JsonResult result) {
		view.showToastExceptionMsg(result.getResultMsg());
	}

	@Override
	public void setType(int type) {
		this.type=type;
		if(type==Exception.MYDEAL){//处理
			getMyDealInfo();
		}
		if(type==Exception.MYCREATE){//创建
			getMyCreateInfo();
		}
	}

	@Override
	public void goExceptionDetailPage(int pos) {
		Bundle bundle=new Bundle();
		bundle.putInt("type", type);
		bundle.putString("ID", exceptionList.get(pos).getId());
		view.gotoActivityForResult(ExceptionManageDeatilActivity.class,bundle,REQUESTCODE_DETAIL);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_DETAIL:
			if(resultCode==Activity.RESULT_OK){//重新刷新頁面
				getExceptionInfo();
			}
			break;
		default:
			break;
		}
	}

	

}
