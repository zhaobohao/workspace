import {
  formatDate
} from '@/utils/date'
export default function dict() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 字典编号
      code: undefined,
      // 字典名称
      dictValue: undefined,
      // 字典键值
      dictKey: undefined,
      // 上级字典
      parentId: undefined,
      // 字典排序
      sort: undefined,
      // 字典备注
      remark: undefined
    },
    rules: {
      code: [{
        required: true,
        message: '请输入字典编号',
        trigger: 'blur'
      }],
      dictValue: [{
        required: true,
        message: '请输入字典名称',
        trigger: 'blur'
      }],
      parentId: [{
        required: false,
        message: '请选择上级字典',
        trigger: 'click'
      }],
      dictKey: [{
        required: true,
        message: '请输入字典键值',
        trigger: 'blur'
      }],
      sort: [{
        required: true,
        message: '请输入字典排序',
        trigger: 'blur'
      }]

    }
  }
}
