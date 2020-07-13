<template>
  <div class="pageHeader">
    <div class="asidePart">
      <van-icon v-if="leftIcon" @click="back" name="arrow-left" />
    </div>
    <div class="center van-hairline--top-bottom">{{title}}</div>
    <div class="asidePart">
      <van-icon v-if="leftIcon" name="chat-o" info="9" />
      <van-icon v-if="leftIcon" name="shopping-cart-o" />
    </div>
  </div>
</template>

<script>
export default {
  name: "PageHeader",
  props: {
    title: {
      type: String,
      default: "这是标题"
    },
    leftIcon: {
      type: Boolean,
      default: true
    },
    rightIcon: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      userInfo: {},
      triangleShow: false,
      selectedShop: 0
    };
  },
  created() {
    // console.log(this.$store.getters.userInfo)
  },
  methods: {
    /**
     * 返回上一步
     */
    back() {
      const vm = this;
      vm.$emit("back");
    },
    goRightCross() {
      const vm = this;
    },
    menuChange() {
      console.log(this.selectedShop)
    }
  }
};
</script>
<style lang="scss" scoped>
.pageHeader {
  height: 50px;
  width: 100%;
  color: $colorWhite;
  @include displayFlex();
  line-height: 50px;
  .asidePart { 
    padding: 16px 10px 0;
    height: 50px;
    width: 80px;
    font-size: 20px;
    display: flex;
    justify-content: space-between; position: relative;
  }
  .center {
    flex: 1;
    text-align: center;
    font-size: $default;
  }
}

.triangle{ position: absolute; bottom: 0; right: 5px; z-index: 2;
	   width:0;
        height:0;
	border-right:10px solid transparent;
	border-left:10px solid transparent;
	border-bottom:10px solid #fff;
}

// 下拉菜单
.dropdownMenu{
  position: absolute; width: 30px; overflow: hidden;
  top:-0px; right:5px;
  /deep/ .van-dropdown-menu__title{ z-index: 11; opacity: 0;}
  /deep/ .van-dropdown-menu{ background-color:unset; border: 0;}
}

// 去除自带边框
/deep/ .van-hairline--top-bottom::after{ border: 0;}
/deep/ .van-hairline-unset--top-bottom::after {border: 0;}
</style>
