package com.foxconn.paperless.main.check.presenter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.CheckPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.check.model.CheckModel;
import com.foxconn.paperless.main.check.model.CheckModelImpl;
import com.foxconn.paperless.main.check.view.BUReportView;
/**
 * 各BU報表邏輯處理
 *@ClassName BUReportPresenterImpl
 *@Author wunian
 *@Date 2017/11/3 上午8:06:01
 */
public class BUReportPresenterImpl implements BUReportPresenter ,OnModelListener{

	private Context context;
	private BUReportView buReportView;
	private CheckModel checkModel;
	//private Euser user;
	private Params buReportParam;
	private List<ReportInfo> buReportList;
	
	public BUReportPresenterImpl(BUReportView buReportView,Context context){
		this.buReportView=buReportView;
		this.context=context;
		this.checkModel=new CheckModelImpl(this);
		this.buReportParam=new Params(checkModel);
	}

	@Override
	public void success(JsonResult result) {
		buReportView.dismissLoading();
		if(result.getAction().equals(Action.GET_BU_REPORTNAME)){
			buReportList=CheckPresenterHelper.getBUReport(result);
			buReportView.setReportAdapter(buReportList);
			String totalCount=context.getResources().getString(R.string.totalreport_count)+
					buReportList.size();
			buReportView.setTotalCount(totalCount);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_BU_REPORTNAME)){
			buReportView.showToastFailedMsg(R.string.getreport_failed);
		}
		
	}

	@Override
	public void exception(JsonResult result) {
		buReportView.showToastExceptionMsg(result.getResultMsg());
	}

	@Override
	public void getReport(Activity activity) {
		
		Bundle bundle=activity.getIntent().getExtras();
		String BU=bundle.getString("BU");
		buReportParam=ParamsUtil.getParam(buReportParam, Action.GET_BU_REPORTNAME, new String[]{BU});
		checkModel.getBUReportName(buReportParam);//獲取當前BU的報表信息
		buReportView.showLoading();
		//設置標題
		String title=BU+context.getResources().getString(R.string.allreport);
		buReportView.setTitleName(title);
	}
}
