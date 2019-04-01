package com.foxconn.paperless.http;

import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.constant.MyEnum.WS;
import com.foxconn.paperless.http.test.DataSourceTest;

/**
 * WebService管理類
 *@ClassName WebServiceConnect
 *@Author wunian
 *@Date 2017/9/5 下午4:12:02
 */
public class WebServiceConnect {
	
	private WebServiceUtil webServiceUtil;
	
	//private DataSourceTest
	
	public WebServiceConnect(){
		this.webServiceUtil=new WebServiceUtil();
	}
	
	/**
	 * 連接MyPaperless3.0Server WebService
	 * @param p
	 * @param param
	 */
	public void getCommonServerData(Params p){
		/*webServiceUtil.init(p.getWsdl(), p.getNameSpace(), p.getMethodName(), 
				p.getActionName(),p.getParams(), p.getResponse());
		webServiceUtil.connectService(Site.LH);*/
		
		//模拟数据进行测试
		DataSourceTest test=new DataSourceTest();
		test.init(p.getWsdl(),  p.getNameSpace(),  p.getMethodName(), p.getActionName(), p.getParams(), p.getResponse());
		test.getDataSource();
	}
	/**
	 * 連接MyPaperless3.0Server WebService(帶base64圖片數據)
	 * @param p
	 */
	public void getCommonServerDataWithImageData(Params p){
		/*webServiceUtil.init(p.getWsdl(), p.getNameSpace(), WS.METHODNAME_IMAGEDATA, 
				p.getActionName(),p.getParams(), p.getResponse());
		webServiceUtil.connectService(Site.LH);*/
		
		//模拟数据进行测试
		DataSourceTest test=new DataSourceTest();
		test.init(p.getWsdl(),  p.getNameSpace(),  p.getMethodName(), p.getActionName(), p.getParams(),  p.getResponse());
		test.getDataSource();
	}
}
