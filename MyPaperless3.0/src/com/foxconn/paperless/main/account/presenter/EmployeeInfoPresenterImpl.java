package com.foxconn.paperless.main.account.presenter;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.Language;
import com.foxconn.paperless.constant.MyEnum.Userlevel;
import com.foxconn.paperless.helper.AccountPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.main.account.model.AccountModel;
import com.foxconn.paperless.main.account.model.AccountModelImpl;
import com.foxconn.paperless.main.account.view.EmployeeDetailOrAddActivity;
import com.foxconn.paperless.main.account.view.EmployeeInfoView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;
/**
 * 員工信息邏輯處理
 *@ClassName EmployeeInfoPresenterImpl
 *@Author wunian
 *@Date 2017/10/21 上午11:33:04
 */
public class EmployeeInfoPresenterImpl implements EmployeeInfoPresenter,OnModelListener {
	
	private Context context;
	private EmployeeInfoView employeeInfoView;
	private AccountModel accountModel;
	private LoginModel loginModel;
	private Euser user;
	private Params p;
	private List<Euser> employeeInfoList;//員工列表的所有數據集合（值只獲取一次基本不變）
	private List<Euser> employeeSearchInfoList;//查詢出來的數據集合
	private List<String> searchInputList;//輸入框的可查詢數據集合
	
	public EmployeeInfoPresenterImpl(EmployeeInfoView employeeInfoView,Context context){
		this.context=context;
		this.employeeInfoView=employeeInfoView;
		this.user=(Euser) context.getApplicationContext();
		this.accountModel=new AccountModelImpl(this);
		this.loginModel=new LoginModelImpl(this);
		this.p=new Params(accountModel);
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_EMPLOYEEINFO)){
			employeeInfoView.dismissLoading();
			employeeInfoList=AccountPresenterHelper.getEmployeeInfo(result);
			employeeSearchInfoList=employeeInfoList;//重新賦值
			employeeInfoView.setEmployeeInfoAdapter(employeeSearchInfoList);//設置員工信息列表適配，填充數據
			//整理查詢框的數據
			searchInputList=AccountPresenterHelper.getEmployeeLogonNameAndName(employeeInfoList);
			employeeInfoView.setSearchAdapter(searchInputList);
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.GET_EMPLOYEEINFO)){
			employeeInfoView.showToastFailedMsg(R.string.getemployeeinfo_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		employeeInfoView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲得同一製造處、SBU、Team下的員工信息列表
	 */
	@Override
	public void getEmployeeInfo() {
		p=ParamsUtil.getParam(p, Action.GET_EMPLOYEEINFO, 
				new String[]{user.getMfg(),user.getSbu(),user.getTeam()});
		accountModel.getEmployeeInfo(p);
		employeeInfoView.showLoading();
	}
	/**
	 * 根據用戶等級判斷是否顯示右邊菜單，只有1級以上才能添加用戶
	 */
	@Override
	public void showAddMenu() {
		if(user.getUserlevel().equals(Userlevel.level_0)){
			employeeInfoView.showAdd(View.GONE);
		}else{
			employeeInfoView.showAdd(View.VISIBLE);
		}
		
	}
	/**
	 * 顯示員工的姓名
	 */
	@Override
	public void setName(TextView tvName,Euser euser) {
		int languageId=loginModel.getLanguageId(context);
		//根據不同語言環境選擇不同的語言來顯示名字
		String name=languageId==Language.CHINESE?euser.getChineseName():euser.getEnglishName();
		tvName.setText(name);
	}
	/**
	 * 跳轉到詳細信息頁面
	 */
	@Override
	public void goEmployeeDetailPage(int pos) {
		String logonName=employeeSearchInfoList.get(pos).getLogonName();
		Euser euser=accountModel.getSingleEmployeeInfo(logonName);
		Bundle bundle =new Bundle();
		bundle.putString("mode", "showInfo");
		bundle.putSerializable("employInfo", euser);//保存當前點擊條目員工的數據
		employeeInfoView.gotoActivity(EmployeeDetailOrAddActivity.class,bundle);
	}
	/**
	 * 搜尋框的下拉列表被選中
	 */
	@Override
	public void onSearchItemSelected(String logonNameOrName) {
		//下拉框的文本都是繁體，可以不考慮簡繁轉化
		//ToastHelper.showInfo(context, logonNameOrName, 0);
		employeeSearchInfoList=AccountPresenterHelper.getSearchEmployeeInfo(employeeInfoList,logonNameOrName);
		employeeInfoView.setEmployeeInfoAdapter(employeeSearchInfoList);//重新生成適配器
	}
	/**
	 * 點擊搜尋按鈕進行查詢
	 */
	@Override
	public void search(String text) {
		text=TextUtil.simpleToTradition(text);//輸入的文本可能是簡體，需要考慮簡繁轉化，工號轉化為大寫
		employeeSearchInfoList=AccountPresenterHelper.getSearchEmployeeInfo(employeeInfoList,text);
		employeeInfoView.setEmployeeInfoAdapter(employeeSearchInfoList);//重新生成適配器
	}
	/**
	 * 跳轉到添加員工頁面
	 */
	@Override
	public void goEmployeeAddPage() {
		Bundle bundle =new Bundle();
		bundle.putString("mode", "addemployee");
		employeeInfoView.gotoActivity(EmployeeDetailOrAddActivity.class,bundle);
	}

	

}
