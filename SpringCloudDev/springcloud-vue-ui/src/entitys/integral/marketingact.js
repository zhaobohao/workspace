                                                                                    export default function area() {
                                                                                      return {
                                                                                        current: 1,
                                                                                        size: 20,
                                                                                        isNewRecord: false,
                                                                                        query: {
                                                                                          // 表id
                                                                                          marketingActId: undefined,
                                                                                          // 活动CODE
                                                                                          actCode: undefined,
                                                                                          // 活动名称
                                                                                          actName: undefined,
                                                                                          // 活动开始时间
                                                                                          beginTime: undefined,
                                                                                          // 活动结束时间
                                                                                          endTime: undefined,
                                                                                          // 卡券编号
                                                                                          cardTieketId: undefined,
                                                                                          // 积分类型
                                                                                          integralType: undefined,
                                                                                          // 积分类型ID
                                                                                          integralId: undefined,
                                                                                          // 活动链接
                                                                                          actLink: undefined,
                                                                                          // 活动状态
                                                                                          actStatus: undefined,
                                                                                          // 活动状态ID
                                                                                          actStatusId: undefined,
                                                                                          // 活动说明
                                                                                          actExplain: undefined,
                                                                                          // 活动目标描述
                                                                                          actTargetBreif: undefined,
                                                                                          // 活动卡券数量
                                                                                          actCardTicketNum: undefined,
                                                                                          // 活动目标类型
                                                                                          actTargetType: undefined,
                                                                                          // 活动目标数量
                                                                                          actTargetNumNum: undefined,
                                                                                          // 活动积分数量
                                                                                          actIntegralNumNum: undefined,
                                                                                          // 审核评语
                                                                                          reviewComments: undefined,
                                                                                          // 预留字段一
                                                                                          reserveColumn1: undefined,
                                                                                          // 预留字段二
                                                                                          reserveColumn2: undefined,
                                                                                          // 预留字段三
                                                                                          reserveColumn3: undefined
                                                                                        },
                                                                                        rules: {
                                                                                          marketingActId: [{
                                                                                            required: true,
                                                                                            message: '请输入表id',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actCode: [{
                                                                                            required: true,
                                                                                            message: '请输入活动CODE',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actName: [{
                                                                                            required: true,
                                                                                            message: '请输入活动名称',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          beginTime: [{
                                                                                            required: true,
                                                                                            message: '请输入活动开始时间',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          endTime: [{
                                                                                            required: true,
                                                                                            message: '请输入活动结束时间',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          cardTieketId: [{
                                                                                            required: true,
                                                                                            message: '请输入卡券编号',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          integralType: [{
                                                                                            required: true,
                                                                                            message: '请输入积分类型',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          integralId: [{
                                                                                            required: true,
                                                                                            message: '请输入积分类型ID',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actLink: [{
                                                                                            required: true,
                                                                                            message: '请输入活动链接',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actStatus: [{
                                                                                            required: true,
                                                                                            message: '请输入活动状态',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actStatusId: [{
                                                                                            required: true,
                                                                                            message: '请输入活动状态ID',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actExplain: [{
                                                                                            required: true,
                                                                                            message: '请输入活动说明',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actTargetBreif: [{
                                                                                            required: true,
                                                                                            message: '请输入活动目标描述',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actCardTicketNum: [{
                                                                                            required: true,
                                                                                            message: '请输入活动卡券数量',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actTargetType: [{
                                                                                            required: true,
                                                                                            message: '请输入活动目标类型',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actTargetNumNum: [{
                                                                                            required: true,
                                                                                            message: '请输入活动目标数量',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          actIntegralNumNum: [{
                                                                                            required: true,
                                                                                            message: '请输入活动积分数量',
                                                                                            trigger: 'blur'
                                                                                          }],
                                                                                          reviewComments: [{
                                                                                            required: true,
                                                                                            message: '请输入审核评语',
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
