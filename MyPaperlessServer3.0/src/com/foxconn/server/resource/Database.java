package com.foxconn.server.resource;

import java.sql.SQLException;

import com.foxconn.server.constant.MyEnum.DBFlag;
import com.foxconn.server.constant.MyEnum.Site;

/**
 * 数据库连接信息表
 * 
 * @author NSD
 * 
 */
public class Database {

	static String DBCOM = "3000", DBNAME = "eerp", SQLUSER = "erpuser",
			PASSWORD = "never!again";

	static String ORCLDBCOM = "1903", ORCLUSER = "IPCBUUSER",
			ORCLPASS = "IPCBUTEST";
	/**
	 * 区分Paperless和ICAR两部分逻辑 Server 连接主DB
	 * @param db_flag
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String[] getDatabase(String dbSite) {
		String DBCOM = "3000", DBNAME = "gstp";
		String DBUSER = "gstpuser", PASSWORD = "soquickgstp";
		
		String DBCOM_TW = "80", DBNAME_TW = "GSTP";
		String DBUSER_TW = "apadmin", PASSWORD_TW = "Foxconn123";// 台灣廠區
		
		String DBCOM_SH = "1433", DBNAME_SH = "PaperLess";
		String DBUSER_SH = "sa", PASSWORD_SH = "ambit123!";// 上海廠區
		
		String DBCOM_CQ="1433",DBNAME_CQ="paperless";
		String DBUSER_CQ="sa",PASSWORD_CQ="foxconn168!";//重慶廠區(CBT)
		
		String DBCOM_CQ_MBD="1433",DBNAME_CQ_MBD="gstp";
		String DBUSER_CQ_MBD="sa",PASSWORD_CQ_MBD="foxconn168~!@";//重慶廠區(MBD)
		
		String DBCOM_VN="3000",DBNAME_VN="gstp";
		String DBUSER_VN="sa",PASSWORD_VN="foxconn168!";//越南廠區
		
		String DBCOM_NN="3000",DBNAME_NN="gstp";
		String DBUSER_NN="sa",PASSWORD_NN="foxconn168!!";//南寧廠區
		
		String DBCOM_LH_NWE="1433",DBNAME_LH_NWE="paperless";
		String DBUSER_LH_NWE="paperless",PASSWORD_LH_NWE="test123";//龍華NWE廠區
		
		
		if (dbSite.toUpperCase().equals(Site.LH)){
			return getSqlServerConn("10.167.4.132", DBCOM, DBNAME, DBUSER,
					PASSWORD);
		}
		if(dbSite.toUpperCase().equals(Site.LH_121)){
			return getSqlServerConn("10.134.44.121", DBCOM, DBNAME, DBUSER,
					PASSWORD);
		}
		if (dbSite.toUpperCase().equals(Site.LH_131)){
			return getSqlServerConn("10.167.4.131", DBCOM, DBNAME, DBUSER,
					PASSWORD);// 測試用
		}
		if (dbSite.toUpperCase().equals(Site.TW)){
			return getSqlServerConn("10.59.65.95", DBCOM_TW, DBNAME_TW,
					DBUSER_TW, PASSWORD_TW);// 台灣廠區
		}
		if (dbSite.toUpperCase().equals(Site.SH)){
			return getSqlServerConn("10.64.32.198", DBCOM_SH, DBNAME_SH,
					DBUSER_SH, PASSWORD_SH);// 上海廠區
		}
		if(dbSite.toUpperCase().equals(Site.CQ)){
			return getSqlServerConn("10.129.4.77",DBCOM_CQ, DBNAME_CQ, 
					DBUSER_CQ,PASSWORD_CQ);//重慶廠區（CBT）
		}
		if(dbSite.toUpperCase().equals(Site.CQ_MBD)){
			return getSqlServerConn("10.250.185.61",DBCOM_CQ_MBD, DBNAME_CQ_MBD, 
					DBUSER_CQ_MBD,PASSWORD_CQ_MBD);//重慶廠區(MBD)
		}
		if(dbSite.toUpperCase().equals(Site.VN)){//越南廠區
			return getSqlServerConn("10.224.81.103",DBCOM_VN, DBNAME_VN, 
					DBUSER_VN,PASSWORD_VN);
		}
		if(dbSite.toUpperCase().equals(Site.NN)){//南寧廠區
			return getSqlServerConn("10.120.101.188",DBCOM_NN, DBNAME_NN, 
					DBUSER_NN,PASSWORD_NN);
		}
		if(dbSite.toUpperCase().equals(Site.LH_NWE)){//龍華NWE廠區
			return getSqlServerConn("10.132.130.113",DBCOM_LH_NWE, DBNAME_LH_NWE, 
					DBUSER_LH_NWE,PASSWORD_LH_NWE);
		}
		return null;
	}
	/**
	 * 根据厂区获取数据库配置文件名称
	 * @param site
	 * @return
	 */
	public static String getPropertyFileName(String dbSite){
		return "database/"+dbSite+".properties";
	}
	/**
	 * 獲得各製造處的SFC數據庫
	 * @param strings
	 * @return
	 */
	public static String[] getConnByMFG(String... strings) {
		String mfg_flag = strings[0];
		if (mfg_flag.trim().toUpperCase().equals("MFGI"))
			return getSqlServerConn("192.168.88.50", DBCOM, DBNAME, SQLUSER,
					PASSWORD);
		else if (mfg_flag.trim().toUpperCase().equals("MFGII"))
			return getSqlServerConn("192.168.88.53", DBCOM, DBNAME, SQLUSER,
					PASSWORD);
		else if (mfg_flag.trim().toUpperCase().equals("MFGIII"))
			return getOracleConn("192.168.12.2", ORCLDBCOM, "IPCBUODB",
					ORCLUSER, ORCLPASS);
		else if (mfg_flag.trim().toUpperCase().equals("MFGV"))
			return getSqlServerConn("192.168.15.2", DBCOM, DBNAME, SQLUSER,
					PASSWORD);
		else if (mfg_flag.trim().toUpperCase().equals("MFGVI"))
			return getSqlServerConn("10.143.192.56", DBCOM, DBNAME, SQLUSER,
					PASSWORD);
		else if (mfg_flag.trim().toUpperCase().equals("MFGVII"))
			return getOracleConn("10.142.149.5", ORCLDBCOM, "SEBUODB",
					ORCLUSER, ORCLPASS);
		else if (mfg_flag.trim().toUpperCase().equals("MFGVIII"))// "ETTC"
			return getSqlServerConn("192.168.88.57", DBCOM, DBNAME, SQLUSER,
					PASSWORD);
		else if (mfg_flag.trim().toUpperCase().equals("SPC")) 
			return getOracleConn("10.129.4.89", "1903", "nsd06", "web2",
					"NSDSPCWEB2228");
		else if(mfg_flag.trim().toUpperCase().equals(DBFlag.CND))//HWT
			return getOracleConn("10.143.208.2", "1527", "HWODB",
					"HWUSER", "HWTEST");
		//CSD对接SFC 帶deviation
		else if (mfg_flag.trim().toUpperCase().equals(DBFlag.CSD))
			return getOracleConn("10.143.192.2", "1527", "sfcodb",
						"hogan", "sfsselect");
		
		 else
			return null;
	}

