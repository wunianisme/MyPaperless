package com.foxconn.paperless.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.activity.R.color;
import com.foxconn.paperless.bean.CheckSearch;
import com.foxconn.paperless.main.function.view.CheckSearchActivity;

import android.R.drawable;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.VpnService;
import android.provider.CalendarContract.Colors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 點檢狀態信息列表的ListView適配器
 *@ClassName CheckSearchListViewAdapter
 *@Author wyb update wunian 
 *@Date 2017/12/28 下午3:26:39
 */
public class CheckSearchListViewAdapter extends BaseAdapter{

	private List<CheckSearch> list=new ArrayList<CheckSearch>();
	private LayoutInflater inflater;

	public CheckSearchListViewAdapter(Context context,List<CheckSearch> list){
		this.list=list;
		this.inflater=LayoutInflater.from(context);
	}

	public void getRefreshData() {
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_checksearch_item, null);
			holder.SearchNumber=(TextView)convertView.findViewById(R.id.SearchNumber);
			holder.reportName=(TextView)convertView.findViewById(R.id.reportName);
			holder.Equipment=(TextView)convertView.findViewById(R.id.Equipment);
			holder.LineName=(TextView)convertView.findViewById(R.id.LineName);
			holder.YieldName=(TextView)convertView.findViewById(R.id.YieldName);
			holder.ChineseName=(TextView)convertView.findViewById(R.id.ChineseName);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		holder.SearchNumber.setText((position+1)+"");
		holder.reportName.setText(list.get(position).getReportName());
		holder.Equipment.setText(list.get(position).getEquipment());
		holder.LineName.setText(list.get(position).getLineName());
		holder.YieldName.setText(list.get(position).getYieldName());
		holder.ChineseName.setText(list.get(position).getChineseName());
		
		//改變未點檢item背景顏色
		if (list.get(position).getCheckFlag().equals("0")) {//未點檢 （紅色背景）
			holder.SearchNumber.setTextColor(Color.WHITE);
			holder.reportName.setTextColor(Color.WHITE);
			holder.Equipment.setTextColor(Color.WHITE);
			holder.LineName.setTextColor(Color.WHITE);
			holder.YieldName.setTextColor(Color.WHITE);
			holder.ChineseName.setTextColor(Color.WHITE);
			convertView.setBackgroundColor(Color.RED);
		} else {
			holder.SearchNumber.setTextColor(Color.BLACK);
			holder.reportName.setTextColor(Color.BLACK);
			holder.Equipment.setTextColor(Color.BLACK);
			holder.LineName.setTextColor(Color.BLACK);
			holder.YieldName.setTextColor(Color.BLACK);
			holder.ChineseName.setTextColor(Color.BLACK);
			convertView.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

	private class ViewHolder{
		TextView SearchNumber;
		TextView reportName;
		TextView Equipment;
		TextView LineName;
		TextView YieldName;
		TextView ChineseName;
	}
}

