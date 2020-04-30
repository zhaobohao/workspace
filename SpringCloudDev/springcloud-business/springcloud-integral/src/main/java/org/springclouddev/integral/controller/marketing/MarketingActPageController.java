package org.springclouddev.integral.controller.marketing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.impl.MarketingActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api("营销活动相关API")
@RestController
public class MarketingActPageController {
	@Autowired
	MarketingActService mktActService;
	private static Logger logger = LoggerFactory.getLogger(MarketingActPageController.class);
	@ApiOperation("营销活动修改页面")
	@PostMapping("/updMarketingActList")
	@PreAuth("admin:marketingAct:upd")
	public Object updateMarketActPage(@RequestBody Long actId){
		MarketingAct mktAct = new MarketingAct();
		List<MarketingAct> list =new ArrayList<MarketingAct>();
		mktAct = mktActService.queryById(actId);
		if(mktAct!=null){
			String statusName = LocalDictCache.getDicPrmById(mktAct.getActStatusId()).getName();
			mktAct.setActStatus(statusName);
			list.add(mktAct);
		}
		return R.data(list);
		
	}
	
	@ApiOperation("营销活动详情页面")
	@PostMapping("/detailMarketingActList")
	@PreAuth("admin:marketingAct:detail")
	public Object detailMarketActPage(@RequestBody Long actId){
		MarketingAct mktAct = new MarketingAct();
		List<MarketingAct> list =new ArrayList<MarketingAct>();
		mktAct = mktActService.queryById(actId);
		if(mktAct!=null){
			String statusName = LocalDictCache.getDicPrmById(mktAct.getActStatusId()).getName();
			mktAct.setActStatus(statusName);
			list.add(mktAct);
		}
		return R.data(list);
	}
}
