<template>
    <div class="cpicContainer">
        <van-nav-bar
                title="停车缴费"
                left-text=""
                :fixed="fixed"
                left-arrow
                @click-left="onClickLeft"
        />
        <van-tabs v-model="active" swipeable animated color="#3282F9" title-active-color="#3282F9" style="margin-top: 40px">
            <van-tab title="全部">
                <van-empty description="暂无订单"/>
            </van-tab>
            <van-tab title="待支付">
                <van-empty description="暂无待支付订单"/>
            </van-tab>
            <van-tab title="已支付">
                <van-empty description="暂无已支付订单"/>
            </van-tab>
        </van-tabs>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Card, Tag} from 'vant';
    import {List} from 'vant';
    import { NavBar } from 'vant';
    import { Tab, Tabs } from 'vant';
    import { Empty } from 'vant';

    Vue.use(Empty);
    Vue.use(Tab);
    Vue.use(Tabs);
    Vue.use(NavBar);
    Vue.use(List)
    Vue.use(Tag);
    Vue.use(Card);
    export default {
        name: "index",
        data(){
            return {
                fixed:true,
                active:true
            }
        },
        mounted() {
        },
        methods:{
            onClickLeft(){
                this.$router.push("/index")
            }
        }
    }
</script>

<style scoped>
     .van-nav-bar__title{
        color: #3282F9 !important;
    }
</style>