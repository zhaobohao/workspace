package org.springclouddev.integral.enums;

import lombok.Getter;
import org.springclouddev.core.tool.api.IResultCode;

/**
 * 业务代码枚举
 *
 * @author zhaobohao
 */
@Getter
public enum ReturnCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),

    LOST_REQUIRED_PARAM(500001, "缺少必输项"),

    NO_THIS_ITEM_IN_DATABASE(500002, "数据库查询结果为空"),

    THIS_ITEM_CANT_COMMITTE(500003, "该状态不允许提交审核"),

    THE_WRONG_STATUS(500004, "状态不正确"),

    DELETE_ITEM_FAILE(500005, "删除数据失败"),

    PARAM_IS_NULL(500006, "传入参数未空"),

    JSON_ERROR(100007, "数据异常"),

    RULE_NAME_REPEAT(100006, "规则名称重复"),

    SYSTEM_EXCEPTION(999999, "系统繁忙"),

    UPD_EXCEPTION(100001, "更新失败"),

    ADD_EXCEPTION(100002, "新增失败"),

    DEL_EXCEPTION(100003, "新增失败"),

    AUDIT_EXCEPTION(100004, "送审失败"),

    HAS_AUDIT_EXCEPTION(100005, "在审核中"),

    CRT_DD_ERROR(600001, "新增数据字典项失败"),

    DEL_DD_ERROR(600002, "删除数据字典项失败"),

    QRY_DD_BY_ID_ERROR(600003, "主键查询数据字典项失败"),

    UPD_DD_ERROR(600004, "修改数据字典项失败"),

    MISS_MAST_FIELD(700001, "必输字段缺失"),

    DUOLICATE_KEY_ERROR(700002, "新增失败，新增主键冲突"),

    UNAUTHORIZED(700005, "未授权操作"),

    UNAUTHENTICATION(700006, "登录超时，请重新登录!");

    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;

    }

    /**
     * code编码
     */
    final int code;
    /**
     * 中文信息描述
     */
    final String message;
}
