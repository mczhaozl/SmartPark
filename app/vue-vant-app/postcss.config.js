module.exports = {
    "plugins": {
        "postcss-import": {},
        "postcss-url": {},
        // to edit target browsers: use "browserslist" field in package.json
        "autoprefixer": {
            overrideBrowserslist: [
                "Android 4.1",
                "iOS 7.1",
                "Chrome > 31",
                "ff > 31",
                "ie >= 8"
            ]
        },
        "postcss-pxtorem": {
            rootValue: 37.5,
            propList: ['*']
                /**
                 * 当375的设计稿，如果设计稿是750的话,计算÷2
                 * ①比如设计稿20px,页面上写10px
                 * ②可以模仿原项目375的尺寸，直接写对应px 
                 * */
        }
    }
};