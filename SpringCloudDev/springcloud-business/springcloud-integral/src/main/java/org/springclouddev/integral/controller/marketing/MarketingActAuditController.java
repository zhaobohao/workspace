package org.springclouddev.integral.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.impl.MarketingActService;
import org.springclouddev.integral.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

@Api("营销审核活动相关API")
@RestController
public class MarketingActAuditController {
	private static Logger logger = LoggerFactory.getLogger(MarketingActController.class);
	@Autowired
	private MarketingActService mktActService;
	
	@ApiOperation("营销活动审核")
	@PostMapping("/marketingActAudit/audit")
	public Object auditMarketActById(@RequestBody MarketingAct mktAct) throws ParseException{
		MarketingAct example = new MarketingAct();
		example.setActCode(mktAct.getActCode());
		MarketingAct act = new MarketingAct();
		act.setActCode(mktAct.getActCode());
		act.setActStatusId(mktAct.getActStatusId());
		act.setReviewComments(mktAct.getReviewComments());
		try {
			mktActService.updMarketingAct(act, example);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info("审核失败："+e+e.getMessage());
			return R.fail(ReturnCode.AUDIT_EXCEPTION);
		}
	}
	
	
}
