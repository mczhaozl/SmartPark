<template>
    <div class="cpicContainer">
        <van-nav-bar
                style="background-color: #3282F9"
                title="南昌高新交通"
                :left-arrow=false
        />
        <div class="van-card bg">
            <van-grid :border="false">
                <van-grid-item :icon="icon.navigation" text="找车位" to="/parkinglot"/>
                <van-grid-item :icon="icon.appointment" text="预约停车" to="/appointment"/>
                <van-grid-item :icon="icon.parking" text="道路停车" to="/roadside"/>
                <van-grid-item :icon="icon.pay" text="停车缴费" to="/pay"/>
            </van-grid>
        </div>
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
                                <van-col span="4"><img height="40px" style="border-radius:5px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】注意！7月起实施非机动车新规，这类行为将扣车+罚款！ </van-col>
                                <van-col span="4"><img height="40px" style="border-radius:5px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】彰显省会担当 加快赶超步伐 决战决胜攻坚——南昌市交通运输迎“国评”动员部署会召开</van-col>
                                <van-col span="4"><img height="40px" style="border-radius:5px" src="~@/assets/images/news-1.png"/></van-col>
                            </van-row>
                            <van-divider/>
                            <van-row>
                                <van-col span="20">【交通资讯】彰显省会担当 加快赶超步伐 决战决胜攻坚——南昌市交通运输迎“国评”动员部署会召开</van-col>
                                <van-col span="4"><img height="40px" style="border-radius:5px" src="~@/assets/images/news-1.png"/></van-col>
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

        <pageFooter tabActive="1"></pageFooter>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Grid, GridItem} from 'vant';
    import {PullRefresh} from 'vant';
    import {Tab, Tabs} from 'vant';
    import {Swipe, SwipeItem} from 'vant';
    import {Divider} from 'vant';
    import {NavBar} from 'vant';
    import {Col, Row} from 'vant';
    import {Lazyload} from 'vant';
    //导入图片
    import parking from '@/assets/images/road_parking.png'
    import appointment from '@/assets/images/appointment.png'
    import pay from '@/assets/images/pay.png'
    import navigation from '@/assets/images/search_parking.png'
    import banner from '@/assets/images/banner.png'
    import { List } from 'vant';

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
    Vue.use(PullRefresh);
    Vue.use(Grid);
    Vue.use(GridItem);
    export default {
        name: "Index",
        data() {
            return {
                value: '',
                active: '',
                list: [],
                loading: false,
                finished: false,
                activeNames: ['1'],
                icon: {
                    parking: parking,
                    appointment: appointment,
                    pay: pay,
                    navigation: navigation
                },
                images: [
                    banner,
                    banner,
                ]
            };
        },
        computed: {
            baseUrl() {
                // return process.env.VUE_APP_BASE_URL;
            }
        },
        components: {
            pageFooter: () => import("@/components/pageFooter/index"),
        },
        created() {
        },
        mounted() {
            let vm = this;
            vm.init();
        },
        methods: {
            /**
             * 获取数据
             */
            init() {
                console.log('init')
                this.finished = false;
                setTimeout(() => {
                    this.list = [];
                    this.$store.dispatch("storeSetLoading", false);
                    for (let i = 0; i < 10; i++) {
                        this.list.push(this.list.length + 1);
                    }
                    this.$store.dispatch('storeSetRefresh', false)
                }, 1000);
            },


            onLoad() {
                console.log('load')

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

    /deep/    .van-nav-bar__title {
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
