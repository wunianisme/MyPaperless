package com.foxconn.paperless.main.check.presenter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ParamsConfig;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.DBFlag;
import com.foxconn.paperless.constant.MyEnum.Exception;
import com.foxconn.paperless.constant.MyEnum.FinalDB;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportCheck;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.ResultMsg;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.constant.MyEnum.TakePhoto;
import com.foxconn.paperless.constant.MyEnum.Userlevel;
import com.foxconn.paperless.editimage.EditImageActivity;
import com.foxconn.paperless.helper.CheckPresenterHelper;
import com.foxconn.paperless.helper.CheckReportHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.check.model.CheckModel;
import com.foxconn.paperless.main.check.model.CheckModelImpl;
import com.foxconn.paperless.main.check.view.ReportCheckView;
import com.foxconn.paperless.qr.CaptureActivity;
import com.foxconn.paperless.util.ContentProviderUtil;
import com.foxconn.paperless.util.FileUtil;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;
import com.foxconn.paperless.util.IntentUtil;
import com.foxconn.paperless.util.TextUtil;
/**
 * 報表點檢邏輯處理
 *@ClassName ReportCheckPresenterImpl
 *@Author wunian
 *@Date 2018/1/17 下午4:57:44
 */
public class ReportCheckPresenterImpl implements ReportCheckPresenter,OnModelListener,FinalHttpCallback{

	private Context context;
	private ReportCheckView reportCheckView;
	private Euser user;
	private CheckModel checkModel;
	private Params params;
	private FinalDb finalDb;
	private QRReportInfo qrReportInfo;//二維碼綁定報表信息
	private String reportType;//報表類型
	private List<CheckItem> checkItemList;//點檢項目列表
	private boolean isConfig;//報表是否配置標記，true:配置 false未配置
	private boolean WORight;//正常情況下工單是否輸入正確的標記，true:是
	private boolean isGetParams;//是否帶出標準參數，true:是
	
	private String checkByTeam;//用戶選擇的簽核人員team信息
	private List<Euser> allCheckByList;//具有簽核權限人員信息列表
	private List<Euser> checkByList;//查詢具有簽核權限人員信息列表（數據從allCheckByList中篩選）
	private List<Euser> selectedCheckByList;//已選擇的簽核人員信息列表
	private List<String> imgFilePathList;//上傳異常照片文件路徑集合
	private List<Bitmap> bitmapList;//上傳異常照片位圖集合
	private File imageFile;//上傳異常照片
	private File takePhotoFile;//點檢拍照圖片
	private String takePhotoName;//點檢照片名（無擴展名）
	private int tag;//點檢項索引
	private int submitAction;//提交動作（異常/點檢）
	public static final int SUBMITEXCEPTION=0;//異常提交
	public static final int SUBMITCHECK=1;//點檢提交
	
	private List<ParamsConfig> paramConfigList;//已配置帶參數的報表點檢子項信息
	private String openLine;//開線備註
	private String changeLine;//換線備註
	
	private static final int REQUESTCODE_SCANWO=10;//掃碼輸入工單請求碼
	private static final int REQUESTCODE_GALLERY=20;//圖庫請求碼
	private static final int REQUESTCODE_CAMERA_EXCEPTION=30;//異常拍照請求碼
	private static final int REQUESTCODE_SCANCONTENT=40;//掃碼輸入點檢內容請求碼
	private static final int REQUESTCODE_CAMERA_TAKEPHOTO=50;//點檢拍照請求碼
	private static final int REQUESTCODE_EDITIMAGE=60;//圖片編輯請求碼
	
	private int type;//點檢類型（正常/提前維護）
	private String date;//日期
	private String time;//時間
	
	public ReportCheckPresenterImpl(ReportCheckView reportCheckView,Context context){
		this.reportCheckView=reportCheckView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.checkModel=new CheckModelImpl(this);
		this.params=new Params(checkModel);
		this.isConfig=true;
		this.WORight=true;
		this.isGetParams=true;
		this.checkByList=new ArrayList<Euser>();
		this.selectedCheckByList=new ArrayList<Euser>();
		this.bitmapList=new ArrayList<Bitmap>();
		this.finalDb=FinalDb.create(context, FinalDB.DB_CHECKITEM, true);
		this.paramConfigList=new ArrayList<ParamsConfig>();
		this.openLine=context.getResources().getString(R.string.openline);
		this.changeLine=context.getResources().getString(R.string.changeline);
		this.type=ReportCheck.NORMAL;
	}

