
package org.springbootdev.modules.drools.wrapper;


import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.drools.entity.DroolsRuls;
import org.springbootdev.modules.drools.vo.DroolsRulsVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
public class DroolsRulsWrapper extends BaseEntityWrapper<DroolsRuls, DroolsRulsVO> {

    public static DroolsRulsWrapper build() {
        return new DroolsRulsWrapper();
    }

	@Override
	public DroolsRulsVO entityVO(DroolsRuls droolsRuls) {
		DroolsRulsVO droolsRulsVO = BeanUtil.copy(droolsRuls, DroolsRulsVO.class);

		return droolsRulsVO;
	}

}
