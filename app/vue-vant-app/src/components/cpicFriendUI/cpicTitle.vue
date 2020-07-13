<template>
  <div class="commonTitle am-padding-vertical-sm">
    <div class="line"></div>
    <div class="text">{{title}}</div>
  </div>
</template>

<script>
export default {
  name: "Template",
  props: {
    title: {
      type: String,
      default: "请传入标题文字"
    }
  },
  data() {
    return {};
  }
};
</script>

<style lang="scss">
.commonTitle {
  @include displayFlex();
  .line {
    width: 5px;
    background-color: $colorMain;
    height: 16px;
    margin-right: $sm;
    @include borderRadius(3px);
  }
  .text {
    flex: 1;
    color: $colorDark;
    font-size: $textDefault;
    font-weight: 700;
    line-height: 24px;
  }
}
</style>
