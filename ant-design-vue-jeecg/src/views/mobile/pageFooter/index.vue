<template>
  <div class="pageFooter">
    <van-tabbar v-model="active" active-color="#0081F8" safe-area-inset-bottom badge="3">
      <van-tabbar-item replace to="/mobile" v-if="num==0">
        <span>消息</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeHome : icon.inactiveHome" />
        </template>
      </van-tabbar-item>
      <van-tabbar-item replace to="/mobile" v-else :badge="num">
        <span>消息</span>
        <template #icon="props">
          <img :src="props.active ? icon.activeHome : icon.inactiveHome" />
        </template>
      </van-tabbar-item>

      <van-tabbar-item  replace to="/appointment">
        <span>会议预约</span>
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
  import activeNews from '@/assets/images/appointment_selector.png';
  import inactiveNews from '@/assets/images/appointment.png';
  import { getAction,putAction } from '@/api/manage'
  Vue.use(Tabbar);
  Vue.use(TabbarItem);
export default {
  name: "PageHeader",
  data() {
    return {
      active: 0,
      num: 0,
      tabbar:['mobile','appointment','me'],
      icon: {
        activeHome: activeHome,
        inactiveHome: inactiveHome,
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
    this.loadData ();
  },
  watch: {
    //监听路由变化
    '$route' (to, from) {// 对路由变化作出响应
      this.tabbarActive();
    }
  },
  methods: {
    tabbarActive(){
      const href = window.location.href.split("/")[3];
      for(let i=0;i<3;i++){
        if(href==this.tabbar[i]){
          this.active=i;
        }
      }
    },
    loadData (){
      try {
        // 获取系统消息
        getAction("/sys/annountCement/listByUser").then((res) => {
          if (res.success) {
            this.num = res.result.sysMsgTotal;
          }
        }).catch(error => {
          console.log("系统消息通知异常",error);//这行打印permissionName is undefined
        });
      } catch (err) {
        console.log("通知异常",err);
      }
    },
  }
};
</script>
<style lang="scss" scoped>
</style>
