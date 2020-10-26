package org.springclouddev.crm.admin.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeptUserListByHrmBO {

    private List<Long> userIdList;

    private List<Integer> deptIdList;
}
