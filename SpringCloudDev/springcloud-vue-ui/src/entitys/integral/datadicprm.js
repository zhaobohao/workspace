                                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 编号
  dataDicPrmId: undefined,
      // 参数编号
  code: undefined,
      // 父级菜单
  parentId: undefined,
      // 参数名称
  name: undefined,
      // 参数描述
  ddDesc: undefined,
      // 参数类别
  type: undefined,
      // 预留字段1
  reserveCokumn1: undefined,
      // 预留字段2
  reserveCokumn2: undefined,
      // 预留字段3
  reserveCokumn3: undefined,
      // 是否是叶子节点，0是，1不是
  isLeaf: undefined,
          parentId_equal: 0
        },
    rules: {
        dataDicPrmId: [{
  required: true,
  message: '请输入编号',
  trigger: 'blur'
  }],
          code: [{
  required: true,
  message: '请输入参数编号',
  trigger: 'blur'
  }],
          parentId: [{
  required: true,
  message: '请输入父级菜单',
  trigger: 'blur'
  }],
          name: [{
  required: true,
  message: '请输入参数名称',
  trigger: 'blur'
  }],
          ddDesc: [{
  required: true,
  message: '请输入参数描述',
  trigger: 'blur'
  }],
          type: [{
  required: true,
  message: '请输入参数类别',
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
          isLeaf: [{
  required: true,
  message: '请输入是否是叶子节点，0是，1不是',
  trigger: 'blur'
  }]
      }
  }
}
