package com.mama100.crawler.framework.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mama100.crawler.framework.config.Config;

public class PageUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PageUtils.class);
	
	/**
	 * 获取页面内容
	 * @param url
	 * @return
	 */
	public static String getContent(String url){
		String content = "";
		HttpClientBuilder builder = HttpClients.custom();
		// 模拟浏览器agent
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		// 开启代理模式
		if(Config.isProxy){
			//TODO 
			//从代理地址库中随机取
			String ip = "219.135.252.41";
			int port = 8090;
			HttpHost proxy = new HttpHost(ip, port);
			builder.setProxy(proxy);
			logger.info("页面下载使用代理IP:{},代理port:{}",ip,port);
		}
		CloseableHttpClient client = builder.build();
		HttpGet request = new HttpGet(url);
		try {
			long start_time = System.currentTimeMillis();
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			// httpclient4 中文乱码问题 
			// http://sb122k.iteye.com/blog/1584395
			content = EntityUtils.toString(entity,"utf-8");  
			if(Config.isDebug)
				logger.info("页面下载成功:{},消耗时间:{}",url,System.currentTimeMillis()-start_time);
		} catch (Exception e) {
			logger.error("页面下载失败:{}",url);
			e.printStackTrace();
		}
		return content;
	}

}
