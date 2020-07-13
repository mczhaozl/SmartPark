<template>
  <div class="container" :style="{ 'top': top + 'rem', backgroundColor: background }">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "Template",
  props: {
    top: {
      type: String,
      default: 1.33333
    },
    background: {
      type: String,
      default: "nuset"
    }
  }
};
</script>

<style lang="scss">
.container {
  @include container();
}
</style>
