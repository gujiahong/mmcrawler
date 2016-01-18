package com.mama100.crawler.framework.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mama100.crawler.framework.config.Config;
import com.mama100.crawler.framework.download.Downloadable;
import com.mama100.crawler.framework.process.Processable;
import com.mama100.crawler.framework.repository.QueueRepository;
import com.mama100.crawler.framework.repository.RedisRepository;
import com.mama100.crawler.framework.repository.Repository;
import com.mama100.crawler.framework.store.Storeable;
import com.mama100.crawler.framework.threadpool.ThreadPool;

public class CrawlerUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlerUtils.class);
	
	public static void genCrawlerBatchId() {
		String batchIdStr = DateUtils.date2Str(new Date(), "yyyyMMddHHmmss");
		Config.bacth_id = Long.parseLong(batchIdStr);
		logger.info("爬虫CRAWLER_ID：{}",Config.bacth_id);
	}
	
	/**
	 * 配置爬虫启动
	 */
	public static boolean startUpParamsCheck(String[] args) {
		int length = args.length;
		if(length>3){
			Config.crawler_interval = Integer.parseInt(args[0]);
			Config.nThread = Integer.parseInt(args[1]);
			Config.isPreSetSeedUrl = Boolean.parseBoolean(args[2]);
			Config.repositoryClass = String.valueOf(args[3]);
			logger.info("爬虫CRAWLER_INTERVAL(毫秒)：{}",Config.crawler_interval);
			logger.info("爬虫THREAD_NUM：{}",Config.nThread);
			logger.info("爬虫PRE_SET_SEED_URL：{}",Config.isPreSetSeedUrl);
			logger.info("爬虫仓库类名称：{}",Config.repositoryClass);
			reflectRepositoryInstance(args);   
	        logger.info("爬虫仓库类实例：{}",Config.repository);
	        if(length>5){
	        	logger.info("爬虫仓库高优先级队列：{}",Config.heightkey);
	        	logger.info("爬虫仓库低优先级队列：{}",Config.lowkey);
	        }
	        genCrawlerBatchId();
			return true;
		}
		System.err.println("请输入: 爬虫批次|爬虫线程数|爬虫预设种子URL|爬虫仓库类名称");
		return false;
		
	}

	/**
	 * 通过反射来取得相应的仓库类
	 */
	private static void reflectRepositoryInstance(String[] args) {
		Class<?> class1 = null;  
		String[] arr = Config.repositoryClass.split("\\.");
		String mainClassName = arr[arr.length-1];
		try {
			class1 = Class.forName(Config.repositoryClass);
			Constructor<?>[] constructors = class1.getConstructors();  
			Repository repo = null;
			if(mainClassName.equals("QueueRepository")){
			   repo = (QueueRepository) class1.newInstance();
		    }else if(mainClassName.equals("RedisRepository")){
		       int length = args.length;
		       if(length>5){
		    	   Config.heightkey = args[4];
		    	   Config.lowkey = args[5];
		       }else{
		    	   logger.info("无爬虫仓库高|低优先级队列，走[默认]队列");
		       }
		       repo = (RedisRepository) constructors[1].newInstance(Config.heightkey, Config.lowkey);
		    }else{
		    	;;
		    } 
			if(null!=repo)
				Config.repository = repo;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 检查爬虫的配置
	 */
	@SuppressWarnings("rawtypes")
	public static void checkConf(Processable processable,Downloadable downloadable,Storeable storeable,Repository repository,ThreadPool threadPool) {
		if(processable==null || downloadable==null || storeable==null ||  repository==null || threadPool==null){
			String message = "没有配置爬虫Downloadable(下载),Processable(解析),Storeable(存储),Repository(仓库),ThreadPool(线程池)类.";
			logger.error(message);
			throw new RuntimeException(message);
		}
		logger.info("****************************************************");
		logger.info("downloadable的实现类：{}",downloadable.getClass().getSimpleName());
		logger.info("processable的实现类：{}",processable.getClass().getSimpleName());
		logger.info("storeable的实现类：{}",storeable.getClass().getSimpleName());
		logger.info("repository的实现类：{}",repository.getClass().getSimpleName());
		logger.info("threadPool的实现类：{}",threadPool.getClass().getSimpleName());
		logger.info("****************************************************");
	}
	
	/**
	 * 爬虫的监控
	 */
	public static void initCrawlerWatcher() {
	}
	
	public static void threadSleep(long million){
		try {
			Thread.sleep(million);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String getRegexContent(String content,String compileRule,int group){
		String res = null;
		if(!StringUtils.isEmpty(content) || !StringUtils.isEmpty(compileRule) ){
			Pattern compile = Pattern.compile(compileRule);
			Matcher matcher = compile.matcher(content);
			if(matcher.find()){
				res = matcher.group(group);
			}
		}
		return res;
	}

}
