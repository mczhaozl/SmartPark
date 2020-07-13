/** When your routing table is too long, you can split it into small modules**/
/**
 * 我的
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/me",
    component: Layout,
    redirect: "/me/index",
    children: [{
        path: "index",
        component: () => import("@/views/Me/index"),
        name: "TeamsIndex",
        meta: { title: "我的", icon: "" }
    }]
};

export default index;