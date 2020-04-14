                                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 自增主键
  integralStatusActionId: undefined,
      // 账户号
  accountId: undefined,
      // 客户编号
  custId: undefined,
      // 客户名称
  custName: undefined,
      // 积分类型
  integralType: undefined,
      // 当前余额
  blance: undefined,
      // 操作
  operationType: undefined,
      // 退回原因
  rejectReason: undefined,
      // 状态id
  accountStatusId: undefined,
      // 状态
  accountStatus: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        integralStatusActionId: [{
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
          custName: [{
  required: true,
  message: '请输入客户名称',
  trigger: 'blur'
  }],
          integralType: [{
  required: true,
  message: '请输入积分类型',
  trigger: 'blur'
  }],
          blance: [{
  required: true,
  message: '请输入当前余额',
  trigger: 'blur'
  }],
          operationType: [{
  required: true,
  message: '请输入操作',
  trigger: 'blur'
  }],
          rejectReason: [{
  required: true,
  message: '请输入退回原因',
  trigger: 'blur'
  }],
          accountStatusId: [{
  required: true,
  message: '请输入状态id',
  trigger: 'blur'
  }],
          accountStatus: [{
  required: true,
  message: '请输入状态',
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
