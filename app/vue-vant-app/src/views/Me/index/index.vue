<template>
    <div class="cpicContainer">
        <van-nav-bar
                style="background-color: #3282F9"
                title="我的"
                :left-arrow=false
        />
        <div class="van-card">
            <van-row style="background: #fff; padding:15px;">
                <van-col span="4">
                    <van-image round
                               width="2rem"
                               height="2rem" src="https://img.yzcdn.cn/vant/cat.jpeg"/>
                </van-col>
                <van-col span="20" style="padding-left: 1rem;padding-top: 0.3rem">
                    <van-row style="font-size:0.5rem;font-weight: bold;color: #646566" @click="login">{{userInfo.realname}}</van-row>
                    <van-row style="margin-top: 15px" @click="login">{{userInfo.phone}}</van-row>
                </van-col>
            </van-row>
        </div>
        <div class="van-card">
            <van-grid :border="false">
                <van-grid-item :icon="icon.car" text="我的车辆" @click="getCarList" />
                <van-grid-item :icon="icon.appointment" text="我的预约"/>
                <van-grid-item :icon="icon.wallet" text="我的钱包"/>
                <van-grid-item :icon="icon.record" text="停车记录"/>
            </van-grid>
        </div>
        <div class="van-card">
            <van-cell tabindex="0" title="我的订单" is-link to="index"/>
            <van-cell tabindex="0" title="常见问题" is-link to="index"/>
            <van-cell tabindex="0" title="意见反馈" is-link to="index"/>
            <van-cell tabindex="0" title="设置" is-link to="index"/>
        </div>
        <pageFooter></pageFooter>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Grid, GridItem} from 'vant';
    import {Col, Row} from 'vant';
    import {Image as VanImage} from 'vant';
    import {Cell, CellGroup} from 'vant';

    //导入图片
    import car from '@/assets/images/my-car.png'
    import appointment from '@/assets/images/my-appointment.png'
    import wallet from '@/assets/images/my-wallet.png'
    import record from '@/assets/images/my-record.png'
    import {Lazyload} from 'vant';

    Vue.use(VanImage);
    Vue.use(Grid);
    Vue.use(Lazyload);
    Vue.use(GridItem);
    Vue.use(Col);
    Vue.use(Row);
    Vue.use(Cell);
    Vue.use(CellGroup);

    export default {
        name: "Index",
        data() {
            return {
                loading: false,
                icon: {
                    car: car,
                    appointment: appointment,
                    wallet: wallet,
                    record: record
                },
                userInfo:{
                    realname:'未登录',
                    phone:''
                },
            };
           
        },
        computed: {},
        components: {
            pageFooter: () => import("@/components/pageFooter/index"),
        },
        created() {
            if(localStorage.getItem("isLogin")=="true"){
                this.userInfo.realname=localStorage.realname;
                this.userInfo.phone=localStorage.phone;
            }
        },
        mounted() {

        },
        methods: {
            login(){
                 if(localStorage.getItem("isLogin") !="true"){
                    this.$router.push("/login");
                 }
               
            },
            getCarList(){
                  if(localStorage.getItem("isLogin") !="true"){
                        this.$router.push("/login");
                  }else{
                        this.$router.push("/me/carList");
                  }
            }
        }
    };
</script>

<style lang="scss">
    .van-nav-bar__title {
        color: #ffffff !important;
        font-weight: bold !important;
    }

    .cpicContainer {
        background-color: #fafafa;
    }

    .van-card:not(:first-child) {
        margin-top: 0;
    }

    #app {
        background-color: #fafafa;
    }
</style>