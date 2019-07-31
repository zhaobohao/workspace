package com.gitee.easyopen.util;

import com.gitee.easyopen.bean.Consts;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Velocity工具类,根据模板内容生成文件
 *
 * @author tanghc
 */
public class VelocityUtil {
    private static String UTF8 = Consts.UTF8;

    static {
        // 禁止输出日志
        Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        Velocity.init();
    }

    private static String LOG_TAG = VelocityUtil.class.getName();

    public static String generateToString(VelocityContext context, InputStream inputStream) {
        StringWriter writer = new StringWriter();
        generate(context, inputStream, writer);
        return writer.toString();
    }

    public static String generateToString(VelocityContext context, String content) {
        StringWriter writer = new StringWriter();
        generate(context, new StringReader(content), writer);
        return writer.toString();
    }

    public static void generate(VelocityContext context, InputStream inputStream, Writer writer) {
        try {
            generate(context, new InputStreamReader(inputStream, UTF8), writer);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public static void generate(VelocityContext context, Reader reader, Writer writer) {
        try {
            Velocity.evaluate(context, writer, LOG_TAG, reader);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

}
