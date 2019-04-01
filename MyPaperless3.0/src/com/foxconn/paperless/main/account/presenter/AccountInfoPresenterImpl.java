package com.foxconn.paperless.main.account.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.AccountPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.account.model.AccountModel;
import com.foxconn.paperless.main.account.model.AccountModelImpl;
import com.foxconn.paperless.main.account.view.AccountInfoView;
import com.foxconn.paperless.util.TextUtil;

public class AccountInfoPresenterImpl implements AccountInfoPresenter,
		OnModelListener {
	private Context context;
	private AccountInfoView accountInfoView;
	private AccountModel accountModel;
	private Euser user;
	private Params p;
	private boolean loadingFinish;//初始化加載數據完成標記
	private List<String> MFGList;//製造處列表
	private List<String> SBUList;//SBU列表
	private List<String> teamList;//Team列表
	private List<String> sectionList;//工站列表
	private String[] userInfo;
	
	public AccountInfoPresenterImpl(AccountInfoView accountInfoView,Context context){
		this.accountInfoView=accountInfoView;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.accountModel=new AccountModelImpl(this);
		this.p=new Params(accountModel);
		this.loadingFinish=false;
	}
	/**
	 * 獲取用戶賬號信息
	 */
	@Override
	public void getAccountInfo() {
		accountInfoView.setAccountBaseInfo(user);
		p=ParamsUtil.getParam(p, Action.GET_MFG_ITEM, new String[]{user.getBu()});
		accountModel.getMFGItem(p);//獲得BU下的所有製造處
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_MFG_ITEM)){
			MFGList=result.getData();
			accountInfoView.setMFGAdapter(MFGList);
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
			accountInfoView.setMFGSelection(AccountPresenterHelper.getSelectionIndex(
					MFGList, user.getMfg()));//選中用戶的製造處
		}
		if(result.getAction().equals(Action.UPDATE_ACCOUNTINFO)){
			accountInfoView.showToastSuccessMsg(R.string.saveaccountinfo_success);
			user.setEuser(userInfo);//修改Euser屬性值
			//重新刷新頁面數據
			accountInfoView.setAccountBaseInfo(user);
			accountInfoView.setMFGSelection(AccountPresenterHelper.getSelectionIndex(
					MFGList, user.getMfg()));//選中用戶的製造處
		}
	}

	@Override
	public void failed(JsonResult result) {
		if(result.getAction().equals(Action.UPDATE_ACCOUNTINFO)){
			accountInfoView.showToastFailedMsg(R.string.saveaccountinfo_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		accountInfoView.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 通知SBU列表數據更新
	 */
	@Override
	public void notifySBUDataChanged(String MFG) {
		if(loadingFinish){
			SBUList=accountModel.SBUDataChanged(MFG);//重新添加數據
			accountInfoView.setSBUAdapter(SBUList,
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
			accountInfoView.setTeamAdapter(teamList,
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
			accountInfoView.setSectionAdapter(sectionList,
					AccountPresenterHelper.getSelectionIndex(
							sectionList, user.getSection()));
		}
	}
	/**
	 * 保存賬號信息
	 */
	@Override
	public void saveAccountInfo(String ChineseName, String EnglishName,
			String ext, String email, String MFG, String SBU, String team,
			String section) {
		if(TextUtil.isEmpty(ChineseName)){//中文名為空
			accountInfoView.showToastFailedMsg(R.string.chinesename_empty);
		}else if(TextUtil.isEmpty(EnglishName)){//英文名為空
			accountInfoView.showToastFailedMsg(R.string.englishname_empty);
		}else if(TextUtil.isEmpty(ext)){//電話為空
			accountInfoView.showToastFailedMsg(R.string.ext_empty);
		}else if(TextUtil.isEmpty(email)){//郵箱為空
			accountInfoView.showToastFailedMsg(R.string.mail_empty);
		}else if(!TextUtil.isChinese(ChineseName)){//中文名不合法
			accountInfoView.showToastFailedMsg(R.string.chinesename_illegal);
		}else if(TextUtil.isChinese(EnglishName)){//英文名不合法
			accountInfoView.showToastFailedMsg(R.string.englishname_illegal);
		}else if(ext.length()<5){//電話長度不足5位
			accountInfoView.showToastFailedMsg(R.string.ext_lengthnotenough);
		}else if(!TextUtil.EmailLegal(email)){//郵箱地址不合法
			accountInfoView.showToastFailedMsg(R.string.email_illegal);
		}else{
			ChineseName=TextUtil.simpleToTradition(ChineseName);//轉換為繁體中文
			userInfo=new String[]{ChineseName,EnglishName,ext,email,
					team,section,SBU,MFG,user.getLogonName()};
			p=ParamsUtil.getParam(p, Action.UPDATE_ACCOUNTINFO, userInfo);//保存修改信息
			accountModel.saveAccountInfo(p);
			accountInfoView.showLoading();
		}
	}
}
