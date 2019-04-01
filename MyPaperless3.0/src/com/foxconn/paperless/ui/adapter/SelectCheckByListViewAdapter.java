package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.main.check.presenter.ReportCheckPresenter;
/**
 * 選擇簽核人的ListView適配器(可選簽核人、已選簽核人)
 *@ClassName SelectCheckByListViewAdapter
 *@Author wunian
 *@Date 2017/12/16 下午3:21:49
 */
public class SelectCheckByListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Euser> checkByList;
	private LayoutInflater inflater;
	private ReportCheckPresenter reportCheckPresenter;
	private int showDelete;//是否顯示刪除按鈕
	
	public final int HIDE_DELETE=0;
	public final int SHOW_DELETE=1;
	public final int HIDE_ALL=2;

	public SelectCheckByListViewAdapter(Context context,
			List<Euser> checkByList, ReportCheckPresenter reportCheckPresenter,
			int showDelete) {
		this.context = context;
		this.checkByList = checkByList;
		this.reportCheckPresenter = reportCheckPresenter;
		this.showDelete=showDelete;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return checkByList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return checkByList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_checkby_item, null);
			holder.tvLogonName=(TextView) convertView.findViewById(R.id.tvlogonName);
			holder.tvTeam=(TextView) convertView.findViewById(R.id.tvTeam);
			holder.tvAdd=(TextView) convertView.findViewById(R.id.tvAdd);
			holder.tvDelete=(TextView) convertView.findViewById(R.id.tvDelete);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvAdd.setTag(pos);
		holder.tvDelete.setTag(pos);
		Euser checkby=checkByList.get(pos);
		holder.tvLogonName.setText(checkby.getChineseName()+"（"+checkby.getLogonName()+"）");
		holder.tvTeam.setText(checkby.getTeam());
		//顯示刪除控件,隱藏添加控件
		if(showDelete==SHOW_DELETE){
			holder.tvDelete.setVisibility(View.VISIBLE);
			holder.tvAdd.setVisibility(View.GONE);
			holder.tvDelete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					int tag=(Integer) holder.tvDelete.getTag();
					reportCheckPresenter.deleteCheckBy(tag);
				}
			});
			
		}else if(showDelete==HIDE_DELETE){//隱藏Delete,顯示add
			holder.tvDelete.setVisibility(View.GONE);
			holder.tvAdd.setVisibility(View.VISIBLE);
			holder.tvAdd.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int tag=(Integer) holder.tvAdd.getTag();
					reportCheckPresenter.addCheckBy(tag);
				}
			});
		}else{//隱藏Delete和 add
			holder.tvDelete.setVisibility(View.GONE);
			holder.tvAdd.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvLogonName;
		TextView tvTeam;
		TextView tvAdd;
		TextView tvDelete; 
	}

}
