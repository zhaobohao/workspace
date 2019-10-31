
package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.RoleMenu;
import org.springclouddev.system.vo.RoleMenuVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */
public interface RoleMenuMapper extends SuperMapper<RoleMenu> {

	/**
	 * 自定义分页
	 * @param page
	 * @param roleMenu
	 * @return
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

}
