<template>
  <div>
    <van-dropdown-menu>
      <van-dropdown-item v-model="value1" :options="option1" @change="displayByDefault"/>
      <van-dropdown-item v-model="value2" :options="option2" @change="displayByRooms"/>
    </van-dropdown-menu>
    <div v-if="value2 == 0">
      <van-list
        v-if="!isEmpty"
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
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
    </div>
    <div v-else>
      <van-tabs>
        <van-tab v-for="(it,index) in rooms" :title="it.name" :key="it.id">
          <van-list
            v-if="!isRoomsEmpty[index]"
            v-model="roomsLoading[index]"
            :finished="roomsFinished[index]"
            finished-text="没有更多了"
            @load="onRoomLoad(index,it.name)"
          >
            <van-collapse v-model="activeNames">
              <van-collapse-item v-for="(item,idk) in roomsList[index]" :title="item.name" :key="idk">
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
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { DropdownMenu, DropdownItem, List, Collapse, CollapseItem, Tab, Tabs, Empty } from 'vant'
  import { getAction, postAction } from '@/api/manage'

  Vue.use(DropdownMenu)
  Vue.use(DropdownItem)
  Vue.use(List)
  Vue.use(Collapse)
  Vue.use(CollapseItem)
  Vue.use(Tab)
  Vue.use(Tabs)
  Vue.use(Empty)
  export default {
    name: 'index',
    data() {
      return {
        activeNames: [],
        list: [],
        isEmpty: false,
        loading: false,
        finished: false,
        pageNo: 1,
        pageSize: 10,
        value1: 0,
        value2: 0,
        option1: [{ text: '全部', value: 0 }, { text: '与我有关', value: 1 }],
        option2: [{ text: '默认显示', value: 0 }, { text: '会议室维度', value: 1 }],
        roomsList: [],
        rooms: [],
        roomsFinished: [],
        roomsLoading: [],
        isRoomsEmpty: [],
        roomsPageNo: []
      }
    },
    components: {
      pageFooter: () => import('../../pageFooter/index')
    },
    created() {
    },
    mounted() {
      this.getRooms()
    },
    methods: {
      onLoad() {
        let that = this
        getAction('/meeting/jsMeeting/meetingPlan', {
            'pageNo': that.pageNo,
            'pageSize': that.pageSize,
            'isMy': that.value1
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
      },
      onRoomLoad(index, name) {
        let that = this
        console.log(index + name)
        getAction('/meeting/jsMeeting/meetingPlanByRoom', {
            'pageNo': that.roomsPageNo[index],
            'pageSize': that.pageSize,
            'isMy': that.value1,
            'room': name
          }
        ).then(data => {
          // 加载状态结束
          that.roomsLoading[index] = false
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.roomsList[index].push(data.result.records[i])
            }
            that.roomsPageNo[index]++
          } else {
            that.roomsFinished[index] = true
            if (that.roomsPageNo[index] == 1) {
              that.isRoomsEmpty[index] = true
            }
          }
        })
      },
      displayByDefault() {

      },
      displayByRooms() {

      },
      getRooms() {
        //获取room
        let that = this
        getAction('/meeting/jsRoom/list', {
            'pageNo': 1,
            'pageSize': 1000
          }
        ).then(data => {
          // 加载状态结束
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.rooms.push(data.result.records[i])
              //初始化isRoomsEmpty,roomsFinished,roomsLoading
              that.isRoomsEmpty.push(false)
              that.roomsFinished.push(false)
              that.roomsLoading.push(false)
              that.roomsPageNo.push(1)
              that.roomsList.push([])
            }
          }
        })
      }
    }
  }
</script>

<style scoped>
  .van-cell::after {
    border-bottom: 0px;
  }

  .van-my-cell {
    padding: 5px;
  }
</style>