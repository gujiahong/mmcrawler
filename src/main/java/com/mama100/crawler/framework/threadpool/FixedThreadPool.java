package com.mama100.crawler.framework.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool implements ThreadPool {
	
	// 线程数量
	int myThread; 
	
	ExecutorService newFixedThreadPool = null;
	
	public FixedThreadPool(int myThread) {
		this.myThread = myThread;
		this.newFixedThreadPool = Executors.newFixedThreadPool(this.myThread);;
	}
	
	@Override
	public void execute(Runnable runnable) {
		this.newFixedThreadPool.execute(runnable);
	}

}
