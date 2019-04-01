package com.foxconn.paperless.login.presenter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.ResultMsg;
import com.foxconn.paperless.helper.LoginPresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.login.view.ForgetPwdView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 忘記密碼頁面邏輯處理
 *@ClassName ForgetPwdPresenterImpl
 *@Author wunian
 *@Date 2017/10/14 上午9:51:00
 */
public class ForgetPwdPresenterImpl implements ForgetPwdPresenter,OnModelListener {

	private ForgetPwdView forgetPwdView;
	private LoginModel loginModel;
	//private Context context;
	private Params p;
	private List<ServerConfig> serverList;
	private List<String> serverName;
	
	public ForgetPwdPresenterImpl(ForgetPwdView forgetPwdView,Context context){
		this.forgetPwdView=forgetPwdView;
		this.loginModel=new LoginModelImpl(this);
		//this.context=context;
		this.p=new Params(loginModel);
		this.serverName=new ArrayList<String>();
	}

	/**
	 * 獲取服務器數據，填充到Spinner
	 */
	@Override
	public void setFactoryAdapter(List<ServerConfig> serverList,int languageId) {
		this.serverList=serverList;
		serverName=LoginPresenterHelper.getServerName(serverName, serverList, languageId);
		if(serverName.size()>0) forgetPwdView.setServerAdapter(serverName);
	}
	/**
	 * 獲得用戶的郵箱地址
	 */
	@Override
	public void getUserEmail(String logonName,int pos) {
		if(TextUtil.isEmpty(logonName)){
			forgetPwdView.showToastFailedMsg(R.string.logonname_empty);
		}else{
			ServerConfig sc=LoginPresenterHelper.getUserServerInfo(serverList, pos);
			if(sc.getSite()!=null){
				p=ParamsUtil.getParam(p, sc, Action.GET_EMAIL, new String[]{logonName});
				loginModel.getUserEmail(p);
				forgetPwdView.showLoading();
			}else{
				forgetPwdView.showToastFailedMsg(R.string.connect_network_failed);
			}
		}
	}
	/**
	 * 發送密碼到郵箱
	 */
	@Override
	public void sendPasswordToEmail(String logonName,String Email,int pos) {
		if(TextUtil.isEmpty(logonName)){//賬號為空
			forgetPwdView.showToastFailedMsg(R.string.logonname_empty);
		}else if(TextUtil.isEmpty(Email)){//郵箱為空
			forgetPwdView.showToastFailedMsg(R.string.email_empty);
		}else{
			ServerConfig sc=LoginPresenterHelper.getUserServerInfo(serverList, pos);
			if(sc.getSite()!=null){
				p=ParamsUtil.getParam(p, sc, Action.FIND_PASSWORD, new String[]{logonName,Email});
				loginModel.sendPasswordToEmail(p);
				forgetPwdView.showLoading();
			}else{
				forgetPwdView.showToastFailedMsg(R.string.connect_network_failed);
			}
		}
	}

	@Override
	public void success(JsonResult result) {
		if(result.getAction().equals(Action.GET_EMAIL)){
			forgetPwdView.dismissLoading();
			forgetPwdView.inputEmail(result.getData().get(0));
		}
		if(result.getAction().equals(Action.FIND_PASSWORD)){
			if(result.getResultMsg().equals(ResultMsg.SEND_SUCCESS)){//發送成功
				forgetPwdView.showToastSuccessMsg(R.string.findpwd_success);
			}
			if(result.getResultMsg().equals(ResultMsg.SEND_ERROR)){//發送失敗
				forgetPwdView.showToastFailedMsg(R.string.sendemail_failed);
			}
		}
	}

	@Override
	public void failed(JsonResult result) {
		//forgetPwdView.dismissLoading();
		if(result.getAction().equals(Action.GET_EMAIL)){
			forgetPwdView.showToastFailedMsg(R.string.loginname_notexist);
		}
		if(result.getAction().equals(Action.FIND_PASSWORD)){
			forgetPwdView.showToastFailedMsg(R.string.findpwd_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		forgetPwdView.showToastExceptionMsg(result.getResultMsg());
	}
}
