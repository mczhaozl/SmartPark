import Vue from 'vue'
import axios from "axios";
import $router from "../router/index";
import layer from "@/utils/layer";
import { Toast } from "vant";
import { VueAxios } from './axios'
import * as util from "@/utils/utils";
import store from '../store'

Toast.allowMultiple(); // 允许多个Toast实例

// 创建 axios 实例
const service = axios.create({
    baseURL: '/', // api 的 base_url
    timeout: 60000 // 请求超时时间
});

// request 拦截器
service.interceptors.request.use(
    config => {
        const token = localStorage.token
        if (token) {
            config.headers[ 'X-Access-Token' ] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
        }
        config.headers['Content-Type'] = 'application/json;charset=UTF-8';
        // 如果用戶信息存在
        config.headers['utoken'] = store.getters.userInfo.utoken ? store.getters.userInfo.utoken : ""; // 没有取本地调试token
        return config;
    },
    error => {
        // Do something with request error
        return Promise.reject(error);
    }
);

// response 拦截器
service.interceptors.response.use(
    response => {
        let res = response.data;
        if (res.code === 0 || res.code === '000' || res.code === "1" || parseInt(res.current) == 0) {
            // console.log(res, '9999')
            // 处理无意义数据，null undefined 为 空
            res = util.toEmptyStr(res)
            return res;
        } else if (parseInt(res.code) === 402) {
            layer.toast(res.message);
            $router.push("/");
        } else {
            //layer.toast(res.msg || res.message || '网络异常，请稍后重试！');
            return res; // 请求code返回-1当错误处理
        }
    },
    err => {
        // 接口错误提示
        const token = localStorage.token;
       
        if (err.response && err.response.status) {
            let msg = '出错了'
            switch (err.response.status) {
                case 400:
                    msg = '请求错误'
                    break
                case 401:
                    msg = '未授权，请登录'
                    break
                case 403:
                    msg = '拒绝访问'
                    break
                case 404:
                    msg = `请求地址出错: ${err.response.config.url}`
                    break
                case 408:
                    msg = '请求超时'
                    break
                case 500:
                    if(token && err.response.data.message=="Token失效，请重新登录"){
                        msg = '登录失效，请重新登录！'
                        layer.toast(msg)
                        $router.push("/login");
                        break
                    }else{
                        msg = '服务器错误'
                        break
                    }                   
                case 501:
                    msg = '服务未实现'
                    break
                case 502:
                    msg = '网关错误'
                    break
                case 503:
                    msg = '服务不可用'
                    break
                case 504:
                    msg = '网关超时'
                    break
                case 505:
                    msg = 'HTTP版本不受支持'
                    break
                default:
            }
            layer.toast(msg)
        }
        return Promise.reject(err);
    }
);

export default function(
    option,
    //hwq: 是否开启 api 请求 loading，默认不开启
    {
        loading = false,
        message = "加载中",
        forbidClick = true,
        loadingType = "spinner"
    } = {}
) {
    if (loading)
        var loadingInstance = Toast.loading({
            duration:0,
            forbidClick,
            loadingType,
            message
        });
    return new Promise((resolve, reject) => {
        service(option)
            .then(resolve)
            .catch(reject)
            .finally(() => {
                if (loading) loadingInstance.clear();
            });
    }).catch((e) => {});
}

const installer = {
    vm: {},
    install (Vue, router = {}) {
        Vue.use(VueAxios, router, service)
    }
}
export {
    installer as VueAxios,
    service as axios
}