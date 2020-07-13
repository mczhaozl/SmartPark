<template>
  <van-pull-refresh
    v-model="refreshIng"
    @refresh="onRefresh"
    class="container"
    success-text="刷新成功"
    :style="{ 'top': top + 'rem', backgroundColor: background }"
  >
    <slot></slot>
  </van-pull-refresh>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "Template",
  props: {
    top: {
      type: Number,
      default: 1.3333333
    },
    background: {
      type: String,
      default: "nuset"
    }
  },
  watch: {},
  data() {
    return {};
  },
  created() {},
  computed: {
    refreshIng: {
      get() {
        return this.$store.state.common.refreshIng;
      },
      set() {
        this.$store.dispatch("storeSetRefresh", true);
      }
    }
  },
  methods: {
    onRefresh() {
      let vm = this;
      // 调用父组件 init 方法 父组件写法如下：
      // init() {
      //   setTimeout(() => {
      //     this.$store.dispatch('storeSetRefresh', false)
      //   }, 1000);
      // }
      vm.$parent.init();
    }
  }
};
</script>

<style lang="scss">
.container {
  @include container();
}
</style>
