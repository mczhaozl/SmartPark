/**
 * 找车位
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/parkinglot",
    component: Layout,
    redirect: "/parkinglot/index",
    children: [{
        path: "index",
        component: () => import("@/views/Parkinglot/index/index"),
        name: "Parkinglot",
        meta: { title: "找车位", icon: "" }
    },{
        path: "list",
        component: () => import("@/views/Parkinglot/list/index"),
        name: "ParkinglotList",
        meta: { title: "找车位", icon: "" }
    }]
};

export default index;