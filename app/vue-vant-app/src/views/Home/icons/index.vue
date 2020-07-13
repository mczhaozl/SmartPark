<template>
  <div class="icons-container">
        <div v-for="item of svgIcons" :key="item" @click="handleClipboard(generateIconCode(item),$event)">
            <div class="icon-item">
              <svg-icon :icon-class="item" class-name="disabled" />
              <span>{{ item }}</span>
            </div>
        </div>
  </div>
</template>

<script>
import clipboard from '@/utils/clipboard'
import svgIcons from './svg-icons'

export default {
  name: 'Icons',
  data() {
    return {
      svgIcons
    }
  },
  methods: {
    generateIconCode(symbol) {
      return `<svg-icon icon-class="${symbol}" />`
    },
    handleClipboard(text, event) {
      clipboard(text, event)
    }
  }
}
</script>

<style lang="scss" scoped>
.icons-container {
  background-color: #fff;
  margin: 0px 0px;
  overflow: hidden;

  .icon-item {
    margin: 10px;
    height: 55px;
    text-align: center;
    width: 72px;
    float: left;
    font-size: 20px;
    color: #24292e;
    cursor: pointer;
  }

  span {
    display: block;
    font-size: 12px;
    margin-top: 5px;
  }

  .disabled {
    pointer-events: none;
  }
}
</style>
