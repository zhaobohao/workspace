
package org.springclouddev.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springclouddev.core.tool.node.ForestNodeMerger;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.StringPool;
import org.springclouddev.system.entity.Dict;
import org.springclouddev.system.mapper.DictMapper;
import org.springclouddev.system.service.IDictService;
import org.springclouddev.system.vo.DictVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springclouddev.common.cache.CacheNames.DICT_LIST;
import static org.springclouddev.common.cache.CacheNames.DICT_VALUE;

/**
 * 服务实现类
 *
 * @author zhaobohao
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

	@Override
	public IPage<DictVO> selectDictPage(IPage<DictVO> page, DictVO dict) {
		return page.setRecords(baseMapper.selectDictPage(page, dict));
	}

	@Override
	public List<DictVO> tree(String parentId) {
		return ForestNodeMerger.merge(baseMapper.tree(parentId));
	}

	@Override
	@Cacheable(cacheNames = DICT_VALUE, key = "#code+'_'+#dictKey")
	public String getValue(String code, Integer dictKey) {
		return Func.toStr(baseMapper.getValue(code, dictKey), StringPool.EMPTY);
	}

	@Override
	@Cacheable(cacheNames = DICT_LIST, key = "#code")
	public List<Dict> getList(String code) {
		return baseMapper.getList(code);
	}

	@Override
	@CacheEvict(cacheNames = {DICT_LIST, DICT_VALUE}, allEntries = true)
	public boolean submit(Dict dict) {
		LambdaQueryWrapper<Dict> lqw = Wrappers.<Dict>query().lambda().eq(Dict::getCode, dict.getCode()).eq(Dict::getDictKey, dict.getDictKey());
		Integer cnt = baseMapper.selectCount((Func.isEmpty(dict.getId())) ? lqw : lqw.notIn(Dict::getId, dict.getId()));
		if (cnt > 0) {
			throw new ApiException("当前字典键值已存在!");
		}
		if(dict.getParentId()!=null && 0!=dict.getParentId())
		{
	      Dict parent=baseMapper.selectById(dict.getParentId());
	      parent.setIsLeaf(1);
	      baseMapper.updateById(parent);
		}

		return saveOrUpdate(dict);
	}
}
