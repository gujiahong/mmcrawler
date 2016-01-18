# mmcrawler
<b>Project Description</b></br>
  mmcrawler is simple web crawler apps, it references the most common web crawler's idea and developing of it.It uses to parse     the html web page and get the web infomation that we need.In the apps,we can dynamicly specify the multiple params to run the    apps stabley, and look upon the demo template to program your own web parse rules.
  
<b>Apps Usage</b></br>
for example, the demo locates in : <b>com.mama100.crawler.framework.crawler.KaoLaCrawler</b><br>
and you should specify the running params, and it exits if none params.<br>
the runtime arguments is about following :
>1. crawler interval.<br>
>2. numbers of thread.<br>
>3. is preset the seed url or not.<br>
>4. url repository java class.<br>
>5. redis high priority key.<br>
>6. redis low priority key.<br>

Note: if url repository java class is <b>com.mama100.crawler.framework.repository.QueueRepository</b>,you dont need to specify the args of 5th and 6th.<br>

<b>Run Apps</b></br>
the following steps :<br>
>0. ensure yout env, JDK1.7+
>1. ues the maven to package the apps.</br>
>2. in your linux/nt command line :</br>

<b>ie:</b><br> 
```JAVA
java -cp mmcrawler-0.0.1-SNAPSHOT-jar-with-dependencies.jar com.mama100.crawler.framework.crawler.KaoLaCrawler 2000 20 false com.mama100.crawler.framework.repository.RedisRepository crawler.todo.high.163.kaola crawler.todo.low.163.kaola
```

<br>Note: you can also references the com/mama100/crawler/framework/scripts.
