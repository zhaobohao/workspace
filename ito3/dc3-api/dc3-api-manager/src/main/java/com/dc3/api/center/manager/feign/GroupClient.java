package com.dc3.api.center.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.hystrix.GroupClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.GroupDto;
import com.dc3.common.model.Group;
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
 * <p>分组 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_MANAGER_GROUP_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = GroupClientHystrix.class)
public interface GroupClient {

    /**
     * 新增 Group
     *
     * @param group Group
     * @return Group
     */
    @PostMapping("/add")
    R<Group> add(@Validated(Insert.class) @RequestBody Group group);

    /**
     * 根据 ID 删除 Group
     *
     * @param id Group Id
     * @return Boolean
     */
    @PostMapping("/delete/{id}")
    R<Boolean> delete(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 修改 Group
     *
     * @param group Group
     * @return Group
     */
    @PostMapping("/update")
    R<Group> update(@Validated(Update.class) @RequestBody Group group);

    /**
     * 根据 ID 查询 Group
     *
     * @param id Group Id
     * @return Group
     */
    @GetMapping("/id/{id}")
    R<Group> selectById(@NotNull @PathVariable(value = "id") Long id);

    /**
     * 分页查询 Group
     *
     * @param groupDto Group Dto
     * @return Page<Group>
     */
    @PostMapping("/list")
    R<Page<Group>> list(@RequestBody(required = false) GroupDto groupDto);

}
