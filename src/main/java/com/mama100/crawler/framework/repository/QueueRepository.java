package com.mama100.crawler.framework.repository;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 优先级队列
 * @author Administrator
 *
 */
public class QueueRepository implements Repository {
	
	// 定义低优先级url队列
	Queue<String> lowqueue = new ConcurrentLinkedQueue<String>();
	
	// 定义高优先级url队列
	Queue<String> highqueue = new ConcurrentLinkedQueue<String>();
	
	@Override
	public String poll() {
		String url = highqueue.poll();
		if(url==null){
			url = lowqueue.poll();
		}
		return url;
	}

	@Override
	public void add(String nexturl) {
		this.lowqueue.add(nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		this.highqueue.add(nexturl);
	}

}
