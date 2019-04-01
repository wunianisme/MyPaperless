package service;

import com.foxconn.server.bean.JsonParam;
import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyAction.Action;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
import com.foxconn.server.dao.ServiceDaoImpl;
import com.foxconn.server.model.account.AccountModel;
import com.foxconn.server.model.account.AccountModelImpl;
import com.foxconn.server.model.account.FeedbackModel;
import com.foxconn.server.model.account.FeedbackModelImpl;
import com.foxconn.server.model.check.CheckModel;
import com.foxconn.server.model.check.CheckModelImpl;
import com.foxconn.server.model.function.AuditSearchModel;
import com.foxconn.server.model.function.AuditSearchModelImpl;
import com.foxconn.server.model.function.CheckSearchModel;
import com.foxconn.server.model.function.CheckSearchModelImpl;
import com.foxconn.server.model.function.ExceptionManageModel;
import com.foxconn.server.model.function.ExceptionManageModelImpl;
import com.foxconn.server.model.function.MTInadvanceModel;
import com.foxconn.server.model.function.MTInadvanceModelImpl;
import com.foxconn.server.model.function.ParameterManageModel;
import com.foxconn.server.model.function.ParameterManageModelImpl;
import com.foxconn.server.model.function.ReportConfigModel;
import com.foxconn.server.model.function.ReportConfigModelImpl;
import com.foxconn.server.model.login.LoginModel;
import com.foxconn.server.model.login.LoginModelImpl;
import com.foxconn.server.resource.Database;
import com.foxconn.server.util.GsonUtil;

public class MyPaperlessService {

	//private ServiceDao serviceDao;
	private LoginModel loginModel;
	private AccountModel accountModel;
	private FeedbackModel feedbackModel;
	private CheckModel checkModel;
	
