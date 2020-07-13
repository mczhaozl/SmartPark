/**
 * 去字符串两头空字符串
 * @author wfyuan 2020-02-07
 */
export function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 去除字符串全部空格
 * @author wfyuan 2020-02-07
 */
export function trimAll(str) {
    return str.replace(/\s+/g, "");
}

/**
 * 设别判断
 * 已经作为全局混入使用
 * @author wfyuan 2020-02-07
 * @returns string
 */
export function getDevice() {
    const u = navigator.userAgent;
    // console.log(u)
    let device = ''
    if ((u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) && u.indexOf('Safari') === -1) {
        // console.log('android')
        device = 'android'
    } else if (u.indexOf('iPhone') > -1 && u.indexOf('Safari') === -1) {
        // console.log('ios', 11111)
        device = 'ios'
    } else if (u.indexOf('MicroMessenger') > -1) {
        // console.log('wechat')
        device = 'wechat'
    } else {
        // console.log('web',2222)
        device = 'web'
    }
    return device
}

/**
 * @author: hongwenqing
 * @date:
 * @desc: 深拷贝
 * @lastEditor:
 */
export function deepClone(obj, toemptystr = false) {
    let copy;

    // handle null undefined to empty string
    if (toemptystr && (obj === null || obj === undefined)) return '';

    // Handle the 3 simple types, and null or undefined
    if (null == obj || "object" != typeof obj) return obj;

    // Handle Date
    if (obj instanceof Date) {
        copy = new Date();
        copy.setTime(obj.getTime());
        return copy;
    }

    // Handle Array
    if (obj instanceof Array) {
        copy = [];
        for (var i = 0, len = obj.length; i < len; i++) {
            copy[i] = deepClone(obj[i], toemptystr);
        }
        return copy;
    }

    // Handle Function
    if (obj instanceof Function) {
        copy = function() {
            return obj.apply(this, arguments);
        }
        return copy;
    }

    // Handle Object
    if (obj instanceof Object) {
        copy = {};
        for (var attr in obj) {
            if (obj.hasOwnProperty(attr)) copy[attr] = deepClone(obj[attr], toemptystr);
        }
        return copy;
    }

    throw new Error("Unable to copy obj as type isn't supported " + obj.constructor.name);
}

/**
 * @author: hongwenqing
 * @date:
 * @desc: 数据类型判断
 * @lastEditor:
 */
export function getType(_val) {
    let s = Object.prototype.toString.call(_val)
    return s.slice(s.indexOf(' ') + 1, s.length - 1)

    // eg: getType({})  Object
}


/**
 * @author: hongwenqing
 * @date:
 * origin:  数据源
 * @desc: 处理复杂数据结构中  null undefined 类型值为空字符串 ''
 * @lastEditor:
 */

export function toEmptyStr(origin) {
    return deepClone(origin, true)
}
/**
 * 将文件转化为base64
 * @author wfyuan 2020-02-27
 * @param {*} file 待转换的文件
 * @returns base64文件流
 */
export function fileToBase64(file) {
    var reader = new FileReader();
    reader.readAsDataURL(oFiles[0]);
    reader.onload = function(e) {
        return this.result;
    }
}
/**
 * 将base64转换为文件
 * @param {*} dataurl base64数据
 * @param {*} filename 文件名（示例:img.jpg）
 */
export function convertBase64UrlToFile(dataurl, filename) {
    var arr = dataurl.split(','),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
    while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
}

/**
 * 图片上传压缩  压缩至尺寸: 383*512
 * @param {*} params 参数对象 
 * params.img 被压缩的图片对象（示例：<img src="1.png"/>） 
 * params.width 压缩后显示的宽度（默认414）
 * @param {*} callback 
 */

export function utilsCompressImg(params, callback) {
    params.img.onload = () => {
        let limitWidth = params.width ? params.width : 414;
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = params.img.width > limitWidth ? limitWidth : params.img.width;
        canvas.height = params.img.width > limitWidth ? parseInt((params.img.height * limitWidth) / params.img.width) : params.img.height;
        ctx.drawImage(params.img, 0, 0, canvas.width, canvas.height);
        callback && callback(canvas.toDataURL('image/png'));
    }
}


/**
 * 创建水印图片
 * @param {*} string 
 */
export function utilsCreateMarkImg(params, callback) {
    params.img.onload = () => {
        let limitWidth = params.width ? params.width : 414;
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = params.img.width > limitWidth ? limitWidth : params.img.width;
        canvas.height = params.img.width > limitWidth ? parseInt((params.img.height * limitWidth) / params.img.width) : params.img.height;
        ctx.drawImage(params.img, 0, 0, canvas.width, canvas.height);
        for(let i = 0; i < 20; i++){
            ctx.font = "16px 微软雅黑";
            ctx.rotate(-45 * Math.PI / 180);
            ctx.textAlign = 'center';
            ctx.textBaseline = 'Middle';
            ctx.fillText('我是水印', -260, i*50);
            ctx.fillText('我是水印', -120, i*50);
            ctx.fillText('我是水印', 20, i*50);
            ctx.fillText('我是水印', 160, i*50);
            ctx.fillText('我是水印', 300, i*50);
            ctx.rotate(45 * Math.PI / 180);
        }
        callback && callback(canvas.toDataURL('image/png'));
    }
}

/**
 * 添加水印图片
 * @param {*} string 
 */
export function utilsAddMarkImg(params, callback) {
    params.img.onload = () => {
        
    }
}

/**
 * 字符串千分位格式化
 * @export
 * @param {*} string 要格式化的字符串
 * @returns 
 */
export function commafy(string) {
    return string ? string.toString().replace(/(\d{1,3})(?=(\d{3})+(?:$|\.))/g, "$1,") : '';
}

/**
 * 千分位字符串还原
 * @export
 * @param {*} num
 * @returns
 */
export function delcommafy(num) {
    //去除千分位中的‘，’
    if (num && num != "undefined" && num != "null") {
        let numS = num;
        numS = numS.toString();
        numS = numS.replace(/,/gi, "");
        return numS;
    } else {
        return num;
    }
}