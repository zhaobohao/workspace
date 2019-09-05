
package org.springclouddev.core.oss.rule;

import lombok.AllArgsConstructor;
import org.springclouddev.core.tool.utils.DateUtil;
import org.springclouddev.core.tool.utils.FileUtil;
import org.springclouddev.core.tool.utils.StringPool;
import org.springclouddev.core.tool.utils.StringUtil;

/**
 * 默认存储桶生成规则
 *
 * @author zhaobohao
 */
@AllArgsConstructor
public class BladeOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StringPool.SLASH + DateUtil.today() + StringPool.SLASH + StringUtil.randomUUID() + StringPool.DOT + FileUtil.getFileExtension(originalFilename);
	}

}
