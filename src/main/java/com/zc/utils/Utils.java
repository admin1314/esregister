package com.zc.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String randomNumber(int length) {
		Random rand = new Random();
		String result = "";
		for (int i = 0; i < length; i++) {
			result += rand.nextInt(10);
		}
		return result;
	}

	public static String randomStr(int length) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += (char) (Math.random() * 26 + 'a');
		}
		return result;
	}

	public static Boolean strIsEmpty(String str) {
		if (str == null) {
			return true;
		} else if (str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean arrayIsEmpty(List<?> list) {
		if (list == null) {
			return true;
		} else if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static Boolean mapIsEmpty(Map<?, ?> map) {
		if (map == null) {
			return true;
		} else if (map.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String combinePath(String path, String name) {
		if (path.endsWith(File.separator)) {
			return path + name;
		} else {
			return path + File.separator + name;
		}
	}

	/**
	 * 正则匹配
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static Boolean match(String str, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			return matcher.matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String getOsType() {
		String os = System.getProperty("os.name");
		if (os.toLowerCase().startsWith("windows")) {
			return "windows";
		} else if (os.toLowerCase().startsWith("linux")) {
			return "linux";
		} else {
			return "unknown";
		}
	}

	public static Boolean isLinux() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.toLowerCase().startsWith("linux");
	}

	public static Boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.startsWith("windows");
	}

	/**
	 * * 对象转数组 * @param obj * @return
	 */
	public static byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	/**
	 * * 数组转对象 * @param bytes * @return
	 */
	public static Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	/**
	 * 去掉src前后的str字符串
	 * 
	 * @param src
	 * @param str
	 * @return
	 */
	public static String trim(String src, String str) {

		if (Utils.strIsEmpty(src) || Utils.strIsEmpty(str)) {
			return src;
		}
		if (src.length() < str.length()) {
			return src;
		}
		if (src.substring(0, str.length()).equals(str)) {
			src = src.substring(str.length(), src.length());
		}
		if (src.length() < str.length()) {
			return src;
		}
		if (src.substring(src.length() - str.length(), src.length()).equals(str)) {
			src = src.substring(0, src.length() - str.length());
		}
		return src;
	}

	public static void main(String[] args) {
		// System.out.println(trim("\"{\"XM\":\"ZHANGSAN\"}\"", "\""));
		// System.out.println(trim("aab", "aa"));
		System.out.println(randomStr(4));
	}

}
