package com.mama100.crawler;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.mama100.crawler.framework.utils.DateUtils;


/**
 * Unit test for simple App.
 */
public class AppTest {
	
	@Test
	public void test(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 15);
		Date now = new Date();
		long millsend = calendar.getTime().getTime() - now.getTime();
		int bc = 1000;
		System.out.println("millsend="+millsend+",bc="+ bc + "," + (millsend/bc));
		String dateDiffTmp = DateUtils.dateDiff("2015081614", "2015081615","yyyyMMddHH", "h")+"";
		String evalTime = DateUtils.evalTime("2015081613", "yyyyMMddHH", "yyyyMMddHH", "h", 1);
		int parseInt = Integer.parseInt(dateDiffTmp);
		System.out.println(parseInt);
		for(int i =0;i<=parseInt;i++){
			String evalTimeTmp = DateUtils.evalTime("2015081614", "yyyyMMddHH", "yyyyMMddHH", "hour", i);
			System.out.println(evalTimeTmp);
		}
		boolean today = DateUtils.isToday("2015081811","yyyyMMddHH", "yyyyMMdd");
		System.out.println(today);
	}
    
}
