import Vue from "vue";
import Router from "vue-router";
Vue.use(Router);

import Home from "./modules/home"; // 主页面
import Service from "./modules/service"; // 服务
import News from "./modules/news"; // 资讯
import Me from "./modules/me"; // 我的
import Parkinglot from "./modules/parkinglot"; // 找车位
import Appointment from "./modules/appointment"; // 预约停车
import Roadside from "./modules/roadside"; // 预约停车
import Pay from "./modules/pay"; // 停车缴费
let staticRoutes = [
    Home,
    Me,
    News,
    Parkinglot,
    Service,
    Appointment,
    Roadside,
    Pay,
    {
        path: "/404",
        name: "notFound",
        component: () =>
            import("@/views/Home/error/notFound.vue")
    },
    {
        path: "*",
        redirect: "404",
    }
];
export default new Router({
    // mode: 'history',
    routes: staticRoutes
});