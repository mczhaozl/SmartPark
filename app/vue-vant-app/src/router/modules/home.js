/** When your routing table is too long, you can split it into small modules**/
/**
 * 一级路由使用当前模块，如首页，登录页等
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/",
    component: Layout,
    redirect: "/index", // 默认跟路由重定向至登录页
    children: [{
        path: "login",
        component: () => import("@/views/Home/login"),
        name: "HomeLogin",
        meta: { title: "登录", icon: "" }
    }, {
        path: "index",
        component: () => import("@/views/Home/index/index"),
        name: "HomeIndex",
        meta: { title: "首页", icon: "" }
    }, {
        path: "icons",
        component: () => import("@/views/Home/icons/index"),
        name: "HomeIcons",
        meta: { title: "SVG图标", icon: "" }
    }, {
        path: "test",
        component: () => import("@/views/Home/test/index"),
        name: "HomeTest",
        meta: { title: "技术准备页面", icon: "" }
    }]
};

export default index;