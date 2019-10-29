
package org.springbootdev.core.oss.rule;

import lombok.AllArgsConstructor;
import org.springbootdev.core.tool.utils.DateUtil;
import org.springbootdev.core.tool.utils.FileUtil;
import org.springbootdev.core.tool.utils.StringPool;
import org.springbootdev.core.tool.utils.StringUtil;

/**
 * 默认存储桶生成规则
 *
 * @author zhaobohao
 */
@AllArgsConstructor
public class SpringCloudOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
