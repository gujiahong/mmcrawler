package com.mama100.crawler.framework.utils;


import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import com.mama100.crawler.framework.config.Config;

@SuppressWarnings("deprecation")
public class RedisUtils {
	
	public static String start_url = "start_url";
	public static String heightkey = "crawler.todo.height";
	public static String lowkey = "crawler.todo.low";
	
	private static JedisPool jedisPool = null;

	// 使用的时候如果没有就创建一个新的JedisPool,而且要保证单例模式
	// 可以设置临界区 synchronized如果放到方法上则效率低,要放到代码块中
	public static JedisPool getJedis() {
		if (jedisPool == null) {
			// 创建JedisPool,设置临界区
			synchronized (RedisUtils.class) {
				if (jedisPool == null) {
					try {
						JedisPoolConfig poolConfig = new JedisPoolConfig();
						poolConfig.setMaxIdle(Config.redis_max_idle);
						poolConfig.setMaxTotal(Config.redis_max_total);
						poolConfig.setMaxWaitMillis(10000);
						poolConfig.setTestOnBorrow(true);
						jedisPool = new JedisPool(poolConfig, Config.redis_host, Config.redis_port);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return jedisPool;
	}
	
	public RedisUtils(){
		getJedis();
	}
	
	public List<String> lrange(String key, int start, int end) {
		Jedis jedis = jedisPool.getResource();
		List<String> list = new ArrayList<String>();
		try {
			list = jedis.lrange(key, start, end);
			//jedisPool.returnResourceObject(jedis);
		} catch (JedisConnectionException e) {
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
		return list;
	}
	
	public void add(String lowKey, String url) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.lpush(lowKey, url);
			//jedisPool.returnResourceObject(jedis);
		} catch (JedisConnectionException e) {
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
	}
	
	public String poll(String key) {
		Jedis jedis = jedisPool.getResource();
		String r = null;
		try {
			r = jedis.rpop(key);
			//jedisPool.returnResourceObject(jedis);
		} catch (JedisConnectionException e) {
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
		return r;
	}
	
	public long hset(String key, String field,String value,boolean isExists){
		Jedis jedis = jedisPool.getResource();
		long r = -1l;
		try {
			if(isExists) {
				r = jedis.hsetnx(key, field, value);
			} else{
				r = jedis.hset(key, field, value);
			}
		} catch (JedisConnectionException e) {
			 if (null != jedis) {
			 	jedisPool.returnBrokenResource(jedis);
		        jedis = null;
			 }
		}finally {
			  if (null != jedis)
				  jedisPool.returnResource(jedis);
		}
		return r;
	}
	
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String r = null;
		try {
			r = jedis.hget(key, field);
		} catch (JedisConnectionException e) {
			if (null != jedis) {
				jedisPool.returnBrokenResource(jedis);
				jedis = null;
			}
		} finally {
			if (null != jedis)
				jedisPool.returnResource(jedis);
		}
		return r;
	}
	
}
