package com.foxconn.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.foxconn.server.constant.MyEnum.WS;


/**
 * 連接WebService工具類
 * @author foxconn
 *
 */
public class WebServiceUtil {
	
	private String wsdl;
	private String nameSpace;
	private String methodName;
	private Map<String,String> params;

	private String soupacation ;

	public WebServiceUtil(String wsdl, String nameSpace, String methodName,
			Map<String,String> params) {
		this.wsdl = wsdl;
		this.nameSpace = nameSpace;
		this.methodName = methodName;
		this.params = params;
	}

	@SuppressWarnings("rawtypes")
	public Object getWebServiceResponse(boolean dotNet) throws Exception {
	//	String wsdl="http://10.129.4.106:60/GetAllpart.asmx";
		SoapObject request=new SoapObject(nameSpace,methodName);
		if(params!=null){
			for (Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext();) {
				Map.Entry e = (Entry) it.next();
				request.addProperty(e.getKey().toString(), e.getValue());
				System.out.println("allpartWSParam:" +e.getKey().toString()+":"+e.getValue());
			}
		}
		HttpTransportSE ht = new HttpTransportSE(wsdl);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.dotNet = dotNet;
		envelope.setOutputSoapObject(request);
		soupacation = nameSpace + methodName;
		ht.call(soupacation, envelope);
		ht.debug = true;
		Object result = (SoapObject)envelope.getResponse();
		return result;
	}
	/**
	 * 根據不同的wsdl解析不同的webservice
	 * @param dotNet
	 * @param index
	 * @return
	 */
	public List<String> connectWebService(boolean dotNet,int index){
		List<String> list =new ArrayList<String>();
		try {
			SoapObject result = (SoapObject) getWebServiceResponse(dotNet);
			if(wsdl.equals("http://10.129.4.106:60/GetAllpart.asmx")){
				list=parseAllPartData(result,index);
			}
			if(wsdl.equals("http://10.167.4.131:8000/SFCWebService.asmx")){
				list=parseSmartGuardTopIssue(result,index);
			}
			if(wsdl.equals(WS.WSDL_TW)){
				list=parseTWSFCWebService(result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("allpartWSError:"+e.getMessage());
		}
		return list;
	}
	/**
	 * 解析台灣廠區提供的SFCWebService
	 * @param result
	 * @return
	 */
	private List<String> parseTWSFCWebService(SoapObject result) {
		List<String> list =new ArrayList<String>();
		//SoapObject sninfoResult=(SoapObject) result.getProperty("getsninfoResult");//getsninfoResult
		//Log.i("sninfoResult", sninfoResult.toString());
		SoapObject diffgram=(SoapObject) result.getProperty("diffgram");//diffgram
		//Log.i("diffgram<<<<",diffgram.toString());
		SoapObject dataset=(SoapObject) diffgram.getProperty("NewDataSet");//NewDataSet
		//Log.i("NewDataSet<<<<",dataset.toString());
		SoapObject sn_info=(SoapObject) dataset.getProperty("sn_info");//sn_info
		//Log.i("sn_info<<<<",sn_info.toString());
		if (sn_info != null && sn_info.getPropertyCount() > 0) {
			//jsonResult.setResultCode(ResultCode.TRUE);
			for (int i = 0; i < sn_info.getPropertyCount(); i++) {
				if(sn_info.getProperty(i)!=null){
					list.add(sn_info.getProperty(i).toString());
				} 
			}
		}
		return list;
	}

	/**
	 * 解析AllPart數據
	 * @param result
	 * @return
	 */
	private List<String> parseAllPartData(SoapObject result,int index){
		List<String> list =new ArrayList<String>();
		result = (SoapObject) result.getProperty(1);
		/*SoapObject result1=(SoapObject) result.getProperty(0);
		System.out.println("SoapObject result:"+result.toString());
		for (int i = 0; i < result1.getPropertyCount(); i++) {
			SoapObject object = (SoapObject) result1.getProperty(i);
			list.add(object.getProperty("KP_NO").toString());
			list.add(object.getProperty("STANDARD_QTY").toString());
		}*/
		if(result.getPropertyCount()>0){
			SoapObject result1=(SoapObject) result.getProperty(0);
			if(index<result1.getPropertyCount()){//獲得webservice中對應項的數據
				SoapObject object = (SoapObject) result1.getProperty(index);
				list.add(object.getProperty("KP_NO").toString());
				list.add(object.getProperty("STANDARD_QTY").toString());
			}else{
				list.add("NA");
			}
			for (int j = 0; j < list.size(); j++) {
				System.out.println("list"+list.get(j));
			}
		}else{
			list.add("NA");
		}
		return list;
	}
	/**
	 * 解析SmartGuardTopIssue數據
	 * @param result
	 * @param index
	 * @return
	 */
	private List<String> parseSmartGuardTopIssue(SoapObject result, int index) {
		List<String> list =new ArrayList<String>();
		result = (SoapObject) result.getProperty(1);
		if(result.getPropertyCount()>0){
			SoapObject result1=(SoapObject) result.getProperty(0);
			if(index<result1.getPropertyCount()){//獲得webservice中對應項的數據
				SoapObject object = (SoapObject) result1.getProperty(index);
				list.add("rootcause:"+object.getProperty("rootcause").toString());
				list.add("failurecode:"+object.getProperty("failurecode").toString());
				list.add("location:"+object.getProperty("location").toString());
			}else{
				list.add("NA");
			}
		}else{
			list.add("NA");
		}
		for (int j = 0; j < list.size(); j++) {
			System.out.println(list.get(j));
		}
		return list;
	}
	
	
}
