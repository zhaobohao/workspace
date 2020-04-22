package org.springbootdev.modules.drools.controller;

import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springbootdev.modules.drools.entity.Address;
import org.springbootdev.modules.drools.entity.AddressCheckResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/test")
@Controller
public class TestController {

	@Resource
	private KieHelper kieHelper;

	@ResponseBody
	@RequestMapping("/address")
	public void test(){
		KieSession kieSession=kieHelper.build().newKieSession();
		Address address = new Address();
		address.setPostcode("99425");

		AddressCheckResult result = new AddressCheckResult();
		kieSession.insert(address);
		kieSession.insert(result);
		int ruleFiredCount = kieSession.fireAllRules();
		System.out.println("触发了" + ruleFiredCount + "条规则");

		if(result.isPostCodeResult()){
			System.out.println("规则校验通过");
		}

	}
}
