                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 编号
  grpListId: undefined,
      // 集合码
  code: undefined,
      // 集合名称
  name: undefined,
      // 描述
  glDesc: undefined,
      // 集合类别
  sort: undefined,
      // 集合类型
  type: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        grpListId: [{
  required: true,
  message: '请输入编号',
  trigger: 'blur'
  }],
          code: [{
  required: true,
  message: '请输入集合码',
  trigger: 'blur'
  }],
          name: [{
  required: true,
  message: '请输入集合名称',
  trigger: 'blur'
  }],
          glDesc: [{
  required: true,
  message: '请输入描述',
  trigger: 'blur'
  }],
          sort: [{
  required: true,
  message: '请输入集合类别',
  trigger: 'blur'
  }],
          type: [{
  required: true,
  message: '请输入集合类型',
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
