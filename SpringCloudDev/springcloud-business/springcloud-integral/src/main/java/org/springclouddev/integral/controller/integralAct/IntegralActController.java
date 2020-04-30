package org.springclouddev.integral.controller.integralAct;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.jackson.JsonUtil;
import org.springclouddev.integral.entity.IntegralAct;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.actIntegral.IintegralActService;
import org.springclouddev.integral.vo.ActIntegralInfoVo;
import org.springclouddev.integral.wrapper.ActIntegralInfoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Api("积分活动接口")
@RestController
@RequestMapping("/integralAct")
public class IntegralActController {
	
	@Autowired
	private IintegralActService actIntegralService;

	/**
	 * 添加积分活动
	 */
	@ApiOperation("新增IntegralAct")
	@PostMapping("/addIntegralAct")
	public Object addIntegralAct(@RequestBody IntegralAct actIntegralInfo) {
		actIntegralInfo.setState("audit_status_tosub");
		String resultMsg = actIntegralService.insertIntegralAct(actIntegralInfo);
		if ("缺少必输项".equals(resultMsg)) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if ("插入数据库失败".equals(resultMsg)) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}

	/**
	 * 修改积分活动
	 */
	@ApiOperation("修改IntegralAct")
	@PostMapping("/updateIntegralAct")
	public Object updateIntegralAct(@RequestBody IntegralAct actIntegralInfo) {
		String resultMsg = actIntegralService.updateActivity(actIntegralInfo);
		if (!"修改成功".equals(resultMsg)) {
			R.fail("操作失败");
		}
		return R.success("操作成功");

	}

	/**
	 * 根据条件查询
	 */
	@ApiOperation("根据给定的条件查询")
	@GetMapping("/queryByInfo")
	public Object queryIntegralActByInfo(@RequestParam(value = "actCode", required = false) String actCode,
			@RequestParam(value = "actName", required = false) String actName) {

		int page = 1;
		int limit = 10;
		List<IntegralAct> page1 = actIntegralService.findPage(page, limit, actCode, actName);
		List<ActIntegralInfoVo> list1 = new ArrayList<ActIntegralInfoVo>();
		for (IntegralAct actIntegralInfo : page1) {
			ActIntegralInfoVo actIntegralInfoVo = ActIntegralInfoWrapper.build().entityVO(actIntegralInfo);
			list1.add(actIntegralInfoVo);
		}
		return R.data(list1);

	}

	/**
	 * 根据活动id删除积分活动
	 */
	@ApiOperation("通过actCode删除积分活动")
	@PostMapping("/deleteByActCode")
	public Object deleteIntegeralAct(@RequestBody String actCode) {
		String actCode1 = JsonUtil.getString(actCode, "actCode");
		String resultMsg = actIntegralService.deleteByActCode(actCode1);
		if ("缺少必输项".equals(resultMsg)) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		return R.success("操作成功");
	}

	/**
	 * 积分活动提交审核
	 */
	@ApiOperation("提交审核")
	@PostMapping("/pushAudit")
	public Object updateIntegralActStatusToOne(@RequestBody String reqBody) {
		String actCode = JsonUtil.getString(reqBody, "actCode");
		String resultMsg = actIntegralService.pushToAudit(actCode);
		if ("缺少必输项".equals(resultMsg)) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if ("该状态的活动不允许提交二次提交审核".equals(resultMsg)) {
			return R.fail(ReturnCode.THIS_ITEM_CANT_COMMITTE);
		}
		return R.success("操作成功");
	}

	/**
	 * 积分活动审核
	 */
	@ApiOperation("审核通过或者拒绝")
	@PostMapping("/integralActAuditPass")
	public Object auditPass(@RequestBody IntegralAct actIntegralInfo) {
		String resultMsg = actIntegralService.auditPass(actIntegralInfo);
		if ("缺少必输项".equals(resultMsg)) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if ("对不起，不能审核该状态的活动".equals(resultMsg)) {
			return R.fail(ReturnCode.THE_WRONG_STATUS);
		}
		return R.success("操作成功");

	}



	/**
	 * 分页查询积分活动管理业内容
	 */
	@ApiOperation("积分管理页面--分页查询")
	@GetMapping("/findAllIntegralAct")
	public Object findIntegralactPage(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(value = "actCode", required = false) String actCode,
			@RequestParam(value = "actName", required = false) String actName) {

		List<IntegralAct> page1 = actIntegralService.findPage(page, limit, actCode, actName);

		IPage<ActIntegralInfoVo> page2 = new Page<>();
		List<ActIntegralInfoVo> list=new LinkedList<>();
		for (IntegralAct actIntegralInfo : page1) {
			ActIntegralInfoVo actIntegralInfoVo = ActIntegralInfoWrapper.build().entityVO(actIntegralInfo);
			list.add(actIntegralInfoVo);
		}
		page2.setRecords(list);
		page2.setSize(limit);
		page2.setCurrent(page);
		page2.setTotal(page1.size());
		return R.data(page2);

	}
	
	/**
	  * 审核页面显示查询--只显示待审核和审核通过 
	  */
	@ApiOperation("积分活动审核页面--分页查询")
	@GetMapping("/findAboutAuditIntegralAct")
	public  Object findAboutAuditPage(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit,
			@RequestParam(value = "actCode", required = false) String actCode,
			@RequestParam(value = "actName", required = false) String actName) {
		List<IntegralAct> page1 = actIntegralService.queryAboutAduit(page, limit, actCode, actName);
		if(page1==null&&page1.size()==0) {
			return R.fail(ReturnCode.NO_THIS_ITEM_IN_DATABASE);
		}
		List<ActIntegralInfoVo> list1 = new ArrayList<ActIntegralInfoVo>();
		for (IntegralAct actIntegralInfo : page1) {
			ActIntegralInfoVo actIntegralInfoVo = ActIntegralInfoWrapper.build().entityVO(actIntegralInfo);
			list1.add(actIntegralInfoVo);
		}

		return R.data(list1);

	}
    @ApiOperation("下拉框查询所有合适的积分活动")
    @GetMapping("/queryRightIntegralAct")
	public Object  queryRightIntegralAct() {
		List<IntegralAct> list = actIntegralService.queryAllAct();
		if(list==null&&list.size()==0) {
			R.fail("操作失败");
		}
		
		return  R.data(list);
	}
}
