# SOP Admin 前端vue实现

  前提：先安装好npm，[npm安装教程](https://blog.csdn.net/zhangwenwu2/article/details/52778521)

1. 启动服务端程序，运行`SopAdminServerApplication.java`
2. `cd sop-admin-vue`
3. 执行`npm install --registry=https://registry.npm.taobao.org`
4. 执行`npm run dev`，访问`http://localhost:9528/`，用户名密码：`admin/123456`

```
# 建议不要用cnpm  安装有各种诡异的bug 可以通过如下操作解决npm速度慢的问题
npm install --registry=https://registry.npm.taobao.org

# 开发调试 localhost:9528
npm run dev

# 打包发布
npm run build:prod

# Build for production and view the bundle analyzer report
npm run build:prod --report
```

- 修改端口号：打开`vue.config.js`，找到`port`属性

## 打包放入到服务端步骤

如果想要把vue打包放到服务端，步骤如下：

- 执行`npm run build:prod`进行打包，结果在dest下
- 打包完成后，把dest中的所有文件，放到`sop-admin-server/src/main/resources/public`下