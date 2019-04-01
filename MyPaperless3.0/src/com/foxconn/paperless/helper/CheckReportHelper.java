package com.foxconn.paperless.helper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.QRReportInfo;
import com.foxconn.paperless.constant.MyEnum.CheckFlag;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.TakePhoto;
import com.foxconn.paperless.main.check.view.ReportCheckView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;

/**
 * 專門用於報表點檢檢驗的輔助類
 *@ClassName CheckReportHelper
 *@Author wunian
 *@Date 2018/1/26 上午11:26:47
 */
public class CheckReportHelper {
	/**
	 * 點檢內容校驗
	 * @param context
	 * @param reportCheckView
	 * @param checkItemList
	 * @param qrReportInfo
	 * @return
	 */
	public static boolean checkInputContent(Context context,
			ReportCheckView reportCheckView, List<CheckItem> checkItemList,
			QRReportInfo qrReportInfo) {
		//標準參數校驗
		Map<String,String> standardMap=qrReportInfo.getStandardParam();
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			String checkContent=checkItem.getCheckContent();
			if(TextUtil.isEmpty(checkContent)){//點檢輸入內容為空，提示不能為空
				CheckPresenterHelper.showTipDialog(context, reportCheckView,
						checkItem, R.string.submitfailed_contentempty);
				return false;
			}else{
				checkItemList.get(i).setSaveContent(checkContent);
				boolean checkOK=true;
				//印刷機、波峰焊、回焊爐參數比對
				if(ReportNum.REPORT_GETPARAMS.contains(
						qrReportInfo.getReportNum())){
					//只校驗從工單帶出的數據（未提交異常）
					if(checkItem.getCheckFlag().equals(CheckFlag.INPUT_WO)
							&&checkItem.getCheckResult().equals(Report.ISCHECKCONTENT)){
						checkOK=checkInputOK(
								context,reportCheckView,checkItem,standardMap);
					}
					if(!checkOK){//校驗不成功
						return false;
					}
					//三個參數表中輸入內容直接跟著標準參數之後，用分號隔開
					if(!TextUtil.isEmpty(standardMap.get(checkItem.getProId()))){
						String content=standardMap.get(checkItem.getProId())
								+";"+checkContent;
						//保存到數據庫中的值
						checkItemList.get(i).setSaveContent(content);
					}
				}else{
					//部分特殊報表參數校驗  add in 2018/01/26 
					checkOK=checkSpecialReport(context, reportCheckView,
							checkItem, qrReportInfo.getReportNum());
					if(!checkOK){//校驗不成功
						return false;
					}
				}
				//判斷是否配置拍照，配置了拍照的項必須拍照（未提交異常）
				if(!TextUtil.isEmpty(checkItem.getTakePhoto())&&
						checkItem.getTakePhoto().equals(TakePhoto.NEED)
						&&checkItem.getCheckResult().equals(Report.ISCHECKCONTENT)){
					if(checkItem.getImageData().equals(Report.DEFAULT_VALUE)){
						CheckPresenterHelper.showTipDialog(context,reportCheckView, 
								checkItem,R.string.submitfailed_nottakephoto);
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * 標準參數比對
	 * @param context
	 * @param checkView
	 * @param checkItem
	 * @param standardMap
	 * @return
	 */
	private static boolean checkInputOK(Context context,
			ReportCheckView checkView, CheckItem checkItem,
			Map<String, String> standardMap) {
		String proId=checkItem.getProId();//點檢子項的ID
		String checkContent=checkItem.getCheckContent().trim();//點檢輸入內容
		String standardParam=standardMap.get(proId);//標準參數
		
		if(!TextUtil.isEmpty(standardParam)){//標準參數不為空才要進行比對
			
			standardParam=standardParam.trim();
			/*ToastHelper.showInfo(context, "standard:"+standardParam+"," +
					"content:"+checkContent, 0);*/
			int x = standardParam.length();
			// 判斷字符串是否全是數字類型
			Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
			//輸入值不在標準參數範圍內
			int notInRangeMsgId=R.string.submitfailed_notinrange;
			//輸入值與標準參數不匹配
			int notMatchMsgId=R.string.submitfailed_notmatch;
			//輸入值不是數字或格式錯誤
			int notNumberMsgId=R.string.submitfailed_notnumber;
			try {
				if (standardParam.contains("+/-")
						&& !standardParam.startsWith("N/A")) {
					int m = standardParam.indexOf("+");
					int n = standardParam.indexOf("-");
					float a = Float.valueOf(standardParam.substring(0, m));
					float b;
					if (standardParam.substring(n + 1, x).contains(";")) {
						b = Float.valueOf(standardParam.substring(n + 1, x - 1));
					} else {
						b = Float.valueOf(standardParam.substring(n + 1, x));
					}
					float c = Float.valueOf(checkContent.toString());

					BigDecimal a2 = new BigDecimal(Float.valueOf(a).toString());
					BigDecimal b2 = new BigDecimal(Float.valueOf(b).toString());
					// a2-b2>c ||c>a2+b2
					if (a2.subtract(b2).floatValue() > c
							|| c > a2.add(b2).floatValue()) {
						//checkView.showTipDialog(R.string.check_tip, notInRangeMsg);
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notInRangeMsgId);
						return false;
					}

				} else if (standardParam.contains("~")) {
					int w = standardParam.indexOf("~");
					float a = Float.valueOf(standardParam.substring(0, w));
					float b;
					if (standardParam.substring(w + 1, x).contains(";")) {
						b = Float.valueOf(standardParam.substring(w + 1, x - 1));
					} else {
						b = Float.valueOf(standardParam.substring(w + 1, x));
					}
					float c = Float.valueOf(checkContent.toString());
					if (a > c || c > b) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notInRangeMsgId);
						return false;
					}
				} else if (standardParam.contains("or")) {
					boolean vsor = false;
					String[] str = standardParam.split("or");
					for (int k = 0; k < str.length; k++) {
						if (str[k].contains(";")) {
							str[k] = str[k].replace(";", "");
						}
						if (str[k].equalsIgnoreCase(checkContent.toString())) {
							vsor = true;
						}
					}
					if (!vsor) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notInRangeMsgId);
						return false;
					}
				} else if (pattern.matcher(standardParam.substring(0, x))
						.matches()) {
					
					float a = Float.valueOf(standardParam.substring(0, x));
					float c = Float.valueOf(checkContent.toString());
					//ToastHelper.showInfo(context, "stand:"+a+",content:"+c, 0);
					if (a != c) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notMatchMsgId);
						return false;
					}
				}
				// 加上“3000-3500”諸如這種形式的標準值比對判斷（迴焊爐參數中有這種形式）
				else if (!standardParam.contains("+/-")
						&& standardParam.contains("-")) {
					int index = standardParam.indexOf("-");
					int endIndex = standardParam.indexOf(";");
					float min = Float.valueOf(standardParam.substring(0, index));
					float max = 0;
					if (standardParam.endsWith(";")) {
						max = Float.valueOf(standardParam.substring(index + 1,
								endIndex));
					} else {
						max = Float.valueOf(standardParam.substring(index + 1));
					}
					float inputValue = Float.valueOf(checkContent.toString());
				
					if (inputValue < min || inputValue > max) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notInRangeMsgId);
						return false;
					}
				}
				// 加上"<1000"諸如這種形式的標準值比對判斷（迴焊爐參數中有這種形式）
				else if (standardParam.startsWith("<")) {
					int endIndex = standardParam.lastIndexOf(";");
					float max = 0;
					if (standardParam.endsWith(";")) {
						max = Float.valueOf(standardParam.substring(1, endIndex));
					} else {
						max = Float.valueOf(standardParam.substring(1));
					}
					float inputValue = Float.valueOf(checkContent.toString());
					if (inputValue >= max) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notInRangeMsgId);
						return false;
					}
				}
				// 诸如"kester#985m"这种形式，只判断输入值是否含有“985”（PTH波峰焊参数形式）
				else if (standardParam.contains("985")) {
					if (!checkContent.contains("985")) {
						CheckPresenterHelper.showTipDialog(context,checkView,checkItem,notMatchMsgId);
						return false;
					}
				}
			} catch (Exception e) {
				CheckPresenterHelper.showTipDialog(context,checkView,checkItem, notNumberMsgId);
				return false;
			}
		}
		return true;
	}
	/**
	 * 一些特殊報表的校驗 true:OK
	 * @return
	 */
	private static boolean checkSpecialReport(Context context,
			ReportCheckView checkView, CheckItem checkItem,String reportNum){
		if(reportNum.equals(ReportNum.OBA_OPEN)){//OBA開線點檢表參數校驗
			return checkOBAOpen(context, checkView, checkItem);
		}
		return true;
	}
	
	
	/**
	 * 校驗OBA開線點檢表參數 true:OK
	 * @param context
	 * @param checkView
	 * @param checkItem
	 * @param standardMap
	 * @return
	 */
	private static boolean checkOBAOpen(Context context,
			ReportCheckView checkView, CheckItem checkItem) {
		//OBA中開線點檢表需要校驗的點檢項ProID
		String[] checkProId=new String[]{"1","2","3","4","5"};
		//OBA中開線點檢表中對應ProID項必須含有對應的字符
		String[] standParam=new String[]{"SDT","FLP","FDJ","SG","NLQZ"};
		for (int i = 0; i < checkProId.length; i++) {
			if(checkItem.getProId().equals(checkProId[i])
					&&!checkItem.getCheckContent().trim().contains(standParam[i])){
				CheckPresenterHelper.showTipDialog(context, checkView,
						checkItem, R.string.scaninfo_wrong);//掃描信息錯誤！
				return false;
			}
		}
		return true;
	}
}
