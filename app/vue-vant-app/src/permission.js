import router from './router';
import { DJSettingPlugin } from "@/utils/webViewBridge";
import $store from '@/store';

// 全局路由钩子
router.beforeEach((to, from, next) => {
    // 配置statusBar颜色 并且 在壳运行
    if (to.meta.statusBackGroundColor && ($store.getters.deviceInfo.system != undefined && $store.getters.deviceInfo.system !== "web")) {
        DJSettingPlugin.setHeadColor({ headColor: to.meta.statusBackGroundColor });
    }
    // 设定title
    document.title = to.meta.title
    next();
})

router.afterEach(() => {
    // 进入页面之间，滚动到顶部
    window.scrollTo(0, 0);
})