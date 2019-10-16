package com.gitee.sop.servercommon.swagger;

import com.gitee.sop.servercommon.annotation.ApiAbility;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.mapping.RouteUtil;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import org.springframework.core.annotation.Order;
import springfox.documentation.service.Operation;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

import java.util.List;

/**
 * @author tanghc
 */
public class DocumentationPluginsManagerExt extends DocumentationPluginsManager {

    private static final String SOP_NAME = "sop_name";
    private static final String SOP_VERSION = "sop_version";
    private static final String MODULE_ORDER = "module_order";

    @Override
    public Operation operation(OperationContext operationContext) {
        Operation operation = super.operation(operationContext);
        this.setVendorExtension(operation, operationContext);
        return operation;
    }

    private void setVendorExtension(Operation operation, OperationContext operationContext) {
        List<VendorExtension> vendorExtensions = operation.getVendorExtensions();
        Optional<ApiMapping> mappingOptional = operationContext.findAnnotation(ApiMapping.class);
        if (mappingOptional.isPresent()) {
            ApiMapping apiMapping = mappingOptional.get();
            String name = apiMapping.value()[0];
            String version = buildVersion(apiMapping.version());
            vendorExtensions.add(new StringVendorExtension(SOP_NAME, name));
            vendorExtensions.add(new StringVendorExtension(SOP_VERSION, version));
        } else {
            Optional<ApiAbility> abilityOptional = operationContext.findAnnotation(ApiAbility.class);
            if (abilityOptional.isPresent()) {
                ApiAbility apiAbility = abilityOptional.get();
                String mappingPattern = operationContext.requestMappingPattern();
                String name = RouteUtil.buildApiName(mappingPattern);
                String version = buildVersion(apiAbility.version());
                vendorExtensions.add(new StringVendorExtension(SOP_NAME, name));
                vendorExtensions.add(new StringVendorExtension(SOP_VERSION, version));
            }
        }
        Optional<Api> apiOptional = operationContext.findControllerAnnotation(Api.class);
        int order = 0;
        if (apiOptional.isPresent()) {
            order = apiOptional.get().position();
        } else {
            Optional<Order> orderOptional = operationContext.findControllerAnnotation(Order.class);
            if (orderOptional.isPresent()) {
                order = orderOptional.get().value();
            }
        }
        vendorExtensions.add(new StringVendorExtension(MODULE_ORDER, String.valueOf(order)));
    }

    private String buildVersion(String version) {
        if ("".equals(version)) {
            return ServiceConfig.getInstance().getDefaultVersion();
        } else {
            return version;
        }
    }
}
