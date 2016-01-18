#!/bin/bash
# 10 4 * * *
params=$1
if [ $# -lt 1 ]; then
    echo "请输入目标种子类型."
    exit
fi
redisPath=/home/hadoop/redis-2.8.21/src
redisHost=192.168.234.16
redisPort=6379
if [ $params == 'tm-swisse' ];then
   echo 'hello , params is tm-swisse'
   ${redisPath}/redis-cli -h ${redisHost} -p ${redisPort} lpush "crawler.todo.high.tm.swisse" "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120103.56.eJvsC9&auction_tag=13186&area=b2c&acm=lb-zebra-17069-396898.1003.8.492986&q=swisse&code=gbk&scm=1003.8.lb-zebra-17069-396898.ITEM_14448737362521_492986"
fi
if [ $params == 'jd-swisse' ];then
   echo 'hello, params is jd-swisse'  
   ${redisPath}/redis-cli -h ${redisHost} -p ${redisPort} lpush "crawler.todo.high.jd.swisse" "http://search.jd.hk/Search?keyword=swisse&enc=utf-8"
fi
if [ $params == 'mia-all' ];then
   echo 'hello, params is mia-all'  
   ${redisPath}/redis-cli -h ${redisHost} -p ${redisPort} lpush "crawler.todo.high.mia.all" "http://www.mia.com"
fi
#${redisPath}/redis-cli -h ${redisHost} -p ${redisPort} lpush "crawler.todo.high.jd.swisse" "http://search.jd.hk/Search?keyword=swisse&enc=utf-8"
#${redisPath}/redis-cli -h ${redisHost} -p ${redisPort} lpush "crawler.todo.high.tm.swisse" "https://list.tmall.hk/search_product.htm?spm=a2231.7718719.2014120103.56.eJvsC9&auction_tag=13186&area=b2c&acm=lb-zebra-17069-396898.1003.8.492986&q=swisse&code=gbk&scm=1003.8.lb-zebra-17069-396898.ITEM_14448737362521_492986"
