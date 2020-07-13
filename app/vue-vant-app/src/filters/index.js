// 公共过滤器
import moment from "moment"
/**
 * @author: hongwenqing
 * @date: 
 * @desc: 时间格式处理
 * @lastEditor: 
 */
export const timeFilter = function(val, { format = 'Y-MM-DD' } = {}) {
    if (!val) return ''
    return moment(val).format(format)
}

/**
 * @author: wangyuhong
 * @date: 
 * @desc: 保留小数
 * @lastEditor: 
 */
export const number = function(val, num = 1) {
    val = Number.parseFloat(val) || "";
    return val != "" ? val.toFixed(num) : 0;
}

/**
 * @author: hongwenqing
 * @date: 
 * @desc: 转百分比
 * @lastEditor: 
 */
export const percentFilter = function(str, num = 0) {

    if (!str) return ''

    str = Number.parseFloat(str)

    return (str * 100).toFixed(num) + '%'
}
/**
 * @author: yzd
 * @date: 
 * @desc: 金额格式化
 * @lastEditor: 
 */
export const getAns = function(str) {

    if (!str) return ''
    const pattern = /(?=((?!\b)\d{3})+$)/g;
     return str.replace(pattern, ',');
    // str = Number.parseFloat(str)

    // return (str * 100).toFixed(num) + '%'
}

/**
 * 保障期，年险格式化  10_Y -> 10年
 */
// export const guaranteedValFormat = function(year) {

//     return 1

// }