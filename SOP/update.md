# 关于升级

本项目进行了改造，加入了pab的报文加解密方式。大部新增加的程序都是以Pab开头的，
只有三个文件要注意
* com.gitee.sop.bridge.ZuulConfig
* com.gitee.sop.test.Client
* com.gitee.sop.gatewaycommon.result.BaseExecutorAdapter
* com.gitee.sop.sdk.common.OpenConfig
* com.gitee.sop.sdk.client.OpenClient
* com.gitee.sop.gatewaycommon.zuul.filter.PostResultFilter
每次升级的时候，把上面5文件，单独的拿出来，然后全量copy过来。最后上面5重修改即可。
还要修改websit,沙盒环境的参数。