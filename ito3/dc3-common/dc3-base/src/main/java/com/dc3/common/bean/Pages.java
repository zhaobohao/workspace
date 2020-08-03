package com.dc3.common.bean;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础查询类，其中包括分页以及排序
 *
 * @author pnoker
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Pages implements Serializable {
    private static final long serialVersionUID = 1L;

    private long current = 1;
    private long size = 20;
    private long startTime;
    private long endTime;
    private List<OrderItem> orders = new ArrayList<>();

    public <T> Page<T> convert() {
        Page<T> page = new Page<>();
        BeanUtils.copyProperties(this, page);
        page.getOrders().add(OrderItem.desc("create_time"));
        return page;
    }

}
