'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // PROD_ENVIROMENT:'"http://172.1.0.142:8070"'
  // 测试环境
  // BASE_API:'"http://172.1.0.142:8070"'
  // 配置中心url
  BASE_API:'"http://localhost:8070"'
})
