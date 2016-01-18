package com.mama100.crawler.framework.config;

import com.mama100.crawler.framework.repository.QueueRepository;
import com.mama100.crawler.framework.repository.Repository;

public class Config {
	
	/**TODO
	 * 注意：这些配置的值应该是从数据库或者配置文件中读取过来的
	 * 
	 */
	
	/* 线程数 */
	public static Integer nThread = 3;
	
	/* 线程休眠时间,每次抓取间隔 */
	public static long crawler_interval = 1000;
	
	/* 线程休眠时间,无URL */
	public static long crawler_interval_if_no_seedurl = 5000;
	
	/* 是否开启debug模式 */
	public static boolean isDebug = true;

	/* 是否开启httpclient的代理模式 */
	public static boolean isProxy = false;
	
	/* redis的主机地址 */
	public static String redis_host = "192.168.234.16";
	
	/* redis的主机端口 */
	public static int redis_port = 6379;
	
	/* redis的连接最大空闲 */
	public static int redis_max_idle = 500;
	
	/* redis的连接最大值 */
	public static int redis_max_total = 1000;
	
	/* zk连接池 */
	public static String zk_connect_pool = "";
	
	/* 是否存储持久化 */
	public static boolean isStore = false;
	
	/*批次号*/
	public static long bacth_id = 00000000000000;
	
	/*默认redis高队列*/
	public static String heightkey = "crawler.todo.height";
	
	/*默认redis低队列*/
	public static String lowkey = "crawler.todo.low";
	
	/* 是否预先设置种子url */
	public static boolean isPreSetSeedUrl = true;
	
	/* url仓库类型名称  */
	public static String repositoryClass = "com.mama100.crawler.framework.repository.QueueRepository";
	
	/* url仓库类型类  */
	public static Repository repository = new QueueRepository();

}
