
package org.springbootdev.modules.develop.wrapper;

import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.modules.develop.entity.TableInfo;
import org.springbootdev.modules.develop.vo.TableInfoVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public class TableInfoWrapper extends BaseEntityWrapper<TableInfo, TableInfoVO> {

    public static TableInfoWrapper build() {
        return new TableInfoWrapper();
    }

	@Override
	public TableInfoVO entityVO(TableInfo tableInfo) {
		TableInfoVO tableInfoVO = BeanUtil.copy(tableInfo, TableInfoVO.class);

		return tableInfoVO;
	}

}
