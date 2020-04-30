package org.springclouddev.integral.controller.data_dic;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.GrpList;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.service.data_dic.IGrpListService;
import org.springclouddev.integral.vo.data_dic.GrpListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("组群集合接口")
@RestController
@RequestMapping("/grpList")
public class SysGrpListController {
	
	@Autowired
	private IGrpListService grpListService;
	
	@ApiOperation("查询组群集合列表")
	@GetMapping("qryGrpLists")
	public Object qryGrpLists(@RequestParam(value="page",defaultValue ="1",required = false) Integer pageNum,
                              @RequestParam(value="limit",defaultValue ="10",required = false) Integer pageSize,
                              @RequestParam(value="code",required = false) String code, @RequestParam(value="name",required = false) String name,
                              @RequestParam(value="typeId",required = false) String typeId, @RequestParam(value="sortId",required = false) String sortId) {
		GrpList grpList=new GrpList();
		grpList.setCode(code);
		grpList.setName(name);
		grpList.setType(typeId);
		grpList.setSort(sortId);
		List<GrpList> list=grpListService.qryGrpLists(grpList, pageNum, pageSize);
		for(int i=0;i<list.size();i++) {
			list.set(i, setGrpListVo(list.get(i)));
		}
		return R.data(list);
	}
	
	@ApiOperation("查询组群集合详情")
	@GetMapping("qryGrpListInfo")
	public Object qryGrpListInfo(@RequestParam Long id) {
		if(id==null||id<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		GrpList grpList=setGrpListVo(grpListService.qryGrpListById(id));
		
		return R.data(grpList);
	}
	
	
	
	@ApiOperation("新建组群集合")
	@PostMapping("crtGrpList")
	public Object crtGrpList(@RequestBody GrpList grpList) {
		if(grpListService.crtGrpList(grpList)!=1) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	
	@ApiOperation("修改群组集合")
	@PostMapping("updGrpListById")
	public Object updGrpListById(@RequestBody GrpList grpList) {
		if(grpList==null|| grpList.getId()==null||grpList.getId()<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if(grpListService.updGrpList(grpList)<1) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	
	@ApiOperation("删除组群集合")
	@PostMapping("delGrpListById")
	public Object delGrpListById(@RequestParam Long id) {
		if(id==null||id<1) {
			return R.fail(ReturnCode.LOST_REQUIRED_PARAM);
		}
		if(!grpListService.delGrpListById(id)) {
			return R.fail("操作失败");
		}
		return R.success("操作成功");
	}
	
	private static GrpList setGrpListVo(GrpList grpList) {
		if(grpList==null) {
			return null;
		}
		GrpListVo grpListVo=new GrpListVo();
		BeanUtil.copyProperties(grpList, grpListVo);
		grpListVo.setSortName(LocalDictCache.getDicNameById(grpListVo.getSort()));
		grpListVo.setTypeName(LocalDictCache.getDicNameById(grpListVo.getType()));
		return grpListVo;
	}

}
