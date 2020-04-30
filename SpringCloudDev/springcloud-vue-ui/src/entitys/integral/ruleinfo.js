                                                export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 规则编号
  ruleId: undefined,
      // 规则名称
  ruleName: undefined,
      // 规则状态
  ruleStatus: undefined,
      //
  ruleExp: undefined,
      // 备注
  remark: undefined,
      // 创建人
  crtUser: undefined,
      // 创建日期
  crtDate: undefined,
      // 送审人
  lstUpdUser: undefined,
      // 送审日期
  lstUpdTime: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined
          },
    rules: {
        ruleId: [{
  required: true,
  message: '请输入规则编号',
  trigger: 'blur'
  }],
          ruleName: [{
  required: true,
  message: '请输入规则名称',
  trigger: 'blur'
  }],
          ruleStatus: [{
  required: true,
  message: '请输入规则状态',
  trigger: 'blur'
  }],
          ruleExp: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          remark: [{
  required: true,
  message: '请输入备注',
  trigger: 'blur'
  }],
          crtUser: [{
  required: true,
  message: '请输入创建人',
  trigger: 'blur'
  }],
          crtDate: [{
  required: true,
  message: '请输入创建日期',
  trigger: 'blur'
  }],
          lstUpdUser: [{
  required: true,
  message: '请输入送审人',
  trigger: 'blur'
  }],
          lstUpdTime: [{
  required: true,
  message: '请输入送审日期',
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
