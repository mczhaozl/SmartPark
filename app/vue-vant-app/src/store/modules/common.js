import Vue from 'vue'
import { userState } from "@/api/common";
import $router from "../../router/index";
const common = {
    state: {
        isLoading: false, // 是否正在加载，主要在上拉刷新下拉加载时使用
        refreshIng: false, // 是否正在下拉刷新，主要在上拉刷新下拉加载时使用
        userInfo: {}, // 用户信息
        locationData: {}, // 手机定位信息
        deviceInfo: {}
    },
    getters: {
        userInfo: state => state.userInfo, //用户信息
        locationData: state => state.locationData,
        deviceInfo: state => state.deviceInfo,
        isLoading: state => state.isLoading,
        refreshIng: state => state.refreshIng,
    },
    mutations: {
        set_loading: (state, type) => {
            state.isLoading = type;
        },
        set_refresh: (state, type) => {
            state.refreshIng = type;
        },
        set_userInfo: (state, userInfo) => {
            state.userInfo = userInfo;
        },
        set_location: (state, data) => {
            state.locationData = data;
        },
        set_deviceInfo: (state, data) => {
            state.deviceInfo = data;
        }
    },
    actions: {
        // 导出方法都要添加命名空间
        storeSetLoading({ commit }, data) {
            commit('set_loading', data)
        },
        storeSetRefresh({ commit }, data) {            
            commit('set_refresh', data)
        },
        storeSetUserInfo({ commit }, userInfo) {
            commit('set_userInfo', userInfo)
        },
        storeSetLocationData({ commit }, data) {
            commit('set_location', data)
        },
        storeSetDeviceInfo({ commit }, data) {
            commit('set_deviceInfo', data)
        }
    }
}

export default common