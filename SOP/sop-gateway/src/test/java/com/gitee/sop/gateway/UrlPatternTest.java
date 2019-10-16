package com.gitee.sop.gateway;

import junit.framework.TestCase;
import org.junit.Assert;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author tanghc
 */
public class UrlPatternTest extends TestCase {

    private PathMatcher pathMatcher = new AntPathMatcher();

    public void testA() {
        Assert.assertTrue(match("/food/get/{id}", "/food/get/2"));
        Assert.assertTrue(match("/food/get/{id}1.0", "/food/get/21.0"));
    }

    /**
     * @param pattern    food/get/{id}
     * @param lookupPath /food/get/2
     *
     * @return
     */
    public boolean match(String pattern, String lookupPath) {
        return this.pathMatcher.match(pattern, lookupPath);
    }

}
