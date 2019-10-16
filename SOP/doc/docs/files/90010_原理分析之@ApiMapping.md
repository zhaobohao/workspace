# 原理分析之@ApiMapping注解

@ApiMapping注解的使用方式参考了Spring自带的@PostMapping注解。

查看org.springframework.web.bind.annotation.PostMapping的类注释，有这么一句话：


> Specifically, @PostMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).

翻译过来就是说，@PostMapping是一个组合模式的注解，可以看成是@RequestMapping(method = RequestMethod.POST)快捷方式。

如果我们自己定义个Mapping，仿照@PostMapping的方式，然后作用在方法上面会不会成功呢？实践证明是可以的。

@ApiMapping注解正是仿照了@PostMapping注解，然后再添加了几个自己的属性，比如版本号字段。

那么如何才能通过path + 版本号来确定一个接口呢？

springmvc提供了RequestCondition接口来实现这个功能，具体的操作可参考这篇文章：[让SpringMVC支持可版本管理的Restful接口](http://www.cnblogs.com/jcli/p/springmvc_restful_version.html)

SOP对应的是`com.gitee.sop.servercommon.mapping.ApiMappingRequestCondition`，这个类在com.gitee.sop.servercommon.mapping下。可以从`ApiMappingHandlerMapping`类开始解读。

