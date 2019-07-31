package com.gitee.easyopen.serializer;

import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.ResultSerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * 序列化成xml
 * @author tanghc
 */
public class XmlResultSerializer implements ResultSerializer {

    private static XStream xStream = new XStream(new StaxDriver(new NoNameCoder()));
    static {
        xStream.processAnnotations(ApiResult.class);
        xStream.aliasSystemAttribute(null, "class");
    }

    @Override
    public String serialize(Object obj) {
        return getXStream().toXML(obj);
    }

    public XStream getXStream() {
        return xStream;
    }

}
