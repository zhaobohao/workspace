/*
 * smart-doc
 *
 * Copyright (C) 2018-2020 smart-doc
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.power.doc.template;

import com.power.common.util.StringUtil;
import com.power.doc.builder.ProjectDocConfigBuilder;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.constants.DocTags;
import com.power.doc.constants.DubboAnnotationConstants;
import com.power.doc.helper.ParamsBuildHelper;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiParam;
import com.power.doc.model.CustomRespField;
import com.power.doc.model.JavaMethodDoc;
import com.power.doc.model.rpc.RpcApiDoc;
import com.power.doc.utils.DocClassUtil;
import com.power.doc.utils.DocUtil;
import com.power.doc.utils.JavaClassUtil;
import com.power.doc.utils.JavaClassValidateUtil;
import com.thoughtworks.qdox.model.*;

import java.util.*;

import static com.power.doc.constants.DocTags.IGNORE;

/**
 * @author yu 2020/1/29.
 */
public class RpcDocBuildTemplate implements IDocBuildTemplate<RpcApiDoc> {

    public List<RpcApiDoc> getApiData(ProjectDocConfigBuilder projectBuilder) {
        ApiConfig apiConfig = projectBuilder.getApiConfig();
        List<RpcApiDoc> apiDocList = new ArrayList<>();
        int order = 0;
        for (JavaClass cls : projectBuilder.getJavaProjectBuilder().getClasses()) {
            if (!checkDubboInterface(cls)) {
                continue;
            }
            if (StringUtil.isNotEmpty(apiConfig.getPackageFilters())) {
                if (DocUtil.isMatch(apiConfig.getPackageFilters(), cls.getCanonicalName())) {
                    order++;
                    List<JavaMethodDoc> apiMethodDocs = buildServiceMethod(cls, apiConfig, projectBuilder);
                    this.handleJavaApiDoc(cls, apiDocList, apiMethodDocs, order, projectBuilder);
                }
            } else {
                order++;
                List<JavaMethodDoc> apiMethodDocs = buildServiceMethod(cls, apiConfig, projectBuilder);
                this.handleJavaApiDoc(cls, apiDocList, apiMethodDocs, order, projectBuilder);
            }
        }
        if (apiConfig.isSortByTitle()) {
            Collections.sort(apiDocList);
        }
        return apiDocList;
    }

    public RpcApiDoc getSingleApiData(ProjectDocConfigBuilder projectBuilder, String apiClassName) {
        return null;
    }

    public boolean ignoreReturnObject(String typeName) {
        return false;
    }


    private List<JavaMethodDoc> buildServiceMethod(final JavaClass cls, ApiConfig apiConfig, ProjectDocConfigBuilder projectBuilder) {
        String clazName = cls.getCanonicalName();
        List<JavaMethod> methods = cls.getMethods();
        List<JavaMethodDoc> methodDocList = new ArrayList<>(methods.size());
        int methodOrder = 0;
        for (JavaMethod method : methods) {
            if (method.isPrivate()) {
                continue;
            }
            if (StringUtil.isEmpty(method.getComment()) && apiConfig.isStrict()) {
                throw new RuntimeException("Unable to find comment for method " + method.getName() + " in " + cls.getCanonicalName());
            }
            boolean deprecated = false;
            List<JavaAnnotation> annotations = method.getAnnotations();
            for (JavaAnnotation annotation : annotations) {
                String annotationName = annotation.getType().getName();
                if ("Deprecated".equals(annotationName)) {
                    deprecated = true;
                }
            }
            methodOrder++;
            JavaMethodDoc apiMethodDoc = new JavaMethodDoc();
            String methodDefine = methodDefinition(method);
            String scapeMethod = methodDefine.replaceAll("<", "&lt;");
            scapeMethod = scapeMethod.replaceAll(">", "&gt;");
            apiMethodDoc.setMethodDefinition(methodDefine);
            apiMethodDoc.setEscapeMethodDefinition(scapeMethod);
            apiMethodDoc.setOrder(methodOrder);
            apiMethodDoc.setDesc(method.getComment());
            apiMethodDoc.setName(method.getName());
            String methodUid = DocUtil.generateId(clazName + method.getName());
            apiMethodDoc.setMethodId(methodUid);
            String apiNoteValue = DocUtil.getNormalTagComments(method, DocTags.API_NOTE, cls.getName());
            if (StringUtil.isEmpty(apiNoteValue)) {
                apiNoteValue = method.getComment();
            }
            String authorValue = DocUtil.getNormalTagComments(method, DocTags.AUTHOR, cls.getName());
            if (apiConfig.isShowAuthor() && StringUtil.isNotEmpty(authorValue)) {
                apiMethodDoc.setAuthor(authorValue);
            }
            apiMethodDoc.setDetail(apiNoteValue);
            if (Objects.nonNull(method.getTagByName(IGNORE))) {
                continue;
            }
            apiMethodDoc.setDeprecated(deprecated);
            // build request params
            List<ApiParam> requestParams = requestParams(method, DocTags.PARAM, projectBuilder);
            apiMethodDoc.setRequestParams(requestParams);
            // build response params
            List<ApiParam> responseParams = buildReturnApiParams(method, projectBuilder);
            apiMethodDoc.setResponseParams(responseParams);
            methodDocList.add(apiMethodDoc);

        }
        return methodDocList;
    }

