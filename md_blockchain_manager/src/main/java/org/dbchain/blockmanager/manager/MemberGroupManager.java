package org.dbchain.blockmanager.manager;

import org.dbchain.blockmanager.repository.MemberGroupRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhaobo create on 2020/3/7.
 */
@Component
public class MemberGroupManager {
    @Resource
    private MemberGroupRepository memberGroupRepository;
}
