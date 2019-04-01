package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.R.string;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.CheckSearch;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.CheckSearchPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.CheckSearchModel;
import com.foxconn.paperless.main.function.model.CheckSearchModelImpl;
import com.foxconn.paperless.main.function.view.CheckSearchView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 點檢查詢邏輯處理
 *@ClassName CheckSearchPresenterImpl
 *@Author wyb update by wunian 
 *@Date 2017/12/28 下午3:23:56
 */
public class CheckSearchPresenterImpl implements CheckSearchPresenter,OnModelListener {

	private CheckSearchView checkSearchView;
	private CheckSearchModel checkSearchModel;
	private Context context;
	private Euser user;
	private Params params;
	private List<CheckSearch> checkStatusList;

	public CheckSearchPresenterImpl(CheckSearchView checkSearchView,Context context){
		this.checkSearchView =checkSearchView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.checkSearchModel=new CheckSearchModelImpl(this);
		this.params=new Params(checkSearchModel);
		this.checkStatusList=new ArrayList<CheckSearch>();
	}


	@Override
	public void success(JsonResult result) {
		checkSearchView.dismissLoading();
		if(result.getAction().equals(Action.GET_FLOORNAME)){//獲取樓層
			List<String> floorNameList=result.getData();
			checkSearchView.setFloorNameAdapter(floorNameList);
		}
		if(result.getAction().equals(Action.GET_LINENAME)){//獲取線別
			List<String> lineNameList=result.getData();
			checkSearchView.setlineNameAdapter(lineNameList);
		}
		if(result.getAction().equals(Action.GET_CHECKSTATUS)){//獲取點檢狀態信息
			checkStatusList=CheckSearchPresenterHelper.getCheckStatus(result);
			checkSearchView.setCheckStatusAdapter(checkStatusList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		checkSearchView.dismissLoading();
		if(result.getAction().equals(Action.GET_CHECKSTATUS)){//獲取點檢狀態信息失敗
			checkSearchView.showToastFailedMsg(R.string.getcheckstatus_failed);
			checkStatusList.clear();
			checkSearchView.setCheckStatusAdapter(checkStatusList);
		}
	}

	@Override
	public void exception(JsonResult result) {
		checkSearchView.showToastExceptionMsg(result.getResultMsg());

	}
	/**
	 * 獲得當前日期并設置日期提示框
	 */
	@Override
	public void getTime() {
		int[] date=TextUtil.getdate();//獲取日期
		checkSearchView.setDatePickDialog(date[0],date[1],date[2]);
	}
	/**
	 * 獲得製造處下面的樓層
	 */
	@Override
	public void getFloorName() {
		String BU=user.getBu();
		String MFG=user.getMfg();
		params=ParamsUtil.getParam(params, Action.GET_FLOORNAME, new String[]{BU,MFG});
		checkSearchModel.getFloorName(params);
	}
	/**
	 * 獲取用戶所在樓層所屬其製造處所有線別
	 */
	@Override
	public void getLineName(String floorName) {
		String MFG=user.getMfg();
		params=ParamsUtil.getParam(params, Action.GET_LINENAME, new String[]{floorName,MFG});
		checkSearchModel.getLineName(params);
	}
	/**
	 * 按用戶所選條件獲取點檢狀態信息
	 */
	@Override
	public void getCheckStatus(String flag, String floorName, String lineName,
			String shift, String date) {
		String BU=user.getBu();
		String MFG=user.getMfg();
		params=ParamsUtil.getParam(params, Action.GET_CHECKSTATUS, 
				new String[]{flag,BU,MFG,date,shift,lineName});
		checkSearchModel.getCheckStatus(params);
		checkSearchView.showLoading();
	}
	
}
