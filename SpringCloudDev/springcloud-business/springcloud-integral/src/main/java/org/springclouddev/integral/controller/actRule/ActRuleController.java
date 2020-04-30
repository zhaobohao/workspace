package org.springclouddev.integral.controller.actRule;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.ActRuleInfo;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.actRule.IActRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actRule")
@Api("积分活动规则接口")
public class ActRuleController {

		@Autowired
		private IActRuleService actRuleService;


		@GetMapping("/queryActRuleByActCode")
		@ApiOperation("根据actCode查询活动规则")
		public R<List<ActRuleInfo>> queryActRuleByActCode( @RequestParam String actCode) {
			List<ActRuleInfo> list =  actRuleService.queryActRuleByActCode(actCode);
			if(list==null&&list.size()==0) {
				return R.fail(ReturnCode.NO_THIS_ITEM_IN_DATABASE);
			}
			return R.data(list);
		}
	
	
	@PostMapping("/deleteByActCode")
	@ApiOperation("通过actCode删除积分活动规则")
	public Object deleteByActCode( @RequestBody String jsonStr) {
		Map map = (Map) JSON.parse(jsonStr);
		int  resultCode = actRuleService.deleteActRuleBy((String)map.get("actCode"));
		if(resultCode==-1) {
			return R.fail(ReturnCode. DELETE_ITEM_FAILE);
		}
		return R.success("操作成功");
	}
	
	
	@PostMapping("/ruleRelatedAct")
	@ApiOperation("积分活动绑定规则插入数据库")
	public Object batchInsert(@RequestBody JSONObject data) {
		String actCode = data.getStr("actCode");
		String type = data.getStr("type");
		JSONArray jsonArr=  data.getJSONArray("boundRules");
		List<ActRuleInfo> list = new ArrayList<ActRuleInfo>();
		int i = 1;
		for (Object jobj : jsonArr) {
			JSONObject obj = (JSONObject) jobj;
			ActRuleInfo actRule = new ActRuleInfo();
			actRule.setActCode(actCode);
			actRule.setRuleId(obj.getLong("key"));
			actRule.setRuleName(obj.getStr("label"));
			actRule.setType(type);
			actRule.setGrade(String.valueOf(i));
			if("rule_type_sum".equals(type)) {
				actRule.setGrade("");
			}
			list.add(actRule);
			i++;
		}
		
		
		int resultCode = actRuleService.insertBatchActRule(list);
		if (resultCode == -1) {
			return R.fail(ReturnCode.PARAM_IS_NULL);
		}

		return R.success("操作成功");
	}
	

}
