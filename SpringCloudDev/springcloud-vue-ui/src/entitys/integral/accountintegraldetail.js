                                                                export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 自增主键
  accountIntegralDetailId: undefined,
      // 账户号
  accountId: undefined,
      // 客户编号
  custId: undefined,
      // 证件类型
  identyType: undefined,
      // 证件号码
  identyCard: undefined,
      // 交易积分
  changeIntegral: undefined,
      // 交易日期
  changeDate: undefined,
      // 积分有效期余额
  integralValidBlance: undefined,
      // 积分有效期
  integralValidDate: undefined,
      // 账户余额
  accountBlance: undefined,
      // 业务
  business: undefined,
      // 描述
  remark: undefined,
      // 积分类型
  integralType: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        accountIntegralDetailId: [{
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
          changeIntegral: [{
  required: true,
  message: '请输入交易积分',
  trigger: 'blur'
  }],
          changeDate: [{
  required: true,
  message: '请输入交易日期',
  trigger: 'blur'
  }],
          integralValidBlance: [{
  required: true,
  message: '请输入积分有效期余额',
  trigger: 'blur'
  }],
          integralValidDate: [{
  required: true,
  message: '请输入积分有效期',
  trigger: 'blur'
  }],
          accountBlance: [{
  required: true,
  message: '请输入账户余额',
  trigger: 'blur'
  }],
          business: [{
  required: true,
  message: '请输入业务',
  trigger: 'blur'
  }],
          remark: [{
  required: true,
  message: '请输入描述',
  trigger: 'blur'
  }],
          integralType: [{
  required: true,
  message: '请输入积分类型',
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
