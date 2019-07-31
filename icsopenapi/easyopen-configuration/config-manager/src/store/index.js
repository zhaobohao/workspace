
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state:{
        openState:false
    },
    mutations:{
        toggleState(state){
            state.openState = !state.openState
        }
    }
})
