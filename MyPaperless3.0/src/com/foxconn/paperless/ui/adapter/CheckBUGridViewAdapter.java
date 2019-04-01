package com.foxconn.paperless.ui.adapter;

import java.util.List;

import com.foxconn.paperless.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 點檢BU的GridView適配器
 *@ClassName CheckBUGridViewAdapter
 *@Author wunian
 *@Date 2017/11/1 上午11:22:55
 */
public class CheckBUGridViewAdapter extends BaseAdapter {

	private LayoutInflater inflater ;
	private List<String> BUList;
	
	public CheckBUGridViewAdapter(Context context,List<String> BUList){
		this.BUList=BUList;
		this.inflater=LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return BUList.size();
	}

	@Override
	public Object getItem(int position) {
		return BUList.get(position);
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
			convertView=inflater.inflate(R.layout.gridview_checkbu_item, null);
			holder.ivBU=(ImageView) convertView.findViewById(R.id.ivBU);
			holder.tvBU=(TextView) convertView.findViewById(R.id.tvBU);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvBU.setText(BUList.get(position));
		return convertView;
	}
	
	private class ViewHolder{
		ImageView ivBU;
		TextView tvBU;
	}

}
