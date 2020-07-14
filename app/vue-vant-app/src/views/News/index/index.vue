<template>
    <div class="cpicContainer">
        <van-nav-bar
                style="background-color: #3282F9"
                title="资讯"
                :left-arrow=false
        />
        <div class="van-card">
            <van-tabs v-model="active" color="#3282F9" swipeable title-active-color="#3282F9">
                <van-tab title="交通资讯">
                    <van-swipe :autoplay="3000" style="height: 145px;">
                        <van-swipe-item v-for="(image, index) in images" :key="index">
                            <img v-lazy="image" width="100%" height="100%"/>
                        </van-swipe-item>
                    </van-swipe>
                    <div style="background-color: #ffffff; line-height: 20px;padding-top: 10px; overflow-y:auto;">
                        <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
                            <van-list
                                    v-if="!isEmpty"
                                    v-model="loading"
                                    :finished="finished"
                                    finished-text="没有更多了"
                                    @load="onLoad"
                            >
                                <template #pulling="props">
                                    <img
                                            class="doge"
                                            src="https://img.yzcdn.cn/vant/doge.png"
                                            :style="{ transform: `scale(${props.distance / 80})` }"
                                    />
                                </template>
                                <!-- 释放提示 -->
                                <template #loosing>
                                    <img class="doge" src="https://img.yzcdn.cn/vant/doge.png"/>
                                </template>
                                <div v-for="(item,index) in trafficList" :key="index" @click="onClickTraffic(item)">
                                    <van-row>
                                        <van-col span="18">【{{item.type}}】{{item.title}}</van-col>
                                        <van-col span="6"><img style="height: 55px;width: 100%"
                                                               :src="getAvatarView(item.thumbnail)"/></van-col>
                                    </van-row>
                                    <van-divider style="  margin-top:5px !important; margin-bottom:5px !important;"/>
                                </div>
                            </van-list>
                            <van-empty v-else description="暂无" style="background-color: #ffffff"/>
                        </van-pull-refresh>
                    </div>
                </van-tab>
                <van-tab title="出行提示">
                    <div style="background-color: #ffffff;line-height: 20px;padding-top: 10px;">
                        <van-pull-refresh v-model="tRefreshing" @refresh="ontRefresh">
                            <van-list
                                    v-if="!isTEmpty"
                                    v-model="tloading"
                                    :finished="tfinished"
                                    finished-text="没有更多了"
                                    @load="ontLoad"
                            >
                                <div v-for="(item,index) in travelList" :key="index" @click="onClickTravel(item)">
                                    <van-row>
                                        <van-col span="24">【{{item.type}}】{{item.title}}</van-col>
                                    </van-row>
                                    <van-divider/>
                                </div>
                            </van-list>
                            <van-empty v-else description="暂无" style="background-color: #ffffff"/>
                        </van-pull-refresh>
                    </div>
                </van-tab>
            </van-tabs>
        </div>
        <pageFooter></pageFooter>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {getAction, getFileAccessHttpUrl} from '@/api/manage';
    import {Grid, GridItem, List} from 'vant';
    import {PullRefresh, Empty} from 'vant';
    import {Tab, Tabs} from 'vant';
    import {Swipe, SwipeItem} from 'vant';
    import {Divider} from 'vant';
    import {NavBar} from 'vant';
    import {Col, Row} from 'vant';
    import {Lazyload} from 'vant';
    import banner from '@/assets/images/banner.png'

    Vue.use(List);
    Vue.use(Lazyload);
    Vue.use(Col);
    Vue.use(Row);
    Vue.use(NavBar);
    Vue.use(Divider);
    Vue.use(Swipe);
    Vue.use(SwipeItem);
    Vue.use(Tab);
    Vue.use(Tabs);
    Vue.use(Empty);
    Vue.use(PullRefresh);
    Vue.use(Grid);
    Vue.use(GridItem);
    export default {
        name: "Index",
        data() {
            return {
                isEmpty: false,
                pageNo: 1,
                pageSize: 5,
                refreshing: false,
                active: '',
                trafficList: [],
                loading: false,
                finished: false,
                isTEmpty: false,
                pageTNo: 1,
                pageTSize: 5,
                tRefreshing: false,
                travelList: [],
                tloading: false,
                tfinished: false,
                activeNames: ['1'],
                images: [
                    banner,
                    banner,
                ]
            };
        },
        computed: {},
        components: {
            pageFooter: () => import("@/components/pageFooter/index"),
        },
        created() {
        },
        mounted() {

        },
        methods: {
            getAvatarView: function (avatar) {
                return getFileAccessHttpUrl(avatar)
            },
            onLoad() {
                let that = this
                getAction(window._CONFIG['domianURL'] + '/parking/pkTrafficInfo/list', {
                        'pageNo': that.pageNo,
                        'pageSize': that.pageSize
                    }
                ).then(data => {
                    // 加载状态结束
                    that.loading = false;
                    if (data.result.records != null && data.result.records.length > 0) {
                        for (let i in data.result.records) {
                            that.trafficList.push(data.result.records[i])
                        }
                        that.pageNo++
                    } else {
                        that.finished = true
                        if (that.pageNo == 1) {
                            that.isEmpty = true
                        }
                    }
                })

            },
            onClickTraffic(item){
                this.$router.push({
                    name: 'HomeDeatil',
                    params: {
                        item: item
                    }
                })
            },
            ontLoad() {
                let that = this
                getAction(window._CONFIG['domianURL'] + '/parking/pkTravelTip/list', {
                        'pageNo': that.pageTNo,
                        'pageSize': that.pageTSize
                    }
                ).then(data => {
                    // 加载状态结束
                    that.tloading = false;
                    if (data.result.records != null && data.result.records.length > 0) {
                        for (let i in data.result.records) {
                            that.travelList.push(data.result.records[i])
                        }
                        that.pageTNo++
                    } else {
                        that.tfinished = true
                        if (that.pageTNo == 1) {
                            that.isTEmpty = true
                        }
                    }
                })
            },
            onClickTravel(item){
                this.$router.push({
                    name: 'HomeDeatil',
                    params: {
                        item: item
                    }
                })
            },
            onRefresh() {
                // 清空列表数据
                this.finished = false
                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.isEmpty = false
                this.loading = true
                this.trafficList = []
                this.refreshing = false
                this.pageNo = 1
                this.onLoad()
            },
            ontRefresh() {
                // 清空列表数据
                this.tfinished = false
                // 重新加载数据
                // 将 loading 设置为 true，表示处于加载状态
                this.isTEmpty = false
                this.tloading = true
                this.travelList = []
                this.tRefreshing = false
                this.pageTNo = 1
                this.ontLoad()
            }
        }
    };
</script>
<style lang="scss" scoped>
    .my-swipe .van-swipe-item {
        color: #fff;
        font-size: 20px;
        line-height: 150px;
        text-align: center;
        background-color: #39a9ed;
    }

    .center {
        flex: 1;
        text-align: center;
        margin-top: 25px;
        color: #ffffff;
        font-weight: bold;
        font-size: $default;
    }

    .nav {
        background-color: #005aa9 !important;
    }

    /deep/ .van-nav-bar__title {
        color: #ffffff !important;
        font-weight: bold !important;
    }

    .van-card:not(:first-child) {
        margin-top: 0;
    }

    .van-card.van-tabs > .van-tabs__wrap > .van-tabs__nav > .van-tabs__line > .van-tab--active {
        color: #5eb95e !important;
    }

    /*   /deep/ .van-grid {
           background: url("/assets/images/bg.png") no-repeat;
           background-size: 100% 100%;
       }*/

    // 去除自带边框
    /deep/ .van-hairline--top-bottom::after {
        border: 0;
    }

</style>