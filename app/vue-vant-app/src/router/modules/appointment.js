/**
 * 预约停车
 * 页面的 name 值是页面路径的全拼
 */
import Layout from "@/views/Layout";

const index = {
    path: "/appointment",
    component: Layout,
    redirect: "/appointment/index",
    children: [{
        path: "index",
        component: () => import("@/views/Appointment/index/index"),
        name: "Appointment",
        meta: { title: "预约停车", icon: "" }
    },{
        path: "detail",
        component: () => import("@/views/Appointment/detail/detail"),
        name: "AppointmentDetail",
        meta: { title: "预约停车", icon: "" }
    },{
        path: "form",
        component: () => import("@/views/Appointment/form/form"),
        name: "AppointmentForm",
        meta: { title: "预约停车", icon: "" }
    }]
};

export default index;