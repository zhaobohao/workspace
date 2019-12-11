import {
  formatDate
} from '@/utils/date'
export default function param() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 参数名称
      paramName: undefined,
      // 参数键名
      paramKey: undefined,
      // 参数键值
      paramValue: undefined,
      // 备注
      remark: undefined
    },
    rules: {
      paramName: [{
        required: true,
        message: '请输入参数名称',
        trigger: 'blur'
      }],
      paramKey: [{
        required: true,
        message: '请输入参数键名',
        trigger: 'blur'
      }],
      paramValue: [{
        required: true,
        message: '请输入参数键值',
        trigger: 'blur'
      }]

    }
  }
}
