                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 组群编号
  groupId: undefined,
      // 组群号码
  groupNm: undefined,
      // 组群名称
  groupName: undefined,
      // 组群描述
  groupDesc: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined,
          },
    rules: {
        groupId: [{
  required: true,
  message: '请输入组群编号',
  trigger: 'blur'
  }],
          groupNm: [{
  required: true,
  message: '请输入组群号码',
  trigger: 'blur'
  }],
          groupName: [{
  required: true,
  message: '请输入组群名称',
  trigger: 'blur'
  }],
          groupDesc: [{
  required: true,
  message: '请输入组群描述',
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
