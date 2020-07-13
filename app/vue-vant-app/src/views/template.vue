<template>
  <div class="cpicContainer"></div>
</template>

<script>
export default {
  name: "Template",
  data() {
    return {
        msg: 123
    };
  },
  components: {
    // pageHeader: () => import("@/components/pageHeader/index"),
    // pageFooter: () => import("@/components/pageFooter/index"),
    // mainContainer: () => import("@/components/mainContainer/default"),
    // mainContainer: () => import("@/components/mainContainer/refresh"),
    // mainContainer: () => import("@/components/mainContainer/refreshReload"),
  },
  computed: {
  },
  created() {},
  mounted() {
    let vm = this;
    vm.init()
  },
  methods: {
      /**
       * 获取数据
       */
      init() {

      }

  }
};
</script>

<style lang="scss">
</style>
