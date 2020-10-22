package org.springclouddev.develop.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.vo.DbInstanceVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public class DbInstanceWrapper extends BaseEntityWrapper<DbInstance, DbInstanceVO> {

    public static DbInstanceWrapper build() {
        return new DbInstanceWrapper();
    }

	@Override
	public DbInstanceVO entityVO(DbInstance dbInstance) {
		DbInstanceVO dbInstanceVO = BeanUtil.copy(dbInstance, DbInstanceVO.class);

		return dbInstanceVO;
	}

}
