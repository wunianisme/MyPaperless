package com.foxconn.paperless.bean;

import java.io.Serializable;
/**
 * 維護纖體信息的bean
 * @author F1333452
 *
 */
public class MTLineInfo implements Serializable {

	private String lineName;
	private String site;
	private String BU;
	private String MFG;
	
	public MTLineInfo(){}
	
	public MTLineInfo(String lineName, String site, String bU, String mFG) {
		this.lineName = lineName;
		this.site = site;
		BU = bU;
		MFG = mFG;
	}

	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getBU() {
		return BU;
	}
	public void setBU(String bU) {
		BU = bU;
	}
	public String getMFG() {
		return MFG;
	}
	public void setMFG(String mFG) {
		MFG = mFG;
	}
}