	private AuditSearchModel auditSearchModel;
	private CheckSearchModel checkSearchModel;
	private ReportConfigModel reportConfigModel;
	private ParameterManageModel parameterManageModel;
	private ExceptionManageModel exceptionModel;
	private MTInadvanceModel mtInadvanceModel;
	
	
	static ServiceDao serviceDao=new ServiceDaoImpl();
	/**
	 * 普遍Action的連接服務器方法
	 * @param json JSON字符串
	 * @return
	 */
	public String getServerData(String json){
		
		JsonResult jsonResult = null;
		MyParam p = null;
		String jsonStr="";
		try {
			p=initData(json);//數據初始化
			jsonResult=getActionData(p);//獲得數據庫數據
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);//異常
			jsonResult.setResultMsg(e.getMessage());
		}finally{
			jsonResult.setAction(p.getAction());
			jsonStr=GsonUtil.beanToJson(jsonResult);//返回JSON字符串給客戶端
		}
		System.out.println("return JSON:"+jsonStr);
		System.out.println("--------------------------------------------------------");
		return jsonStr;
	}
	/**
	 * 帶提交base64圖片數據的Action的服務器連接方法
	 * @param json JSON字符串
	 * @param imageDataStr base64編碼圖片數據
	 * @return
	 */
	public String getServerDataWithImageData(String json,String imageDataStr){
		JsonResult jsonResult = null;
		MyParam p = null;
		String jsonStr="";
		try {
			p=initData(json);//數據初始化
			p.setImageDataStr(imageDataStr);//設置base64圖片數據
			jsonResult=getActionData(p);//獲得數據庫數據
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);//異常
			jsonResult.setResultMsg(e.getMessage());
		}finally{
			jsonResult.setAction(p.getAction());
			jsonStr=GsonUtil.beanToJson(jsonResult);//返回JSON字符串給客戶端
		}
		System.out.println("ImageData return JSON:"+jsonStr);
		System.out.println("--------------------------------------------------------");
		return jsonStr;
	}

	/**
	 * 初始化數據
	 * @param json
	 * @return
	 */
	private MyParam initData(String json){
		System.out.println("jsonParam:"+json);
		JsonParam jsonParam=GsonUtil.jsonToBean(json, JsonParam.class);//json轉化為bean
		System.out.println("action:"+jsonParam.getAction()+"," +
				"dbsite:"+jsonParam.getDBSite()+",paramsize:"+jsonParam.getPm().size());
		String action=jsonParam.getAction();//獲得Action
		String dbSite=jsonParam.getDBSite();
		//放棄使用連接池（更耗時），改回JDBC連接數據庫
		//serviceDao.initDBCP(Database.getPropertyFileName(site));//初始化DBCP连接池
		MyParam p=new MyParam(jsonParam, MySql.getSQL(action),Database.getDatabase(dbSite));//設置各項參數
		return p;
	}
	
	/**
	 * 获得Action数据
	 * @param p
	 * @return
	 */
	private JsonResult getActionData(MyParam p) {
		
		JsonResult jsonResult = null;
		//登陸模塊
		loginModel=new LoginModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_SERVER)){//获得厂区服务器信息
			jsonResult=loginModel.getServer(p);
		}
		if(p.getAction().equals(Action.CHECK_LOGIN)){//登陆校验
			jsonResult=loginModel.checkLogin(p);
		}
		if(p.getAction().equals(Action.UPDATE_PASSWORD)){//修改密碼
			jsonResult=loginModel.updatePwd(p);
		}
		if(p.getAction().equals(Action.GET_EMAIL)){//獲得用戶郵箱地址
			jsonResult=loginModel.getEmail(p);
		}
		if(p.getAction().equals(Action.FIND_PASSWORD)){//找回密碼
			jsonResult=loginModel.findPassword(p);
		}
		if(p.getAction().equals(Action.GET_APKINFO)){//獲得服務器更新的APK信息
			jsonResult=loginModel.getAppInfo(p);
		}
		if(p.getAction().equals(Action.SAVE_DOWNLOADINFO)){//保存用戶手機下載APK信息記錄
			jsonResult=loginModel.saveDownloadInfo(p);
		}
		//賬戶模塊
		accountModel=new AccountModelImpl(serviceDao);
		//賬號信息和添加員工兩個模塊均用到以下四個Action
		if(p.getAction().equals(Action.GET_MFG_ITEM)){//獲得BU下的所有製造處名稱
			jsonResult=accountModel.getMFGItem(p);
		}
		if(p.getAction().equals(Action.GET_SBU_ITEM)){//獲得BU下的所有SBU、製造處名稱
			jsonResult=accountModel.getMFGItem(p);
		}
		if(p.getAction().equals(Action.GET_TEAM_ITEM)){//獲得BU下的所有Team、SBU、製造處名稱
			jsonResult=accountModel.getMFGItem(p);
		}
		if(p.getAction().equals(Action.GET_SECTION_ITEM)){//獲得BU下的所有工站、SBU名稱
			jsonResult=accountModel.getMFGItem(p);
		}
		if(p.getAction().equals(Action.UPDATE_ACCOUNTINFO)){//保存修改后的賬號信息
			jsonResult=accountModel.saveAccountInfo(p);
		}
		if(p.getAction().equals(Action.GET_EMPLOYEEINFO)){//獲得同一個製造處、SBU和Team下的員工信息
			jsonResult=accountModel.getEmployeeInfo(p);
		}
		if(p.getAction().equals(Action.SAVE_EMPLOYEEINFO)){//添加員工賬號
			jsonResult=accountModel.saveEmployeeInfo(p);
		}
		//意見反饋
		feedbackModel=new FeedbackModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_FEEDBACK)){//獲得意見反饋記錄
			jsonResult=feedbackModel.getFeedback(p);
		}
		if(p.getAction().equals(Action.SAVE_FEEDBACK)){//添加意見反饋記錄
			jsonResult=feedbackModel.saveFeedback(p);
		}
		//點檢模塊
		checkModel=new CheckModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_BU)){//獲得廠區和事業群下的所有BU
			jsonResult=checkModel.getBU(p);
		}
		if(p.getAction().equals(Action.GET_BU_REPORTNAME)){//獲得BU下的報表名稱和編號
			jsonResult=checkModel.getBUReportName(p);
		}
		if(p.getAction().equals(Action.GET_QR_REPORTINPUTTYPE)){//获得二维码对应的报表输入类型（输工单/不输工单）
			jsonResult=checkModel.getQRReportInputType(p);
		}
		if(p.getAction().equals(Action.GET_CHECKRECORD)){//獲得該時段的點檢記錄（有則表示已被點檢）
			jsonResult=checkModel.getCheckRecord(p);
		}
		if(p.getAction().equals(Action.GET_CHECKITEM)){//獲得報表配置的點檢項目
			jsonResult=checkModel.getCheckItem(p);
		}
		if(p.getAction().equals(Action.GET_WO)){//通過SN帶出工單
			jsonResult=checkModel.getWO(p);
		}
		if(p.getAction().equals(Action.GET_REPORTBASEINFO)){//獲得點檢報表的基本信息（機種、機種版本、批量、deviation）
			jsonResult=checkModel.getReportBaseInfo(p);
		}
		if(p.getAction().equals(Action.GET_SHIFTCHECKNODE)){//獲得班別和節次
			jsonResult=checkModel.getShiftCheckNode(p);
		}
		if(p.getAction().equals(Action.GET_SIDE)){//獲得面別
			jsonResult=checkModel.getSide(p);
		}
		if(p.getAction().equals(Action.GET_PARAMS)){//獲得標準參數（印刷機、回焊爐、波峰焊）
			jsonResult=checkModel.getParams(p);
		}
		if(p.getAction().equals(Action.GET_CHECKBY)){//獲取具有簽核權限的人員信息（工號和中文名）
			jsonResult=checkModel.getCheckBy(p);
		}
		if(p.getAction().equals(Action.GET_MASTER)){//獲取上級主管
			jsonResult=checkModel.getMaster(p);
		}
		if(p.getAction().equals(Action.SAVE_EXCEPTION)){//提交異常信息
			jsonResult=checkModel.saveException(p);
		}
		if(p.getAction().equals(Action.SAVE_CHECKINFO)){//提交點檢信息
			jsonResult=checkModel.saveCheckInfo(p);
		}
		if(p.getAction().equals(Action.GET_PARAMSCONFIG_REPORT)){//獲取已配置了要帶參數的報表（除三個帶參數表的報表外）
			jsonResult=checkModel.getParamsConfigReport(p);
		}
		if(p.getAction().equals(Action.GET_CHECKITEM_PARAM)){//獲取部分要帶參數的點檢項的參數值
			jsonResult=checkModel.getCheckItemParam(p);
		}
		//功能管理模塊
		//簽核查詢
		auditSearchModel=new AuditSearchModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_MESSAGE_NUM)){//獲得功能菜單中消息條數
			jsonResult=auditSearchModel.getMessageNum(p);
		}
		if(p.getAction().equals(Action.GET_MFG_REPORT)){//獲取製造處下所有已導入的報表信息
			jsonResult=auditSearchModel.getMFGReport(p);
		}
		if(p.getAction().equals(Action.GET_AUDIT_REPORT)){//獲取待簽核/已簽核/拒簽報表信息
			jsonResult=auditSearchModel.getAuditReport(p);
		}
		if(p.getAction().equals(Action.GET_AUDIT_INFO_BYQRCODE)){//掃碼獲得簽核記錄 add in 2018/2/7
			jsonResult=auditSearchModel.getAuditInfoByQRCode(p);
		}
		if(p.getAction().equals(Action.GET_AUDIT_INFO)){////獲得簽核歷史/待簽核/已簽核/拒簽記錄
			jsonResult=auditSearchModel.getAuditInfo(p);
		}
		if(p.getAction().equals(Action.GET_AUDIT_BASEINFO)){//獲得簽核記錄基本信息
			jsonResult=auditSearchModel.getAuditBaseInfo(p);
		}
		if(p.getAction().equals(Action.GET_AUDIT_CHECKRESULT)){//獲得簽核詳細點檢信息
			jsonResult=auditSearchModel.getAuditCheckResult(p);
		}
		if(p.getAction().equals(Action.GET_REJECT_REMARK)){//獲得拒簽原因
			jsonResult=auditSearchModel.getRejectRemark(p);
		}
		if(p.getAction().equals(Action.UPDATE_AUDIT_RESULT)){//提交簽核結果
			jsonResult=auditSearchModel.updateAuditResult(p);
		}
		if(p.getAction().equals(Action.UPDATE_AUDIT_PASS)){//提交簽核通過（批量）
			jsonResult=auditSearchModel.updateAuditPass(p);
		}
		//點檢狀態查詢
		checkSearchModel=new CheckSearchModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_FLOORNAME)){//獲得BU和製造處下面的樓層
			jsonResult=checkSearchModel.getFloorName(p);
		}
		if(p.getAction().equals(Action.GET_LINENAME)){//獲得製造處樓層下面的線別
			jsonResult=checkSearchModel.getLineName(p);
		}
		if(p.getAction().equals(Action.GET_CHECKSTATUS)){//獲得同一個製造處下某天的報表點檢狀態
			jsonResult=checkSearchModel.getCheckStatus(p);
		}
		//報表配置
		reportConfigModel=new ReportConfigModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_REPORT_CHECKITEM)){//獲得報表點檢項
			jsonResult=reportConfigModel.getReportCheckItem(p);
		}
		if(p.getAction().equals(Action.GET_CONFIG_PROID)){//獲得該SBU報表已配置的PROID
			jsonResult=reportConfigModel.getConfigProId(p);
		}
		if(p.getAction().equals(Action.GET_SBU)){//獲得製造處下的所有已配置的SBU
			jsonResult=reportConfigModel.getSBU(p);
		}
		if(p.getAction().equals(Action.SAVE_REPORT_CONFIG)){//保存報表配置信息
			jsonResult=reportConfigModel.saveReportConfig(p);
		}
		//參數管理
		parameterManageModel=new ParameterManageModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_PARAM_FLOORNAME)){//獲得參數所對應的樓層
			jsonResult=parameterManageModel.getParamFloorName(p);
		}
		if(p.getAction().equals(Action.GET_PARAM_LINENAME)){//獲得參數表對應線別
			jsonResult=parameterManageModel.getParamLineName(p);
		}
		if(p.getAction().equals(Action.GET_PARAM_INFO)){//獲得參數基本信息
			jsonResult=parameterManageModel.getParamInfo(p);
		}
		if(p.getAction().equals(Action.GET_PARAM_DETAILINFO)){//獲得參數詳細信息
			jsonResult=parameterManageModel.getParamDetailInfo(p);
		}
		if(p.getAction().equals(Action.UPDATE_SMTPRINTER_INFO)){//修改SMT印刷機參數信息
			jsonResult=parameterManageModel.updateSMTPrinterInfo(p);
		}
		if(p.getAction().equals(Action.UPDATE_SMTREFLOW_INFO)){//修改SMT回焊爐參數信息
			jsonResult=parameterManageModel.updateSMTReflowInfo(p);
		}
		if(p.getAction().equals(Action.UPDATE_PTHWS_INFO)){//修改PTH波峰焊參數信息
			jsonResult=parameterManageModel.updatePTHWSInfo(p);
		}
		if(p.getAction().equals(Action.DELETE_SMTPRINTER_INFO)){//刪除SMT印刷機參數信息
			jsonResult=parameterManageModel.updateSMTPrinterInfo(p);
		}
		if(p.getAction().equals(Action.DELETE_SMTREFLOW_INFO)){//刪除SMT回焊爐參數信息
			jsonResult=parameterManageModel.updateSMTReflowInfo(p);
		}
		if(p.getAction().equals(Action.DELETE_PTHWS_INFO)){//刪除PTH波峰焊參數信息
			jsonResult=parameterManageModel.updatePTHWSInfo(p);
		}
		if(p.getAction().equals(Action.GET_PARAMCHECK_MASTER)){//獲得參數提交審核主管信息
			jsonResult=parameterManageModel.getParamCheckMaster(p);
		}
		if(p.getAction().equals(Action.GET_PARAMMYSUBMIT_INFO)){//獲得我提交的參數內容
			jsonResult=parameterManageModel.getParamMySubmitInfo(p);
		}	
		if(p.getAction().equals(Action.GET_PARAMMYCHECK_INFO)){//獲得我要簽核的參數內容
			jsonResult=parameterManageModel.getParamMyCheckInfo(p);
		}	
		if(p.getAction().equals(Action.GET_PARAMAUDIT_DETAILINFO)){//獲得參數簽核詳細信息
			jsonResult=parameterManageModel.getParamAuditDetailInfo(p);
		}
		if(p.getAction().equals(Action.PARAM_CHECKPASS)){//提交參數簽核通過
			jsonResult=parameterManageModel.paramCheckPass(p);
		}
		if(p.getAction().equals(Action.PARAM_CHECKFAILED)){//提交參數簽核駁回
			jsonResult=parameterManageModel.paramCheckFailed(p);
		}
		//異常管理
		exceptionModel=new ExceptionManageModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_EXCEPTIONINFO)){//獲得異常信息
			jsonResult=exceptionModel.getExceptionInfo(p);
		}
		if(p.getAction().equals(Action.GET_EXCEPTION_DETAILINFO)){//獲得詳細異常信息
			jsonResult=exceptionModel.getExceptionDetailInfo(p);
		}
		if(p.getAction().equals(Action.UPDATE_EXCEPTION_DEALSTATE)){//更改異常處理結果
			jsonResult=exceptionModel.updateExceptionDealState(p);
		}
		//提前維護線體
		mtInadvanceModel=new MTInadvanceModelImpl(serviceDao);
		if(p.getAction().equals(Action.GET_MT_FLOOR)){//獲得樓層
			jsonResult=mtInadvanceModel.getMTFloor(p);
		}
		if(p.getAction().equals(Action.GET_MT_LINE)){//獲得線別
			jsonResult=mtInadvanceModel.getMTLine(p);
		}
		if(p.getAction().equals(Action.GET_MT_CHECKRECORD)){//獲得提前維護/補點檢點檢記錄
			jsonResult=mtInadvanceModel.getMTCheckRecord(p);
		}
		if(p.getAction().equals(Action.SAVE_MT)){//提交提前維護信息
			jsonResult=mtInadvanceModel.saveMT(p);
		}
		return jsonResult;
	}
}
