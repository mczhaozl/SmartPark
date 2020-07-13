<template>
    <div class="cpicContainer">
        <van-row>
            <van-col span="1">
                <router-link to="/index">
                    <van-icon name="arrow-left" size="0.5rem"
                              style="margin-top: 0.5rem;margin-right: -10px;"></van-icon>
                </router-link>
            </van-col>
            <van-col span="21">
                <form action="/">
                    <van-search
                            id="input-search"
                            v-model="searchValue"
                            placeholder="请输入搜索目的地"
                            @input="changeInput"
                            @search="onSearch">
                        <slot slot="right-icon" name="right-icon"><span @click="audioSearch"><van-icon
                                :name="icon"/></span></slot>
                    </van-search>
                </form>
            </van-col>
            <van-col span="2">
                <van-icon name="bars" size="0.5rem" style="margin-top: 0.5rem;"/>
            </van-col>
        </van-row>
        <div class="van-card">
            <van-grid :border="false">
                <van-grid-item :icon="nav.stopPay" text="停车缴费"/>
                <van-grid-item :icon="nav.bdCar" text="绑定车辆"/>
                <van-grid-item :icon="nav.djPay" text="临时代缴"/>
                <van-grid-item :icon="nav.eBill" text="电子票据"/>
            </van-grid>
        </div>
        <van-row class="van-card" style="width: 100%;padding-top: 0px;padding-bottom: 0px;">
            <van-notice-bar :scrollable="scrollable"  text="温馨提示：一千米范围内无最近路边停车场" left-icon="volume-o" />
        </van-row>
        <!--地图 -->
        <div class="van-card">
            <van-row>
                <div id="container" v-bind:style="{width:'auto',height: mapHeight }"></div>
            </van-row>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {NavBar, Divider} from 'vant';
    import {Search} from 'vant';
    import {Col, Row} from 'vant';
    import {Icon} from 'vant';
    import {Button} from 'vant';
    import {Grid, GridItem} from 'vant';
    import { NoticeBar } from 'vant';

    import audio from '@/assets/images/audio.png'
    import bdCar from '@/assets/images/bd-car.png'
    import djPay from '@/assets/images/dj-pay.png'
    import eBill from '@/assets/images/e-bill.png'
    import stopPay from '@/assets/images/stop-pay.png'

    Vue.use(Button);
    Vue.use(Icon);
    Vue.use(Col);
    Vue.use(Row);
    Vue.use(Search);
    Vue.use(NavBar);
    Vue.use(Divider);
    Vue.use(Grid);
    Vue.use(GridItem);
    Vue.use(NoticeBar);

    export default {
        name: "index",
        data() {
            return {
                map: {},
                searchValue: '',
                icon: audio,
                scrollable:true,
                nav: {
                    bdCar: bdCar,
                    djPay: djPay,
                    eBill: eBill,
                    stopPay: stopPay,
                },
                mapHeight: (document.body.clientHeight - 85) + 'px',
            }
        },
        mounted() {
            let that = this;
            that.map = new AMap.Map('container', {
                resizeEnable: true,
                zoomEnable: true,
                zoom: 13
            });
            that.map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                    enableHighAccuracy: true,//是否使用高精度定位，默认:true
                    timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                    maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                    convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                    showButton: true,        //显示定位按钮，默认：true
                    buttonPosition: 'LT',    //定位按钮停靠位置，默认：'LB'，左下角
                    buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                    showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                    showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                    panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                    zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                });
                that.map.addControl(geolocation);
                that.map.on('touchend', function (e) {
                    that.show = false;
                }, that);
                geolocation.getCurrentPosition();
                AMap.event.addListener(geolocation, 'complete', that.onComplete);//返回定位信息
                AMap.event.addListener(geolocation, 'error', that.onError);      //返回定位出错信息
            });
        },
        methods: {
            changeInput() {

            },
            onSearch() {

            },
            audioSearch() {

            },
            onComplete(){

            },
            onError(){

            }
        }
    }
</script>

<style scoped>
    .van-card:not(:first-child) {
        margin-top: 0;
    }
</style>