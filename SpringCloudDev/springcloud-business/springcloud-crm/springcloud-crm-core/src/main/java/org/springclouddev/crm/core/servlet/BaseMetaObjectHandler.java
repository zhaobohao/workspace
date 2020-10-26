package org.springclouddev.crm.core.servlet;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springclouddev.crm.core.utils.UserUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangzhiwei
 * mybatisPlus的自动注入
 */
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    private static final String FIELD_CREATE_TIME = "createTime";
    private static final String FIELD_UPDATE_TIME = "updateTime";
    private static final String FIELD_CREATE_USER = "createUser";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictInsertFill(metaObject, FIELD_CREATE_TIME, Date.class, date);
        this.strictInsertFill(metaObject, FIELD_CREATE_USER, Long.class, UserUtil.getUserId());
    }


    @Override
    public MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        setFieldValByName(fieldName, fieldVal, metaObject);
        return this;
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, FIELD_UPDATE_TIME, Date.class, new Date());
    }
}
