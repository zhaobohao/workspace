export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 区域编码
      areaCode: undefined,
      // 父级编号
      parentId: undefined,
      // 是否叶子节点
      isLeaf: undefined,
      // 区域名称
      areaName: undefined,
      // 排序
      sort: undefined,
      // 区域类型
      areaType: undefined,
      // 备注信息
      remarks: undefined,
      parentId_equal: 0
    },
    rules: {
      areaCode: [{
        required: true,
        message: '请输入区域编码',
        trigger: 'blur'
      }],
      areaName: [{
        required: true,
        message: '请输入区域名称',
        trigger: 'blur'
      }],
      areaType: [{
        required: true,
        message: '请输入区域类型',
        trigger: 'blur'
      }]
    }
  }
}
