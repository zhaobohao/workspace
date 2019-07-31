package com.easyopen.sdk;

import com.easyopen.sdk.client.OpenClient;
import junit.framework.TestCase;

public class BaseTest extends TestCase {
    static String url = "http://localhost:8080/api";
    static String appKey = "test";
    static String secret = "123456";

    static OpenClient client = new OpenClient(url, appKey, secret);
}
