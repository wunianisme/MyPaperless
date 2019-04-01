package com.foxconn.server.model.login;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyConstant;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
import com.foxconn.server.email.SendEmailManager;
import com.foxconn.server.util.TextUtil;
/**
 * 登陸業務數據處理
 * @author NSD
 *
 */
public class LoginModelImpl implements LoginModel {

	private ServiceDao serviceDao;
	private JsonResult jsonResult;
	
	public LoginModelImpl(ServiceDao serviceDao) {
		this.serviceDao=serviceDao;
	}
	
	@Override
	public JsonResult getServer(MyParam p) {
		//jsonResult=serviceDao.queryByDBCP(p.getSql());
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql());
		return jsonResult;
	}

	@Override
	public JsonResult checkLogin(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		String password=p.getParams().get(1).toString();
		//jsonResult=serviceDao.queryByDBCP(p.getSql(), logonName,password);
		/*jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), logonName,password);
		if(jsonResult.getResultCode()==ResultCode.TRUE&&!MyConstant.TEST_ACCOUNT.contains(logonName)){
			if(!TextUtil.passwordLegal(password)){//判断密码合法性
				jsonResult.setResultCode(ResultCode.PASSWORDNOTLEGAL);
			}
		}*/
		jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_1),
				logonName,password);
		if(jsonResult.getResultCode()!=ResultCode.TRUE){
			return jsonResult;
		}
		//錯誤登陸次數
		int failedNum=Integer.parseInt(jsonResult.getData().get(0).trim());
		if(failedNum==-1){//賬號不存在
			jsonResult.setResultCode(ResultCode.LOGONNAME_NOTEXIST); 
		}else if(failedNum==0){//密碼輸入正確，未被鎖定
			jsonResult=serviceDao.query(p.getDatabase(), MySql.getSQL(p.getAction(), MySql.STEP_2),
					logonName,password);
			if(jsonResult.getResultCode()==ResultCode.TRUE&&!MyConstant.TEST_ACCOUNT.contains(logonName)){
				if(!TextUtil.passwordLegal(password)){//判断密码合法性
					jsonResult.setResultCode(ResultCode.PASSWORDNOTLEGAL); 
				}
			}
		}else if(failedNum<5){//密碼錯誤，未被鎖定
			jsonResult.setResultCode(ResultCode.PWDERROR_NOLOCK);
			jsonResult.setResultMsg((4-failedNum)+"");
		}else if(failedNum==5){//密碼輸錯剛好輸入五次，被鎖定
			jsonResult.setResultCode(ResultCode.PWDERROR_ISLOCK);
		}else{//已被鎖定，無法登陸
			jsonResult.setResultCode(ResultCode.ERROR_LOCEKD);
		}
		return jsonResult;
	}

	@Override
	public JsonResult updatePwd(MyParam p) {
		String password=p.getParams().get(0).toString();
		String logonName=p.getParams().get(1).toString();
		//jsonResult=serviceDao.updateByDBCP(p.getSql(), password,logonName);
		jsonResult=serviceDao.update(p.getDatabase(), p.getSql(),  password,logonName);
		return jsonResult;
	}

	@Override
	public JsonResult getEmail(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		//jsonResult=serviceDao.queryByDBCP(p.getSql(), logonName);
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), logonName);
		return jsonResult;
	}

	@Override
	public JsonResult findPassword(MyParam p) {
		String logonName=p.getParams().get(0).toString();
		String Email=p.getParams().get(1).toString();
		//jsonResult=serviceDao.queryByDBCP(p.getSql(), logonName);
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(), logonName);
		if(jsonResult.getResultCode()==ResultCode.TRUE){
			String ChineseName=jsonResult.getData().get(0);
			String pwd=jsonResult.getData().get(1);
			SendEmailManager sendEmailManager=new SendEmailManager();
			String result=sendEmailManager.SubmitMail_pwd(ChineseName, Email, pwd);//發送郵件
			jsonResult.setResultMsg(result);
		}
		return jsonResult;
	}

	@Override
	public JsonResult getAppInfo(MyParam p) {
		String appName=p.getParams().get(0).toString();
		//jsonResult=serviceDao.queryByDBCP(p.getSql(), appName);
		jsonResult=serviceDao.query(p.getDatabase(), p.getSql(),appName);
		return jsonResult;
	}

	@Override
	public JsonResult saveDownloadInfo(MyParam p) {
		String deviceId=p.getParams().get(0).toString();
		String phoneBrand=p.getParams().get(1).toString();
		String phoneModel=p.getParams().get(2).toString();
		String verCode=p.getParams().get(3).toString();
		String metric=p.getParams().get(4).toString();
		String simoperatorName=p.getParams().get(5).toString();
		//jsonResult=serviceDao.updateByDBCP(p.getSql(), deviceId,phoneBrand,phoneModel,verCode,metric,simoperatorName);
		jsonResult=serviceDao.update(p.getDatabase(),p.getSql(), deviceId,phoneBrand,
				phoneModel,verCode,metric,simoperatorName);
		return jsonResult;
	}
}
