<template>
  <div class="pageFooter">
    <van-tabbar v-model="active" active-color="#0081F8" safe-area-inset-bottom>
      <van-tabbar-item replace to="/index" >
        <span>首页</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeHome : icon.inactiveHome" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item replace to="/service">
        <span>服务</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeService : icon.inactiveService" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item  replace to="/news">
        <span>资讯</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeNews : icon.inactiveNews" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item replace to="/me">
        <span>我的</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeMe : icon.inactiveMe" />
        </template>
      </van-tabbar-item>
    </van-tabbar>
    <router-view />
  </div>
</template>

<script>
  import Vue from 'vue';
  import { Tabbar, TabbarItem } from 'vant';
  import activeHome from '@/assets/images/home_selector.png';
  import inactiveHome from '@/assets/images/home.png';
  import activeService from '@/assets/images/service_selector.png';
  import inactiveService from '@/assets/images/service.png';
  import activeNews from '@/assets/images/news_selector.png';
  import inactiveNews from '@/assets/images/news.png';

  Vue.use(Tabbar);
  Vue.use(TabbarItem);
export default {
  name: "PageFooter",
  data() {
    return {
      active: 0,
      tabbar:['index','service/index','news/index','me/index'],
      icon: {
        activeHome: activeHome,
        inactiveHome: inactiveHome,
        activeService: activeService,
        inactiveService: inactiveService,
        activeNews: activeNews,
        inactiveNews: inactiveNews,
        activeMe: 'https://img.yzcdn.cn/vant/user-active.png',
        inactiveMe: 'https://img.yzcdn.cn/vant/user-inactive.png',
      },
    };
  },
  created() {
    // console.log(this.$store.getters.userInfo)
  },
  mounted() {
    this.tabbarActive();
  },
  watch: {
    //监听路由变化
    '$route' (to, from) {// 对路由变化作出响应
      this.tabbarActive();
    }
  },
  methods: {
    tabbarActive(){
      const href = window.location.href.split("#/")[1];

      for(let i=0;i<4;i++){
        if(href==this.tabbar[i]){
          this.active=i;
        }
      }
    },
  }
};
</script>
<style lang="scss" scoped>
</style>
