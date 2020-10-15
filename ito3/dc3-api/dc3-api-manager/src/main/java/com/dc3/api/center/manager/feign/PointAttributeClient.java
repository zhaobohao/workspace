package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.PointAttributeClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.PointAttributeDto;
import com.dc3.common.model.PointAttribute;
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
 * <p>位号配置属性 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_POINT_ATTRIBUTE_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = PointAttributeClientHystrix.class)
public interface PointAttributeClient {

    /**
     * 新增 PointAttribute
     *
     * @param pointAttribute PointAttribute
     * @return PointAttribute
     */
    @PostMapping("/add")
    R<PointAttribute> add(@Validated(Insert.class) @RequestBody PointAttribute pointAttribute);

    /**
     * 根据 ID 删除 PointAttribute
     *
     * @param id PointAttribute Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 PointAttribute
     *
     * @param pointAttribute PointAttribute
     * @return PointAttribute
     */
    @PostMapping("/update")
    R<PointAttribute> update(@Validated(Update.class) @RequestBody PointAttribute pointAttribute);

    /**
     * 根据 ID 查询 PointAttribute
     *
     * @param id PointAttribute Id
     * @return PointAttribute
     */
    @GetMapping("/id/{id}")
    R<PointAttribute> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 PointAttribute
     *
     * @param pointAttributeDto PointAttribute Dto
     * @return Page<PointAttribute>
     */
    @PostMapping("/list")
    R<Page<PointAttribute>> list(@RequestBody(required = false) PointAttributeDto pointAttributeDto);

}
