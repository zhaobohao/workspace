                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    //
  ruleId: undefined,
      // 表达式
  expId: undefined,
      // MCC码
  mccId: undefined,
      // 名单码
  nameId: undefined,
      // 创建时间
  crtTime: undefined,
      // 更新时间
  updTime: undefined,
      // 更新人
  updUser: undefined,
      //
  version: undefined,
      //
  params: undefined
          },
    rules: {
        ruleId: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          expId: [{
  required: true,
  message: '请输入表达式',
  trigger: 'blur'
  }],
          mccId: [{
  required: true,
  message: '请输入MCC码',
  trigger: 'blur'
  }],
          nameId: [{
  required: true,
  message: '请输入名单码',
  trigger: 'blur'
  }],
          crtTime: [{
  required: true,
  message: '请输入创建时间',
  trigger: 'blur'
  }],
          updTime: [{
  required: true,
  message: '请输入更新时间',
  trigger: 'blur'
  }],
          updUser: [{
  required: true,
  message: '请输入更新人',
  trigger: 'blur'
  }],
          version: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          params: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }]
      }
  }
}
