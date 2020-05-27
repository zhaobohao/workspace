package org.dbchain.blockchain.common;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author zhaobo create on 2020/2/27.
 */
public class Sha256 {
    public static String sha256(String input) {
        return DigestUtil.sha256Hex(input);
    }

}
