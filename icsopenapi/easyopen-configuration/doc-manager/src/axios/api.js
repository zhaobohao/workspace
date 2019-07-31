import axios from 'axios'
import vue from 'vue'
import { Message,MessageBox} from 'element-ui';
import router from '@/router'
import store from '@/store'
console.log('process.env.BASE_API',process.env.BASE_API)
axios.defaults.baseURL = process.env.BASE_API

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
// 设置请求request超时时长
axios.defaults.timeout =  5000;
// 请求拦截器
axios.interceptors.request.use(function(config) {
  // let jwt = window.sessionStorage.jwt;
  // let resUrl = JSON.parse(localStorage.getItem('appInfo'))
  // if (jwt!== 'null'&& jwt!== null && jwt !=='') {
  //     config.headers.Authorization = resUrl.app + ' ' + jwt;
  // }
    return config;
  }, function(error) {
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    // MessageBox.confirm('此操作将永久删除该文件, 是否继续?', '提示', {
    //   confirmButtonText: '确定',
    //   cancelButtonText: '取消',
    //   type: 'warning'
    // }).then(() => {
    //   Message({
    //     type: 'success',
    //     message: '删除成功!'
    //   });
    // }).catch(() => {
    //   Message({
    //     type: 'info',
    //     message: '已取消删除'
    //   });          
    // });
    return Promise.reject(error);
  })

  // 响应拦截器，响应超时不能设置
axios.interceptors.response.use(function(response) {
  if(response.status=='405'){
    
  }
  return response;
}, function(error) {
  // 提示响应错误信息
  let errorMsg = ''
  if(error.message=='Network Error'){
    errorMsg = '网络错误'
  }else if(error.message=='timeout of 5000ms exceeded'){
    errorMsg = '连接超时'
  }else{
    errorMsg = error.message
  }
  Message({
    message: errorMsg,
    type: 'error',
    duration: 5 * 1000 
  })
  // MessageBox.confirm(error.message, '提示', {
  //   confirmButtonText: '刷新',
  //   cancelButtonText: '取消',
  //   type: 'warning'
  // }).then(() => {
  //   // 刷新
  //   store.commit('setRes')
  // }).catch(() => {
  //   // 取消         
  // });
  return Promise.reject(error);
})
 
// 封装axios的post请求
export function fetch(url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params)
      .then(response => {
        // if(response.data.code && response.data.code=='-9'){
        //   reject(error);
        // }
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      })
  })
}
 
export default {
  post(url, params) {
    return fetch(url, params);
  }
}