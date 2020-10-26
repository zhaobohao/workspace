package org.springclouddev.crm.admin.api.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhangzhiwei
 * BO业务逻辑代码
 */
@Data
@ToString
@ApiModel(value="AdminCompany对象", description="云平台公司配置表")
public class AdminCompanyBO {
    @ApiModelProperty(value = "企业名称",required = true,example = "良心企业")
    @NotEmpty
    private String companyName;

    @ApiModelProperty(value = "企业LOGO", example = "/logo")
    private String companyLogo;
}
