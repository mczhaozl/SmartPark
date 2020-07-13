<template>
  <van-pull-refresh
    v-model="refreshIng"
    @refresh="onRefresh"
    class="container"
    success-text="刷新成功"
    :style="{ 'top': top + 'rem', backgroundColor: background }"
  >
    <van-list v-model="isLoading" :finished="finished" finished-text="没有更多了" @load="onLoad">
      <slot></slot>
    </van-list>
  </van-pull-refresh>
</template>

<script>
export default {
  name: "Template",
  props: {
    top: {
      type: Number,
      default: 1.33333
    },
    finished: {
      type: Boolean,
      default: false
    },
    background: {
      type: String,
      default: "nuset"
    }
  },
  data() {
    return {
      // list: [],
      // finished: false
    };
  },
  computed: {
    refreshIng: {
      get() {
        return this.$store.state.common.refreshIng;
      },
      set() {
        this.$store.dispatch("storeSetRefresh", true);
      }
    },
    isLoading: {
      get() {
        return this.$store.state.common.isLoading;
      },
      set() {
        this.$store.dispatch("storeSetLoading", true);
      }
    }
  },
  created() {
    this.$store.dispatch("storeSetLoading", false);
  },
  mounted() {},
  methods: {
    onLoad() {
      // 父页面写法 请求后续页面
      // onLoad() {
      //   setTimeout(() => {
      //     for (let i = 0; i < 10; i++) {
      //       this.list.push(this.list.length + 1);
      //     }
      //     this.$store.dispatch("storeSetLoading", false);

      //     if (this.list.length >= 60) {
      //       this.finished = true;
      //     }
      //   }, 1000);
      // }

      this.$parent.onLoad();
    },
    onRefresh() {
      this.$store.dispatch("storeSetLoading", true);
      // 父页面写法：将结束状态置为否  在请求首页数据
      // init() {
      //   this.finished = false;
      //   setTimeout(() => {
      //     this.list = [];
      //     this.$store.dispatch("storeSetRefresh", false);
      //     for (let i = 0; i < 10; i++) {
      //       this.list.push(this.list.length + 1);
      //     }
      //     this.$store.dispatch('storeSetRefresh', false)
      //   }, 1000);
      // }

      this.$parent.init();
    }
  }
};
</script>

<style lang="scss">
.container {
  @include container();
  /deep/ .van-loading {
    color: $colorDark;
  }
  /deep/ .van-loading__text {
    color: $colorDark;
  }
}
</style>
