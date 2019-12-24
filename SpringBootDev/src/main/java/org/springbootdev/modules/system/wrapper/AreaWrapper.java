
package org.springbootdev.modules.system.wrapper;


import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.system.entity.Area;
import org.springbootdev.modules.system.vo.AreaVO;

/**
 * 行政区划包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public class AreaWrapper extends BaseEntityWrapper<Area, AreaVO> {

    public static AreaWrapper build() {
        return new AreaWrapper();
    }

	@Override
	public AreaVO entityVO(Area area) {
		AreaVO areaVO = BeanUtil.copy(area, AreaVO.class);

		return areaVO;
	}

}
