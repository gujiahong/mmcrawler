package com.mama100.crawler.framework.download;

import java.lang.reflect.ParameterizedType;

import com.mama100.crawler.framework.domain.BasePage;
import com.mama100.crawler.framework.utils.PageUtils;

public class BaseDownload<T extends BasePage> {
	
	protected T model = null; 
	
	protected void setModel(T model) {
		this.model = model;
	}
	
	protected T getModel() {
		return model;
	}
	
	protected T baseDownload(String url) {
		generateNewModel(); // 生成新模型
		String content = PageUtils.getContent(url);
		model.setContent(content);
		model.setUrl(url);
		return model;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void generateNewModel() {
		// 获取参数类型信息
		ParameterizedType paramType = (ParameterizedType)this.getClass().getGenericSuperclass();
		// 返回参数类型数组,默认只有一个,所以返回[0]
		Class clazz = (Class)paramType.getActualTypeArguments()[0];
		try {
			model = (T)clazz.newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
