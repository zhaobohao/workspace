export default function param() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 角色名称
      roleName: undefined,
      // 所属租户
      tenantId: undefined,
      // 角色别名
      roleAlias: undefined,
      // 上级角色
      parentId: undefined,
      // 角色排序
      sort: undefined
    },
    rules: {
      roleName: [{
        required: true,
        message: '请输入角色名称',
        trigger: 'blur'
      }],
      tenantId: [{
        required: true,
        message: '请输入所属租户',
        trigger: 'click'
      }],
      roleAlias: [{
        required: true,
        message: '请输入角色别名',
        trigger: 'blur'
      }],
      parentId: [{
        required: false,
        message: '请选择上级角色',
        trigger: 'click'
      }],
      sort: [{
        required: true,
        message: '请输入角色排序',
        trigger: 'blur'
      }]
    }
  }
}
