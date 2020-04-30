                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 批量编号
  batchId: undefined,
      // 批量所属类型
  batchType: undefined,
      // 批量描述
  batchDesc: undefined,
      // 批量数量
  batchNm: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined
          },
    rules: {
        batchId: [{
  required: true,
  message: '请输入批量编号',
  trigger: 'blur'
  }],
          batchType: [{
  required: true,
  message: '请输入批量所属类型',
  trigger: 'blur'
  }],
          batchDesc: [{
  required: true,
  message: '请输入批量描述',
  trigger: 'blur'
  }],
          batchNm: [{
  required: true,
  message: '请输入批量数量',
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
