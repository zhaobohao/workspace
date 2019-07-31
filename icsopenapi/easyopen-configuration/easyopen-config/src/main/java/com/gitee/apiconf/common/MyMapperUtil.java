package com.gitee.apiconf.common;

import java.util.ArrayList;
import java.util.List;

import com.gitee.easyopen.util.CopyUtil;
import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.mapper.SchMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.support.PageEasyui;
import com.gitee.fastmybatis.core.util.MapperUtil;

public class MyMapperUtil extends MapperUtil {

    public static <Entity, T> PageEasyui<T> queryToEasyui(SchMapper<Entity, ?> mapper, Query query, Class<T> clazz) {
        PageInfo pageInfo = query(mapper, query, PageInfo.class);
        PageEasyui<T> ret = new PageEasyui<>();
        List list = pageInfo.getList();
        List<T> retList = new ArrayList<>(list.size());
        try {
            for (Object object : list) {
                if (clazz == object.getClass()) {
                    retList.add((T) object);
                } else {
                    T t = clazz.newInstance();
                    CopyUtil.copyProperties(object, t);
                    retList.add(t);
                }
            }
            CopyUtil.copyProperties(pageInfo, ret);
            ret.setList(retList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static <Entity, T> PageEasyui<T> queryToEasyui(SchMapper<Entity, ?> mapper, Query query) {
        return MapperUtil.query(mapper, query, PageEasyui.class);
    }
}
