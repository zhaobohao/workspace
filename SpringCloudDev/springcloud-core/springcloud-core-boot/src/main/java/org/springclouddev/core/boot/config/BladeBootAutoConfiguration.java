
package org.springclouddev.core.boot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.launch.props.BladeProperties;
import org.springclouddev.core.tool.constant.SystemConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 配置类
 * @author firewan
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
	BladeProperties.class
})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AllArgsConstructor
public class BladeBootAutoConfiguration {

	private BladeProperties bladeProperties;

	/**
	 * 全局变量定义
	 */
	@Bean
	public SystemConstant fileConst() {
		SystemConstant me = SystemConstant.me();

		//设定开发模式
		me.setDevMode(("dev".equals(bladeProperties.getEnv())));

		//设定文件上传远程地址
		me.setDomain(bladeProperties.get("upload-domain", "http://localhost:8888"));

		//设定文件上传是否为远程模式
		me.setRemoteMode(bladeProperties.getBoolean("remote-mode", true));

		//远程上传地址
		me.setRemotePath(bladeProperties.get("remote-path", System.getProperty("user.dir") + "/work/blade"));

		//设定文件上传头文件夹
		me.setUploadPath(bladeProperties.get("upload-path", "/upload"));

		//设定文件下载头文件夹
		me.setDownloadPath(bladeProperties.get("download-path", "/download"));

		//设定上传图片是否压缩
		me.setCompress(bladeProperties.getBoolean("compress", false));

		//设定上传图片压缩比例
		me.setCompressScale(bladeProperties.getDouble("compress-scale", 2.00));

		//设定上传图片缩放选择:true放大;false缩小
		me.setCompressFlag(bladeProperties.getBoolean("compress-flag", false));

		return me;
	}

}
