package com.foxconn.paperless.main.function.presenter;

import java.util.List;

import com.foxconn.paperless.bean.ParamInfo;

public interface ParameterManageDetailPresenter {

	void init(String reportNum, ParamInfo paramInfo,
			List<String> floorNameList, List<String> lineNameList);

	void switchSide();

	void getParamDetail();

	void updateParam();

	void deleteParam();

	void validateSMTPrinterParam(String floorName, String lineName, String skuNo,
			String side, String scraperPressure, String printSpeed, String separationSpeed,
			String separationDistance, String screenCleanRate, String squeegeeLength, 
			String problemDesc);

	void submitSMTPrinterParam();
	
	void initCheckBy();

	void setCheckBy(int pos);

	void validateSMTReflowParam(String floorName, String lineName, String skuNo,
			String side, String zone1, String zone2, String zone3,
			String zone4, String zone5, String zone6, String zone7,
			String zone8, String zone9, String zone10, String zone11,
			String zone12, String zone13, String speed, String fanSpeed,
			String shuntValue, String problemDesc);

	void submitSMTReflowParam();
	
	void validatePTHWSParam(String floorName, String lineName, String skuNo,
			String modelName,String BOTPreheatI, String BOTPreheatII,String BOTPreheatIII,
			String BOTPreheatIV,String TOPPreheatI,String TOPPreheatII,String TOPPreheatIII,
			String TOPPreheatIV,String ChainSpeedAfter,String ChainSpeed,
			String TinTemperature,String TinBathHeight, String TurbulentWave, 
			String AdvectionWave, String TinBarType,String FluxType, String FluxFlow, 
			String NozzleAirPressure, String TotalAirPressure,String TankAirPressure, 
			String FluxProportion, String remark, String problemDesc);

	void submitPTHWSParam();

	void setSide(String side);

	

	

}
