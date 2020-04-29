package org.springclouddev.mockserver.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springclouddev.core.mp.base.BaseEntity;
import org.springclouddev.core.mp.base.TenantEntity;

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
@TableName("mk_mock_http")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MockHttp对象", description = "MockHttp对象")
public class MockHttp extends BaseEntity implements TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @ApiModelProperty(value = "表id")
    @TableId(value = "id", type = IdType.NONE)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * web_site表id
     */
    @ApiModelProperty(value = "web_site表id")
    @TableField("web_site_id")
    private Long webSiteId;
    /**
     * 配置的url路径，支持正则表达式
     */
    @ApiModelProperty(value = "配置的url路径，支持正则表达式")
    @TableField("request_path")
    private String requestPath;
    /**
     * http的method,例如get,put,delete,post,支持正则表达式
     */
    @ApiModelProperty(value = "http的method,例如get,put,delete,post,支持正则表达式")
    @TableField("request_method")
    private String requestMethod;
    /**
     * http传过来的参数，录入为json结构。key,value支持正则表达式
     */
    @ApiModelProperty(value = "http传过来的参数，录入为json结构。key,value支持正则表达式")
    @TableField("request_params")
    private String requestParams;
    /**
     * http传过来的头部参数，录入为json结构，key,value支持正则表达式
     */
    @ApiModelProperty(value = "http传过来的头部参数，录入为json结构，key,value支持正则表达式")
    @TableField("request_headers")
    private String requestHeaders;
    /**
     * htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式
     */
    @ApiModelProperty(value = "htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式")
    @TableField("request_cookies")
    private String requestCookies;
    /**
     * http传过来的request报文里的body段所包含的值，不支持正则表达式
     */
    @ApiModelProperty(value = "http传过来的request报文里的body段所包含的值，不支持正则表达式")
    @TableField("request_json_body")
    private String requestJsonBody;
    /**
     * request报文的编码，默认为utf-8
     */
    @ApiModelProperty(value = "request报文的编码，默认为utf-8")
    @TableField("request_charsets")
    private String requestCharsets;
    /**
     * http传过来的request报文里的body段所包含的值，不支持正则表达式
     */
    @ApiModelProperty(value = "http传过来的request报文里的body段所包含的值，不支持正则表达式")
    @TableField("request_form_body")
    private String requestFormBody;
    /**
     * http传过来的request报文里的body段所包含的值，默认是使用正则表达式
     */
    @ApiModelProperty(value = "http传过来的request报文当成字符串，默认是使用正则表达式")
    @TableField("request_string_body")
    private String requestStringBody;
    /**
     * response报文里的headers,录入为json.程序会正确拆分，附值 。
     */
    @ApiModelProperty(value = "response报文里的headers,录入为json.程序会正确拆分，附值 。")
    @TableField("response_headers")
    private String responseHeaders;
    /**
     * response报文里的body
     */
    @ApiModelProperty(value = "response报文里的body")
    @TableField("response_body")
    private String responseBody;
    /**
     * response报文里的body的编码
     */
    @ApiModelProperty(value = "response报文里的body的编码")
    @TableField("response_charsets")
    private String responseCharsets;
    /**
     * response报文里的 status code.例如：400，302，501
     */
    @ApiModelProperty(value = "response报文里的 status code.例如：400，302，501")
    @TableField("response_status_code")
    private Integer responseStatusCode;
    /**
     * 配合statuscode使用的，状态码解释文本。
     */
    @ApiModelProperty(value = "配合statuscode使用的，状态码解释文本。")
    @TableField("response_reason_phrase")
    private String responseReasonPhrase;
    /**
     * 延迟响应时候，默认时间单位为秒。
     */
    @ApiModelProperty(value = "延迟响应时候，默认时间单位为秒。")
    @TableField("response_delay")
    private Integer responseDelay;
    /**
     * 跳转域名
     */
    @ApiModelProperty(value = "跳转域名")
    @TableField("forward_host")
    private String forwardHost;
    /**
	 * 跳转域名接口
	 */
    @ApiModelProperty(value = "跳转域名接口")
    @TableField("forward_port")
    private String forwardPort;
    /**
     * 跳转路径
     */
    @ApiModelProperty(value = "跳转路径")
    @TableField("forward_path")
    private String forwardPath;
    /**
     * 跳转时头部参数，录入为json.程序自动拆分配置。
     */
    @ApiModelProperty(value = "跳转时头部参数，录入为json.程序自动拆分配置。")
    @TableField("forward_headers")
    private String forwardHeaders;
    /**
     * 跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress("target.host.com", 1234, SocketAddress.Scheme.HTTPS)
     */
    @ApiModelProperty(value = "跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress('target.host.coms', 1234, SocketAddress.Scheme.HTTPS)")
    @TableField("forward_socket_address")
    private String forwardSocketAddress;
    /**
     * 跳转时的延迟时间，默认时间单位为秒
     */
    @ApiModelProperty(value = "跳转时的延迟时间，默认时间单位为秒")
    @TableField("forward_delay")
    private String forwardDelay;
    /**
     * 跳转时，重写转给第三方的body.
     */
    @ApiModelProperty(value = "跳转时，重写转给第三方的body.")
    @TableField("forward_body")
    private String forwardBody;
    /**
     * 是否丢失链接，true,false
     */
    @ApiModelProperty(value = "是否丢失链接，true,false")
    @TableField("error_drop_connection")
    private String errorDropConnection;
    /**
     * 出错时，返回的报文
     */
    @ApiModelProperty(value = "出错时，返回的报文")
    @TableField("error_response_bytes")
    private String errorResponseBytes;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    @TableField(fill = FieldFill.INSERT)
    private String tenantId;


}
