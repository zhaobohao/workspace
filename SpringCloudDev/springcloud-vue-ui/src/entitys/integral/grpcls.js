                                    export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 编号
  grpClsId: undefined,
      // 
  listId: undefined,
      // 组群码
  code: undefined,
      // 组群名称
  name: undefined,
      // 描述
  gcDesc: undefined,
      // 组群类别
  type: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        grpClsId: [{
  required: true,
  message: '请输入编号',
  trigger: 'blur'
  }],
          listId: [{
  required: true,
  message: '请输入',
  trigger: 'blur'
  }],
          code: [{
  required: true,
  message: '请输入组群码',
  trigger: 'blur'
  }],
          name: [{
  required: true,
  message: '请输入组群名称',
  trigger: 'blur'
  }],
          gcDesc: [{
  required: true,
  message: '请输入描述',
  trigger: 'blur'
  }],
          type: [{
  required: true,
  message: '请输入组群类别',
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
