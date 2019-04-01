package com.foxconn.paperless.main.function.presenter;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Config;
import com.foxconn.paperless.constant.MyEnum.IsInputOrder;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.constant.MyEnum.Team;
import com.foxconn.paperless.helper.MainPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.check.view.ReportCheckActivity;
import com.foxconn.paperless.main.function.model.MTInadvanceModel;
import com.foxconn.paperless.main.function.model.MTInadvanceModelImpl;
import com.foxconn.paperless.main.function.view.MTInadvanceView;
import com.foxconn.paperless.qr.CaptureActivity;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 提前維護邏輯處理
 *@ClassName MTInadvancePresenterImpl
 *@Author wunian
 *@Date 2018/1/16 下午5:22:45
 */
public class MTInadvancePresenterImpl implements MTInadvancePresenter,
		OnModelListener {
	
	private MTInadvanceView view;
	private MTInadvanceModel model;
	private Context context;
	private Euser user;
	private Params params;
	private int type;//維護方式：提前維護/補點檢
	private List<QRReportInfo> qrReportInfoList;//二維碼綁定的報表信息列表
	private QRReportInfo qrReportInfo;//用戶要點檢的二維碼報表信息
	private String qrInfo;//二維碼信息
	private String isInputOrder;//是否要輸入工單
	private String reportNum;//報表編號
	private String yeildFlag;//點檢頻率
	private String floorName;//樓層
	private String equipment;//線別
	private String date;
	private String time;
	private int year,month,day,hour,minute,second,millSecond;
	
	
	private static final int  REQUESTCODE_SCAN=100;
	

	public MTInadvancePresenterImpl(MTInadvanceView view,Context context){
		this.view=view;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.model=new MTInadvanceModelImpl(this);
		this.params=new Params(model);
		this.type=ReportCheck.INADVANCE;
	}
	
	@Override
	public void init(int type) {
		this.type=type;
		int[] date=TextUtil.getdate();
		int[] time=TextUtil.getTime();
		view.setDate(date[0],date[1],date[2]);
		view.setTime(time[0],time[1],time[2],time[3]);
		year=date[0];
		month=date[1]+1;
		day=date[2];
		hour=time[0];
		minute=time[1];
		second=time[2];
		millSecond=time[3];
		
	}
	/**
	 * 設置日期
	 */
	@Override
	public void setDate(){
		int[] date=TextUtil.getdate();
		//date[2]=TextUtil.addDay(1);
		view.setDatePickDialog(date[0],date[1],date[2]);
	}
	/**
	 * 設置時間
	 */
	@Override
	public void setTime() {
		int[] time=TextUtil.getTime();
		view.setTimePickDialog(time[0],time[1],time[2],time[3]);
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
				view.showSelectReportDialog(
						R.string.select_checkreport,itemList);
			}else{//一個二維碼綁定一個報表
				qrReportInfo=qrReportInfoList.get(0);
				check();
			}
		}
		if(result.getAction().equals(Action.GET_MT_CHECKRECORD)){
			String checkby=result.getData().get(1);//點檢人姓名
			String checkedInfo=context.getResources().getString(R.string.report_checked)+checkby;
			view.showToastFailedMsg(checkedInfo);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_QR_REPORTINPUTTYPE)){
			view.showToastFailedMsg(R.string.qrcode_notexist);//二維碼不存在
		}
		if(result.getAction().equals(Action.GET_MT_CHECKRECORD)){
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
			bundle.putInt("type", type);//點檢類型
			bundle.putString("date", date);//維護日期
			bundle.putString("time", time);//維護時間
			view.gotoActivity(ReportCheckActivity.class,bundle);
		}
	}

	@Override
	public void exception(JsonResult result) {
		view.showToastExceptionMsg(result.getResultMsg());
	}
	
	@Override
	public void selectCheckReport(int pos) {
		//ToastHelper.showInfo(context, qrReportInfoList.get(pos).getReportName(), 0);
		qrReportInfo=qrReportInfoList.get(pos);
		check();
	}
	/**
	 * 判斷權限，進入點檢界面
	 */
	private void check(){
		qrReportInfo.setQrInfo(qrInfo);//設置二維碼信息
		isInputOrder=qrReportInfo.getIsInputOrder();
		String MFG=qrReportInfo.getMFG();
		String SBU=qrReportInfo.getSBU();
		if(!user.getMfg().equalsIgnoreCase(MFG)||
				!user.getSbu().equalsIgnoreCase(SBU)){//根據製造處和SBU判斷用戶有無點檢權限
			view.showToastFailedMsg(R.string.check_notpermission);//無點檢權限
			return ;
		}
		reportNum=qrReportInfo.getReportNum();//報表編號
		yeildFlag=qrReportInfo.getYeildFlag();//點檢頻率
		floorName=qrReportInfo.getFloorName();//樓層
		equipment=qrReportInfo.getEquipment();//線別
		//拼接日期
		String createDate=year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second+"."+millSecond;
		//獲得該時段是否被點檢，未被點檢過才能進入點檢頁面
		params=ParamsUtil.getParam(params, Action.GET_MT_CHECKRECORD,
				new String[]{yeildFlag,reportNum,MFG,floorName,equipment,createDate});
		model.getCheckRecord(params);
	}
	/**
	 * 掃碼點檢
	 */
	@Override
	public void scanQR(String date,String time) {
		//Toast.makeText(context, year+"/"+month+"/"+day, 0).show();
		if(type==ReportCheck.INADVANCE){//提前維護的選擇時間必須大於當前時間
			boolean later=isLaterTime();
			if(!later){
				view.showToastFailedMsg(R.string.selecttime_small);
				return;
			}
		}
		if(type==ReportCheck.SUPPLEMENT){//補點檢的選擇時間必須小於當前時間（前一天）
			boolean before=isBeforeTime();
			if(!before){
				view.showToastFailedMsg(R.string.selecttime_big);
				return;
			}
		}
		this.date=date;
		this.time=time;
		if(Config.ScanToCheck){//掃碼點檢
			view.goActivityForResult(
			new Intent(context,CaptureActivity.class),REQUESTCODE_SCAN);
		}else{//傳值點檢
			//模擬器上不能掃碼，直接傳入二維碼
			qrInfo=Config.QRCode;
			//獲取報表類型 is_input_order 同時判斷二維碼是否存在
			params=ParamsUtil.getParam(params, Action.GET_QR_REPORTINPUTTYPE, 
					new String[]{qrInfo});
			model.getQRReportInputType(params);
		}
	}
	
	private boolean isBeforeTime(){
		Calendar calendar=Calendar.getInstance();
		int nowMonth=calendar.get(Calendar.MONTH)+1;//當前月份
		//ToastHelper.showInfo(context,nowMonth+"月", 0);
		if(year<calendar.get(Calendar.YEAR)){
			return true;
		}else if(year==calendar.get(Calendar.YEAR)){
			if(month<nowMonth){
				return true;
			}else if(month==nowMonth){
				if(day<calendar.get(Calendar.DAY_OF_MONTH)){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	
	
	/**
	 * 判斷選擇時間是否大於當前時間，小時數必須大於當前小時(提前維護)
	 * @return
	 */
	private boolean isLaterTime(){
		Calendar calendar=Calendar.getInstance();
		int nowMonth=calendar.get(Calendar.MONTH)+1;//當前月份
		//ToastHelper.showInfo(context,nowMonth+"月", 0);
		if(year<calendar.get(Calendar.YEAR)){
			return false;
		}else if(year==calendar.get(Calendar.YEAR)){
			if(month<nowMonth){
				return false;
			}else if(month==nowMonth){
				if(day<calendar.get(Calendar.DAY_OF_MONTH)){
					return false;
				}else if(day==calendar.get(Calendar.DAY_OF_MONTH)){
					if(hour<=calendar.get(Calendar.HOUR_OF_DAY)){
						return false;
					}else{
						return true;
					}
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
	
	//獲取報表類型 is_input_order 同時判斷二維碼是否存在
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (requestCode) {
			case REQUESTCODE_SCAN://掃碼
				if(resultCode==Activity.RESULT_OK&&data!=null){
					qrInfo=data.getStringExtra("result");
					params=ParamsUtil.getParam(params, Action.GET_QR_REPORTINPUTTYPE, 
							new String[]{qrInfo});
					model.getQRReportInputType(params);
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void setDate(int year, int month, int day) {
			this.year=year;
			this.month=month+1;
			this.day=day;
			//ToastHelper.showInfo(context, year+"/"+this.month+"/"+day, 0);
		}

		@Override
		public void setTime(int hour, int minute) {
			this.hour=hour;
			this.minute=minute;
		}

	
}
