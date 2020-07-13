<template>
  <div class="not-found padding-top46">
    
    <div class="svg"><svg-icon icon-class="404" /></div>
    <h1 class="h1">页面未找到</h1>
  </div>
</template>
<script>
export default {
  data(){
    return {

    }
  },
  components: {
  },
  methods:{
    handleClickLeft(){
      this.$router.go(-1)
    },
  }
}
</script>
<style lang="scss" scoped>
  .not-found {
    .svg{
      font-size: 60px; text-align: center; padding: 50px 0 10px;
    }
    .h1 {
      text-align: center;
      padding: 10px;
    }
  }
</style>

