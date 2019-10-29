package org.springbootdev.core.log.utils;

import org.springbootdev.core.launch.props.SystemProperties;
import org.springbootdev.core.launch.server.ServerInfo;
import org.springbootdev.core.log.model.LogAbstract;
import org.springbootdev.core.secure.utils.SecureUtil;
import org.springbootdev.core.tool.utils.DateUtil;
import org.springbootdev.core.tool.utils.StringPool;
import org.springbootdev.core.tool.utils.UrlUtil;
import org.springbootdev.core.tool.utils.WebUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * INet 相关工具
 *
 * @author zhaobohao
 */
public class LogAbstractUtil {

	/**
	 * 向log中添加补齐request的信息
	 *
	 * @param request     请求
	 * @param logAbstract 日志基础类
	 */
	public static void addRequestInfoToLog(HttpServletRequest request, LogAbstract logAbstract) {
		logAbstract.setRemoteIp(WebUtil.getIP(request));
		logAbstract.setUserAgent(request.getHeader(WebUtil.USER_AGENT_HEADER));
		logAbstract.setRequestUri(UrlUtil.getPath(request.getRequestURI()));
		logAbstract.setMethod(request.getMethod());
		logAbstract.setParams(WebUtil.getRequestParamString(request));
		logAbstract.setCreateBy(SecureUtil.getUserAccount(request));
	}

	/**
	 * 向log中添加补齐其他的信息（eg：springcloud、server等）
	 *
	 * @param logAbstract     日志基础类
	 * @param systemProperties 配置信息
	 * @param serverInfo      服务信息
	 */
	public static void addOtherInfoToLog(LogAbstract logAbstract, SystemProperties systemProperties, ServerInfo serverInfo) {
		logAbstract.setServiceId(systemProperties.getName());
		logAbstract.setServerHost(serverInfo.getHostName());
		logAbstract.setServerIp(serverInfo.getIpWithPort());
		logAbstract.setEnv(systemProperties.getEnv());
		logAbstract.setCreateTime(DateUtil.now());

		//这里判断一下params为null的情况，否则springcloud-log服务在解析该字段的时候，可能会报出NPE
		if (logAbstract.getParams() == null) {
			logAbstract.setParams(StringPool.EMPTY);
		}
	}
}
