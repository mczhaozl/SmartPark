// The Vue build version to load with the `import` command
import Vue from "vue";
import App from "./App";
import vueRouter from 'vue-router'
import router from "./router";
import store from "./store"; // 引用store
import './icons' // icon
import './components/cpicFriendUI/index' // icon
import "@/permission"; // permission control
import "lib-flexible"; // rem响应文件

import myPlugins from "@/utils/plugins"
// 引入vue-amap
import AMap from 'vue-amap';
Vue.use(AMap);
Vue.use(myPlugins)
import "@/components/Vant"; // 引用vant组件
import "vant/lib/index.css";
// import 'swiper/css/swiper.css';

import "@/styles/index.scss"; // global css

// vconsole状态
/*
import Vconsole from 'vconsole'
// 上线关闭
const vConsole = process.env.VUE_APP_VCONSOLE === 'true' ? new Vconsole() : {};
*/
// 初始化vue-amap
AMap.initAMapApiLoader({
    // 申请的高德key
    key: 'b9e229109bb17a314c7c302fbf43f00d',
    // 插件集合
    v: '1.4.4',
    protocol:'https',
    plugin: ['AMap.Geolocation','AMap.Autocomplete','AMap.PlaceSearch'],
    uiVersion: '1.0.11'  //添加 uiVersion 的脚本版本号
});

//hwq: 在ios中, fastclick.js 对复制到剪切板功能有影响，目前移动端可以通过设置 touch-action: manipulation 来解决 300ms 延迟问题
Vue.config.productionTip = false;

// 解决不能跳转自己的问题
const originalPush = vueRouter.prototype.push
vueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

/* eslint-disable no-new */
new Vue({
    el: "#app",
    router,
    store,
    render(h) {
        return h(App);
    }
});