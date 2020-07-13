<template>
  <div>
    <van-popup v-model="show" position="bottom" :overlay="true">
      <van-picker
        show-toolbar
        :default-index="active"
        :columns="columns"
        @cancel="handleCancel"
        @confirm="(value)=>{ handleConfirm(value) }"
      />
    </van-popup>
  </div>
</template> 
<script>
export default {
  name: "djPicker",
  props: {
    columns: {
      type: Array,
      required: true
    },
    active: {
      default: () => {
        return 0;
      }
    },
    confirm: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      show: false
    };
  },
  computed: {},
  components: {},
  methods: {
    handleShow() {
      this.show = true;
    },
    handleCancel() {
      this.show = false;
    },
    handleConfirm(value) {
      this.show = false;
      this.confirm(this.columns.indexOf(value), value);
    }
  }
};
</script>
<style lang="scss" scoped>
</style>