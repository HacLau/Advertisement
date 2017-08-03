package com.qc.advertisement.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * 字符串的加密
	 * @param message
	 * @return 返回MD5加密后的字符串
	 * String
	 * 20172017-4-1上午10:28:23
	 * HacLau
	 */
	public static String Md5(String message){
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(message.getBytes());
			for (int i = 0; i < digest.length; i++) {
				int result = digest[i] & 0xff;
				String hexString = Integer.toHexString(result);
				if (hexString.length()<2) {
					sb.append("0");
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
