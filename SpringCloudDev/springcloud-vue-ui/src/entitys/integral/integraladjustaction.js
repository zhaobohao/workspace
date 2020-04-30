                                                                export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 自增主键
  integralAdjustActionId: undefined,
      // 账户号
  accountId: undefined,
      // 客户编号
  custId: undefined,
      // 调整数量
  adjustNum: undefined,
      // 调整状态id
  statusValue: undefined,
      // 调整状态name
  statusName: undefined,
      // 调整人
  crtUser: undefined,
      // 积分类型
  integralType: undefined,
      // 积分类型id
  integralTypeId: undefined,
      // 最后更新人
  lstUpdUser: undefined,
      // 最后更新时间
  lstUpdTime: undefined,
      // 调整日期
  adjustDate: undefined,
      // 调整原因
  adjustReason: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined
          },
    rules: {
        integralAdjustActionId: [{
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
          adjustNum: [{
  required: true,
  message: '请输入调整数量',
  trigger: 'blur'
  }],
          statusValue: [{
  required: true,
  message: '请输入调整状态id',
  trigger: 'blur'
  }],
          statusName: [{
  required: true,
  message: '请输入调整状态name',
  trigger: 'blur'
  }],
          crtUser: [{
  required: true,
  message: '请输入调整人',
  trigger: 'blur'
  }],
          integralType: [{
  required: true,
  message: '请输入积分类型',
  trigger: 'blur'
  }],
          integralTypeId: [{
  required: true,
  message: '请输入积分类型id',
  trigger: 'blur'
  }],
          lstUpdUser: [{
  required: true,
  message: '请输入最后更新人',
  trigger: 'blur'
  }],
          lstUpdTime: [{
  required: true,
  message: '请输入最后更新时间',
  trigger: 'blur'
  }],
          adjustDate: [{
  required: true,
  message: '请输入调整日期',
  trigger: 'blur'
  }],
          adjustReason: [{
  required: true,
  message: '请输入调整原因',
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
