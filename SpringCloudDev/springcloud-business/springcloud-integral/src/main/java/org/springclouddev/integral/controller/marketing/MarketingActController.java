package org.springclouddev.integral.controller.marketing;

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
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.impl.MarketingActService;
import org.springclouddev.integral.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Api("营销活动相关API")
@RestController
@RequestMapping("/marketingAct")
public class MarketingActController {
	private static Logger logger = LoggerFactory.getLogger(MarketingActController.class);
	@Autowired
	private MarketingActService mktActService;
	@ApiOperation("营销活动分页查询")
	@GetMapping("/list")
	@PreAuth("hasResource('')")
	public Object queryMarketingActListByPage(Query query,
											  Map<String,Object> map
			) throws ParseException{
		query.setDescs("updateTime");
		List<MarketingAct> list = new ArrayList<MarketingAct>();
		IPage<MarketingAct> page1 = new Page<MarketingAct>();
		try {
			list = mktActService.page(Condition.getPage(query),Condition.getQueryWrapper(map,MarketingAct.class)).getRecords();
			if(list!=null && list.size()>0){
				for(MarketingAct marketAct:list){
					String statusName = LocalDictCache.getDicPrmById(marketAct.getActStatusId()).getName();
					String integralName = LocalDictCache.getDicPrmById(marketAct.getIntegralId()).getName();
					marketAct.setActStatus(statusName);
					marketAct.setIntegralType(integralName);
					list.add(marketAct);
				}
				page1.setTotal(((Page)list).getTotal()).setSize(query.getSize()).setCurrent(query.getCurrent()).setRecords(list);
			}
			
		} catch (Exception e) {
			logger.info("查询营销活动列表异常：" + e.getMessage(), e);
			return R.fail(ReturnCode.SYSTEM_EXCEPTION);
		}
		return R.data(page1);
	}

	@ApiOperation("营销活动新增")
	@PostMapping("/add")
	@PreAuth("admin:marketingAct:add")
	public Object addMarketAct(@RequestBody MarketingAct mktAct){
		try {
			mktAct.setActStatusId("audit_status_tosub");//待提交
			mktActService.insertMarketingAct(mktAct);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info("新增营销活动失败："+e+e.getMessage());
			return R.fail(ReturnCode.ADD_EXCEPTION);
		}
		
		
	}
	@ApiOperation("修改营销活动")
	@PostMapping("/upd")
	public Object updMarketAct(@RequestBody MarketingAct mktAct) throws ParseException{
		try {
			mktAct.setActStatusId("audit_status_tosub");//待提交
			mktActService.updateMarketingActById(mktAct);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info("更新失败："+e+e.getMessage());
			return R.fail(ReturnCode.UPD_EXCEPTION);
		}
		
	}
	@ApiOperation("删除营销活动")
	@PostMapping("/delete")
	@PreAuth("admin:marketingAct:delete")
	public Object deleteMarketActById( @RequestParam Long id){
		try {
			mktActService.deleteMarketingActById(id);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info(""
					+ "删除营销活动失败："+e+e.getMessage());
			return R.fail(ReturnCode.DEL_EXCEPTION);
		}
	}
	
	@ApiOperation("营销活动送审")
	@PostMapping("/audit")
	@PreAuth("admin:marketingAct:audit")
	public Object auditMarketActById(@RequestBody MarketingAct mktAct) throws ParseException{
		MarketingAct act = new MarketingAct();
		act.setActCode(mktAct.getActCode());
		act.setActStatusId("audit_status_wait");//待审核

		try {
			mktActService.updMarketingAct(act, mktAct);
			return R.success("操作成功");
		} catch (Exception e) {
			logger.info("送审失败："+e+e.getMessage());
			return R.fail(ReturnCode.AUDIT_EXCEPTION);
		}
	}
	
	@ApiOperation("营销活动列表")
	@GetMapping("/getAllMktList")
	public Object getAllMarketingActList(){
		return R.data(mktActService.list());
	}
	
	@ApiOperation("有效的营销活动列表查询")
	@GetMapping("/aliveList")
	public Object queryliveMarketingActList() throws ParseException{
		String statusId = "audit_status_pass";
		String currDate = DateFormatUtil.dateToString(new Date());
		List<MarketingAct> list ;
		List<MarketingAct> list2 = new LinkedList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("actStatusId",statusId);
		map.put("beginTime_lt",currDate);
		map.put("endTime_gt",currDate);

		list = mktActService.list(Condition.getQueryWrapper(map,MarketingAct.class));
		if(list!=null && list.size()>0){
			for(MarketingAct act:list){
				MarketingAct mkt = new MarketingAct();
				mkt.setActCode(act.getActCode());
				mkt.setActName(act.getActName());
				list2.add(mkt);
			}
		}
		return R.data(list2);
	}
	
}
