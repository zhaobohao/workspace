package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springclouddev.integral.entity.ActRuleInfo;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface ActRuleInfoMapper extends SuperMapper<ActRuleInfo> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param actRuleInfo
	 * @return
	 */
	List<ActRuleInfo> selectActRuleInfoPage(IPage page, ActRuleInfo actRuleInfo);

	@Select("SELECT A.`ACT_CODE`,B.`ACT_NAME`,A.`RULE_ID`,A.`GRADE`,B.`RULE_TEAM` FROM TBL_ACT_RULE_INFO A JOIN  TBL_INTEGRAL_ACT B ON A.ACT_CODE=B.ACT_CODE ORDER BY a.`GRADE`")
	@Results({
			@Result(property="actCode",column="ACT_CODE"),
			@Result(property="actName",column="ACT_NAME"),
			@Result(property="ruleId",column="RULE_ID"),
			@Result(property="grade",column="GRADE"),
			@Result(property="ruleTeam",column="RULE_TEAM")
	})
	List<ActRuleInfo> selectAllActRuleInfo();
}
