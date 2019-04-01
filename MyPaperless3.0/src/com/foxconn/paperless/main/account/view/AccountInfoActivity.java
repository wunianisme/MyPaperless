package com.foxconn.paperless.main.account.view;

import java.util.List;

import net.tsz.afinal.annotation.view.ViewInject;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.BaseActivity;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.main.account.presenter.AccountInfoPresenter;
import com.foxconn.paperless.main.account.presenter.AccountInfoPresenterImpl;
/**
 * 賬號信息
 *@ClassName AccountInfoActivity
 *@Author wunian
 *@Date 2017/10/12 上午10:31:55
 */
public class AccountInfoActivity extends BaseActivity implements AccountInfoView {

	@ViewInject(id=R.id.tvLeft,click="btnClick")TextView tvLeft;
	@ViewInject(id=R.id.tvTitle,click="btnClick")TextView tvTitle;
	@ViewInject(id=R.id.tvRight,click="btnClick")TextView tvRight;
	@ViewInject(id=R.id.svContainer)ScrollView svContainer;
	@ViewInject(id=R.id.tvLogonName)TextView tvLogonName;
	@ViewInject(id=R.id.etChineseName)EditText etChineseName;
	@ViewInject(id=R.id.etEnglishName)EditText etEnglishName;
	@ViewInject(id=R.id.etExt)EditText etExt;
	@ViewInject(id=R.id.etEmail)EditText etEmail;
	@ViewInject(id=R.id.spTeam)Spinner spTeam;
	@ViewInject(id=R.id.spSection)Spinner spSection;
	@ViewInject(id=R.id.spSBU)Spinner spSBU;
	@ViewInject(id=R.id.spMFG)Spinner spMFG;
	@ViewInject(id=R.id.ibTeamDrop,click="btnClick")ImageButton ibTeamDrop;
	@ViewInject(id=R.id.ibSectionDrop,click="btnClick")ImageButton ibSectionDrop;
	@ViewInject(id=R.id.ibSBUDrop,click="btnClick")ImageButton ibSBUDrop;
	@ViewInject(id=R.id.ibMFGDrop,click="btnClick")ImageButton ibMFGDrop;
	
	@ViewInject(id=R.id.tvJob)TextView tvJob;
	//@ViewInject(id=R.id.userLevelLayout)LinearLayout userLevelLayout;
	@ViewInject(id=R.id.tvUserLevel)TextView tvUserLevel;
	@ViewInject(id=R.id.tvMaster)TextView tvMaster;
	@ViewInject(id=R.id.tvBU)TextView tvBU;
	@ViewInject(id=R.id.tvSite)TextView tvSite;
	@ViewInject(id=R.id.tvLastEditby)TextView tvLastEditby;
	@ViewInject(id=R.id.tvLastEditdt)TextView tvLastEditdt;
	
	private Context context;
	private AccountInfoPresenter accountInfoPresenter;
	private List<String> MFGList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountinfo);
		init();
		getAccountInfo();
		
	}
	
	@Override
	protected void init() {
		context=AccountInfoActivity.this;
		accountInfoPresenter=new AccountInfoPresenterImpl(this, context);
		tvTitle.setText(R.string.accountinfo_title);
		tvRight.setVisibility(View.VISIBLE);
		tvRight.setText(R.string.accountinfo_save);
		
		spMFG.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				accountInfoPresenter.notifySBUDataChanged(
						parent.getItemAtPosition(pos).toString());//通知SBU數據更新
				//ToastHelper.showInfo(context, "spMFG selected", 0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		spSBU.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				accountInfoPresenter.notifyTeamDataChanged(
						parent.getItemAtPosition(pos).toString(),
						spMFG.getSelectedItem().toString());//通知Team數據更新
				
				accountInfoPresenter.notifySectionDataChanged(
						parent.getItemAtPosition(pos).toString());//通知工站數據更新
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		spTeam.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long arg3) {}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		spSection.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long arg3) {}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
	}
	
	@Override
	public void getAccountInfo() {
		accountInfoPresenter.getAccountInfo();
	}
	
	public void btnClick(View v){
		switch (v.getId()) {
		case R.id.tvLeft:
			finish();
			break;
		case R.id.tvTitle:
			svContainer.scrollTo(0, 0);//點擊標題滑動到頂部
			break;
		case R.id.tvRight:
			accountInfoPresenter.saveAccountInfo(
					etChineseName.getText().toString(), 
					etEnglishName.getText().toString(), 
					etExt.getText().toString(), 
					etEmail.getText().toString(),
					spMFG.getSelectedItem().toString(), 
					spSBU.getSelectedItem().toString(), 
					spTeam.getSelectedItem().toString(), 
					spSection.getSelectedItem().toString());
			break;
		case R.id.ibTeamDrop:
			spTeam.performClick();
			break;
		case R.id.ibSectionDrop:
			spSection.performClick();
			break;
		case R.id.ibSBUDrop:
			spSBU.performClick();
			break;
		case R.id.ibMFGDrop:
			spMFG.performClick();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void setAccountBaseInfo(Euser user) {
		tvLogonName.setText(user.getLogonName());
		etChineseName.setText(user.getChineseName());
		etEnglishName.setText(user.getEnglishName());
		etExt.setText(user.getExt());
		etEmail.setText(user.getEmail());
		tvJob.setText(user.getTitle());
		tvUserLevel.setText(user.getUserlevel());
		tvMaster.setText(user.getMaster());
		tvBU.setText(user.getBu());
		tvSite.setText(user.getSite());
		tvLastEditby.setText(user.getLasteditby());
		tvLastEditdt.setText(user.getLasteditdt());
		//userLevelLayout.setVisibility(visibility);//根據賬號類型判斷是否顯示用戶等級，測試賬號才顯示
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
	public void setMFGAdapter(List<String> MFGList) {
		this.MFGList=MFGList;
		ArrayAdapter<String> MFGAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, MFGList);
		spMFG.setAdapter(MFGAdapter);
	}

	@Override
	public void setSBUAdapter(List<String> SBUList,int index) {
		ArrayAdapter<String> SBUAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, SBUList);
		spSBU.setAdapter(SBUAdapter);
		spSBU.setSelection(index, true);
		
	}

	@Override
	public void setTeamAdapter(List<String> TeamList,int index) {
		ArrayAdapter<String> teamAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, TeamList);
		spTeam.setAdapter(teamAdapter);
		spTeam.setSelection(index, true);
	}

	@Override
	public void setSectionAdapter(List<String> sectionList,int index) {
		ArrayAdapter<String> sectionAdapter=new ArrayAdapter<String>(context, R.layout.layout_spinner_item, sectionList);
		spSection.setAdapter(sectionAdapter);
		spSection.setSelection(index, true);
	}

	@Override
	public void setMFGSelection(int index) {
		spMFG.setSelection(index, true);//選中index索引位置的條目，由此可觸發OnItemSelected事件
		// update in 2018/2/27
		if(index==0){//當索引為0時，spMFG可能不會調用OnItemSelected事件，這時需要手動去更新spSBU適配器數據變化
			accountInfoPresenter.notifySBUDataChanged(
					MFGList.get(index));//通知SBU數據更新
		}
	}
}
