package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.Dict;
import org.springclouddev.system.vo.DeptVO;
import org.springclouddev.system.vo.DictVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author zhaobohao
 */
public interface DictMapper extends SuperMapper<Dict> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dict
	 * @return
	 */
	List<DictVO> selectDictPage(IPage page, DictVO dict);

	/**
	 * 获取字典表对应中文
	 *
	 * @param code    字典编号
	 * @param dictKey 字典序号
	 * @return
	 */
	String getValue(String code, String dictKey);

	/**
	 * 获取字典表
	 *
	 * @param code 字典编号
	 * @return
	 */
	List<Dict> getList(String code);

	/**
	 *  获取树形节点,获取指定parentId这一层的数据
	 * @param parentId 如果为空，返回所有树形结构数据
	 * @return
	 */
	List<DictVO> tree( String parentId);
}
