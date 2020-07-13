<template>
    <div class="cpicContainer">
        <van-nav-bar
                title="中节能停车场"
                left-text=""
                left-arrow
                @click-left="onClickLeft"
        />
        <van-swipe :autoplay="3000" style="height: 160px;">
            <van-swipe-item v-for="(image, index) in images" :key="index">
                <img v-lazy="image" width="98%" height="100%"/>
            </van-swipe-item>
        </van-swipe>
        <van-panel title="中节能停车场" desc="总车位:1045  空闲车位:860" status="空闲">
            <div>
                <van-field
                        v-model="tips"
                        type="textarea"
                        name="温馨提示"
                        label="温馨提示"
                        readonly
                />
                <van-field
                        v-model="rule"
                        name="优惠政策"
                        label="优惠政策"
                        readonly
                />
                <van-divider content-position="left">收费标准</van-divider>
                <van-row>
                    <van-col style="text-align: center;" span="8">时间</van-col>
                    <van-col style="text-align: center;" span="8">车型</van-col>
                    <van-col style="text-align: center;" span="8">收费标准</van-col>
                </van-row>
                <van-divider :style="{padding: '0 16px' }"/>
                <van-row>
                    <van-col style="text-align: center;" span="8">8:00-19:00</van-col>
                    <van-col style="text-align: center;" span="8">小车</van-col>
                    <van-col style="text-align: center;" span="8">3元/小时</van-col>
                </van-row>
                <van-divider :style="{padding: '0 16px' }"/>
                <van-row>
                    <van-col style="text-align: center;" span="8">19:00-次日8:00</van-col>
                    <van-col style="text-align: center;" span="8">小车</van-col>
                    <van-col style="text-align: center;" span="8">2元/小时</van-col>
                </van-row>
                <div style="margin: 20px;">
                    <van-button round block type="info" to="/appointment/form">
                        立即预定
                    </van-button>
                </div>
            </div>
        </van-panel>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Lazyload, Panel,Divider} from 'vant';
    import {NavBar} from 'vant';
    import {Swipe, SwipeItem} from 'vant';
    import { Col, Row } from 'vant';
    import banner from '@/assets/images/p1.jpg'

    Vue.use(Col);
    Vue.use(Row);
    Vue.use(Divider);
    Vue.use(Lazyload);
    Vue.use(Swipe);
    Vue.use(SwipeItem);
    Vue.use(NavBar);
    Vue.use(Panel);
    export default {
        name: "detail",
        data() {
            return {
                tips:'昌东大道，艾溪湖公园旁，步行至艾溪湖公园3分钟',
                rule:'进场15分钟内免费',
                images: [
                    banner,
                    banner,
                ]
            }
        },
        methods: {
            onClickLeft() {
                this.$router.push("/appointment")
            },
        }
    }
</script>

<style scoped>
    /deep/ .van-cell__value {
        color: #4CCB65;
    }
    /deep/ .van-nav-bar__title{
        color: #3282F9;
    }
</style>