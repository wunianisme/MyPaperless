package com.foxconn.paperless.main.function.presenter;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.Side;
import com.foxconn.paperless.helper.ParameterManagePresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ParameterManageModel;
import com.foxconn.paperless.main.function.model.ParameterManageModelImpl;
import com.foxconn.paperless.main.function.view.ParameterManageAuditActivity;
import com.foxconn.paperless.main.function.view.ParameterManageDetailActivity;
import com.foxconn.paperless.main.function.view.ParameterManageView;
import com.foxconn.paperless.main.view.MainActivity;
import com.foxconn.paperless.qr.CaptureActivity;
/**
 * 參數管理邏輯處理
 *@ClassName ParameterManagePresenterImpl
 *@Author wunian
 *@Date 2018/1/8 下午3:22:02
 */
public class ParameterManagePresenterImpl implements ParameterManagePresenter,OnModelListener {

	private ParameterManageView paramView;
	private Context context;
	private ParameterManageModel paramModel;
	private Euser user;
	private Params params;
	private String reportNum;
	private List<ParamInfo> paramInfoList;//參數基本信息列表
	private List<String> floorNameList;//樓層列表
	private List<String> lineNameList;//線別列表
	
	private final static int REQUESTCODE_SCAN=100;
	
	public ParameterManagePresenterImpl(ParameterManageView paramView,
			Context context){
		this.paramView=paramView;
		this.context=context;
		this.paramModel=new ParameterManageModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(paramModel);
		this.reportNum=ReportNum.SMT_PRINTER;
		this.paramInfoList=new ArrayList<ParamInfo>();
		this.floorNameList=new ArrayList<String>();
		this.lineNameList=new ArrayList<String>();
	}
	
	@Override
	public void success(JsonResult result) {
		paramView.dismissLoading();
		if(result.getAction().equals(Action.GET_PARAM_FLOORNAME)){
			floorNameList=result.getData();
			paramView.setFloorNameAdapter(floorNameList);
		}
		if(result.getAction().equals(Action.GET_PARAM_LINENAME)){
			lineNameList=result.getData();
			paramView.setLineNameAdapter(lineNameList);
			//默認查詢條件：第一個樓層、線別，所有機種
			search(floorNameList.get(0), lineNameList.get(0), "");
		}
		if(result.getAction().equals(Action.GET_PARAM_INFO)){
			paramInfoList=ParameterManagePresenterHelper.getParamInfo(result);
			paramView.setParamInfoAdapter(paramInfoList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		paramView.dismissLoading();
		if(result.getAction().equals(Action.GET_PARAM_INFO)){
			paramView.showToastFailedMsg(R.string.getparaminfo_failed);
			paramInfoList.clear();
			paramView.setParamInfoAdapter(paramInfoList);
		}
	}

	@Override
	public void exception(JsonResult result) {
		paramView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲取樓層
	 */
	@Override
	public void getFloorName() {
		params=ParamsUtil.getParam(params, Action.GET_PARAM_FLOORNAME, new String[]{
			user.getBu(),
			user.getMfg(),
			reportNum
		});
		paramModel.getFloorName(params);
	}
	/**
	 * 獲取線別
	 */
	@Override
	public void getLineName(String floorName) {
		params=ParamsUtil.getParam(params, Action.GET_PARAM_LINENAME, new String[]{
				floorName,
				user.getMfg(),
				reportNum
			});
		paramModel.getLineName(params);
	}
	/**
	 * 設置參數報表編號
	 */
	@Override
	public void setReportNum(String reportNum) {
		this.reportNum=reportNum;
		//paramView.showAuditMessageView(View.VISIBLE);
	}
	/**
	 * 參數查詢
	 */
	@Override
	public void search(String floorName, String lineName, String skuno) {
		params=ParamsUtil.getParam(params, Action.GET_PARAM_INFO, new String[]{
				reportNum,	
				floorName,
				lineName,
				skuno,
				Side.BOT
		});
		paramModel.getParamInfo(params);
		paramView.showLoading();
	}
	/**
	 * 跳轉到詳細信息頁面
	 */
	@Override
	public void goParameterManageDetailPage(int pos) {
		Bundle bundle=new Bundle();
		bundle.putString("reportNum", reportNum);
		bundle.putSerializable("paramInfo", paramInfoList.get(pos));
		bundle.putStringArrayList("floorNameList",(ArrayList<String>)floorNameList);
		bundle.putStringArrayList("lineNameList",(ArrayList<String>)lineNameList);
		paramView.gotoActivity(ParameterManageDetailActivity.class,bundle);
	}
	/**
	 * 進入簽核消息界面
	 */
	@Override
	public void auditMessage() {
		Bundle bundle=new Bundle();
		bundle.putString("reportNum", reportNum);
		paramView.gotoActivity(ParameterManageAuditActivity.class, bundle);
	}
	/**
	 * 掃碼輸入機種
	 */
	@Override
	public void scanSkuNo() {
		paramView.gotoActivityForResult(CaptureActivity.class, null,
				REQUESTCODE_SCAN);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			switch (requestCode) {
			case REQUESTCODE_SCAN://掃碼輸入機種
				String skuNo=data.getStringExtra("result");
				paramView.inputSkuNo(skuNo);//將二維碼信息輸入到輸入框中
				break;
			default:
				break;
			}
		}
	}
}
