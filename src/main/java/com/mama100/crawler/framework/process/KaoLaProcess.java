package com.mama100.crawler.framework.process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONObject;

import com.mama100.crawler.framework.config.KaoLaConfig;
import com.mama100.crawler.framework.domain.KaoLaItemPage;
import com.mama100.crawler.framework.utils.HtmlUtils;
import com.mama100.crawler.framework.utils.RedisUtils;

public class KaoLaProcess implements Processable<KaoLaItemPage> {
	
	HtmlCleaner htmlCleaner = new HtmlCleaner();
	
	RedisUtils redisUtils = new RedisUtils();
	
	@Override
	public void process(KaoLaItemPage page) {
		String content = page.getContent();
		TagNode rootNode = htmlCleaner.clean(content);
		String currentUrl = page.getUrl();
		if(currentUrl.equals(KaoLaConfig.seedUrl)){
			parseKaoLaProductOnSeed(page, rootNode);
		} else if(currentUrl.startsWith("http://www.kaola.com/category")
				|| currentUrl.startsWith("http://www.kaola.com/brand")){
			parseKaoLaProductOnPage(page, rootNode,currentUrl);
		}else if(currentUrl.startsWith("http://www.kaola.com/product")){
			parseKaoLaProductOnDetail(page, rootNode, currentUrl);
		}else{
			System.out.println("add process url is not inculde inside the rules : " + currentUrl);
		}
	}

	private void parseKaoLaProductOnSeed(KaoLaItemPage page, TagNode rootNode) {
		try {
			Object[] evaluateXPath = rootNode.evaluateXPath("//*[@id=\"funcTab\"]/li");
			int i = 1;
			for(Object obj : evaluateXPath){
				TagNode tgn = (TagNode)obj;
				List<? extends TagNode> elementListByName = tgn.getElementListByName("a", true);
				for (TagNode tagNode : elementListByName) {
					String nexturl = tagNode.getAttributeByName("href");
					nexturl = nexturl.replace("&amp;", "&");
					if(!nexturl.contains("activity"))
						if(nexturl.indexOf("javascript")==-1)page.addUrl(nexturl);
					System.out.println(i +","+ nexturl);
				}
				i++;
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
	}

	private void parseKaoLaProductOnDetail(KaoLaItemPage page,TagNode rootNode, String currentUrl) {
		String brand = HtmlUtils.getText(rootNode, "/body/article/div[2]/dl/dt[1]/a");
		String fromCountry = HtmlUtils.getText(rootNode, "/body/article/div[2]/dl/dt[1]/span[1]");
		System.out.println("band: " + brand + ", fromCountry :" + fromCountry);
		String productId = "";
		if(!StringUtils.isEmpty(currentUrl)){
			Pattern compile = Pattern.compile("http://www.kaola.(com|hk)/product/([0-9]+).html");
			Matcher matcher = compile.matcher(currentUrl);
			if(matcher.find()){
				productId = matcher.group(2);
			}
			System.out.println("productId: " + productId);
			if(!StringUtils.isEmpty(productId)){
				String hget = redisUtils.hget("crawler.page.value.163.kaola",productId);
				JSONObject jsonObject = new JSONObject(hget);
				Set keySet = jsonObject.keySet();
				for (Object object : keySet) {
					 String key = (String)object;
					 String value =jsonObject.getString(key);
					 if(KaoLaConfig.isDebug)System.out.println("key : " + key + "，value : " + value);
					 if(key.equals("productId"))page.setGoodsid(value);
					 if(key.equals("productUrl"))page.setKaoLaGoodsUrl(value); 
					 if(key.equals("productName"))page.setGoodsName(value);
					 if(key.equals("pageTitle"))page.setPageTitle(value);
					 if(key.equals("productPrice"))page.setGoodsPrice(value);
					 if(key.equals("productComment"))page.setGoodsComment(value);
					 if(key.equals("productFromCountry"))page.setFromCountry(!StringUtils.isEmpty(value) ? value : fromCountry);
					 page.setBrand(brand);
				 }
			}
		}
	}

	private void parseKaoLaProductOnPage(KaoLaItemPage page, TagNode rootNode,String currentUrl) {
		String nexturl = null;// 只有1页
		if(currentUrl.contains("pageSize")||currentUrl.contains("pageNo"))
			nexturl = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"order\"]/div/a",1); // 大于2页
		else
			nexturl = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"order\"]/div/a",0); // 只有2页
		if(!StringUtils.isEmpty(nexturl) && nexturl.indexOf("javascript")==-1){
			nexturl = nexturl.replace("&amp;", "&");
			page.addUrl(nexturl); // 加入下一页的分页
			System.out.println("next page url : " + nexturl);
		}
		try {
			Object[] evaluateXPath = rootNode.evaluateXPath("//*[@id=\"result\"]/li");
			System.out.println("evaluateXPath size() : " + evaluateXPath.length);
			for(int i=1;i<=evaluateXPath.length;i++){
				Map<String,String> itemMap = new HashMap<String, String>();
				String productUrl = HtmlUtils.getAttributeByName(rootNode, "href", "//*[@id=\"result\"]/li["+i+"]/div/div[1]/a");
				String productId = "";
				if(!StringUtils.isEmpty(productUrl)){
					Pattern compile = Pattern.compile("http://www.kaola.(com|hk)/product/([0-9]+).html");
					Matcher matcher = compile.matcher(productUrl);
					if(matcher.find()){
						productId = matcher.group(2);
					}
					System.out.println("productId: " + productId);
					itemMap.put("productId", "KL_" + productId);
				}
				String productName = HtmlUtils.getText(rootNode, "//*[@id=\"result\"]/li["+i+"]/div/div[2]/p[2]/a");
				String productPrice = HtmlUtils.getText(rootNode, "//*[@id=\"result\"]/li["+i+"]/div/div[2]/p[1]/span[1]");
				String productComment = HtmlUtils.getText(rootNode, "//*[@id=\"result\"]/li["+i+"]/div/div[2]/p[4]/a");
				String productFromCountry = HtmlUtils.getText(rootNode, "//*[@id=\"result\"]/li["+i+"]/div/div[2]/p[4]/span");
				productUrl = productUrl.replace("&amp;", "&");
				System.out.println("productUrl: " + productUrl);
				itemMap.put("productUrl", productUrl);
				page.addUrl(productUrl);
				
				productName = productName.trim();
				System.out.println("productName: " + productName);
				itemMap.put("productName", productName);
				itemMap.put("pageTitle", "海淘KAOLA.COM-" + productName);
				
				productPrice = productPrice.replace("¥", "");
				System.out.println("productPrice: " + productPrice);
				itemMap.put("productPrice", productPrice);
				
				productComment = productComment.trim();
				System.out.println("productComment: " + productComment);
				itemMap.put("productComment", productComment);
				
				productFromCountry = productFromCountry.trim();
				System.out.println("productFromCountry: " + productFromCountry);
				itemMap.put("productFromCountry", productFromCountry);
				
				String valueToString = JSONObject.valueToString(itemMap);
				long hset = redisUtils.hset("crawler.page.value.163.kaola", productId, valueToString, true);
				System.out.println("redis hset result :" + hset);
			}
		}catch (XPatherException e) {
			e.printStackTrace();
		}
	}
}
