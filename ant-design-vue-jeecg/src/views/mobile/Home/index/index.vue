<template>
  <div>
    <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
      <van-list
        v-if="!isEmpty"
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad">
        <template #pulling="props">
          <img
            class="doge"
            src="https://img.yzcdn.cn/vant/doge.png"
            :style="{ transform: `scale(${props.distance / 80})` }"
          />
        </template>

        <!-- 释放提示 -->
        <template #loosing>
          <img class="doge" src="https://img.yzcdn.cn/vant/doge.png"/>
        </template>
        <div class="ant-card ant-card-bordered" v-for="(item,index) in list" :key="index" @click="detail(item.anntId,item.busId,item.busType)">
          <div class="ant-card-head">
            <div class="ant-card-head-wrapper">
              <div class="ant-card-head-title">{{item.titile}}</div>
              <div class="ant-card-extra">
                <a href="#" v-if="item.readFlag == 0" style="color:red">{{changeStatus(item.readFlag)}}</a>
                <a href="#" v-else>{{changeStatus(item.readFlag)}}</a>
              </div>
            </div>
          </div>
          <div class="ant-card-body">
            <div v-html="item.msgContent"></div>
          </div>
        </div>
      </van-list>
      <van-empty v-else description="暂无消息" style="background-color: #ffffff"/>
    </van-pull-refresh>
    <pageFooter></pageFooter>
  </div>
</template>

<script>
  import Vue from 'vue'
  import 'vant/lib/index.css'
  import { Empty, PullRefresh } from 'vant'
  import { Lazyload } from 'vant'
  import { List } from 'vant'
  import { getAction, putAction} from '@/api/manage'

  Vue.use(List)
  Vue.use(Lazyload)
  Vue.use(PullRefresh)
  Vue.use(Empty)
  export default {
    name: 'Index',
    data() {
      return {
        isEmpty: false,
        list: [],
        loading: false,
        finished: false,
        refreshing: false,
        pageNo: 1,
        pageSize: 10
      }
    },
    computed: {},
    components: {
      pageFooter: () => import('../../pageFooter/index')
    },
    created() {
    },
    mounted() {
    },
    methods: {
      onLoad() {
        let that = this
        getAction('/sys/sysAnnouncementSend/getMyAnnouncementSend', {
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
      },
      detail(anntId,id,busType) {
        //修改状态
        putAction("/sys/sysAnnouncementSend/editByAnntIdAndUserId",{anntId:anntId}).then((res)=>{
          if(res.success){
            this.$router.push({path:'/mobile/detail',query:{id:id,hidden:busType == 'bpm'?true:false}});
          }
        });
      },
      onRefresh() {
        // 清空列表数据
        this.finished = false
        // 重新加载数据
        // 将 loading 设置为 true，表示处于加载状态
        this.isEmpty = false
        this.loading = true
        this.list = []
        this.refreshing = false
        this.pageNo = 1
        this.onLoad()
      },
      changeStatus(text) {
        if (text == '0') {
          return '未读'
        } else if (text == '1') {
          return '已读'
        } else {
          return text
        }
      }
    }
  }
</script>
<style lang="scss" scoped>
  .ant-card-bordered {
    border: 1px solid #e8e8e8;
  }

  .ant-card {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    color: rgba(0, 0, 0, .65);
    font-size: 14px;
    font-variant: tabular-nums;
    line-height: 1.5;
    list-style: none;
    font-feature-settings: "tnum";
    position: relative;
    background: #fff;
    border-radius: 2px;
    transition: all .3s;
  }

  .ant-card-head {
    min-height: 48px;
    margin-bottom: -1px;
    padding: 0 24px;
    color: rgba(0, 0, 0, .85);
    font-weight: 500;
    font-size: 16px;
    background: transparent;
    border-bottom: 1px solid #e8e8e8;
    border-radius: 2px 2px 0 0;
    zoom: 1;
  }

  .ant-card-head-wrapper {
    display: flex;
    align-items: center;
  }

  .ant-card-head-title {
    display: inline-block;
    flex: 1;
    padding: 16px 0;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  .ant-card-extra {
    float: right;
    margin-left: auto;
    padding: 16px 0;
    color: rgba(0, 0, 0, .65);
    font-weight: 400;
    font-size: 14px;
  }

  .ant-card-body {
    padding: 24px;
    zoom: 1;
  }
</style>
