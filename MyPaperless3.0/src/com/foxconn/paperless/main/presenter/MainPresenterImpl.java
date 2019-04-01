package com.foxconn.paperless.main.presenter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.Config;
import com.foxconn.paperless.constant.MyEnum.IsInputOrder;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.constant.MyEnum.Team;
import com.foxconn.paperless.helper.MainPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.check.view.ReportCheckActivity;
import com.foxconn.paperless.main.model.MainModel;
import com.foxconn.paperless.main.model.MainModelImpl;
import com.foxconn.paperless.main.view.AccountFragment;
import com.foxconn.paperless.main.view.HomeFragment;
import com.foxconn.paperless.main.view.MainView;
import com.foxconn.paperless.qr.CaptureActivity;
import com.foxconn.paperless.ui.widget.ToastHelper;

public class MainPresenterImpl implements MainPresenter, OnModelListener {

	private MainView mainView;
	private MainModel mainModel;
	private Context context;
	private Euser user;
	private Params params;
	private List<QRReportInfo> qrReportInfoList;//二維碼綁定的報表信息列表
	private QRReportInfo qrReportInfo;//用戶要點檢的二維碼報表信息
	private String qrInfo;//二維碼信息
	private String isInputOrder;//是否要輸入工單
	private String reportNum;//報表編號
	private String yeildFlag;//點檢頻率
	private String floorName;//樓層
	private String equipment;//線別
	private static final int  REQUESTCODE_SCAN=100;

	public MainPresenterImpl(MainView mainView, Context context) {
		this.mainView = mainView;
		this.context = context;
		this.user = (Euser) context.getApplicationContext();
		this.mainModel=new MainModelImpl(this);
		this.params=new Params(mainModel);
		//初始化存储所有BU的集合（每次回到首页所有BU需重新获取）
		MyConstant.ALLBULIST=new ArrayList<String>();
	}

	@Override
	public void changeFragment(Activity activity, int checkId) {
		//CheckReportFragment checkReportFragment;
		//CheckManageFragment checkManageFragment;
		//AccountFragment accountFragment;
		switch (checkId) {
		case R.id.rbHomeReport:
			activity.getFragmentManager().beginTransaction()
				.replace(R.id.mainLayout, new HomeFragment()).commit();
			mainView.setMainTitle(R.string.home_title);
			break;
			
		case R.id.rbAccount:
			activity.getFragmentManager().beginTransaction()
				.replace(R.id.mainLayout, new AccountFragment()).commit();
			mainView.setMainTitle(R.string.account_title);
			break;
		default:
			break;
		}
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_QR_REPORTINPUTTYPE)){
			//封裝到bean
			qrReportInfoList=MainPresenterHelper.getQRReportInfo(result);
			if(qrReportInfoList.size()>1){//一個二維碼綁定多個報表
				//獲得報表ListView條目
				List<String> itemList=MainPresenterHelper.getReportItemList(qrReportInfoList);
				//顯示選擇報表的提示框
				mainView.showSelectReportDialog(
						R.string.select_checkreport,itemList);
			}else{//一個二維碼綁定一個報表
				qrReportInfo=qrReportInfoList.get(0);
				getCheckRecord();
			}
		}
		if(result.getAction().equals(Action.GET_CHECKRECORD)){//該時段已被點檢（有數據）
			String checkby=result.getData().get(1);//點檢人姓名
			String checkedInfo=context.getResources().getString(R.string.report_checked)+checkby;
			mainView.showToastFailedMsg(checkedInfo);
		}
	}
	/**
	 * 選擇點檢報表
	 */
	@Override
	public void selectCheckReport(int pos) {
		//ToastHelper.showInfo(context, qrReportInfoList.get(pos).getReportName(), 0);
		qrReportInfo=qrReportInfoList.get(pos);
		getCheckRecord();
	}
	/**
	 * 獲得該時段點檢信息
	 *
	 */
	private void getCheckRecord(){
		qrReportInfo.setQrInfo(qrInfo);//設置二維碼信息
		isInputOrder=qrReportInfo.getIsInputOrder();
		String MFG=qrReportInfo.getMFG();
		String SBU=qrReportInfo.getSBU();
		if(!user.getMfg().equalsIgnoreCase(MFG)||
				!user.getSbu().equalsIgnoreCase(SBU)){//根據製造處和SBU判斷用戶有無點檢權限
			mainView.showToastFailedMsg(R.string.check_notpermission);//無點檢權限
			return ;
		}
		reportNum=qrReportInfo.getReportNum();//報表編號
		yeildFlag=qrReportInfo.getYeildFlag();//點檢頻率
		floorName=qrReportInfo.getFloorName();//樓層
		equipment=qrReportInfo.getEquipment();//線別
		//獲得該時段是否被點檢，未被點檢過才能進入點檢頁面
		params=ParamsUtil.getParam(params, Action.GET_CHECKRECORD,
				new String[]{yeildFlag,reportNum,MFG,floorName,equipment});
		mainModel.getCheckRecord(params);
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_QR_REPORTINPUTTYPE)){
			mainView.showToastFailedMsg(R.string.qrcode_notexist);//二維碼不存在
		}
		if(result.getAction().equals(Action.GET_CHECKRECORD)){//二維碼還未點檢
			
			if(isInputOrder.trim().equals(IsInputOrder.NEED_INPUT)){//要輸工單報表
				if(user.getTeam().equalsIgnoreCase(Team.IPQC)){//IPQC點檢報表
					//ToastHelper.showInfo(context, "IPQC點檢", 0);
					qrReportInfo.setReportType(Report.CHECK_IPQC);
				}else{//PD點檢報表
					//ToastHelper.showInfo(context, "CheckPD點檢", 0);
					qrReportInfo.setReportType(Report.CHECKPD_INPUT);
				}
			}else{//不用輸工單報表
				//ToastHelper.showInfo(context, "Echeck點檢", 0);
				qrReportInfo.setReportType(Report.CHECK_NOINPUT);
			}
			Bundle bundle =new Bundle();
			bundle.putSerializable("qrReportInfo", qrReportInfo);
			bundle.putInt("type", ReportCheck.NORMAL);//點檢類型：正常點檢
			mainView.gotoActivity(ReportCheckActivity.class,bundle);
		}
	}

	@Override
	public void exception(JsonResult result) {
		mainView.showToastExceptionMsg(result.getResultMsg());
	}

	/**
	 * 打開掃碼頁面
	 */
	@Override
	public void openScanQRPage() {
		if(Config.ScanToCheck){//掃碼點檢
			mainView.goActivityForResult(
					new Intent(context,CaptureActivity.class),REQUESTCODE_SCAN);
		}else{//傳值點檢
			//模擬器上不能掃碼，直接傳入二維碼
			qrInfo=Config.QRCode;
			//獲取報表類型 is_input_order 同時判斷二維碼是否存在
			params=ParamsUtil.getParam(params, Action.GET_QR_REPORTINPUTTYPE, 
					new String[]{qrInfo});
			mainModel.getQRReportInputType(params);
		}
	}

	//獲取報表類型 is_input_order 同時判斷二維碼是否存在
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_SCAN://掃碼
			if(resultCode==Activity.RESULT_OK&&data!=null){
				qrInfo=data.getStringExtra("result").trim();
				params=ParamsUtil.getParam(params, Action.GET_QR_REPORTINPUTTYPE, 
						new String[]{qrInfo});
				mainModel.getQRReportInputType(params);
			}
			break;
		default:
			break;
		}
	}
}
