package com.foxconn.paperless.main.function.presenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.R.integer;
import android.content.Context;

import com.foxconn.paperless.activity.R;
import com.foxconn.paperless.base.OnModelListener;
import com.foxconn.paperless.bean.Euser;
import com.foxconn.paperless.bean.JsonResult;
import com.foxconn.paperless.bean.MTLineInfo;
import com.foxconn.paperless.bean.Params;
import com.foxconn.paperless.constant.MyAction.Action;
import com.foxconn.paperless.helper.MTInadvancePresenterHelper;
import com.foxconn.paperless.http.ParamsUtil;
import com.foxconn.paperless.main.function.model.MTInadvanceModel;
import com.foxconn.paperless.main.function.model.MTInadvanceModelImpl;
import com.foxconn.paperless.main.function.view.MTInadvanceByLineView;
import com.foxconn.paperless.ui.widget.ToastHelper;
import com.foxconn.paperless.util.TextUtil;

public class MTInadvanceByLinePresenterImpl implements
		MTInadvanceByLinePresenter,OnModelListener {

	private MTInadvanceByLineView view;
	private Context context;
	private Euser user;
	private MTInadvanceModel model;
	private Params params;
	private List<String> floorNameList;//樓層列表
	private List<MTLineInfo> lineInfoList;//纖體信息列表
	private MTLineInfo lineInfo;//用戶選中的纖體信息
	private String floorName;
	private String shift;//班别
	private int[] date;//日期數組
	
	public final static String ALLDAY="day_night";//整天
	public final static String Day="day";//白班
	public final static String Night="night";//晚班
	
	public MTInadvanceByLinePresenterImpl(MTInadvanceByLineView view,Context context){
		this.view=view;
		this.context=context;
		this.user=(Euser) context.getApplicationContext();
		this.model=new MTInadvanceModelImpl(this);
		this.params=new Params(model);
		this.floorNameList=new ArrayList<String>();
		this.lineInfoList=new ArrayList<MTLineInfo>();
	}

	@Override
	public void success(JsonResult result) {
		view.dismissLoading();
		if(result.getAction().equals(Action.GET_MT_FLOOR)){
			floorNameList=result.getData();
			int titleId=R.string.select_floor;
			view.showSelectFloorDialog(titleId,floorNameList);
		}
		if(result.getAction().equals(Action.GET_MT_LINE)){
			lineInfoList=MTInadvancePresenterHelper.getLineInfoList(result);
			view.setLineInfoAdapter(lineInfoList);
		}
		if(result.getAction().equals(Action.SAVE_MT)){//維護成功
			view.showToastSuccessMsg(R.string.mt_success);
			view.back();
		}
	}

	@Override
	public void failed(JsonResult result) {
		view.dismissLoading();
		if(result.getAction().equals(Action.GET_MT_FLOOR)){
			//沒有獲取到樓層信息
			view.showToastFailedMsg(R.string.getfloor_failed);
		}
		if(result.getAction().equals(Action.GET_MT_LINE)){
			//沒有獲取到纖體信息
			view.showToastFailedMsg(R.string.getline_failed);
		}
		if(result.getAction().equals(Action.SAVE_MT)){//維護失敗
			view.showToastFailedMsg(R.string.mt_failed);
		}
	}

	@Override
	public void exception(JsonResult result) {
		view.showToastExceptionMsg(result.getResultMsg());
	}
	/**
	 * 初始化
	 */
	@Override
	public void init() {
		//獲取樓層信息
		params=ParamsUtil.getParam(params, Action.GET_MT_FLOOR,
				new String[]{
					user.getBu(),
					user.getMfg()
		});
		model.getMTFloor(params);
		view.showLoading();
	}
	/**
	 * 獲得用戶選擇的樓層
	 */
	@Override
	public void setFloor(int position) {
		floorName=floorNameList.get(position);
	}

	@Override
	public void getLineName() {
		if(TextUtil.isEmpty(floorName)){//floorName是否為“”或null
			//樓層信息不存在不能繼續執行
			view.showToastFailedMsg(R.string.floor_notexist);
			return;
		}
		//獲取纖體信息
		params=ParamsUtil.getParam(params, Action.GET_MT_LINE,
				new String[]{
					floorName,
					user.getMfg()
		});
		model.getMTLine(params);
		view.showLoading();
	}
	/**
	 * 獲得當前選中的樓層
	 */
	@Override
	public String getFloor() {
		return floorName;
	}
	/**
	 * 設置當前選中的線別信息
	 */
	@Override
	public void setLineInfo(int index) {
		lineInfo=lineInfoList.get(index);
	}
	/**
	 * 選擇班別
	 */
	@Override
	public void selectShift() {
		String[] shiftArray=context.getResources().getStringArray(R.array.mtShift_array);
		String title=context.getResources().getString(R.string.select_shift)+
				lineInfo.getLineName();
		view.showSelectShiftDialog(title,shiftArray);
	}
	/**
	 * 設置班別
	 */
	@Override
	public void setShift(int position) {
		switch (position) {
		case 0://整天
			shift=ALLDAY;
			break;
		case 1://白班
			shift=Day;
			break;
		case 2://晚班
			shift=Night;
			break;
		default:
			break;
		}
		//ToastHelper.showInfo(context, shift, 0);
	}
	/**
	 * 選擇日期
	 */
	@Override
	public void selectDate() {
		int titleId=R.string.select_mt_date;
		date=TextUtil.getdate();
		view.showSelectDateDialog(titleId,date);
	}
	/**
	 * 設置日期
	 */
	@Override
	public void setDate(int year, int month, int d) {
		date[0]=year;
		date[1]=month+1;
		date[2]=d;
		//ToastHelper.showInfo(context, year+"/"+date[1]+"/"+d, 0);
	}
	/**
	 * 提交提前維護信息
	 */
	@Override
	public void submitMTInadvance() {
		boolean canMT=judgeTime();//判斷維護時間是否合理
		if(!canMT){//不允許維護，選擇日期不符合要求
			view.showToastFailedMsg(R.string.mt_notpermission);
			return;
		}
		//'Night','LH','NSDI','MFGI','D9 3F','D93FS1B','','2016/12/07','admin'
		params=ParamsUtil.getParam(params, Action.SAVE_MT, new String[]{
				shift,
				user.getSite(),
				user.getBu(),
				user.getMfg(),
				floorName,
				lineInfo.getLineName(),
				date[0]+"/"+date[1]+"/"+date[2],
				user.getLogonName()
		});
		model.saveMT(params);
		view.showLoading();
	}
	/**
	 * 判斷維護時間是否合理 true:合理
	 * @return
	 */
	private boolean judgeTime(){
		int year=date[0];
		int month=date[1];
		int day=date[2];
		int nowHour=TextUtil.getTime()[0];//用戶現在操作的時間
		Calendar calendar=Calendar.getInstance();
		int nowMonth=calendar.get(Calendar.MONTH)+1;//當前月份
		//ToastHelper.showInfo(context,nowMonth+"月", 0);
		if(year<calendar.get(Calendar.YEAR)){
			return false;
		}else if(year==calendar.get(Calendar.YEAR)){
			if(month<nowMonth){
				return false;
			}else if(month==nowMonth){
				if(day<calendar.get(Calendar.DAY_OF_MONTH)){
					return false;
				}else if(day==calendar.get(Calendar.DAY_OF_MONTH)){
				//只有晚班可以在當天晚上八點以前進行維護，其餘只能維護隔天以後
					if(nowHour<20&&shift.equalsIgnoreCase(Night)){
						return true;
					}else{
						return false;
					}
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
	}
}
