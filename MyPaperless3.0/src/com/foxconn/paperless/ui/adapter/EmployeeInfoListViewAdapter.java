package com.foxconn.paperless.ui.adapter;

import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.main.account.presenter.EmployeeInfoPresenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 員工信息列表的ListView適配器
 *@ClassName EmployeeInfoListViewAdapter
 *@Author wunian
 *@Date 2017/10/23 下午4:51:47
 */
public class EmployeeInfoListViewAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private List<Euser> employeeInfoList;//員工信息集合
	private EmployeeInfoPresenter employeeInfoPresenter;
	
	public EmployeeInfoListViewAdapter(Context context,List<Euser> employeeInfoList,
			EmployeeInfoPresenter employeeInfoPresenter){
		this.context=context;
		this.employeeInfoList=employeeInfoList;
		this.inflater=LayoutInflater.from(context);
		this.employeeInfoPresenter=employeeInfoPresenter;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return employeeInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return employeeInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.listview_employeeinfo_item, null);
			holder=new ViewHolder();
			holder.tvLogonName=(TextView) convertView.findViewById(R.id.tvLogonName);
			holder.tvName=(TextView) convertView.findViewById(R.id.tvName);
			holder.tvJob=(TextView) convertView.findViewById(R.id.tvJob);
			holder.tvMaster=(TextView) convertView.findViewById(R.id.tvMaster);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvLogonName.setText(employeeInfoList.get(position).getLogonName());
		holder.tvJob.setText(employeeInfoList.get(position).getTitle());
		holder.tvMaster.setText(employeeInfoList.get(position).getMaster());
		employeeInfoPresenter.setName(holder.tvName,employeeInfoList.get(position));//不同語言顯示不同名稱
		if(position==employeeInfoList.size()-1){
			holder.tvRowNum.setVisibility(View.VISIBLE);
			holder.tvRowNum.setText(context.getResources().
					getString(R.string.totalrownum)+employeeInfoList.size());//顯示記錄總數
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvLogonName;
		TextView tvName;
		TextView tvJob;
		TextView tvMaster;
		TextView tvRowNum;
	}
}
