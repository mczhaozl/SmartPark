<template>
  <van-tabs v-model="active">
    <van-tab>
      <template #title>
        <van-icon name="clock-o"/>
        待审核
      </template>
      <van-list
        v-if="!isAttendingEmpty"
        v-model="attendingLoading"
        :finished="attendingFinished"
        finished-text="没有更多了"
        @load="onAttendingLoad"
      >
        <van-collapse v-model="activeNames">
          <van-collapse-item v-for="item in attendingList" :title="item.name" :key="item.id">
            <div class="van-my-cell">会议主题：{{item.theme}}</div>
            <div class="van-my-cell">会议室：{{item.roomName}}</div>
            <div class="van-my-cell">开始时间：{{item.startTime}}</div>
            <div class="van-my-cell">结束时间：{{item.endTime}}</div>
            <div class="van-my-cell">参会人员：{{item.representatives}}</div>
            <div class="van-my-cell">会议发起人：{{item.sponsor}}</div>
            <div class="van-my-cell">备注：{{item.remark}}</div>
            <van-button plain size="small" type="warning" @click="show=true,type='不同意',id=item.id">不同意</van-button>
            <van-button plain size="small" type="info" style="margin-left: 5px" @click="show=true,type='同意',id=item.id">同意</van-button>
          </van-collapse-item>
        </van-collapse>
      </van-list>
      <van-empty v-else description="暂无数据" style="background-color: #ffffff" />
      <van-dialog
        v-model="show"
        show-cancel-button
        @confirm="onApprove">
        <van-field
          v-model="message"
          rows="2"
          autosize
          label="审批意见"
          type="textarea"
          maxlength="150"
          placeholder="请输入审批意见"
          show-word-limit
        />
      </van-dialog>
    </van-tab>
    <van-tab>
      <template #title>
        <van-icon name="more-o"/>
        已审核
      </template>
      <van-list
        v-if="!isAttendEmpty"
        v-model="attendLoading"
        :finished="attendFinished"
        finished-text="没有更多了"
        @load="onAttendLoad"
      >
        <van-collapse v-model="activeNames">
          <van-collapse-item v-for="item in attendList" :title="item.name" :key="item.id">
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
  import { getAction, postAction } from '@/api/manage'
  import { Field, Form, Tab, Tabs } from 'vant'
  import { List } from 'vant'
  import { Icon } from 'vant'
  import { Empty } from 'vant'
  import { Collapse, CollapseItem } from 'vant';
  import { Button } from 'vant';
  import { Dialog } from 'vant';

  Vue.use(Form)
  Vue.use(Field)
  Vue.use(Button);
  Vue.use(Collapse);
  Vue.use(CollapseItem);
  Vue.use(Icon)
  Vue.use(List)
  Vue.use(Tab)
  Vue.use(Tabs)
  Vue.use(Empty)
  export default {
    name: 'index',
    components: {
      [Dialog.Component.name]: Dialog.Component,
    },
    data() {
      return {
        active: 0,
        id:'',
        type:'',
        activeNames: [],
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
        pageSize: 10,
        show: false,
        message:''
      }
    },
    methods: {
      onAttendingLoad() {
        let that = this

        getAction('/meeting/jsMeeting/queryMyApprove', {
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
        getAction('/meeting/jsMeeting/queryMyEndApprove', {
            'pageNo': that.attendPageNo,
            'pageSize': that.pageSize
          }
        ).then(data => {
          // 加载状态结束
          that.attendLoading = false
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
      },
      onApprove(){
        let that = this;
        //审批通过
        getAction('/meeting/jsMeeting/approve',{'id':that.id,'status':that.type,'opinion':that.message}).then(data => {
          Dialog.alert({
            message: data.message,
          }).then(() => {
            this.$router.push('/mobile/index');
          });
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