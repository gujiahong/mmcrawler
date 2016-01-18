package com.mama100.crawler.framework.repository;

public interface Repository {

	String poll();

	void add(String nexturl);

	void addHigh(String nexturl);

}
