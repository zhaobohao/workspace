                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 黑名单编号
  blackId: undefined,
      // 规则名称
  clientId: undefined,
      // 客户姓名
  clientName: undefined,
      // 开户行名称
  bankName: undefined,
      // 性别
  sex: undefined,
      // 联系电话
  mobile: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        blackId: [{
  required: true,
  message: '请输入黑名单编号',
  trigger: 'blur'
  }],
          clientId: [{
  required: true,
  message: '请输入规则名称',
  trigger: 'blur'
  }],
          clientName: [{
  required: true,
  message: '请输入客户姓名',
  trigger: 'blur'
  }],
          bankName: [{
  required: true,
  message: '请输入开户行名称',
  trigger: 'blur'
  }],
          sex: [{
  required: true,
  message: '请输入性别',
  trigger: 'blur'
  }],
          mobile: [{
  required: true,
  message: '请输入联系电话',
  trigger: 'blur'
  }],
          reserveColumn1: [{
  required: true,
  message: '请输入预留字段一',
  trigger: 'blur'
  }],
          reserveColumn2: [{
  required: true,
  message: '请输入预留字段二',
  trigger: 'blur'
  }],
          reserveColumn3: [{
  required: true,
  message: '请输入预留字段三',
  trigger: 'blur'
  }],
      }
  }
}
