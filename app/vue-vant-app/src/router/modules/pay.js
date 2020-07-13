/**
 * 道路停车
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/pay",
    component: Layout,
    redirect: "/pay/index",
    children: [{
        path: "index",
        component: () => import("@/views/Pay/index/index"),
        name: "Pay",
        meta: { title: "停车缴费", icon: "" }
    }]
};

export default index;