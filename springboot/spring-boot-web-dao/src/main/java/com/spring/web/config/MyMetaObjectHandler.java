package com.spring.web.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
 * 填充器,自动写入各种共用的字段 ，节省代码量
 *
 * @author nieqiurong 2018-08-10 22:59:23.
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //避免使用metaObject.setValue()
       // this.setInsertFieldValByName("createTime", new Timestamp(System.currentTimeMillis()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("nothing to fill ....");
    }
}
