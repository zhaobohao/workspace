package org.springclouddev.develop.templateengine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class VelocityTemplateZipEngine extends VelocityTemplateEngine {
    private static final String DOT_VM = ".vm";
    private VelocityEngine velocityEngine;
    ZipOutputStream zip ;
    public VelocityTemplateZipEngine(ZipOutputStream zip) {
        this.zip=zip;
    }

    public VelocityTemplateZipEngine init(ConfigBuilder configBuilder) {
        super.init(configBuilder);
        if (null == this.velocityEngine) {
            Properties p = new Properties();
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            p.setProperty("file.resource.loader.path", "");
            p.setProperty("UTF-8", ConstVal.UTF8);
            p.setProperty("input.encoding", ConstVal.UTF8);
            p.setProperty("file.resource.loader.unicode", "true");
            this.velocityEngine = new VelocityEngine(p);
        }

        return this;
    }
    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isBlank(templatePath)) {
            return;
        }
        Template template = velocityEngine.getTemplate(templatePath, ConstVal.UTF8);
        try (StringWriter sw = new StringWriter()) {
            template.merge(new VelocityContext(objectMap), sw);
            //添加到zip
            zip.putNextEntry(new ZipEntry(outputFile));
            IOUtils.write(sw.toString(), zip, "UTF-8");
        }
        zip.closeEntry();
        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }
}
