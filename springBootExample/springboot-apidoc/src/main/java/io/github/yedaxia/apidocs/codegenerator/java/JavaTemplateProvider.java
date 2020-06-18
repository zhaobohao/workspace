package io.github.yedaxia.apidocs.codegenerator.java;

import io.github.yedaxia.apidocs.codegenerator.TemplateProvider;

import java.io.IOException;

/**
 *
 */
public class JavaTemplateProvider {

    public String provideTemplateForName(String templateName) throws IOException {
    	return TemplateProvider.provideTemplateForName(templateName);
    }
    
}
