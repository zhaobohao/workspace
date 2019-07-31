package com.gitee.durcframework.easyopen.server;

import org.junit.Test;

import com.gitee.easyopen.util.KeyStore;
import com.gitee.easyopen.util.RSAUtil;

import junit.framework.TestCase;

public class PubPriKeyTest extends TestCase {
    /**
     * 生成公私钥
     * @throws Exception
     */
    @Test
    public void testCreate() throws Exception {
        KeyStore store = RSAUtil.createKeys();
        System.out.println("公钥:");
        System.out.println(store.getPublicKey());
        System.out.println("私钥:");
        System.out.println(store.getPrivateKey());
    }
}
