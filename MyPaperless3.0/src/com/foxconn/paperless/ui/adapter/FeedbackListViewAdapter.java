package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.Feedback;
import com.foxconn.paperless.main.account.presenter.FeedbackPresenter;

public class FeedbackListViewAdapter extends BaseAdapter {
	private Context context;
	private List<Feedback> feedbackList;
	private LayoutInflater inflater;
	@SuppressWarnings("unused")
	private FeedbackPresenter feedbackPresenter;
	
	public FeedbackListViewAdapter(){}

	public FeedbackListViewAdapter(Context context,List<Feedback> feedbackList,
			 FeedbackPresenter feedbackPresenter) {
		this.context = context;
		this.feedbackList = feedbackList;
		this.feedbackPresenter = feedbackPresenter;
		this.inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return feedbackList.size();
	}

	@Override
	public Object getItem(int position) {
		return feedbackList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.listview_feedback_item, null);
			holder=new ViewHolder();
			holder.ivHead=(ImageView) convertView.findViewById(R.id.ivHead);
			holder.tvName=(TextView) convertView.findViewById(R.id.tvName);
			holder.tvFeedback=(TextView) convertView.findViewById(R.id.tvFeedback);
			holder.tvLasteditdt=(TextView) convertView.findViewById(R.id.tvLasteditdt);
			holder.tvSite=(TextView) convertView.findViewById(R.id.tvSite);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(feedbackList.get(position).getChineseName()
				+"（"+feedbackList.get(position).getLogonName()+"）");
		holder.tvFeedback.setText(feedbackList.get(position).getFeedback());
		holder.tvLasteditdt.setText(feedbackList.get(position).getLasteditdt());
		holder.tvSite.setText(feedbackList.get(position).getSite());
		if(position==feedbackList.size()-1){
			holder.tvRowNum.setVisibility(View.VISIBLE);
			holder.tvRowNum.setText(context.getResources().
					getString(R.string.totalrownum)+feedbackList.size());
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		return convertView;
	}
	
	private class ViewHolder{
		@SuppressWarnings("unused")
		ImageView ivHead;
		TextView tvName;
		TextView tvFeedback;
		TextView tvLasteditdt;
		TextView tvSite;
		TextView tvRowNum;
	}

}
