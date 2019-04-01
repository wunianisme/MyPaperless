package com.foxconn.paperless.bean;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import android.graphics.Bitmap;

import com.foxconn.paperless.constant.MyEnum.Report;
import com.foxconn.paperless.constant.MyEnum.ReportConfig;
/**
 * 點檢項bean
 *@ClassName CheckItem
 *@Author wunian
 *@Date 2017/12/9 上午10:16:13
 */
@Table(name="CheckItem")
public class CheckItem implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String checkName;
	private String checkProName;
	private String proId;//點檢子項Id
	private String checkFlag;//點檢標記，通過工單帶出/手寫/帶出關鍵字
	private String takePhoto;
	
	private String checkContent;//輸入內容
	//最終保存到數據庫的點檢內容：標準參數+實際值（防止校驗過程中輸入內容改變而校驗失敗）
	private String saveContent;
	private String imageData;//拍的照片數據
	//private String icar;//checkResult表中的一個字段，在三個參數表中作為實際輸入值欄位保存
	private boolean isFirstProName;//是否是點檢項的第一個子項
	private String checkItemCount;//點檢項內的點檢子項個數（只在第一個子項中設置）
	private Bitmap takePhotoBitmap;//拍照圖標
	private String checkResult;//是否是點檢內容
	private String checked;//該點檢項是否配置在Check_Configure表中 (報表配置時使用) 1:是  2：不是
	//以下幾個參數用於FinalDB中存儲添加的欄位
	@Id
	private int id;
	private String reportNum;//判斷條件
	private String workorderNo;//工單
	private String RNO;//點檢編號用於查找上傳圖片
	
	
	public CheckItem(){
	//	icar=Report.DEFAULT_VALUE;
		imageData=Report.DEFAULT_VALUE;
		checkResult=Report.ISCHECKCONTENT;//默認是點檢內容
		checked=ReportConfig.NOCHECKED;//默認未配置點檢子項（報表配置）
	}
	
	public CheckItem(String checkName, String checkProName, String proId,
			String checkFlag, String takePhoto, String checkContent,
			String imageData, boolean isFirstProName) {
		this.checkName = checkName;
		this.checkProName = checkProName;
		this.proId = proId;
		this.checkFlag = checkFlag;
		this.takePhoto = takePhoto;
		this.checkContent = checkContent;
		this.imageData = imageData;
		this.isFirstProName = isFirstProName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckProName() {
		return checkProName;
	}
	public void setCheckProName(String checkProName) {
		this.checkProName = checkProName;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getCheckFlag() {
		return checkFlag;
	}
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
	public String getTakePhoto() {
		return takePhoto;
	}
	public void setTakePhoto(String takePhoto) {
		this.takePhoto = takePhoto;
	}
	public String getCheckContent() {
		return checkContent;
	}
	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
	public String getImageData() {
		return imageData;
	}
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	public boolean isFirstProName() {
		return isFirstProName;
	}
	public void setFirstProName(boolean isFirstProName) {
		this.isFirstProName = isFirstProName;
	}

	public String getCheckItemCount() {
		return checkItemCount;
	}

	public void setCheckItemCount(String checkItemCount) {
		this.checkItemCount = checkItemCount;
	}

	

	public Bitmap getTakePhotoBitmap() {
		return takePhotoBitmap;
	}

	public void setTakePhotoBitmap(Bitmap takePhotoBitmap) {
		this.takePhotoBitmap = takePhotoBitmap;
	}

	/*public String getIcar() {
		return icar;
	}

	public void setIcar(String icar) {
		this.icar = icar;
	}*/

	public String getSaveContent() {
		return saveContent;
	}

	public void setSaveContent(String saveContent) {
		this.saveContent = saveContent;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getWorkorderNo() {
		return workorderNo;
	}

	public void setWorkorderNo(String workorderNo) {
		this.workorderNo = workorderNo;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public String getRNO() {
		return RNO;
	}

	public void setRNO(String rNO) {
		RNO = rNO;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "CheckItem [checkName=" + checkName + ", checkProName="
				+ checkProName + ", proId=" + proId + ", checkFlag="
				+ checkFlag + ", takePhoto=" + takePhoto + ", checkContent="
				+ checkContent + ", saveContent=" + saveContent
				+ ", imageData=" + imageData + ", isFirstProName="
				+ isFirstProName + ", checkItemCount=" + checkItemCount
				+ ", takePhotoBitmap=" + takePhotoBitmap + ", checkResult="
				+ checkResult + ", checked=" + checked + ", id=" + id
				+ ", reportNum=" + reportNum + ", workorderNo=" + workorderNo
				+ ", RNO=" + RNO + "]";
	}
}
