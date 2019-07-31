package com.gitee.easyopen.util;

import java.util.Arrays;
import java.util.List;

import com.gitee.easyopen.bean.Pagable;

/**
 * @author tanghc
 */
public class ListUtil {


    /**
     * 对List进行分页
     * 
     * @param list 待分页list
     * @param pageIndex 页索引，从1开始
     * @param pageSize 每页大小，如果为0，返回原list
     * @return 返回结果集
     */
    @SuppressWarnings("unchecked")
    public static <T extends Pagable> List<T> page(List<T> list, int pageIndex, int pageSize) {
        if (pageSize == 0) {
            return list;
        } else {
            // 起始位置
            int start = ((pageIndex - 1) * pageSize);
            // 总记录数
            int total = list.size();
            // 剩余长度
            int leftLimit = total - start;
            // 偏移量
            int limit = pageSize > leftLimit ? leftLimit : pageSize;

            Pagable[] arr = list.toArray(new Pagable[total]);
            Pagable[] newArr = new Pagable[limit];

            System.arraycopy(arr, start, newArr, 0, limit);

            return (List<T>) Arrays.asList(newArr);
        }
    }
}
