package org.springclouddev.iot.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.GroupDto;
import org.springclouddev.iot.manager.entity.Group;
import org.springclouddev.iot.manager.feign.GroupClient;
import org.springclouddev.iot.manager.service.GroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>设备 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_GROUP_URL_PREFIX)
public class GroupApi implements GroupClient {
    @Resource
    private GroupService groupService;

    @Override
    public R<Group> add(Group group) {
        try {
            Group add = groupService.add(group);
            if (null != add) {
                return R.data(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(Long id) {
        try {
            return groupService.delete(id) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Group> update(Group group) {
        try {
            Group update = groupService.update(group);
            if (null != update) {
                return R.data(update);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Group> selectById(Long id) {
        try {
            Group select = groupService.selectById(id);
            if (null != select) {
                return R.data(select);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Page<Group>> list(GroupDto groupDto) {
        try {
            Page<Group> page = groupService.list(groupDto);
            if (null != page) {
                return R.data(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
