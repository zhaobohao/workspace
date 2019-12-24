
package org.springclouddev.system.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.system.entity.Area;
import org.springclouddev.system.vo.AreaVO;

/**
 * 行政区划包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public class AreaWrapper extends BaseEntityWrapper<Area, AreaVO>  {

    public static AreaWrapper build() {
        return new AreaWrapper();
    }

	@Override
	public AreaVO entityVO(Area area) {
		AreaVO areaVO = BeanUtil.copy(area, AreaVO.class);

		return areaVO;
	}

}
