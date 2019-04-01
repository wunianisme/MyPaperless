package service;

import com.foxconn.server.bean.JsonParam;
import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.bean.MyParam;
import com.foxconn.server.constant.MyAction.Action;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.constant.MySql;
import com.foxconn.server.dao.ServiceDao;
import com.foxconn.server.dao.ServiceDaoImpl;
import com.foxconn.server.model.account.AccountModel;
import com.foxconn.server.model.account.AccountModelImpl;
import com.foxconn.server.model.account.FeedbackModel;
import com.foxconn.server.model.account.FeedbackModelImpl;
import com.foxconn.server.model.check.CheckModel;
import com.foxconn.server.model.check.CheckModelImpl;
import com.foxconn.server.model.function.CheckSearchModel;
import com.foxconn.server.model.function.CheckSearchModelImpl;
import com.foxconn.server.model.login.LoginModel;
import com.foxconn.server.model.login.LoginModelImpl;
import com.foxconn.server.resource.Database;
import com.foxconn.server.util.GsonUtil;

@javax.jws.WebService(targetNamespace = "http://service/", serviceName = "MyPaperlessServiceService", portName = "MyPaperlessServicePort")
public class MyPaperlessServiceDelegate {

	service.MyPaperlessService myPaperlessService = new service.MyPaperlessService();

	public String getServerData(String json) {
		return myPaperlessService.getServerData(json); 
	}

	public String getServerDataWithImageData(String json, String imageDataStr) {
		return myPaperlessService
				.getServerDataWithImageData(json, imageDataStr);
	}

}