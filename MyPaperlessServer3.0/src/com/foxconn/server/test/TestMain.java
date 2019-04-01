package com.foxconn.server.test;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.dao.ServiceDao;
import com.foxconn.server.dao.ServiceDaoImpl;
import com.foxconn.server.resource.Database;
/**
 * 測試頁面
 * @author NSD
 *
 */
public class TestMain {

	/**
	 * @param args
	 */
	static ServiceDao serviceDao;
	public static void main(String[] args) {
		try {
			serviceDao=new ServiceDaoImpl();
			serviceDao.initDBCP("database/LH.properties");
			long start=System.currentTimeMillis();
			//testDBCP();//exec time:397
			//testDBCP();//exec time:679
			//testJDBC();//exec time:413
			//testJDBC();
			testOracleJDBC();
			long end=System.currentTimeMillis();
			
			System.out.println("exec time:"+(end-start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void testDBCP() throws Exception{
		String sql="select * from ipqceuser ";
		JsonResult json=serviceDao.queryByDBCP(sql, null);
		
	}
	
	public static void testJDBC(){
		//ServiceDao serviceDao=new ServiceDaoImpl();
		String sql="select * from ipqceuser ";
		JsonResult json=serviceDao.query(Database.getDatabase("LH"), sql, null);
		//System.out.println(json.toString());
	}
	
	public static void testOracleJDBC(){
		String sql="select * from mes4.r_station_wip where rownum<1000 ";
		JsonResult json=serviceDao.query(Database.getMFGVConn("ALLPART"), sql,null);
		System.out.println(json.toString());
	}

}
