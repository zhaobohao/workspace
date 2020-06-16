package com.timewheel;


import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RingBufferWheelTest {

    private static Logger logger = LoggerFactory.getLogger(RingBufferWheelTest.class) ;

    public static void main(String[] args) throws Exception {

        test1();

    }

    private static void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel.Task task = new Task() ;
        task.setDelayTime(10);
        RingBufferWheel wheel = new RingBufferWheel(executorService) ;
        wheel.addTask(task) ;

        task = new Task() ;
        task.setDelayTime(74);
        wheel.addTask(task) ;

        while (true){
            logger.info("task size={}" , wheel.taskSize());
            TimeUnit.SECONDS.sleep(1);
        }
    }
    private static void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel.Task task = new Task() ;
        task.setDelayTime(10);
        RingBufferWheel wheel = new RingBufferWheel(executorService) ;
        wheel.addTask(task) ;

        task = new Task() ;
        task.setDelayTime(74);
        wheel.addTask(task) ;

        wheel.start();

//        new Thread(() -> {
//            while (true){
//                logger.info("task size={}" , wheel.taskSize());
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        TimeUnit.SECONDS.sleep(12);
        wheel.stop(true);


    }
    private static void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel.Task task = new Task() ;
        task.setDelayTime(10);
        RingBufferWheel wheel = new RingBufferWheel(executorService) ;
        wheel.addTask(task) ;

        task = new Task() ;
        task.setDelayTime(60);
        wheel.addTask(task) ;


        TimeUnit.SECONDS.sleep(2);
        wheel.stop(false);


    }
    private static void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel wheel = new RingBufferWheel(executorService) ;

        for (int i = 0; i < 65; i++) {
            RingBufferWheel.Task task = new Job(i) ;
            task.setDelayTime(i);
            wheel.addTask(task);
        }

        wheel.start();

        logger.info("task size={}",wheel.taskSize());

        wheel.stop(false);


    }
    private static void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel wheel = new RingBufferWheel(executorService,512) ;

        for (int i = 0; i < 65; i++) {
            RingBufferWheel.Task task = new Job(i) ;
            task.setDelayTime(i);
            wheel.addTask(task);
        }

        logger.info("task size={}",wheel.taskSize());

        wheel.stop(false);


    }
    private static void test6() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel wheel = new RingBufferWheel(executorService,512) ;

        for (int i = 0; i < 10; i++) {
            RingBufferWheel.Task task = new Job(i) ;
            task.setDelayTime(i);
            wheel.addTask(task);
        }

        TimeUnit.SECONDS.sleep(5);
        RingBufferWheel.Task task = new Job(15) ;
        task.setDelayTime(15);
        wheel.addTask(task);

        logger.info("task size={}",wheel.taskSize());

        wheel.stop(false);
    }

    private static void test7() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2) ;

        RingBufferWheel wheel = new RingBufferWheel(executorService,512) ;

        for (int i = 0; i < 10; i++) {
            RingBufferWheel.Task task = new Job(i) ;
            task.setDelayTime(i);
            wheel.addTask(task);
        }

        RingBufferWheel.Task task = new Job(15) ;
        task.setDelayTime(15);
        int cancel = wheel.addTask(task);

        new Thread(() -> {
            boolean flag = wheel.cancel(cancel);
            logger.info("cancel task={}",flag) ;
        }).start();

        RingBufferWheel.Task task1 = new Job(20) ;
        task1.setDelayTime(20);
        wheel.addTask(task1) ;

        logger.info("task size={}",wheel.taskSize());

        wheel.stop(false);
    }


    private static void concurrentTest() throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue(10);
        ThreadFactory product = new ThreadFactoryBuilder()
                .setNameFormat("msg-callback-%d")
                .setDaemon(true)
                .build();
        ThreadPoolExecutor business = new ThreadPoolExecutor(4, 4, 1, TimeUnit.MILLISECONDS, queue,product);

        ExecutorService executorService = Executors.newFixedThreadPool(10) ;
        RingBufferWheel wheel = new RingBufferWheel(executorService) ;

        for (int i = 0; i < 10; i++) {
            business.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 30; i1++) {
                        RingBufferWheel.Task task = new Job(i1) ;
                        task.setDelayTime(i1);
                        wheel.addTask(task);
                    }
                }
            });
        }

        logger.info("task size={}",wheel.taskSize());

        wheel.stop(false);


    }

    private static class Job extends RingBufferWheel.Task{

        private int num ;

        public Job(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            logger.info("number={}" , num);
        }
    }

    private static class Task extends RingBufferWheel.Task{

        @Override
        public void run() {
            logger.info("================");
        }

    }

}