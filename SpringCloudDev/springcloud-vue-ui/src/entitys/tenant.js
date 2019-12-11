import {
  formatDate
} from '@/utils/date'
export default function tenant() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 租户ID
      tenantId: undefined,
      // 租户名称
      tenantName: undefined,
      // 联系人
      linkman: undefined,
      // 联系电话
      contactNumber: undefined,
      // 联系地址
      address: undefined
    },
    rules: {
      tenantId: [{
        required: true,
        message: '请输入租户ID',
        trigger: 'blur'
      }],
      tenantName: [{
        required: true,
        message: '请输入参数名称',
        trigger: 'blur'
      }],
      linkman: [{
        required: true,
        message: '请输入联系人',
        trigger: 'blur'
      }]
    }
  }
}
