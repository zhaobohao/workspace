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
      //  应用id
      clientId: undefined,
      //  应用密钥
      clientSecret: undefined,
      //  授权类型
      authorizedGrantTypes: 'refresh_token,password,authorization_code',
      //  授权范围
      scope: 'all',
      //  令牌秒数
      accessTokenValidity: 3600,
      // 刷新秒数
      refreshTokenValidity: 604800,
      // 回调地址
      webServerRedirectUri: undefined,
      // 资源集合
      resourceIds: undefined,
      // 权限
      authorities: undefined,
      // 自动授权
      autoapprove: undefined,
      // 附加说明
      additionalInformation: undefined

    },
    rules: {
      clientId: [{
        required: true,
        message: '请输入客户端id',
        trigger: 'blur'
      }],
      clientSecret: [{
        required: true,
        message: '请输入客户端密钥',
        trigger: 'blur'
      }],
      authorizedGrantTypes: [{
        required: true,
        message: '请输入授权类型',
        trigger: 'blur'
      }],
      scope: [{
        required: true,
        message: '请输入授权范围',
        trigger: 'blur'
      }],
      accessTokenValidity: [{
        required: true,
        message: '请输入令牌过期秒数',
        trigger: 'blur'
      }],
      refreshTokenValidity: [{
        required: true,
        message: '请输入刷新令牌过期秒数',
        trigger: 'blur'
      }],
      webServerRedirectUri: [{
        required: true,
        message: '请输入回调地址',
        trigger: 'blur'
      }],
      resourceIds: [{
        message: '请输入资源集合',
        trigger: 'blur'
      }],
      authorities: [{
        message: '请输入权限',
        trigger: 'blur'
      }],
      autoapprove: [{
        message: '请输入自动授权',
        trigger: 'blur'
      }],
      additionalInformation: [{
        message: '请输入附加说明',
        trigger: 'blur'
      }]
    }
  }
}
