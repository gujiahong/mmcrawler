package com.mama100.crawler.framework.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BasePage {
	
	/**
	 * 临时存储下一页及当前页商品的url
	 */
	private List<String> urlList = new CopyOnWriteArrayList<String>();
	
	/**
	 * 存储页面的基本信息
	 */
	private Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 原始url
	 */
	private String url;
	
	/**
	 * 原始页面内容
	 */
	private String content;
	
	/**
	 * 原始页面TITLE
	 * @return
	 */
	private String pageTitle;
	
	/**
	 * 批次号batchId
	 * @return
	 */
	private String batchId;
	
	/**
	 * 是否为种子url
	 * @return
	 */
	private boolean isSeedUrl = false;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void addField(String key ,String value){
		this.map.put(key, value);
	}

	public List<String> getUrlList() {
		return urlList;
	}
	
	public void addUrl(String url){
		this.urlList.add(url);
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public boolean isSeedUrl() {
		return isSeedUrl;
	}

	public void setSeedUrl(boolean isSeedUrl) {
		this.isSeedUrl = isSeedUrl;
	}
	
}
