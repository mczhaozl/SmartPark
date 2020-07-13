/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
    const valid_map = ['admin', 'editor']
    return valid_map.indexOf(str.trim()) >= 0
}

/**
 * @param {string} url
 * @returns {Boolean}
 */
export function validURL(url) {
    const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
    return reg.test(url)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validLowerCase(str) {
    const reg = /^[a-z]+$/
    return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUpperCase(str) {
    const reg = /^[A-Z]+$/
    return reg.test(str)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validAlphabets(str) {
    const reg = /^[A-Za-z]+$/
    return reg.test(str)
}

/**
 * @param {string} email
 * @returns {Boolean}
 */
export function validEmail(email) {
    const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return reg.test(email)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function isString(str) {
    if (typeof str === 'string' || str instanceof String) {
        return true
    }
    return false
}

/**
 * @param {Array} arg
 * @returns {Boolean}
 */
export function isArray(arg) {
    if (typeof Array.isArray === 'undefined') {
        return Object.prototype.toString.call(arg) === '[object Array]'
    }
    return Array.isArray(arg)
}

/**
 * @author: wangyuhong
 * @action: 验证手机号是否符合规划
 */
export function checkPhone(value) {
    return /^1\d{10}$/.test(value)
}
/**
 * 验证邮箱
 * @author: wfyuan 2020-02-07
 * @action: boolean
 */
export function checkMail(mail) {
    return /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+(\.([a-zA-Z]{2,4}))+$/.test(mail)
}

/**
 * 验证用户姓名
 * 仅是中文或者仅是英文，不能混合，英文不少于4个字符，中文不少于2个字符
 * @author wfyuan 2020-3-2
 */
export function checkName(name) {
  let en = /[A-Za-z]+/
  let ch = /[\u4e00-\u9fa5]+/
  let en_res = name.search(en)
  let ch_res = name.search(ch)
  if (en_res>=0 && ch_res>=0) {
    // 既有中文又有英文  
    return false 
  } else if (en_res == -1 && ch_res>=0 && (name.length < 2 || name.length > 15)) {
    // 中文长度不小于2  
    return false 
  } else if (en_res >= 0 && ch_res == -1 && (name.length < 4 || name.length > 30)) {
    return false 
  } else {
      return true
  }
}
/**
 * 验证身份证号
 * @author: wfyuan 2020-02-07
 * @action: boolean
 */
export function checkIDNumber($idNo) {
    const reg = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
    //var reg =/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i ;
    if (!reg.test($idNo)) {
        return false;
    } else {
        //18位身份证需要验证最后一位校验位
        if ($idNo.length == 18) {
            $idNo = $idNo.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            const parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            let sum = 0;
            let ai = 0;
            let wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = $idNo[i];
                wi = factor[i];
                sum += ai * wi;
            }
            const last = parity[sum % 11];
            if (parity[sum % 11] != $idNo[17]) {
                return false;
            }
        }
        return true;
    }
    return true;
}

/**
 * 验证银行卡号
 * @author: wangyuhong 2020-03-03
 * @action: boolean
 */
export function checkBankNumber(number) {
    return /^[0-9]{15,20}$/.test(number);
}

/** 
 * 验证验证码
 * 6位数字
*/
export function checkCode(number) {
    return /^[0-9]{6}$/.test(number);
}