	@Override
	public void success(JsonResult result) {
		reportCheckView.dismissLoading();
		if(result.getAction().equals(Action.GET_WO)){//通過SN帶工單
			reportCheckView.setWO(result.getData().get(0).trim());//顯示為工單
		}
		if(result.getAction().equals(Action.GET_REPORTBASEINFO)){//帶出基本信息
			WORight=true;
			qrReportInfo=CheckPresenterHelper.getReportBaseInfo(result,qrReportInfo);
			//設置基本信息（CheckPD/IPQC）
			reportCheckView.setBaseInfo(
					qrReportInfo.getSkuNo(),
					qrReportInfo.getSkuVersion(),
					qrReportInfo.getQty(),
					qrReportInfo.getDeviation());
			if(MyConstant.DBSITE.equals(Site.TW)) reportCheckView.setWO(qrReportInfo.getWorkorderNo());//顯示為工單
			//部分報表需要帶出面別(CSD所有報表都要帶面別)
			if(qrReportInfo.getMFG().equalsIgnoreCase(DBFlag.CSD)||
					ReportNum.REPORT_GETSIDE.contains(qrReportInfo.getReportNum())){
				params=ParamsUtil.getParam(params, Action.GET_SIDE, new String[]{
						qrReportInfo.getWorkorderNo(),
						qrReportInfo.getEquipment(),
						qrReportInfo.getSkuNo(),
						qrReportInfo.getMFG()
				});
				checkModel.getSide(params);
			}
			//波峰焊不需要帶出面別，直接獲取基本標準參數
			else if(qrReportInfo.getReportNum().equals(ReportNum.PTH_WS)){//波峰焊
				params=ParamsUtil.getParam(params, Action.GET_PARAMS, new String[]{
						qrReportInfo.getReportNum(),
						qrReportInfo.getMFG(),
						qrReportInfo.getEquipment(),
						qrReportInfo.getSkuNo()
				});
				checkModel.getParams(params);
			}else{//不需要帶面別的，部分子項可能需要帶參數
				getCheckItemParam();//獲取配置帶參的點檢項參數
			}
		}
		if(result.getAction().equals(Action.GET_CHECKITEM)){
			checkItemList=CheckPresenterHelper.getCheckItem(context,result,
					qrReportInfo.getReportNum());//獲得點檢項目列表
			reportCheckView.setCheckItemAdapter(checkItemList);//設置點檢項的適配器
			getShiftCheckNode();//獲得班別和節次
		}
		if(result.getAction().equals(Action.GET_SHIFTCHECKNODE)){//獲得班別節次
			qrReportInfo=CheckPresenterHelper.getShiftCheckNodeInfo(result,qrReportInfo);
			if(reportType.equals(Report.CHECK_IPQC)){//顯示IPQC節次
				reportCheckView.setCheckNode(qrReportInfo.getCheckNode());
			}
			setRNO();//設置點檢編號和樓層線體
			//查詢要帶參數的報數及其點檢子項（IPQC_Param_Config）
			if(!reportType.equals(Report.CHECK_NOINPUT)){//不輸工單的報表不需要考慮
				params=ParamsUtil.getParam(params, Action.GET_PARAMSCONFIG_REPORT,
						new String[]{qrReportInfo.getReportNum()});
				checkModel.getParamsConfigReport(params);
			}
		}
		if(result.getAction().equals(Action.GET_PARAMSCONFIG_REPORT)){//查詢要帶參數的報數及其點檢子項
			paramConfigList=CheckPresenterHelper.getParamConfig(result);
			//設置部分帶出固定值的點檢子項參數值
			checkItemList=CheckPresenterHelper.getFixedParams(checkItemList,paramConfigList);
			reportCheckView.notifyCheckItemDataSetChanged();//更新數據
		}
		if(result.getAction().equals(Action.GET_SIDE)){//獲得面別
			qrReportInfo.setSide(result.getData().get(0));
			reportCheckView.setSide(qrReportInfo.getSide());//設置面別
			//印刷機、波峰焊帶出標準參數
			if(qrReportInfo.getReportNum().equals(ReportNum.SMT_PRINTER)
					||qrReportInfo.getReportNum().equals(ReportNum.SMT_REFLOW)){//印刷機、回焊爐
				params=ParamsUtil.getParam(params, Action.GET_PARAMS,new String[]{ 
						qrReportInfo.getReportNum(),
						qrReportInfo.getFloorName(),
						qrReportInfo.getEquipment(),
						qrReportInfo.getSkuNo(),
						qrReportInfo.getSide()});
				checkModel.getParams(params);//獲取標準參數
			}else{
				getCheckItemParam();//獲取配置帶參的點檢項參數
			}
		}
		if(result.getAction().equals(Action.GET_PARAMS)){//帶出標準參數
			isGetParams=true;
			Map<String,String> standardParam=CheckPresenterHelper.getStandardParam(qrReportInfo.getReportNum(),
					result);
			qrReportInfo.setStandardParam(standardParam);
			reportCheckView.showGetParamsSuccessTip(View.VISIBLE);
			getCheckItemParam();//獲取配置帶參的點檢項參數
		}
		if(result.getAction().equals(Action.GET_CHECKITEM_PARAM)){//獲取部分要帶參數的點檢項的參數值
			checkItemList=CheckPresenterHelper.getCheckItemParam(result,checkItemList);
			reportCheckView.notifyCheckItemDataSetChanged();//刷新數據
		}
		if(result.getAction().equals(Action.GET_CHECKBY)){//獲得具有簽核權限的人員列表
			allCheckByList=CheckPresenterHelper.getCheckBy(result,checkByTeam);
			checkByList=allCheckByList;
			setCheckByAdapter();//設置簽核人適配器
			if(selectedCheckByList.size()<1){//當未選擇簽核人時，從數據庫獲取上級主管信息
				getMaster();
			}else{
				setSelectedCheckByAdapter();//設置已選簽核人的適配器
			}
		}
		if(result.getAction().equals(Action.GET_MASTER)){//獲得上級主管
			selectedCheckByList=CheckPresenterHelper.
					addMasterToSelectedCheckBy(result);//添加進已選擇的簽核主管列表中
			setSelectedCheckByAdapter();
		}
		if(result.getAction().equals(Action.SAVE_EXCEPTION)){//提交異常信息成功
			//設置點檢項為不是點檢內容
			checkItemList.get(tag).setCheckResult(Report.NOTCHECKCONTENT);
			reportCheckView.showToastSuccessMsg(R.string.submitexception_success);
			reportCheckView.dismissSubmitExceptionDialog();//關閉彈框
		}
		if(result.getAction().equals(Action.SAVE_CHECKINFO)){//提交點檢信息成功
			
			reportCheckView.dismissSubmitCheckDialog();
			if(result.getResultMsg().equals(ResultMsg.CHECKINFO_EXIST)){
				reportCheckView.showToastFailedMsg(R.string.submitcheck_exist);//已點檢
			}else{
				reportCheckView.showToastSuccessMsg(R.string.submitcheck_success);
				cancel(true);//退出當前界面
			}
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_WO)){
			reportCheckView.showToastFailedMsg(R.string.sn_error);//輸入SN有誤
		}
		if(result.getAction().equals(Action.GET_REPORTBASEINFO)){
			reportCheckView.showToastFailedMsg(R.string.wo_error);//獲取報表信息失敗(輸入工單有誤)
			reportCheckView.showGetParamsSuccessTip(View.GONE);
			WORight=false;//正常情況不允許提交
		}
		if(result.getAction().equals(Action.GET_CHECKITEM)){
			reportCheckView.showToastFailedMsg(R.string.report_notconfig);//報表未配置
			isConfig=false;
		}
		if(result.getAction().equals(Action.GET_SHIFTCHECKNODE)){
			reportCheckView.showToastFailedMsg(R.string.getshift_failed);//獲取班別節次失敗
		}
		if(result.getAction().equals(Action.GET_SIDE)){
			reportCheckView.showToastFailedMsg(R.string.side_notsupport);//未獲取到面別
		}
		if(result.getAction().equals(Action.GET_PARAMS)){
			reportCheckView.showToastFailedMsg(R.string.params_notsupport);//ME未提供標準參數
			reportCheckView.showGetParamsSuccessTip(View.GONE);
			isGetParams=false;
		}
		if(result.getAction().equals(Action.GET_CHECKBY)){//獲取簽核人員名單失敗
			reportCheckView.showToastFailedMsg(R.string.getcheckby_failed);
			checkByList.clear();//清空數據
			setCheckByAdapter();
		}
		if(result.getAction().equals(Action.SAVE_EXCEPTION)){//提交異常信息失敗
			reportCheckView.showToastFailedMsg(R.string.submitexception_failed);
		}
		if(result.getAction().equals(Action.SAVE_CHECKINFO)){//提交點檢信息失敗
			reportCheckView.showToastFailedMsg(R.string.submitcheck_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		reportCheckView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 部分報表需要另外帶參數
	 */
	private void getCheckItemParam(){
		if(paramConfigList.size()>0){//配置了帶參數的項才要從數據庫帶
			//獲取不是固定值類型的要帶參數的點檢子項ID，拼接成一個字符串
			String proIdStr=CheckPresenterHelper.getParamProId(paramConfigList,checkItemList);
			params=ParamsUtil.getParam(params, Action.GET_CHECKITEM_PARAM, new String[]{
					qrReportInfo.getReportNum(),
					qrReportInfo.getMFG(),
					qrReportInfo.getWorkorderNo(),
					qrReportInfo.getFloorName(),
					qrReportInfo.getEquipment(),
					qrReportInfo.getSkuNo(),
					qrReportInfo.getSkuVersion(),
					qrReportInfo.getQty(),
					qrReportInfo.getDeviation(),
					qrReportInfo.getSide(),
					qrReportInfo.getCheckRemark(),
					proIdStr
			});
			checkModel.getCheckItemParam(params);
		}
	}
	/**
	 * 初始化
	 */
	@Override
	public void init(int type, String date, String time) {
		this.type=type;
		if(type!=ReportCheck.NORMAL){//提前維護/補點檢
			this.date=date;
			this.time=time;
		}
		
		
	}
	/**
	 * 設置頂部標題
	 */
	@Override
	public void setTitle(QRReportInfo qrReportInfo) {
		this.qrReportInfo=qrReportInfo;
		reportType=qrReportInfo.getReportType();
		reportCheckView.setPageTitle(qrReportInfo.getReportName());
		if(type==ReportCheck.INADVANCE){//提前維護增加標識
			String maintenance=context.getResources().getString(R.string.maintenance_inadvance);
			reportCheckView.setPageTitle(qrReportInfo.getReportName()+"（"+maintenance+"）");
		}
		if(type==ReportCheck.SUPPLEMENT){//補點檢增加標識
			String supplement=context.getResources().getString(R.string.supplement_title);
			reportCheckView.setPageTitle(qrReportInfo.getReportName()+"（"+supplement+"）");
		}
		//默認顯示輸工單界面
		int checkInputVisibility=View.VISIBLE;
		int checkNoInputVisibility=View.GONE;
		if(reportType.equals(Report.CHECK_IPQC)){//IPQC需顯示節次
			reportCheckView.showCheckId();//顯示節次控件
		}
		if(reportType.equals(Report.CHECK_NOINPUT)){//CheckNoInput 顯示不輸工單頂部界面
			checkInputVisibility=View.GONE;
			checkNoInputVisibility=View.VISIBLE;
		}
		//顯示頂部界面
		reportCheckView.showTopLayout(checkInputVisibility, checkNoInputVisibility);
	}
	/**
	 * 顯示備註信息
	 */
	@Override
	public void selectCheckRemark() {
		String[] checkRemarkArray;
		//SMT開線&換線點檢表要顯示開線、換線備註
		if(ReportNum.REPORT_SHOWOPENCHANGE.contains(qrReportInfo.getReportNum())){
			checkRemarkArray=context.getResources().getStringArray(R.array.checkRemark_array);
		}else{//其他報表不顯示開線、換線備註
			checkRemarkArray=context.getResources().getStringArray(R.array.checkRemark2_array);
		}
		reportCheckView.showCheckRemarkSpinner(checkRemarkArray);
	}
	
	/**
	 * 顯示或隱藏頂部的報表信息
	 */
	@Override
	public void toggleTopInfo(int visibility) {
		if(visibility==View.VISIBLE){
			if(reportType.equals(Report.CHECK_NOINPUT)){
				reportCheckView.toggleNoInputTopInfo(View.GONE,R.drawable.arrow_down);
			}else{//checkpd/IPQC
				reportCheckView.toggleTopInfo(View.GONE,R.drawable.arrow_down);
			}
		}else{
			if(reportType.equals(Report.CHECK_NOINPUT)){
				reportCheckView.toggleNoInputTopInfo(View.VISIBLE,R.drawable.arrow_up);
			}else{//checkpd/IPQC
				reportCheckView.toggleTopInfo(View.VISIBLE,R.drawable.arrow_up);
			}
		}
	}
	/**
	 * 輸入工單帶出基本信息
	 */
	@Override
	public void getBaseInfo(String workorderNo) {
		if(!isConfig){
			reportCheckView.showToastFailedMsg(R.string.report_notconfig);//報表未配置
			return;
		}
		qrReportInfo.setWorkorderNo(workorderNo);
		if(workorderNo.length()==11&&!workorderNo.startsWith("00")
				&&!MyConstant.DBSITE.equals(Site.TW)){//SN位數為11位並且不是台灣廠區
			//獲取工單
			params=ParamsUtil.getParam(params, Action.GET_WO,
					new String[]{user.getMfg(),workorderNo});
			checkModel.getWO(params);
		}else if(workorderNo.length()>=10){
			//update in 2018/05/15  添加CSD邏輯
			if(qrReportInfo.getMFG().equalsIgnoreCase(DBFlag.CSD)){//CSD的工單位數在10位以上
				doInfo(workorderNo);
			}else if(workorderNo.length()>=12){//正常工單位數為12位
				doInfo(workorderNo);
			}
			//reportCheckView.showLoading();
		}else{//輸入工單有誤
			WORight=false;
		}
	}
	/**
	 * 獲得基本信息  add in 2018/05/15
	 * @param workorderNo
	 */
	private void doInfo(String workorderNo){
		params=ParamsUtil.getParam(params, Action.GET_REPORTBASEINFO,
				new String[]{user.getMfg(),workorderNo});
		checkModel.getReportBaseInfo(params);
	}
	
	/**
	 * 獲取點檢項目
	 */
	@Override
	public void getCheckItem() {
		params=ParamsUtil.getParam(params, Action.GET_CHECKITEM,
				new String[]{
				qrReportInfo.getReportNum(),
				user.getSite(),
				qrReportInfo.getMFG(),
				qrReportInfo.getSBU()});
		checkModel.getCheckItem(params);
		reportCheckView.showLoading();
	}
	/**
	 * 獲得班別節次
	 */
	@Override
	public void getShiftCheckNode() {
		String[] param = null;
		if(type==ReportCheck.NORMAL){//正常點檢
			param=new String[]{""};
		}
		if(type==ReportCheck.INADVANCE||type==ReportCheck.SUPPLEMENT){//提前維護/補點檢
			//截取HH：MM，根據用戶設置的時間獲取班別和節次
			String nowTime=time.substring(0, time.indexOf("."));
			param=new String[]{nowTime};
		}
		params=ParamsUtil.getParam(params, Action.GET_SHIFTCHECKNODE, param);
		checkModel.getShiftCheckNode(params);
	}
	/**
	 * 設置點檢編號 線體樓層
	 */
	@Override
	public void setRNO() {
		String RNO="";
		//線別=樓層+“/”+設備名(IPQC/PD)
		String lineName=qrReportInfo.getFloorName()+"/"+qrReportInfo.getEquipment();
		if(reportType.equals(Report.CHECK_IPQC)){//IPQC的點檢編號（yyyyMMdd+MFG+工站+節次+班別） D/N
			//add in 2018/02/07 解決IPQC八九十節無法點檢問題
			Date nd=new Date();
			/*String checkIdStr="8,9,10";//第8,9,10節認為是前一天的點檢記錄，編號中的日期-1
			if(checkIdStr.contains(qrReportInfo.getCheckId())){
				nd.setDate(nd.getDate()-1);
			}*/
			String timeSuffix=TextUtil.dateFormat(nd, "yyyyMMdd");
			if(type!=ReportCheck.NORMAL){//提前維護時間/補點檢前綴
				Date d=TextUtil.strToDate(date, "yyyy/MM/dd");	
				timeSuffix=TextUtil.dateFormat(d, "yyyyMMdd");
			}
			//update in 2018/02/02
			//為了讓IPQC整個白班或晚班點檢基本信息在AuditBaseInfo頁面顯示，RNO命名去除節次 
			RNO=timeSuffix+qrReportInfo.getMFG()+qrReportInfo.getSBU()
					+qrReportInfo.getEquipment()/*+qrReportInfo.getCheckId()*/
					+qrReportInfo.getShiftName();
			reportCheckView.setCheckPdInputRNO(RNO,lineName);//設置編號和線別
		}else{
			//CheckPd和CheckNoInput點檢編號：yyyyMMddHHmmssSSS+MFG+工站+班別
			String timeSuffix=TextUtil.dateFormat(new Date(),"yyyyMMddHHmmssSSS");
			if(type!=ReportCheck.NORMAL){//提前維護/補點檢時間前綴
				Date d=TextUtil.strToDate(date+" "+time, "yyyy/MM/dd HH:mm:ss.SSS");	
				timeSuffix=TextUtil.dateFormat(d, "yyyyMMddHHmmssSSS");
			}
			RNO=timeSuffix+qrReportInfo.getMFG()+
					qrReportInfo.getSection()+qrReportInfo.getShiftName();
			if(reportType.equals(Report.CHECKPD_INPUT)){//CheckPdInput
				reportCheckView.setCheckPdInputRNO(RNO,lineName);//設置編號和線別
			}
			if(reportType.equals(Report.CHECK_NOINPUT)){//CheckNoInput
				reportCheckView.setCheckNoInputRNO(RNO,
						qrReportInfo.getFloorName(),qrReportInfo.getEquipment());
			}
		}
		this.qrReportInfo.setRNO(RNO);
		//獲取之前在本地數據庫中存儲的輸入內容信息
		List<CheckItem> saveItemList=checkModel.findCheckItem(finalDb, checkItemList);
		if(saveItemList.size()>0){//存儲過才顯示
			//輸入內容
			checkItemList=CheckPresenterHelper.getSaveItem(saveItemList,checkItemList,RNO);
			reportCheckView.showSaveInputInfo(saveItemList.get(0).getWorkorderNo());
		}
	}
	/**
	 * 掃碼輸入工單
	 */
	@Override
	public void scanWO() {
		reportCheckView.gotoActivityForResult(CaptureActivity.class, null, 
				REQUESTCODE_SCANWO);
	}
	/**
	 * 清除etWO焦點
	 */
	@Override
	public void clearFocus() {
		reportCheckView.clearFocus();
	}
	/**
	 * CheckPd/IPQC點檢提交
	 */
	@Override
	public void submitCheckPdOrIPQC(String checkRemark,
			String checkRemarkReason, String checkType,String deviation,String side) {
		if(isConfig){//必須配置報表才能提交
			qrReportInfo.setCheckRemark(checkRemark);
			qrReportInfo.setCheckRemarkReason(checkRemarkReason);
			if(type==ReportCheck.INADVANCE){//提前維護在末尾加上“（維護）”二字
				checkType=checkType+"（"+context.getResources().getString(R.string.maintenance)+"）";
			}
			if(type==ReportCheck.SUPPLEMENT){//補點檢在末尾加上“（補點檢）”二字
				checkType=checkType+"（"+context.getResources().getString(R.string.supplement_title)+"）";
			}
			qrReportInfo.setCheckType(checkType);
			if(!checkRemark.equals(Report.DEFAULT_VALUE)
					&&!checkRemark.equals(openLine)
					&&!checkRemark.equals(changeLine)){//除開線、換線、N/A外，停線等其他備註信息可直接提交
				qrReportInfo.setCheck(Report.NOTCHECK);//不點檢
				selectCheckBy();//選擇簽核人
			}else{
				//add in 2018/05/03  開線換線有兩種情況，一種可以不輸工單提交，一種必須輸工單（SMT開換線點檢表）
				if(checkRemark.equals(openLine)||checkRemark.equals(changeLine)){
					if(!qrReportInfo.getReportNum().equals(ReportNum.SMT_OPENCHANGE)){
						qrReportInfo.setCheck(Report.NOTCHECK);//不點檢
						selectCheckBy();//選擇簽核人
						return;
					}
				}
				qrReportInfo.setCheck(Report.CHECK);//點檢
				if(!TextUtil.isEmpty(qrReportInfo.getWorkorderNo())&&
				!qrReportInfo.getWorkorderNo().equals(Report.DEFAULT_VALUE)){//必須輸工單
					if(!WORight){
						//請輸入正確的工單
						reportCheckView.showTipDialog(R.string.check_tip,
								R.string.submitfailed_wowrong);
						return;
					}else if(!isGetParams){//未獲取到標準參數，不允許提交
						reportCheckView.showTipDialog(R.string.check_tip,
								R.string.submitfailed_notgetparam);
						return;
					}
					//部分報表必須填寫deviation欄位，否則不能提交
					if(ReportNum.REPORT_DEVIATION.contains(qrReportInfo.getReportNum())){
						if(TextUtil.isEmpty(deviation)){//deviation不允許為空
							reportCheckView.showToastFailedMsg(R.string.submitfailed_deviationempty);
							return;
						}
						qrReportInfo.setDeviation(deviation);//設置deviation欄位
					}
					//部分報表必須填寫side欄位，否則不能提交
					if(ReportNum.REPORT_SIDE.contains(qrReportInfo.getReportNum())){
						if(TextUtil.isEmpty(side)){//side不允許為空
							reportCheckView.showToastFailedMsg(R.string.submitfailed_sideempty);
							return;
						}
						qrReportInfo.setSide(side);//設置side欄位
					}
					if(checkOK()){
						selectCheckBy();//選擇簽核人
					}
				}else{
					//請輸入工單后提交
					reportCheckView.showTipDialog(R.string.check_tip,
							R.string.submitfailed_inputwo);
				}
			}
		}else{//未配置點檢項不允許提交
			reportCheckView.showTipDialog(R.string.check_tip,
					R.string.submitfailed_notconfig);
		}
	}
	/**
	 * 不輸工單報表點檢提交
	 */
	@Override
	public void submitCheckNoInput() {
		if(isConfig){
			if(checkOK()){
				/*if(type==ReportCheck.INADVANCE){//提前維護在末尾加上“（維護）”二字
					String checkType=qrReportInfo.getCheckType()+
							"（"+context.getResources().getString(R.string.maintenance)+"）";
					qrReportInfo.setCheckType(checkType);
				}*/
				selectCheckBy();
			}
		}else{//未配置點檢項不允許提交
			reportCheckView.showTipDialog(R.string.check_tip,
					R.string.submitfailed_notconfig);
		}
	}
	/**
	 * 不輸工單報表點檢提交（提前維護/補點檢,添加備註選項）  add in 2018/4/26
	 */
	@Override
	public void submitCheckNoInput(String checkRemark, String checkRemarkReason) {
		if(isConfig){
			String checkType="";
			if(type==ReportCheck.INADVANCE){//提前維護在末尾加上“（維護）”二字
				checkType="（"+context.getResources().getString(R.string.maintenance)+"）";
			}
			if(type==ReportCheck.SUPPLEMENT){//補點檢在末尾加上“（補點檢）”二字
				checkType="（"+context.getResources().getString(R.string.supplement_title)+"）";
			}
			qrReportInfo.setCheckType(checkType);
			if(!checkRemark.equals(Report.DEFAULT_VALUE)){//除N/A外，其他備註信息可直接提交
				qrReportInfo.setCheck(Report.NOTCHECK);//不點檢
				selectCheckBy();//選擇簽核人
			}else{
				qrReportInfo.setCheck(Report.CHECK);//點檢
				if(checkOK()){
					selectCheckBy();
				}
			}
		}else{//未配置點檢項不允許提交
			reportCheckView.showTipDialog(R.string.check_tip,
					R.string.submitfailed_notconfig);
		}
	}
	
	/**
	 * 點檢檢驗是否成功  true：是
 	 * @return
	 */
	private boolean checkOK(){
		checkItemList=reportCheckView.getCheckItemList();
		//校驗
		boolean checkOK=CheckReportHelper.checkInputContent(
				context, reportCheckView, checkItemList, qrReportInfo);	
		return checkOK;
	}
	
	/**
	 * 選擇簽核人
	 */
	private void selectCheckBy(){
		//選擇簽核人
		String submitCheckTitle=context.getResources()
				.getString(R.string.submitcheck_title);
		submitAction=SUBMITCHECK;//提交點檢
		reportCheckView.showCheckByDialog(
				submitCheckTitle,R.string.btn_submitcheck,R.string.btn_no);
	}
	/**
	 * 獲取具有簽核權限的人員信息
	 */
	@Override
	public void getCheckBy(String team) {
		checkByTeam=team;
		params=ParamsUtil.getParam(params, Action.GET_CHECKBY, new String[]
				{team,user.getMfg()});
		checkModel.getCheckBy(params);
	}
	/**
	 * 獲取簽核上級主管
	 */
	@Override
	public void getMaster() {
		params=ParamsUtil.getParam(params, Action.GET_MASTER, new String[]
				{user.getLogonName()});
		checkModel.getMaster(params);
	}
	/**
	 * 設置提交簽核人信息的Action
	 */
	@Override
	public void setSubmitAction(int submitAction) {
		this.submitAction=submitAction;
	}
	/**
	 * 設置選擇簽核人的適配器
	 */
	private void setCheckByAdapter(){
		if(submitAction==SUBMITEXCEPTION){
			reportCheckView.setCheckByAdapter(checkByList);//設置選擇簽核人的適配器
		}
		if(submitAction==SUBMITCHECK){
			reportCheckView.setSubmitCheckAdapter(checkByList);
		}
	}
	/**
	 * 設置已選簽核人的適配器
	 */
	private void setSelectedCheckByAdapter(){
		if(submitAction==SUBMITEXCEPTION){
			reportCheckView.setSelectedCheckByAdapter(selectedCheckByList);
		}
		if(submitAction==SUBMITCHECK){
			reportCheckView.setSubmitSelectedCheckAdapter(selectedCheckByList);
		}
	}
	/**
	 * 添加審核人至已選擇簽核人列表中
	 */
	@Override
	public void addCheckBy(int tag) {
		Euser checkBy=checkByList.get(tag);
		//不存在則添加進來
		if(!CheckPresenterHelper.existInSelected(checkBy,selectedCheckByList)){
			selectedCheckByList.add(checkBy);
			setSelectedCheckByAdapter();
		}
	}
	/**
	 * 刪選已選擇簽核人
	 */
	@Override
	public void deleteCheckBy(int tag) {
		Euser selectedCheckBy=selectedCheckByList.get(tag);
		selectedCheckByList.remove(selectedCheckBy);
		setSelectedCheckByAdapter();
	}
	/**
	 * 查詢簽核人
	 */
	@Override
	public void queryCheckBy(String logonName) {
		logonName=TextUtil.simpleToTradition(logonName);//要查詢的簽核人工號或姓名
		checkByList=CheckPresenterHelper.queryCheckBy(allCheckByList,logonName);
		setCheckByAdapter();
	}
	/**
	 * 選擇完簽核人，進入下一步(提交異常)
	 */
	@Override
	public void submitNextStep(int tag) {
		this.tag=tag;
		if(selectedCheckByList.size()<1){
			reportCheckView.showToastFailedMsg(R.string.checkby_notselect);
		}else{
			reportCheckView.dismissSelectCheckByDialog();
			imgFilePathList=new ArrayList<String>();//每次點開重置圖片路徑集合，需要重新添加圖片
			String title=CheckPresenterHelper.getTitle(
					context, checkItemList.get(tag), R.string.submitexception_title);
			reportCheckView.showSubmitExceptionDialog(title,selectedCheckByList);
		}
	}
	/**
	 * 提交異常信息
	 */
	@Override
	public void submitException(String exception) {
		if(TextUtil.isEmpty(exception)){//異常信息必須填寫
			reportCheckView.showToastFailedMsg(R.string.exceptioninfo_empty);
			return;
		}
		exception=TextUtil.simpleToTradition(exception);//轉為繁體
		String toUsers=CheckPresenterHelper.getToUsers(selectedCheckByList);
		String pictureUrl=CheckPresenterHelper.getPictureUrl(imgFilePathList);
		//提交異常信息
		params=ParamsUtil.getParam(params, Action.SAVE_EXCEPTION, 
				new String[]{
					user.getSite(),
					user.getBu(),
					qrReportInfo.getMFG(),
					qrReportInfo.getEquipment(),
					qrReportInfo.getFloorName(),
					user.getTeam(),
					qrReportInfo.getReportNum(),
					qrReportInfo.getRNO(),
					checkItemList.get(tag).getProId(),
					qrReportInfo.getCheckId(),//除IPQC外，默認為0
					user.getLogonName(),
					exception,
					toUsers,
					pictureUrl
		});
		checkModel.submitException(params);
		reportCheckView.showLoading();
		if(imgFilePathList.size()>0){//上傳圖片
			List<File> imgFileList=CheckPresenterHelper.getUploadFileList(imgFilePathList);
			checkModel.uploadExceptionPhoto(this,Exception.UPLOAD_URL,imgFileList);
		}
	}
	/**
	 * 上傳圖庫圖片
	 */
	@Override
	public void OpenGallery() {
		if(imgFilePathList.size()<Exception.MAX_PHOTONUM){
			reportCheckView.goActivityForResult(
					IntentUtil.openGallery(),REQUESTCODE_GALLERY);
		}else{//只允許上傳三張圖片
			reportCheckView.showToastFailedMsg(R.string.photonum_toomore);
		}
	}
	/**
	 * 上傳照片
	 */
	@Override
	public void openCamera() {
		if(imgFilePathList.size()<Exception.MAX_PHOTONUM){
			FileUtil.createLocalDir(Exception.PHOTO_DIR);//創建目錄
			//圖片以年月日時分秒命名
			String imgPath=Exception.PHOTO_DIR+
					TextUtil.dateFormat(new Date(),"yyyyMMddHHmmss")+".png";
			imageFile=FileUtil.getLocalFile(imgPath);
			reportCheckView.goActivityForResult(
					IntentUtil.openCamera(Uri.fromFile(imageFile)),
					REQUESTCODE_CAMERA_EXCEPTION);
		}else{//只允許上傳三張圖片
			reportCheckView.showToastFailedMsg(R.string.photonum_toomore);
		}
	}
	
	//文件上傳下載的回調接口
	@Override
	public void downloadSuccess(File file) {}

	@Override
	public void downloadFailure(int errorNo, String strMsg) {}

	@Override
	public void uploadStart() {}

	@Override
	public void uploadSuccess(Object t) {
		reportCheckView.showToastSuccessMsg(R.string.submitphoto_success);
	}

	@Override
	public void uploadFailure(int errorNo, String strMsg) {}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==Activity.RESULT_OK){
			switch (requestCode) {
			case REQUESTCODE_SCANWO://掃碼輸入工單
					String WO=data.getStringExtra("result");
					reportCheckView.setWO(WO);
				break;
			case REQUESTCODE_SCANCONTENT://掃碼輸入點檢內容
				String checkContent=data.getStringExtra("result");
				reportCheckView.setCheckContent(tag,checkContent);
				break;
			case REQUESTCODE_GALLERY://圖庫
					String path=ContentProviderUtil.getImageMediaData(
							context, data.getData());
					String fileName=TextUtil.dateFormat(new Date(), "yyyyMMddHHmmss");
					String imagePath=FileUtil.saveToLocal(
							BitmapFactory.decodeFile(path),Exception.PHOTO_DIR, 
							fileName);
					imgFilePathList.add(imagePath);//保存圖片路徑
					//根據路徑獲得位圖集合，縮略圖顯示在界面上
					bitmapList=CheckPresenterHelper.getBitmap(context,imgFilePathList);
					reportCheckView.setBitmapAdapter(bitmapList);
				break;
			case REQUESTCODE_CAMERA_EXCEPTION://異常圖片相機
				imgFilePathList.add(imageFile.getAbsolutePath());
				bitmapList=CheckPresenterHelper.getBitmap(context,imgFilePathList);
				reportCheckView.setBitmapAdapter(bitmapList);
				break;
			case REQUESTCODE_CAMERA_TAKEPHOTO://點檢拍照圖片相機
				Intent intent=new Intent(context,EditImageActivity.class);
				intent.putExtra("path", takePhotoFile.getAbsolutePath());
				intent.putExtra("savePath", TakePhoto.PHOTO_DIR);
				intent.putExtra("imgFileName",takePhotoName);
				reportCheckView.goActivityForResult(intent, REQUESTCODE_EDITIMAGE);
				break;
			case REQUESTCODE_EDITIMAGE://編輯圖片
				String imgPath=data.getStringExtra("path");
				reportCheckView.setTakePhotoBitmap(tag,imgPath);
				break;
			default:
				break;
			}
		}
	}
	/**
	 * 掃描二維碼輸入點檢內容
	 */
	@Override
	public void scanCheckContent(int tag) {
		this.tag=tag;
		reportCheckView.gotoActivityForResult(CaptureActivity.class, null, 
				REQUESTCODE_SCANCONTENT);
	}
	/**
	 * 打開點檢拍照的相機
	 */
	@Override
	public void openCheckCamera(int tag) {
		this.tag=tag;
		File dir=FileUtil.createLocalDir(TakePhoto.PHOTO_DIR);
		//點檢拍照的圖片命名：點檢編號+“_”+proId
		takePhotoName=qrReportInfo.getRNO()+"_"+checkItemList.get(tag).getProId();
		takePhotoFile=new File(dir,takePhotoName+".png");
		reportCheckView.goActivityForResult(
				IntentUtil.openCamera(Uri.fromFile(takePhotoFile)),
				REQUESTCODE_CAMERA_TAKEPHOTO);
	}
	/**
	 * 提交點檢信息
	 */
	@Override
	public void submitCheck() {
		if(selectedCheckByList.size()<1){
			reportCheckView.showToastFailedMsg(R.string.checkby_notselect);
			return;
		}
		//設置提前維護或補點檢的創建時間
		if(type!=ReportCheck.NORMAL){
			qrReportInfo.setCreateDate(date+" "+time);
		}
		//簽核人字符串
		String checkByStr=CheckPresenterHelper.getCheckByStr(selectedCheckByList);
		//proId字符串
		String proIdStr=CheckPresenterHelper.getProIdStr(checkItemList);
		//提交的點檢內容字符串
		String checkContentStr=CheckPresenterHelper.getCheckContentStr(checkItemList);
		checkContentStr=TextUtil.simpleToTradition(checkContentStr);
		//是否正常點檢的標記
		String checkResultStr=CheckPresenterHelper.getCheckResultStr(checkItemList);
		//點檢拍照圖片數據
		String imageDataStr=CheckPresenterHelper.getCheckImageDataStr(checkItemList);
		params=ParamsUtil.getParam(params, Action.SAVE_CHECKINFO, new String[]{
				qrReportInfo.getRNO(),
				qrReportInfo.getCheckId(),
				qrReportInfo.getWorkorderNo(),
				qrReportInfo.getSkuNo(),
				qrReportInfo.getQty(),
				qrReportInfo.getSkuVersion(),
				qrReportInfo.getSide(),
				qrReportInfo.getDeviation(),
				qrReportInfo.getCheck(),
				qrReportInfo.getCheckRemark(),
				qrReportInfo.getCheckRemarkReason(),
				qrReportInfo.getCheckType(),
				user.getLogonName(),
				qrReportInfo.getReportNum(),
				qrReportInfo.getMFG(),
				qrReportInfo.getFloorName(),
				qrReportInfo.getEquipment(),
				qrReportInfo.getShiftName(),
				checkByStr,
				proIdStr,
				checkContentStr,
				checkResultStr,
				/*imageDataStr,*/
				Report.SPLIT,
				qrReportInfo.getCreateDate()
		},imageDataStr);
		checkModel.submitCheckInfo(params);//提交點檢信息
		reportCheckView.showLoading();
	}
	/**
	 * 退出當前界面
	 */
	@Override
	public void cancel(boolean isSubmit) {
		if(isConfig){//必須已配置點檢項
			checkItemList=reportCheckView.getCheckItemList();
			checkModel.deleteCheckItem(finalDb,checkItemList);//刪除已保存的checkItem
			if(!isSubmit){//非提交狀態下退出保存輸入內容
				//保存當前界面上的輸入內容（包括工單/點檢編號）到數據庫中
				checkModel.saveCheckItem(finalDb,checkItemList,qrReportInfo.getWorkorderNo(),
						qrReportInfo.getReportNum(),qrReportInfo.getRNO());
			}else{//點檢成功/刪除點檢照片文件夾和異常上傳圖片文件夾
				//刪除存放異常上傳圖片文件夾
				FileUtil.deleteAllFilesOfDir(FileUtil.getLocalFile(Exception.PHOTO_DIR));
				//刪除存放點檢照片文件夾
				FileUtil.deleteAllFilesOfDir(FileUtil.getLocalFile(TakePhoto.PHOTO_DIR));
			}
		}
		reportCheckView.cancel();
	}
	/**
	 * 設置備註參數
	 */
	@Override
	public void setCheckRemark(String checkRemark) {
		qrReportInfo.setCheckRemark(checkRemark);
		if(checkRemark.equals(openLine)||checkRemark.equals(changeLine)){
			boolean yes=CheckPresenterHelper.getOpenOrChangeLineParam
					(paramConfigList,checkRemark);//判斷開線換線是否要帶數據
			if(yes){
				getCheckItemParam();//獲取點檢項目參數
			}
		}
	}
}
