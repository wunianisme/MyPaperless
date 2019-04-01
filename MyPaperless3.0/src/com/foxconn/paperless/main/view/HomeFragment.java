package com.foxconn.paperless.main.view;

import java.util.List;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.main.check.view.BUReportActivity;
import com.foxconn.paperless.main.presenter.HomePresenter;
import com.foxconn.paperless.main.presenter.HomePresenterImpl;
import com.foxconn.paperless.ui.adapter.CheckBUGridViewAdapter;
import com.foxconn.paperless.ui.adapter.FunctionManageGridViewAdapter;
import com.foxconn.paperless.ui.widget.DialogHelper.CustomerDialog;
/**
 * 點檢首页
 *@ClassName CheckReportFragment
 *@Author wunian
 *@Date 2017/10/28 下午4:55:19
 */
public class HomeFragment extends Fragment implements HomeView{
	
	@ViewInject(id=R.id.gvBU)GridView gvBU;
	@ViewInject(id=R.id.tvNullTip)TextView tvNullTip;
	@ViewInject(id=R.id.gvManage)GridView gvManage;
	
	private MainActivity activity;
	private Context context;
	private HomePresenter presenter;
	private CheckBUGridViewAdapter checkBUAdapter;
	private FunctionManageGridViewAdapter adapter;
	private List<String> BUList;
	private CustomerDialog wayDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_check, container, false);
		FinalActivity.initInjectedView(this, view);
		init();
		return view;
	}

	public void init(){
		context=getActivity();
		presenter =new HomePresenterImpl(this, context);
		presenter.getAllBU();//獲得當前事業群和廠區下的所有BU
		presenter.setFunctionMenu();
		gvBU.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				presenter.goBUReportPage(BUList.get(pos));
			}
		});
		gvManage.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				presenter.goFunctionMenu(pos);
			}
		});
	}
	
	@Override
	public void setBUAdapter(List<String> BUList){
		this.BUList=BUList;
		checkBUAdapter=new CheckBUGridViewAdapter(context, BUList);
		gvBU.setAdapter(checkBUAdapter);
	}
	
	public void btnClick(View v){
		switch (v.getId()) {

		default:
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=(MainActivity) activity;
	}

	@Override
	public void showToastSuccessMsg(int strId) {
		activity.showToastSuccessMsg(strId);
		
	}

	@Override
	public void showToastFailedMsg(int strId) {
		activity.showToastFailedMsg(strId);
	}

	@Override
	public void showToastExceptionMsg(String msg) {
		activity.showToastExceptionMsg(msg);
	}

	@Override
	public void showLoading() {
		activity.showLoading();
	}

	@Override
	public void dismissLoading() {
		activity.dismissLoading();
	}

	@Override
	public void showNullReportTip() {
		tvNullTip.setVisibility(View.VISIBLE);
	}

	@Override
	public void gotoActivity(Class<?> class1, Bundle bundle) {
		Intent intent=new Intent(getActivity(),class1);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	@Override
	public void setFunctionMenuAdapter(int[] iconArray, int[] itemArray,int[] msgNumArray) {
		adapter=new FunctionManageGridViewAdapter(
						context, iconArray, itemArray,msgNumArray,presenter);
		gvManage.setAdapter(adapter);
	}

	@Override
	public void refreshFunctionMenuAdapter() {
		if(adapter!=null) adapter.notifyDataSetChanged();
	}

	@Override
	public void showSelectWayDialog(int titleId, String[] wayArray) {
		ArrayAdapter<String> wayAdapter=new ArrayAdapter<String>(context,
				android.R.layout.simple_list_item_1, wayArray);
		wayDialog=new CustomerDialog(context, titleId, wayAdapter, new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				wayDialog.dismiss();
				presenter.goMTInadvancePage(position);
			}
		}, true);
		
	}
}
