package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.GroupClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.GroupDto;
import com.dc3.common.model.Group;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>GroupClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class GroupClientHystrix implements FallbackFactory<GroupClient> {

    @Override
    public GroupClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new GroupClient() {

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

        };
    }
}