package org.springclouddev.develop.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.vo.TableInfoVO;

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
