

package com.dc3.center.manager.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.center.manager.service.GroupService;
import com.dc3.api.center.manager.feign.GroupClient;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.GroupDto;
import com.dc3.common.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>设备 Client 接口实现
 *
 * @author pnoker
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
                return R.ok(add);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

    @Override
    public R<Boolean> delete(Long id) {
        try {
            return groupService.delete(id) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Group> update(Group group) {
        try {
            Group update = groupService.update(group);
            if (null != update) {
                return R.ok(update);
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
                return R.ok(select);
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
                return R.ok(page);
            }
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
        return R.fail();
    }

}
