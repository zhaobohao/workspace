package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.PointClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.PointDto;
import com.dc3.common.model.Point;
import com.dc3.common.valid.Insert;
import com.dc3.common.valid.Update;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * <p>位号 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_MANAGER_POINT_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = PointClientHystrix.class)
public interface PointClient {

    /**
     * 新增 Point
     *
     * @param point Point
     * @return Point
     */
    @PostMapping("/add")
    R<Point> add(@Validated(Insert.class) @RequestBody Point point);

    /**
     * 根据 ID 删除 Point
     *
     * @param id Point Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Point
     *
     * @param point Point
     * @return Point
     */
    @PostMapping("/update")
    R<Point> update(@Validated(Update.class) @RequestBody Point point);

    /**
     * 根据 ID 查询 Point
     *
     * @param id Point Id
     * @return Point
     */
    @GetMapping("/id/{id}")
    R<Point> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 Point
     *
     * @param pointDto Point Dto
     * @return Page<Point>
     */
    @PostMapping("/list")
    R<Page<Point>> list(@RequestBody(required = false) PointDto pointDto);

}
