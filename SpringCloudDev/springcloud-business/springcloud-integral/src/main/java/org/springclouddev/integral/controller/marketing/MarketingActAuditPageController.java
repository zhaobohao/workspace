package org.springclouddev.integral.controller.marketing;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.annotation.PreAuth;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.utils.StringUtil;
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.impl.MarketingActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api("营销审核活动相关API")
@RestController
public class MarketingActAuditPageController {
	@Autowired
	MarketingActService mktActService;
	private static Logger logger = LoggerFactory.getLogger(MarketingActPageController.class);
	
	@ApiOperation("营销活动审核分页查询")
	@GetMapping("/getMarketingActAuditList")
	@PreAuth("admin:marketingAct:query")
	public Object getAuditQueryListPage(@RequestParam(defaultValue="1") int page,
			  @RequestParam(defaultValue="10") int limit,
			  @RequestParam(required=false) String actCode,
			  @RequestParam(required=false) String actName,
			  @RequestParam(required=false) String beginTime,
										@RequestParam(required=false) String endTime						){
		Query query=new Query();
		query.setCurrent(page).setSize(limit);
		MarketingAct example = new MarketingAct();
		if(StringUtils.isNotBlank(actCode)){
			example.setActCode(actCode);
		}
		if(StringUtils.isNotBlank(actName)){
			example.setActName(actName);
		}
		QueryWrapper<MarketingAct> queryWrapper=Condition.getQueryWrapper(example);
		queryWrapper.orderByDesc("update_time");
		List<MarketingAct> list = new ArrayList<MarketingAct>();
		IPage<MarketingAct> page1 = new Page<MarketingAct>();
		try {
			list = mktActService.page(Condition.getPage(query),Condition.getQueryWrapper(example).lambda().ne(MarketingAct::getActStatus,"audit_status_tosub").ge((StringUtil.isNotBlank(beginTime)),MarketingAct::getBeginTime,beginTime).le((StringUtil.isNotBlank(endTime)),MarketingAct::getEndTime,endTime)).getRecords();
			if(list!=null && list.size()>0){
				for(MarketingAct marketAct:list){
					String statusName = LocalDictCache.getDicPrmById(marketAct.getActStatusId()).getName();
					String integralName = LocalDictCache.getDicPrmById(marketAct.getIntegralId()).getName();
					marketAct.setActStatus(statusName);
					marketAct.setIntegralType(integralName);
					list.add(marketAct);
				}
				page1.setTotal(((Page)list).getTotal()).setSize(limit).setCurrent(page).setRecords(list);
			}
		} catch (Exception e) {
			logger.info("查询营销活动列表异常："+e+e.getMessage());
			return R.fail(ReturnCode.SYSTEM_EXCEPTION);
		}
		return R.data(page1);
	}
	
	@ApiOperation("营销活动详情查看")
	@PostMapping("/detailMarketingActAuditList")
	@PreAuth("admin:marketingAct:detail")
	public Object updateAuditMarketActPage(@RequestBody Long actId){
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
