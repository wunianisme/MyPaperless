package com.foxconn.server.util;


public class TextUtil {

	//匹配密码的正则表达式
	public static final String PASSWORD_REGEXP="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*" +
			"[\\!\\@\\#\\$\\%\\^\\&\\*\\.])[0-9a-zA-Z\\!\\@\\#\\$\\%\\^\\&\\*\\.]{8,16}$";
	/**
	 * 判断密码的合法性，true：合法
	 * @param password
	 * @return
	 */
	public static boolean passwordLegal(String password){
		return password.matches(PASSWORD_REGEXP);
	}
	/**
	 * 判断文本是否为空
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		if (text == null || text.trim().equals("")) {
			return true;
		}
		return false;
	}
}
