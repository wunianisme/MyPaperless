package com.foxconn.server.resource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
/**
 * DBCP连接池实现类
 * @author NSD
 *
 */
public class DBCPConnectionPool {

	private static DataSource ds;
	
	
	public static void initConnection(String fileName) throws Exception{
		InputStream in=DBCPConnectionPool.class.getClassLoader().getResourceAsStream(fileName);
		Properties prop=new Properties();
		prop.load(in);
		ds=BasicDataSourceFactory.createDataSource(prop);
		
	}
	
	public static  Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	public static void release(Connection conn,Statement stmt,ResultSet rs) throws Exception{
		if(rs!=null){
            //关闭存储查询结果的ResultSet对象
			rs.close();
			rs=null;
        }
        if(stmt!=null){
            //关闭负责执行SQL命令的Statement对象
            stmt.close();
            stmt=null;
        }
        
        if(conn!=null){
           //将Connection连接对象还给数据库连接池
           conn.close();
           conn=null;
        }
	}
	
}