	/**
	 * 返回製造五處SFC和ALLPART特殊數據庫
	 * 
	 * @param db_flag
	 * @param type_flag
	 * @return Connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String[] getMFGVConn(String db_flag){
		if (db_flag.trim().toUpperCase().equals(DBFlag.EERP))
			return getSqlServerConn("192.168.15.2", "3000", "eerp",
					SQLUSER, PASSWORD);
		else if (db_flag.trim().toUpperCase().equals(DBFlag.CDBU))
			return getSqlServerConn("10.143.200.62", "1433", "BPD_ICSBU",
					SQLUSER, PASSWORD);
		else if (db_flag.trim().toUpperCase().equals(DBFlag.BPD))
			return getOracleConn("192.168.15.3", "1903", "NSDBPD", "AP3",
					"NSD0170AP3");
		else if (db_flag.trim().toUpperCase().equals(DBFlag.ALLPART))
			return getOracleConn("10.132.48.70", "1903", "NSD01", "ap3",
					"nsd0170ap3");
		else if(db_flag.trim().toUpperCase().equals(DBFlag.CND))//HWT
			return getOracleConn("10.143.208.2", "1527", "HWODB",
					"HWUSER", "HWTEST");
		//CSD对接AllParts 帶面別
		else if (db_flag.trim().toUpperCase().equals(DBFlag.CSD))
			return getOracleConn("10.143.192.9", "1901", "NSD04",
			"BEACON", "BEACON2017!");
		return null;
	}
	/**
	 * 连接SqlServer数据库(端口包含在DBAddress中)
	 * @param DBAddress
	 * @param DBNAME
	 * @param USERNAME
	 * @param PASSWORD
	 * @return
	 */
	public static String[] getSqlServerConn(String DBAddress,String DBNAME,String USERNAME,
			String PASSWORD){
		String forName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String URL="jdbc:sqlserver://" + DBAddress+ ";databaseName=" + DBNAME;
		return new String[] { forName, URL, USERNAME, PASSWORD };
	}
	/**
	 * 连接SqlServer数据库
	 * @param DBLINK
	 * @param DBCOM
	 * @param DBNAME
	 * @param USERNAME
	 * @param PASSWORD
	 * @return
	 */
	private static String[] getSqlServerConn(String DBLINK, String DBCOM,
			String DBNAME, String USERNAME, String PASSWORD) {
		String forName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String URL = "jdbc:sqlserver://" + DBLINK + ":" + DBCOM
				+ ";databaseName=" + DBNAME;
		// 80端口可以省略
		if (DBCOM.equals("80")) {
			URL = "jdbc:sqlserver://" + DBLINK + ";databaseName=" + DBNAME;
		}
		return new String[] { forName, URL, USERNAME, PASSWORD };
	}
	/**
	 * 连接Oracle数据库(端口包含在DBAddress中)
	 * @param DBAddress
	 * @param DBNAME
	 * @param USERNAME
	 * @param PASSWORD
	 * @return
	 */
	public static String[] getOracleConn(String DBAddress,
			String DBNAME, String USERNAME, String PASSWORD) {
		String forName = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@" + DBAddress+":" + DBNAME;
		return new String[] { forName, URL, USERNAME, PASSWORD };
	}
	/**
	 * 连接Oracle数据库
	 * 
	 * @param DBLINK
	 * @param DBCOM
	 * @param DBNAME
	 * @param USERNAME
	 * @param PASSWORD
	 * @return
	 */
	private static String[] getOracleConn(String DBLINK, String DBCOM,
			String DBNAME, String USERNAME, String PASSWORD) {
		String forName = "oracle.jdbc.driver.OracleDriver";
		String URL = "jdbc:oracle:thin:@" + DBLINK + ":" + DBCOM + ":" + DBNAME;
		return new String[] { forName, URL, USERNAME, PASSWORD };
	}
}
