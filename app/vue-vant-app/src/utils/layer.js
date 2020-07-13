import { Toast, Notify, Dialog } from "vant";
let layer = {
    // toast 消息提示层
    toast: function(message = '', {
            type = 'text', // success , fail , error , loading
            duration = 3000, // 持续时间
            mask = false, // 是否显示遮罩
            forbidClick = true, // 是否禁止背景点击
            position = 'middle', // 显示位置
            loadingType = 'circular', // 加载图标类型, 可选值为 spinner
            onClose // 提示层关闭时回调
        } = {}

    ) {

        type = type === 'error' ? 'fail' : type

        Toast({
            message,
            type,
            duration,
            mask,
            forbidClick,
            position,
            loadingType,
            onClose
        })

    },
    // 弹窗关闭
    toastClear: function() {
        Toast.clear();
    },
    // notify 提示框
    notify: function(message, { duration = 3000, background = "#ff4444" } = {}) {
        Notify({
            message,
            duration,
            background
        });
    },
    // alert 警告框
    alert: function(
        message, { title = "标题", showConfirmButton = false, confirmButtonText = "确认" }
    ) {
        return new Promise((resolve, reject) => {
            Dialog.alert({
                    message,
                    title,
                    showConfirmButton,
                    confirmButtonText
                })
                .then(() => resolve())
                .catch(() => reject());
        });
    },
    //确认框
    confirm: function(
        message, {
            title = "温馨提示",
            showConfirmButton = true,
            confirmButtonText = "确认",
            showCancelButton = true,
            cancelButtonText = "取消"
        } = {}
    ) {
        return new Promise((resolve, reject) => {
            Dialog.alert({
                    message,
                    title,
                    showConfirmButton,
                    confirmButtonText,
                    showCancelButton,
                    cancelButtonText
                })
                .then(() => resolve())
                .catch(() => reject());
        });
    }
};
export default layer;