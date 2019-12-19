export default function dbinstance() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 数据库名称
      name: undefined,
      // data账号
      dataUser: undefined,
      // etl账号
      etlUser: undefined,
      // opr账号
      oprUser: undefined,
      // rpt账号
      rptUser: undefined
    },
    rules: {
      name: [{
        required: true,
        message: '请输入数据库名称',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }],
      dataUser: [{
        required: true,
        message: '请输入data用户账号',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }],
      etlUser: [{
        required: true,
        message: '请输入etl用户账号',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }],
      oprUser: [{
        required: true,
        message: '请输入opr用户账号',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }],
      rptUser: [{
        required: true,
        message: '请输入rpt用户账号',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }]

    }
  }
}
