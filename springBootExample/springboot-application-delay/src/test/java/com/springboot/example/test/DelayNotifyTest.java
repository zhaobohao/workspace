package com.springboot.example.test;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.springboot.example.delay.DelayNotify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class DelayNotifyTest {

    private static Logger logger = LoggerFactory.getLogger(DelayNotifyTest.class);

    public static void main(String[] args) throws Exception {

        //test1();
        //test2();
        test3();
        //test4();
        //test5();
        //test6();
        //test7();
    }

    private static void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify.Task task = new Task();
        task.setKey(10);
        DelayNotify notify = new DelayNotify(executorService);
        notify.addTask(task);

        task = new Task();
        task.setKey(15);
        notify.addTask(task);

        while (true) {
            logger.info("task size={}", notify.taskSize());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private static void test2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify.Task task = new Task();
        task.setKey(10);
        DelayNotify notify = new DelayNotify(executorService);
        notify.addTask(task);

        task = new Task();
        task.setKey(15);
        notify.addTask(task);

        notify.start();

//        new Thread(() -> {
//            while (true){
//                logger.info("task size={}" , notify.taskSize());
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        TimeUnit.SECONDS.sleep(12);
        notify.stop(true);


    }

    private static void test3() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify.Task task = new Task();
        task.setKey(10);
        DelayNotify notify = new DelayNotify(executorService);
        notify.addTask(task);

        task = new Task();
        task.setKey(15);
        notify.addTask(task);

        TimeUnit.SECONDS.sleep(2);
        notify.stop(false);

    }

    private static void test4() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify notify = new DelayNotify(executorService);

        for (int i = 0; i < 65; i++) {
            DelayNotify.Task task = new Job(i);
            task.setKey(i);
            notify.addTask(task);
        }

        notify.start();

        logger.info("task size={}", notify.taskSize());

        notify.stop(false);


    }

    private static void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify notify = new DelayNotify(executorService, 512);

        for (int i = 0; i < 65; i++) {
            DelayNotify.Task task = new Job(i);
            task.setKey(i);
            notify.addTask(task);
        }

        logger.info("task size={}", notify.taskSize());

        notify.stop(false);


    }

    private static void test6() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify notify = new DelayNotify(executorService, 512);

        for (int i = 0; i < 10; i++) {
            DelayNotify.Task task = new Job(i);
            task.setKey(i);
            notify.addTask(task);
        }

        TimeUnit.SECONDS.sleep(5);
        DelayNotify.Task task = new Job(15);
        task.setKey(15);
        notify.addTask(task);

        logger.info("task size={}", notify.taskSize());

        notify.stop(false);
    }

    private static void test7() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        DelayNotify notify = new DelayNotify(executorService, 512);

        for (int i = 0; i < 10; i++) {
            DelayNotify.Task task = new Job(i);
            task.setKey(i);
            notify.addTask(task);
        }

        DelayNotify.Task task = new Job(15);
        task.setKey(15);
        int cancel = notify.addTask(task);

        new Thread(() -> {
            boolean flag = notify.cancel(cancel);
            logger.info("cancel task={}", flag);
        }).start();

        DelayNotify.Task task1 = new Job(20);
        task1.setKey(20);
        notify.addTask(task1);

        logger.info("task size={}", notify.taskSize());

        notify.stop(false);
    }


    private static void concurrentTest() throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue(10);
        ThreadFactory product = new ThreadFactoryBuilder()
                .setNameFormat("msg-callback-%d")
                .setDaemon(true)
                .build();
        ThreadPoolExecutor business = new ThreadPoolExecutor(4, 4, 1, TimeUnit.MILLISECONDS, queue, product);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        DelayNotify notify = new DelayNotify(executorService);

        for (int i = 0; i < 10; i++) {
            business.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i1 = 0; i1 < 30; i1++) {
                        DelayNotify.Task task = new Job(i1);
                        task.setKey(i1);
                        notify.addTask(task);
                    }
                }
            });
        }

        logger.info("task size={}", notify.taskSize());

        notify.stop(false);


    }

    private static class Job extends DelayNotify.Task {

        private int num;

        public Job(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            logger.info("number={}", num);
        }
    }

    private static class Task extends DelayNotify.Task {

        @Override
        public void run() {
            logger.info("================ task completed!");
        }

    }

}