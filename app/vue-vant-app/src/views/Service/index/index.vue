<template>
    <div class="cpicContainer">
        <van-nav-bar
                style="background-color: #3282F9"
                title="服务"
                :left-arrow=false
        />
        <van-grid>
            <van-grid-item :icon="icon.bus" text="实时公交"/>
            <van-grid-item :icon="icon.traffic" text="实时路况"/>
            <van-grid-item :icon="icon.card" text="一卡通服务"/>
            <van-grid-item :icon="icon.gas" text="加油站"/>
            <van-grid-item :icon="icon.phone" text="交通热线"/>
            <van-grid-item :icon="icon.other" text="更多"/>
        </van-grid>
        <pageFooter></pageFooter>
    </div>

</template>

<script>
    import {Grid, GridItem} from 'vant';
    import Vue from "vue";
    //导入图片
    import bus from '@/assets/images/bus.png'
    import traffic from '@/assets/images/traffic.png'
    import card from '@/assets/images/card.png'
    import gas from '@/assets/images/gas.png'
    import phone from '@/assets/images/phone.png'
    import other from '@/assets/images/other.png'

    Vue.use(Grid);
    Vue.use(GridItem);
    export default {
        name: "Template",
        data() {
            return {
              icon: {
                  bus: bus,
                  traffic: traffic,
                  card: card,
                  gas: gas,
                  phone:phone,
                  other:other
              },
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

            }

        }
    };
</script>

<style lang="scss">
  .van-nav-bar__title {
    color: #ffffff;
    font-weight: bold;
  }
</style>
