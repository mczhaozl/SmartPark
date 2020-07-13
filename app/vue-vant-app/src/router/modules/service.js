/** When your routing table is too long, you can split it into small modules**/
/**
 * 服务
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/service",
    component: Layout,
    redirect: "/service/index",
    children: [{
        path: "index",
        component: () => import("@/views/Service/index"),
        name: "RepairIndex",
        meta: { title: "服务", icon: "" }
    }]
};

export default index;