export default function datasource() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      //  名称
      name: undefined,
      //  驱动类
      driverClass: undefined,
      //  用户名
      username: undefined,
      //  密码
      password: undefined,
      //  连接地址
      url: undefined,
      // 备注
      remark: undefined
    },
    rules: {
      name: [{
        required: true,
        message: '请输入数据源名称 ',
        trigger: 'blur '
      }],
      driverClass: [{
        required: true,
        message: '请输入驱动类 ',
        trigger: 'blur '
      }],
      username: [{
        required: true,
        message: '请输入用户名 ',
        trigger: 'blur '
      }],
      password: [{
        required: true,
        message: '请输入密码 ',
        trigger: 'blur '
      }],
      url: [{
        required: true,
        message: '请输入连接地址 ',
        trigger: 'blur '
      }]
    }
  }
}
