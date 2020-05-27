package org.dbchain.blockchain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MdBlockchainApplicationTests {

	@Test
	public void contextLoads() {
        //TODO 需要测试高并发下生成block的速度和冲突情况
	}

}
