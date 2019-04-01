package com.foxconn.paperless.login.presenter;

import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.login.model.LoginModel;
import com.foxconn.paperless.login.model.LoginModelImpl;
import com.foxconn.paperless.login.view.UpdatePwdView;
import com.foxconn.paperless.util.TextUtil;
/**
 * 修改密碼邏輯處理
 *@ClassName UpdatePwdPresenterImpl
 *@Author wunian
 *@Date 2017/9/21 上午11:28:21
 */
public class UpdatePwdPresenterImpl implements UpdatePwdPresenter,OnModelListener {

	private UpdatePwdView updatePwdView;
	private LoginModel loginModel;
	private Context context;
	private Euser user;
	private Params p;
	private String newPassword;
	
	public UpdatePwdPresenterImpl(UpdatePwdView updatePwdView,Context context){
		this.updatePwdView=updatePwdView;
		this.loginModel=new LoginModelImpl(this);
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.p=new Params(loginModel);
	}
	/**
	 * 提交密碼修改校驗
	 */
	@Override
	public void checkUpdate(String oldPassword, String newPassword,
			String checkNewPassword) {
		if(TextUtil.isEmpty(oldPassword)){//舊密碼為空
			updatePwdView.showToastFailedMsg(R.string.oldpwd_empty);
		}else if(TextUtil.isEmpty(newPassword)){//新密碼為空
			updatePwdView.showToastFailedMsg(R.string.newpwd_empty);
		}else if(TextUtil.isEmpty(checkNewPassword)){//驗證密碼為空
			updatePwdView.showToastFailedMsg(R.string.confirmpwd_empty);
		}else if(!oldPassword.equals(user.getPassword())){//舊密碼輸入錯誤
			updatePwdView.showToastFailedMsg(R.string.oldpwd_error);
		}else if(!newPassword.equals(checkNewPassword)){//驗證密碼與新密碼不一致
			updatePwdView.showToastFailedMsg(R.string.confirmpwd_identical);
		}else if(!TextUtil.passwordLegal(newPassword)){//新密碼不合法
			updatePwdView.showToastFailedMsg(R.string.newpwd_illegal);
		}else{
			p=ParamsUtil.getParam(p, Action.UPDATE_PASSWORD, new String[]{newPassword,user.getLogonName()});
			updatePwdView.showLoading();
			loginModel.saveUpdatePwd(p);//保存修改密碼
			this.newPassword=newPassword;
		}
	}
	
	@Override
	public void success(JsonResult result) {
		updatePwdView.dismissLoading();
		if(result.getAction().equals(Action.UPDATE_PASSWORD)){
			user.setPassword(newPassword);//設置用戶新密碼
			loginModel.saveAccount(context, user.getLogonName(), user.getPassword());//保存到Sharepreference
			loginModel.saveLastLoginAccount(context, user.getLogonName(), user.getPassword());//保存最後一次賬號
			updatePwdView.showSuccessDialog(R.string.system_tip, 
					R.string.updatepwd_success, R.string.btn_ok);
		}
	}

	@Override
	public void failed(JsonResult result) {
		//updatePwdView.dismissLoading();
		if(result.getAction().equals(Action.UPDATE_PASSWORD)){
			updatePwdView.showToastFailedMsg(R.string.updatepwd_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		updatePwdView.showToastExceptionMsg(result.getResultMsg());
	}
}