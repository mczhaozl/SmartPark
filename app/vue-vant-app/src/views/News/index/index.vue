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
                        <van-list
                                v-model="loading"
                                :finished="finished"
                                finished-text="没有更多了"
                                @load="onLoad"
                        >
                            <van-row>
                                <van-col span="20">【交通管制】2020年3月22日起至2021年5月26日高新区部分道路交通管制</van-col>
                                <van-col span="4"><img height="40px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】注意！7月起实施非机动车新规，这类行为将扣车+罚款！</van-col>
                                <van-col span="4"><img height="40px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】彰显省会担当 加快赶超步伐 决战决胜攻坚——南昌市交通运输迎“国评”动员部署会召开</van-col>
                                <van-col span="4"><img height="40px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】彰显省会担当 加快赶超步伐 决战决胜攻坚——南昌市交通运输迎“国评”动员部署会召开</van-col>
                                <van-col span="4"><img height="40px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                        </van-list>
                    </div>
                </van-tab>
                <van-tab title="出行提示">
                    <div style="background-color: #ffffff;line-height: 20px;padding-top: 10px;">
                        <van-list
                                v-model="loading"
                                :finished="finished"
                                finished-text="没有更多了"
                                @load="onLoad"
                        >
                            <van-row>
                                <van-col span="24">【出行提示】骑电动车要戴头盔？交警解答来了</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【出行提示】开学啦！近期交通出行提示：早晚高峰将提前！时段将延长！</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【必看】今日（5月21日）出行重要提示&明日（5月22日）交通预报</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【出行提示】一周交通出行提示</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【出行提示】南昌周边景区交通出行提示</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【出行提示】出行提醒！明天上午高新部分多路段将分时交通管控</van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="24">【出行提示】南昌交警发布5月25日出行提示，机动车不限号</van-col>
                            </van-row>
                            <van-divider/>
                        </van-list>
                    </div>
                </van-tab>
            </van-tabs>
        </div>
        <pageFooter></pageFooter>
    </div>
</template>

<script>
    import Vue from "vue";
    import {PullRefresh, Tab, Tabs, NavBar, Divider} from 'vant';
    import {Lazyload} from 'vant';
    import {Swipe, SwipeItem} from 'vant';
    import {List} from 'vant';
    import banner from '@/assets/images/banner.png'

    Vue.use(List);
    Vue.use(Lazyload);
    Vue.use(Divider);
    Vue.use(Tab);
    Vue.use(Tabs);
    Vue.use(Swipe);
    Vue.use(SwipeItem);
    Vue.use(NavBar);
    Vue.use(PullRefresh);
    export default {
        name: "Template",
        data() {
            return {
                show: false,
                src: '',
                active: '',
                list: [],
                loading: false,
                finished: false,
                index: 0,
                imgList: [],
                images: [
                    banner,
                    banner,
                ]
            };
        },
        components: {
            // pageHeader: () => import("@/components/pageHeader/index"),
            pageFooter: () => import("@/components/pageFooter/index"),
            // mainContainer: () => import("@/components/mainContainer/default"),
            // mainContainer: () => import("@/components/mainContainer/refresh"),
            // mainContainer: () => import("@/components/mainContainer/refreshReload"),
        },
        computed: {},
        created() {
        },
        mounted() {
            let vm = this;
            vm.init()
        },
        methods: {
            /**
             * 获取数据
             */
            init() {

            },
            onLoad() {
                console.log('load')

            },
            onChange(index) {
                this.index = index;
            }
        }
    };
</script>

<style lang="scss">
    .van-nav-bar__title {
        color: #ffffff;
        font-weight: bold;
    }

    /deep/ .van-image-preview__image {
        border: 1px solid #f00 !important;
    }

    /deep/ .van-image-preview__image img {
        border: 1px solid #f00;
        height: 100px;
    }
</style>