    private List<ApiParam> requestParams(final JavaMethod javaMethod, final String tagName, ProjectDocConfigBuilder builder) {
        boolean isStrict = builder.getApiConfig().isStrict();
        boolean isShowJavaType = builder.getApiConfig().getShowJavaType();
        Map<String, CustomRespField> responseFieldMap = new HashMap<>();
        String className = javaMethod.getDeclaringClass().getCanonicalName();
        Map<String, String> paramTagMap = DocUtil.getParamsComments(javaMethod, tagName, className);
        List<JavaParameter> parameterList = javaMethod.getParameters();
        if (parameterList.size() < 1) {
            return null;
        }
        List<ApiParam> paramList = new ArrayList<>();
        out:
        for (JavaParameter parameter : parameterList) {
            String paramName = parameter.getName();
            String typeName = parameter.getType().getGenericCanonicalName();
            String simpleName = parameter.getType().getValue().toLowerCase();
            String fullTypeName = parameter.getType().getFullyQualifiedName();
            String paramPre = paramName + ".";
            if (!paramTagMap.containsKey(paramName) && JavaClassValidateUtil.isPrimitive(fullTypeName) && isStrict) {
                throw new RuntimeException("ERROR: Unable to find javadoc @param for actual param \""
                        + paramName + "\" in method " + javaMethod.getName() + " from " + className);
            }
            String comment = this.paramCommentResolve(paramTagMap.get(paramName));
            JavaClass javaClass = builder.getJavaProjectBuilder().getClassByName(fullTypeName);
            List<JavaAnnotation> annotations = parameter.getAnnotations();
            List<String> groupClasses = JavaClassUtil.getParamGroupJavaClass(annotations);
            String strRequired = "true";
            Boolean required = Boolean.parseBoolean(strRequired);
            if (JavaClassValidateUtil.isCollection(fullTypeName) || JavaClassValidateUtil.isArray(fullTypeName)) {
                String[] gicNameArr = DocClassUtil.getSimpleGicName(typeName);
                String gicName = gicNameArr[0];
                if (JavaClassValidateUtil.isArray(gicName)) {
                    gicName = gicName.substring(0, gicName.indexOf("["));
                }
                if (JavaClassValidateUtil.isPrimitive(gicName)) {
                    String processedType = isShowJavaType ?
                            JavaClassUtil.getClassSimpleName(typeName) : DocClassUtil.processTypeNameForParams(simpleName);
                    ApiParam param = ApiParam.of().setField(paramName)
                            .setType(processedType);
                    paramList.add(param);
                } else {
                    paramList.addAll(ParamsBuildHelper.buildParams(gicNameArr[0], paramPre, 0, "true", responseFieldMap, Boolean.FALSE, new HashMap<>(), builder, groupClasses));
                }
            } else if (JavaClassValidateUtil.isPrimitive(simpleName)) {
                ApiParam param = ApiParam.of().setField(paramName)
                        .setType(JavaClassUtil.getClassSimpleName(typeName))
                        .setDesc(comment).setRequired(required).setVersion(DocGlobalConstants.DEFAULT_VERSION);
                paramList.add(param);
            } else if (JavaClassValidateUtil.isMap(fullTypeName)) {
                if (DocGlobalConstants.JAVA_MAP_FULLY.equals(typeName)) {
                    ApiParam apiParam = ApiParam.of().setField(paramName).setType(typeName)
                            .setDesc(comment).setRequired(required).setVersion(DocGlobalConstants.DEFAULT_VERSION);
                    paramList.add(apiParam);
                    continue out;
                }
                String[] gicNameArr = DocClassUtil.getSimpleGicName(typeName);
                paramList.addAll(ParamsBuildHelper.buildParams(gicNameArr[1], paramPre, 0, "true", responseFieldMap, Boolean.FALSE, new HashMap<>(), builder, groupClasses));
            } else if (javaClass.isEnum()) {
                ApiParam param = ApiParam.of().setField(paramName)
                        .setType("Enum").setDesc(comment).setRequired(required).setVersion(DocGlobalConstants.DEFAULT_VERSION);
                paramList.add(param);
            } else {
                paramList.addAll(ParamsBuildHelper.buildParams(typeName, paramPre, 0, "true", responseFieldMap, Boolean.FALSE, new HashMap<>(), builder, groupClasses));
            }
        }
        return paramList;
    }


