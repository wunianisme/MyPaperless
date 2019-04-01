package com.foxconn.paperless.constant;

import java.util.Locale;

import android.content.Context;

import com.foxconn.paperless.activity.R;

/**
 * 定义枚举常量的类
 *@ClassName MyEnum
 *@Author wunian
 *@Date 2017/9/5 上午10:02:22
 */
public class MyEnum {
	//一些基本配置
	public static final class Config{
		//是否掃碼點檢（true:是，false:直接傳入二維碼編號）
		public static final boolean ScanToCheck=true; //更新時必須改為true
		//傳值點檢時的二維碼編號
		public static final String QRCode="201611170128302688";
		//初始化連接的數據庫對應的廠區(獲得服務器地址和APK下載地址時使用)
		public static final String InitSite="LH";//更新時改為要發佈的廠區
		//是否切換為測試模式（true:是，false:否     此模式下普通賬號可連接測試服務器）
		public static final boolean TestMode=false; //更新時必須改為false
	}
	//初始化時連接的服務器IP
	public static final class InitIP{
		public static final String LH="http://10.129.8.38:8888/";//龍華
		public static final String LH_131="http://10.167.4.131:8888/";//龍華（測試）
		public static final String VN="http://10.224.81.103:8888/";//越南
		public static final String NN="http://10.120.101.188:8888/";//南寧
		public static final String TW="http://10.59.65.95:8888/";//台灣
		public static final String CQ="http://10.129.4.77:8080/";//重慶(CBT)
		public static final String CQ_MBD="http://10.250.185.61:8888/";//重慶(MBD)
		public static final String LH_NWE="http://10.132.130.113:8888/";//龍華NWE
	}
	//廠區名或數據庫名
	public static final class Site{
		//public static final String INIT="LH_121";//初始化廠區
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
	//WebService（初始化）
	public static final class WS{
		//public static final String IP="http://10.132.46.102:8080/";//服務器IP
		//WebService後綴
		public static final String WSDLSUFFIX="MyPaperlessServer3.0/MyPaperlessServicePort?wsdl";
		public static final String NAMESPACE="http://service/";//命名空间
		public static final String METHODNAME="getServerData";//调用方法
		//帶提交base64圖片數據的Action調用方法
		public static final String METHODNAME_IMAGEDATA="getServerDataWithImageData";
		//台灣廠區提供的webservice接口
		public static final String WSDL_TW="http://10.59.73.106:855/WEBSERVICE/SFCWebservice1.asmx";
		public static final String NAMESPACE_TW="http://tempuri.org/";
		public static final String METHODNAME_TW="getsninfo";
	}
	//APK
	public static final class APK{
		public static final String NAME="MyPaperless3.0.apk";//APK名稱
		public static final String SAVEPATH = "/mnt/sdcard/MyPaperless3.0/";//APK本地保存路徑
	}
	//用戶頭像
	public static final class HeadPortrait{
		public static final String DIR_PATH="MyPaperless3.0/image/HeadPortrait/";//文件目錄路徑
		public static final String FILE_URI="/mnt/sdcard/MyPaperless3.0/" +
				"image/HeadPortrait/";//圖片在本地的路徑
		public static final String DOWNLOAD_URL=MyConstant.IP+"MyPaperlessServer3.0/image/";//下載地址
		public static final String UPLOAD_URL=MyConstant.IP+"MyPaperlessServer3.0/FileUploadServlet";//上傳地址
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
	//服务器类型
	public static final class ServerType{
		public static final String OFFICAl="offical";//正式服务器
		//public static final String TEST="test";//测试服务器
		public static final String ALL="all";//所有服務器
	}
	//部分數據庫標記(部分是以製造處命名的)
	public static final class DBFlag{
		public static final String CSD="CSD";//CSD製造處
	}
	//語言
	public static final class Language{
		public static final int CHINESE=0;//中文
		public static final int ENGLISH=1;//英文
		public static final int VIETNAMESE=2;//越南文
		public static Locale vietNam=new Locale("vi","VN");//越南語語言對象
		public static final Locale[] LOCALE={Locale.CHINESE,Locale.ENGLISH,vietNam};//語言對象數組
		
	}
	//网络类型
	public static final class NetWorkType{
		public static final String WIFI="WIFI";//wifi
		public static final String MOBILE="MOBILE";//移动网络
		public static final int DISCONNECT=R.string.disconnect;//无网络
	}
	//SharePreference信息
	public static final class SharePreferenceInfo{
		public static final String FILE_ACCOUNT="File_Account";//登陸賬號存儲文件
		public static final int FILE_ACCOUNT_MODE=Context.MODE_APPEND;//追加模式
		public static int SAVE_ACCOUNT_NUM=5;//Sharepreference保存賬號個數
		
		public static final String FILE_LASTACCOUNT="File_lastaccount";//最後一次登錄賬號存儲文件
		public static final int FILE_LASTACCOUNT_MODE=Context.MODE_PRIVATE;//私有模式
		
