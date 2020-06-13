package com.transcation.service;

import com.transcation.layout.executor.Layout;
import com.transcation.layout.instance.ServiceInstance;
import com.transcation.service.enums.ServiceStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.*;

/**
 * 后面请求依赖于前面请求的执行结果
 */
public class TranscationTest {
    DeService w;
    DeService1 w1;
    DeService2 w2;
    //DeService3 w3;
    DeServiceServiceContext deWorkBaseServiceContext;
    ThreadPoolExecutor COMMON_POOL;
    @BeforeTest
    public void beforeTest() {
        System.out.println("init test parameters");
        w = new DeService();
        w1 = new DeService1();
        w2 = new DeService2();
        //w3 = new DeService3();
        // 初始化context
        deWorkBaseServiceContext = new DeServiceServiceContext();
        deWorkBaseServiceContext.setUser(new User("user0"));
        COMMON_POOL =
                new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, 1024,
                        15L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        (ThreadFactory) Thread::new);
    }
    @DataProvider(name = "changeTradeMethodResult")
    public Object[] provideData() {
        Object[] o = new Object[] {
                new DeService3().setTradeServiceStatus(ServiceStatus.SUCCESS),
                new DeService3().setTradeServiceStatus(ServiceStatus.FAILS),
                new DeService3().setTradeServiceStatus(ServiceStatus.TIMEOUT),
                new DeService3().setTradeServiceStatus(ServiceStatus.TIMEOUT).setCheckServiceStatus(ServiceStatus.FAILS)
        };
        return o;
    }
    /**
     * 测试串行的情况 s0->s1->s2->s3,
     * s3的trade模拟各种失败情况
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test(dataProvider = "changeTradeMethodResult")
    public void normalTest(DeService3 w3) throws ExecutionException, InterruptedException {
        // 参数化测试的时候，befortest不会每次都执行，所以还是要在方法体内来初始化
        w = new DeService();
        w1 = new DeService1();
        w2 = new DeService2();
        COMMON_POOL =
                new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, 1024,
                        15L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        (ThreadFactory) Thread::new);
        ServiceInstance service3 = new ServiceInstance.Builder()
                .service(w3)
                .callback(w3)
                .build();

        ServiceInstance service2 = new ServiceInstance.Builder()
                .service(w2)
                .callback(w2)
                .next(service3)
                .build();

        ServiceInstance service1 = new ServiceInstance.Builder()
                .service(w1)
                .callback(w1)
                .next(service2)
                .build();

        ServiceInstance service0 = new ServiceInstance.Builder()
                .service(w)
                .param(deWorkBaseServiceContext)
                .next(service1)
                .callback(w)
                .build();
        //忽略执行service0
        //ServiceUtil.skipTrade(service0);
        service1.setParam(deWorkBaseServiceContext);
        service2.setParam(deWorkBaseServiceContext);
        service3.setParam(deWorkBaseServiceContext);
        if (Layout.beginWork(4500,COMMON_POOL, service0)) {
            // 执行成功，成功后，交易置为成功。
            System.out.println(" 交易成功！");
        } else {
            // 失败的情况，返回失败,交易设置为失败。
            System.out.println("交易失败！");
        }
        // 展示最后一个Service的运行结果
        //System.out.println(service2.getserviceResult());
        //Layout.shutDown();
        COMMON_POOL.shutdown();
    }
}
