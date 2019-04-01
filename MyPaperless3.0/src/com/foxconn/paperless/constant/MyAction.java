package com.foxconn.paperless.constant;
/**
 * 获取数据的动作常量类
 *@ClassName MyAction
 *@Author wunian
 *@Date 2017/9/11 下午2:55:49
 */
public class MyAction {
	//數據Action
	public static final class Action{
		
		/*-----------------------------登陸模塊--------------------------*/
		//获得各厂区服务器信息
		public static final String GET_SERVER="getserver";
		//登陆校验
		public static final String CHECK_LOGIN="checklogin";
		//修改密碼
		public static final String UPDATE_PASSWORD="updatepassword";
		//獲得用戶郵箱地址
		public static final String GET_EMAIL="getemail";
		//找回密碼
		public static final String FIND_PASSWORD="findpassword";
		//獲得服務器上APK的信息
		public static final String GET_APKINFO="getapkinfo";
		//添加手機下載信息記錄
		public static final String SAVE_DOWNLOADINFO="savedownloadinfo";
		
		/*--------------------------賬戶模塊---------------------------*/
		//獲得BU下的所有製造處列表
		public static final String GET_MFG_ITEM="getmfgitem";
		//獲得BU下的所有SBU列表
		public static final String GET_SBU_ITEM="getsbuitem";
		//獲得BU下的所有Team列表
		public static final String GET_TEAM_ITEM="getteamitem";
		//獲得BU下的所有工站列表
		public static final String GET_SECTION_ITEM="getsectionitem";
		//保存修改后的賬號信息
		public static final String UPDATE_ACCOUNTINFO="updateaccountinfo";
		//獲得同一個製造處、SBU和Team下的員工信息
		public static final String GET_EMPLOYEEINFO="getemployeeinfo";
		//添加員工賬號信息
		public static final String SAVE_EMPLOYEEINFO = "saveemployeeinfo";
		//獲得意見反饋記錄
		public static final String GET_FEEDBACK="getfeedback";
		//添加意見反饋記錄
		public static final String SAVE_FEEDBACK = "savefeedback";
		
		/*--------------------------點檢模塊---------------------------*/
		//獲取廠區和事業群下的所有BU
		public static final String GET_BU="getbu";
		//顯示BU下的報表名稱和編號
		public static final String GET_BU_REPORTNAME="getbureportname";
		//获得二维码对应的报表输入类型（输工单/不输工单）
		public static final String GET_QR_REPORTINPUTTYPE = "getqrreportinputtype";
		//獲得該時段的點檢記錄（有則表示已被點檢）
		public static final String GET_CHECKRECORD="getcheckrecord";
		//獲得報表配置的點檢項目
		public static final String GET_CHECKITEM="getcheckitem";
		//通過SN獲取工單
		public static final String GET_WO="getwo";
		//獲得點檢報表的基本信息（機種、機種版本、批量、deviation）
		public static final String GET_REPORTBASEINFO="getreportbaseinfo";
		//獲得班別和節次
		public static final String GET_SHIFTCHECKNODE="getshiftchecknode";
		//獲取面別
		public static final String GET_SIDE="getside";
		//獲得標準參數（印刷機、回焊爐、波峰焊）
		public static final String GET_PARAMS="getparams";
		//獲取具有簽核權限的人員信息（工號和中文名）
		public static final String GET_CHECKBY="getcheckby";
		//獲取上級主管信息
		public static final String GET_MASTER = "getmaster";
		//保存提交的異常點檢信息
		public static final String SAVE_EXCEPTION = "saveexception";
		//提交點檢信息（四合一）
		public static final String SAVE_CHECKINFO = "savecheckinfo";
		//獲取已配置了要帶參數的報表（除三個帶參數表的報表外）
		public static final String GET_PARAMSCONFIG_REPORT="getparamsconfigreport";
		//獲取部分要帶參數的點檢項的參數值
		public static final String GET_CHECKITEM_PARAM="getcheckitemparam";
		
