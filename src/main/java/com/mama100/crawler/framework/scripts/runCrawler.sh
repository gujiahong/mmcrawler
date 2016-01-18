#!/bin/bash

batchId=$1
threadNum=1
if [ $# -lt 1 ]; then
    batchId=`date +"%Y%m%d%H%M%S"`
fi
javaCmd=`which java`
mainClass=com.mama100.crawler.framework.crawler.TbSwisseCrawler
jarPath=/data/crawler/tm/crawler-core-0.0.1-SNAPSHOT-jar-with-dependencies.jar
isPreSetSeedUrl=false
repositoryClass=com.mama100.crawler.framework.repository.RedisRepository
heightkey=crawler.todo.high.tm.swisse
lowkey=crawler.todo.low.tm.swisse
#echo ${batchId}
${javaCmd} -cp ${jarPath} ${mainClass} ${batchId} ${threadNum} ${isPreSetSeedUrl} ${repositoryClass} ${heightkey} ${lowkey}
