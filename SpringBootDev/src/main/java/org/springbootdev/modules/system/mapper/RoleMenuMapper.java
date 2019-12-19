
package org.springbootdev.modules.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.system.entity.RoleMenu;
import org.springbootdev.modules.system.vo.RoleMenuVO;

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
