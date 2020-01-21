import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken,set } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    storeName:'',
    menus: [],
    roles: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_STORENAME: (state, storeName) => {
      state.storeName = storeName
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    }
  },

  actions: {
    // 登录
    Login1({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          console.log(response);
          const data = response.data
          const tokenStr = data.tokenHead+data.token
          setToken(tokenStr)

          commit('SET_MENUS', data.menus);
          commit('SET_TOKEN', tokenStr);

          resolve()
        }).catch(error => {
          console.log(error);
          reject(error)
        })
      })
    },Login({ commit }, userInfo) {
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(response => {
          if (response.access_token) {
            setToken('bearer ' +response.access_token);
            commit('SET_TOKEN', 'bearer ' +response.access_token);
            resolve()
          }
        }).catch(error => {
          console.log(error);
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(response => {
          const data = response.data
          if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', data.roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          set('userId',data.userId)
          set('storeId',data.storeId)
          commit('SET_NAME', data.username)
          commit('SET_AVATAR', data.icon)
          commit('SET_STORENAME', data.storeName)
          resolve(response)
        }).catch(error => {
          console.log(error);
          reject(error)
        })
      })
    },

    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_MENUS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }

  }
}

export default user
