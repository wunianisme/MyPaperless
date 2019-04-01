package com.foxconn.paperless.main.account.presenter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.ResultMsg;
import com.foxconn.paperless.constant.MyEnum.User;
import com.foxconn.paperless.helper.AccountPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.account.model.AccountModel;
import com.foxconn.paperless.main.account.model.AccountModelImpl;
import com.foxconn.paperless.main.account.view.EmployeeDetailOrAddView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 顯示員工詳細信息或添加員工邏輯處理
 *@ClassName EmployeeDetailOrAddPresenterImpl
 *@Author wunian
 *@Date 2017/10/24 下午1:32:34
 */
public class EmployeeDetailOrAddPresenterImpl implements
		EmployeeDetailOrAddPresenter,OnModelListener {
	private Context context;
	private EmployeeDetailOrAddView employeeDetailOrAddView;
	private AccountModel accountModel;
	private Euser user;
	private Params p;
	private Euser euser;//要顯示的用戶信息
	private String mode;//標記當前頁面是顯示頁面還是添加頁面
	public static final String MODE_SHOWINFO="showinfo";
	public static final String MODE_ADDEMPLOYEE="addemployee";
	//添加員工
	private boolean loadingFinish;//初始化加載數據完成標記
	private List<String> MFGList;//製造處列表
	private List<String> SBUList;//SBU列表
	private List<String> teamList;//Team列表
	private List<String> sectionList;//工站列表
	
	public EmployeeDetailOrAddPresenterImpl(EmployeeDetailOrAddView employeeDetailOrAddView,Context context){
		this.employeeDetailOrAddView=employeeDetailOrAddView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.accountModel=new AccountModelImpl(this);
		this.p=new Params(this.accountModel);
		this.loadingFinish=false;
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_MFG_ITEM)){
			MFGList=result.getData();
			employeeDetailOrAddView.setMFGAdapter(MFGList);
			p=ParamsUtil.getParam(p, Action.GET_SBU_ITEM, new String[]{user.getBu()});
			accountModel.getSBUItem(p);//獲得BU下的所有SBU、MFG，暫時存儲于Model層
		}
		if(result.getAction().equals(Action.GET_SBU_ITEM)){
			p=ParamsUtil.getParam(p, Action.GET_TEAM_ITEM, new String[]{user.getBu()});
			accountModel.getTeamItem(p);//獲得BU下的所有Team、SBU、MFG，暫時存儲于Model層
		}
		if(result.getAction().equals(Action.GET_TEAM_ITEM)){
			p=ParamsUtil.getParam(p, Action.GET_SECTION_ITEM, new String[]{user.getBu()});
			accountModel.getSectionItem(p);//獲得BU下的所有工站，SBU，咱叔存儲于Model層
		}
		if(result.getAction().equals(Action.GET_SECTION_ITEM)){
			loadingFinish=true;//加載完成
			employeeDetailOrAddView.setMFGSelection(AccountPresenterHelper.getSelectionIndex(
					MFGList, user.getMfg()));//選中用戶的製造處
		}
		if(result.getAction().equals(Action.SAVE_EMPLOYEEINFO)){
			if(result.getResultMsg().equals(ResultMsg.ADDEMPLOYEE_SUCCESS)){
				employeeDetailOrAddView.dismissLoading();
				//顯示添加成功對話框，提示初始密碼
				String message=context.getResources().getString(R.string.addemployee_success)+User.PASSWORD;
				employeeDetailOrAddView.showAddEmployeeSuccessDialog(
						R.string.system_tip,message,R.string.btn_ok);//顯示添加成功的對話框
			}
			if(result.getResultMsg().equals(ResultMsg.ADDEMPLOYEE_EXISIT)){//賬號已存在
				employeeDetailOrAddView.showToastFailedMsg(R.string.employee_exist);
			}
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.SAVE_EMPLOYEEINFO)){
			employeeDetailOrAddView.showToastFailedMsg(R.string.addemployee_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		employeeDetailOrAddView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 獲取前一個Activity傳遞的Intent數據
	 */
	@Override
	public void getIntent(Activity activity) {
		Bundle bundle=activity.getIntent().getExtras();
		mode=bundle.getString("mode");
		if(mode.equalsIgnoreCase(MODE_SHOWINFO)){//顯示員工信息
			euser=(Euser) bundle.getSerializable("employInfo");
			employeeDetailOrAddView.setEmployeeInfo(euser);
		}
		if(mode.equalsIgnoreCase(MODE_ADDEMPLOYEE)){//添加員工賬號
			employeeDetailOrAddView.showEmployeeAddView(user);//顯示和隱藏一部分控件
			employeeDetailOrAddView.setUserlevelAdapter(
					context.getResources().getStringArray(R.array.userlevel_array));//設置用戶等級的適配器
			employeeDetailOrAddView.setJobAdapter(
					context.getResources().getStringArray(R.array.job_array));//設置職務的適配器
			getMFGItem();
		}
	}
	/**
	 * 獲得BU下的所有製造處
	 */
	@Override
	public void getMFGItem() {
		p=ParamsUtil.getParam(p, Action.GET_MFG_ITEM, new String[]{user.getBu()});
		accountModel.getMFGItem(p);//獲得BU下的所有製造處
	}

	/**
	 * 通知SBU列表數據更新
	 */
	@Override
	public void notifySBUDataChanged(String MFG) {
		if(loadingFinish){
			SBUList=accountModel.SBUDataChanged(MFG);//重新添加數據
			employeeDetailOrAddView.setSBUAdapter(SBUList,
					AccountPresenterHelper.getSelectionIndex(SBUList, user.getSbu()));
		}
		
	}
	/**
	 * 通知Team列表數據更新
	 */
	@Override
	public void notifyTeamDataChanged(String SBU, String MFG) {
		if(loadingFinish){
			teamList=accountModel.teamDataChanged(SBU, MFG);
			employeeDetailOrAddView.setTeamAdapter(teamList,
					AccountPresenterHelper.getSelectionIndex(teamList, user.getTeam()));
		}
	}
	/**
	 * 通知工站列表數據更新
	 */
	@Override
	public void notifySectionDataChanged(String SBU) {
		if(loadingFinish){
			sectionList=accountModel.sectionDataChanged(SBU);
			employeeDetailOrAddView.setSectionAdapter(sectionList,
					AccountPresenterHelper.getSelectionIndex(
							sectionList, user.getSection()));
		}
	}

	@Override
	public void saveEmployeeInfo(String logonName, String ChineseName,
			String EnglishName, String ext, String email, String job,
			String master, String userlevel, String MFG, String SBU,
			String team, String section, String BU, String site) {
		if(TextUtil.isEmpty(logonName)){//工號為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.logonnames_empty);
		}else if(TextUtil.isEmpty(ChineseName)){//中文名為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.chinesename_empty);
		}else if(TextUtil.isEmpty(EnglishName)){//英文名為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.englishname_empty);
		}else if(TextUtil.isEmpty(ext)){//電話為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.ext_empty);
		}else if(TextUtil.isEmpty(email)){//郵箱為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.mail_empty);
		}else if(TextUtil.isEmpty(master)){//主管工號為空
			employeeDetailOrAddView.showToastFailedMsg(R.string.master_empty);
		}else if(logonName.length()<5){//工號不足5位
			employeeDetailOrAddView.showToastFailedMsg(R.string.logonname_lengthnotenough);
		}else if(!TextUtil.isChinese(ChineseName)){//中文名不合法
			employeeDetailOrAddView.showToastFailedMsg(R.string.chinesename_illegal);
		}else if(TextUtil.isChinese(EnglishName)){//英文名不合法
			employeeDetailOrAddView.showToastFailedMsg(R.string.englishname_illegal);
		}else if(ext.length()<5){//電話長度不足5位
			employeeDetailOrAddView.showToastFailedMsg(R.string.ext_lengthnotenough);
		}else if(!TextUtil.EmailLegal(email)){//郵箱地址不合法
			employeeDetailOrAddView.showToastFailedMsg(R.string.email_illegal);
		}else if(master.length()<5){//主管工號不足5位
			employeeDetailOrAddView.showToastFailedMsg(R.string.master_lengthnotenough);
		}else{
			//把中文文本進行繁體轉換
			ChineseName=TextUtil.simpleToTradition(ChineseName);
			job=TextUtil.simpleToTradition(job);
			String[] employInfo=new String[]{logonName,User.PASSWORD,ChineseName,EnglishName,job,ext,
					email,userlevel,master,team,section,SBU,MFG,BU,site,user.getLogonName(),User.BG};
			p=ParamsUtil.getParam(p, Action.SAVE_EMPLOYEEINFO, employInfo);
			accountModel.saveEmployeeInfo(p);
			employeeDetailOrAddView.showLoading();
		}
	}
}