		public static final String FILE_BASEINFO="File_baseinfo";//其他基本信息存儲文件
		public static final int FILE_BASEINFO_MODE=Context.MODE_PRIVATE;
		public static final String KEY_LANGUAGEID="languageId";//語言Id
		
	}
	//用戶等級
	public static final class Userlevel{
		public static final String level_0="0";
		public static final String level_1="1";
		public static final String level_2="2";
		public static final String level_3="3";//只用於補點檢
	}
	//用戶的一些初始化屬性
	public static final class User{
		public static final String PASSWORD="Foxconn168!";//用戶的初始密碼
		public static final String BG="CNSBG";//默認事業群
	}
	//功能管理（配置功能菜單）
	public static final class FunctionManage{
		//各菜單對應的索引
		public static final int INDEX_AUDITSEARCH=0;
		public static final int INDEX_CHECKSEARCH=1;
		public static final int INDEX_CHECKEDIT=2;
		public static final int INDEX_MTADVANCE=3;
		public static final int INDEX_REPORTCONFIG=4;
		public static final int INDEX_PARAMMANAGE=5;
		public static final int INDEX_EXCEPTIONMANAGE=6;
		//菜單圖標
		public static final int[] iconArray={
			R.drawable.ic_auditsearch,
			R.drawable.ic_checksearch,
			R.drawable.ic_checkedit,
			R.drawable.ic_mtadvance,
			R.drawable.ic_reportconfig,
			R.drawable.ic_parammanage,
			R.drawable.ic_exceptionmanage
		};
		//菜單文字
		public static final int[] itemArray={
			R.string.audit_search,
			R.string.check_search,
			R.string.check_edit,
			R.string.maintenance_inadvance,
			R.string.report_config,
			R.string.parameter_manage,
			R.string.exception_manage
		};
	}
	//輸入工單標記
	public static final class IsInputOrder{
		public static final String NEED_INPUT="0";//要輸工單
		public static final String NOTNEED_INPUT="1 or 2";//不要輸工單
	}
	//用戶的Team
	public static final class Team{
		public static final String IPQC="IPQC";
		public static final String PD="PD";
		public static final String ME="ME";
		public static final String PROCESS="PROCESS";
	}
	//異常
	public static final class Exception{
		//圖片存儲在本地的臨時目錄(提交異常)
		public static final String PHOTO_DIR="MyPaperless3.0/image/exception/";
		//下載圖片目錄
		public static final String SAVE_DIR="MyPaperless3.0/image/ecdownload/";
		//下載圖片的保存路徑
		public static final String SAVE_PATH="/mnt/sdcard/MyPaperless3.0/image/ecdownload/";
		//上傳服務器的地址
		public static final String UPLOAD_URL=HeadPortrait.UPLOAD_URL;
		//下載地址
		public static final String DOWNLOAD_URL=MyConstant.IP+"MyPaperlessServer3.0/image/";
		public static final int MAX_PHOTONUM=3;//可上傳照片的最大張數
		public static final String SPLIT="---";//提交數據的分割符
		//異常信息顯示的類型
		public static final int MYDEAL=1;//处理
		public static final int MYCREATE=2;//创建
		//異常處理狀態
		public static final String DEALSTATE_REVIEW="0";//待處理
		public static final String DEALSTATE_OK="1";//處理完成
		public static final String DEALSTATE_REJECT="2";//駁回
		
	}
	//報表編號
	public static final class ReportNum{
		//含參數表的三個報表
		public static final String SMT_PRINTER="FM3NCD034003A";//SMT印刷機參數表
		public static final String SMT_REFLOW="FM3NCD034003B";//SMT回焊爐參數表
		public static final String PTH_WS="FM3NMD197001E";//NSDI 開 (換)線點檢(PTH)
		public static final String OBA_OPEN="FQ3NMD005001A";//OBA開線點檢表
		public static final String SMT_OPENCHANGE="FD3NMD219001A";//SMT開線&換線點檢表
		//public static final String SMT_FIRST="FQ3NMD054001B";//SMT首件檢查記錄表
		
