package com.foxconn.server.dao;

import java.util.List;

import com.foxconn.server.bean.JsonResult;
/**
 * 数据库数据操作JDBC
 * @author NSD
 *
 */
public interface ServiceDao {

	
	/**
	 * 查询数据库,并返回数据
	 * @param databaseInfo
	 * @param sql
	 * @param params
	 * @return
	 */
	public JsonResult query(String[] databaseInfo,String sql,String...params);
	
	/**
	 * 更改数据库,并返回数据
	 * @param databaseInfo
	 * @param sql
	 * @param params
	 * @return
	 */
	public JsonResult update(String[] databaseInfo,String sql,String...params);
	/**
	 * DBCP连接池查询
	 * @param sql
	 * @param params
	 * @return
	 */
	public JsonResult queryByDBCP(String sql,String...params);
	/**
	 * DBCP连接池更改
	 * @param sql
	 * @param params
	 * @return
	 */
	public JsonResult updateByDBCP(String sql,String...params);
	/**
	 * 初始化连接池
	 * @param fileName
	 * @throws Exception
	 */
	public void initDBCP(String fileName) throws Exception;
	
	
	
}
