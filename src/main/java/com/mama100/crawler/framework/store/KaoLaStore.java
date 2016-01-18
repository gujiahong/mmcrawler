package com.mama100.crawler.framework.store;

import com.mama100.crawler.framework.config.KaoLaConfig;
import com.mama100.crawler.framework.domain.KaoLaItemPage;
import com.mama100.crawler.framework.utils.DbUtils;

public class KaoLaStore implements Storeable<KaoLaItemPage> {

	@Override
	public void store(KaoLaItemPage kaoLaPage) {
		System.err.println("爬取批次ID为 ： " + KaoLaConfig.bacth_id);
		System.err.println("网页标题：" + kaoLaPage.getPageTitle());
		System.err.println("商品ID：" + kaoLaPage.getGoodsid());
		System.err.println("商品名称：" + kaoLaPage.getGoodsName());
		System.err.println("商品URL：" + kaoLaPage.getKaoLaGoodsUrl());
		System.err.println("商品产国：" + kaoLaPage.getFromCountry());
		System.err.println("商品价格：" + kaoLaPage.getGoodsPrice());
		System.err.println("商品月销量：" + kaoLaPage.getGoodsMonthDeal());
		System.err.println("商品评论数：" + kaoLaPage.getGoodsComment());
		System.err.println("商品所属品牌：" + kaoLaPage.getBrand());
		if(KaoLaConfig.isStore){
			KaoLaItemPage kaoLaItemPage = new KaoLaItemPage();
			// TODO
			DbUtils.insertItem(kaoLaItemPage);
			System.err.println("考拉入库的值为：" + kaoLaItemPage.toString());
			System.out.println();
		}
	}
	
}
