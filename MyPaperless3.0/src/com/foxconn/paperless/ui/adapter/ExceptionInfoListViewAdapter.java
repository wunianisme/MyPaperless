package com.foxconn.paperless.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.ExceptionFeedback;
import com.foxconn.paperless.bean.ParamInfo;
import com.foxconn.paperless.constant.MyEnum.Exception;
import com.foxconn.paperless.constant.MyEnum.ParamManage;
import com.foxconn.paperless.main.function.presenter.ParameterManagePresenter;
import com.foxconn.paperless.ui.widget.ToastHelper;
/**
 * 異常管理異常信息ListView適配器
 *@ClassName ParamInfoListViewAdapter
 *@Author wunian
 *@Date 2018/1/10 下午4:40:10
 */
public class ExceptionInfoListViewAdapter extends BaseAdapter {
	private Context context;
	private List<ExceptionFeedback> exceptionList;
	private LayoutInflater inflater;
	private int type;
	
	public ExceptionInfoListViewAdapter(Context context,List<ExceptionFeedback> 
		exceptionList,int type){
		this.context=context;
		this.exceptionList=exceptionList;
		this.inflater=LayoutInflater.from(context);
		this.type=type;
	}

	@Override
	public int getCount() {
		return exceptionList.size();
	}

	@Override
	public Object getItem(int position) {
		return exceptionList.get(position);
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
			convertView=inflater.inflate(R.layout.listview_exceptioninfo_item, null);
			holder.tvRowNum=(TextView) convertView.findViewById(R.id.tvRowNum);
			holder.tvSequence=(TextView) convertView.findViewById(R.id.tvSequence);
			holder.tvReportName=(TextView) convertView.findViewById(R.id.tvReportName);
			holder.tvNameStr=(TextView) convertView.findViewById(R.id.tvNameStr);
			holder.tvName=(TextView) convertView.findViewById(R.id.tvName);
			holder.tvDealState=(TextView) convertView.findViewById(R.id.tvDealState);
			holder.tvContent=(TextView) convertView.findViewById(R.id.tvContent);
			holder.tvCommitTime=(TextView) convertView.findViewById(R.id.tvCommitTime);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ExceptionFeedback exception=exceptionList.get(position);
		if(position==0){
			holder.tvRowNum.setVisibility(View.VISIBLE);
			String rowCount=context.getResources().getString(R.string.queryrow)+exceptionList.size();
			holder.tvRowNum.setText(rowCount);
		}else{
			holder.tvRowNum.setVisibility(View.GONE);
		}
		holder.tvSequence.setText((position+1)+"");
		holder.tvReportName.setText(exception.getReportName());
		
		//String name=exception.get ();
		if(type==Exception.MYDEAL){//處理類型，顯示創建人
			//ToastHelper.showInfo(context, "deal", 0);
			holder.tvNameStr.setText(R.string.exception_from);
			holder.tvName.setText(exception.getFromUser());
		}
		else if(type==Exception.MYCREATE){//創建類型，顯示處理人
			//ToastHelper.showInfo(context, "create", 0);
			holder.tvNameStr.setText(R.string.exception_to);
			holder.tvName.setText(exception.getToUser());
		}
		String dealState=exception.getDealState();
		int green=context.getResources().getColor(R.color.green);
		int red=context.getResources().getColor(R.color.red);
		if(dealState.equals(Exception.DEALSTATE_REVIEW)){//待處理
			holder.tvDealState.setText(R.string.dealstate_review);
			holder.tvDealState.setTextColor(red);
		}
		else if(dealState.equals(Exception.DEALSTATE_OK)){//處理完成
			holder.tvDealState.setText(R.string.dealstate_ok);
			holder.tvDealState.setTextColor(green);
		}
		else if(dealState.equals(Exception.DEALSTATE_REJECT)){//駁回
			holder.tvDealState.setText(R.string.dealstate_reject);
			holder.tvDealState.setTextColor(green);
		}
		holder.tvContent.setText(exception.getContent());
		holder.tvCommitTime.setText(exception.getCommitTime());
		return convertView;
	}
	
	private class ViewHolder{
		TextView tvRowNum;
		TextView tvSequence;
		TextView tvReportName;
		TextView tvNameStr;
		TextView tvName;
		TextView tvDealState;
		TextView tvContent;
		TextView tvCommitTime;
		
	}

}
