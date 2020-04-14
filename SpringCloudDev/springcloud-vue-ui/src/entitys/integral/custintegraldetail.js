                                                                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 表id主键
  custIntegralDetailId: undefined,
      // 账户号
  accountId: undefined,
      // 客户编号
  custId: undefined,
      // 客户姓名
  custName: undefined,
      // 证件类型
  identyType: undefined,
      // 证件号码
  identyCard: undefined,
      // 客户电话
  phoneNum: undefined,
      // 客户地址
  custAddress: undefined,
      // 积分类型
  integralType: undefined,
      // 积分类型ID
  integralTypeId: undefined,
      // 积分有效期
  integralValidDate: undefined,
      // 积分有效期余额
  integralValidBlance: undefined,
      // 账户余额
  accountBlance: undefined,
      // 已使用总额
  usedToatl: undefined,
      // 账户状态
  accountStatus: undefined,
      // 账户状态ID
  accountStatusId: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        custIntegralDetailId: [{
  required: true,
  message: '请输入表id主键',
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
  message: '请输入客户姓名',
  trigger: 'blur'
  }],
          identyType: [{
  required: true,
  message: '请输入证件类型',
  trigger: 'blur'
  }],
          identyCard: [{
  required: true,
  message: '请输入证件号码',
  trigger: 'blur'
  }],
          phoneNum: [{
  required: true,
  message: '请输入客户电话',
  trigger: 'blur'
  }],
          custAddress: [{
  required: true,
  message: '请输入客户地址',
  trigger: 'blur'
  }],
          integralType: [{
  required: true,
  message: '请输入积分类型',
  trigger: 'blur'
  }],
          integralTypeId: [{
  required: true,
  message: '请输入积分类型ID',
  trigger: 'blur'
  }],
          integralValidDate: [{
  required: true,
  message: '请输入积分有效期',
  trigger: 'blur'
  }],
          integralValidBlance: [{
  required: true,
  message: '请输入积分有效期余额',
  trigger: 'blur'
  }],
          accountBlance: [{
  required: true,
  message: '请输入账户余额',
  trigger: 'blur'
  }],
          usedToatl: [{
  required: true,
  message: '请输入已使用总额',
  trigger: 'blur'
  }],
          accountStatus: [{
  required: true,
  message: '请输入账户状态',
  trigger: 'blur'
  }],
          accountStatusId: [{
  required: true,
  message: '请输入账户状态ID',
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
