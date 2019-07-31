package com.gitee.easyopen.doc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.Result;
import com.gitee.easyopen.bean.Api;
import com.gitee.easyopen.bean.DefinitionHolder;
import com.gitee.easyopen.doc.annotation.ApiDoc;
import com.gitee.easyopen.doc.annotation.ApiDocBean;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.easyopen.doc.annotation.ApiDocMethod;
import com.gitee.easyopen.doc.annotation.ApiDocReturn;
import com.gitee.easyopen.doc.annotation.ApiDocRootData;
import com.gitee.easyopen.exception.ApiException;
import com.gitee.easyopen.register.SingleParameterContext;

/**
 * 文档生成器
 *
 * @author tanghc
 *
 */
public class ApiDocBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ApiDocBuilder.class);

    private static String PACKAGE_PREFIX = "java.";

    /** key:@ApiDoc.value() */
    private Map<String, ApiModule> apiModuleMap = new ConcurrentHashMap<>(64);

    /** key:handler.getClass().getName() */
    private static Map<String, ApiDoc> apiDocCache = new ConcurrentHashMap<>(64);

    /** key:name+version */
    private static Map<String, ApiDocItem> apiDocItemCache = new ConcurrentHashMap<>(64);

    private Class<?> lastClass;
    private int loopCount;

    private volatile List<ApiModule> allApiModules;
    
    public ApiDocItem getApiDocItem(String name, String version) {
        return apiDocItemCache.get(name + version);
    }

    public Collection<ApiModule> getApiModules() {
        if (allApiModules == null) {
            synchronized (ApiDocBuilder.class) {
                if (allApiModules == null) {
                    List<ApiModule> apiModules = new CopyOnWriteArrayList<>(apiModuleMap.values());
                    // 大模块排序
                    this.sort(apiModules);

                    for (ApiModule apiModule : apiModules) {
                        // 接口排序
                        this.sort(apiModule.getModuleItems());
                    }
                    allApiModules = apiModules;
                }
            }
        }
        return allApiModules;
    }

    protected <T extends Orderable> void sort(List<T> list) {
        List<T> listHasOrder = new ArrayList<>();
        List<T> listNoOrder = new ArrayList<>();

        int max = Integer.MAX_VALUE;

        for (T orderable : list) {
            if (orderable.getOrder() == max) {
                listNoOrder.add(orderable);
            } else {
                listHasOrder.add(orderable);
            }
        }

        Collections.sort(listHasOrder, new Comparator<Orderable>() {
            @Override
            public int compare(Orderable o1, Orderable o2) {
                return Integer.compare(o1.getOrder(), o2.getOrder());
            }
        });

        Collections.sort(listNoOrder, new Comparator<Orderable>() {
            @Override
            public int compare(Orderable o1, Orderable o2) {
                return o1.getOrderName().compareTo(o2.getOrderName());
            }
        });

        list.clear();

        list.addAll(listHasOrder);
        list.addAll(listNoOrder);
    }

    public synchronized void addDocItem(Api api, Object handler, Method method) {
        ApiDocMethod apiDocMethod = AnnotationUtils.findAnnotation(method, ApiDocMethod.class);
        if (apiDocMethod == null) {
            return;
        }
        // 修复显示cglib后缀的class名称，应该显示原类名称
        Class<?> handlerClass = ClassUtils.getUserClass(handler);
        String serviceName = handlerClass.getSimpleName();
        int order = Integer.MAX_VALUE;

        ApiDoc apiDoc = this.getApiDoc(handler);

        if (apiDoc != null) {
            serviceName = apiDoc.value();
            order = apiDoc.order();
        }
        api.setModuleName(serviceName);
        api.setDescription(apiDocMethod.description());
        api.setOrderIndex(order);

        // 设置接口信息
        DefinitionHolder.setApiInfo(api);

        ApiModule apiModule = this.getApiModule(serviceName, order);
        ApiDocItem apiDocItem = this.buildDocItem(api, apiDocMethod, method);
        apiModule.getModuleItems().add(apiDocItem);
        this.putCache(apiDocItem);
    }

    protected void putCache(ApiDocItem apiDocItem) {
        apiDocItemCache.put(apiDocItem.getNameVersion(), apiDocItem);
    }

    protected ApiDoc getApiDoc(Object handler) {
        String className = handler.getClass().getName();
        ApiDoc apiDoc = apiDocCache.get(className);
        if (apiDoc == null) {
            apiDoc = AnnotationUtils.findAnnotation(handler.getClass(), ApiDoc.class);
            if (apiDoc != null) {
                apiDocCache.put(className, apiDoc);
            }
        }
        return apiDoc;
    }

    protected ApiModule getApiModule(String serviceName, int order) {
        ApiModule apiModule = this.apiModuleMap.get(serviceName);

        if (apiModule == null) {
            apiModule = new ApiModule(serviceName, order);
            this.apiModuleMap.put(serviceName, apiModule);
        }

        return apiModule;
    }

    protected ApiDocItem buildDocItem(Api api, ApiDocMethod apiDocMethod, Method method) {
        ApiDocItem docItem = new ApiDocItem();

        docItem.setName(api.getName());
        docItem.setVersion(api.getVersion());
        docItem.setDescription(apiDocMethod.description());
        docItem.setRemark(apiDocMethod.remark());
        docItem.setOrder(apiDocMethod.order());

        List<ApiDocFieldDefinition> paramDefinitions = buildParamApiDocFieldDefinitions(apiDocMethod, method);
        List<ApiDocFieldDefinition> resultDefinitions = buildResultApiDocFieldDefinitions(apiDocMethod, method);
        ApiDocReturnDefinition apiDocReturnDefinition = buildApiDocReturnDefinition(method);
        List<ApiDocFieldDefinition> wrapper;

        Class<? extends Result> wrapperClass = this.getWrapperClass(apiDocMethod);

        // 默认返回结果
        if (wrapperClass == ApiResult.class) {
            wrapper = resultDefinitions;
        } else if(wrapperClass == NoResultWrapper.class) {
            docItem.setCustomWrapper(true);
            wrapper = resultDefinitions;
        } else {
            // 否则是其它返回类
            docItem.setCustomWrapper(true);
            wrapper = this.buildApiDocFieldDefinitionsByClass(wrapperClass);
            boolean hasRootAnno = false;
            for (ApiDocFieldDefinition wrapperDefinition : wrapper) {
                if (wrapperDefinition.isRootData()) {
                    hasRootAnno = true;
                    wrapperDefinition.setElements(resultDefinitions);
                    break;
                }
            }
            if (!hasRootAnno) {
                logger.error(wrapperClass.getName() + "没有设置@ApiDocRootData注解");
            }
        }

        docItem.setParamDefinitions(paramDefinitions);
        docItem.setResultDefinitions(wrapper);
        docItem.setApiDocReturnDefinition(apiDocReturnDefinition);

        Map<String, Object> paramRootData = new HashMap<>();
        for (ApiDocFieldDefinition paramDefinition : paramDefinitions) {
            this.fillDefinitionData(paramRootData, paramDefinition);
        }
        docItem.setParamData(paramRootData);

        Map<String, Object> resultRootData = new HashMap<>();
        for (ApiDocFieldDefinition resultDefinition : resultDefinitions) {
            this.fillDefinitionData(resultRootData, resultDefinition);
        }
        try {
            if (wrapperClass == NoResultWrapper.class) {
                docItem.setResultData(resultRootData);
            } else {
                Result result = wrapperClass.newInstance();
                result.setData(resultRootData);
                docItem.setResultData(result);
            }
        } catch (Exception e) {
            logger.error("生成数据模型失败", e);
        }

        return docItem;
    }

    protected void fillDefinitionData(Map<String, Object> data, ApiDocFieldDefinition definition) {
        String name = definition.getName();
        Object value;
        List<ApiDocFieldDefinition> elements = definition.getElements();
        boolean isArray = definition.getDataType().equalsIgnoreCase(DataType.ARRAY.name());
        if (elements.size() > 0) {
            if (isArray) {
                List<Object> elList = new ArrayList<>(elements.size());
                Map<String, Object> elData = new HashMap<>();
                for (ApiDocFieldDefinition element : elements) {
                    this.fillDefinitionData(elData, element);
                }
                elList.add(elData);
                value = elList;
            } else {
                Map<String, Object> val =  new HashMap<>();
                for (ApiDocFieldDefinition element : elements) {
                    this.fillDefinitionData(val, element);
                }
                value = val;
            }
        } else {
            value = isArray ? Collections.emptyList() : definition.getExample();
        }
        data.put(name, value);
    }

    protected ApiDocReturnDefinition buildApiDocReturnDefinition(Method method) {
        ApiDocReturn apiDocReturn = AnnotationUtils.findAnnotation(method, ApiDocReturn.class);
        if (apiDocReturn == null) {
            return null;
        }
        Class<?> returnType = method.getReturnType();
        ApiDocReturnDefinition apiDocReturnDefinition = new ApiDocReturnDefinition();
        apiDocReturnDefinition.setDescription(apiDocReturn.description());
        apiDocReturnDefinition.setDataType(returnType.getSimpleName().toLowerCase());
        apiDocReturnDefinition.setExample(apiDocReturn.example());
        return apiDocReturnDefinition;
    }

    protected Class<? extends Result> getWrapperClass(ApiDocMethod apiDocMethod) {
        Class<?> wrapperClass = null;
        ApiConfig apiConfig = ApiContext.getApiConfig();
        if (apiConfig != null) {
            wrapperClass = apiConfig.getWrapperClass();
        }
        if (wrapperClass == null) {
            wrapperClass = ApiResult.class;
        }
        Class<?> methodWrapperClass = apiDocMethod.wrapperClass();
        if (methodWrapperClass != Result.class) {
            wrapperClass = methodWrapperClass;
        }
        return (Class<? extends Result>)wrapperClass;
    }

    protected List<ApiDocFieldDefinition> buildParamApiDocFieldDefinitions(ApiDocMethod apiDocMethod, Method method) {
        List<ApiDocFieldDefinition> paramDefinitions = Collections.emptyList();

        ApiDocField[] params = apiDocMethod.params();
        Class<?> paramClass = apiDocMethod.paramClass();
        if (!ArrayUtils.isEmpty(params)) {
            paramDefinitions = buildApiDocFieldDefinitionsByApiDocFields(params);
        } else if (paramClass != Object.class) {
            paramDefinitions = this.buildApiDocFieldDefinitionsByClass(paramClass);
        } else {
            paramDefinitions = this.buildParamDefinitions(method);
        }

        return paramDefinitions;
    }

    protected List<ApiDocFieldDefinition> buildResultApiDocFieldDefinitions(ApiDocMethod apiDocMethod, Method method) {
        List<ApiDocFieldDefinition> resultDefinitions = Collections.emptyList();

        Class<?> elClass = apiDocMethod.elementClass();
        if (elClass != Object.class) {
            return buildApiDocFieldDefinitionsByType(elClass);
        }
        ApiDocField[] results = apiDocMethod.results();
        Class<?> resultClass = apiDocMethod.resultClass();
        if (!ArrayUtils.isEmpty(results)) {
            resultDefinitions = buildApiDocFieldDefinitionsByApiDocFields(results);
        } else if (resultClass != Object.class) {
            resultDefinitions = this.buildApiDocFieldDefinitionsByClass(resultClass);
        } else {
            resultDefinitions = this.buildResultDefinitions(method);
        }

        return resultDefinitions;
    }

    protected List<ApiDocFieldDefinition> buildApiDocFieldDefinitionsByClass(Class<?> paramClass) {
        ApiDocBean bean = AnnotationUtils.findAnnotation(paramClass, ApiDocBean.class);
        if (bean != null) {
            ApiDocField[] fields = bean.fields();
            if (!ArrayUtils.isEmpty(fields)) {
                return buildApiDocFieldDefinitionsByApiDocFields(fields);
            }
        }

        return buildApiDocFieldDefinitionsByType(paramClass);
    }

    protected List<ApiDocFieldDefinition> buildApiDocFieldDefinitionsByApiDocFields(ApiDocField[] params) {
        ArrayList<ApiDocFieldDefinition> paramDefinitions = new ArrayList<ApiDocFieldDefinition>();
        for (ApiDocField apiDocField : params) {
            if (apiDocField.beanClass() != Void.class) {
                paramDefinitions.add(buildApiDocFieldDefinitionByClass(apiDocField, apiDocField.beanClass(), null));
            } else {
                paramDefinitions.add(buildApiDocFieldDefinition(apiDocField, null));
            }
        }
        return paramDefinitions;
    }

    protected List<ApiDocFieldDefinition> buildParamDefinitions(Method method) {
        SingleParameterContext.SingleParameterContextValue value = SingleParameterContext.get(method);
        if (value != null) {
            return buildApiDocFieldDefinitionsByType(value.getWrapClass());
        }

        Class<?>[] types = method.getParameterTypes();
        if (types.length == 0) {
            return Collections.emptyList();
        }
        Class<?> paramClass = types[0];
        return buildApiDocFieldDefinitionsByType(paramClass);
    }

    protected List<ApiDocFieldDefinition> buildResultDefinitions(Method method) {
        Class<?> type = method.getReturnType();
        if (type == Void.class) {
            return Collections.emptyList();
        }
        return buildApiDocFieldDefinitionsByType(type);
    }

    /** 从api参数中构建 */
    protected List<ApiDocFieldDefinition> buildApiDocFieldDefinitionsByType(Class<?> clazz) {
        if (clazz.isInterface()) {
            return Collections.emptyList();
        }
        if (lastClass != null) {
            // 解决循环注解导致死循环问题
            if (lastClass == clazz) {
                loopCount++;
            } else {
                loopCount = 0;
            }
        }
        lastClass = clazz;

        final List<String> fieldNameList = new ArrayList<String>();
        final List<ApiDocFieldDefinition> docDefinition = new ArrayList<ApiDocFieldDefinition>();

        // 找到类上面的ApiDocBean注解
        ApiDocBean apiDocBean = AnnotationUtils.findAnnotation(clazz, ApiDocBean.class);
        if (apiDocBean != null) {
            ApiDocField[] fields = apiDocBean.fields();
            for (ApiDocField apiDocField : fields) {
                docDefinition.add(buildApiDocFieldDefinition(apiDocField, null));
                fieldNameList.add(apiDocField.name());
            }
        }
        // 遍历参数对象中的属性
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                ApiDocField docField = AnnotationUtils.findAnnotation(field, ApiDocField.class);
                // 找到有注解的属性
                if (docField != null) {
                    ApiDocFieldDefinition fieldDefinition = buildApiDocFieldDefinition(docField, field);
                    Class<?> beanClass = docField.beanClass();
                    Class<?> targetClass = field.getType();

                    if (beanClass != Void.class) {
                        fieldDefinition = buildApiDocFieldDefinitionByClass(docField, beanClass, field);
                    } else if (!isJavaType(targetClass)) {
                        // 如果是自定义类
                        fieldDefinition = buildApiDocFieldDefinitionByClass(docField, targetClass, field);
                    }

                    ApiDocRootData rootData = AnnotationUtils.findAnnotation(field, ApiDocRootData.class);
                    if (rootData != null) {
                        fieldDefinition.setRootData(true);
                    }

                    docDefinition.add(fieldDefinition);
                }
            }
        });

        return docDefinition;
    }

    protected static boolean isJavaType(Class<?> type) {
        if (type.isPrimitive()) {
            return true;
        }
        if (type.isArray()) {
            return true;
        }
        Package pkg = type.getPackage();
        if (pkg == null) {
            return false;
        }
        return pkg.getName().startsWith(PACKAGE_PREFIX);
    }

    protected ApiDocFieldDefinition buildApiDocFieldDefinitionByClass(ApiDocField docField, Class<?> clazz, Field field) {
        String name = docField.name();
        String type = DataType.OBJECT.getValue();
        String description = docField.description();
        description = description + "<br/>" + getEnumDescription(docField.enumClass());
        boolean required = docField.required();
        String example = docField.example();

        if (clazz == MultipartFile.class) {
            type = DataType.FILE.getValue();
        }
        if (field != null) {
            name = field.getName();
        }

        ApiDocFieldDefinition fieldDefinition = new ApiDocFieldDefinition();
        fieldDefinition.setName(name);
        fieldDefinition.setDataType(type);
        fieldDefinition.setRequired(String.valueOf(required));
        fieldDefinition.setExample(example);
        fieldDefinition.setDescription(description);

        List<ApiDocFieldDefinition> elementsDefinition = buildApiDocFieldDefinitionsByType(clazz);
        fieldDefinition.setElements(elementsDefinition);

        return fieldDefinition;
    }

    protected ApiDocFieldDefinition buildApiDocFieldDefinition(ApiDocField docField, Field field) {
        String type = getFieldType(field);
        String fieldName = null;
        if (field != null) {
            fieldName = field.getName();
        }

        return buildApiDocFieldDefinition(docField, type, fieldName);
    }

    protected ApiDocFieldDefinition buildApiDocFieldDefinition(ApiDocField docField, String type, String fieldName) {
        String name = docField.name();
        DataType dataType = docField.dataType();
        if (dataType != DataType.UNKNOW) {
            type = dataType.getValue();
        }
        String description = docField.description();
        description = description + "<br/>" + getEnumDescription(docField.enumClass());
        boolean required = docField.required();
        String example = docField.example();

        if (StringUtils.isNotBlank(fieldName)) {
            name = fieldName;
        }

        ApiDocFieldDefinition fieldDefinition = new ApiDocFieldDefinition();
        fieldDefinition.setName(name);
        fieldDefinition.setDataType(type);
        fieldDefinition.setRequired(String.valueOf(required));
        fieldDefinition.setExample(example);
        fieldDefinition.setDescription(description);

        List<ApiDocFieldDefinition> elementsDefinition = loopCount < 1 ? buildElementListDefinition(docField) : Collections.emptyList();
        fieldDefinition.setElements(elementsDefinition);
        fieldDefinition.setElementClass(docField.elementClass());

        if (elementsDefinition.size() > 0) {
            fieldDefinition.setDataType(DataType.ARRAY.getValue());
        }

        return fieldDefinition;
    }

    protected static String getEnumDescription(Class<?> enmuClass) {
        if (enmuClass == Void.class) {
            return "";
        }
        Class<?>[] interfaces = enmuClass.getInterfaces();
        boolean hasIEnum = false;
        for (Class<?> interfacesForClass : interfaces) {
            if (interfacesForClass == IEnum.class) {
                hasIEnum = true;
                break;
            }
        }
        if (!hasIEnum) {
            throw new ApiException(enmuClass.getName() + "必须实现" + IEnum.class.getName() + "接口");
        }
        IEnum[] enums = (IEnum[])enmuClass.getEnumConstants();
        if (enums == null || enums.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (IEnum anEnum : enums) {
            sb.append(", ").append(anEnum.getCode()).append("：").append(anEnum.getDescription());
            if (++i % 5 == 0) {
                sb.append("<br/>");
            }
        }
        return sb.toString().substring(1);
    }

    protected static String getFieldType(Field field) {
        if (field == null) {
            return DataType.STRING.getValue();
        }
        Class<?> type = field.getType();
        if (type == List.class || type == Collection.class || type == Set.class || type.isArray()) {
            return DataType.ARRAY.getValue();
        }
        if(type == Date.class) {
            return DataType.DATE.getValue();
        }
        if(type == Timestamp.class) {
            return DataType.DATETIME.getValue();
        }
        return field.getType().getSimpleName().toLowerCase();
    }

    protected List<ApiDocFieldDefinition> buildElementListDefinition(ApiDocField docField) {
        Class<?> elClass = docField.elementClass();
        if (elClass != Void.class) {
            return buildApiDocFieldDefinitionsByType(elClass);
        } else {
            return Collections.emptyList();
        }
    }

}
