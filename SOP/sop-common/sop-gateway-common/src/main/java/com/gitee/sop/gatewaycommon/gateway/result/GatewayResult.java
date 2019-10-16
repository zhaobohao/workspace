package com.gitee.sop.gatewaycommon.gateway.result;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @author tanghc
 */
@Data
public class GatewayResult {
    private HttpStatus httpStatus;
    private MediaType contentType;
    private String body;

    public GatewayResult(HttpStatus httpStatus, MediaType contentType, String body) {
        this.httpStatus = httpStatus;
        this.contentType = contentType;
        this.body = body;
    }
}
