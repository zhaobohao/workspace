package com.gitee.sop.gatewaycommon;

import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;

/**
 * @author tanghc
 */
public class ExcludeTest extends TestCase {
    public void testRegex() {
        String serviceId = "com.aaa.bbb.story-service";
        String sopServiceExcludeRegex = "com\\..*;story\\-.*";
        if (StringUtils.isNotBlank(sopServiceExcludeRegex)) {
            String[] regexArr = sopServiceExcludeRegex.split(";");
            for (String regex : regexArr) {
                if (serviceId.matches(regex)) {
                    System.out.println("111");
                }
            }
        }
    }
}
