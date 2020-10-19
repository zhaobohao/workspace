package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.valid.Insert;
import org.springclouddev.iot.common.valid.Update;
import org.springclouddev.iot.manager.dto.GroupDto;
import org.springclouddev.iot.manager.entity.Group;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

/**
 * <p>分组 FeignClient
 */
@FeignClient(path = Common.Service.DC3_MANAGER_GROUP_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = GroupClientFallback.class)
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
