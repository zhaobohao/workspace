                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 表id
  id: undefined,
      // drools_group表id
  groupId: undefined,
      // 备注信息
  remarks: undefined,
      // 规则程序
  ruleBody: undefined
            },
    rules: {
        id: [{
  required: true,
  message: '请输入表id',
  trigger: 'blur'
  }],
          groupId: [{
  required: true,
  message: '请输入drools_group表id',
  trigger: 'blur'
  }],
          remarks: [{
  required: true,
  message: '请输入备注信息',
  trigger: 'blur'
  }],
          ruleBody: [{
  required: true,
  message: '请输入规则程序',
  trigger: 'blur'
  }]
        }
  }
}
