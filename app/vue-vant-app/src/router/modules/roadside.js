/**
 * 道路停车
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/roadside",
    component: Layout,
    redirect: "/roadside/index",
    children: [{
        path: "index",
        component: () => import("@/views/Roadside/index/index"),
        name: "Roadside",
        meta: { title: "道路停车", icon: "" }
    }]
};

export default index;