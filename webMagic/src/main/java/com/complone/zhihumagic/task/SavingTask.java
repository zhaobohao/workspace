package com.complone.zhihumagic.task;

import com.complone.zhihumagic.model.UserDetailInfo;
import com.complone.zhihumagic.service.UserBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;

@Service
@Async
public class SavingTask implements Runnable {

    Logger logger = LoggerFactory.getLogger(SavingTask.class);


    @Autowired
    private UserBaseInfoService userBaseInfoService;

    private BlockingDeque<UserDetailInfo> blockingDeque; //新建一个阻塞队列

    private volatile boolean isStop = false; //标记多线程征用资源时，锁是否得到释放

    private volatile int i =0; //多线程存储次数计数器

    public void setUserBaseInfoService(UserBaseInfoService userBaseInfoService) {
        this.userBaseInfoService = userBaseInfoService;
    }


    public SavingTask(){}
    public SavingTask(BlockingDeque<UserDetailInfo> blockingDeque) {
        this.blockingDeque = blockingDeque;
    }


    public synchronized void insertInfo(UserDetailInfo userDetailInfo){
        userBaseInfoService.insertOne(userDetailInfo);
    }


    @Override
    public void run() {
        while (true) {
            if (isStop) { //线程标记符，判断是否终止
                return;
            }
            UserDetailInfo userDetailInfo = blockingDeque.poll(); //获取进入阻塞队列的对象
//            System.out.println(userDetailInfo.getNickname());
            if (userDetailInfo == null) {
                try {

                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {

                    synchronized (this) { //分离写对象操作,加锁防止线程争用

                        try {
                            insertInfo(userDetailInfo);

//                            userBaseInfoService.insertOne(userDetailInfo);
                            logger.info("-------------存贮了：{}------------", ++i);
                        } catch (Exception e) {
                            logger.error("-------出现问题------{}---{}", e, userDetailInfo);
                        }

                    }
            }
        }
    }

    public synchronized void startSave() {
        this.isStop = true;
    }

    public synchronized void stopSave() {
        this.isStop = true;
    }
}
