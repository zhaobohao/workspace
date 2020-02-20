package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class ForwardInfo {

    private TargetRoute targetRoute;

    public static ForwardInfo getErrorForwardInfo() {
        return ErrorForwardInfo.errorForwardInfo;
    }

    public ForwardInfo(TargetRoute targetRoute) {
        this.targetRoute = targetRoute;
    }

    public String getPath() {
        return targetRoute.getRouteDefinition().getPath();
    }

    public String getVersion() {
        return targetRoute.getRouteDefinition().getVersion();
    }

    static class ErrorForwardInfo extends ForwardInfo {

        private static final String VALIDATE_ERROR_PATH = "/sop/validateError";

        public static ErrorForwardInfo errorForwardInfo = new ErrorForwardInfo();

        public ErrorForwardInfo() {
            this(null);
        }

        public ErrorForwardInfo(TargetRoute targetRoute) {
            super(targetRoute);
        }

        @Override
        public String getPath() {
            return VALIDATE_ERROR_PATH;
        }

        @Override
        public String getVersion() {
            return "";
        }
    }

}
