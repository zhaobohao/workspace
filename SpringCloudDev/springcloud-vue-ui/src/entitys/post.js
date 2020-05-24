export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      // 主键
      id: undefined,
      // 岗位类型
      category: undefined,
      // 岗位编号
      postCode: undefined,
      // 岗位名称
      postName: undefined,
      // 岗位排序
      sort: undefined,
      // 岗位描述
      remark: undefined
    },
    rules: {
      id: [{
        required: true,
        message: '请输入主键',
        trigger: 'blur'
      }],
      category: [{
        required: true,
        message: '请输入岗位类型',
        trigger: 'blur'
      }],
      postCode: [{
        required: true,
        message: '请输入岗位编号',
        trigger: 'blur'
      }],
      postName: [{
        required: true,
        message: '请输入岗位名称',
        trigger: 'blur'
      }],
      sort: [{
        required: true,
        message: '请输入岗位排序',
        trigger: 'blur'
      }],
      remark: [{
        required: true,
        message: '请输入岗位描述',
        trigger: 'blur'
      }]
    }
  }
}
