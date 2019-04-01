package com.foxconn.paperless.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.foxconn.paperless.bean.JsonParam;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.bean.ServerConfig;
import com.foxconn.paperless.constant.MyConstant;
import com.foxconn.paperless.constant.MyEnum.Config;
import com.foxconn.paperless.constant.MyEnum.InitIP;
import com.foxconn.paperless.constant.MyEnum.ServerType;
import com.foxconn.paperless.constant.MyEnum.Site;
import com.foxconn.paperless.constant.MyEnum.WS;
import com.foxconn.paperless.util.GsonUtil;
/**
 * Params工具类
 *@ClassName ParamsUtil
 *@Author wunian
 *@Date 2017/9/9 下午5:34:40
 */
public class ParamsUtil {

	private static HashMap<String,Object> params;
	
	/**
	 *返回最终Params,设置除Response外的所有参数
	 *（（MyPaperlessServer）
	 * @param p
	 * @param sc
	 * @param action
	 * @param param
	 * @return
	 */
	public static Params getParam(Params p,ServerConfig sc,
			String action,String[] param){
		p.setWsdl(sc.getUrl()+WS.WSDLSUFFIX);
		p.setNameSpace(sc.getNameSpace());
		p.setMethodName(sc.getMethodName());
		p.setActionName(action);
		p.setDBSite(sc.getSite());
		p.setParams(getMapParam(getJsonParam(action,p.getDBSite(), param)));
		return p;
	}
	
	/**
	 * 返回最终Params参数，设置action+params
	 * （MyPaperlessServer）
	 * @param action
	 * @param site
	 * @param param
	 * @return
	 */
	public static Params getParam(Params p,String action,String[] param){
		p.setActionName(action);
		p.setParams(getMapParam(getJsonParam(action, p.getDBSite(), param)));
		return p;
	}
	/**
	 * 返回最终Params参数，设置action+params+base64圖片數據
	 * @param p
	 * @param action
	 * @param param
	 * @param imageDataStr
	 * @return
	 */
	public static Params getParam(Params p,String action,String[] param,String imageDataStr){
		p.setActionName(action);
		p.setParams(getMapParam(getJsonParam(action, p.getDBSite(), param),imageDataStr));
		return p;
	}
	/**
	 * 返回最终Params参数,设置action+site+params
	 * （MyPaperlessServer）
	 * @param action
	 * @param site
	 * @param param
	 * @return
	 */
	public static Params getParam(Params p,String action,String dbSite,String[] param){
		p.setActionName(action);
		p.setDBSite(dbSite);
		p.setParams(getMapParam(getJsonParam(action,dbSite, param)));
		return p;
	}
	/**
	 * 初始化部分可變常量（上一次的static生命週期可能未結束）
	 */
	public static void initMyConstant(){
		MyConstant.DBSITE=Config.InitSite;
		MyConstant.IP=getInitIP();
		MyConstant.WSDL=MyConstant.IP+WS.WSDLSUFFIX;//WSDL=IP+WSDL後綴
		MyConstant.NAMESPACE=WS.NAMESPACE;
		MyConstant.METHODNAME=WS.METHODNAME;
		MyConstant.SERVERTYPE = ServerType.OFFICAl;
	}
	/**
	 * 獲得初始化連接服務器
	 */
	public static String getInitIP(){
		if(Config.InitSite.equals(Site.VN)){//越南
			return InitIP.VN;
		}else if(Config.InitSite.equals(Site.NN)){//南寧
			return InitIP.NN;
		}else if(Config.InitSite.equals(Site.TW)){//台灣
			return InitIP.TW;
		}else if(Config.InitSite.equals(Site.CQ)){//重慶(CBT)
			return InitIP.CQ;
		}else if(Config.InitSite.equals(Site.CQ_MBD)){//重慶(MBD)
			return InitIP.CQ_MBD;
		}else if(Config.InitSite.equals(Site.LH_NWE)){//龍華NWE
			return InitIP.LH_NWE;
		}else if(Config.InitSite.equals(Site.LH_131)){//龍華（測試）
			return InitIP.LH_131;
		}else{//其他廠區統一都連龍華
			return InitIP.LH;
		}
	}
	
	/**
	 * 更改與服務器相關的常量值
	 * @param sc
	 */
	public static void setServerConstant(ServerConfig sc){
		//更改一些連接參數的值
		MyConstant.DBSITE=sc.getSite();
		MyConstant.IP=sc.getUrl();
		MyConstant.WSDL=sc.getUrl()+WS.WSDLSUFFIX;
		MyConstant.NAMESPACE=sc.getNameSpace();
		MyConstant.METHODNAME=sc.getMethodName();
	}
	/**
	 * 将webService传递参数转换为json字符串（MyPaperlessServer）
	 * @param action
	 * @param site
	 * @param param
	 * @return
	 */
	private static String getJsonParam(String action,String site,String...param){
		List<Object> pm=new ArrayList<Object>();
		for (int i = 0; i < param.length; i++) {
			pm.add(param[i]);
		}
		JsonParam jsonParam=new JsonParam(action, site, pm);
		String jsonStr=GsonUtil.beanToJson(jsonParam);
		return jsonStr;
	}
	/**
	 * 使用HashMap存储传递参数(MyPaperlessServer)
	 * @param jsonParam
	 * @return
	 */
	private static HashMap<String,Object> getMapParam(String jsonParam){
		if(params==null) params=new HashMap<String, Object>();
		else if(params.size()>0) params.clear();
		params.put("arg0", jsonParam);//action
		return params;
	}
	/**
	 * 使用HashMap存储传递 並且帶base64圖片數據参数(MyPaperlessServer)
	 * @param jsonParam
	 * @param imageDataStr
	 * @return
	 */
	private static HashMap<String,Object> getMapParam(String jsonParam,String imageDataStr){
		if(params==null) params=new HashMap<String, Object>();
		else if(params.size()>0) params.clear();
		params.put("arg0", jsonParam);//action
		params.put("arg1", imageDataStr);//base64圖片數據
		return params;
	}
	/**
	 * 使用HashMap存储台灣廠區webservice传递参数
	 * @param wo 工單或SN
	 * @return
	 */
	/*public static HashMap<String,Object> getTWMapParam(String wo){
		if(params==null) params=new HashMap<String, Object>();
		else if(params.size()>0) params.clear();
		params.put("strDB", "NETAPP");
		params.put("data",wo);
		return params;
	}*/
}
