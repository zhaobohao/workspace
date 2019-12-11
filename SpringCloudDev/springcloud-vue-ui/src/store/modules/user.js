import {
  loginByUsername,
  logout,
  getUserInfo,
  refeshToken,
  getMenu
} from '@/api/user'
import {
  getToken,
  setToken,
  removeToken,
  getRefreshToken,
  setRefreshToken,
  removeRefreshToken
} from '@/utils/auth'
import router, {
  resetRouter,
  componentMap
} from '@/router'
import {
  setStore
} from '@/utils/store.js'
const state = {
  token: getToken(),
  name: '',
  avatar: '',
  introduction: '',
  roles: [],
  refreshtoken: ''
}
/* Layout */
import Layout from '@/layout'
import {
  TimePicker
} from 'element-ui'

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
    setStore({
      name: 'tokenTime',
      content: new Date().getTime(),
      type: 'session'
    })
  },
  SET_REFRESH_TOKEN: (state, refreshtoken) => {
    state.refreshtoken = refreshtoken
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  }
}

const actions = {
  // user login
  login({
    commit,
    state
  }, userInfo) {
    const {
      username,
      password
    } = userInfo
    return new Promise((resolve, reject) => {
      loginByUsername(
        '000000',
        username.trim(),
        password,
        'password'
      ).then(response => {
        const {
          data
        } = response
        commit('SET_TOKEN', data.accessToken)
        commit('SET_REFRESH_TOKEN', data.refreshToken)
        setRefreshToken(data.refreshToken)
        setToken(data.accessToken)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  refeshToken({
    commit,
    state
  }) {
    return new Promise((resolve, reject) => {
      // console.log(state)
      refeshToken(
        'refresh_token',
        getRefreshToken()
      ).then(response => {
        const {
          data
        } = response
        commit('SET_TOKEN', data.accessToken)
        commit('SET_REFRESH_TOKEN', data.refreshToken)
        setRefreshToken(data.refreshToken)
        setToken(data.accessToken)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },
  // get user info
  getInfo({
    commit,
    state
  }) {
    return new Promise((resolve, reject) => {
      getUserInfo().then(response => {
        const {
          data
        } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const {
          roles,
          name,
          avatar,
          introduction
        } = data

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_INTRODUCTION', introduction)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({
    commit,
    state
  }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_REFRESH_TOKEN')
        removeToken()
        removeRefreshToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({
    commit
  }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      commit('SET_REFRESH_TOKEN')
      removeToken()
      resolve()
    })
  },
  // 获取系统菜单
  getMenu({
    commit,
    state,
    dispatch
  }) {
    return new Promise((resolve, reject) => {
      getMenu().then(response => {
        const {
          data
        } = response
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },
  // dynamically modify permissions
  changeRoles({
    commit,
    dispatch
  }, role) {
    return new Promise(async resolve => {
      const token = role + '-token'

      commit('SET_TOKEN', token)
      setToken(token)

      const {
        roles
      } = await dispatch('getInfo')

      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = await dispatch('permission/generateRoutes', roles, {
        root: true
      })

      // dynamically add accessible routes
      router.addRoutes(accessRoutes)

      // reset visited views and cached views
      dispatch('tagsView/delAllViews', null, {
        root: true
      })

      resolve()
    })
  },
  // dynamically modify menu
  dynamicallyLoadMenu({
    commit,
    dispatch
  }) {
    return new Promise(async resolve => {
      const menus = await dispatch('getMenu')
      resetRouter()

      // generate accessible routes map based on roles
      const accessRoutes = generateRoutes(menus)
      dispatch('permission/updateRoutes', accessRoutes, {
        root: true
      })
      resolve(accessRoutes)
    })
  }
}
export function generateRoutes(menus) {
  const res = []
  menus.forEach(menu => {
    const tmp = {
      path: menu.path,
      hidden: false,
      name: menu.code,
      meta: {
        title: menu.code,
        icon: menu.source,
        noCache: false,
        breadcrumb: true
      }
    }
    if (menu.children && menu.parentId === 0) {
      tmp.redirect = menu.path
      tmp.alwaysShow = true
      tmp.component = Layout
    } else {
      tmp.component = componentMap[menu.code]
    }

    if (menu.children) {
      tmp.children = generateRoutes(menu.children)
    }
    res.push(tmp)
  })
  return res
}
export default {
  namespaced: true,
  state,
  mutations,
  actions
}
