package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.bean.ApiSearch;
import com.gitee.easyopen.monitor.MonitorApiInfo;
import com.gitee.easyopen.monitor.MonitorStore;
import com.gitee.easyopen.support.VelocityContextHandler;
import com.gitee.easyopen.util.VelocityUtil;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanghc
 */
public class MonitorTemplate extends AbstractVelocityContextTemplate {
    private static final String SESSION_KEY_MONITOR_PASSWORD = "session_key_monitor_password";

    public MonitorTemplate(ApiConfig apiConfig, VelocityContextHandler velocityContextHandler) {
        super(apiConfig, velocityContextHandler);
    }

    public void processMonitor(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        if (!this.apiConfig.isShowMonitor()) {
            response.getWriter().write("监控功能未开启");
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Object value = request.getSession().getAttribute(SESSION_KEY_MONITOR_PASSWORD);
        VelocityContext context = this.buildVelocityContext(request);
        // 如果没有登陆
        if(value == null) {
            // 校验登陆密码
            if(this.checkMonitorPassword(request)) {
                request.getSession().setAttribute(SESSION_KEY_MONITOR_PASSWORD, true);
                response.sendRedirect(String.valueOf(context.get("loginUrl")));
                return;
            } else { // 密码错误跳转
                request.getSession().setAttribute(SESSION_KEY_MONITOR_PASSWORD, null);

                ClassPathResource res = new ClassPathResource(this.apiConfig.getLoginClassPath());

                context.put("title", "API监控");
                context.put("remark", "监控页面密码：");

                VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
                return;
            }
        } else { // 已登录
            ClassPathResource res = new ClassPathResource(this.apiConfig.getMonitorClassPath());

            context.put("title", "监控");
            context.put("errorSize", this.apiConfig.getMonitorErrorQueueSize());

            this.processMonitorVelocityContext(context);

            this.toHtml(context, res, response);
        }
    }

    public Object monitorData(ApiSearch apiSearch, HttpServletRequest request) {
        // 无权限
        if(request.getSession().getAttribute(SESSION_KEY_MONITOR_PASSWORD) == null) {
            return -1;
        }
        MonitorStore store = this.apiConfig.getMonitorStore();
        long total = store.getTotal(apiSearch);
        List<MonitorApiInfo> list = store.getList(apiSearch);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);

        return map;
    }

    public Object monitorDel(String name, String version) {
        MonitorStore store = this.apiConfig.getMonitorStore();
        store.clean(name, version);
        return Collections.emptyMap();
    }

    protected boolean checkMonitorPassword(HttpServletRequest request) {
        return this.checkPassword(request, this.apiConfig.getMonitorPassword());
    }

    /**
     * 对监控模板做额外处理
     * @param context
     */
    protected void processMonitorVelocityContext(VelocityContext context) {
        this.velocityContextHandler.processMonitorVelocityContext(context);
    }
}
