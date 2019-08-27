package com.batch.test.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Date;

public class JobUtils {
    /**
     * 根据正则表达式路径，返回所有匹配的文件Resource
     * @param regexPath
     * @return
     */
   public   static Resource[] getResources(String regexPath)
    {
        if(!regexPath.startsWith("file:"))
            regexPath="file:"+regexPath;
        try {
            ResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
            Resource[] resources=resolver.getResources(regexPath);
            return resources;
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource[]{};
        }
    }

    /**
     * 获取种子参数，默认在一天时间里，这个种子都是一样的，这样job一天只能执行一次
     * @param isForce 默认job一天只执行一次，如果要强执执行，请输入true
     * @return
     */
    public static String getSeed(Boolean isForce) {
        return isForce? DateUtil.formatDate(new Date()): RandomUtil.randomString(10);
    }
}
