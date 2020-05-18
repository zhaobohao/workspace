package org.springbootdev.modules.drools.jms;

import lombok.extern.slf4j.Slf4j;
import org.drools.core.impl.KnowledgeBaseImpl;
import org.springbootdev.core.tool.utils.RegexUtil;
import org.springbootdev.modules.drools.entity.DroolsRuls;
import org.springbootdev.modules.drools.service.IDroolsRulsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhaobo
 * @date  2020-04-09
 */
@Slf4j
@Component
public class DroolsClearListener {
   @Autowired
    KnowledgeBaseImpl kieBase;
    @Autowired
    private IDroolsRulsService droolsRulsService;
	public void receiveMessage(String id) {

                log.info("id : {}  Thread: {}",id,Thread.currentThread().toString());
                DroolsRuls dr=droolsRulsService.getById(Long.valueOf(id));
                kieBase.removeRule(RegexUtil.findResult("package (.*)",dr.getRuleBody(),1).replaceAll(";",""),RegexUtil.findResult("rules (.*)",dr.getRuleBody(),1).replaceAll("\"",""));
                log.info(" we remove drool, package:{}  . name :{}");

    }
}
