package com.gitee.sop.sdk.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetStoryResponse extends BaseResponse {
    private Long id;
    private String name;
    private Date gmt_create;
}
