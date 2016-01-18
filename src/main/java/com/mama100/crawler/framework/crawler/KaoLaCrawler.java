package com.mama100.crawler.framework.crawler;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.mama100.crawler.framework.config.Config;
import com.mama100.crawler.framework.config.KaoLaConfig;
import com.mama100.crawler.framework.domain.KaoLaItemPage;
import com.mama100.crawler.framework.download.KaoLaDownload;
import com.mama100.crawler.framework.process.KaoLaProcess;
import com.mama100.crawler.framework.store.KaoLaStore;
import com.mama100.crawler.framework.threadpool.FixedThreadPool;
import com.mama100.crawler.framework.utils.CrawlerUtils;


/**
 * 此爬虫主要是爬取网易考拉海外购的商品数据
 * @author turner
 * 2015/12/31
 * shell#java -cp crawler-core-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.mama100.crawler.framework.crawler.KaoLaCrawler 2000 20 false com.mama100.crawler.framework.repository.RedisRepository crawler.todo.high.163.kaola crawler.todo.low.163.kaola
 */
public class KaoLaCrawler extends BaseCrawler<KaoLaItemPage>{

	@Override
	protected KaoLaCrawler start() {
		CrawlerUtils.checkConf(processable, downloadable, storeable, repository, threadPool);
		logger.info("MaMa100 web crawler starts to crawl [kaola.com] product on detail.");
		while(true){
			final String url = repository.poll();
			if(StringUtils.isNotBlank(url)){
				threadPool.execute(new Runnable() {
					public void run() {
						// download
						KaoLaItemPage page = KaoLaCrawler.this.download(url);
						// process
						KaoLaCrawler.this.process(page);
						List<String> urlList = page.getUrlList();
						for (String nexturl : urlList) {
							if(nexturl.startsWith("http://www.kaola.com/category")
									|| nexturl.startsWith("http://www.kaola.com/brand")){
								repository.add(nexturl); // product-page
							}else if(nexturl.startsWith("http://www.kaola.com/product")){
								repository.addHigh(nexturl); // product-detail
							}else{
								System.out.println("add repo url is not inculde inside the rules : " + nexturl);
							}
						}
						// store
						if (url.startsWith("http://www.kaola.com/product")) {
							KaoLaCrawler.this.store(page);
						}
						if(KaoLaConfig.isDebug)
							logger.info("current thread id ："+Thread.currentThread().getId());
					}
				});
				CrawlerUtils.threadSleep(KaoLaConfig.crawler_interval);
			}else{
				logger.info("repo no urls, and take a rest. ");
				CrawlerUtils.threadSleep(KaoLaConfig.crawler_interval_if_no_seedurl);
			}
		}
	}
	
	public static void main(String[] args) {
		if(!CrawlerUtils.startUpParamsCheck(args))return;
		KaoLaCrawler kaoLaCrawler = new KaoLaCrawler();
		kaoLaCrawler.withDownload(new KaoLaDownload())
			.withProcessable(new KaoLaProcess())
			.withStore(new KaoLaStore())
			.withThreadPool(new FixedThreadPool(KaoLaConfig.nThread))
			.withRepository(Config.repository);
		if(KaoLaConfig.isPreSetSeedUrl)kaoLaCrawler.withSeedUrl(KaoLaConfig.seedUrl);
		kaoLaCrawler.start();
	}

}