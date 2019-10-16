package com.gitee.sop.bookweb.controller;

import lombok.Data;

/**
 * 默认的结果封装类.
 * <pre>
 *
 * xml返回结果:
 * <response>
 *     <code>50</code>
 *     <msg>Remote service error</msg>
 *     <sub_code>isv.invalid-parameter</sub_code>
 *     <sub_msg>非法参数</sub_msg>
 * </response>
 * 成功情况：
 * <response>
 *     <code>0</code>
 *     <msg>成功消息</msg>
 *     <data>
 *         ...返回内容
 *     </data>
 * </response>
 *
 * json返回格式：
 * {
 *  "code":"50",
 * 	"msg":"Remote service error",
 * 	"sub_code":"isv.invalid-parameter",
 * 	"sub_msg":"非法参数"
 * }
 * 成功情况：
 * {
 *  "code":"0",
 * 	"msg":"成功消息内容。。。",
 * 	"data":{
 * 	    ...返回内容
 *    }
 * }
 * </pre>
 * <p>
 * 字段说明：
 * code:网关异常码 <br>
 * msg:网关异常信息 <br>
 * sub_code:业务异常码 <br>
 * sub_msg:业务异常信息 <br>
 *
 * @author tanghc
 */
@Data
public class ApiResult  {

    /**
     * 网关异常信息
     */
    private String msg;

    /**
     * 业务异常码
     */
    private String sub_msg;

    /**
     * 业务异常信息
     */
    private String sub_code;

    /**
     * 返回结果
     */
    private Object data;
}
