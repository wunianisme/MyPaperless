package com.foxconn.paperless.http.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;

import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.constant.MyEnum.APK;
import com.foxconn.paperless.constant.MyEnum.InitIP;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.constant.MyEnum.ServerType;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.constant.MyEnum.Team;
import com.foxconn.paperless.constant.MyEnum.WS;
import com.foxconn.paperless.http.WebServiceUtil.Response;

/**
 * 模拟数据源进行测试(模拟从数据库中取数据)
 * 
 * @author Administrator
 * 
 */
public class DataSourceTest {

	private String wsdl;
	private String nameSpace;
	private String methodName;
	private Map<String, Object> params;
	private Response responseCallback;

	private String action;

	public static final int SUCCESS_FLAG = 1;
	public static final int ERROR_FLAG = -1;

	public DataSourceTest() {}

	/**
	 * 初始化參數
	 * 
	 * @param wsdl
	 * @param nameSpace
	 * @param methodName
	 * @param actionName
	 * @param params
	 * @param responseCallback
	 */
	public void init(String wsdl, String nameSpace, String methodName,
			String actionName,Map<String, Object> params,Response responseCallback){
		this.wsdl = wsdl;
		this.nameSpace = nameSpace;
		this.methodName = methodName;
		this.params = params;
		this.responseCallback = responseCallback;
		this.action = actionName;
	}

	/**
	 * 获取数据源
	 * 
	 */
	public void getDataSource() {

		JsonResult jsonResult = new JsonResult();
		Message msg = null;
		try {
			jsonResult = getJsonResult();
			msg = responseHandler.obtainMessage(0, SUCCESS_FLAG, 0, jsonResult);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);
			jsonResult.setResultMsg(e.getMessage());
			msg = responseHandler.obtainMessage(0, ERROR_FLAG, 0, jsonResult);
		} finally {
			responseHandler.sendMessage(msg);
		}
	}

	/**
	 * 根据不同的Action获取携带数据的JSONResult
	 * 
	 * @return
	 */
	private JsonResult getJsonResult() {
		List<String> data = new ArrayList<String>();
		JsonResult jsonResult = new JsonResult();
		jsonResult.setResultCode(ResultCode.TRUE);
		jsonResult.setAction(action);
		if (action.equals(Action.GET_APKINFO)) {
			data.add(APK.NAME);
			data.add("1");
			data.add("description");
			data.add("");
			data.add("");
			data.add("");
			data.add("");
			data.add("localhost:8080/apk/"+APK.NAME);
			jsonResult.setColumnNum(2);
			jsonResult.setData(data);
			
			return jsonResult;
		}
		if (action.equals(Action.GET_SERVER)) {
			ServerConfig server = new ServerConfig(Site.LH, "测试", "Test",
					InitIP.LH, WS.NAMESPACE, WS.METHODNAME, ServerType.ALL,
					"1", "2018/7/18");
			data=server.getServerConfig();
			jsonResult.setColumnNum(9);
			jsonResult.setData(data);
			return jsonResult;
		}
		
		if (action.equals(Action.CHECK_LOGIN)) {
			Euser euser=new Euser("admin", "admin", "系统管理员", "administrator", "管理员", "187781", "123@qq.com", "3", "admin", Team.PD, "SMT", "SAVBU", "MFGVIII", "NSDI", Site.LH, "F1331847", "2018/7/18", "CNSBG");
			data=euser.getEuser();
			jsonResult.setColumnNum(18);
			jsonResult.setData(data);
			return jsonResult;
		}
		
		if (action.equals(Action.GET_BU)) {
			data.add("NSDI");
			data.add("CND");
			data.add("资讯管理处");
			data.add("工务");
			jsonResult.setColumnNum(1);
			jsonResult.setData(data);
			return jsonResult;
		}
		
		jsonResult.setResultCode(ResultCode.NULL);
		return jsonResult;
	}

	final Handler responseHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.arg1 == SUCCESS_FLAG) {
				responseCallback.onSuccess((JsonResult) msg.obj);
			}
			if (msg.arg1 == ERROR_FLAG) {
				responseCallback.onError((JsonResult) msg.obj);
			}
		};
	};

	/**
	 * 响应回调接口
	 * 
	 * @ClassName Response
	 * @Author wunian
	 * @Date 2017/9/5 上午9:22:42
	 */
	/*public interface Response {
		public void onSuccess(JsonResult result);

		public void onError(JsonResult result);
	}*/

}
