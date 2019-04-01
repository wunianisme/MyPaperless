package com.foxconn.paperless.helper;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ParamsConfig;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.CheckFlag;
import com.foxconn.paperless.constant.MyEnum.ParamConfig;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.constant.MyEnum.TakePhoto;
import com.foxconn.paperless.main.check.view.ReportCheckView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.DensityUtil;
import com.foxconn.paperless.util.FileUtil;
import com.foxconn.paperless.util.TextUtil;
/**
 * 點檢模塊的presenter助手
 *@ClassName CheckPresenterHelper
 *@Author wunian
 *@Date 2017/11/3 上午9:20:29
 */
public class CheckPresenterHelper {
	/**
	 * 將BU報表信息封裝到BUReport類中
	 * @param result
	 * @return
	 */
	public static List<ReportInfo> getBUReport(JsonResult result){
		List<ReportInfo> buReportList=new ArrayList<ReportInfo>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ReportInfo buReport=new ReportInfo();
			buReport.setReportNum(result.getData().get(i));
			buReport.setReportName(result.getData().get(i+1));
			buReportList.add(buReport);
		}
		return buReportList;
	}
	/**
	 * 將數據庫中帶出的基本信息設置到QRReportInfo對象中
	 * @param result
	 * @param qrReportInfo
	 * @return
	 */
	public static QRReportInfo getReportBaseInfo(JsonResult result,QRReportInfo qrReportInfo) {
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			//data":["110-00546","B0","735","N/A","002310098931-1"]
			qrReportInfo.setSkuNo(result.getData().get(i));
			qrReportInfo.setSkuVersion(result.getData().get(i+1));
			qrReportInfo.setQty(result.getData().get(i+2));
			qrReportInfo.setDeviation(result.getData().get(i+3));
		}
		//add in 2017/02/28 台灣廠區需要再重新設置工單(可能輸入的是SN)
		if(MyConstant.DBSITE.equals(Site.TW)){
			qrReportInfo.setWorkorderNo(result.getData().get(4));
		}
		return qrReportInfo;
	}
	/**
	 * 獲得保存在本地數據庫中的點檢條目輸入信息
	 * @param saveItemList
	 * @param checkItemList
	 * @return
	 */
	public static List<CheckItem> getSaveItem(List<CheckItem> saveItemList,
			List<CheckItem> checkItemList,String RNO) {
		for (int i = 0; i < saveItemList.size(); i++) {
			CheckItem saveItem=saveItemList.get(i);
			for (int j = 0; j < checkItemList.size(); j++) {
				CheckItem checkItem=checkItemList.get(j);
				if(saveItem.getProId().equals(checkItem.getProId())){
					checkItemList.get(j).setCheckContent(saveItem.getCheckContent());
					
					checkItemList.get(j).setImageData(saveItem.getImageData());
					//因為本地數據庫不能存儲Bitmap數據上傳圖片直接去目錄中找
					File file=FileUtil.getLocalFile(TakePhoto.PHOTO_DIR+
							saveItem.getRNO()+"_"+checkItem.getProId()+".png");
					if(file.exists()){
						checkItemList.get(j).setTakePhotoBitmap(
								BitmapFactory.decodeFile(file.getAbsolutePath()));
						//重命名文件名（部分點檢編號一直在變化）
						file.renameTo(FileUtil.getLocalFile(TakePhoto.PHOTO_DIR+
								RNO+"_"+checkItem.getProId()+".png"));
					}
					break;
				}
			}
		}
		return checkItemList;
	}
	/**
	 * 將點檢項目相關信息封裝到CheckItem類中
	 * @param result
	 * @return
	 */
	public static List<CheckItem> getCheckItem(Context context,JsonResult result,String reportNum) {
		List<CheckItem> checkItemList=new ArrayList<CheckItem>();
		//遍歷傳回的數據，設置到CheckItem對象中
		for (int i = 0; i <result.getData().size(); i+=result.getColumnNum()) {
			CheckItem checkItem=new CheckItem();
			checkItem.setCheckName(result.getData().get(i));
			checkItem.setCheckProName(result.getData().get(i+1));
			checkItem.setProId(result.getData().get(i+2));
			checkItem.setCheckFlag(result.getData().get(i+3));
			checkItem.setTakePhoto(result.getData().get(i+4));
			//設置默認不是第一子項、無點檢子項
			checkItem.setFirstProName(false);
			checkItem.setCheckItemCount("");
			checkItem.setTakePhotoBitmap(BitmapFactory.decodeResource(context.getResources(), TakePhoto.DEFAULT_ICON));
			//設置報表編號，用於作為數據庫查詢條件
			checkItem.setReportNum(reportNum);
			checkItemList.add(checkItem);
		}
		//篩選出點檢項及其第一個點檢子項，并計算子項數目
		Map<String,String> checkNameMap=new HashMap<String,String>();
		Map<String,Integer> checkItemCount=new HashMap<String, Integer>();
		for (int i = 0; i < checkItemList.size(); i++) {
			if(checkNameMap.get(checkItemList.get(i).getCheckName())==null){
				checkNameMap.put(checkItemList.get(i).getCheckName(), 
						checkItemList.get(i).getProId());
				checkItemCount.put(checkItemList.get(i).getCheckName(), 1);//計算該點檢項的子項數目
			}else{
				//累加計算子項數目
				checkItemCount.put(checkItemList.get(i).getCheckName(), 
						checkItemCount.get(checkItemList.get(i).getCheckName())+1);
			}
		}
		//設置第一個點檢子項標記、該點檢項的子項數目
		for (int i = 0; i < checkItemList.size(); i++) {
			for (String checkName : checkNameMap.keySet()) {
				if(checkItemList.get(i).getProId().equals(checkNameMap.get(checkName))){
					checkItemList.get(i).setFirstProName(true);
					checkItemList.get(i).setCheckItemCount(checkItemCount.get(checkName)+"");
				}
			}
		}
		//某些相同點檢項的點檢子項proId并不相連，需要將其單獨作為一個點檢項的第一項（波峰焊）
		for (int i = 0; i < checkItemList.size(); i++) {
			//當前點檢子項的點檢項與前一項不同，但又不是作為第一個點檢子項的時候，將其作為第一子項
			if(i>0&&!checkItemList.get(i).getCheckName()
					.equals(checkItemList.get(i-1).getCheckName())
					&&!checkItemList.get(i).isFirstProName()){
				checkItemList.get(i).setFirstProName(true);
			}
		}
		return checkItemList;
	}
	/**
	 * 將班別和節次信息設置到qrReportInfo對象中
	 * @param result
	 * @param qrReportInfo
	 * @return
	 */
	public static QRReportInfo getShiftCheckNodeInfo(JsonResult result,
			QRReportInfo qrReportInfo) {
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			if(qrReportInfo.getReportType().equals(Report.CHECK_IPQC)){//IPQC考慮節次
				qrReportInfo.setCheckId(result.getData().get(i));
			}
			qrReportInfo.setShiftName(result.getData().get(i+1));
			qrReportInfo.setCheckNode(result.getData().get(i+2));
		}
		return qrReportInfo;
	}
	/**
	 * 根據不同報表整理標準參數到Map中
	 * @param reportNum
	 * @param result
	 * @return
	 */
	public static Map<String, String> getStandardParam(String reportNum,
			JsonResult result) {
		Map<String,String> standardMap=new HashMap<String, String>();
		int addCount=1;//ProId與data中集合數據索引的差值，印刷機和回焊爐:proId=index+1;
		if(reportNum.equals(ReportNum.PTH_WS)){//波峰焊：proId=index+15(不包含第一個單選類型的子項（proId:14）)
			addCount=15;
		}
		for (int i = 0; i < result.getData().size(); i++) {
			standardMap.put((i+addCount)+"", result.getData().get(i));//map的key對應點檢項的proId
		}
		return standardMap;
	}
	
	/**
	 * 顯示點檢信息(帶點檢項和提示信息的類型)
	 * @param context
	 * @param checkView
	 * @param checkItem
	 * @param msgId
	 */
	public static void showTipDialog(Context context,ReportCheckView checkView,
			CheckItem checkItem,int msgId){
		int titleId=R.string.check_tip;
		//點檢項/點檢子項信息
		String itemMessage="【"+checkItem.getCheckName()+
				"/"+checkItem.getCheckProName()+"】";
		//提示信息
		String message=itemMessage+context.getResources().getString(msgId);
		checkView.showTipDialog(titleId, message);
		
	}
	/**
	 * 獲得標題（括號內帶點檢項）
	 * @param context
	 * @param checkItem
	 * @param titleId
	 */
	public static String getTitle(Context context,CheckItem checkItem,int titleId){
		String title=context.getResources().getString(titleId)+
				"（"+checkItem.getCheckName()+"/"+checkItem.getCheckProName()+"）";
		return title;
	}
	/**
	 * 將獲取的簽核人員信息封裝到Euser中
	 * @param result
	 * @return
	 */
	public static List<Euser> getCheckBy(JsonResult result,String checkByTeam) {
		List<Euser> checkByList=new ArrayList<Euser>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			Euser checkBy=new Euser();
			checkBy.setLogonName(result.getData().get(i));
			checkBy.setChineseName(result.getData().get(i+1));
			checkBy.setTeam(checkByTeam);
			checkByList.add(checkBy);
		}
		return checkByList;
	}
	/**
	 * 將獲得的主管信息封裝到Euser中，添加入已選擇的簽核主管列表信息
	 * @param result
	 * @return
	 */
	public static List<Euser> addMasterToSelectedCheckBy(JsonResult result) {
		List<Euser> selectedCheckByList=new ArrayList<Euser>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			Euser master=new Euser();
			master.setLogonName(result.getData().get(i));
			master.setChineseName(result.getData().get(i+2));
			master.setTeam(result.getData().get(i+9));
			selectedCheckByList.add(master);
		}
		return selectedCheckByList;
	}
	/**
	 * 判斷用戶選擇的簽核人信息是否存在已選擇簽核人列表中
	 * @param checkBy
	 * @param selectedCheckByList
	 * @return true:存在
	 */
	public static boolean existInSelected(Euser checkBy,
			List<Euser> selectedCheckByList) {
		for (int i = 0; i < selectedCheckByList.size(); i++) {
			Euser selectedCheckBy=selectedCheckByList.get(i);
			if(checkBy.getLogonName().equalsIgnoreCase(
					selectedCheckBy.getLogonName())){//存在則直接返回
				return true;
			}
		}
		return false;
	}
	/**
	 * 查詢簽核人信息列表
	 * @param checkByList
	 * @param logonName
	 * @return
	 */
	public static List<Euser> queryCheckBy(List<Euser> allCheckByList,
			String logonName) {
		List<Euser> queryCheckByList=new ArrayList<Euser>();
		for (int i = 0; i < allCheckByList.size(); i++) {
			Euser checkBy=allCheckByList.get(i);
			//根據工號或姓名查詢
			if(checkBy.getLogonName().contains(logonName)
					||checkBy.getChineseName().contains(logonName)){
				queryCheckByList.add(checkBy);
			}
		}
		return queryCheckByList;
	}
	/**
	 * 獲得異常圖片位圖集合
	 * @param imgFilePathList
	 * @return
	 */
	public static List<Bitmap> getBitmap(Context context,List<String> imgFilePathList) {
		List<Bitmap> bitmapList=new ArrayList<Bitmap>();
		for (int i = 0; i < imgFilePathList.size(); i++) {
			Bitmap bitmap=BitmapFactory.decodeFile(imgFilePathList.get(i));
			//先壓縮一下再顯示(100dp*100dp)
			bitmap=FileUtil.compressBitmap(
					imgFilePathList.get(i),
					DensityUtil.dpTopx(context, 100f) , 
					DensityUtil.dpTopx(context, 100f));
			bitmapList.add(bitmap);
		}
		return bitmapList;
	}
	/**
	 * 將提交異常簽核人用分割符拼接成一個字符串
	 * @param selectedCheckByList
	 * @return
	 */
	public static String getToUsers(List<Euser> selectedCheckByList) {
		String toUsers="";
		for (int i = 0; i < selectedCheckByList.size(); i++) {
			toUsers+=selectedCheckByList.get(i).getLogonName()
					+com.foxconn.paperless.constant.MyEnum.Exception.SPLIT;
		}
		return toUsers;
	}
	/**
	 * 將上傳異常圖片的文件名用拼接符拼成一個字符串
	 * @param imgFilePathList
	 * @return
	 */
	public static String getPictureUrl(List<String> imgFilePathList) {
		String pictureUrl="";
		for (int i = 0; i < imgFilePathList.size(); i++) {
			int index=imgFilePathList.get(i).lastIndexOf("/");
			pictureUrl+=imgFilePathList.get(i).substring(index+1)
					+com.foxconn.paperless.constant.MyEnum.Exception.SPLIT;
		}
		return pictureUrl;
	}
	/**
	 * 獲得上傳異常圖片文件集合
	 * @param imgFilePathList
	 * @return
	 */
	public static List<File> getUploadFileList(List<String> imgFilePathList) {
		List<File> imgFileList=new ArrayList<File>();
		for (int i = 0; i < imgFilePathList.size(); i++) {
			imgFileList.add(new File(imgFilePathList.get(i)));
		}
		return imgFileList;
	}
	/**
	 * 將簽核人用分割符進行拼接
	 * @param selectedCheckByList
	 * @return
	 */
	public static String getCheckByStr(List<Euser> selectedCheckByList) {
		String checkByStr="";
		for (int i = 0; i < selectedCheckByList.size(); i++) {
			Euser checkBy=selectedCheckByList.get(i);
			checkByStr+=checkBy.getLogonName()+Report.SPLIT;
		}
		return checkByStr;
	}
	/**
	 * 將ProId用分割符進行拼接
	 * @param checkItemList
	 * @return
	 */
	public static String getProIdStr(List<CheckItem> checkItemList) {
		String proIdStr="";
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			proIdStr+=checkItem.getProId()+Report.SPLIT;
		}
		return proIdStr;
	}
	/**
	 * 將標準參數+點檢內容用分割符進行拼接
	 * @param checkItemList
	 * @return
	 */
	public static String getCheckContentStr(List<CheckItem> checkItemList) {
		String checkContentStr="";
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			checkContentStr+=checkItem.getSaveContent()+Report.SPLIT;
		}
		return checkContentStr;
	}
	/**
	 * 將點檢結果標記用分隔符進行拼接
	 * @param checkItemList
	 * @return
	 */
	public static String getCheckResultStr(List<CheckItem> checkItemList) {
		String checkResultStr="";
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			checkResultStr+=checkItem.getCheckResult()+Report.SPLIT;
		}
		return checkResultStr;
	}
	/**
	 * 將點檢上傳的照片信息 用分割符進行拼接
	 * @param checkItemList
	 * @return
	 */
	public static String getCheckImageDataStr(List<CheckItem> checkItemList) {
		String imageDataStr="";
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			imageDataStr+=checkItem.getImageData()+Report.SPLIT;
		}
		return imageDataStr;
	}
	/**
	 *將IPQC_Param_Config的點檢子項參數信息封裝到ParamsConfig類中
	 * @param result
	 * @return
	 */
	public static List<ParamsConfig> getParamConfig(
			JsonResult result) {
		List<ParamsConfig> paramConfigList=new ArrayList<ParamsConfig>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ParamsConfig paramsConfig=new ParamsConfig();
			paramsConfig.setReportNum(result.getData().get(i));
			paramsConfig.setProId(result.getData().get(i+1));
			paramsConfig.setType(result.getData().get(i+2));
			paramsConfig.setServerAddress(result.getData().get(i+3));
			paramsConfig.setParam(result.getData().get(i+4));
			paramsConfig.setParamNum(Integer.parseInt(result.getData().get(i+5)));
			paramsConfig.setDescribe(result.getData().get(i+6));
			paramConfigList.add(paramsConfig);
		}
		return paramConfigList;
	}
	/**
	 * 獲得固定參數類型值（對應IPQC_Param_Config表中的ServerAddress欄位）
	 * @param checkItemList
	 * @param result
	 * @return
	 */
	public static List<CheckItem> getFixedParams(List<CheckItem> checkItemList,
			List<ParamsConfig> paramConfigList) {
		for (int i = 0; i < paramConfigList.size(); i++) {
			ParamsConfig paramsConfig=paramConfigList.get(i);
			for (int j = 0; j < checkItemList.size(); j++) {
				if(paramsConfig.getType().equals(ParamConfig.TYPE_FIXED)//固定值類型參數
						&&paramsConfig.getProId().equals(checkItemList.get(j).getProId())){
					//將值設置進checkItem內
					checkItemList.get(j).setCheckContent(paramsConfig.getServerAddress());
					break;
				}
			}
		}
		return checkItemList;
	}
	/**
	 * 將要帶參數的點檢子項ID用分割符進行拼接（非fixed類型）
	 * @param result
	 * @return
	 */
	public static String getParamProId(List<ParamsConfig> paramConfigList,
			List<CheckItem> checkItemList) {
		String proIdStr="";
		for (int i = 0; i < paramConfigList.size(); i++) {
			ParamsConfig paramsConfig=paramConfigList.get(i);
			for (int j = 0; j < checkItemList.size(); j++) {
				CheckItem checkItem=checkItemList.get(j);
				//從已配置的點檢項中選擇需要帶的點檢項
				if(checkItem.getProId().equals(paramsConfig.getProId())){
					//固定值類型不需要再獲取參數（已獲取）
					if(!paramsConfig.getType().equalsIgnoreCase(ParamConfig.TYPE_FIXED)){
						proIdStr+=paramsConfig.getProId()+Report.SPLIT;
						break;
					}
				}
			}
		}
		return proIdStr;
	}
	/**
	 * 判斷開線或換線時，點檢項是否要帶出數據
	 * @param paramConfigList
	 *@param checkRemark
	 * @return
	 */
	public static boolean getOpenOrChangeLineParam(
			List<ParamsConfig> paramConfigList, String checkRemark) {
		for (int i = 0; i < paramConfigList.size(); i++) {
			ParamsConfig paramsConfig=paramConfigList.get(i);
			//判斷Describe欄位是否是開線、換線
			if(paramsConfig.getDescribe().equals(checkRemark)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 將從服務器獲得的除固定參數類型外的其他類型參數值設置到點檢內容中顯示出來
	 * @param result
	 * @param checkItemList
	 * @return
	 */
	public static List<CheckItem> getCheckItemParam(JsonResult result,
			List<CheckItem> checkItemList) {
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			for (int j = 0; j < checkItemList.size(); j++) {
				if(checkItemList.get(j).getProId().equals(result.getData().get(i))){
					checkItemList.get(j).setCheckContent(result.getData().get(i+1));
					break;
				}
			}
		}
		return checkItemList;
	}
}
