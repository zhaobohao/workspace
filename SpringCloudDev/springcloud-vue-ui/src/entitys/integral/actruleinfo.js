                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 表id编号
  actRuleInfoId: undefined,
      // 
  actCode: undefined,
      // 
  ruleId: undefined,
      // 规则计算类型 
  type: undefined,
      // 优先级
  grade: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
      // 规则名称
  ruleName: undefined,
          },
    rules: {
        actRuleInfoId: [{
  required: true,
  message: '请输入表id编号',
  trigger: 'blur'
  }],
          actCode: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          ruleId: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          type: [{
  required: true,
  message: '请输入规则计算类型 ',
  trigger: 'blur'
  }],
          grade: [{
  required: true,
  message: '请输入优先级',
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
          ruleName: [{
  required: true,
  message: '请输入规则名称',
  trigger: 'blur'
  }],
      }
  }
}
