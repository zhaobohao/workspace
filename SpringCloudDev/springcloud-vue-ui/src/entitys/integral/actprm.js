export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      // 表id编号
      actPrmId: undefined,
      // 活动类别编号
      code: undefined,
      // 活动类别名称
      name: undefined,
      // 备注
      apDesc: undefined,
      // 状态取自参数表
      stat: undefined,
      // 显示顺序
      sort: undefined,
      hideDeptId: undefined,
      dataModeId: undefined,
      parentCoderId: undefined,
      selectorModeId: undefined,
      conditionTypeId: undefined,
      // 所属系统ID
      sysId: undefined,
      // 预留字段1
      reserveCokumn1: undefined,
      // 预留字段2
      reserveCokumn2: undefined,
      // 预留字段3
      reserveCokumn3: undefined,
      // 日期类型（当DATA_MODE为DATE时的扩展）
      dateType: undefined,
      // 试算是否显示字段，0显示1不显示
      trialShow: undefined,
      // 隐藏预留字段
      parentValue: undefined,
      // 转换表达式
      value: undefined
    },
    rules: {
      actPrmId: [{
        required: true,
        message: '请输入表id编号',
        trigger: 'blur'
      }],
      code: [{
        required: true,
        message: '请输入活动类别编号',
        trigger: 'blur'
      }],
      name: [{
        required: true,
        message: '请输入活动类别名称',
        trigger: 'blur'
      }],
      apDesc: [{
        required: true,
        message: '请输入备注',
        trigger: 'blur'
      }],
      stat: [{
        required: true,
        message: '请输入状态取自参数表',
        trigger: 'blur'
      }],
      sort: [{
        required: true,
        message: '请输入显示顺序',
        trigger: 'blur'
      }],
      hideDeptId: [{
        required: true,
        message: '请输入',
        trigger: 'blur'
      }],
      dataModeId: [{
        required: true,
        message: '请输入',
        trigger: 'blur'
      }],
      parentCoderId: [{
        required: true,
        message: '请输入',
        trigger: 'blur'
      }],
      selectorModeId: [{
        required: true,
        message: '请输入',
        trigger: 'blur'
      }],
      conditionTypeId: [{
        required: true,
        message: '请输入',
        trigger: 'blur'
      }],
      sysId: [{
        required: true,
        message: '请输入所属系统ID',
        trigger: 'blur'
      }],
      reserveCokumn1: [{
        required: true,
        message: '请输入预留字段1',
        trigger: 'blur'
      }],
      reserveCokumn2: [{
        required: true,
        message: '请输入预留字段2',
        trigger: 'blur'
      }],
      reserveCokumn3: [{
        required: true,
        message: '请输入预留字段3',
        trigger: 'blur'
      }],
      dateType: [{
        required: true,
        message: '请输入日期类型（当DATA_MODE为DATE时的扩展）',
        trigger: 'blur'
      }],
      trialShow: [{
        required: true,
        message: '请输入试算是否显示字段，0显示1不显示',
        trigger: 'blur'
      }],
      parentValue: [{
        required: true,
        message: '请输入隐藏预留字段',
        trigger: 'blur'
      }],
      value: [{
        required: true,
        message: '请输入转换表达式',
        trigger: 'blur'
      }]
    }
  }
}
