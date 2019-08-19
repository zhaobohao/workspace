package com.batch.test.job;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class ReadFromMultiResourceJob {










    /**
     * 根据正则表达式路径，返回所有匹配的文件Resource
     * @param regexPath
     * @return
     */
    private static Resource[] getResources(String regexPath)
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
}
