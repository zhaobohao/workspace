                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 自增主键
  integralStatusId: undefined,
      // 账户号
  accountId: undefined,
      // 客户编号
  custId: undefined,
      // 调整状态
  statusValue: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined
          },
    rules: {
        integralStatusId: [{
  required: true,
  message: '请输入自增主键',
  trigger: 'blur'
  }],
          accountId: [{
  required: true,
  message: '请输入账户号',
  trigger: 'blur'
  }],
          custId: [{
  required: true,
  message: '请输入客户编号',
  trigger: 'blur'
  }],
          statusValue: [{
  required: true,
  message: '请输入调整状态',
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
  }]
      }
  }
}
