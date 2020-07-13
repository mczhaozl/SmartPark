<template>
  <div class="cpicCell" :class="{'van-hairline--bottom': border}" :style="{'paddingTop': vertical + 'rem', 'paddingBottom': vertical + 'rem'}">
    <div v-if="title" class="cellTitle" :style="{'width': width + 'rem'}">{{title}}</div>
    <div class="text" :style="{'textAlign': align}">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "Template",
  props: {
    title: {
      type: String,
      default: ""
    },
    width: { // 左侧标题宽度默认四个字80px
      type: Number,
      default: 2
    },
    align: {
      type: String,
      default: 'left'
    },
    vertical: {
      type: Number,
      default: 0.2666667 // rem默认大小37.5，，，写在页面中的时候要用rem
    },
    border: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {};
  }
};
</script>

<style lang="scss">
.cpicCell {
  @include displayFlex(top); font-size: $textSm; padding: 10px 0; line-height: 24px; 
  .cellTitle {
    color: $colorDefault;
  }
  .text {
    flex: 1;
    color: $colorDark;
  }
}
</style>
