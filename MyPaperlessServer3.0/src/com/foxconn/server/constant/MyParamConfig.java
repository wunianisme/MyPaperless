package com.foxconn.server.constant;

import java.util.HashMap;
import java.util.Map;

import com.foxconn.server.constant.MyEnum.ParamConfig;
import com.foxconn.server.constant.MyEnum.ReportNum;
import com.foxconn.server.constant.MyEnum.Side;
import com.foxconn.server.constant.MyEnum.WS;

public class MyParamConfig {

	private String reportNum;
	private String MFG;
	private String workorderNo;
	private String floorName;
	private String equipment;
	private String skuNo;
	private String skuVersion;
	private String qty;
	private String deviation;
	private String side;
	private String checkRemark;
	
	
	public MyParamConfig(){}
	
	public MyParamConfig(String reportNum, String mFG, String workorderNo,
			String floorName, String equipment, String skuNo,
			String skuVersion, String qty, String deviation, String side,
			String checkRemark) {
		this.reportNum = reportNum;
		MFG = mFG;
		this.workorderNo = workorderNo;
		this.floorName = floorName;
		this.equipment = equipment;
		this.skuNo = skuNo;
		this.skuVersion = skuVersion;
		this.qty = qty;
		this.deviation = deviation;
		this.side = side;
		this.checkRemark = checkRemark;
	}
	/**
	 * 獲得請求參數(WebService型)
	 * @param params
	 * @return
	 */
	public Map<String,String> getWSParams(String...params){
		Map<String,String> paramMap=new HashMap<String, String>();
		String param=params[0];
		//String[] keyArray=param.split(ParamConfig.PARAM_SPLIT);
		if(param.equals(WS.param_TW)){//台灣廠區WebService
			paramMap.put("strDB", params[1]);//製造處
			paramMap.put("data", params[2]);//工單/SN
		}
		if(param.equals("strstation,strDb_Name,strwo")){//NSDI 開 (換)線點檢(PTH)/物料料號、插件數量(WebService)
			paramMap.put("strstation", "PTH1");
			if(MFG.equals(ParamConfig.MFGV)){
				paramMap.put("strDb_Name", "NSDBPD");
			}else{
				paramMap.put("strDb_Name", "NSD01");
			}
			paramMap.put("strwo", workorderNo);
			return paramMap;
		}
		if(param.equals("strDB,trantype,bu_name,skuno")){//Smart Guard Top Issue
			paramMap.put("strDB", "ICARDB");
			if(reportNum.equals(ReportNum.SMT_TOURCHECK)){//SMT巡迴檢查記錄表
				paramMap.put("trantype","SMT");
			}
			paramMap.put("bu_name",MFG);
			paramMap.put("skuno", skuNo);
		}
		return paramMap;
	}
	/**
	 * 獲得請求參數(SQL型)
	 * @param params
	 * @return
	 */
	public String[] getDBParams(String...params){
		String param=params[0];
		if(param.equals("Building,Line,ProductName,side")){
			return new String[]{floorName,equipment,skuNo,getSide(side)};
		}
		if(param.equals("wo,station_Name,process")){
			return new String[]{workorderNo,equipment,side};
		}
		if(param.equals("plant_name,line_name,p_no")){
			return new String[]{getFloor(floorName),equipment,skuNo};
		}
		if(param.equals("wo,process_flag")){
			return new String[]{workorderNo,side};
		}
		return new String[]{};
	}
	/**
	 * 轉換面別
	 * @param side
	 * @return
	 */
	private String getSide(String side){
		if(side.equalsIgnoreCase(Side.BOT[0])){
			return Side.BOT[1];
		}
		if(side.equalsIgnoreCase(Side.TOP[0])){
			return Side.TOP[1];
		}
		if(side.equalsIgnoreCase(Side.DOUBLE[0])){
			return Side.DOUBLE[1];
		}
		return "";
	}
	/**
	 * 轉換樓層
	 * @param floor
	 * @return
	 */
	private String getFloor(String floor){
		floor=floor.replace(" ", "");
		floor=floor.replace("D10", "DA");
		floor=floor.replace("F21", "FL");
		return floor;
	}
	/**
	 * 轉換是否為.Net webservice true:是
	 * @param dotNet
	 * @return
	 */
	public boolean getIsDotNet(String dotNet){
		if(dotNet.equals("1")){
			return true;
		}
		if(dotNet.equals("0")){
			return false;
		}
		return false;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getMFG() {
		return MFG;
	}

	public void setMFG(String mFG) {
		MFG = mFG;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSkuVersion() {
		return skuVersion;
	}

	public void setSkuVersion(String skuVersion) {
		this.skuVersion = skuVersion;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getDeviation() {
		return deviation;
	}

	public void setDeviation(String deviation) {
		this.deviation = deviation;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
}
