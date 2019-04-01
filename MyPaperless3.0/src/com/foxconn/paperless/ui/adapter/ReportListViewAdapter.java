package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ReportInfo;
/**
 * 顯示報表信息的ListView適配器（報表名+報表編號）
 *@ClassName ReportListViewAdapter
 *@Author wunian
 *@Date 2017/11/3 上午9:44:40
 */
public class ReportListViewAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ReportInfo> reportInfoList;
	
	public ReportListViewAdapter(Context context,List<ReportInfo> reportInfoList){
		inflater=LayoutInflater.from(context);
		this.reportInfoList=reportInfoList;
	}
	
	@Override
	public int getCount() {
		return reportInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return reportInfoList.get(position);
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
			convertView=inflater.inflate(R.layout.listview_bureport_item, null);
			holder.tvReportNum=(TextView) convertView.findViewById(R.id.tvReportNum);
			holder.tvReportName=(TextView) convertView.findViewById(R.id.tvReportName);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvReportNum.setText(reportInfoList.get(position).getReportNum());
		holder.tvReportName.setText(reportInfoList.get(position).getReportName());
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvReportNum;
		TextView tvReportName;
	}

}
