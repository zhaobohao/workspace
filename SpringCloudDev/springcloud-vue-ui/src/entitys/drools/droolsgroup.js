export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      // 表id
      id: undefined,
      // 分组名称
      name: undefined,
      // 分组说明
      remarks: undefined
    },
    rules: {
      name: [{
        required: true,
        message: '请输入分组名称',
        trigger: 'blur'
      }],
      remarks: [{
        required: true,
        message: '请输入分组说明',
        trigger: 'blur'
      }]
    }
  }
}
