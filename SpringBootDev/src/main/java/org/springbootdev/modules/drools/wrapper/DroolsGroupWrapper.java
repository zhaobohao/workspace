
package org.springbootdev.modules.drools.wrapper;


import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.drools.entity.DroolsGroup;
import org.springbootdev.modules.drools.vo.DroolsGroupVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
public class DroolsGroupWrapper extends BaseEntityWrapper<DroolsGroup, DroolsGroupVO> {

    public static DroolsGroupWrapper build() {
        return new DroolsGroupWrapper();
    }

	@Override
	public DroolsGroupVO entityVO(DroolsGroup droolsGroup) {
		DroolsGroupVO droolsGroupVO = BeanUtil.copy(droolsGroup, DroolsGroupVO.class);

		return droolsGroupVO;
	}

}
