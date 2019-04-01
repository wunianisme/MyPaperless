package com.foxconn.paperless.ui.adapter.audit;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.bean.audit.CheckInfo;
import com.foxconn.paperless.main.function.presenter.AuditBaseInfoPresenter;
import com.foxconn.paperless.main.function.view.AuditCheckResultActivity;
import com.foxconn.paperless.ui.widget.ToastHelper;
/**
 * 簽核基本信息ListView的適配器
 *@ClassName AuditBaseInfoListViewAdapter
 *@Author wunian
 *@Date 2018/1/4 上午9:05:56
 */
public class AuditBaseInfoListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<CheckInfo> checkInfoList;
	private Context context;
	private AuditBaseInfoPresenter auditBaseInfoPresenter;
	private String checkType;
	
	public AuditBaseInfoListViewAdapter(Context context,List<CheckInfo> checkInfoList,
			AuditBaseInfoPresenter auditBaseInfoPresenter,String checkType){
		inflater=LayoutInflater.from(context);
		this.checkInfoList=checkInfoList;
		this.context=context;
		this.auditBaseInfoPresenter=auditBaseInfoPresenter;
		this.checkType=checkType;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.listview_auditbaseinfo_item, null);
			holder.tvCheckId=(TextView) convertView.findViewById(R.id.tvCheckId);
			holder.tvRNO=(TextView) convertView.findViewById(R.id.tvRNO);
			holder.tvCheckBy=(TextView) convertView.findViewById(R.id.tvCheckBy);
			holder.tvCheckStatus=(TextView) convertView.findViewById(R.id.tvCheckStatus);
			holder.tvCheckInfo=(TextView) convertView.findViewById(R.id.tvCheckInfo);
			holder.ibBaseInfoDropdown=(ImageButton) convertView.findViewById(R.id.ibBaseInfoDropdown);
			holder.auditStatusLayout=(LinearLayout) convertView.findViewById(R.id.auditStatusLayout);
			holder.baseInfoLayout=(LinearLayout) convertView.findViewById(R.id.baseInfoLayout);
			//基本信息
			holder.tvReportName=(TextView) convertView.findViewById(R.id.tvReportName);
			holder.tvReportNum=(TextView) convertView.findViewById(R.id.tvReportNum);
			holder.tvMFG=(TextView) convertView.findViewById(R.id.tvMFG);
			holder.tvFloorName=(TextView) convertView.findViewById(R.id.tvFloorName);
			holder.tvWO=(TextView) convertView.findViewById(R.id.tvWO);
			holder.tvLineName=(TextView) convertView.findViewById(R.id.tvLineName);
			holder.tvSkuNo=(TextView) convertView.findViewById(R.id.tvSkuNo);
			holder.tvQty=(TextView) convertView.findViewById(R.id.tvQty);
			holder.tvDeviation=(TextView) convertView.findViewById(R.id.tvDeviation);
			holder.tvSide=(TextView) convertView.findViewById(R.id.tvSide);
			holder.tvRemark=(TextView) convertView.findViewById(R.id.tvRemark);
			holder.tvRemarkReason=(TextView) convertView.findViewById(R.id.tvRemarkReason);
			holder.tvCreateDate=(TextView) convertView.findViewById(R.id.tvCreateDate);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.baseInfoLayout.setTag(position);
		holder.tvCheckInfo.setTag(position);
		holder.tvCheckId.setText(checkInfoList.get(position).getCheckId());
		holder.tvRNO.setText(checkInfoList.get(position).getRNO());
		holder.tvCheckBy.setText(checkInfoList.get(position).getCheckByName());
		String checkStatus=checkInfoList.get(position).getCheckStatus();
		int red=context.getResources().getColor(R.color.red);
		if(checkStatus.equals("0")){//待簽核
			
			holder.tvCheckStatus.setText(R.string.audit_wait);
			holder.tvCheckStatus.setTextColor(red);
		}else if(checkStatus.equals("1")){//已簽核
			int green=context.getResources().getColor(R.color.green);
			holder.tvCheckStatus.setText(R.string.audit_ok);
			holder.tvCheckStatus.setTextColor(green);
		}else{// 拒簽（-1）
			holder.tvCheckStatus.setText(R.string.audit_reject);
			holder.tvCheckStatus.setTextColor(red);
		}
		//點擊按鈕彈出隱藏頁面
		holder.ibBaseInfoDropdown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (holder.baseInfoLayout.getVisibility()==View.GONE) {
					holder.baseInfoLayout.setVisibility(View.VISIBLE);
					setBaseInfo(holder, position);
				}else {
					holder.baseInfoLayout.setVisibility(View.GONE);
				}
			}
		});
		//點擊簽核狀態條目item彈出隱藏頁面
		holder.auditStatusLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (holder.baseInfoLayout.getVisibility()==View.GONE) {
					holder.baseInfoLayout.setVisibility(View.VISIBLE);
					setBaseInfo(holder, position);
				}else {
					holder.baseInfoLayout.setVisibility(View.GONE);
				}
			}
		});
		final String status=holder.tvCheckStatus.getText().toString();
		//點擊詳細信息item TextView跳轉到詳細點檢信息頁面
		holder.tvCheckInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int tag=(Integer) holder.tvCheckInfo.getTag();
				String checkId=checkInfoList.get(tag).getCheckId();//獲得點檢節次
				//ToastHelper.showInfo(context, "status:"+status+",checkId:"+checkId, 0);
				auditBaseInfoPresenter.goAuditCheckResultPage(tag,checkId,status);
			}
		});
		return convertView;
	}
	
	private void setBaseInfo(ViewHolder holder,int pos){
		holder.tvReportName.setText(checkInfoList.get(0).getReportName());
		holder.tvReportNum.setText(checkInfoList.get(0).getReportNum());
		holder.tvMFG.setText(checkInfoList.get(pos).getMFG());
		holder.tvFloorName.setText(checkInfoList.get(pos).getFloorName());
		holder.tvWO.setText(checkInfoList.get(pos).getWorkorderNo());
		holder.tvLineName.setText(checkInfoList.get(pos).getLineName());
		holder.tvSkuNo.setText(checkInfoList.get(pos).getSkuNo());
		holder.tvQty.setText(checkInfoList.get(pos).getQty());
		holder.tvDeviation.setText(checkInfoList.get(pos).getDeviation());
		holder.tvSide.setText(checkInfoList.get(pos).getSide());
		holder.tvRemark.setText(checkInfoList.get(pos).getCheckRemark());
		holder.tvRemarkReason.setText(checkInfoList.get(pos).getCheckRemarkReason());
		holder.tvCreateDate.setText(checkInfoList.get(pos).getCreateDate());
	}
	
	private class ViewHolder{
		TextView tvCheckId;
		TextView tvRNO;
		TextView tvCheckBy;
		TextView tvCheckStatus;
		TextView tvCheckInfo;
		ImageButton ibBaseInfoDropdown;
		LinearLayout auditStatusLayout,baseInfoLayout;
		TextView tvReportName;
		TextView tvReportNum;
		TextView tvMFG;
		TextView tvFloorName;
		TextView tvWO;
		TextView tvLineName;
		TextView tvSkuNo;
		TextView tvQty;
		TextView tvDeviation;
		TextView tvSide;
		TextView tvRemark;
		TextView tvRemarkReason;
		TextView tvCreateDate;
	}

}
