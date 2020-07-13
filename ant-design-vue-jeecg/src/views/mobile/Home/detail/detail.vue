<template>
  <van-panel :title="record.name" :desc="'会议主题：'+record.theme">
    <div class="content">
      <p>会议室：{{record.roomName}}</p>
      <p>开始时间：{{record.startTime}}</p>
      <p>结束时间：{{record.endTime}}</p>
      <p>发起人：{{record.sponsor}}</p>
      <p>参会人员：{{record.representatives}}</p>
      <p>状态：{{record.status}}</p>
    </div>
    <template #footer v-if="record.status == '待审核'">
      <van-button plain size="small" @click="show=true,type='不同意',id=item.id">不同意</van-button>
      <van-button plain size="small" type="info" style="margin-left: 5px" @click="show=true,type='同意',id=item.id">同意</van-button>
    </template>
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
  </van-panel>

</template>

<script>
  import Vue from 'vue'
  import { Button, Dialog, Field, Form, Panel } from 'vant'
  import { getAction, putAction } from '@/api/manage'

  Vue.use(Form)
  Vue.use(Field)
  Vue.use(Button);
  Vue.use(Panel)
  export default {
    name: 'detail',
    components: {
      [Dialog.Component.name]: Dialog.Component,
    },
    data() {
      return {
        id:'',
        isHidden: true,
        message: '',
        show: false,
        record: {},
        type:''
      }
    },
    created() {

    },
    mounted() {
      this.id = this.$route.query.id
      this.showDetail(this.id)
    },
    methods: {
      showDetail(id) {
        getAction('/meeting/jsMeeting/queryById', { id: id }).then((res) => {
          if (res.success) {
            this.record = res.result
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
  .content p {
    line-height: 30px;
    font-size: 12px;
    margin: 10px 10px;
  }
</style>