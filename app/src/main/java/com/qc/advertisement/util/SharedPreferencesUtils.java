package com.qc.advertisement.util;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 
 * @author HacLau
 *
 */
public class SharedPreferencesUtils {
	private static SharedPreferences sp;
	
	/**
	 * 本地存储boolean值
	 * @param context
	 * @param key
	 * @param value
	 * void
	 * 20172017-4-1上午10:08:47
	 * HacLau
	 */
	public static void putBoolean(Context context, String key, boolean value){
		if(sp == null){
			sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	/**
	 * 获取本地存储的Boolean值
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 * boolean
	 * 20172017-4-1上午10:10:45
	 * HacLau
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue){
		if(sp == null){
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	
	/**
	 * 本地存储字符串
	 * @param context
	 * @param key
	 * @param value
	 * void
	 * 20172017-4-1上午10:10:51
	 * HacLau
	 */
	public static void putString(Context context, String key, String value){
		if(sp == null){
			sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取本地存储的字符串
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 * String
	 * 20172017-4-1上午10:10:54
	 * HacLau
	 */
	public static String getString(Context context, String key, String defValue){
		if(sp == null){
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
}
