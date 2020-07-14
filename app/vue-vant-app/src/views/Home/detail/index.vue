<template>
    <div class="cpicContainer">
        <van-nav-bar fixed="true" class="nav_title" :title="model.title" left-arrow   @click-left="onClickLeft"
                     @click-right="onClickRight">
            <template #right>
                <van-icon name="ellipsis" size="18" />
            </template>
        </van-nav-bar>
        <van-share-sheet
                v-model="showShare"
                title="立即分享给好友"
                :options="options"
                @select="onSelect"
        />
        <div style="padding: 15px; margin-top: 50px">
            <van-row><div class="title">{{model.title}}</div></van-row>
            <van-row> <div class="date"><span style="color: #005aa9">{{model.createTime}}</span><span style="margin-left: 5px">{{model.author}}</span></div>
            </van-row>
            <van-row> <van-col span="24"><div class="content" v-html="model.content"></div></van-col></van-row>
        </div>
    </div>
</template>

<script>
    import Vue from 'vue';
    import { NavBar } from 'vant';
    import {Col, Row} from 'vant';
    import { ShareSheet } from 'vant';

    Vue.use(ShareSheet);
    Vue.use(NavBar);
    Vue.use(Col);
    Vue.use(Row);
    export default {
        name: "index",
        data(){
            return {
                model:{},
                showShare:false,
                width:'',
                options: [
                    { name: '微信', icon: 'wechat' },
                    { name: '微博', icon: 'weibo' },
                    { name: '复制链接', icon: 'link' },
                    { name: '分享海报', icon: 'poster' },
                    { name: '二维码', icon: 'qrcode' },
                ],
            }
        },
        mounted() {
           this.model = this.$route.params.item;
           this.width = window.screen.width;
        },
        methods:{
            onClickLeft(){
                this.$router.push('/index');
            },
            onClickRight(){
                this.showShare = true;
            },
            onSelect(option) {
                this.showShare = false;
            },
        }
    }
</script>

<style scoped>
    .cpicContainer{
        background-color: #ffffff;
        overflow-x:hidden;
    }
    .title{
        line-height: 35px;
        font-size: 20px;
        font-weight: bold;
    }
    /deep/.nav_title .van-nav-bar__title{
        color: #3282F9 !important;
    }
    .date{
        margin: 10px;
        font-size: 14px;
    }

</style>