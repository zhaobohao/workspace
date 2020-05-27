package org.dbchain.blockmanager.manager;

import org.dbchain.blockmanager.bean.MemberData;
import org.dbchain.blockmanager.model.Member;
import org.dbchain.blockmanager.repository.MemberRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaobo create on 2020/3/7.
 */
@Component
public class MemberManager {
    @Resource
    private MemberRepository memberRepository;

    /**
     * 查询某成员的groupId
     * @param memberName
     * 成员名
     * @return
     * 分组id
     */
    public String findGroupId(String memberName) {
        Member member = memberRepository.findFirstByName(memberName);
        if (member != null) {
            return member.getGroupId();
        }
        return null;
    }

    public MemberData memberData(String name, String appId, String ip) {
        MemberData memberData = new MemberData();
        Member member = memberRepository.findFirstByAppId(appId);
        //客户不存在
        if (member == null) {
            memberData.setCode(-1);
            memberData.setMessage("客户不存在");
        } else if (!member.getName().equals(name)) {
            //name错误
            memberData.setCode(-2);
            memberData.setMessage("name错误");
        } else if (!member.getIp().equals(ip)) {
            //ip错误
            memberData.setCode(-3);
            memberData.setMessage("ip错误");
        } else {
            memberData.setCode(0);
            String groupId = findGroupId(name);
            //如果该member是合法的，则返回给他联盟内所有的成员列表
            List<Member> members = memberRepository.findByGroupId(groupId);
            memberData.setMembers(members);
        }
        return memberData;
    }

}
