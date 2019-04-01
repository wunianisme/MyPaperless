package com.foxconn.paperless.constant;

import java.util.List;

import com.foxconn.paperless.bean.audit.CheckInfo;



/**
 * 一些可变值的常量类
 *@ClassName HttpConstant
 *@Author wunian
 *@Date 2017/9/1 上午11:34:42
 */
public final class MyConstant {

	//默认數據庫服務器名
	public static String DBSITE;
	//服務器IP
	public static String IP;
	//服务器
	public static String WSDL;
	//命名空间
	public static String NAMESPACE;
	//调用方法
	public static String METHODNAME;
	//服務器類型
	public static String SERVERTYPE;
	//服務器上APK版本號
	public static int SERVERVERSION;
	//服務器名稱
	public static String SERVERNAME;
	
	//存储所有BU的List
	public static List<String> ALLBULIST;
	//存儲待簽核列表信息的List
	public static List<CheckInfo> CHECKINFOLIST;
	
	//测试账号
	public static String TEST_ACCOUNT="test,admin";
	//測試賬號分隔符
	public static String TEST_ACCOUNT_SPLIT=",";
	
	
	
	
}
