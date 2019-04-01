package com.foxconn.paperless.ui.adapter.audit;

import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ReportInfo;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.bean.audit.CheckResult;
import com.foxconn.paperless.main.function.presenter.AuditSearchPresenter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 簽核查詢ListView適配器
 * @author nwe
 *
 */
public class AuditSearchListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<CheckInfo> checkInfoList;
	private AuditSearchPresenter auditSearchPresenter;
	private String checkType;
	
	public AuditSearchListViewAdapter(Context context,List<CheckInfo> checkInfoList,
			AuditSearchPresenter auditSearchPresenter,String checkType){
		this.context=context;
		this.checkInfoList=checkInfoList;
		this.auditSearchPresenter=auditSearchPresenter;
		this.checkType=checkType;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return checkInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return checkInfoList.get(position);
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
			convertView=inflater.inflate(R.layout.listview_auditsearch_item, null);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			holder.tvSequence=(TextView) convertView.findViewById(R.id.tvSequence);
			holder.tvRNO=(TextView) convertView.findViewById(R.id.tvRNO);
			holder.tvAnditStatus=(TextView) convertView.findViewById(R.id.tvAnditStatus);
			holder.tvCreateDate=(TextView) convertView.findViewById(R.id.tvCreateDate);
			holder.tvBy=(TextView) convertView.findViewById(R.id.tvBy);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if(position==0){//顯示記錄條數
			holder.tvRowNum.setVisibility(View.VISIBLE);
			String rowNum=context.getResources().getString(R.string.queryrow)
					+checkInfoList.size(); 
			holder.tvRowNum.setText(rowNum);
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		holder.tvSequence.setText(position+1+"");
		holder.tvRNO.setText(checkInfoList.get(position).getRNO());
		String checkStatus=checkInfoList.get(position).getCheckStatus();
		if(checkStatus.equals("0")){//待簽核(紅色字體)
			int red=context.getResources().getColor(R.color.red);
			holder.tvAnditStatus.setText(R.string.audit_wait);
			holder.tvAnditStatus.setTextColor(red);
		}else{//已簽核（綠色字體）
			int green=context.getResources().getColor(R.color.green);
			holder.tvAnditStatus.setText(R.string.audit_ok);
			holder.tvAnditStatus.setTextColor(green);
		}
		holder.tvCreateDate.setText(checkInfoList.get(position).getCreateDate());
		holder.tvBy.setText(checkInfoList.get(position).getCreateByName());
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvRowNum;
		TextView tvSequence;
		TextView tvRNO;
		TextView tvAnditStatus;
		TextView tvCreateDate;
		TextView tvBy;
	}

}
