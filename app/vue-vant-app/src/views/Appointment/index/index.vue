<template>
    <div class="cpicContainer">
        <van-row>
            <van-col span="1">
                <router-link to="/index">
                    <van-icon name="arrow-left" size="0.5rem"
                              style="margin-top: 0.5rem;margin-right: -10px;"></van-icon>
                </router-link>
            </van-col>
            <van-col span="23">
                <form action="/">
                    <van-search
                            id="input-search"
                            v-model="searchValue"
                            placeholder="请输入停车场"
                            @search="onSearch">
                    </van-search>
                </form>
            </van-col>
        </van-row>
        <van-dropdown-menu>
            <van-dropdown-item v-model="distance" :options="distanceOptions" :overlay="overlay"/>
            <van-dropdown-item title="筛选" ref="item">
                <van-switch-cell v-model="num" title="空闲车位优先"/>
                <van-switch-cell v-model="price" title="价格优先"/>
                <van-button block type="info" @click="onConfirm">确认</van-button>
            </van-dropdown-item>
        </van-dropdown-menu>
        <van-list
                v-model="loading"
                :finished="finished"
                finished-text="没有更多了"
                @load="onLoad">
            <van-card v-for="item in list"
                      :key="item"
                      :price="item.price"
                      :desc="item.desc"
                      :title="item.title"
                      :thumb="item.thumb" @click="detail">
                <template #tags>
                    <van-tag plain type="success">微信支付</van-tag>
                    <van-tag plain type="success">支付宝支付</van-tag>
                    <van-tag plain type="success">现金支付</van-tag>
                </template>
            </van-card>
        </van-list>
    </div>
</template>

<script>
    import Vue from 'vue';
    import {Card, Tag, SwitchCell} from 'vant';
    import park from '@/assets/images/p1.jpg'
    import {List} from 'vant';

    Vue.use(List)
    Vue.use(Tag);
    Vue.use(Card);
    Vue.use(SwitchCell);
    export default {
        name: "index",
        components: {},
        data() {
            return {
                searchValue: '',
                distance: 0,
                price: false,
                num: false,
                overlay: false,
                loading: false,
                finished: false,
                list: [],
                park: park,
                distanceOptions: [
                    {text: '500m', value: 0},
                    {text: '1km', value: 1},
                    {text: '2km', value: 2},
                ],
            }
        },
        mounted() {
        },
        methods: {
            onSearch() {
            },
            onConfirm() {

            },
            detail(){
                this.$router.push('/appointment/detail')
            },
            onLoad() {
                let item = {
                    price: '5.00/小时',
                    desc: '总车位:1045,空闲车位:860',
                    title: '中节能停车场',
                    thumb: this.park
                };
                this.list.push(item);
                this.loading = false;
                this.finished = true;
            }
        }
    }
</script>

<style scoped>
    .cpicContainer{
        background-color:#ffffff;
    }
</style>