
package org.springclouddev.drools.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.drools.entity.DroolsGroup;
import org.springclouddev.drools.vo.DroolsGroupVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
public class DroolsGroupWrapper extends BaseEntityWrapper<DroolsGroup, DroolsGroupVO>  {

    public static DroolsGroupWrapper build() {
        return new DroolsGroupWrapper();
    }

	@Override
	public DroolsGroupVO entityVO(DroolsGroup droolsGroup) {
		DroolsGroupVO droolsGroupVO = BeanUtil.copy(droolsGroup, DroolsGroupVO.class);

		return droolsGroupVO;
	}

}
