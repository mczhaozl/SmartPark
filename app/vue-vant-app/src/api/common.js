import request from "@/utils/request";
/**
 * 接口命名规范
 * Api+模块名+接口最后一个词
 * 示例：/api/user/login : ApiCommonLogin
 */

/**
 * 公用接口，全局模块要用到的接口
 */


// 获取验证码接口 
export function captcha(params) {
    return request({
        url: "/api/captcha",
        method: "post",
        data: params
    });
}
