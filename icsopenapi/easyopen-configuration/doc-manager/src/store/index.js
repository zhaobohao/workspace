import Vue from 'vue'
import Vuex from 'vuex'
import api from '@/axios/api.js'
import axios from 'axios'
import router from '@/router'
import { Message } from 'element-ui'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        resData:[],
        resAllData:{},
        appInfo:{},
        loading:true,
    },
    mutations:{
        setRes(state){
            let resUrl = JSON.parse(localStorage.getItem('appInfo')) || {}

            console.log('resUrl',resUrl)
            if(JSON.stringify(resUrl) == "{}"){
                router.push('/login')  
                return
            }
            let jwt = sessionStorage.getItem('jwt')
            state.appInfo = resUrl
            if(resUrl.needPassword===true){
                if(jwt=='' || jwt==null || jwt==undefined){
                    router.push('/login')  
                }
            }
            api.post(resUrl.indexUrl).then((res)=>{
                if(res.code){
                    router.push('/login')
                    Message({
                        message: '系统错误，错误码'+res.code,
                        type: 'error',
                        duration: 5 * 1000 
                      })
                      return
                }
                console.log('res',res)
                state.resAllData = res
                state.resData = res.apiModules
                state.loading = false
            }).catch((error)=>{
                state.loading = false
            })
        },
        setAppInfo(state,appInfo){
            state.appInfo = appInfo
            let appObj = JSON.stringify(appInfo)
            localStorage.setItem('appInfo',appObj)
        },
        setLoading(state){
            state.loading = false
        }
    }
})
