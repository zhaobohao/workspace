# sop-common

- sop-bridge-gateway：网关桥接器，供sop-gateway依赖，依赖后使用spring cloud gateway网关
- sop-bridge-zuul：网关桥接器，供sop-gateway依赖，依赖后使用spring cloud zuul网关
- sop-gateway-common：提供给网关使用
- sop-service-common：提供给微服务端使用，需要打成jar

正式开发请将这些模块上传的maven私服

- 打包成jar：`mvn clean package`
- 上传到本机仓库：`mvn clean install`
- 上传到maven私服：`mvn clean deploy`