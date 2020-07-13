<template>
  <div ref="wrapper" class="wrapper">
    <div class="list">
      <!-- 下拉刷新信息 -->
      <loading class="refresh-dom" v-show="isPulldown"></loading>
      <slot name="default">
      </slot>
      <!-- 上拉加载信息 -->
      <loading class="load-dom" v-show="isPullup"></loading>
      <!-- 无数据 -->
      <div v-if="noData" class="no-data">没有相关数据...</div>
      <div v-if="finished && !noData" class="no-data">我是有底线的...</div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
import BScroll from "better-scroll";

export default {
  props: {
    probeType: {
      type: Number,
      default: 1
    },
    click: {
      type: Boolean,
      default: true
    },
    listenScroll: {
      type: Boolean,
      default: false
    },
    data: {
      type: [Array, Number],
      default: null
    },
    // 支持下拉
    pulldown: {
      type: Boolean,
      default: false
    },
    // 支持上拉
    pullup: {
      type: Boolean,
      default: false
    },
    beforeScroll: {
      type: Boolean,
      default: false
    },
    refreshDelay: {
      type: Number,
      default: 20
    },
    // 是否已加载完全部数据
    finished: {
      type: Boolean,
      default: false
    },
    noData: {
      type: Boolean,
      default: false
    },
    // 下拉刷新处理 async 函数
    refreshHandle: {
      type: [Function, undefined],
      default: undefined
    },
    // 上拉加载处理 async 函数
    loadHandle: {
      type: [Function, undefined],
      default: undefined
    }
  },
  data() {
    return {
      isPulldown: false, // 下拉刷新
      isPullup: false // 上拉加载
    };
  },
  components: {
    Loading: () => import("@/components/loading/index")
  },
  mounted() {
    setTimeout(() => {
      this._initScroll();
    }, 20);
  },
  methods: {
    //下拉刷新
    onRefresh() {
      this.refreshHandle &&
        this.refreshHandle().finally(() => {
          console.log("刷新结束");
          this.isPulldown = false;
        });
    },
    //上拉加载
    onLoad() {
      this.loadHandle &&
        this.loadHandle().finally(() => {
          console.log("加载结束");
          this.isPullup = false;
        });
    },
    _initScroll() {
      if (!this.$refs.wrapper) {
        return;
      }
      this.scroll = new BScroll(this.$refs.wrapper, {
        probeType: this.probeType,
        click: this.click
      });

      // 滚动监听
      if (this.listenScroll) {
        // console.log(this.listenScroll)
        let me = this;
        me.scroll.on("scroll", pos => {
          if (this.pulldown && pos.y > 40) {
            // 下拉中
            me.isPulldown = true;
          }
          if (this.pullup && pos.y < -45) {
            // 上拉中
            me.isPullup = true;
          }
          me.$emit("scroll", pos);
        });
      }

      this.scroll.on("touchEnd", pos => {
        // console.log(pos);
        if (this.pulldown && pos.y > 40) {
          //下拉刷新,加载第一页
          console.log("下拉刷新");
          this.onRefresh();
          // this.$emit("onRefresh");
        }

        if (this.pullup && this.scroll.y <= this.scroll.maxScrollY + 50) {
          console.log("加载更多");
          this.onLoad();
          // this.$emit("onLoad");
        }
      });

      if (this.beforeScroll) {
        this.scroll.on("beforeScrollStart", () => {
          this.$emit("beforeScroll");
        });
      }
    },
    disable() {
      this.scroll && this.scroll.disable();
    },
    enable() {
      this.scroll && this.scroll.enable();
    },
    refresh() {
      this.scroll && this.scroll.refresh();
    },
    scrollTo() {
      this.scroll && this.scroll.scrollTo.apply(this.scroll, arguments);
    },
    scrollToElement() {
      this.scroll && this.scroll.scrollToElement.apply(this.scroll, arguments);
    }
  },
  watch: {
    data() {
      setTimeout(() => {
        this.refresh();
      }, this.refreshDelay);
    }
  }
};
</script>

<style lang="scss" scoped>
.list {
  position: relative;
  .refresh-dom {
    position: absolute;
    top: -40px;
  }
  .load-dom {
    position: absolute;
    bottom: -40px;
  }
  .no-data {
    height: 40px;
    line-height: 40px;
    width: 100%;
    text-align: center;
    font-size: 14px;
    color: #ccc;
  }
}
.top-tip {
  width: 100%;
  height: 40px;
  line-height: 40px;
  text-align: center;

  span {
    font-size: 0.2rem;
  }
}
</style>