    private boolean checkDubboInterface(JavaClass cls) {
        List<JavaAnnotation> classAnnotations = cls.getAnnotations();
        for (JavaAnnotation annotation : classAnnotations) {
            String name = annotation.getType().getCanonicalName();
            if (DubboAnnotationConstants.SERVICE.equals(name)) {
                return true;
            }
        }
        List<DocletTag> docletTags = cls.getTags();
        for (DocletTag docletTag : docletTags) {
            String value = docletTag.getName();
            if (DocTags.DUBBO.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void handleJavaApiDoc(JavaClass cls, List<RpcApiDoc> apiDocList, List<JavaMethodDoc> apiMethodDocs, int order, ProjectDocConfigBuilder builder) {
        String className = cls.getCanonicalName();
        List<JavaType> javaTypes = cls.getImplements();
        if (javaTypes.size() >= 1 && !cls.isInterface()) {
            className = javaTypes.get(0).getCanonicalName();
        }
        RpcApiDoc apiDoc = new RpcApiDoc();
        apiDoc.setOrder(order);
        apiDoc.setName(className);
        apiDoc.setShortName(cls.getName());
        apiDoc.setAlias(className);
        apiDoc.setUri(builder.getServerUrl() + "/" + className);
        apiDoc.setProtocol("dubbo");
        if (builder.getApiConfig().isMd5EncryptedHtmlName()) {
            String name = DocUtil.generateId(apiDoc.getName());
            apiDoc.setAlias(name);
        }
        apiDoc.setDesc(cls.getComment());
        apiDoc.setList(apiMethodDocs);
        apiDocList.add(apiDoc);
        List<DocletTag> docletTags = cls.getTags();
        List<String> authorList = new ArrayList<>();
        for (DocletTag docletTag : docletTags) {
            String name = docletTag.getName();
            if (DocTags.VERSION.equals(name)) {
                apiDoc.setVersion(docletTag.getValue());
            }
            if (DocTags.AUTHOR.equals(name)) {
                authorList.add(docletTag.getValue());
            }
        }
        apiDoc.setAuthor(String.join(", ", authorList));
    }

    private String methodDefinition(JavaMethod method) {
        StringBuilder methodBuilder = new StringBuilder();
        JavaType returnType = method.getReturnType();
        String simpleReturn = returnType.getCanonicalName();
        String returnClass = returnType.getGenericCanonicalName();
        returnClass = returnClass.replace(simpleReturn, JavaClassUtil.getClassSimpleName(simpleReturn));
        String[] arrays = DocClassUtil.getSimpleGicName(returnClass);
        for (String str : arrays) {
            returnClass = returnClass.replaceAll(str, JavaClassUtil.getClassSimpleName(str));
        }
        methodBuilder.append(returnClass).append(" ");
        List<String> params = new ArrayList<>();
        List<JavaParameter> parameters = method.getParameters();
        for (JavaParameter parameter : parameters) {
            params.add(JavaClassUtil.getClassSimpleName(parameter.getType().getValue()) + " " + JavaClassUtil.getClassSimpleName(parameter.getName()));
        }
        methodBuilder.append(method.getName()).append("(")
                .append(String.join(", ", params)).append(")");
        return methodBuilder.toString();
    }

}
