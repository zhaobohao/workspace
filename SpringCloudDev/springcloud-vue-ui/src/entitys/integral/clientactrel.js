                export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 表id编号
  clientActRelId: undefined,
      // 客户ID
  clientId: undefined,
      // 积分活动ID
  actId: undefined,
      // 账户ID
  accountId: undefined
          },
    rules: {
        clientActRelId: [{
  required: true,
  message: '请输入表id编号',
  trigger: 'blur'
  }],
          clientId: [{
  required: true,
  message: '请输入客户ID',
  trigger: 'blur'
  }],
          actId: [{
  required: true,
  message: '请输入积分活动ID',
  trigger: 'blur'
  }],
          accountId: [{
  required: true,
  message: '请输入账户ID',
  trigger: 'blur'
  }]
      }
  }
}
