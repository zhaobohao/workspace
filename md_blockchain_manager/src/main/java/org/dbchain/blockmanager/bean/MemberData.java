package org.dbchain.blockmanager.bean;

import org.dbchain.blockmanager.model.Member;

import java.util.List;

/**
 * @author zhaobo create on 2020/3/19.
 */
public class MemberData extends BaseData {
    private List<Member> members;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
