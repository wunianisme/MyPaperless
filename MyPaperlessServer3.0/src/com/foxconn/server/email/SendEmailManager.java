package com.foxconn.server.email;


public class SendEmailManager {
	
	private String url;
	public SendEmailManager(){
		url="supporter.team@mail.foxconn.com";
	}

	/**
	 * 發送密碼到用戶郵箱
	 * @param person
	 * @param strto
	 * @param pwd
	 * @return
	 */
	public String SubmitMail_pwd(String person, String strto, String pwd) {
		String returnmessage = "";
		String title = "From MyPaperless APP(密碼找回):" + "您好, " + person + ",您的"
				+ "密碼是：" + pwd;
		returnmessage = SendEmailUtil.SendiCarMail("", title, strto, url);
		return returnmessage;
	}
	/**
	 * 發送待簽核報表信息給用戶主管郵箱
	 * @param reportname
	 * @param person
	 * @param strto
	 * @return
	 */
	public String SubmitMailTo(String reportname, String person, String strto) {
		String returnmessage = "";
		String title = "From MyPaperless APP:" + "您好, " + person + "主管,您有"
				+ reportname + "報表需要簽核, 請及時處理, 謝謝!";
		
		returnmessage = SendEmailUtil.SendiCarMail("", title, strto, url);
		return returnmessage;
	}
	/**
	 * 兩小時簽核報警
	 * @param reportname
	 * @param person
	 * @param strto
	 * @return
	 */
	public String SubmitMailTo2H(String reportname, String person, String strto) {
		String returnmessage = "";
		String title = "From MyPaperless APP:" + "您好, " + person + "主管,您有"
				+ reportname + "報表已超過兩小時未簽核, 請及時簽核, 謝謝!";
		returnmessage = SendEmailUtil.SendiCarMail("", title, strto, url);
		return returnmessage;
	}
}
