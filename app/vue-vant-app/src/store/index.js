import Vue from 'vue'
import Vuex from 'vuex'
import common from './modules/common'
import createPersistedState from 'vuex-persistedstate'
Vue.use(Vuex)
const store = new Vuex.Store({
    modules: {
        common
    },
    plugins: [createPersistedState({
        storage: window.sessionStorage // 记录sessionStorage,会话结束缓存清除
    })]
})

export default store