package com.foxconn.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.foxconn.server.bean.JsonResult;
import com.foxconn.server.constant.MyEnum.ResultCode;
import com.foxconn.server.resource.DBCPConnectionPool;
public class ServiceDaoImpl implements ServiceDao {

	private JsonResult jsonResult;
	
	public ServiceDaoImpl(){
		System.out.println("New Object!");//只是在項目開始部署時創建對象
	}
	
	private void close(Connection conn,PreparedStatement stmt,ResultSet rs) throws Exception{
		DBCPConnectionPool.release(conn, stmt, rs);
	}
	
	private PreparedStatement getStatement(String sql,Connection conn,boolean isDBCP,String...params) throws Exception{
		PreparedStatement stmt =conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_SENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
		System.out.println("sql:"+sql);
		for (int i = 0; params != null && i < params.length; i++)
			stmt.setObject(i + 1, params[i]);
		return stmt;
	}
	
	private JsonResult getQueryData(JsonResult json,String sql,String[] databaseInfo,boolean isDBCP,String...params) throws SQLException {
		//這三個對象都改為局部對象，使每個請求都相對獨立，互不影響
		Connection conn =null;
		PreparedStatement stmt = null ;
		ResultSet rs=null;
		try {
			if(isDBCP){
				conn=DBCPConnectionPool.getConnection();
			}else{
				Class.forName(databaseInfo[0]);
				conn = DriverManager.getConnection(databaseInfo[1], databaseInfo[2],
						databaseInfo[3]);
			}
			conn.setAutoCommit(false);// 设置事务的提交方式为非自动提交：
			stmt=getStatement(sql,conn, isDBCP, params);
			List<String> data=new ArrayList<String>();
			rs=stmt.executeQuery();
			conn.commit();//在try块内添加事务的提交操作，表示操作无异常，提交事务。
			ResultSetMetaData rsmd = rs.getMetaData(); // 取得数据表中的字段数目，类型等返回结果
			int columnCount = rsmd.getColumnCount(); // 列的总数//
			rs.last();
			int row=rs.getRow();
			System.out.println("查詢行數-------"+row);
			if(row<=0){
				json.setResultCode(ResultCode.NULL);
				return json;
			}
			rs.beforeFirst();
			String value="";
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					value = rs.getString(rsmd.getColumnName(i));
					if (value==null||value.length() == 0) {
						data.add("");
					} else {
						data.add(value);
					}
				}
			}
			json.setResultCode(ResultCode.TRUE);
			json.setResultMsg("query rownum:"+row);
			json.setData(data);
			json.setColumnNum(columnCount);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//在catch块内添加回滚事务，表示操作出现异常，撤销事务
			json.setResultCode(ResultCode.EXCEPTION);
			json.setResultMsg(e.getMessage());
			return json;
		} finally {
			try {
				close(conn,stmt,rs);
			} catch (Exception e) {
				e.printStackTrace();
				json.setResultCode(ResultCode.EXCEPTION);
				json.setResultMsg(e.getMessage());
				return json;
			}
		}
		return json;
	}
	
	private JsonResult getUpdateData(JsonResult json,String sql,String[] databaseInfo,boolean isDBCP,String...params) throws SQLException{
		Connection conn =null;
		PreparedStatement stmt = null ;
		try {
			if(isDBCP){
				conn=DBCPConnectionPool.getConnection();
			}else{
				Class.forName(databaseInfo[0]);
				conn = DriverManager.getConnection(databaseInfo[1], databaseInfo[2],
						databaseInfo[3]);
			}
			conn.setAutoCommit(false);// 设置事务的提交方式为非自动提交：
			stmt=getStatement(sql,conn, isDBCP, params);
			int row=0;
			row=stmt.executeUpdate();
			conn.commit();//在try块内添加事务的提交操作，表示操作无异常，提交事务。
			System.out.println("影響行數>>>"+row);
			if (row>0) {
				json.setResultCode(ResultCode.TRUE);
			}else {
				json.setResultCode(ResultCode.NULL);
			}
			json.setResultMsg("update rownum:"+row);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//在catch块内添加回滚事务，表示操作出现异常，撤销事务
			json.setResultCode(ResultCode.EXCEPTION);
			json.setResultMsg(e.getMessage());
			return json;
		}finally {
			try {
				close(conn,stmt,null);
			} catch (Exception e) {
				e.printStackTrace();
				json.setResultCode(ResultCode.EXCEPTION);
				json.setResultMsg(e.getMessage());
				return json;
			}
		}
		return json;
	}
	
	public JsonResult query(String[] databaseInfo,String sql,String...params) {
		try {
			jsonResult=new JsonResult();
			jsonResult=getQueryData(jsonResult, sql, databaseInfo, false, params);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);
			jsonResult.setResultMsg(e.getMessage());
			return jsonResult;
		}
		return jsonResult;
	}

	public JsonResult update(String[] databaseInfo,String sql,String...params) {
		try {
			jsonResult=new JsonResult();
			jsonResult=getUpdateData(jsonResult, sql, databaseInfo, false, params);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);
			jsonResult.setResultMsg(e.getMessage());
			return jsonResult;
		}
		return jsonResult;
	}
	
	@Override
	public JsonResult queryByDBCP(String sql, String...params) {
		try {
			jsonResult=new JsonResult();
			jsonResult=getQueryData(jsonResult, sql, null, true, params);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);
			jsonResult.setResultMsg(e.getMessage());
			return jsonResult;
		}
		return jsonResult;
	}

	@Override
	public JsonResult updateByDBCP(String sql, String...params) {
		try {
			jsonResult=new JsonResult();
			jsonResult=getUpdateData(jsonResult, sql, null, true, params);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setResultCode(ResultCode.EXCEPTION);
			jsonResult.setResultMsg(e.getMessage());
			return jsonResult;
		}
		return jsonResult;
	}

	@Override
	public void initDBCP(String fileName) throws Exception {
		DBCPConnectionPool.initConnection(fileName);
	}
}
