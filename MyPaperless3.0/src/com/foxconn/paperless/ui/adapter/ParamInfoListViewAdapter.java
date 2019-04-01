package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.main.function.presenter.ParameterManagePresenter;
/**
 * 參數管理基本信息ListView適配器
 *@ClassName ParamInfoListViewAdapter
 *@Author wunian
 *@Date 2018/1/10 下午4:40:10
 */
public class ParamInfoListViewAdapter extends BaseAdapter {
	private Context context;
	private List<ParamInfo> paramInfoList;
	private LayoutInflater inflater;
	
	public ParamInfoListViewAdapter(Context context,List<ParamInfo> paramInfoList){
		this.context=context;
		this.paramInfoList=paramInfoList;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return paramInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return paramInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_param_item, null);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			holder.tvSequence=(TextView) convertView.findViewById(R.id.tvSequence);
			holder.tvSkuNo=(TextView) convertView.findViewById(R.id.tvSkuNo);
			holder.tvFloorName=(TextView) convertView.findViewById(R.id.tvFloorName);
			holder.tvLastEditDate=(TextView) convertView.findViewById(R.id.tvLastEditDate);
			holder.tvLineName=(TextView) convertView.findViewById(R.id.tvLineName);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ParamInfo paramInfo=paramInfoList.get(position);
		if(position==0){
			holder.tvRowNum.setVisibility(View.VISIBLE);
			String rowCount=context.getResources().getString(R.string.queryrow)+paramInfoList.size();
			holder.tvRowNum.setText(rowCount);
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		holder.tvSequence.setText((position+1)+"");
		holder.tvSkuNo.setText(paramInfo.getProductName());
		holder.tvFloorName.setText(paramInfo.getBuilding());
		holder.tvLineName.setText(paramInfo.getLine());
		holder.tvLastEditDate.setText(paramInfo.getLastEditDate());
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvRowNum;
		TextView tvSequence;
		TextView tvSkuNo;
		TextView tvFloorName;
		TextView tvLastEditDate;
		TextView tvLineName;
		
	}

}
