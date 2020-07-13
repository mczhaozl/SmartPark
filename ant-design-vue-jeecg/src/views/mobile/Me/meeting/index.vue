<template>
  <van-tabs v-model="active">
    <van-tab>
      <template #title>
        <van-icon name="clock-o"/>
        待参加
      </template>
      <van-list
        v-if="!isAttendingEmpty"
        v-model="attendingLoading"
        :finished="attendingFinished"
        finished-text="没有更多了"
        @load="onAttendingLoad"
      >
        <van-collapse v-model="activeNames">
          <van-collapse-item  v-for="item in attendingList" :title="item.name" :key="item.id">
            <div class="van-my-cell">会议主题：{{item.theme}}</div>
            <div class="van-my-cell">会议室：{{item.roomName}}</div>
            <div class="van-my-cell">开始时间：{{item.startTime}}</div>
            <div class="van-my-cell">结束时间：{{item.endTime}}</div>
            <div class="van-my-cell">参会人员：{{item.representatives}}</div>
            <div class="van-my-cell">会议发起人：{{item.sponsor}}</div>
            <div class="van-my-cell">备注：{{item.remark}}</div>
          </van-collapse-item>
        </van-collapse>

      </van-list>
      <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
    </van-tab>
    <van-tab>
      <template #title>
        <van-icon name="more-o"/>
        已参加
      </template>
      <van-list
        v-if="!isAttendEmpty"
        v-model="attendLoading"
        :finished="attendFinished"
        finished-text="没有更多了"
        @load="onAttendLoad"
      >
        <van-collapse v-model="activeNames">
          <van-collapse-item  v-for="item in attendList" :title="item.name" :key="item.id">
          <div class="van-my-cell">会议主题：{{item.theme}}</div>
          <div class="van-my-cell">会议室：{{item.roomName}}</div>
          <div class="van-my-cell">开始时间：{{item.startTime}}</div>
          <div class="van-my-cell">结束时间：{{item.endTime}}</div>
          <div class="van-my-cell">参会人员：{{item.representatives}}</div>
          <div class="van-my-cell">会议发起人：{{item.sponsor}}</div>
          <div class="van-my-cell">备注：{{item.remark}}</div>
          </van-collapse-item>
        </van-collapse>
      </van-list>
      <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
    </van-tab>
  </van-tabs>
</template>

<script>
  import Vue from 'vue'
  import { Tab, Tabs } from 'vant'
  import { List } from 'vant'
  import { Icon } from 'vant'
  import { getAction } from '@/api/manage'
  import { Empty } from 'vant'
  import { Collapse, CollapseItem } from 'vant';

  Vue.use(Collapse);
  Vue.use(CollapseItem);
  Vue.use(Icon)
  Vue.use(List)
  Vue.use(Tab)
  Vue.use(Tabs)
  Vue.use(Empty)
  export default {
    name: 'index',
    data() {
      return {
        active: 0,
        activeNames:[],
        isAttendingEmpty: false,
        attendingLoading: false,
        attendingFinished: false,
        attendingList: [],
        attendingPageNo: 1,
        isAttendEmpty: false,
        attendLoading: false,
        attendFinished: false,
        attendList: [],
        attendPageNo: 1,
        pageSize: 10
      }
    },
    methods: {
      onAttendingLoad() {
        let that = this

        getAction('/meeting/jsMeeting/myAttendMeetings', {
            'pageNo': that.attendingPageNo,
            'pageSize': that.pageSize
          }
        ).then(data => {
          // 加载状态结束
          that.attendingLoading = false;
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.attendingList.push(data.result.records[i])
            }
            that.attendingPageNo++
          } else {
            that.attendingFinished = true
            if (that.attendingPageNo == 1) {
              that.isAttendingEmpty = true
            }
          }
        })
      },
      onAttendLoad() {
        let that = this
        getAction('/meeting/jsMeeting/myEndMeetings', {
            'pageNo': that.attendPageNo,
            'pageSize': that.pageSize
          }
        ).then(data => {
          // 加载状态结束
          that.attendLoading = false
          console.log(data)
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.attendList.push(data.result.records[i])
            }
            that.attendPageNo++
          } else {
            that.attendFinished = true
            if (that.attendPageNo == 1) {
              that.isAttendEmpty = true
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