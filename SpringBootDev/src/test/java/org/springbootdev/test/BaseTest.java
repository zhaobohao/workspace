package org.springbootdev.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springbootdev.core.test.BootTest;
import org.springbootdev.core.test.SysSpringRunner;
import org.springbootdev.modules.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Blade单元测试
 *
 * @author merryChen
 */
@RunWith(SysSpringRunner.class)
@BootTest(appName = "runner", profile = "test")
public class BaseTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
