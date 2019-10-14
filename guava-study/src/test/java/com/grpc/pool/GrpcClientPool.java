package com.grpc.pool;

import cn.hutool.log.StaticLog;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class GrpcClientPool {

    private static GenericObjectPool<GrpcClient> objectPool = null;

    static {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 池中的最大连接数
        poolConfig.setMaxTotal(8);
        // 最少的空闲连接数
        poolConfig.setMinIdle(0);
        // 最多的空闲连接数
        poolConfig.setMaxIdle(8);
        // 当连接池资源耗尽时,调用者最大阻塞的时间,超时时抛出异常 单位:毫秒数
        poolConfig.setMaxWaitMillis(-1);
        // 连接池存放池化对象方式,true放在空闲队列最前面,false放在空闲队列最后
        poolConfig.setLifo(true);
        // 连接空闲的最小时间,达到此值后空闲连接可能会被移除,默认即为30分钟
        poolConfig.setMinEvictableIdleTimeMillis(1000L * 60L * 30L);// 连接耗尽时是否阻塞,默认为true
        poolConfig.setBlockWhenExhausted(true);
        objectPool = new GenericObjectPool<>(new GrpcClientFactory(), poolConfig);
    }

    public static GrpcClient borrowObject() {
        try {
            GrpcClient client = objectPool.borrowObject();
            StaticLog.info("=======total threads created: " + objectPool.getCreatedCount());
            return client;
        } catch (Exception e) {
            StaticLog.error("objectPool.borrowObject error, msg:{}, exception:{}", e.toString(), e);
        }
        return createClient();
    }

    public static void returnObject(GrpcClient client) {
        try {
            objectPool.returnObject(client);
        } catch (Exception e) {
            StaticLog.error("objectPool.returnObject error, msg:{}, exception:{}", e.toString(), e);
        }
    }

    private static GrpcClient createClient() {
        return new GrpcClient(GrpcClientFactory.LOCALHOST, GrpcClientFactory.PORT);
    }

}