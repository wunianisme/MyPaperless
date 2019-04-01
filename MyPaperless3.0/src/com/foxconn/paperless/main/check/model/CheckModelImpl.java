package com.foxconn.paperless.main.check.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.http.AjaxParams;

import android.util.Log;

import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.CheckItem;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.helper.CheckReportHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.http.WebServiceConnect;
import com.foxconn.paperless.util.FinalHttpManager;
import com.foxconn.paperless.util.FinalHttpManager.FinalHttpCallback;
/**
 * 點檢業務數據處理
 *@ClassName CheckModelImpl
 *@Author wunian
 *@Date 2017/10/31 上午11:31:06
 */
public class CheckModelImpl implements CheckModel {
	
	private WebServiceConnect webServiceConnect;
	private OnModelListener onModelListener;
	//private JsonResult checkByResult;//將具有點檢權限的主管信息暫存于此
	private JsonResult masterResult;//將上級主管的信息暫存于此
	
	public CheckModelImpl(OnModelListener onModelListener){
		this.onModelListener=onModelListener;
		this.webServiceConnect=new WebServiceConnect();
	}

	@Override
	public void onSuccess(JsonResult result) {
		if(result.getResultCode()==ResultCode.TRUE){
			if(result.getAction().equals(Action.GET_MASTER)){
				masterResult=result;
			}
			//add in 2018/02/27
			/*if(result.getAction().equals(Action.GET_REPORTBASEINFO)){
				if(MyConstant.DBSITE.equals(Site.TW)){//如果是台灣廠區，重新整理數據的位置
					List<String> data=result.getData();
					if(data.size()>=4){
						List<String> newData=new ArrayList<String>();
						newData.add(data.get(0));//sku
						newData.add(data.get(1));//skuversion
						newData.add(data.get(3));//qty
						newData.add(Report.DEFAULT_VALUE);//deviation
						result.setData(newData);
					}
				}
			}*/
			onModelListener.success(result);
		}
		if(result.getResultCode()==ResultCode.NULL){
			onModelListener.failed(result);
		}
	}

	@Override
	public void onError(JsonResult result) {
		onModelListener.exception(result);
	}

	@Override
	public void getBUReportName(Params buReportParam) {
		webServiceConnect.getCommonServerData(buReportParam);
	}

	@Override
	public void getWO(Params params) {
		webServiceConnect.getCommonServerData(params);
	}
	
	@Override
	public void getReportBaseInfo(Params params) {
		//台灣廠區從webservice接口帶出基本數據
		/*if(params.getDBSite().equals(Site.TW)){
			params.setParams(ParamsUtil.getTWMapParam(workorderNo));
			webServiceConnect.getTWServerData(params);
		}else{*/
			webServiceConnect.getCommonServerData(params);
		/*}*/
	}

	@Override
	public void getCheckItem(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getShiftCheckNode(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getSide(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getParams(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getCheckBy(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getMaster(Params params) {
		if(masterResult==null||masterResult.getData().size()<1){
			webServiceConnect.getCommonServerData(params);
		}else{//上級主管只需要獲取一次
			onModelListener.success(masterResult);
		}
		
	}

	@Override
	public void submitException(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void uploadExceptionPhoto(FinalHttpCallback callback,
			String uploadUrl, List<File> imgFileList) {
		try {
			FinalHttpManager manager=new FinalHttpManager(callback);
			AjaxParams params=new AjaxParams();
			for (int i = 0; i < imgFileList.size(); i++) {//添加文件數據到params中
				params.put("File"+i, imgFileList.get(i));
			}
			manager.post(uploadUrl, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void submitCheckInfo(Params params) {
		webServiceConnect.getCommonServerDataWithImageData(params);
	}

	@Override
	public void deleteCheckItem(FinalDb finalDb, List<CheckItem> checkItemList) {
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			//刪除之前保存的點檢數據
			finalDb.deleteByWhere(CheckItem.class, "reportNum='"+checkItem.getReportNum()+
					"' and  proId='"+checkItem.getProId()+"'");
			
		}
	}

	@Override
	public void saveCheckItem(FinalDb finalDb, List<CheckItem> checkItemList,
			String workorderNo,String reportNum,String RNO) {
		
		for (int i = 0; i < checkItemList.size(); i++) {
			CheckItem checkItem=checkItemList.get(i);
			//保存工單和報表
			if(!workorderNo.equals(Report.DEFAULT_VALUE)){//有輸入工單的時候才設置工單內容
				checkItem.setWorkorderNo(workorderNo);
			}
			checkItem.setReportNum(reportNum);
			checkItem.setRNO(RNO);
			finalDb.save(checkItem);
		}
	}

	@Override
	public List<CheckItem> findCheckItem(FinalDb finalDb,
			List<CheckItem> checkItemList) {
		List<CheckItem> saveCheckItemList=new ArrayList<CheckItem>();
		//查找之前保存的數據
		saveCheckItemList=finalDb.findAllByWhere(CheckItem.class, "reportNum='"+
		checkItemList.get(0).getReportNum()+"'", "proId");
		for (int i = 0; i < saveCheckItemList.size(); i++) {
			Log.i("saveItem:",saveCheckItemList.get(i).toString());
		}
		return saveCheckItemList;
	}

	@Override
	public void getParamsConfigReport(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	@Override
	public void getCheckItemParam(Params params) {
		webServiceConnect.getCommonServerData(params);
	}

	
}
