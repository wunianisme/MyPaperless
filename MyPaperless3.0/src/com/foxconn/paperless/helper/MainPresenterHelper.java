package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.QRReportInfo;
/**
 * MainPresenter的輔助工具類
 *@ClassName MainPresenterHelper
 *@Author wunian
 *@Date 2017/12/2 下午4:40:39
 */
public class MainPresenterHelper {
	/**
	 * 將二維碼綁定的報表信息封裝到QRReportInfo類中
	 * @param result
	 * @return
	 */
	public static List<QRReportInfo> getQRReportInfo(JsonResult result) {
		List<QRReportInfo> qrReportInfoList=new ArrayList<QRReportInfo>();
		List<String> data=result.getData();
		for (int i = 0; i < data.size(); i+=result.getColumnNum()) {
			QRReportInfo qrReportInfo=new QRReportInfo();
			qrReportInfo.setReportNum(data.get(i));
			qrReportInfo.setReportName(data.get(i+1));
			qrReportInfo.setIsInputOrder(data.get(i+2));
			qrReportInfo.setMFG(data.get(i+3));
			qrReportInfo.setSBU(data.get(i+4));
			qrReportInfo.setYeildFlag(data.get(i+5));
			qrReportInfo.setFloorName(data.get(i+6));
			qrReportInfo.setEquipment(data.get(i+7));
			qrReportInfo.setSection(data.get(i+8));
			qrReportInfoList.add(qrReportInfo);
		}
		return qrReportInfoList;
	}
	
	/**
	 * 整理得到一個二維碼配置的多個報表條目信息列表
	 * @param result
	 * @return
	 */
	public static List<String> getReportItemList(List<QRReportInfo> qrReportInfoList) {
		List<String> itemList=new ArrayList<String>();
		for (int i = 0; i < qrReportInfoList.size(); i++) {
			QRReportInfo qrReportInfo=qrReportInfoList.get(i);
			itemList.add(qrReportInfo.getReportName()+"("+qrReportInfo.getReportNum()+"-"+qrReportInfo.getMFG()+")");//報表名+（報表編號-製造處）
		}
		return itemList;
	}

	

}
