export default function tenant() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      //  数据源
      datasourceId: undefined,
      //  模块名
      codeName: undefined,
      //  服务名
      serviceName: undefined,
      //  表名
      tableName: undefined,
      //  表前缀
      tablePrefix: undefined,
      // 主键名
      pkName: undefined,
      //  包名
      packageName: undefined,
      //  基础业务
      baseMode: undefined,
      //  包装器
      wrapMode: undefined,
      //  后端生成路径
      apiPath: undefined,
      //  前端生成路径
      webPath: undefined
    },
    rules: {
      datasourceId: [{
        required: true,
        message: '请选择数据源 ',
        trigger: 'blur '
      }],
      codeName: [{
        required: true,
        message: '请输入模块名 ',
        trigger: 'blur '
      }],
      serviceName: [{
        required: true,
        message: '请输入服务名 ',
        trigger: 'blur '
      }],
      tableName: [{
        required: true,
        message: '请输入表名 ',
        trigger: 'blur '
      }],
      tablePrefix: [{
        required: true,
        message: '请输入表前缀 ',
        trigger: 'blur '
      }],
      pkName: [{
        required: true,
        message: '请输入主键名 ',
        trigger: 'blur '
      }],
      packageName: [{
        required: true,
        message: '请输入包名 ',
        trigger: 'blur '
      }],
      baseMode: [{
        required: true,
        message: '请选择基础业务 ',
        trigger: 'blur '
      }],
      wrapMode: [{
        required: true,
        message: '请选择包装器 ',
        trigger: 'blur '
      }],
      apiPath: [{
        required: true,
        message: '请输入后端生成路径 ',
        trigger: 'blur '
      }],
      webPath: [{
        required: true,
        message: '请输入前端生成路径 ',
        trigger: 'blur '
      }]
    }
  }
}
