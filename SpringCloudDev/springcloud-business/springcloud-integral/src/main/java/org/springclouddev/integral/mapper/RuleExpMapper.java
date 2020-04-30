package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.RuleExp;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 规则表达式表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface RuleExpMapper extends SuperMapper<RuleExp> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ruleExp
	 * @return
	 */
	List<RuleExp> selectRuleExpPage(IPage page, RuleExp ruleExp);

    List<RuleExp> selectAllRuleExp();
}
