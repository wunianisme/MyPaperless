package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.constant.MyEnum.Side;
import com.foxconn.paperless.main.function.presenter.ParameterManageDetailPresenter;
import com.foxconn.paperless.main.function.presenter.ParameterManageDetailPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
import com.foxconn.paperless.util.TextUtil;
/**
 * 參數管理詳細信息/修改/刪除
 *@ClassName ParameterManageDetailActivity
 *@Author wunian
 *@Date 2018/1/11 上午9:56:03
 */
public class ParameterManageDetailActivity extends BaseActivity implements
		ParameterManageDetailView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	//基本
	@ViewInject(id=R.id.BULayout)LinearLayout BULayout;
	@ViewInject(id=R.id.tvBU)TextView tvBU;
	@ViewInject(id=R.id.tvFloorName)TextView tvFloorName;
	@ViewInject(id=R.id.tvLineName)TextView tvLineName;
	@ViewInject(id=R.id.tvSkuNo)TextView tvSkuNo;
	@ViewInject(id=R.id.sideLayout)LinearLayout sideLayout;
	@ViewInject(id=R.id.tvSide)TextView tvSide;
	@ViewInject(id=R.id.problemDescLayout)LinearLayout problemDescLayout;
	@ViewInject(id=R.id.etProblemDesc)EditText etProblemDesc;
	//修改信息
	@ViewInject(id=R.id.createLayout)LinearLayout createLayout;
	@ViewInject(id=R.id.tvLastEditdate)TextView tvLastEditdate;
	@ViewInject(id=R.id.tvCreateBy)TextView tvCreateBy;
	//按鈕
	@ViewInject(id=R.id.btnLayout)LinearLayout btnLayout;
	@ViewInject(id=R.id.btnUpdate,click="btnClick")Button btnUpdate;
	@ViewInject(id=R.id.btnDelete,click="btnClick")Button btnDelete;
	@ViewInject(id=R.id.btnSubmit,click="btnClick")Button btnSubmit;
	//SMT印刷機
	@ViewInject(id=R.id.SMTPrinterLayout)LinearLayout SMTPrinterLayout;
	@ViewInject(id=R.id.tvScraperPressure)TextView tvScraperPressure;
	@ViewInject(id=R.id.etScraperPressure)TextView etScraperPressure;
	@ViewInject(id=R.id.tvPrintSpeed)TextView tvPrintSpeed;
	@ViewInject(id=R.id.etPrintSpeed)EditText etPrintSpeed;
	@ViewInject(id=R.id.tvSeparationSpeed)TextView tvSeparationSpeed;
	@ViewInject(id=R.id.etSeparationSpeed)EditText etSeparationSpeed;
	@ViewInject(id=R.id.tvSeparationDistance)TextView tvSeparationDistance;
	@ViewInject(id=R.id.etSeparationDistance)EditText etSeparationDistance;
	@ViewInject(id=R.id.tvScreenCleanRate)TextView tvScreenCleanRate;
	@ViewInject(id=R.id.etScreenCleanRate)EditText etScreenCleanRate;
	@ViewInject(id=R.id.tvSqueegeeLength)TextView tvSqueegeeLength;
	@ViewInject(id=R.id.etSqueegeeLength)EditText etSqueegeeLength;
	//SMT回焊爐
	@ViewInject(id=R.id.SMTReflowLayout)LinearLayout SMTReflowLayout;
	@ViewInject(id=R.id.tvZone1)TextView tvZone1;
	@ViewInject(id=R.id.etZone1)EditText etZone1;
	@ViewInject(id=R.id.tvZone2)TextView tvZone2;
	@ViewInject(id=R.id.etZone2)EditText etZone2;
	@ViewInject(id=R.id.tvZone3)TextView tvZone3;
	@ViewInject(id=R.id.etZone3)EditText etZone3;
	@ViewInject(id=R.id.tvZone4)TextView tvZone4;
	@ViewInject(id=R.id.etZone4)EditText etZone4;
	@ViewInject(id=R.id.tvZone5)TextView tvZone5;
	@ViewInject(id=R.id.etZone5)EditText etZone5;
	@ViewInject(id=R.id.tvZone6)TextView tvZone6;
	@ViewInject(id=R.id.etZone6)EditText etZone6;
	@ViewInject(id=R.id.tvZone7)TextView tvZone7;
	@ViewInject(id=R.id.etZone7)EditText etZone7;
	@ViewInject(id=R.id.tvZone8)TextView tvZone8;
	@ViewInject(id=R.id.etZone8)EditText etZone8;
	@ViewInject(id=R.id.tvZone9)TextView tvZone9;
	@ViewInject(id=R.id.etZone9)EditText etZone9;
	@ViewInject(id=R.id.tvZone10)TextView tvZone10;
	@ViewInject(id=R.id.etZone10)EditText etZone10;
	@ViewInject(id=R.id.tvZone11)TextView tvZone11;
	@ViewInject(id=R.id.etZone11)EditText etZone11;
	@ViewInject(id=R.id.tvZone12)TextView tvZone12;
	@ViewInject(id=R.id.etZone12)EditText etZone12;
	@ViewInject(id=R.id.tvZone13)TextView tvZone13;
	@ViewInject(id=R.id.etZone13)EditText etZone13;
	@ViewInject(id=R.id.tvSpeed)TextView tvSpeed;
	@ViewInject(id=R.id.etSpeed)EditText etSpeed;
	@ViewInject(id=R.id.tvFanSpeed)TextView tvFanSpeed;
	@ViewInject(id=R.id.etFanSpeed)EditText etFanSpeed;
	@ViewInject(id=R.id.tvShuntValue)TextView tvShuntValue;
	@ViewInject(id=R.id.etShuntValue)EditText etShuntValue;
	//波峰焊
	@ViewInject(id=R.id.PTHWSLayout)LinearLayout PTHWSLayout;
	@ViewInject(id=R.id.tvModelName)TextView tvModelName;
	@ViewInject(id=R.id.etModelName)EditText etModelName;
	@ViewInject(id=R.id.tvBOTPreheatI)TextView tvBOTPreheatI;
	@ViewInject(id=R.id.etBOTPreheatI)EditText etBOTPreheatI;
	@ViewInject(id=R.id.tvBOTPreheatII)TextView tvBOTPreheatII;
	@ViewInject(id=R.id.etBOTPreheatII)EditText etBOTPreheatII;
	@ViewInject(id=R.id.tvBOTPreheatIII)TextView tvBOTPreheatIII;
	@ViewInject(id=R.id.etBOTPreheatIII)EditText etBOTPreheatIII;
	@ViewInject(id=R.id.tvBOTPreheatIV)TextView tvBOTPreheatIV;
	@ViewInject(id=R.id.etBOTPreheatIV)EditText etBOTPreheatIV;
	@ViewInject(id=R.id.tvTopPreheatI)TextView tvTopPreheatI;
	@ViewInject(id=R.id.etTopPreheatI)EditText etTopPreheatI;
	@ViewInject(id=R.id.tvTopPreheatII)TextView tvTopPreheatII;
	@ViewInject(id=R.id.etTopPreheatII)EditText etTopPreheatII;
	@ViewInject(id=R.id.tvTopPreheatIII)TextView tvTopPreheatIII;
	@ViewInject(id=R.id.etTopPreheatIII)EditText etTopPreheatIII;
	@ViewInject(id=R.id.tvTopPreheatIV)TextView tvTopPreheatIV;
	@ViewInject(id=R.id.etTopPreheatIV)EditText etTopPreheatIV;
	@ViewInject(id=R.id.tvChainSpeedAfter)TextView tvChainSpeedAfter;
	@ViewInject(id=R.id.etChainSpeedAfter)EditText etChainSpeedAfter;
	@ViewInject(id=R.id.tvChainSpeed)TextView tvChainSpeed;
	@ViewInject(id=R.id.etChainSpeed)EditText etChainSpeed;
	@ViewInject(id=R.id.tvTinTemperature)TextView tvTinTemperature;
	@ViewInject(id=R.id.etTinTemperature)EditText etTinTemperature;
	@ViewInject(id=R.id.tvTinBathHeight)TextView tvTinBathHeight;
	@ViewInject(id=R.id.etTinBathHeight)EditText etTinBathHeight;
	@ViewInject(id=R.id.tvTurbulentWave)TextView tvTurbulentWave;
	@ViewInject(id=R.id.etTurbulentWave)EditText etTurbulentWave;
	@ViewInject(id=R.id.tvAdvectionWave)TextView tvAdvectionWave;
	@ViewInject(id=R.id.etAdvectionWave)EditText etAdvectionWave;
	@ViewInject(id=R.id.tvTinBarType)TextView tvTinBarType;
	@ViewInject(id=R.id.etTinBarType)EditText etTinBarType;
	@ViewInject(id=R.id.tvFluxType)TextView tvFluxType;
	@ViewInject(id=R.id.etFluxType)EditText etFluxType;
	@ViewInject(id=R.id.tvFluxFlow)TextView tvFluxFlow;
	@ViewInject(id=R.id.etFluxFlow)EditText etFluxFlow;
	@ViewInject(id=R.id.tvNozzleAirPressure)TextView tvNozzleAirPressure;
	@ViewInject(id=R.id.etNozzleAirPressure)EditText etNozzleAirPressure;
	@ViewInject(id=R.id.tvTotalAirPressure)TextView tvTotalAirPressure;
	@ViewInject(id=R.id.etTotalAirPressure)EditText etTotalAirPressure;
	@ViewInject(id=R.id.tvTankAirPressure)TextView tvTankAirPressure;
	@ViewInject(id=R.id.etTankAirPressure)EditText etTankAirPressure;
	@ViewInject(id=R.id.tvFluxProportion)TextView tvFluxProportion;
	@ViewInject(id=R.id.etFluxProportion)EditText etFluxProportion;
	@ViewInject(id=R.id.tvRemark)TextView tvRemark;
	@ViewInject(id=R.id.etRemark)EditText etRemark;
	
	private Context context;
	private ParameterManageDetailPresenter presenter;
	private String reportNum;
	private CustomerDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametermanagedetail);
		init();
	}
	
	@Override
	protected void init() {
		context=ParameterManageDetailActivity.this;
		presenter=new ParameterManageDetailPresenterImpl(this, context);
		Bundle bundle=getIntent().getExtras();
		reportNum=bundle.getString("reportNum");
		tvTitle.setText(R.string.paramdetail_title);
		if(!reportNum.equals(ReportNum.PTH_WS)){//除了波峰焊外顯示切換面別
			tvRight.setVisibility(View.VISIBLE);
			tvRight.setText(R.string.switch_side);
			setSide(Side.BOT);
		}
		ParamInfo paramInfo=(ParamInfo) bundle.getSerializable("paramInfo");
		List<String> floorNameList=bundle.getStringArrayList("floorNameList");
		List<String> lineNameList=bundle.getStringArrayList("lineNameList");
		presenter.init(reportNum,paramInfo,floorNameList,lineNameList);
		presenter.getParamDetail();
		
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvRight:
			presenter.switchSide();
			break;
		
		case R.id.btnUpdate://修改
			presenter.updateParam();
			break;
		case R.id.btnDelete://刪除
			presenter.deleteParam();
			
			break;
		case R.id.btnSubmit://提交修改
			if(reportNum.equals(ReportNum.SMT_PRINTER)){
				presenter.validateSMTPrinterParam(
						tvFloorName.getText().toString(),
						tvLineName.getText().toString(),
						tvSkuNo.getText().toString(),
						tvSide.getText().toString(),
						etScraperPressure.getText().toString(),
						etPrintSpeed.getText().toString(),
						etSeparationSpeed.getText().toString(),
						etSeparationDistance.getText().toString(),
						etScreenCleanRate.getText().toString(),
						etSqueegeeLength.getText().toString(),
						etProblemDesc.getText().toString()
				);
			}
			if(reportNum.equals(ReportNum.SMT_REFLOW)){
				presenter.validateSMTReflowParam(
						tvFloorName.getText().toString(),
						tvLineName.getText().toString(),
						tvSkuNo.getText().toString(),
						tvSide.getText().toString(),
						etZone1.getText().toString(),
						etZone2.getText().toString(),
						etZone3.getText().toString(),
						etZone4.getText().toString(),
						etZone5.getText().toString(),
						etZone6.getText().toString(),
						etZone7.getText().toString(),
						etZone8.getText().toString(),
						etZone9.getText().toString(),
						etZone10.getText().toString(),
						etZone11.getText().toString(),
						etZone12.getText().toString(),
						etZone13.getText().toString(),
						etSpeed.getText().toString(),
						etFanSpeed.getText().toString(),
						etShuntValue.getText().toString(),
						etProblemDesc.getText().toString()
				);
			}
			if(reportNum.equals(ReportNum.PTH_WS)){
				presenter.validatePTHWSParam(
						tvFloorName.getText().toString(),
						tvLineName.getText().toString(),
						tvSkuNo.getText().toString(),
						etModelName.getText().toString(),
						etBOTPreheatI.getText().toString(),
						etBOTPreheatII.getText().toString(),
						etBOTPreheatIII.getText().toString(),
						etBOTPreheatIV.getText().toString(),
						etTopPreheatI.getText().toString(),
						etTopPreheatII.getText().toString(),
						etTopPreheatIII.getText().toString(),
						etTopPreheatIV.getText().toString(),
						etChainSpeedAfter.getText().toString(),
						etChainSpeed.getText().toString(),
						etTinTemperature.getText().toString(),
						etTinBathHeight.getText().toString(),
						etTurbulentWave.getText().toString(),
						etAdvectionWave.getText().toString(),
						etTinBarType.getText().toString(),
						etFluxType.getText().toString(),
						etFluxFlow.getText().toString(),
						etNozzleAirPressure.getText().toString(),
						etTotalAirPressure.getText().toString(),
						etTankAirPressure.getText().toString(),
						etFluxProportion.getText().toString(),
						etRemark.getText().toString(),
						etProblemDesc.getText().toString()
						);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		showSuccess(strId);
	}

	@Override
	public void showToastFailedMsg(int strId) {
		showError(strId);
	}

	@Override
	public void showToastExceptionMsg(String msg) {
		showException(msg);
	}

	@Override
	public void showLoading() {
		showLoadingDialog();
	}

	@Override
	public void dismissLoading() {
		dismissLoadingDialog();
	}

	@Override
	public void showParamDetailLayout(int SMTPrinterVisible,
			int SMTReflowVisible, int PTHWSVisible) {
		SMTPrinterLayout.setVisibility(SMTPrinterVisible);
		SMTReflowLayout.setVisibility(SMTReflowVisible);
		PTHWSLayout.setVisibility(PTHWSVisible);
		if(PTHWSVisible==View.VISIBLE){
			sideLayout.setVisibility(View.GONE);
			BULayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void initParam(ParamInfo paramInfo, String side) {
		tvFloorName.setText(paramInfo.getBuilding());
		tvLineName.setText(paramInfo.getLine());
		tvSkuNo.setText(paramInfo.getProductName());
		if(tvSide.getVisibility()==View.VISIBLE)
			tvSide.setText(side);
	}
	
	@Override
	public void showBaseUpdateView(List<String> floorNameList,
			List<String> lineNameList,String side) {
		tvTitle.setText(R.string.paramupdate_title);
		tvRight.setVisibility(View.GONE);
		problemDescLayout.setVisibility(View.VISIBLE);
		BULayout.setVisibility(View.GONE);
		createLayout.setVisibility(View.GONE);
		//顯示提交按鈕
		btnUpdate.setVisibility(View.GONE);
		btnDelete.setVisibility(View.GONE);
		btnSubmit.setVisibility(View.VISIBLE);
	}

	@Override
	public void showSMTPrinterParamDetail(List<String> paramList,String side) {
		tvScraperPressure.setText(paramList.get(0));
		tvPrintSpeed.setText(paramList.get(1));
		tvSeparationSpeed.setText(paramList.get(2));
		tvSeparationDistance.setText(paramList.get(3));
		tvScreenCleanRate.setText(paramList.get(4));
		tvSqueegeeLength.setText(paramList.get(5));
		tvLastEditdate.setText(paramList.get(6));
		tvCreateBy.setText(paramList.get(7));
		if(!TextUtil.isEmpty(paramList.get(11))){
			tvSide.setText(paramList.get(11));
		}else{
			tvSide.setText(side);
		}
		setSide(tvSide.getText().toString());
		presenter.setSide(tvSide.getText().toString());
	}
	
	@Override
	public void showSMTPrinterUpdateView() {
		etScraperPressure.setText(tvScraperPressure.getText().toString());
		etPrintSpeed.setText(tvPrintSpeed.getText().toString());
		etSeparationSpeed.setText(tvSeparationSpeed.getText().toString());
		etSeparationDistance.setText(tvSeparationDistance.getText().toString());
		etScreenCleanRate.setText(tvScreenCleanRate.getText().toString());
		etSqueegeeLength.setText(tvSqueegeeLength.getText().toString());
		tvScraperPressure.setVisibility(View.GONE);
		tvPrintSpeed.setVisibility(View.GONE);
		tvSeparationSpeed.setVisibility(View.GONE);
		tvSeparationDistance.setVisibility(View.GONE);
		tvScreenCleanRate.setVisibility(View.GONE);
		tvSqueegeeLength.setVisibility(View.GONE);
		etScraperPressure.setVisibility(View.VISIBLE);
		etPrintSpeed.setVisibility(View.VISIBLE);
		etSeparationSpeed.setVisibility(View.VISIBLE);
		etSeparationDistance.setVisibility(View.VISIBLE);
		etScreenCleanRate.setVisibility(View.VISIBLE);
		etSqueegeeLength.setVisibility(View.VISIBLE);
	}

	@Override
	public void showSMTReflowParamDetail(List<String> paramList,String side) {
		tvZone1.setText(paramList.get(0));
		tvZone2.setText(paramList.get(1));
		tvZone3.setText(paramList.get(2));
		tvZone4.setText(paramList.get(3));
		tvZone5.setText(paramList.get(4));
		tvZone6.setText(paramList.get(5));
		tvZone7.setText(paramList.get(6));
		tvZone8.setText(paramList.get(7));
		tvZone9.setText(paramList.get(8));
		tvZone10.setText(paramList.get(9));
		tvZone11.setText(paramList.get(10));
		tvZone12.setText(paramList.get(11));
		tvZone13.setText(paramList.get(12));
		tvSpeed.setText(paramList.get(13));
		tvFanSpeed.setText(paramList.get(14));
		tvShuntValue.setText(paramList.get(15));
		tvLastEditdate.setText(paramList.get(16));
		tvCreateBy.setText(paramList.get(17));
		if(!TextUtil.isEmpty(paramList.get(21))){
			tvSide.setText(paramList.get(21));
		}else{
			tvSide.setText(side);
		}
		setSide(tvSide.getText().toString());
		presenter.setSide(tvSide.getText().toString());
	}
	
	@Override
	public void showSMTReflowUpdateView() {
		etZone1.setText(tvZone1.getText().toString());
		etZone2.setText(tvZone2.getText().toString());
		etZone3.setText(tvZone3.getText().toString());
		etZone4.setText(tvZone4.getText().toString());
		etZone5.setText(tvZone5.getText().toString());
		etZone6.setText(tvZone6.getText().toString());
		etZone7.setText(tvZone7.getText().toString());
		etZone8.setText(tvZone8.getText().toString());
		etZone9.setText(tvZone9.getText().toString());
		etZone10.setText(tvZone10.getText().toString());
		etZone11.setText(tvZone11.getText().toString());
		etZone12.setText(tvZone12.getText().toString());
		etZone13.setText(tvZone13.getText().toString());
		etSpeed.setText(tvSpeed.getText().toString());
		etFanSpeed.setText(tvFanSpeed.getText().toString());
		etShuntValue.setText(tvShuntValue.getText().toString());
		tvZone1.setVisibility(View.GONE);
		tvZone2.setVisibility(View.GONE);
		tvZone3.setVisibility(View.GONE);
		tvZone4.setVisibility(View.GONE);
		tvZone5.setVisibility(View.GONE);
		tvZone6.setVisibility(View.GONE);
		tvZone7.setVisibility(View.GONE);
		tvZone8.setVisibility(View.GONE);
		tvZone9.setVisibility(View.GONE);
		tvZone10.setVisibility(View.GONE);
		tvZone11.setVisibility(View.GONE);
		tvZone12.setVisibility(View.GONE);
		tvZone13.setVisibility(View.GONE);
		tvSpeed.setVisibility(View.GONE);
		tvFanSpeed.setVisibility(View.GONE);
		tvShuntValue.setVisibility(View.GONE);
		etZone1.setVisibility(View.VISIBLE);
		etZone2.setVisibility(View.VISIBLE);
		etZone3.setVisibility(View.VISIBLE);
		etZone4.setVisibility(View.VISIBLE);
		etZone5.setVisibility(View.VISIBLE);
		etZone6.setVisibility(View.VISIBLE);
		etZone7.setVisibility(View.VISIBLE);
		etZone8.setVisibility(View.VISIBLE);
		etZone9.setVisibility(View.VISIBLE);
		etZone10.setVisibility(View.VISIBLE);
		etZone11.setVisibility(View.VISIBLE);
		etZone12.setVisibility(View.VISIBLE);
		etZone13.setVisibility(View.VISIBLE);
		etSpeed.setVisibility(View.VISIBLE);
		etFanSpeed.setVisibility(View.VISIBLE);
		etShuntValue.setVisibility(View.VISIBLE);
	}

	@Override
	public void showPTHWSParamDetail(List<String> paramList) {
		tvBU.setText(paramList.get(0));
		tvModelName.setText(paramList.get(1));
		tvBOTPreheatI.setText(paramList.get(2));
		tvBOTPreheatII.setText(paramList.get(3));
		tvBOTPreheatIII.setText(paramList.get(4));
		tvBOTPreheatIV.setText(paramList.get(5));
		tvTopPreheatI.setText(paramList.get(6));
		tvTopPreheatII.setText(paramList.get(7));
		tvTopPreheatIII.setText(paramList.get(8));
		tvTopPreheatIV.setText(paramList.get(9));
		tvChainSpeedAfter.setText(paramList.get(10));
		tvChainSpeed.setText(paramList.get(11));
		tvTinTemperature.setText(paramList.get(12));
		tvTinBathHeight.setText(paramList.get(13));
		tvTurbulentWave.setText(paramList.get(14));
		tvAdvectionWave.setText(paramList.get(15));
		tvTinBarType.setText(paramList.get(16));
		tvFluxType.setText(paramList.get(17));
		tvFluxFlow.setText(paramList.get(18));
		tvNozzleAirPressure.setText(paramList.get(19));
		tvTotalAirPressure.setText(paramList.get(20));
		tvTankAirPressure.setText(paramList.get(21));
		tvFluxProportion.setText(paramList.get(22));
		tvRemark.setText(paramList.get(23));
		tvLastEditdate.setText(paramList.get(24));
		tvCreateBy.setText(paramList.get(25));
	}
	
	@Override
	public void showPTHWSUpdateView() {
		etModelName.setText(tvModelName.getText().toString());
		etBOTPreheatI.setText(tvBOTPreheatI.getText().toString());
		etBOTPreheatII.setText(tvBOTPreheatII.getText().toString());
		etBOTPreheatIII.setText(tvBOTPreheatIII.getText().toString());
		etBOTPreheatIV.setText(tvBOTPreheatIV.getText().toString());
		etTopPreheatI.setText(tvTopPreheatI.getText().toString());
		etTopPreheatII.setText(tvTopPreheatII.getText().toString());
		etTopPreheatIII.setText(tvTopPreheatIII.getText().toString());
		etTopPreheatIV.setText(tvTopPreheatIV.getText().toString());
		etChainSpeedAfter.setText(tvChainSpeedAfter.getText().toString());
		etChainSpeed.setText(tvChainSpeed.getText().toString());
		etTinTemperature.setText(tvTinTemperature.getText().toString());
		etTinBathHeight.setText(tvTinBathHeight.getText().toString());
		etTurbulentWave.setText(tvTurbulentWave.getText().toString());
		etAdvectionWave.setText(tvAdvectionWave.getText().toString());
		etTinBarType.setText(tvTinBarType.getText().toString());
		etFluxType.setText(tvFluxType.getText().toString());
		etFluxFlow.setText(tvFluxFlow.getText().toString());
		etNozzleAirPressure.setText(tvNozzleAirPressure.getText().toString());
		etTotalAirPressure.setText(tvTotalAirPressure.getText().toString());
		etTankAirPressure.setText(tvTankAirPressure.getText().toString());
		etFluxProportion.setText(tvFluxProportion.getText().toString());
		etRemark.setText(tvRemark.getText().toString());
		tvModelName.setVisibility(View.GONE);
		tvBOTPreheatI.setVisibility(View.GONE);
		tvBOTPreheatII.setVisibility(View.GONE);
		tvBOTPreheatIII.setVisibility(View.GONE);
		tvBOTPreheatIV.setVisibility(View.GONE);
		tvTopPreheatI.setVisibility(View.GONE);
		tvTopPreheatII.setVisibility(View.GONE);
		tvTopPreheatIII.setVisibility(View.GONE);
		tvTopPreheatIV.setVisibility(View.GONE);
		tvChainSpeedAfter.setVisibility(View.GONE);
		tvChainSpeed.setVisibility(View.GONE);
		tvTinTemperature.setVisibility(View.GONE);
		tvTinBathHeight.setVisibility(View.GONE);
		tvTurbulentWave.setVisibility(View.GONE);
		tvAdvectionWave.setVisibility(View.GONE);
		tvTinBarType.setVisibility(View.GONE);
		tvFluxType.setVisibility(View.GONE);
		tvFluxFlow.setVisibility(View.GONE);
		tvNozzleAirPressure.setVisibility(View.GONE);
		tvTotalAirPressure.setVisibility(View.GONE);
		tvTankAirPressure.setVisibility(View.GONE);
		tvFluxProportion.setVisibility(View.GONE);
		tvRemark.setVisibility(View.GONE);
		etModelName.setVisibility(View.VISIBLE);
		etBOTPreheatI.setVisibility(View.VISIBLE);
		etBOTPreheatII.setVisibility(View.VISIBLE);
		etBOTPreheatIII.setVisibility(View.VISIBLE);
		etBOTPreheatIV.setVisibility(View.VISIBLE);
		etTopPreheatI.setVisibility(View.VISIBLE);
		etTopPreheatII.setVisibility(View.VISIBLE);
		etTopPreheatIII.setVisibility(View.VISIBLE);
		etTopPreheatIV.setVisibility(View.VISIBLE);
		etChainSpeedAfter.setVisibility(View.VISIBLE);
		etChainSpeed.setVisibility(View.VISIBLE);
		etTinTemperature.setVisibility(View.VISIBLE);
		etTinBathHeight.setVisibility(View.VISIBLE);
		etTurbulentWave.setVisibility(View.VISIBLE);
		etAdvectionWave.setVisibility(View.VISIBLE);
		etTinBarType.setVisibility(View.VISIBLE);
		etFluxType.setVisibility(View.VISIBLE);
		etFluxFlow.setVisibility(View.VISIBLE);
		etNozzleAirPressure.setVisibility(View.VISIBLE);
		etTotalAirPressure.setVisibility(View.VISIBLE);
		etTankAirPressure.setVisibility(View.VISIBLE);
		etFluxProportion.setVisibility(View.VISIBLE);
		etRemark.setVisibility(View.VISIBLE);
	}

	@Override
	public void setSide(String side) {
		tvSide.setText(side);
		String title=context.getResources().getString(R.string.paramdetail_title);
		tvTitle.setText(title+"-"+side);
	}

	@Override
	public void showBtnLayout(int visible) {
		btnLayout.setVisibility(visible);
	}

	@Override
	public void showSubmitParamDialog(int titleId, int btnOK, int btnNo,
			List<String> masterNameList) {
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, 
				masterNameList);
		dialog=new CustomerDialog(context, titleId, adapter, new OnItemClickListener() {
			int preIndex=0;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				if(preIndex!=pos){//每次點擊條目上一個被點擊的條目恢復白色背景
					arg0.getChildAt(preIndex).setBackgroundColor(Color.WHITE);
				}
				//被點擊的條目顯示為綠色背景
				arg1.setBackgroundColor(Color.GREEN);
				preIndex=pos;
				presenter.setCheckBy(pos);
			}
		}, true);
		dialog.setOKBtn(btnOK, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//dialog.dismiss();
				if(reportNum.equals(ReportNum.SMT_PRINTER)){
					presenter.submitSMTPrinterParam();
				}
				if(reportNum.equals(ReportNum.SMT_REFLOW)){
					presenter.submitSMTReflowParam();
				}
				if(reportNum.equals(ReportNum.PTH_WS)){
					presenter.submitPTHWSParam();
				}
			}
		});
		dialog.setNoBtn(btnNo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				presenter.initCheckBy();
			}
		});
	}
	
	@Override
	public void dismissSubmitParamDialog() {
		if(dialog.isShowing()) dialog.dismiss();
	}

	@Override
	public void submitParam() {//提交刪除操作的參數
		if(reportNum.equals(ReportNum.SMT_PRINTER)){
			presenter.validateSMTPrinterParam(
					tvFloorName.getText().toString(),
					tvLineName.getText().toString(),
					tvSkuNo.getText().toString(),
					tvSide.getText().toString(),
					tvScraperPressure.getText().toString(),
					tvPrintSpeed.getText().toString(),
					tvSeparationSpeed.getText().toString(),
					tvSeparationDistance.getText().toString(),
					tvScreenCleanRate.getText().toString(),
					tvSqueegeeLength.getText().toString(),
					""
			);
		}
		if(reportNum.equals(ReportNum.SMT_REFLOW)){
			presenter.validateSMTReflowParam(
					tvFloorName.getText().toString(),
					tvLineName.getText().toString(),
					tvSkuNo.getText().toString(),
					tvSide.getText().toString(),
					tvZone1.getText().toString(),
					tvZone2.getText().toString(),
					tvZone3.getText().toString(),
					tvZone4.getText().toString(),
					tvZone5.getText().toString(),
					tvZone6.getText().toString(),
					tvZone7.getText().toString(),
					tvZone8.getText().toString(),
					tvZone9.getText().toString(),
					tvZone10.getText().toString(),
					tvZone11.getText().toString(),
					tvZone12.getText().toString(),
					tvZone13.getText().toString(),
					tvSpeed.getText().toString(),
					tvFanSpeed.getText().toString(),
					tvShuntValue.getText().toString(),
					""
			);
		}
		if(reportNum.equals(ReportNum.PTH_WS)){
			presenter.validatePTHWSParam(
					tvFloorName.getText().toString(),
					tvLineName.getText().toString(),
					tvSkuNo.getText().toString(),
					tvModelName.getText().toString(),
					tvBOTPreheatI.getText().toString(),
					tvBOTPreheatII.getText().toString(),
					tvBOTPreheatIII.getText().toString(),
					tvBOTPreheatIV.getText().toString(),
					tvTopPreheatI.getText().toString(),
					tvTopPreheatII.getText().toString(),
					tvTopPreheatIII.getText().toString(),
					tvTopPreheatIV.getText().toString(),
					tvChainSpeedAfter.getText().toString(),
					tvChainSpeed.getText().toString(),
					tvTinTemperature.getText().toString(),
					tvTinBathHeight.getText().toString(),
					tvTurbulentWave.getText().toString(),
					tvAdvectionWave.getText().toString(),
					tvTinBarType.getText().toString(),
					tvFluxType.getText().toString(),
					tvFluxFlow.getText().toString(),
					tvNozzleAirPressure.getText().toString(),
					tvTotalAirPressure.getText().toString(),
					tvTankAirPressure.getText().toString(),
					tvFluxProportion.getText().toString(),
					tvRemark.getText().toString(),
					""
					);
		}
		
	}
	
	
	
}
