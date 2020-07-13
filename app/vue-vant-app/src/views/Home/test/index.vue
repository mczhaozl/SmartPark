<template>
  <div class="cpicContainer">
    <pageHeader title="南昌高新停车" @back="$router.back(-1)"></pageHeader>
    <mainContainer top="1.333333" background="#f8f6f9">
      <cpic-title title="南昌高新停车"></cpic-title>
      <div class="warp">

     
      </div>
    </mainContainer>
    <pageFooter tabActive="2"></pageFooter>
  </div>
</template>

<script>
import echarts from "echarts";
export default {
  name: "Template",
  data() {
    return {
    };
  },
  components: {
    pageHeader: () => import("@/components/pageHeader/index"),
    pageFooter: () => import("@/components/pageFooter/index"),
    mainContainer: () => import("@/components/mainContainer/default"),
    // mainContainer: () => import("@/components/mainContainer/refresh")
    // mainContainer: () => import("@/components/mainContainer/refreshReload"),
  },
  computed: {},
  created() {},
  mounted() {
    
  },
  methods: {
    /**
     * 获取数据
     */
    init() {
      this.$store.dispatch("storeSetRefresh", false);
    },
  }
};
</script>

<style lang="scss">
.warp {
  @include commonWarp();
}
</style>