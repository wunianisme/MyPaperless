package com.foxconn.server.util;

import java.util.List;

import com.foxconn.server.bean.JsonResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Gson工具类，用于bean和json字符串之间的相互转换
 *@ClassName GsonUtils
 *@Author wunian
 *@Date 2017/9/5 上午10:53:29
 */
public class GsonUtil {

	/**
	 * bean转换为json
	 * @param json
	 * @return JsonResult
	 */
	public static String beanToJson(Object obj){
		Gson gson=new Gson();
		//以下两种方式生成的json字符串是一样的
		//return gson.toJson(msg, Message.class);
		return gson.toJson(obj);
	}
	/**
	 * json转换为bean
	 * @param jsonStr json字符串
	 * @return JsonResult
	 */
	public static <T> T jsonToBean(String jsonStr,Class<T> cls){
		Gson gson=new Gson();
		return gson.fromJson(jsonStr, cls);
	}
	/**
	 * List转换为json
	 * @param list 集合
	 * @return json
	 */
	public static String listToJson(List<Object> list){
		Gson gson=new Gson();
		return gson.toJson(list);
	}
	/**
	 * json转换为List
	 * @param jsonStr json字符串
	 * @return list集合
	 */
	public static List<Object> jsonToList(String jsonStr){
		Gson gson=new Gson();
		return gson.fromJson(jsonStr, new TypeToken<List<Object>>(){}.getType());
	}
}

