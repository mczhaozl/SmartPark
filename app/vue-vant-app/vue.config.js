const path = require('path')

function resolve(dir) {
    return path.join(__dirname, './', dir)
}

module.exports = {
    // 项目部署的基础路径，该项配置默认为： "/"
    // 我们默认假设你的应用将会部署在域名的根部，
    // 比如 https://www.my-app.com/
    // 如果你的应用时部署在一个子路径下，那么你需要在这里
    // 指定子路径。比如，如果你的应用部署在
    // https://www.foobar.com/my-app/
    // 那么将这个值改为 `/my-app/`
    publicPath: process.env.NODE_ENV === 'production' ? './' : './',
    // 打包输出文件路径（文件名，默认 dist ）
    outputDir: 'dist',
    //打包后静态静态资源的放置位置，该项配置默认为 "" , 即直接将 ( js/css/img/fonts/...) 这些文件夹放在了 dist 文件夹下
    //这里将这些静态资源放置在 static 文件夹中
    assetsDir: "static",
    // 关闭 eslint
    lintOnSave: false,
    // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
    productionSourceMap: false,

    // alias 配置
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@assets', resolve('src/assets'))
        // 配置svg显示
        config.plugins.delete('preload') // TODO: need test
        config.plugins.delete('prefetch') // TODO: need test
        // set svg-sprite-loader
        config.module
        .rule('svg')
        .exclude.add(resolve('src/icons'))
        .end()
        config.module
        .rule('icons')
        .test(/\.svg$/)
        .include.add(resolve('src/icons'))
        .end()
        .use('svg-sprite-loader')
        .loader('svg-sprite-loader')
        .options({
            symbolId: 'icon-[name]'
        })
        .end()

        // set preserveWhitespace
        config.module
        .rule('vue')
        .use('vue-loader')
        .loader('vue-loader')
        .tap(options => {
            options.compilerOptions.preserveWhitespace = true
            return options
        })
        .end()
        // 配置全局公用sass
        const oneOfsMap = config.module.rule('scss').oneOfs.store
        oneOfsMap.forEach(item => {
        item
            .use('sass-resources-loader')
            .loader('sass-resources-loader')
            .options({
                // Provide path to the file with resources
                resources: './src/styles/variable.scss',

                // Or array of paths
                // resources: ['./path/to/vars.scss', './path/to/mixins.scss']
            })
            .end()
        })
    },

    // 开发服务器配置
    devServer: {
        proxy: {
            // 公共api接口
            '/api': {
                target: process.env.VUE_APP_BASE_URL, // 接口需要转发的地址
                pathRewrite: {
                    // "^/api": "/" // 重写path
                },
                changeOrigin: true // 配置请求头
            }
        }
    }
};


