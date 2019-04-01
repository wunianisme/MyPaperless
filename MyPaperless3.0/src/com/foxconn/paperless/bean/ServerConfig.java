package com.foxconn.paperless.bean;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

import net.tsz.afinal.core.Arrays;

/**
 * 服务器配置信息
 *@ClassName ServerConfig
 *@Author wunian
 *@Date 2017/9/8 下午5:53:59
 */
public class ServerConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private String site;
	private String serverName;
	private String EnglishServerName;
	private String url;
	private String nameSpace;
	private String methodName;
	private String type;
	private String enable;
	private String editdt;
	
	public ServerConfig(){}
	
	public ServerConfig(String site, String serverName,String EnglishServerName,String url,
			String nameSpace, String methodName, String type, String enable,
			String editdt) {
		this.site = site;
		this.serverName = serverName;
		this.EnglishServerName=EnglishServerName;
		this.url = url;
		this.nameSpace = nameSpace;
		this.methodName = methodName;
		this.type = type;
		this.enable = enable;
		this.editdt = editdt;
	}
	
	public List<String> getServerConfig(){
		String[] data={
				site,
				serverName,
				EnglishServerName,
				url,
				nameSpace,
				methodName,
				type,
				enable,
				editdt};
		return Arrays.asList(data);
	}
	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	public String getEnglishServerName() {
		return EnglishServerName;
	}

	public void setEnglishServerName(String englishServerName) {
		EnglishServerName = englishServerName;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEnable() {
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
	public String getEditdt() {
		return editdt;
	}
	public void setEditdt(String editdt) {
		this.editdt = editdt;
	}

	@Override
	public String toString() {
		return "ServerConfig [site=" + site + ", serverName=" + serverName
				+ ", url=" + url + ", nameSpace=" + nameSpace + ", methodName="
				+ methodName + ", type=" + type + ", enable=" + enable
				+ ", editdt=" + editdt + "]";
	}
}
