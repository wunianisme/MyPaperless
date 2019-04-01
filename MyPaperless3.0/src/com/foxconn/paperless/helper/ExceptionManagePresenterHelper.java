package com.foxconn.paperless.helper;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.bean.ExceptionFeedback;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.constant.MyEnum.Exception;

/**
 * 異常管理邏輯助手
 *@ClassName ExceptionManagePresenterHelper
 *@Author wunian
 *@Date 2018/1/19 上午11:34:12
 */
public class ExceptionManagePresenterHelper {
	/**
	 * 將獲得的異常信息封裝到ExceptionFeedback類中
	 * @param result
	 * @param type
	 * @return
	 */
	public static List<ExceptionFeedback> getExceptionFeedback(
			JsonResult result, int type) {
		List<ExceptionFeedback> exceptionList=new ArrayList<ExceptionFeedback>();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			ExceptionFeedback exception=new ExceptionFeedback();
			exception.setId(result.getData().get(i));
			exception.setReportName(result.getData().get(i+1));
			if(type==Exception.MYDEAL){//處理類型，添加創建人
				exception.setFromUser(result.getData().get(i+2));
			}
			if(type==Exception.MYCREATE){//創建類型，添加處理人
				exception.setToUser(result.getData().get(i+2));
			}
			exception.setContent(result.getData().get(i+3));
			exception.setCommitTime(result.getData().get(i+4));
			exception.setDealState(result.getData().get(i+5));
			exceptionList.add(exception);
		}
		return exceptionList;
	}
	/**
	 * 將獲得的詳細異常信息封裝到ExceptionFeedback類中
	 * @param result
	 * @param type
	 * @return
	 */
	public static ExceptionFeedback getExceptionFeedbackDetail(
			JsonResult result, int type) {
		ExceptionFeedback exception=new ExceptionFeedback();
		for (int i = 0; i < result.getData().size(); i+=result.getColumnNum()) {
			exception.setMFG(result.getData().get(i));
			exception.setFloor(result.getData().get(i+1));
			exception.setEquipment(result.getData().get(i+2));
			exception.setReportName(result.getData().get(i+3));
			exception.setRNO(result.getData().get(i+4));
			exception.setCheckId(result.getData().get(i+5));
			exception.setProId(result.getData().get(i+6));
			exception.setCheckName(result.getData().get(i+7));
			exception.setCheckProName(result.getData().get(i+8));
			if(type==Exception.MYDEAL){//處理類型，添加創建人
				exception.setFromUser(result.getData().get(i+9));
			}
			if(type==Exception.MYCREATE){//創建類型，添加處理人
				exception.setToUser(result.getData().get(i+9));
			}
			exception.setContent(result.getData().get(i+10));
			exception.setPictureUrl(result.getData().get(i+11));
			exception.setCommitTime(result.getData().get(i+12));
			exception.setDealState(result.getData().get(i+13));
			exception.setBackContent(result.getData().get(i+14));
			exception.setCompleteDate(result.getData().get(i+15));
		}
		return exception;
	}

}
