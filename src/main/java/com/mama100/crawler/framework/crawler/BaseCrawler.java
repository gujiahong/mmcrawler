package com.mama100.crawler.framework.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mama100.crawler.framework.download.Downloadable;
import com.mama100.crawler.framework.process.Processable;
import com.mama100.crawler.framework.repository.Repository;
import com.mama100.crawler.framework.store.Storeable;
import com.mama100.crawler.framework.threadpool.ThreadPool;

public abstract class BaseCrawler<T> {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Downloadable<T> downloadable;
	protected Storeable<T> storeable;
	protected Repository repository;
	protected ThreadPool threadPool;
	protected Processable<T> processable;
	
	protected abstract BaseCrawler<T> start();
	
	protected BaseCrawler<T> withSeedUrl(String url){
		setSeedUrl(url);
		return this;
	}
	
	protected BaseCrawler<T> withDownload(Downloadable<T> t){
		setDownloadable(t);
		return this;
	}
	
	protected BaseCrawler<T> withStore(Storeable<T> t){
		setStoreable(t);
		return this;
	}
	
	protected BaseCrawler<T> withProcessable(Processable<T> t) {
		setProcessable(t);
		return this;
	}
	
	protected BaseCrawler<T> withThreadPool(ThreadPool threadPool) {
		setThreadPool(threadPool);
		return this;
	}
	
	protected BaseCrawler<T> withRepository(Repository repository) {
		setRepository(repository);
		return this;
	}
	
	/**
	 * 下载网页
	 * @param url
	 */
	protected T download(String url) {
		T t = this.downloadable.download(url);
		return t;
	}
	/**
	 * 解析网页内容
	 * @param T 
	 */
	protected void process(T t) {
		this.processable.process(t);
	}
	/**
	 * 保存网页内容
	 * @param T 
	 */
	protected void store(T t) {
		this.storeable.store(t);
	}
	/**
	 * 投放种子地址
	 * @param url 
	 */
	protected void setSeedUrl(String url){
		if(this.repository==null){
			String message = "先配置repository仓库，方法： withRepository().";
			logger.error(message);
			throw new RuntimeException(message);
		}else{
			this.repository.add(url);
		}
	}
	
	protected Downloadable<T> getDownloadable() {
		return downloadable;
	}

	protected void setDownloadable(Downloadable<T> downloadable) {
		this.downloadable = downloadable;
	}

	protected Processable<T> getProcessable() {
		return processable;
	}

	protected void setProcessable(Processable<T> processable) {
		this.processable = processable;
	}

	protected Storeable<T> getStoreable() {
		return storeable;
	}

	protected void setStoreable(Storeable<T> storeable) {
		this.storeable = storeable;
	}
	
	protected ThreadPool getThreadPool() {
		return threadPool;
	}

	protected void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}

	protected Repository getRepository() {
		return repository;
	}

	protected void setRepository(Repository repository) {
		this.repository = repository;
	}

}
