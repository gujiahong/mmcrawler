package com.mama100.crawler.framework.download;



public interface Downloadable<T> {
	/**
	 * 下载url
	 * @param url
	 * @return
	 */
	T download(String url);
	
}
