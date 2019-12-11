import {
  formatDate
} from '@/utils/date'
export default function dept() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 部门名称
      deptName: undefined,
      // 所属租户
      tenantId: undefined,
      // 部门全称
      fullName: undefined,
      // 上级部门
      parentId: undefined,
      // 排序
      sort: undefined,
      // 备注
      remark: undefined
    },
    rules: {
      remark: [{
        required: false,
        message: '请输入备注',
        trigger: 'blur'
      }],
      sort: [{
        required: true,
        message: '请输入排序',
        trigger: 'blur'
      }],
      deptName: [{
        required: true,
        message: '请输入部门名称',
        trigger: 'blur'
      }],
      tenantId: [{
        required: true,
        message: '请输入所属租户',
        trigger: 'click'
      }],
      fullName: [{
        required: true,
        message: '请输入部门全称',
        trigger: 'blur'
      }],
      parentId: [{
        required: false,
        message: '请选择上级部门',
        trigger: 'click'
      }]
    }
  }
}
