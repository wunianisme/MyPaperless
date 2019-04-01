package com.foxconn.paperless.http;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.constant.MyEnum.ResultCode;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.util.GsonUtil;
/**
 * WebService工具类
 *@ClassName WebServiceUtil
 *@Author wunian
 *@Date 2017/9/1 下午2:15:46
 */
@SuppressLint("HandlerLeak")
public class WebServiceUtil {

	private String wsdl;
	private String nameSpace;
	private String methodName;
	private Map<String,Object> params;
	private Response responseCallback;
	
	public static final int SUCCESS_FLAG=1;
	public static final int ERROR_FLAG=-1;
	
	public WebServiceUtil(){}
	/**
	 * 初始化參數
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
		this.responseCallback=responseCallback;
	}
	
	/**
	 * 调用WebService
	 * @param isDotNet
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	protected Object CallWebService(boolean isDotNet) throws Exception{
		String SOAP_ACTION = nameSpace + methodName;
		// 创建SoapObject实例
		SoapObject rpc = new SoapObject(nameSpace, methodName);
		if(params!=null){
			for (Iterator it = params.entrySet().iterator(); it.hasNext();) {
				Map.Entry e = (Entry) it.next();
				rpc.addProperty(e.getKey().toString(), e.getValue());
				Log.i("ws param:", "" +e.getKey().toString()+":"+e.getValue());
			}
		}
		HttpTransportSE ht = new HttpTransportSE(wsdl);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = isDotNet;
		envelope.setOutputSoapObject(rpc);
		ht.call(SOAP_ACTION, envelope);
		ht.debug = true;
		Object response =null;
		if(envelope.getResponse()!=null) response= (Object) envelope.bodyIn;
		return response;
	}
	/**
	 * 根据不同厂区解析WebService返回数据
	 * @param site 厂区
	 */
	public void connectService(final String site){
		new Thread(new Runnable() {
			
			private SoapObject result=null;
			private JsonResult jsonResult;
			private List<String> data=null;
			
			@Override
			public void run() {
				jsonResult=new JsonResult();
				data=new ArrayList<String>();
				Message msg=null;
				try{
					if(site.equalsIgnoreCase(Site.LH)){//MyPaperless3.0
						jsonResult=parseDataForCommon();
					}
					if(site.equalsIgnoreCase(Site.TW)){//台灣部分數據
						jsonResult=parseDataForTW();
					}
					msg=responseHandler.obtainMessage(0, SUCCESS_FLAG, 0, jsonResult);
				}catch(Exception e){
					e.printStackTrace();
					jsonResult.setResultCode(ResultCode.EXCEPTION);
					jsonResult.setResultMsg(e.getMessage());
					msg=responseHandler.obtainMessage(0, ERROR_FLAG, 0, jsonResult);
				}finally{
					responseHandler.sendMessage(msg);
				}
			}
			//解析台湾厂区部分Action数据（连接台湾的webService）
			private JsonResult parseDataForTW() throws Exception {
				result=(SoapObject) CallWebService(true);
				SoapObject sninfoResult=(SoapObject) result.getProperty("getsninfoResult");//getsninfoResult
				//Log.i("sninfoResult", sninfoResult.toString());
				SoapObject diffgram=(SoapObject) sninfoResult.getProperty("diffgram");//diffgram
				//Log.i("diffgram<<<<",diffgram.toString());
				SoapObject dataset=(SoapObject) diffgram.getProperty("NewDataSet");//NewDataSet
				//Log.i("NewDataSet<<<<",dataset.toString());
				SoapObject sn_info=(SoapObject) dataset.getProperty("sn_info");//sn_info
				//Log.i("sn_info<<<<",sn_info.toString());
				if (sn_info != null && sn_info.getPropertyCount() > 0) {
					jsonResult.setResultCode(ResultCode.TRUE);
					for (int i = 0; i < sn_info.getPropertyCount(); i++) {
						if(sn_info.getProperty(i)!=null){
							data.add(sn_info.getProperty(i).toString());
						} 
					}
				} else {
					// 连接外厂区WebService时需要先添加返回成功/失败 add by wunian
					jsonResult.setResultCode(ResultCode.NULL);
				}
				return jsonResult;
			}
			//解析龙华厂区和其他厂区的大部分数据（连接MyPaperlessServer3.0）
			private JsonResult parseDataForCommon() throws Exception {
				result=(SoapObject) CallWebService(false);
				Log.i("JSONRESULT", result.toString());
				SoapPrimitive returnResult=(SoapPrimitive) result.getProperty("return");
				Log.i("returnResult", returnResult.toString());
				jsonResult=GsonUtil.jsonToBean(returnResult.toString(), JsonResult.class);
				return jsonResult;
			}
		}).start();
	}
	
	final Handler responseHandler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.arg1==SUCCESS_FLAG){
				responseCallback.onSuccess((JsonResult) msg.obj);
			}
			if(msg.arg1==ERROR_FLAG){
				responseCallback.onError((JsonResult) msg.obj);
			}
		};
	};
	
	/**
	 * 响应回调接口
	 *@ClassName Response
	 *@Author wunian
	 *@Date 2017/9/5 上午9:22:42
	 */
	public interface Response{
		public void onSuccess(JsonResult result);
		
		public void onError(JsonResult result);
	}
}
