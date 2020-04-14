export default function area() {
  return {
    current: 1,
    size: 20,
    isNewRecord: false,
    query: {
      // 表id
      id: undefined,
      // web_site表id
      webSiteId: undefined,
      // 配置的url路径，支持正则表达式
      requestPath: '.*',
      // http的method,例如get,put,delete,post,支持正则表达式
      requestMethod: 'get',
      // http传过来的参数，录入为json结构。key,value支持正则表达式
      requestParams: undefined,
      // http传过来的头部参数，录入为json结构，key,value支持正则表达式
      requestHeaders: undefined,
      // htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式
      requestCookies: undefined,
      // http传过来的request报文里的body段所包含的值，默认是使用正则表达式
      requestJsonBody: undefined,
      // request报文的编码，默认为utf-8
      requestCharsets: 'utf-8',
      // http传过来的request报文里的body段所包含的值，默认是使用正则表达式
      requestFormBody: undefined,
      // response报文里的headers,录入为json.程序会正确拆分，附值 。
      responseHeaders: undefined,
      // response报文里的body
      responseBody: undefined,
      // response报文里的body的编码
      responseCharsets: 'utf-8',
      // response报文里的 status code.例如：400，302，501
      responseStatusCode: 200,
      // 配合statuscode使用的，状态码解释文本。
      responseReasonPhrase: '接口访问成功',
      // 延迟响应时候，默认时间单位为秒。
      responseDelay: 0,
      // 跳转域名
      forwardHost: undefined,
      // 跳转域名接口
      forwardPort: undefined,
      // 跳转路径
      forwardPath: undefined,
      // 跳转时头部参数，录入为json.程序自动拆分配置。
      forwardHeaders: undefined,
      // 跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress("target.host.com", 1234, SocketAddress.Scheme.HTTPS)
      forwardSocketAddress: undefined,
      // 跳转时的延迟时间，默认时间单位为秒
      forwardDelay: undefined,
      // 跳转时，重写转给第三方的body.
      forwardBody: undefined,
      // 是否丢失链接，true,false
      errorDropConnection: undefined,
      // 出错时，返回的报文
      errorResponseBytes: undefined,
      requestPath_like: undefined,
      requestMethod_like: undefined,
      webSiteId_equal: undefined
    },
    rules: {
      requestPath: [{
        required: true,
        message: '请输入配置的url路径，支持正则表达式',
        trigger: 'blur'
      }],
      requestMethod: [{
        required: true,
        message: '请输入http的method,例如get,put,delete,post,支持正则表达式',
        trigger: 'blur'
      }],
      requestParams: [{
        required: false,
        message: '请输入http传过来的参数，录入为json结构。key,value支持正则表达式',
        trigger: 'blur'
      }],
      requestHeaders: [{
        required: false,
        message: '请输入http传过来的头部参数，录入为json结构，key,value支持正则表达式',
        trigger: 'blur'
      }],
      requestCookies: [{
        required: false,
        message: '请输入htpp传过来的cookies参数，录入为json结构，key,value支持正则表达式',
        trigger: 'blur'
      }],
      requestJsonBody: [{
        required: false,
        message: '请输入http传过来的request报文里的body段所包含的值，默认是使用正则表达式',
        trigger: 'blur'
      }],
      requestCharsets: [{
        required: false,
        message: '请输入request报文的编码，默认为utf-8',
        trigger: 'blur'
      }],
      requestFormBody: [{
        required: false,
        message: '请输入http传过来的request报文里的body段所包含的值，默认是使用正则表达式',
        trigger: 'blur'
      }],
      responseHeaders: [{
        required: false,
        message: '请输入response报文里的headers,录入为json.程序会正确拆分，附值 。',
        trigger: 'blur'
      }],
      responseBody: [{
        required: false,
        message: '请输入response报文里的body',
        trigger: 'blur'
      }],
      responseCharsets: [{
        required: false,
        message: '请输入response报文里的body的编码',
        trigger: 'blur'
      }],
      responseStatusCode: [{
        required: false,
        message: '请输入response报文里的 status code.例如：400，302，501',
        trigger: 'blur'
      }],
      responseReasonPhrase: [{
        required: false,
        message: '请输入配合statuscode使用的，状态码解释文本。',
        trigger: 'blur'
      }],
      responseDelay: [{
        required: false,
        message: '请输入延迟响应时候，默认时间单位为秒。',
        trigger: 'blur'
      }],
      forwardHost: [{
        required: false,
        message: '请输入跳转域名',
        trigger: 'blur'
      }],
      forwardPort: [{
        required: false,
        message: '请输入跳转域名接口',
        trigger: 'blur'
      }],
      forwardPath: [{
        required: false,
        message: '请输入跳转路径',
        trigger: 'blur'
      }],
      forwardHeaders: [{
        required: false,
        message: '请输入跳转时头部参数，录入为json.程序自动拆分配置。',
        trigger: 'blur'
      }],
      forwardSocketAddress: [{
        required: false,
        message: '请输入跳转时，重写request时的，请求地址。录入时为json数据格式。程序会自动拆分。withSocketAddress("target.host.com", 1234, SocketAddress.Scheme.HTTPS)',
        trigger: 'blur'
      }],
      forwardDelay: [{
        required: false,
        message: '请输入跳转时的延迟时间，默认时间单位为秒',
        trigger: 'blur'
      }],
      forwardBody: [{
        required: false,
        message: '请输入跳转时，重写转给第三方的body.',
        trigger: 'blur'
      }],
      errorDropConnection: [{
        required: false,
        message: '请输入是否丢失链接，true,false',
        trigger: 'blur'
      }],
      errorResponseBytes: [{
        required: false,
        message: '请输入出错时，返回的报文',
        trigger: 'blur'
      }]
    }
  }
}
