package com.foxconn.server.email;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.SmtpService.SmtpService.SmtpServiceLocator;
import org.tempuri.SmtpService.SmtpService.SmtpServiceSoap;

import com.foxconn.server.constant.MyEnum.ResultMsg;

public class SendEmailUtil {

	static SmtpServiceLocator locator = new SmtpServiceLocator();
	static SmtpServiceSoap stub;
	public static String SendiCarMail(String carno, String title, String url,
			String strTo) {
		String message = ResultMsg.SEND_ERROR;

		try {
			//stub = (SmtpServiceSoap) locator.getSmtpServiceSoap();
			boolean results;
			try {
				results = stub
						.WMSendMail(url, strTo, "", title,
								"Thanks for reading!!! <br>  IT:SFC APP Team  <br> TEL:187781");
				if (results == true)
					message = ResultMsg.SEND_SUCCESS;
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// boolean results = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
}