		/*--------------------------功能管理模塊---------------------------*/
		//獲得功能菜單中消息條數
		public static final String GET_MESSAGE_NUM="getmessagenum";
		//審核查詢
		//獲取製造處下所有已導入的報表信息(歷史)
		public static final String GET_MFG_REPORT="getmfgreport";
		//獲取待簽核/已簽核/拒簽報表信息
		public static final String GET_AUDIT_REPORT="getauditreport";
		//掃碼獲得簽核記錄
		public static final String GET_AUDIT_INFO_BYQRCODE="getauditinfobyqrcode";
		//獲得簽核歷史/待簽核/已簽核/拒簽記錄
		public static final String GET_AUDIT_INFO="getauditinfo";
		//獲得簽核記錄基本信息
		public static final String GET_AUDIT_BASEINFO="getauditbaseinfo";
		//獲得點檢詳細點檢信息
		public static final String GET_AUDIT_CHECKRESULT="getauditcheckresult";
		//獲得拒簽原因
		public static final String GET_REJECT_REMARK="getrejectremark";
		//提交簽核結果
		public static final String UPDATE_AUDIT_RESULT="updateauditresult";
		//提交簽核通過（批量簽核）
		public static final String UPDATE_AUDIT_PASS="updateauditpass";
		
		//點檢狀態查詢
		//獲取BU製造處下面的所有樓層
		public static final String GET_FLOORNAME = "getfloorname";
		//獲取製造處下面的所有線別
		public static final String GET_LINENAME = "getlinename";
		//獲得點檢狀態
		public static final String GET_CHECKSTATUS = "getcheckstatus";
		//報表配置
		//獲得報表點檢項
		public static final String GET_REPORT_CHECKITEM="getconfigcheckitem";
		//獲得該SBU報表已配置的PROID
		public static final String GET_CONFIG_PROID="getconfigproid";
		//獲得製造處下的所有SBU
		public static final String GET_SBU="getsbu";
		//保存報表配置信息
		public static final String SAVE_REPORT_CONFIG="savereportconfig";
		//參數管理
		//獲得參數所對應的樓層
		public static final String GET_PARAM_FLOORNAME="getparamfloorname";
		//獲得參數對應的線別
		public static final String GET_PARAM_LINENAME="getparamlinename";
		//獲得參數基本信息
		public static final String GET_PARAM_INFO="getparaminfo";
		//獲得參數詳細信息
		public static final String GET_PARAM_DETAILINFO="getparamdetailinfo";
		//修改SMT印刷機參數信息
		public static final String UPDATE_SMTPRINTER_INFO="updatesmtprinterinfo";
		//修改SMT回焊爐參數信息
		public static final String UPDATE_SMTREFLOW_INFO="updatesmtreflowinfo";
		//修改PTH波峰焊參數信息
		public static final String UPDATE_PTHWS_INFO="updatepthwsinfo";
		//刪除SMT印刷機參數信息
		public static final String DELETE_SMTPRINTER_INFO="deletesmtprinterinfo";
		//刪除SMT回焊爐參數信息
		public static final String DELETE_SMTREFLOW_INFO="deletesmtreflowinfo";
		//刪除PTH波峰焊參數信息
		public static final String DELETE_PTHWS_INFO="deletepthwsinfo";
		//獲得參數提交審核主管信息
		public static final String GET_PARAMCHECK_MASTER="getparamcheckmaster";
		//獲得我提交的參數內容
		public static final String GET_PARAMMYSUBMIT_INFO="getparammysubmitinfo";
		//獲得我要簽核的參數內容
		public static final String GET_PARAMMYCHECK_INFO="getparammycheckinfo";
		//獲得參數簽核詳細信息
		public static final String GET_PARAMAUDIT_DETAILINFO="getparamauditdetailinfo";
		//提交參數簽核通過
		public static final String PARAM_CHECKPASS="paramcheckpass";
		//提交參數簽核駁回
		public static final String PARAM_CHECKFAILED="paramcheckfailed";
		//異常管理
		//獲得異常信息記錄
		public static final String GET_EXCEPTIONINFO="getexceptioninfo";
		//獲得異常詳細信息
		public static final String GET_EXCEPTION_DETAILINFO="getexceptiondetailinfo";
		//更改異常處理結果
		public static final String UPDATE_EXCEPTION_DEALSTATE="updateexceptiondealstate";
	
		//提前維護纖體
		//獲得樓層
		public static final String GET_MT_FLOOR="getmtfloor";
		//獲得纖體
		public static final String GET_MT_LINE="getmtline";
		//獲得提前維護/補點檢點檢記錄
		public static final String GET_MT_CHECKRECORD="getmtcheckrecord";
		//提交提前維護信息
		public static final String SAVE_MT="savemt";
	}
	
	//廣播Action
	public static final class BroadcastAction{
		//來自NetWorkService
		public static final String FromNetWorkService="FromNetWorkService";
	}
}
