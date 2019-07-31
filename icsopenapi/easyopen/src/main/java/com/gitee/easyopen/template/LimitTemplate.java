package com.gitee.easyopen.template;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiValidator;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.limit.LimitConfig;
import com.gitee.easyopen.limit.LimitConfigManager;
import com.gitee.easyopen.limit.LimitSearch;
import com.gitee.easyopen.support.VelocityContextHandler;
import com.gitee.easyopen.util.VelocityUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
public class LimitTemplate extends AbstractVelocityContextTemplate {
    /** 系统最大状态码 */
    private static final int SYSTEM_MAX_CODE = 100;
    private static final String SESSION_KEY_LIMIT_PASSWORD = "session_key_limit_password";

    public LimitTemplate(ApiConfig apiConfig, VelocityContextHandler velocityContextHandler) {
        super(apiConfig, velocityContextHandler);
    }

    public void processLimit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.apiConfig.getLimitManager() == null) {
            response.getWriter().write("限流功能未开启");
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        Object value = request.getSession().getAttribute(SESSION_KEY_LIMIT_PASSWORD);
        VelocityContext context = this.buildVelocityContext(request);
        // 如果没有登陆
        if(value == null) {
            // 校验登陆密码
            if(this.checkLimitPassword(request)) {
                request.getSession().setAttribute(SESSION_KEY_LIMIT_PASSWORD, true);
                response.sendRedirect(String.valueOf(context.get("loginUrl")));
            } else { // 密码错误跳转
                request.getSession().setAttribute(SESSION_KEY_LIMIT_PASSWORD, null);

                ClassPathResource res = new ClassPathResource(this.apiConfig.getLoginClassPath());
                context.put("title", "限流管理");
                context.put("remark", "限流管理页面密码：");

                VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
            }
        } else { // 已登录
            ClassPathResource res = new ClassPathResource(this.apiConfig.getLimitClassPath());

            context.put("title", "限流管理");

            this.processLimitVelocityContext(context);

            VelocityUtil.generate(context, res.getInputStream(), response.getWriter());
        }
    }

    public Object limitData(LimitSearch apiSearch, HttpServletRequest request) {
        // 无权限
        if(request.getSession().getAttribute(SESSION_KEY_LIMIT_PASSWORD) == null) {
            return -1;
        }
        LimitConfigManager limitConfigManager = this.apiConfig.getLimitConfigManager();
        long total = limitConfigManager.getTotal(apiSearch);
        List<?> list = limitConfigManager.listLimitConfig(apiSearch);

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);

        return map;
    }

    public Object limitModify(LimitConfig limitConfig, HttpServletRequest request) {
        // 无权限
        if(request.getSession().getAttribute(SESSION_KEY_LIMIT_PASSWORD) == null) {
            return -1;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(limitConfig.getVersion())) {
            limitConfig.setVersion("");
        }
        Set<ConstraintViolation<LimitConfig>> validateRet = ApiValidator.getValidator().validate(limitConfig);
        if (CollectionUtils.isNotEmpty(validateRet)) {
            ConstraintViolation<LimitConfig> oneError = validateRet.iterator().next();
            String errorMsg = oneError.getMessage();
            throw new ApiException(errorMsg, "500");
        }

        String code = limitConfig.getLimitCode();
        if(NumberUtils.isNumber(code)) {
            int codeDouble = NumberUtils.toInt(code);
            if(codeDouble <= SYSTEM_MAX_CODE) {
                throw new ApiException("code值必须大于100（1~100为系统保留code）", "500");
            }
        }

        LimitConfigManager limitConfigManager = this.apiConfig.getLimitConfigManager();
        limitConfigManager.save(limitConfig);
        return Collections.emptyMap();
    }

    public Object limitStatus(@RequestBody StatusParam statusParam, HttpServletRequest request) {
        // 无权限
        if(request.getSession().getAttribute(SESSION_KEY_LIMIT_PASSWORD) == null) {
            return -1;
        }
        List<String> list = statusParam.getNameVersionList();
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        if(statusParam.getStatus() == null) {
            throw new ApiException("status不能为null", "500");
        }

        LimitConfigManager limitConfigManager = this.apiConfig.getLimitConfigManager();
        byte status = statusParam.getStatus().byteValue();
        for (String nameVersion : list) {
            LimitConfig limitConfig = limitConfigManager.getApiLimitConfig(nameVersion);
            if(limitConfig.getStatus().byteValue() != status) {
                limitConfig.setStatus(status);
                limitConfigManager.save(limitConfig);
            }
        }

        return Collections.emptyMap();
    }

    /**
     * 对限流模板做额外处理
     * @param context
     */
    protected void processLimitVelocityContext(VelocityContext context) {
        this.velocityContextHandler.processLimitVelocityContext(context);
    }

    protected boolean checkLimitPassword(HttpServletRequest request) {
        return this.checkPassword(request, this.apiConfig.getLimitPassword());
    }

}


