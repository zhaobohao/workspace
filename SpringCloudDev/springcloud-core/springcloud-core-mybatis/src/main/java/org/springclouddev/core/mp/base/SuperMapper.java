package org.springclouddev.core.mp.base;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 演示 mapper 父类，注意这个类不要让 mp 扫描到！！
 */
public interface SuperMapper<T> extends BaseMapper<T> {

    // 这里可以放一些公共的方法,在MyLogicSqlInjector里注入的方法，要在这里声明才可以使用
    /**
     * 自定义通用方法
     */
//    Integer deleteAll();

//    int myInsertAll(T entity);

    /**
     * 如果要自动填充，@{@code Param}(xx) xx参数名必须是 list/collection/array 3个的其中之一
     *
     * @param batchList
     * @return
     */
//    int mysqlInsertAllBatch(@Param("list") List<T> batchList);
}

