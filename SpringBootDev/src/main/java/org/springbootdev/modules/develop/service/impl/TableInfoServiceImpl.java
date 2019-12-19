
package org.springbootdev.modules.develop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.core.mp.support.Condition;
import org.springbootdev.modules.develop.entity.DbInstance;
import org.springbootdev.modules.develop.entity.TableInfo;
import org.springbootdev.modules.develop.mapper.TableInfoMapper;
import org.springbootdev.modules.develop.service.IDbInstanceService;
import org.springbootdev.modules.develop.service.ITableInfoService;
import org.springbootdev.modules.develop.vo.TableInfoVO;
import org.springbootdev.modules.develop.wrapper.TableInfoWrapper;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Service
@AllArgsConstructor
public class TableInfoServiceImpl extends BaseServiceImpl<TableInfoMapper, TableInfo> implements ITableInfoService {
	private IDbInstanceService dbInstanceService;
	@Override
	public IPage<TableInfoVO> selectTableInfoPage(IPage<TableInfoVO> page, TableInfoVO tableInfo) {
		return page.setRecords(baseMapper.selectTableInfoPage(page, tableInfo));
	}

	@Override
	public List<TableInfoVO> tree(String parentId) {
		List<TableInfoVO> retList=new LinkedList<TableInfoVO>();
	List<DbInstance> dbInstancesList=	dbInstanceService.list();
	dbInstancesList.forEach(dbInstance -> {
		TableInfoVO tableInfoVO=new TableInfoVO();
		tableInfoVO.setId(dbInstance.getId());
		tableInfoVO.setIsLeaf(1);
		tableInfoVO.setParentId(0L);
		tableInfoVO.setName(dbInstance.getName());
		tableInfoVO.setChildren(baseMapper.selectList(Condition.getQueryWrapper(new TableInfo().setDbInstanceId(tableInfoVO.getId()).setCategory(1))).stream().map(tableinfo->TableInfoWrapper.build().entityVO(tableinfo)).collect(Collectors.toList()));
		retList.add(tableInfoVO);
	});
		return retList;
	}

}
