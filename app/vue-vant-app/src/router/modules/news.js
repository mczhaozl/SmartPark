/** When your routing table is too long, you can split it into small modules**/
/**
 * 新闻资讯
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/news",
    component: Layout,
    redirect: "/news/index",
    children: [{
        path: "index",
        component: () => import("@/views/News/index"),
        name: "NewsIndex",
        meta: { title: "资讯", icon: "" }
    }]
};

export default index;