package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.AuditSearchPresenterHelper;
import com.foxconn.paperless.helper.ReportConfigPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ReportConfigModel;
import com.foxconn.paperless.main.function.model.ReportConfigModelImpl;
import com.foxconn.paperless.main.function.view.ReportConfigDetailActivity;
import com.foxconn.paperless.main.function.view.ReportConfigView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 報表配置邏輯處理
 *@ClassName ReportConfigPresenterImpl
 *@Author wunian
 *@Date 2018/1/5 下午5:12:47
 */
public class ReportConfigPresenterImpl implements ReportConfigPresenter,OnModelListener {

	private ReportConfigView reportConfigView;
	private Context context;
	private ReportConfigModel reportConfigModel;
	private Euser user;
	private Params params;
	private List<ReportInfo> allReportInfoList;
	private List<ReportInfo> reportInfoList; 
	private List<String> reportNameList;
	
	public ReportConfigPresenterImpl(ReportConfigView reportConfigView,Context context){
		this.reportConfigView=reportConfigView;
		this.context=context;
		this.reportConfigModel=new ReportConfigModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(reportConfigModel);
		this.allReportInfoList=new ArrayList<ReportInfo>();
		this.reportInfoList=new ArrayList<ReportInfo>();
		this.reportNameList=new ArrayList<String>();
	}

	@Override
	public void success(JsonResult result) {
		reportConfigView.dismissLoading();
		if(result.getAction().equals(Action.GET_MFG_REPORT)){//獲得製造處下的報表
			allReportInfoList=ReportConfigPresenterHelper.getReportInfo(result);
			reportInfoList=allReportInfoList;
			setReportAdapter();
			//獲得報表名列表
			reportNameList=AuditSearchPresenterHelper.getReportName(reportInfoList);
			reportConfigView.setReportNameAdapter(reportNameList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_MFG_REPORT)){//暫無可配置的報表
			reportConfigView.showToastFailedMsg(R.string.getsbureport_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		reportConfigView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 設置報表信息列表的適配器
	 */
	private void setReportAdapter(){
		reportConfigView.setReportAdapter(reportInfoList);
		//顯示報表條數
		String countInfo=context.getResources().getString(R.string.selectreport_config)
				+"（"+reportInfoList.size()+"）";
		reportConfigView.showCountInfo(countInfo);
	}
	/**
	 * 獲得整個製造處下面的報表
	 */
	@Override
	public void getMFGReport() {
		params=ParamsUtil.getParam(params, Action.GET_MFG_REPORT, new String[]{
				user.getBu(),
				user.getMfg()
		});
		reportConfigModel.getMFGReport(params);
		reportConfigView.showLoading();
	}
	/**
	 * 進入報表配置詳情頁
	 */
	@Override
	public void goReportConfigDetailPage(int pos) {
		String reportNum=reportInfoList.get(pos).getReportNum();
		//進入詳細報表配置頁面
		Bundle bundle=new Bundle();
		bundle.putString("reportNum", reportNum);
		reportConfigView.gotoActivity(ReportConfigDetailActivity.class,bundle);
	}
	/**
	 * 查詢報表
	 */
	@Override
	public void search(String reportName) {
		reportName=TextUtil.simpleToTradition(reportName);
		reportInfoList=ReportConfigPresenterHelper.searchReport(allReportInfoList,reportName);
		setReportAdapter();
	}
	
}
