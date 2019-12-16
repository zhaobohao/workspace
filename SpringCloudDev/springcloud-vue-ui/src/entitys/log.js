export default function param() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      id: undefined,
      // 服务id
      serviceId: undefined,
      // 服务host
      serverHost: undefined,
      // 服务ip
      serverIp: undefined,
      // 软件环境
      env: undefined,
      // 日志名
      title: undefined,
      // 请求方法
      method: undefined,
      // 日志时间
      createTime: undefined,
      // 用户代理
      userAgent: undefined,
      // 请求数据
      params: undefined
    },
    rules: {

    }
  }
}
