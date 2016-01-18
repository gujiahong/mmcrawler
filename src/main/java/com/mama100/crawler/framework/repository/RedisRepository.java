package com.mama100.crawler.framework.repository;

import org.apache.commons.lang.StringUtils;

import com.mama100.crawler.framework.config.Config;
import com.mama100.crawler.framework.utils.RedisUtils;

public class RedisRepository implements Repository {

	RedisUtils redisUtils = new RedisUtils();
	
	// 定义redis中的高优先级队列的key值
	private String heightkey;
	
	// 定义redis中的低优先级队列的key值
	private String lowkey;
	
	public RedisRepository(){
		if(StringUtils.isEmpty(this.heightkey))this.heightkey = Config.heightkey;
	    if(StringUtils.isEmpty(this.lowkey))this.lowkey = Config.lowkey;
	}
	
	public RedisRepository(String heightkey, String lowkey) {
		this.heightkey = heightkey;
		this.lowkey = lowkey;
	}

	@Override
	public String poll() {
		String url = redisUtils.poll(heightkey);
		if(url==null){
			url = redisUtils.poll(lowkey);
		}
		return url;
	}

	@Override
	public void add(String nexturl) {
		redisUtils.add(lowkey, nexturl);
	}

	@Override
	public void addHigh(String nexturl) {
		redisUtils.add(heightkey, nexturl);
	}

	public String getHeightkey() {
		return heightkey;
	}

	public void setHeightkey(String heightkey) {
		this.heightkey = heightkey;
	}

	public String getLowkey() {
		return lowkey;
	}

	public void setLowkey(String lowkey) {
		this.lowkey = lowkey;
	}

}
