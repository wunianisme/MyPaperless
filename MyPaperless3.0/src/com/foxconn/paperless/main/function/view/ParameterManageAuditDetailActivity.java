package com.foxconn.paperless.main.function.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.constant.MyEnum.ReportNum;
import com.foxconn.paperless.main.function.presenter.ParameterManageAuditDetailPresenter;
import com.foxconn.paperless.main.function.presenter.ParameterManageAuditDetailPresenterImpl;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 參數簽核詳細信息
 *@ClassName ParameterManageAuditDetailActivity
 *@Author wunian
 *@Date 2018/1/15 下午1:57:25
 */
public class ParameterManageAuditDetailActivity extends BaseActivity implements
		ParameterManageAuditDetailView {
	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle)TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	//基本
	@ViewInject(id=R.id.MFGLayout)LinearLayout MFGLayout;
	@ViewInject(id=R.id.BULayout)LinearLayout BULayout;
	@ViewInject(id=R.id.tvMFG)TextView tvMFG;
	@ViewInject(id=R.id.tvBU)TextView tvBU;
	@ViewInject(id=R.id.tvFloorName)TextView tvFloorName;
	@ViewInject(id=R.id.tvLineName)TextView tvLineName;
	@ViewInject(id=R.id.tvSkuNo)TextView tvSkuNo;
	@ViewInject(id=R.id.sideLayout)LinearLayout sideLayout;
	@ViewInject(id=R.id.tvSide)TextView tvSide;
	@ViewInject(id=R.id.problemDescLayout)LinearLayout problemDescLayout;
	@ViewInject(id=R.id.tvProblemDesc)TextView tvProblemDesc;
	@ViewInject(id=R.id.tvUpdateType)TextView tvUpdateType;
	@ViewInject(id=R.id.tvCheckState)TextView tvCheckState;
	//修改信息
	@ViewInject(id=R.id.checkLayout)LinearLayout checkLayout;
	@ViewInject(id=R.id.createLayout)LinearLayout createLayout;
	@ViewInject(id=R.id.tvCheckDate)TextView tvCheckDate;
	@ViewInject(id=R.id.tvCheckBy)TextView tvCheckBy;
	@ViewInject(id=R.id.tvCreateDate)TextView tvCreateDate;
	@ViewInject(id=R.id.tvCreateBy)TextView tvCreateBy;
	
	//按鈕
	@ViewInject(id=R.id.btnLayout)LinearLayout btnLayout;
	@ViewInject(id=R.id.btnPass,click="btnClick")Button btnPass;
	@ViewInject(id=R.id.btnFailed,click="btnClick")Button btnFailed;
	//SMT印刷機
	@ViewInject(id=R.id.SMTPrinterLayout)LinearLayout SMTPrinterLayout;
	@ViewInject(id=R.id.tvScraperPressure)TextView tvScraperPressure;
	@ViewInject(id=R.id.tvPrintSpeed)TextView tvPrintSpeed;
	@ViewInject(id=R.id.tvSeparationSpeed)TextView tvSeparationSpeed;
	@ViewInject(id=R.id.tvSeparationDistance)TextView tvSeparationDistance;
	@ViewInject(id=R.id.tvScreenCleanRate)TextView tvScreenCleanRate;
	@ViewInject(id=R.id.tvSqueegeeLength)TextView tvSqueegeeLength;
	//SMT回焊爐
	@ViewInject(id=R.id.SMTReflowLayout)LinearLayout SMTReflowLayout;
	@ViewInject(id=R.id.tvZone1)TextView tvZone1;
	@ViewInject(id=R.id.tvZone2)TextView tvZone2;
	@ViewInject(id=R.id.tvZone3)TextView tvZone3;
	@ViewInject(id=R.id.tvZone4)TextView tvZone4;
	@ViewInject(id=R.id.tvZone5)TextView tvZone5;
	@ViewInject(id=R.id.tvZone6)TextView tvZone6;
	@ViewInject(id=R.id.tvZone7)TextView tvZone7;
	@ViewInject(id=R.id.tvZone8)TextView tvZone8;
	@ViewInject(id=R.id.tvZone9)TextView tvZone9;
	@ViewInject(id=R.id.tvZone10)TextView tvZone10;
	@ViewInject(id=R.id.tvZone11)TextView tvZone11;
	@ViewInject(id=R.id.tvZone12)TextView tvZone12;
	@ViewInject(id=R.id.tvZone13)TextView tvZone13;
	@ViewInject(id=R.id.tvSpeed)TextView tvSpeed;
	@ViewInject(id=R.id.tvFanSpeed)TextView tvFanSpeed;
	@ViewInject(id=R.id.tvShuntValue)TextView tvShuntValue;
	//波峰焊
	@ViewInject(id=R.id.PTHWSLayout)LinearLayout PTHWSLayout;
	@ViewInject(id=R.id.remarkLayout)LinearLayout remarkLayout;
	@ViewInject(id=R.id.tvModelName)TextView tvModelName;
	@ViewInject(id=R.id.tvBOTPreheatI)TextView tvBOTPreheatI;
	@ViewInject(id=R.id.tvBOTPreheatII)TextView tvBOTPreheatII;
	@ViewInject(id=R.id.tvBOTPreheatIII)TextView tvBOTPreheatIII;
	@ViewInject(id=R.id.tvBOTPreheatIV)TextView tvBOTPreheatIV;
	@ViewInject(id=R.id.tvTopPreheatI)TextView tvTopPreheatI;
	@ViewInject(id=R.id.tvTopPreheatII)TextView tvTopPreheatII;
	@ViewInject(id=R.id.tvTopPreheatIII)TextView tvTopPreheatIII;
	@ViewInject(id=R.id.tvTopPreheatIV)TextView tvTopPreheatIV;
	@ViewInject(id=R.id.tvChainSpeedAfter)TextView tvChainSpeedAfter;
	@ViewInject(id=R.id.tvChainSpeed)TextView tvChainSpeed;
	@ViewInject(id=R.id.tvTinTemperature)TextView tvTinTemperature;
	@ViewInject(id=R.id.tvTinBathHeight)TextView tvTinBathHeight;
	@ViewInject(id=R.id.tvTurbulentWave)TextView tvTurbulentWave;
	@ViewInject(id=R.id.tvAdvectionWave)TextView tvAdvectionWave;
	@ViewInject(id=R.id.tvTinBarType)TextView tvTinBarType;
	@ViewInject(id=R.id.tvFluxType)TextView tvFluxType;
	@ViewInject(id=R.id.tvFluxFlow)TextView tvFluxFlow;
	@ViewInject(id=R.id.tvNozzleAirPressure)TextView tvNozzleAirPressure;
	@ViewInject(id=R.id.tvTotalAirPressure)TextView tvTotalAirPressure;
	@ViewInject(id=R.id.tvTankAirPressure)TextView tvTankAirPressure;
	@ViewInject(id=R.id.tvFluxProportion)TextView tvFluxProportion;
	@ViewInject(id=R.id.tvRemark)TextView tvRemark;
	
	
	private Context context;
	private ParameterManageAuditDetailPresenter presenter;
	private String reportNum;
	private int type;
	private ParamInfo paramInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parametermanageauditdetail);
		init();
	}
	
	@Override
	protected void init() {
		context=ParameterManageAuditDetailActivity.this;
		presenter=new ParameterManageAuditDetailPresenterImpl(this, context);
		tvTitle.setText(R.string.paramauditdetail_title);
		Bundle bundle =getIntent().getExtras();
		reportNum=bundle.getString("reportNum");
		type=bundle.getInt("type");
		paramInfo=(ParamInfo) bundle.getSerializable("paramInfo");
		presenter.init(reportNum,type,paramInfo);
		presenter.getAuditDetailInfo();
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
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.btnPass:
//			if(reportNum.equals(ReportNum.SMT_PRINTER)){
//				presenter.passSMTPrinter();
//			}
//			if(reportNum.equals(ReportNum.SMT_REFLOW)){
//				presenter.passSMTReflow();
//			}
//			if(reportNum.equals(ReportNum.PTH_WS)){
//				presenter.passPTHWS();
//			}
			showPassDialog();
			break;
		case R.id.btnFailed:
			showFailedDialog();
			break;

		default:
			break;
		}
	}
	
	private void showPassDialog() {
		final CustomerDialog dialog=new CustomerDialog(context, R.string.paramaudit_tip,
				R.string.paramaudit_passmsg, false);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				presenter.pass();
			}
		});
		dialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
	}

	private void showFailedDialog() {
		final CustomerDialog dialog=new CustomerDialog(context, R.string.paramaudit_tip,
				R.string.paramaudit_failedmsg, false);
		dialog.setOKBtn(R.string.btn_ok, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				presenter.failed();
			}
		});
		dialog.setNoBtn(R.string.btn_no, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	

	@Override
	public void showSMTPrinterParam(List<String> paramList) {
		tvFloorName.setText(paramList.get(0));
		tvLineName.setText(paramList.get(1));
		tvSkuNo.setText(paramList.get(2));
		tvSide.setText(paramList.get(3));
		tvScraperPressure.setText(paramList.get(4));
		tvPrintSpeed.setText(paramList.get(5));
		tvSeparationSpeed.setText(paramList.get(6));
		tvSeparationDistance.setText(paramList.get(7));
		tvScreenCleanRate.setText(paramList.get(8));
		tvSqueegeeLength.setText(paramList.get(9));
		tvProblemDesc.setText(paramList.get(10));
		tvCreateDate.setText(paramList.get(11));
		tvCheckDate.setText(paramList.get(12));
		showCreateLayout();
		
	}

	@Override
	public void showSMTReflowParam(List<String> paramList) {
		tvFloorName.setText(paramList.get(0));
		tvLineName.setText(paramList.get(1));
		tvSkuNo.setText(paramList.get(2));
		tvSide.setText(paramList.get(3));
		tvZone1.setText(paramList.get(4));
		tvZone2.setText(paramList.get(5));
		tvZone3.setText(paramList.get(6));
		tvZone4.setText(paramList.get(7));
		tvZone5.setText(paramList.get(8));
		tvZone6.setText(paramList.get(9));
		tvZone7.setText(paramList.get(10));
		tvZone8.setText(paramList.get(11));
		tvZone9.setText(paramList.get(12));
		tvZone10.setText(paramList.get(13));
		tvZone11.setText(paramList.get(14));
		tvZone12.setText(paramList.get(15));
		tvZone13.setText(paramList.get(16));
		tvSpeed.setText(paramList.get(17));
		tvFanSpeed.setText(paramList.get(18));
		tvShuntValue.setText(paramList.get(19));
		tvProblemDesc.setText(paramList.get(20));
		tvCreateDate.setText(paramList.get(21));
		tvCheckDate.setText(paramList.get(22));
		showCreateLayout();
	}

	@Override
	public void showPTHWSParam(List<String> paramList) {
		MFGLayout.setVisibility(View.VISIBLE);
		BULayout.setVisibility(View.VISIBLE);
		sideLayout.setVisibility(View.GONE);
		remarkLayout.setVisibility(View.GONE);
		
		tvMFG.setText(paramList.get(0));
		tvBU.setText(paramList.get(1));
		tvFloorName.setText(paramList.get(2));
		tvLineName.setText(paramList.get(3));
		tvModelName.setText(paramList.get(4));
		tvSkuNo.setText(paramList.get(5));
		tvBOTPreheatI.setText(paramList.get(6));
		tvBOTPreheatII.setText(paramList.get(7));
		tvBOTPreheatIII.setText(paramList.get(8));
		tvBOTPreheatIV.setText(paramList.get(9));
		tvTopPreheatI.setText(paramList.get(10));
		tvTopPreheatII.setText(paramList.get(11));
		tvTopPreheatIII.setText(paramList.get(12));
		tvTopPreheatIV.setText(paramList.get(13));
		tvChainSpeedAfter.setText(paramList.get(14));
		tvChainSpeed.setText(paramList.get(15));
		tvTinTemperature.setText(paramList.get(16));
		tvTinBathHeight.setText(paramList.get(17));
		tvTurbulentWave.setText(paramList.get(18));
		tvAdvectionWave.setText(paramList.get(19));
		tvTinBarType.setText(paramList.get(20));
		tvFluxType.setText(paramList.get(21));
		tvFluxFlow.setText(paramList.get(22));
		tvNozzleAirPressure.setText(paramList.get(23));
		tvTotalAirPressure.setText(paramList.get(24));
		tvTankAirPressure.setText(paramList.get(25));
		tvFluxProportion.setText(paramList.get(26));
		tvProblemDesc.setText(paramList.get(27));
		tvCreateDate.setText(paramList.get(28));
		tvCheckDate.setText(paramList.get(29));
		showCreateLayout();
	}
	
	private void showCreateLayout(){
		if(type==ParamManage.AUDITTYPE_MYSUBMIT){//顯示簽核人信息
			tvCheckBy.setText(paramInfo.getCheckBy());
		}
		if(type==ParamManage.AUDITTYPE_MYCHECK){//顯示提交人信息
			createLayout.setVisibility(View.VISIBLE);
			checkLayout.setVisibility(View.GONE);
			tvCreateBy.setText(paramInfo.getCreateBy());
		}
	}

	@Override
	public void showCheckStatus(String updateType,String checkState) {
		tvUpdateType.setText(updateType);
		tvCheckState.setText(checkState);
	}

	@Override
	public void showBaseView(int SMTPrinterVisible, int SMTReflowVisible,
			int PTHWSVisible, int btnVisible) {
		SMTPrinterLayout.setVisibility(SMTPrinterVisible);
		SMTReflowLayout.setVisibility(SMTReflowVisible);
		PTHWSLayout.setVisibility(PTHWSVisible);
		btnLayout.setVisibility(btnVisible);
	}

	@Override
	public void back() {
		setResult(RESULT_OK);
		ParameterManageAuditDetailActivity.this.finish();
	}

}
