'use strict'

import axios from 'axios'
import router from '../router'

// 对axios的方法进行一层封装
export default {
  post (url, data) {
    return axios({
      method: 'post',
      url,
      data: data,
      timeout: 5000,
      headers: {
        'X-Requested-With': 'XMLHttpRequest',
        'Content-Type': 'application/json; charset=UTF-8'
      }
    }).then(
      (response) => {
      // return checkStatus(response)
    }
  ).then(
      (res) => {
      // return checkCode(res)
    }
  )
  }
}
