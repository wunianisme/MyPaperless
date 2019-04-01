package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
import com.foxconn.paperless.constant.MyEnum.Team;
import com.foxconn.paperless.helper.ParameterManagePresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.ParameterManageModel;
import com.foxconn.paperless.main.function.model.ParameterManageModelImpl;
import com.foxconn.paperless.main.function.view.ParameterManageDetailView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 參數管理詳細信息/修改/刪除邏輯處理
 *@ClassName ParameterManageDetailPresenterImpl
 *@Author wunian
 *@Date 2018/1/11 上午10:02:34
 */
public class ParameterManageDetailPresenterImpl implements
		ParameterManageDetailPresenter, OnModelListener {
	
	private ParameterManageDetailView paramView;
	private Context context;
	private ParameterManageModel paramModel;
	private Euser user;
	private Params params;
	private String reportNum;
	private String side;
	private ParamInfo paramInfo;
	private List<String> floorNameList;//樓層列表
	private List<String> lineNameList;//線別列表
	private List<String> paramList;//詳細參數信息
	private List<Euser> masterList;//主管列表
	private List<String> masterNameList;
	private String checkBy;
	private String[] paramArray;
	private int mode;
	
	public final static int UPDATE=1;//修改參數
	public final static int DELETE=2;//刪除參數
	
	
	public ParameterManageDetailPresenterImpl(ParameterManageDetailView paramView,
			Context context){
		this.paramView=paramView;
		this.context=context;
		this.paramModel=new ParameterManageModelImpl(this);
		this.user=(Euser) context.getApplicationContext();
		this.params=new Params(paramModel);
		this.reportNum=ReportNum.SMT_PRINTER;
		this.side=Side.BOT;
		this.paramList=new ArrayList<String>();
		this.masterList=new ArrayList<Euser>();
		this.masterNameList=new ArrayList<String>();
	}

	@Override
	public void success(JsonResult result) {
		paramView.dismissLoading();
		if(result.getAction().equals(Action.GET_PARAM_DETAILINFO)){//獲取參數詳細信息成功
			paramList=result.getData();
			showParamDetailData();
		}
		if(result.getAction().equals(Action.GET_PARAMCHECK_MASTER)){//獲得上級主管
			masterList=ParameterManagePresenterHelper.getMaster(result);
			masterNameList=ParameterManagePresenterHelper.getMasterName(masterList);
			if(mode==DELETE){//刪除模式下直接提交參數
				paramView.submitParam();
			}
		}
		if(result.getAction().equals(Action.UPDATE_SMTPRINTER_INFO)){//修改印刷機參數成功
			paramView.showToastSuccessMsg(R.string.updateparam_success);
		}
		if(result.getAction().equals(Action.UPDATE_SMTREFLOW_INFO)){//修改回焊爐參數成功
			paramView.showToastSuccessMsg(R.string.updateparam_success);
		}
		if(result.getAction().equals(Action.UPDATE_PTHWS_INFO)){//修改波峰焊參數成功
			paramView.showToastSuccessMsg(R.string.updateparam_success);
		}
		if(result.getAction().equals(Action.DELETE_SMTPRINTER_INFO)){//刪除印刷機參數成功
			paramView.showToastSuccessMsg(R.string.deleteparam_success);
		}
		if(result.getAction().equals(Action.DELETE_SMTREFLOW_INFO)){//刪除回焊爐參數成功
			paramView.showToastSuccessMsg(R.string.deleteparam_success);
		}
		if(result.getAction().equals(Action.DELETE_PTHWS_INFO)){//刪除波峰焊參數成功
			paramView.showToastSuccessMsg(R.string.deleteparam_success);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_PARAM_DETAILINFO)){//獲取參數詳細信息失敗
			paramView.showToastFailedMsg(R.string.getparamdetail_failed);
			paramList=ParameterManagePresenterHelper.clearParamList(paramList);
			showParamDetailData();		
		}
		if(result.getAction().equals(Action.GET_PARAMCHECK_MASTER)){//暫無主管信息
			paramView.showToastFailedMsg(R.string.getcheckmaster_failed);
		}
		if(result.getAction().equals(Action.UPDATE_SMTPRINTER_INFO)){//修改印刷機參數失敗
			paramView.showToastFailedMsg(R.string.updateparam_failed);
		}
		if(result.getAction().equals(Action.UPDATE_SMTREFLOW_INFO)){//修改回焊爐參數失敗
			paramView.showToastFailedMsg(R.string.updateparam_failed);
		}
		if(result.getAction().equals(Action.UPDATE_PTHWS_INFO)){//修改波峰焊參數失敗
			paramView.showToastFailedMsg(R.string.updateparam_failed);
		}
		if(result.getAction().equals(Action.DELETE_SMTPRINTER_INFO)){//刪除印刷機參數失敗
			paramView.showToastFailedMsg(R.string.deleteparam_failed);
		}
		if(result.getAction().equals(Action.DELETE_SMTREFLOW_INFO)){//刪除回焊爐參數失敗
			paramView.showToastFailedMsg(R.string.deleteparam_failed);
		}
		if(result.getAction().equals(Action.DELETE_PTHWS_INFO)){//刪除波峰焊參數失敗
			paramView.showToastFailedMsg(R.string.deleteparam_failed);
		}
	}
	
	@Override
	public void exception(JsonResult result) {
		paramView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 顯示參數詳細數據
	 */
	private void showParamDetailData(){
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			paramView.showSMTPrinterParamDetail(paramList,side);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			paramView.showSMTReflowParamDetail(paramList,side);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			paramView.showPTHWSParamDetail(paramList);
		}
	}

	@Override
	public void init(String reportNum, ParamInfo paramInfo,
			List<String> floorNameList, List<String> lineNameList) {
		this.reportNum=reportNum;
		this.paramInfo=paramInfo;
		this.floorNameList=floorNameList;
		this.lineNameList=lineNameList;
		if(reportNum.equals(ReportNum.SMT_PRINTER)){//波峰焊
			paramView.showParamDetailLayout(View.VISIBLE,View.GONE,View.GONE);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){//回焊爐
			paramView.showParamDetailLayout(View.GONE,View.VISIBLE,View.GONE);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){//波峰焊
			paramView.showParamDetailLayout(View.GONE,View.GONE,View.VISIBLE);
		}
		paramView.initParam(paramInfo,side);
		//ME和PROCESS有權限修改刪除參數
		if(user.getTeam().equalsIgnoreCase(Team.ME)||
				user.getTeam().equalsIgnoreCase(Team.PROCESS)){
			paramView.showBtnLayout(View.VISIBLE);
		}
	}
	/**
	 *切換面別
	 */
	@Override
	public void switchSide() {
		String suffix="";
		String parameterNum=paramInfo.getParameterNum();
		if(parameterNum.endsWith(side)){
			//獲取parameterNum的前半部分（除面別）
			suffix=parameterNum.substring(0, parameterNum.indexOf(side));
		}
		if(side.equals(Side.BOT)){
			side=Side.TOP;
		}else{
			side=Side.BOT;
		}
		paramView.setSide(side);
		parameterNum=suffix+side;//重新拼接parameterNum
		paramInfo.setParameterNum(parameterNum);
		getParamDetail();
	}
	/**
	 * 獲取詳細參數信息
	 */
	@Override
	public void getParamDetail() {
		params=ParamsUtil.getParam(params, Action.GET_PARAM_DETAILINFO, new String[]{
				reportNum,
				paramInfo.getParameterNum()
		});
		paramModel.getParamDetail(params);
		paramView.showLoading();
	}
	/**
	 * 切換到修改模式
	 */
	@Override
	public void updateParam() {
		mode=UPDATE;
		showParamUpdateView();//顯示參數修改的佈局
		getCheckMaster();//獲得簽核的上級主管
	}
	/**
	 * 切換到刪除模式
	 */
	@Override
	public void deleteParam() {
		mode=DELETE;
		getCheckMaster();//獲得簽核的上級主管
	}
	
	private void getCheckMaster(){
		params=ParamsUtil.getParam(params, Action.GET_PARAMCHECK_MASTER, new String[]{
				user.getMfg(),
				user.getTeam()
		});
		paramModel.getCheckMaster(params);
	}
	/**
	 * 顯示修改參數界面
	 */
	private void showParamUpdateView(){
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			paramView.showBaseUpdateView(floorNameList, lineNameList, side);
			paramView.showSMTPrinterUpdateView();
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			paramView.showBaseUpdateView(floorNameList, lineNameList, side);
			paramView.showSMTReflowUpdateView();
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			paramView.showBaseUpdateView(floorNameList, lineNameList,null);
			paramView.showPTHWSUpdateView();
		}
	}
	/**
	 * 校驗SMT印刷機參數
	 */
	@Override
	public void validateSMTPrinterParam(String floorName, String lineName,
			String skuNo, String side, String scraperPressure,
			String printSpeed, String separationSpeed,
			String separationDistance, String screenCleanRate,
			String squeegeeLength, String problemDesc) {
		int titleId = R.string.paramupdate_title;
		if(mode==UPDATE){//修改
			if(TextUtil.isEmpty(floorName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}else if(TextUtil.isEmpty(lineName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(skuNo)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(side)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(scraperPressure)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(printSpeed)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(separationSpeed)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(separationDistance)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(screenCleanRate)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(squeegeeLength)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(problemDesc)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			titleId=R.string.paramupdate_title;
		}
		if(mode==DELETE){//刪除
			if(paramList.size()==50){
				paramView.showToastFailedMsg(R.string.param_notexist);
				return;
			}
			titleId=R.string.paramdelete_title;
			
		}
		if(masterList.size()<1){
			paramView.showToastFailedMsg(R.string.getcheckmaster_failed);
			return ;
		}
		problemDesc=TextUtil.simpleToTradition(problemDesc);
		paramInfo.setParameterNum(floorName+lineName+skuNo+side);//重新設置參數名稱
		//簽核人暫時空缺，選擇完簽核人再設置進去
		paramArray=new String[]{
				paramInfo.getParameterNum(),floorName,lineName,skuNo,side,scraperPressure,
				printSpeed,separationSpeed,separationDistance,screenCleanRate,squeegeeLength,
				problemDesc,user.getLogonName(),""
		};
		paramView.showSubmitParamDialog(titleId,R.string.btn_submitparam,
				R.string.btn_no,masterNameList);
	}

	@Override
	public void submitSMTPrinterParam() {
		if(mode==UPDATE){
			submitParam(Action.UPDATE_SMTPRINTER_INFO);
		}
		if(mode==DELETE){
			submitParam(Action.DELETE_SMTPRINTER_INFO);
		}
		
	}
	
	@Override
	public void initCheckBy() {
		checkBy="";
	}

	@Override
	public void setCheckBy(int pos) {
		checkBy=masterList.get(pos).getLogonName();
	}
	/**
	 * 回焊爐參數校驗
	 */
	@Override
	public void validateSMTReflowParam(String floorName, String lineName,
			String skuNo, String side, String zone1, String zone2,
			String zone3, String zone4, String zone5, String zone6,
			String zone7, String zone8, String zone9, String zone10,
			String zone11, String zone12, String zone13, String speed,
			String fanSpeed, String shuntValue, String problemDesc) {
		//this.mode=mode;
		int titleId = R.string.paramupdate_title;
		if(mode==UPDATE){//修改
			if(TextUtil.isEmpty(floorName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}else if(TextUtil.isEmpty(lineName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(skuNo)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(side)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone1)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone2)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone3)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone4)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone5)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone6)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone7)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone8)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone9)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone10)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone11)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone12)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(zone13)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(speed)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(fanSpeed)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(shuntValue)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(problemDesc)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			titleId=R.string.paramupdate_title;
		}
		if(mode==DELETE){//刪除
			if(paramList.size()==50){
				paramView.showToastFailedMsg(R.string.param_notexist);
				return;
			}
			titleId=R.string.paramdelete_title;
		}
		if(masterList.size()<1){
			paramView.showToastFailedMsg(R.string.getcheckmaster_failed);
			return ;
		}
		problemDesc=TextUtil.simpleToTradition(problemDesc);
		paramInfo.setParameterNum(floorName+lineName+skuNo+side);//重新設置參數名稱
		//簽核人暫時空缺，選擇完簽核人再設置進去
		paramArray=new String[]{
				paramInfo.getParameterNum(),floorName,lineName,skuNo,side,zone1,
				zone2,zone3,zone4,zone5,zone6,zone7,zone8,zone9,zone10,zone11,zone12,zone13,
				speed,fanSpeed,shuntValue,problemDesc,user.getLogonName(),""
		};
		paramView.showSubmitParamDialog(titleId,R.string.btn_submitparam,
				R.string.btn_no,masterNameList);
	}

	@Override
	public void submitSMTReflowParam() {
		if(mode==UPDATE){
			submitParam(Action.UPDATE_SMTREFLOW_INFO);
		}
		if(mode==DELETE){
			submitParam(Action.DELETE_SMTREFLOW_INFO);
		}
		
	}
	/**
	 * 波峰焊參數校驗
	 */
	@Override
	public void validatePTHWSParam(String floorName, String lineName,
			String skuNo, String modelName, String BOTPreheatI,
			String BOTPreheatII, String BOTPreheatIII, String BOTPreheatIV,
			String TOPPreheatI, String TOPPreheatII, String TOPPreheatIII,
			String TOPPreheatIV, String ChainSpeedAfter, String ChainSpeed,
			String TinTemperature, String TinBathHeight, String TurbulentWave,
			String AdvectionWave, String TinBarType, String FluxType,
			String FluxFlow, String NozzleAirPressure, String TotalAirPressure,
			String TankAirPressure, String FluxProportion, String remark,
			String problemDesc) {
		int titleId = R.string.paramupdate_title;
		if(mode==UPDATE){//修改
			if(TextUtil.isEmpty(floorName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}else if(TextUtil.isEmpty(lineName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(skuNo)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(modelName)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(BOTPreheatI)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(BOTPreheatII)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(BOTPreheatIII)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(BOTPreheatIV)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TOPPreheatI)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TOPPreheatII)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TOPPreheatIII)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TOPPreheatIV)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(ChainSpeedAfter)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(ChainSpeed)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TinTemperature)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TinBathHeight)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TurbulentWave)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(AdvectionWave)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TinBarType)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(FluxType)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(FluxFlow)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(NozzleAirPressure)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TotalAirPressure)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(TankAirPressure)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(FluxProportion)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(remark)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			else if(TextUtil.isEmpty(problemDesc)){
				paramView.showToastFailedMsg(R.string.param_empty);
				return;
			}
			titleId=R.string.paramupdate_title;
		}
		if(mode==DELETE){//刪除
			if(paramList.size()==50){
				paramView.showToastFailedMsg(R.string.param_notexist);
				return;
			}
			titleId=R.string.paramdelete_title;
			
		}
		if(masterList.size()<1){
			paramView.showToastFailedMsg(R.string.getcheckmaster_failed);
			return ;
		}
		remark=TextUtil.simpleToTradition(remark);
		problemDesc=TextUtil.simpleToTradition(problemDesc);
		//簽核人暫時空缺，選擇完簽核人再設置進去
		paramArray=new String[]{
				paramInfo.getParameterNum(),user.getMfg(),user.getSbu(),
				floorName,lineName,modelName,skuNo,BOTPreheatI,BOTPreheatII,
				BOTPreheatIII,BOTPreheatIV,TOPPreheatI,TOPPreheatII,TOPPreheatIII,
				TOPPreheatIV,ChainSpeedAfter,ChainSpeed,TinTemperature,TinBathHeight,
				TurbulentWave,AdvectionWave,TinBarType,FluxType,FluxFlow,NozzleAirPressure,
				TotalAirPressure,TankAirPressure,FluxProportion,problemDesc,
				remark,user.getLogonName(),""
		};
		paramView.showSubmitParamDialog(titleId,R.string.btn_submitparam,
				R.string.btn_no,masterNameList);
	}
	
	@Override
	public void submitPTHWSParam() {
		if(mode==UPDATE){
			submitParam(Action.UPDATE_PTHWS_INFO);
		}
		if(mode==DELETE){
			submitParam(Action.DELETE_PTHWS_INFO);
		}
		
	}
	/**
	 * 提交參數
	 * @param action
	 */
	private void submitParam(String action){
		if(TextUtil.isEmpty(checkBy)){//請選擇
			paramView.showToastFailedMsg(R.string.select_checkby);
			return ;
		}else{
			paramView.dismissSubmitParamDialog();
		}
		paramArray[paramArray.length-1]=checkBy;
		params=ParamsUtil.getParam(params,action, paramArray);
		paramModel.updateParam(params);
		paramView.showLoading();
		initCheckBy();
	}

	@Override
	public void setSide(String side) {
		this.side=side;
	}
}
