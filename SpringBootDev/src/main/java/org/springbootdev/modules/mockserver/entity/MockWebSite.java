package org.springbootdev.modules.mockserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springbootdev.core.mp.base.BaseEntity;
import org.springbootdev.core.mp.base.TenantEntity;

/**
 * 实体类
 *
 * @author zhaobohao
 * @since 2020-04-07
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@TableName("mk_mock_web_site")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MockWebSite对象", description = "MockWebSite对象")
public class MockWebSite extends BaseEntity implements TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 站点名称
     */
    @ApiModelProperty(value = "站点名称")
    @TableField("name")
    private String name;
    /**
     * 远端的访问地址，带端口号。例如：http://xxxx.yyy.com:9090/uri
     */
    @ApiModelProperty(value = "远端的访问地址，带端口号。例如：http://xxxx.yyy.com:9090/uri")
    @TableField("address_url")
    private String addressUrl;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;


}
