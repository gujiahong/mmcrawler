package com.mama100.crawler.framework.domain;

public class KaoLaItemPage extends BasePage {
	/**
	 * 商品ID
	 */
	private String goodsid;
	/**
	 *商品价格 
	 */
	private String goodsPrice;
	/**
	 *商品名称
	 */
	private String goodsName;
	/**
	 *商品月交易
	 */
	private String goodsMonthDeal;
	/**
	 *商品评论数
	 */
	private String goodsComment;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 商品来源
	 */
	private String fromCountry;
	/**
	 * 商品详情页url
	 */
	private String kaoLaGoodsUrl;
	/**
	 * 商品所属品牌
	 */
	private String brand;
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getBrand() {
		return brand;
	}
	public String getGoodsid() {
		return goodsid;
	}
	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsMonthDeal() {
		return goodsMonthDeal;
	}
	public void setGoodsMonthDeal(String goodsMonthDeal) {
		this.goodsMonthDeal = goodsMonthDeal;
	}
	public String getGoodsComment() {
		return goodsComment;
	}
	public void setGoodsComment(String goodsComment) {
		this.goodsComment = goodsComment;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getFromCountry() {
		return fromCountry;
	}
	public void setFromCountry(String fromCountry) {
		this.fromCountry = fromCountry;
	}
	public String getKaoLaGoodsUrl() {
		return kaoLaGoodsUrl;
	}
	public void setKaoLaGoodsUrl(String kaoLaGoodsUrl) {
		this.kaoLaGoodsUrl = kaoLaGoodsUrl;
	}
	@Override
	public String toString() {
		return "KaoLaItemPage [goodsid=" + goodsid + ", goodsPrice="
				+ goodsPrice + ", goodsName=" + goodsName + ", goodsMonthDeal="
				+ goodsMonthDeal + ", goodsComment=" + goodsComment
				+ ", shopName=" + shopName + ", fromCountry=" + fromCountry
				+ ", kaoLaGoodsUrl=" + kaoLaGoodsUrl + ", brand=" + brand + "]";
	}
	
}
