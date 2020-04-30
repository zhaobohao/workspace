package org.springclouddev.integral.controller.latestActCode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.entity.LatestActCode;
import org.springclouddev.integral.service.latestActCode.ILatestActCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/actCode")
@Api("最新活动编号查询接口")
public class LatestActCodeController {
	
	@Autowired
    private ILatestActCodeService latestActCodeService;
	
	@ApiOperation("查询最新的actCode")
	@GetMapping("/queryLatestActCode")
	public Object queryActCode() {
		List<LatestActCode> list = latestActCodeService.queryActCode();
		if (list == null && list.size() == 0) {
			return R.fail("操作失败");
		}
		LatestActCode latestActCode = list.get(0);
		String suffixActCode1 = latestActCode.getSuffixActCode();
		int length = suffixActCode1.length();
		String suffixActCode2 = String.valueOf(Integer.parseInt(suffixActCode1) + 1);
		StringBuffer suffixActCode3 = new StringBuffer();
		if (suffixActCode2.length() < length) {
			int a = length - suffixActCode2.length();
			for (int i = 0; i < a; i++) {
				suffixActCode3.append("0");
			}
		}
		String suffixActCode = suffixActCode3.append(suffixActCode2).toString();

		Map<String, String> map = new HashMap<String, String>();
		map.put("latestActCode", latestActCode.getPrefixActCode() + suffixActCode);
		return R.data(map);

	}
}
