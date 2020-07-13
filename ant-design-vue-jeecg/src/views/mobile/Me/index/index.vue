<template>
  <div class="cpicContainer">
    <div class="van-card">
      <van-row style="background: #fff; padding:15px;">
        <van-col span="4">
          <van-image round
                     width="3.5rem"
                     height="3.5rem" src="https://img.yzcdn.cn/vant/cat.jpeg"/>
        </van-col>
        <van-col span="20" style="padding-left: 1rem;padding-top: 0.3rem">
          <van-row style="font-size:1rem;font-weight: bold;color: #646566">{{ nickname() }}</van-row>
          <van-row style="margin-top: 15px">{{ userInfo().phone }}</van-row>
        </van-col>
      </van-row>
    </div>
    <div class="van-card">
      <van-grid :border="false" :column-num="3">
        <van-grid-item :icon="icon.meeting" text="我的会议" to="/me/meeting"/>
        <van-grid-item :icon="icon.approve" text="我的审核" to="/me/verify"/>
        <van-grid-item :icon="icon.appointment" text="我的预约" to="/me/appointment"/>
      </van-grid>
    </div>
    <div class="van-card">
      <van-cell tabindex="0" title="会议安排" is-link to="/me/plan"/>
      <van-cell tabindex="0" title="会议室" is-link to="/me/room"/>
      <van-cell tabindex="0" title="修改密码" is-link @click="updatePassword"/>
      <van-cell tabindex="0" title="退出登录" is-link @click="handleLogout"/>
    </div>
    <user-password ref="userPassword"></user-password>
    <pageFooter></pageFooter>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { Grid, GridItem } from 'vant'
  import { Col, Row } from 'vant'
  import { Image as VanImage } from 'vant'
  import { Cell, CellGroup } from 'vant'
  //导入图片
  import meeting from '@/assets/images/meeting.png'
  import approve from '@/assets/images/approve.png'
  import appointment from '@/assets/images/my-appointment.png'
  import { mapActions, mapGetters } from 'vuex'
  import UserPassword from '@/components/tools/UserPassword'

  Vue.use(VanImage)
  Vue.use(Grid)
  Vue.use(GridItem)
  Vue.use(Col)
  Vue.use(Row)
  Vue.use(Cell)
  Vue.use(CellGroup)

  export default {
    name: 'Index',
    data() {
      return {
        loading: false,
        icon: {
          meeting: meeting,
          approve: approve,
          appointment: appointment
        }
      }
    },
    computed: {},
    components: {
      pageFooter: () => import('../../pageFooter/index'),
      UserPassword
    },
    created() {
    },
    mounted() {

    },
    methods: {
      ...mapActions(['Logout']),
      ...mapGetters(['nickname', 'avatar', 'userInfo']),
      handleLogout() {
        const that = this

        this.$confirm({
          title: '提示',
          content: '真的要注销登录吗 ?',
          onOk() {
            return that.Logout({}).then(() => {
              window.location.href = '/mobile'
              //window.location.reload()
            }).catch(err => {
              that.$message.error({
                title: '错误',
                description: err.message
              })
            })
          },
          onCancel() {
          }
        })
      },
      updatePassword(){
        let username = this.userInfo().username
        this.$refs.userPassword.show(username)
      },
      getAvatar() {
        return getFileAccessHttpUrl(this.avatar())
      }
    }
  }
</script>

<style lang="scss">
  .cpicContainer {
    background-color: #fafafa;
  }

  .van-card:not(:first-child) {
    margin-top: 0;
  }

</style>