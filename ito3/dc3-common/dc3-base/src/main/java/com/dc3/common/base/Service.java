package com.dc3.common.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 基础服务类接口
 *
 * @author pnoker
 */
public interface Service<T, D> {
    /**
     * 新增
     *
     * @param type Object
     * @return Object
     */
    T add(T type);

    /**
     * 删除
     *
     * @param id Object Id
     * @return Boolean
     */
    boolean delete(Long id);

    /**
     * 更新
     *
     * @param type Object
     * @return Object
     */
    T update(T type);

    /**
     * 通过 ID 查询
     *
     * @param id Object Id
     * @return Object
     */
    T selectById(Long id);

    /**
     * 获取带分页、排序
     *
     * @param dto Object Dto
     * @return Page<Object>
     */
    Page<T> list(D dto);

    /**
     * 统一接口 模糊查询构造器
     *
     * @param dto Object Dto
     * @return QueryWrapper
     */
    LambdaQueryWrapper<T> fuzzyQuery(D dto);

}
