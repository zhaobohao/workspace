import axios from 'axios'
import {
  MessageBox,
  Message
} from 'element-ui'
import store from '@/store'
import {
  getToken
} from '@/utils/auth'
import NProgress from 'nprogress' // progress bar
import {
  serialize
} from '@/utils/util'
import {
  Base64
} from 'js-base64'
import website from '@/config/website'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000, // request timeout
  validateStatus: function (status) {
    return status >= 200 && status <= 500 // 默认的
  },
  withCredentials: true // 跨域请求，允许保存cookie
})
// NProgress Configuration
NProgress.configure({
  showSpinner: false
})
// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    NProgress.start() // start progress bar
    const meta = (config.meta || {})
    const isToken = meta.isToken === false
    config.headers['Authorization'] = `Basic ${Base64.encode(`${website.clientId}:${website.clientSecret}`)}`
    if (getToken() && !isToken) {
      config.headers['springcloud-auth'] = 'bearer ' + getToken() // 让每个请求携带token--['Authorization']为自定义key 请根据实际情况自行修改
    }
    // headers中配置serialize为true开启序列化
    if (config.method === 'post' && meta.isSerialize === true) {
      config.data = serialize(config.data)
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    if (response.headers['content-type'].indexOf('octet-stream') > 0) {
      // console.log(response.headers['content-disposition'])
      return response
    }
    const res = response.data
    NProgress.done()
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 200) {
      Message({
        message: res.msg || '错误',
        type: 'error',
        duration: 5 * 1000
      })
      const statusWhiteList = website.statusWhiteList || []
      // 如果在白名单里则自行catch逻辑处理
      if (statusWhiteList.includes(status)) return Promise.reject(res)
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 401 || res.code === 50008 || res.code === 50012 || res.code === 50014) {
        // to re-login
        MessageBox.confirm('您已被强制退出系统，请选择重新登录！', '确认退出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.msg || '错误'))
    } else {
      return res
    }
  },
  error => {
    console.log('err ' + error) // for debug
    NProgress.done()
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
