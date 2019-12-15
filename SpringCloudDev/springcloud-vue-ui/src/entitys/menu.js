export default function dict() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 菜单名称
      name: undefined,
      // 路由地址
      path: undefined,
      // 菜单图标
      source: undefined,
      // 上级字典
      parentId: undefined,
      // 菜单编号
      code: undefined,
      // 菜单类型
      category: 1,
      // 菜单别名
      alias: undefined,
      // 按钮功能
      // action: undefined,
      // 菜单排序
      sort: undefined,
      // 新窗口
      isOpen: undefined,
      // 菜单备注
      remark: undefined
    },
    rules: {
      name: [{
        required: true,
        message: '请输入菜单名称',
        trigger: 'blur'
      }],
      path: [{
        required: true,
        message: '请输入路由地址',
        trigger: 'blur'
      }],
      parentId: [{
        required: false,
        message: '请选择上级菜单',
        trigger: 'click'
      }],
      source: [{
        required: true,
        message: '请输入菜单图标',
        trigger: 'click'
      }],
      code: [{
        required: true,
        message: '请输入菜单编号',
        trigger: 'blur'
      }],
      category: [{
        required: true,
        message: '请选择菜单类型',
        trigger: 'blur'
      }],
      alias: [{
        required: true,
        message: '请输入菜单别名',
        trigger: 'blur'
      }],
      action: [{
        required: true,
        message: '请选择按钮功能',
        trigger: 'blur'
      }],
      sort: [{
        required: true,
        message: '请输入菜单排序',
        trigger: 'blur'
      }]
    }
  }
}
