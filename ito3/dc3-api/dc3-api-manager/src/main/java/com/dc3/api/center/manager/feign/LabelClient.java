package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.LabelClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.LabelDto;
import com.dc3.common.model.Label;
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
 * <p>标签 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_LABEL_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = LabelClientHystrix.class)
public interface LabelClient {

    /**
     * 新增 Label
     *
     * @param label Label
     * @return Label
     */
    @PostMapping("/add")
    R<Label> add(@Validated(Insert.class) @RequestBody Label label);

    /**
     * 根据 ID 删除 Label
     *
     * @param id Label Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Label
     *
     * @param label Label
     * @return Label
     */
    @PostMapping("/update")
    R<Label> update(@Validated(Update.class) @RequestBody Label label);

    /**
     * 根据 ID 查询 Label
     *
     * @param id Label Id
     * @return Label
     */
    @GetMapping("/id/{id}")
    R<Label> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 Label
     *
     * @param labelDto Label Dto
     * @return Page<Label>
     */
    @PostMapping("/list")
    R<Page<Label>> list(@RequestBody(required = false) LabelDto labelDto);

}
