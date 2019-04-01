package com.foxconn.paperless.ui.adapter;

import java.util.List;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.MTLineInfo;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.main.function.presenter.AuditBatchPresenter;
import com.foxconn.paperless.main.function.presenter.MTInadvanceByLinePresenter;
import com.foxconn.paperless.main.function.presenter.MTInadvancePresenter;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 提前維護纖體信息ListView適配器
 * @author nwe
 *
 */
public class MTLineInfoListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private List<MTLineInfo> lineInfoList;
	private MTInadvanceByLinePresenter presenter;
	private int preItemIndex=-1;
	
	public MTLineInfoListViewAdapter(Context context,List<MTLineInfo> lineInfoList,
			MTInadvanceByLinePresenter presenter){
		this.context=context;
		this.lineInfoList=lineInfoList;
		this.presenter=presenter;
		inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return lineInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return lineInfoList.get(position);
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
			convertView=inflater.inflate(R.layout.listview_mtinadvancebyline_item, null);
			holder.tvFloorName=(TextView) convertView.findViewById(R.id.tvFloorName);
			holder.tvLineName=(TextView) convertView.findViewById(R.id.tvLineName);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvFloorName.setText(presenter.getFloor());
		holder.tvLineName.setText(lineInfoList.get(position).getLineName());
		return convertView;
	}
	
	private class ViewHolder{
		LinearLayout lineInfoLayout;
		TextView tvFloorName;
		TextView tvLineName;
	}

	public List<MTLineInfo> getCheckInfoList() {
		return lineInfoList;
	}

	public void setCheckInfoList(List<MTLineInfo> lineInfoList) {
		this.lineInfoList = lineInfoList;
	}
}
