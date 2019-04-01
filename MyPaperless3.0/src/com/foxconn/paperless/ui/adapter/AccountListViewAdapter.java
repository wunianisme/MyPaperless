package com.foxconn.paperless.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.login.presenter.LoginPresenter;
/**
 * 賬號下拉框的ListView適配器
 *@ClassName AccountListViewAdapter
 *@Author wunian
 *@Date 2017/9/20 上午10:57:28
 */
public class AccountListViewAdapter extends BaseAdapter {

	private Map<String,String> accountMap;//存儲賬號的鍵值對
	private List<String> accountList;//將賬號存儲到list集合（因為這裡必須要用索引來獲取值）
	private LayoutInflater inflater;
	private LoginPresenter loginPresenter;
	
	public AccountListViewAdapter(Context context,
			Map<String, String> accountMap,LoginPresenter loginPresenter) {
		this.accountMap = accountMap;
		this.inflater=LayoutInflater.from(context);
		this.loginPresenter=loginPresenter;
		accountList=new ArrayList<String>();
		setAccountList();
	}
	
	public void setAccountList(){
		if(accountList.size()>0) accountList.clear();
		for (String key:accountMap.keySet()) {
			accountList.add(key);
		}
	}

	@Override
	public int getCount() {
		return accountList.size();
	}

	@Override
	public Object getItem(int position) {
		return accountList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.listview_accountdrop_item, null);
			holder=new ViewHolder();
			holder.tvAccount=(TextView) convertView.findViewById(R.id.tvAccount);
			holder.ibDelete=(ImageButton) convertView.findViewById(R.id.ibDelete);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvAccount.setText(accountList.get(position).toString());
		//刪除保存賬號
		holder.ibDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginPresenter.deleteAccount(accountList.get(position).toString());
				accountList.remove(position);
				AccountListViewAdapter.this.notifyDataSetChanged();
				loginPresenter.updateAccountPopupWindow();//更新賬號下拉框
			}
		});
		//選擇賬號
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginPresenter.inputSelectAccount(
						accountList.get(position).toString(), 
						accountMap.get(accountList.get(position)));
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvAccount;
		ImageButton ibDelete;
	}
}
