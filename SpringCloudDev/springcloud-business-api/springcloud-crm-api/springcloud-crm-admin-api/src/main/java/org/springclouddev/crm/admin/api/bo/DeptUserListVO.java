package org.springclouddev.crm.admin.api.bo;

import org.springclouddev.crm.admin.api.vo.HrmSimpleUserVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeptUserListVO {


    private List<DeptVO> deptList;

    private List<HrmSimpleUserVO> userList;
}
