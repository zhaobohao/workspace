package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.PointInfoClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.PointInfoDto;
import com.dc3.common.model.PointInfo;
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
 * <p>位号配置信息 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_POINT_INFO_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = PointInfoClientHystrix.class)
public interface PointInfoClient {

    /**
     * 新增 PointInfo
     *
     * @param pointInfo PointInfo
     * @return PointInfo
     */
    @PostMapping("/add")
    R<PointInfo> add(@Validated(Insert.class) @RequestBody PointInfo pointInfo);

    /**
     * 根据 ID 删除 PointInfo
     *
     * @param id PointInfo Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 PointInfo
     *
     * @param pointInfo PointInfo
     * @return PointInfo
     */
    @PostMapping("/update")
    R<PointInfo> update(@Validated(Update.class) @RequestBody PointInfo pointInfo);

    /**
     * 根据 ID 查询 PointInfo
     *
     * @param id PointInfo Id
     * @return PointInfo
     */
    @GetMapping("/id/{id}")
    R<PointInfo> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 PointInfo
     *
     * @param pointInfoDto PointInfo Dto
     * @return Page<PointInfo>
     */
    @PostMapping("/list")
    R<Page<PointInfo>> list(@RequestBody(required = false) PointInfoDto pointInfoDto);

}
