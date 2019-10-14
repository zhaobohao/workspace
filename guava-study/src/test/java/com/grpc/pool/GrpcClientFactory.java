package com.grpc.pool;


import cn.hutool.log.StaticLog;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class GrpcClientFactory extends BasePooledObjectFactory<GrpcClient> {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 23333;

    @Override
    public GrpcClient create() throws Exception
    {
        return new GrpcClient(LOCALHOST, PORT);
    }

    @Override
    public PooledObject<GrpcClient> wrap(GrpcClient client) {
        return new DefaultPooledObject<>(client);
    }

    @Override
    public void destroyObject(PooledObject<GrpcClient> p) throws Exception {
        StaticLog.info("==== GrpcClientFactory#destroyObject ====");
        p.getObject().shutdown();
        super.destroyObject(p);
    }

}