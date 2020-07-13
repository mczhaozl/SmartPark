<template>
  <div class="">
    <van-form>
      <van-field
        v-model="model.name"
        required
        name="会议名称"
        label="会议名称"
        placeholder="请输入会议名称"
      />
      <van-field
        v-model="model.theme"
        rows="2"
        autosize
        required
        name="会议主题"
        label="会议主题"
        type="textarea"
        maxlength="50"
        placeholder="请输入会议主题"
        show-word-limit
      />
      <van-field
        readonly
        clickable
        required
        type="datetime"
        name="datetimePicker"
        :value="model.startTime"
        label="会议开始时间"
        placeholder="点击选择时间"
        @click="showStartTime = true"
      />
      <van-field
        readonly
        clickable
        required
        type="datetime"
        name="datetimePicker"
        :value="model.endTime"
        label="会议结束时间"
        placeholder="点击选择时间"
        @click="showEndTime = true"
      />
      <van-popup v-model="showStartTime" position="bottom">
        <van-datetime-picker
          type="datetime"
          :min-date="minDate"
          @confirm="onStartConfirm"
          @cancel="showStartTime = false"
        />
      </van-popup>
      <van-popup v-model="showEndTime" position="bottom">
        <van-datetime-picker
          type="datetime"
          :min-date="minDate"
          @confirm="onEndConfirm"
          @cancel="showEndTime = false"
        />
      </van-popup>
      <van-field
        readonly
        clickable
        required
        name="picker"
        :value="model.roomName"
        label="会议室"
        placeholder="点击选择会议室"
        @click="getRooms"
      />
      <van-popup v-model="showRoomPicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="columns"
          @confirm="onRoomConfirm"
          @cancel="showRoomPicker = false"
        />
      </van-popup>
      <van-field
        readonly
        clickable
        required
        name="picker"
        :value="representativeNames"
        label="参会人员"
        placeholder="点击选择参会人员"
        @click="showRepresentatives = true"
      />

      <van-popup v-model="showRepresentatives" round position="bottom" closeable
                 close-icon="close" :style="{ height: '80%' }">
        <form action="/" style="margin-top:40px">
          <van-search
            v-model="searchValue"
            show-action
            placeholder="请输入搜索关键词"
            @search="onSearch"
            @cancel="onCancel"
          />
        </form>
        <van-list
          v-if="!isEmpty"
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <van-checkbox-group v-model="represent">
            <van-cell-group>
              <van-cell
                v-for="(item, index) in representatives"
                clickable
                :key="item.id"
                :title="item.realname"
                @click="toggle(index)"
              >
                <template #right-icon>
                  <van-checkbox :name="item" ref="checkboxes"/>
                </template>
              </van-cell>
            </van-cell-group>
          </van-checkbox-group>
        </van-list>
        <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
        <van-button @click="onSelectUser" type="info" block plain round style="position: fixed; bottom: 0;left: 0;">确定
        </van-button>
      </van-popup>

      <van-field
        readonly
        clickable
        required
        name="picker"
        :value="approves"
        label="审批人"
        placeholder="点击选择审批人"
        @click="showApprove = true"
      />
      <van-popup v-model="showApprove" round position="bottom" closeable
                 close-icon="close" :style="{ height: '80%' }">
        <form action="/" style="margin-top:40px">
          <van-search
            v-model="searchValue"
            show-action
            placeholder="请输入搜索关键词"
            @search="onSearch"
            @cancel="onCancel"
          />
        </form>
        <van-list
          v-if="!isApproveEmpty"
          v-model="approveLoading"
          :finished="approveFinished"
          finished-text="没有更多了"
          @load="onLoadApprove"
        >

          <van-radio-group v-model="approve">
            <van-cell-group>
              <van-cell
                v-for="(item, index) in approvesList"
                clickable
                :key="item.id"
                :title="item.realname"
                @click="toggleApprove(index)"
              >
                <template #right-icon>
                  <van-radio :name="item" ref="checkboxesApprove"/>
                </template>
              </van-cell>
            </van-cell-group>
          </van-radio-group>
        </van-list>
        <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
        <van-button @click="onSelectApprove" type="info" block plain round style="position: fixed; bottom: 0;left: 0;">
          确定
        </van-button>
      </van-popup>

      <van-field
        v-model="model.remark"
        rows="2"
        autosize
        name="备注"
        label="备注"
        type="textarea"
        maxlength="150"
        placeholder="请输入备注"
        show-word-limit
      />
      <div style="margin: 16px;">
        <van-button round block type="info" native-type="submit" @click="onSubmit">
          提交
        </van-button>
      </div>
    </van-form>
    <pageFooter></pageFooter>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { getAction, postAction } from '@/api/manage'
  import { DatetimePicker, Divider } from 'vant'
  import { Field } from 'vant'
  import { Form } from 'vant'
  import { Button } from 'vant'
  import { Picker } from 'vant'
  import { Popup } from 'vant'
  import { Checkbox, CheckboxGroup } from 'vant'
  import { Search } from 'vant'
  import { List } from 'vant'
  import { Cell, CellGroup } from 'vant'
  import { Dialog } from 'vant'
  import { RadioGroup, Radio } from 'vant'
  import { Notify } from 'vant';
  import { Toast } from 'vant';

  Vue.use(Toast);
  Vue.use(Radio)
  Vue.use(RadioGroup)
  Vue.use(Cell)
  Vue.use(CellGroup)
  Vue.use(List)
  Vue.use(Search)
  Vue.use(Checkbox)
  Vue.use(CheckboxGroup)
  Vue.use(Popup)
  Vue.use(Picker)
  Vue.use(Button)
  Vue.use(Form)
  Vue.use(Field)
  Vue.use(DatetimePicker)
  Vue.use(Divider)
  Vue.use(Notify);
  export default {
    name: 'mform',
    components: {
      pageFooter: () => import('../../pageFooter/index'),
      [Dialog.Component.name]: Dialog.Component
    },
    data() {
      return {
        model: {
          name: '',
          theme: '',
          startTime: '',
          endTime: '',
          roomName: '',
          approveId: '',
          approve: '',
          representatives: '',
          remark: ''
        },
        show: false,
        showStartTime: false,
        showEndTime: false,
        showRoomPicker: false,
        represent: [],
        showRepresentatives: false,
        representatives: [],
        representativeNames: '',
        searchValue: '',
        minDate: new Date(),
        columns: [],
        loading: false,
        finished: false,
        isEmpty: false,
        pageNo: 1,
        pageSize: 10,
        showApprove: false,
        approves: '',
        approve: [],
        isApproveEmpty: false,
        approveLoading: false,
        approveFinished: false,
        approvesList: [],
        approveEmpty: false,
        approvePageNo: 1
      }
    },
    mounted() {
    },
    methods: {
      onClickLeft() {
        this.$router.push('/appointment')
      },
      onSubmit() {
        //必填项验证
        if(this.model.name.trim() == ''){
          Notify({ type: 'warning', message: '请输入会议名称' });
          return ;
        }
        if(this.model.theme.trim() == ''){
          Notify({ type: 'warning', message: '请输入会议主题' });
          return ;
        }
        if(this.model.startTime.trim() == ''){
          Notify({ type: 'warning', message: '请输入开始时间' });
          return ;
        }
        if(this.model.endTime.trim() == ''){
          Notify({ type: 'warning', message: '请输结束时间' });
          return ;
        }
        if(this.model.roomName.trim() == ''){
          Notify({ type: 'warning', message: '请选择会议室' });
          return ;
        }
        if(this.model.representatives.trim() == ''){
          Notify({ type: 'warning', message: '请选择参会人员' });
          return ;
        }
        if(this.model.approve.trim() == ''){
          Notify({ type: 'warning', message: '请选择审批人' });
          return ;
        }
        const toast = Toast.loading({
          duration: 0, // 持续展示 toast
          forbidClick: true,
          message: '正在提交...',
        });
        //提交数据
        postAction('/meeting/jsMeeting/add', this.model).then(data => {
          Toast.clear();
          Toast.success(data.message);
          this.$router.push('/mobile/index');
        })
      },
      onRoomConfirm(value) {
        this.showRoomPicker = false
        this.model.roomName = value
      },
      onStartConfirm(value) {
        //时间转换
        this.showStartTime = false
        var datejson = new Date(value).toJSON()
        var date = new Date(+new Date(datejson)
          + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
        this.model.startTime = date
      },
      onEndConfirm(value) {
        //时间转换
        this.showEndTime = false
        var datejson = new Date(value).toJSON()
        var date = new Date(+new Date(datejson)
          + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
        this.model.endTime = date
      },
      //获取在时间段内可用会议室
      getRooms() {
        let that = this
        that.showRoomPicker = true
        that.columns = []
        getAction('/meeting/jsRoom/queryByDate', {
            'startTime': that.model.startTime,
            'endTime': this.model.endTime
          }
        ).then(data => {
          for (let i in data.result) {
            that.columns.push(data.result[i].name)
          }
        })
      },
      ///获取所有用户
      onLoad() {
        let that = this
        getAction('/sys/user/list', {
            'pageNo': that.pageNo,
            'pageSize': that.pageSize,
            'realname': that.searchValue
          }
        ).then(data => {
          that.loading = false
          console.log(data)
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.representatives.push(data.result.records[i])
            }
            that.pageNo++
          } else {
            that.finished = true
            if (that.pageNo == 1) {
              that.empty = true
            }
          }
        })
      },
      //获取所有审批人
      onLoadApprove() {
        let that = this
        getAction('/meeting/jsMeeting/queryApprove', {
            'pageNo': that.approvePageNo,
            'pageSize': that.pageSize,
            'realname': that.searchValue
          }
        ).then(data => {
          that.approveLoading = false
          if (data.result.records != null && data.result.records.length > 0) {
            for (let i in data.result.records) {
              that.approvesList.push(data.result.records[i])
            }
            that.approvePageNo++
          } else {
            that.approveFinished = true
            if (that.approvePageNo == 1) {
              that.approveEmpty = true
            }
          }
        })
      },
      toggle(index) {
        this.$refs.checkboxes[index].toggle()

      },
      toggleApprove(index) {
        this.$refs.checkboxesApprove[index].toggle()

      },
      onSearch() {

      },
      onCancel() {

      },
      onSelectUser() {
        //获取选中的key
        if (this.represent == null || this.represent.length == 0) {
          Dialog.alert({
            message: '请选择参会人员'
          }).then(() => {
            return
          })
        }
        this.representativeNames = ''
        this.model.representatives = ''
        for (let i in this.represent) {
          this.model.representatives = this.model.representatives + this.represent[i].id + ' '
          this.representativeNames = this.representativeNames + this.represent[i].realname + ' '
        }
        this.showRepresentatives = false
      },
      onSelectApprove() {
        console.log(this.approve)
        //获取选中的key
        if (this.approve == null || this.approve == '') {
          Dialog.alert({
            message: '请选择审批人员'
          }).then(() => {
            return
          })
        }
        this.model.approveId = this.approve.id;
        this.model.approve = this.approve.realname;
        this.approves = this.approve.realname ;
        this.showApprove = false
      }
    }
  }
</script>

<style scoped>
</style>