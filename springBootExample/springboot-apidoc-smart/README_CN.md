## 自定义注释tag
由于java原生的注释tag不是很多，某些特性没法实现，因此Smart-doc提供了一些自定义的注释tag来满足文档生成需求。

##tag名称	描述
````
@ignore	ignore tag用于过滤请求参数对象上的某个字段，设置后smart-doc不输出改字段到请求参数列表中。
@required	如果你没有使用JSR303参数验证规范实现的方式来标准字段，就可以使用@required去标注请求参数对象的字段，标注smart-doc在输出参数列表时会设置为true。
````
## 自定义注释tag
tag名称	描述
````
@ignore	ignore tag用于过滤请求参数对象上的某个字段，设置后smart-doc不输出改字段到请求参数列表中。关于响应字段忽略的请看忽略响应字段，如果ignore加到方法上，则接口方法不会输出到文档。gnore支持添加到controller上进行忽略不想生成文档的接口类。
@required	如果你没有使用JSR303参数验证规范实现的方式来标注字段，就可以使用@required去标注请求参数对象的字段，标注smart-doc在输出参数列表时会设置为true。
@mock	mock tag用于在对象基本类型字段设置自定义文档展示值。设置值后smart-doc不再帮你生成随机值。方便可以通过smart-doc直接输出交付文档。
@dubbo	dubbo tag用于在dubbo的api接口类上添加让smart-doc可以扫描到dubbo rpc的接口生成文档。
@restApi	restApi tag用于支持smart-doc去扫描Spring Cloud Feign的定义接口生成文档。
````
## Maven dependency
```` java
<dependency>
   <groupId>spring.learn</groupId>
    <artifactId>springboot-apidoc-smart</artifactId>
   <version>1.0</version>
    <scope>test</scope>
</dependency>
````
## 使用方式
````
/**
     * 包括设置请求头，缺失注释的字段批量在文档生成期使用定义好的注释
     */
    @Test
    public void testBuilderControllersApi() {
        ApiConfig config = new ApiConfig();
        config.setServerUrl("http://localhost:8080");
        //config.setStrict(true);

        config.setAllInOne(true);
        config.setOutPath("d:\\md2");
        config.setMd5EncryptedHtmlName(true);
        //不指定SourcePaths默认加载代码为项目src/main/java下的
        config.setSourceCodePaths(
                SourceCodePath.path().setDesc("本项目代码").setPath("D:\\workspace\\springBootExample\\springboot-apidoc-smart\\src\\main\\java")
                //SourcePath.path().setPath("F:\\Personal\\project\\smart\\src\\main\\java")
                //SourcePath.path().setDesc("加载项目外代码").setPath("E:\\ApplicationPower\\ApplicationPower\\Common-util\\src\\main\\java")
        );
        config.setDataDictionaries(
                ApiDataDictionary.dict().setTitle("订单字典").setEnumClass(OrderEnum.class).setCodeField("code").setDescField("desc")
        );
        //设置请求头，如果没有请求头，可以不用设置
     /*   config.setRequestHeaders(
                ApiReqHeader.header().setName("access_token").setType("string").setDesc("Basic auth credentials"),
                ApiReqHeader.header().setName("user_uuid").setType("string").setDesc("User Uuid key")
        );*/
        //对于外部jar的类，api-doc目前无法自动获取注释，
        //如果有这种场景，则自己添加字段和注释，api-doc后期遇到同名字段则直接给相应字段加注释
        config.setCustomResponseFields(
//                CustomRespField.field().setName("success").setDesc("成功返回true,失败返回false"),
//                CustomRespField.field().setName("message").setDesc("接口响应信息"),
//                CustomRespField.field().setName("data").setDesc("接口响应数据"),
                CustomRespField.field().setName("code").setValue("00000").setDesc("响应代码")
        );
        //非必须只有当setAllInOne设置为true时文档变更记录才生效，
        config.setRevisionLogs(
                RevisionLog.getLog().setRevisionTime("2018/12/15").setAuthor("chen").setRemarks("测试").setStatus("创建").setVersion("V1.0"),
                RevisionLog.getLog().setRevisionTime("2018/12/16").setAuthor("chen2").setRemarks("测试2").setStatus("修改").setVersion("V2.0")
        );


        long start = System.currentTimeMillis();
//        ApiDocBuilder.builderControllersApi(config);
        HtmlApiDocBuilder.buildApiDoc(config);
        //PostmanJsonBuilder.buildPostmanCollection(config);
        long end = System.currentTimeMillis();
        DateTimeUtil.printRunTime(end, start);
    }
````