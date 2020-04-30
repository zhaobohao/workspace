package org.springclouddev.integral.controller.data_dic;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.DataDicPrm;
import org.springclouddev.integral.service.IMarketingActService;
import org.springclouddev.integral.service.actIntegral.IintegralActService;
import org.springclouddev.integral.service.data_dic.IDataDicPrmService;
import org.springclouddev.integral.service.ruleInfo.IRuleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("首页仪表接口")
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {
	
	@Autowired
	private IintegralActService actIntegralService;
	@Autowired
	private IMarketingActService marketingActService;
	@Autowired
	private IRuleInfoService ruleInfoService;

	@Autowired
	private IDataDicPrmService ddService;
	
	@ApiOperation("获得所有积分营销活动及当前用户个数")
	@GetMapping("info")
	public Object getAllPActCount(@RequestParam(defaultValue = "4") Integer statusId, @RequestParam(required = false) String depId) {
		DataDicPrm dataDicPrm=new DataDicPrm();
		dataDicPrm.setCode("audit_status");
		List<DataDicPrm> prms=ddService.qryDataDicPrms(dataDicPrm, -1, -1);
		String status=statusId==4?"audit_status_pass":statusId==3?"audit_status_nopass":
						statusId==2?"audit_status_wait":statusId==1?"audit_status_tosub":"";
		
		long actIntegralCount=actIntegralService.getStatusCount(status);
		long marketingActCount=marketingActService.getStatusCount(status);
		long ruleInfoCount=ruleInfoService.getStatusCount(status);
		long userCount=10;
		Map<String, Object> resultMap=new HashMap<>();
		resultMap.put("marketingActCount", marketingActCount);
		resultMap.put("actIntegralCount", actIntegralCount);
		resultMap.put("ruleInfoCount", ruleInfoCount);
		resultMap.put("userCount", userCount);
		
		return R.data(resultMap);
	}
}
