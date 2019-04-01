package com.foxconn.paperless.main.presenter;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.FunctionManage;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.constant.MyEnum.Userlevel;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.check.view.BUReportActivity;
import com.foxconn.paperless.main.check.view.ReportCheckActivity;
import com.foxconn.paperless.main.function.view.AuditSearchActivity;
import com.foxconn.paperless.main.function.view.CheckSearchActivity;
import com.foxconn.paperless.main.function.view.ExceptionManageActivity;
import com.foxconn.paperless.main.function.view.MTInadvanceActivity;
import com.foxconn.paperless.main.function.view.MTInadvanceByLineActivity;
import com.foxconn.paperless.main.function.view.ParameterManageActivity;
import com.foxconn.paperless.main.function.view.ReportConfigActivity;
import com.foxconn.paperless.main.model.MainModel;
import com.foxconn.paperless.main.model.MainModelImpl;
import com.foxconn.paperless.main.view.HomeView;
/**
 * 首頁Fragment頁面邏輯處理
 *@ClassName CheckPresenterImpl
 *@Author wunian
 *@Date 2017/11/2 上午8:43:09
 */
public class HomePresenterImpl implements HomePresenter,OnModelListener {

	private HomeView homeView;
	private Context context;
	private MainModel mainModel;
	private Euser user;
	private Params mainParam;
	private int[] msgNumArray;
	
	public HomePresenterImpl(HomeView homeView,Context context){
		this.homeView=homeView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.mainModel=new MainModelImpl(this);
		this.mainParam=new Params(mainModel);
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_BU)){
			homeView.setBUAdapter(result.getData());
			MyConstant.ALLBULIST=result.getData();
			//mainModel.saveAllBU(context,result.getData());//保存到SharePreference
		}
		if(result.getAction().equals(Action.GET_MESSAGE_NUM)){//獲得消息記錄
			//待簽核消息總數
			msgNumArray[FunctionManage.INDEX_AUDITSEARCH]=Integer.parseInt(result.getData().get(0));
			//待簽核參數消息總數
			msgNumArray[FunctionManage.INDEX_PARAMMANAGE]=Integer.parseInt(result.getData().get(1));
			//待處理異常消息總數
			msgNumArray[FunctionManage.INDEX_EXCEPTIONMANAGE]=Integer.parseInt(result.getData().get(2));
			homeView.refreshFunctionMenuAdapter();
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_BU)){
			homeView.showNullReportTip();
		}
		
	}

	@Override
	public void exception(JsonResult result) {
		homeView.showToastExceptionMsg(result.getResultMsg());
		
	}

	/**
	 * 獲得當前廠區和事業群下的所有BU
	 */
	@Override
	public void getAllBU() {
		//如果没有获取过BU,或重新回到首页数据则要重新获取
		if(MyConstant.ALLBULIST.size()==0){
			mainParam=ParamsUtil.getParam(mainParam, Action.GET_BU, 
					new String[]{user.getSite(),user.getBg()});
			mainModel.getAllBU(mainParam);
		}else{//已经获取到了BU数据则直接拿来使用
			homeView.setBUAdapter(MyConstant.ALLBULIST);
		}
		
	}
	/**
	 * 跳轉到BU報表頁面
	 */
	@Override
	public void goBUReportPage(String BU) {
		Bundle bundle=new Bundle();
		bundle.putString("BU", BU);
		homeView.gotoActivity(BUReportActivity.class,bundle);
	}

	/**
	 * 設置功能菜單
	 */
	@Override
	public void setFunctionMenu() {
		int[] iconArray=FunctionManage.iconArray;//圖標
		int[] itemArray=FunctionManage.itemArray;//文字
		msgNumArray=new int[iconArray.length];
		for (int i = 0; i < msgNumArray.length; i++) {//初始化消息條數
			msgNumArray[i]=0;
		}
		homeView.setFunctionMenuAdapter(iconArray,itemArray,msgNumArray);
		getMsgNum();
	}
	/**
	 * 獲得各菜單消息條數
	 */
	private void getMsgNum(){
		mainParam=ParamsUtil.getParam(mainParam, Action.GET_MESSAGE_NUM, new String[]{
				user.getMfg(),
				user.getLogonName()
		});
		mainModel.getMsgNum(mainParam);
	}
	/**
	 * 跳轉到用戶點擊的功能菜單頁面中
	 */
	@Override
	public void goFunctionMenu(int pos) {
		int itemId=FunctionManage.itemArray[pos];
		switch (itemId) {
		case R.string.audit_search://簽核查詢
			homeView.gotoActivity(AuditSearchActivity.class, null);
			break;
		case R.string.check_search://點檢查詢
			homeView.gotoActivity(CheckSearchActivity.class, null);
			break;
		case R.string.check_edit://點檢修改
			homeView.showToastFailedMsg(R.string.function_notopen);	
			break;
		case R.string.maintenance_inadvance://提前維護線體
			if(user.getUserlevel().equals(Userlevel.level_2)
					||user.getUserlevel().equals(Userlevel.level_3)){//課長以上級別才能維護
				//homeView.gotoActivity(MTInadvanceActivity.class, null);
				selectMaintenanceWay();
			}else{
				homeView.showToastFailedMsg(R.string.maintenance_nopermission);	
			}
			break;
		case R.string.report_config://報表配置
			if(user.getUserlevel().equals(Userlevel.level_2)
					||user.getUserlevel().equals(Userlevel.level_3)){//課長以上級別才能配置
				homeView.gotoActivity(ReportConfigActivity.class, null);
			}else{
				homeView.showToastFailedMsg(R.string.reportconfig_nopermission);			
			}
			break;
		case R.string.parameter_manage://參數管理
			Bundle bundle=new Bundle();
			//把待簽核消息條數傳入
			bundle.putInt("msgNum", msgNumArray[FunctionManage.INDEX_PARAMMANAGE]);
			homeView.gotoActivity(ParameterManageActivity.class, bundle);
			break;
		case R.string.exception_manage://異常管理
			//Bundle bundle2=new Bundle();
			//bundle2.putInt("msgNum", msgNumArray[FunctionManage.INDEX_EXCEPTIONMANAGE]);
			homeView.gotoActivity(ExceptionManageActivity.class, null);
			break;
		default:
			break;
		}
	}
	/**
	 * 選擇提前維護的方式（包含補點檢）add in 2018/05/21
	 */
	private void selectMaintenanceWay(){
		int titleId=R.string.select_way;
		String[] wayArray;
		if(user.getUserlevel().equals(Userlevel.level_2)){//普通維護
			wayArray=context.getResources().getStringArray(R.array.mtWay_array);
		}else{//補點檢
			wayArray=context.getResources().getStringArray(R.array.mtWay2_array);
		}
		homeView.showSelectWayDialog(titleId,wayArray);
		
	}
	/**
	 * 跳轉到提前維護頁面
	 */
	@Override
	public void goMTInadvancePage(int position) {
		Bundle bundle=new Bundle();
		switch (position) {
		case 0://樓層纖體維護
			homeView.gotoActivity(MTInadvanceByLineActivity.class, null);
			break;
		case 1://二維碼編號維護
			bundle.putInt("type", ReportCheck.INADVANCE);
			homeView.gotoActivity(MTInadvanceActivity.class, bundle);
			break;
		case 2://補點檢
			bundle.putInt("type", ReportCheck.SUPPLEMENT);
			homeView.gotoActivity(MTInadvanceActivity.class, bundle);
			break;

		default:
			break;
		}
		
	}
}
