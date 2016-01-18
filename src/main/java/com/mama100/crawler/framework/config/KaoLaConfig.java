package com.mama100.crawler.framework.config;

import java.util.HashMap;
import java.util.Map;


public class KaoLaConfig extends Config {
	
	public static boolean isDebug = false;
	
	/* 种子url */
	public static String seedUrl = "http://www.kaola.com/";
	
	/* 值是否存储 */
	public static boolean isStore = true;
	
	/* 临时变量值  */
	public static Map<String,Map<String,String>> kaolaItemMap = new HashMap<String,Map<String,String>>();
	
}
