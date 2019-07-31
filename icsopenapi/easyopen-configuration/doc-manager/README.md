# 文档管理

文档管理界面，采用vue实现

# 启动方式

- 前提：先安装好npm，[npm安装教程](https://blog.csdn.net/zhangwenwu2/article/details/52778521)。建议使用淘宝镜像。
- 打开config/dev.env.js，修改BASE_API变量
- 启动配置中心(easyopen-config)

然后打开命令行窗口，进入到doc-manager目录，输入下面命令

```bash
# 初始化
cnpm install

# 启动，随后出现一个url，直接复制到浏览器即可
cnpm run dev
```

![文档管理](https://images.gitee.com/uploads/images/2018/0918/201954_a68ceb06_332975.png "newdoc.png")

# Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).