		//需從參數表帶出的標準參數的報表編號（印刷機、回焊爐、波峰焊）
		public static final String REPORT_GETPARAMS=ReportNum.SMT_PRINTER+","+ReportNum.SMT_REFLOW
				+","+ReportNum.PTH_WS;
		//必須填寫deviation的報表編號（SMT首件檢查記錄表、量產ECN和Deviation產品首件檢查記錄表
		//、PTH首件檢查記錄表、SI首件檢查記錄表）
		public static final String REPORT_DEVIATION="FQ3NMD054001B,FQ3NMD054004A," +
				"FQ3NMD054002B,FQ3NMD054003B";
		//需要帶出面別的報表編號（印刷機、回焊爐、SMT開線&換線點檢表、SMT巡迴檢查記錄表）
		public static final String REPORT_GETSIDE=SMT_PRINTER+","+SMT_REFLOW
				+",FD3NMD219001A,FQ3NMD002002C,"+REPORT_DEVIATION;
		//7份首件（SMT 首件檢查記錄表、PTH首件檢查記錄表、SI首件檢查記錄表 、SMT量產ECN和Deviation產品首件檢查記錄表
		//NSDI SI FAI Check List、PTH量產ECN和Deviation產品首件檢查記錄表
		//Packing量產ECN和Deviation產品首件檢查記錄表)
		//必須填寫面別的報表編號
		public static final String REPORT_SIDE="FQ3NMD054001B,FQ3NMD054002B,FQ3NMD054003B,"+
				"FQ3NMD054004A,FQ3NMD054005A,FQ3NMD05400PA,FQ3NMD05400SA";
		//需要顯示開線、換線備註的報表  （ SMT開線&換線點檢表）
		public static final String REPORT_SHOWOPENCHANGE=SMT_OPENCHANGE;
		
	}
	//報表類型
	public static final class Report{
		public static final String CHECKPD_INPUT="CheckPdInput";//PD點檢帶輸入工單
		public static final String CHECK_NOINPUT="CheckNoInput";//點檢不輸入工單
		public static final String CHECK_IPQC="CheckIPQC";//IPQC點檢
		//參數默認值(所有參數無值情況下默認為此值)
		public static final String DEFAULT_VALUE="N/A";
		public static final String DEFAULT_CHECKID="0";//默認節次（IPQC需要區分節次）
		public static final String DEFAULT_INTVALUE="0";//int類型默認值
		//簽核人、proID、checkContent、checkResult、ImageData的分割符
		public static final String SPLIT="竜";
		//是否點檢（停線等異常情況為不點檢）
		public static final String CHECK="1";
		public static final String NOTCHECK="0";
		//是否是點檢內容，提交異常后將標記為不是點檢內容
		public static final String ISCHECKCONTENT="0";
		public static final String NOTCHECKCONTENT="1";
	}
	//點檢類型
	public static final class ReportCheck{
		public static final int NORMAL=1;//正常點檢
		public static final int INADVANCE=2;//提前維護
		public static final int SUPPLEMENT=3;//補點檢
	}
	
	//面別（三個參數表）
	public static final class Side{
		public static final String BOT="BOT";
		public static final String TOP="TOP";
	}
	
	//拍照標記
	public static final class TakePhoto{
		public static final String NONEED="0";//不需拍照
		public static final String NEED="1";//需拍照
		public static final int DEFAULT_ICON=R.drawable.ic_camera;//默認圖標
		//存放圖片的目錄
		public static final String PHOTO_DIR="MyPaperless3.0/image/check/";
	}
	//點檢標記
	public static final class CheckFlag{
		public static final String INPUT_MANUAL="0";//手動輸入
		public static final String INPUT_OK="1";//用戶點擊文本框方可觸發事件，填充“OK”
		public static final String INPUT_WO="2";//輸入工單帶出數據
		public static final String INPUT_KEYWORD="3";//輸入關鍵字帶出數據
		public static final String INPUT_TR_SN="4";//輸入TR_SN 
		public static final String INPUT_SN="5";//輸入SN
		public static final String  INPUT_OK_VALUE="OK";//用戶點擊文本框觸發事件，填充的默認值
	}
	
	public static final class FinalDB{
		public static final String DB_CHECKITEM="checkItem.db";//保存點檢子項的數據庫
	}
	//點檢項參數配置
	public static final class ParamConfig{
		//三種帶出參數的方法
		public static final String TYPE_FIXED="fixed";//固定值
		public static final String TYPE_WS="ws";//從WebService帶出
		public static final String TYPE_DB="db";//從數據庫帶出
	}
	//簽核查詢類型
	public static final class CheckType{
		public final static String RECORD="record";//歷史記錄查詢
		public final static String REVIEW="review";//待審核記錄查詢
		public final static String AUDITED="audited";//已審核記錄查詢
		public final static String REJECT="reject";//拒簽記錄查詢
	}
	//報表配置
	public static final class ReportConfig{
		public final static String CHECKED="1";//已配置點檢子項標記(或已配置過點檢項)
		public final static String NOCHECKED="0";//未配置點檢子項標記(或未配置過點檢項)
		public final static String SPLIT="---";//字符串分割符
	}
	//參數管理
	public static final class ParamManage{
		//修改類型
		public final static String UPDATETYPE_ADD="0";//添加參數
		public final static String UPDATETYPE_UPDATE="1";//修改參數
		public final static String UPDATETYPE_DELETE="2";//刪除參數
		//簽核狀態
		public final static String CHECKSTATE_REVIEW="0";//待簽核
		public final static String CHECKSTATE_PASS="1";//通過
		public final static String CHECKSTATE_FAILED="2";//駁回
		//簽核類型
		public final static int AUDITTYPE_MYSUBMIT=1;//我提交的參數
		public final static int AUDITTYPE_MYCHECK=2;//我要簽核的參數
	}
	

	
}
