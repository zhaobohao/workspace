package com.gitee.easyopen.ext.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class BlockExceptionUtil {

    public static String handleException(BlockException ex) {
        System.err.println("错误发生: " + ex.getClass().getCanonicalName());
        return "error";
    }
}
