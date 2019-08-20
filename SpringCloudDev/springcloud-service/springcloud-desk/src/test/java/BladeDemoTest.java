import org.junit.Test;
import org.junit.runner.RunWith;
import org.springclouddev.core.test.BladeBootTest;
import org.springclouddev.core.test.BladeSpringRunner;
import org.springclouddev.desk.DeskApplication;
import org.springclouddev.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Blade单元测试
 *
 * @author firewan
 */
@RunWith(BladeSpringRunner.class)
@SpringBootTest(classes = DeskApplication.class)
@BladeBootTest(appName = "blade-desk", profile = "test", enableLoader = true)
public class BladeDemoTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
