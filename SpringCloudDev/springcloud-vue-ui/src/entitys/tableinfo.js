export default function tableinfo() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 名称
      name: undefined,
      // 字段类型
      typeKey: undefined,
      // 字段类型值
      typeValue: undefined,
      // 是否为空
      isEmpty: undefined,
      // 默认值
      defaultValue: undefined,
      // 注释说明
      comment: undefined,
      // 表ID
      parentId: undefined,
      // 数据类型
      category: undefined,
      // 数据库
      dbInstanceId: undefined,
      // 叶子节点
      isLeaf: undefined
    },
    rules: {
      name: [{
        required: true,
        message: '请输入名称',
        trigger: 'blur'
      }, {
        min: 5,
        max: 30,
        message: '长度在 5 到 30 个字符'
      }],
      typeKey: [{
        required: true,
        message: '请输入字段类型',
        trigger: 'blur'
      }],
      typeValue: [{
        required: true,
        message: '请输入字段类型值',
        trigger: 'blur'
      }],
      isEmpty: [{
        required: true,
        message: '请输入是否为空',
        trigger: 'blur'
      }],
      comment: [{
        required: true,
        message: '请输入注释说明',
        trigger: 'blur'
      }]

    }
  }
}
