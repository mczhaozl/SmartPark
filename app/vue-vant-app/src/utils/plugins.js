let plugins = {}
import $store from '@/store'
plugins.install = function (Vue, options) {
    // 自定义指令
    Vue.directive('my-directive', {
        bind(el, binding, vnode, oldVnode) {
            // 逻辑...
        }
    })
    /**
     * 全局金额格式化指令
     */
    Vue.filter('money_format', function (value) {
        let money = parseFloat(value)
        let format = money.toLocaleString().split('.')
        let i = ''
        if (format[1] && format[1].length == 1) {
            i = format[1] + '0'
        } else if (format[1] && format[1].length > 1) {
            i = format[1].substr(0, 2)
        } else {
            i = '00'
        }
        return `${format}.${i}`
    })
    Vue.mixin({
        data() {
            return {
                deviceHeight: parseInt(document.body.clientHeight),
                deviceWidth: parseInt(document.body.clientWidth),
                mainColor: '#005BAC' // 项目主颜色
            }
        },
        created() {
        },
        computed: {
            device: {
                set() {
                    //
                },
                get() {
                    // console.log($store.getters.deviceInfo.system, '存起来的system')
                    let data = $store.getters.deviceInfo.system
                    let device = ''
                    if (data === 'ios') {
                        device = 'ios'
                    } else if (data === 'android') {
                        device = 'android'
                    } else {
                        device = 'web'
                    }
                    return device
                }
            },
            // 后台服务地址
            serveUrl() {
                if (process.env.NODE_ENV == 'development') {
                    // 本地返回接口映射地址
                    return process.env.VUE_APP_BASE_URL;
                } else {
                    // 获取服务器域名
                    return location.protocol + "//" + document.domain;
                }
            }
        }
    })
}

export default plugins