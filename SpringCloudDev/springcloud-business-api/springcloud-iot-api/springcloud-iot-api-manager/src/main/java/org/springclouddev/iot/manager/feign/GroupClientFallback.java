package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.GroupDto;
import org.springclouddev.iot.manager.entity.Group;
import org.springframework.stereotype.Component;

/**
 * <p>GroupClientHystrix
 */
@Slf4j
@Component
public class GroupClientFallback implements GroupClient {

    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<Group> add(Group group) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Group> update(Group group) {
        return R.fail(message);
    }

    @Override
    public R<Group> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Page<Group>> list(GroupDto groupDto) {
        return R.fail(message);
    }

}