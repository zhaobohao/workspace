package com.gitee.easyopen;

import com.gitee.easyopen.bean.Consts;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;

/**
 * @author tanghc
 */
public class ApiDataDecoder implements DataDecoder {
    @Override
    public String decode(ApiParam param) throws Exception {
        // 业务参数data部分
        String data = param.fatchData();
        if(StringUtils.isEmpty(data)) {
            return Consts.EMPTY_JSON;
        }
        return URLDecoder.decode(data, Consts.UTF8);
    }
}
