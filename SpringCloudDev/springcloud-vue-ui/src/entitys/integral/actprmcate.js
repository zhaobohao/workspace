                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 表id编号
  actPrmCateId: undefined,
      // 所属活动类别编号
  apCode: undefined,
      // 类别项类别编号
  code: undefined,
      // 类别项类别名称
  name: undefined,
      // 备注
  apcDesc: undefined,
      // 预留字段1
  reserveCokumn1: undefined,
      // 预留字段2
  reserveCokumn2: undefined,
      // 预留字段3
  reserveCokumn3: undefined,
      // 显示顺序
  apOrder: undefined
          },
    rules: {
        actPrmCateId: [{
  required: true,
  message: '请输入表id编号',
  trigger: 'blur'
  }],
          apCode: [{
  required: true,
  message: '请输入所属活动类别编号',
  trigger: 'blur'
  }],
          code: [{
  required: true,
  message: '请输入类别项类别编号',
  trigger: 'blur'
  }],
          name: [{
  required: true,
  message: '请输入类别项类别名称',
  trigger: 'blur'
  }],
          apcDesc: [{
  required: true,
  message: '请输入备注',
  trigger: 'blur'
  }],
          reserveCokumn1: [{
  required: true,
  message: '请输入预留字段1',
  trigger: 'blur'
  }],
          reserveCokumn2: [{
  required: true,
  message: '请输入预留字段2',
  trigger: 'blur'
  }],
          reserveCokumn3: [{
  required: true,
  message: '请输入预留字段3',
  trigger: 'blur'
  }],
          apOrder: [{
  required: true,
  message: '请输入显示顺序',
  trigger: 'blur'
  }]
      }
  }
}
