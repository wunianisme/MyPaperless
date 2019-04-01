package com.foxconn.paperless.ui.adapter.audit;

import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.main.function.presenter.AuditBatchPresenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * 批量簽核ListView適配器
 * @author nwe
 *
 */
public class AuditBatchListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<CheckInfo> checkInfoList;
	private AuditBatchPresenter presenter;
	
	public AuditBatchListViewAdapter(Context context,List<CheckInfo> checkInfoList,
			AuditBatchPresenter presenter){
		this.context=context;
		this.checkInfoList=checkInfoList;
		this.presenter=presenter;
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
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_auditbatch_item, null);
			holder.tvSequence=(TextView) convertView.findViewById(R.id.tvSequence);
			holder.tvRNO=(TextView) convertView.findViewById(R.id.tvRNO);
			holder.tvBy=(TextView) convertView.findViewById(R.id.tvBy);
			holder.cbSelect=(CheckBox) convertView.findViewById(R.id.cbSelect);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.cbSelect.setTag(position);
		holder.tvSequence.setText(position+1+"");
		holder.tvRNO.setText(checkInfoList.get(position).getRNO());
		holder.tvBy.setText(checkInfoList.get(position).getCreateByName());
		if(checkInfoList.get(position).isChecked()){
			holder.cbSelect.setChecked(true);
		}else{
			holder.cbSelect.setChecked(false);
		}
		holder.cbSelect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int index=(Integer) holder.cbSelect.getTag();
				//選中或不選
				if(checkInfoList.get(index).isChecked()){//是選中狀態，點擊后設置不選
					checkInfoList.get(index).setChecked(false);
				}else{
					checkInfoList.get(index).setChecked(true);
				}
			}
		});
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvSequence;
		TextView tvRNO;
		TextView tvBy;
		CheckBox cbSelect;
	}

	public List<CheckInfo> getCheckInfoList() {
		return checkInfoList;
	}

	public void setCheckInfoList(List<CheckInfo> checkInfoList) {
		this.checkInfoList = checkInfoList;
	}
}
