<template>
  <div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: "Layout",
  data() {
    return {};
  },
  computed: {},
  created() {}
};
</script>
<style lang="scss">
body,
html {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  font-size: 14px;
}
#app {
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow-y: scroll
    $colorLighter;
  background-size: 100% auto;
}
</style>
