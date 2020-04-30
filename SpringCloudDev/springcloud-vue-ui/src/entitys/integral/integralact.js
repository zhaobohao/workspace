                                                                                            export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
    // 编号
  integralActId: undefined,
      // 积分活动编号
  actCode: undefined,
      // 积分活动名称
  actName: undefined,
      // 营销活动编号
  marketActId: undefined,
      // 营销活动结束时间
  endTime: undefined,
      // 所属部门
  department: undefined,
      // 积分类型
  integralType: undefined,
      // 积分有效期类型
  integralLimitTimeType: undefined,
      // 积分有效期年限
  integralLimitYearNum: undefined,
      // 积分到期月份
  integralEndMonth: undefined,
      // 计划积分
  prepareIntegralNum: undefined,
      // 计划人数
  prepareCount: undefined,
      // 活动描述
  actBreif: undefined,
      // 状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回
  state: undefined,
      // 所属规则组
  ruleTeam: undefined,
      // 审核意见
  opinion: undefined,
      // 创建人
  crtUser: undefined,
      // 创建时间
  crtTime: undefined,
      // 最后更新人
  lstUpdUser: undefined,
      // 最后更新时间
  lstUpdTime: undefined,
      // 预留字段一
  reserveColumn1: undefined,
      // 预留字段二
  reserveColumn2: undefined,
      // 预留字段三
  reserveColumn3: undefined
          },
    rules: {
        integralActId: [{
  required: true,
  message: '请输入编号',
  trigger: 'blur'
  }],
          actCode: [{
  required: true,
  message: '请输入积分活动编号',
  trigger: 'blur'
  }],
          actName: [{
  required: true,
  message: '请输入积分活动名称',
  trigger: 'blur'
  }],
          marketActId: [{
  required: true,
  message: '请输入营销活动编号',
  trigger: 'blur'
  }],
          endTime: [{
  required: true,
  message: '请输入营销活动结束时间',
  trigger: 'blur'
  }],
          department: [{
  required: true,
  message: '请输入所属部门',
  trigger: 'blur'
  }],
          integralType: [{
  required: true,
  message: '请输入积分类型',
  trigger: 'blur'
  }],
          integralLimitTimeType: [{
  required: true,
  message: '请输入积分有效期类型',
  trigger: 'blur'
  }],
          integralLimitYearNum: [{
  required: true,
  message: '请输入积分有效期年限',
  trigger: 'blur'
  }],
          integralEndMonth: [{
  required: true,
  message: '请输入积分到期月份',
  trigger: 'blur'
  }],
          prepareIntegralNum: [{
  required: true,
  message: '请输入计划积分',
  trigger: 'blur'
  }],
          prepareCount: [{
  required: true,
  message: '请输入计划人数',
  trigger: 'blur'
  }],
          actBreif: [{
  required: true,
  message: '请输入活动描述',
  trigger: 'blur'
  }],
          state: [{
  required: true,
  message: '请输入状态: audit_status_tosub：新增，audit_status_wait提交待审核，audit_status_pass审核通过，audit_status_nopass退回',
  trigger: 'blur'
  }],
          ruleTeam: [{
  required: true,
  message: '请输入所属规则组',
  trigger: 'blur'
  }],
          opinion: [{
  required: true,
  message: '请输入审核意见',
  trigger: 'blur'
  }],
          crtUser: [{
  required: true,
  message: '请输入创建人',
  trigger: 'blur'
  }],
          crtTime: [{
  required: true,
  message: '请输入创建时间',
  trigger: 'blur'
  }],
          lstUpdUser: [{
  required: true,
  message: '请输入最后更新人',
  trigger: 'blur'
  }],
          lstUpdTime: [{
  required: true,
  message: '请输入最后更新时间',
  trigger: 'blur'
  }],
          reserveColumn1: [{
  required: true,
  message: '请输入预留字段一',
  trigger: 'blur'
  }],
          reserveColumn2: [{
  required: true,
  message: '请输入预留字段二',
  trigger: 'blur'
  }],
          reserveColumn3: [{
  required: true,
  message: '请输入预留字段三',
  trigger: 'blur'
  }]
      }
  }
}
