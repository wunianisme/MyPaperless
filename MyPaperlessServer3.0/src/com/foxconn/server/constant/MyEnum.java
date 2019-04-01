package com.foxconn.server.constant;
/**
 * 定义枚举常量的类
 *@ClassName MyEnum
 *@Author wunian
 *@Date 2017/9/5 上午10:02:22
 */
public class MyEnum {

	//厂区
	public static final class Site{
		public static final String LH="LH";
		public static final String LH_121="LH_121";
		public static final String LH_131="LH_131";
		public static final String TW="TW";
		public static final String SH="SH";
		public static final String TJ="TJ";
		public static final String CQ="CQ";
		public static final String VN="VN";
		public static final String NN="NN";
		public static final String CQ_MBD="CQ_MBD";
		public static final String LH_NWE="LH_NWE";
	}
	public static final class WS{
		//台灣廠區提供的webservice接口,用於獲得報表基本信息
		public static final String WSDL_TW="http://10.59.73.106:855/WEBSERVICE/SFCWebservice1.asmx";
		public static final String NAMESPACE_TW="http://tempuri.org/";
		public static final String METHODNAME_TW="getsninfo";
		public static final String param_TW="strDB,data";//請求參數
	}
	
	//部分數據庫標記(部分是以製造處命名的)
	public static final class DBFlag{
		public static final String EERP="EERP";
		public static final String CDBU="CDBU";
		public static final String BPD="BPD";
		public static final String ALLPART="ALLPART";
		public static final String CND="CND";//華為數據庫
		public static final String CSD="CSD";
		public static final String MFGV="MFGV";
	}
	//郵箱報警
	public static final class EmailAlert{
		public static final int AUDITDELAY=2*60*60*1000;//點檢簽核報警間隔時間（提交2小時后）
		//public static final int AUDITDELAY=1000;//點檢簽核報警間隔時間（提交1小時后）
	}
	
	//報表編號
	public static final class ReportNum{
		//含參數表的三個報表
		public static final String SMT_PRINTER="FM3NCD034003A";//SMT印刷機參數表
		public static final String SMT_REFLOW="FM3NCD034003B";//SMT回焊爐參數表
		public static final String PTH_WS="FM3NMD197001E";//NSDI 開 (換)線點檢(PTH)
		public static final String SMT_OPENCHANGE="FD3NMD219001A";//SMT開線&換線點檢表
		public static final String SMT_TOURCHECK="FQ3NMD002002C";//SMT巡迴檢查記錄表
	}
	//報表點檢
	public static final class Report{
		//簽核人、proID、checkContent、checkResult、ImageData的分割符
		public static final String SPLIT="竜";
		//參數默認值(所有參數無值情況下默認為此值)
		public static final String DEFAULT_VALUE="N/A";
		public static final String NOAUDIT="0";//未簽核
		public static final String AUDITED="1";//已簽核
		
	}
	//面別
	public static final class Side{
		public static final String[] BOT={"B","BOT"};//B面
		public static final String[] TOP={"T","TOP"};//T面
		public static final String[] DOUBLE={"D","DOUBLE"};
	}
	//返回的结果码
	public static final class ResultCode{
		public static final int TRUE=1;//成功
		public static final int NULL=0;//空
		public static final int EXCEPTION=-1;//异常
		public static final int PASSWORDNOTLEGAL=2;//密码不合法
		public static final int LOGONNAME_NOTEXIST=3;//賬號不存在
		public static final int PWDERROR_NOLOCK=4;//密碼錯誤，未被鎖定
		public static final int PWDERROR_ISLOCK=5;//密碼錯誤，且剛好被鎖定
		public static final int ERROR_LOCEKD=6;//已被鎖定
	}
	//返回結果信息
	public static final class ResultMsg{
		public static final String SEND_SUCCESS="send success";//發送郵件成功
		public static final String SEND_ERROR="send error";//發送郵件失敗
		public static final String ADDEMPLOYEE_SUCCESS="addemployee success";//添加員工賬號成功
		public static final String ADDEMPLOYEE_EXISIT="addemployee exist";//添加員工賬號失敗（賬號已存在）
		public static final String CHECKINFO_EXIST="checkinfo exist";//點檢信息已存在
		
	}
	//異常
	public static final class Exception{
		public static final String SPLIT="---";//字符串分割符
		public final static int MYDEAL=1;//处理
		public final static int MYCREATE=2;//创建
	}
	//點檢項參數配置
	public static final class ParamConfig{
		//獲取數據的方法類型
		public static final String TYPE_WS="ws";
		public static final String TYPE_DB="db";
		//製造處
		public static final String MFG_ALL="ALL";//所有製造處均滿足
		public static final String MFG_OTHER="OTHER";//其他製造處
		public static final String MFGV="MFGV";//製造五處
		
		public static final String PARAM_SPLIT=",";//請求參數的分割符
		public static final String RESULT_SPLIT=";";//查詢結果的分割符
		
		public static final String DBTYPE_SQLSERVER="SQLSERVER";//sqlserver數據庫
		public static final String DBTYPE_ORACLE="ORACLE";//oracle數據庫
		
		//開換線前後的兩個proId
		public static final String OC_BEFORE_PROID="9";
		public static final String OC_AFTER_PROID="10";
		
		public static final String OPENLINE="開線";
		public static final String CHANGELINE="換線";
	}
	
	//報表配置
	public static final class ReportConfig{
		public final static String CHECKED="1";//已配置點檢子項標記
		public final static String NOCHECKED="0";//未配置點檢子項標記
		
		public final static String SPLIT="---";//字符串分割符
	}
	//參數管理
	public static final class ParamManage{
		//從SP：paramManage中查詢相關數據的Type
		//查詢基本參數信息
		public final static String SMT_PRINTER_SELECT="SMT-Printer-select";
		public final static String SMT_REFLOW_SELECT="SMT-Reflow-select";
		public final static String PTH_WS_SELECT="PTH-WS-select";
		//查詢詳細信息
		public final static String  SMT_PRINTER_SELECT_DETAIL="SMT-Printer-select-detail";
		public final static String  SMT_REFLOW_SELECT_DETAIL="SMT-Reflow-select-detail";
		public final static String  PTH_WS_SELECT_DETAIL="PTH-WS-select-detail";
		//查詢當前用戶提交的參數內容
		public final static String SMT_PRINTER_SUBMIT="SMT-Printer-submit";
		public final static String SMT_REFLOW_SUBMIT="SMT-Reflow-submit";
		public final static String PTH_WS_SUBMIT="PTH-WS-submit";
		//查詢當前用戶簽核的參數內容
		public final static String SMT_PRINTER_CHECK="SMT-Printer-check";
		public final static String SMT_REFLOW_CHECK="SMT-Reflow-check";
		public final static String PTH_WS_CHECK="PTH-WS-check";
		//查詢簽核信息
		public final static String SMT_PRINTER_AUDIT_DETAIL="SMT-Printer-audit-detail";
		public final static String SMT_REFLOW_AUDIT_DETAIL="SMT-Reflow-audit-detail";
		public final static String PTH_WS_AUDIT_DETAIL="PTH-WS-audit-detail";
	}
}
