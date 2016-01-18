package com.mama100.crawler.framework.exception;

/**
 * 爬虫自定义异常
 * @author turner
 *
 */
public class CrawlerException extends Exception
{
	private static final long serialVersionUID = -6778098795047618762L;

	public CrawlerException(String msg)
    {
        super(msg);
    }
} 
