<template>
  <van-list
    v-if="!isEmpty"
    v-model="loading"
    :finished="finished"
    finished-text="没有更多了"
    @load="onLoad">
    <van-collapse v-model="activeNames">
      <van-collapse-item v-for="item in list" :title="item.name" :key="item.id">
        <div class="van-my-cell">会议主题：{{item.theme}}</div>
        <div class="van-my-cell">会议室：{{item.roomName}}</div>
        <div class="van-my-cell">开始时间：{{item.startTime}}</div>
        <div class="van-my-cell">结束时间：{{item.endTime}}</div>
        <div class="van-my-cell">参会人员：{{item.representatives}}</div>
        <div class="van-my-cell">会议发起人：{{item.sponsor}}</div>
        <div class="van-my-cell">备注：{{item.remark}}</div>
        <div class="van-my-cell">状态：{{item.status}}</div>
      </van-collapse-item>
    </van-collapse>
  </van-list>
  <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
</template>

<script>
  import Vue from 'vue'
  import { Collapse, CollapseItem, Empty, List } from 'vant'
  import { getAction, postAction } from '@/api/manage'

  Vue.use(List)
  Vue.use(Collapse)
  Vue.use(CollapseItem)
  Vue.use(Empty)
  export default {
    name: 'index',
    data() {
      return {
        activeNames: [],
        active: 0,
        list: [],
        isEmpty: false,
        loading: false,
        finished: false,
        pageNo: 1,
        pageSize: 10
      }
    },
    methods: {
      onLoad() {
        let that = this

        getAction('/meeting/jsMeeting/mySponsorMeetings', {
            'pageNo': that.pageNo,
            'pageSize': that.pageSize
          }
        ).then(data => {
          // 加载状态结束
          that.loading = false
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.list.push(data.result.records[i])
            }
            that.pageNo++
          } else {
            that.finished = true
            if (that.pageNo == 1) {
              that.isEmpty = true
            }
          }
        })
      }
    }
  }
</script>

<style scoped>
  .van-cell::after{
    border-bottom: 0px;
  }
  .van-my-cell{
    padding: 5px;
  }
</style>