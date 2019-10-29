package com.byteman;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.*;

/**
 * useage:
 * bminstall.bat -b  -p 55000 11644
 * 11644 为jvm的进程号， 55000是你对外监听的端口号
 * 动态加载规则
 * bmsubmit -p 55000  appmain.btm
 * 卸载规则
 * bmsubmit -p 55000 -u appmain.btm
 */
public class ThreadServerDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(2);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println( Mytask.print(String.valueOf(RandomUtil.randomInt(10))));
            }
        },2,2, TimeUnit.SECONDS);
    }
}

class Mytask
{
    public static String print(String anInt)
    {
        System.out.println(anInt);
        return anInt+"";
    }
}
