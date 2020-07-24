<template>
  <div >
    <!-- <pageHeader title="南昌高新停车" :leftIcon="false" :rightIcon="false"></pageHeader> -->

    <div class="container">
      <div class="loginWarp">
        <van-cell-group class="am-padding-top">
          <van-field v-model="userData.username" label="账号" placeholder="请输入账号" />
          <van-field
            v-model="userData.password"
            label="密码"
            :type="showPassword ? 'text' : 'password'"
            :right-icon="showPassword ? 'closed-eye' : 'eye-o'"
            placeholder="请输入密码"
            @click-right-icon="passWordType"
          />
        </van-cell-group>

        <div class="am-margin-top-xl">
          <van-button type="info" :color="mainColor" round block @click="login">登录</van-button>
        </div>
        <div class="forgetPassword am-padding-vertical-sm" @click="forgetPassword">忘记密码？</div>
      </div>
    </div>
  </div>
</template>

<script>
import { captcha } from "@/api/common";
import { checkPhone, checkCode } from "@/utils/validate";
import { mapGetters, mapMutations, mapActions } from "vuex";
import {postAction} from '@/api/manage';
import Router from "vue-router";
import VueRouter from 'vue-router';

export default {
  name: "Login",
  data() {
    return {
      showPassword: false,
      userData: {
        name: "",
        password: ""
      }
    };
  },
  components: {
      Router
    // pageHeader: () => import("@/components/pageHeader/index")
  },
  computed: {
    ...mapGetters([
      "userInfo" // 用户信息
    ]),
    baseUrl() {
      return process.env.VUE_APP_BASE_URL;
    }
  },
  methods: {
    /**
     * 忘记密码
     */
    forgetPassword() {
      console.log("忘记密码");
    },
    /**
     * 查看密码形式转换
     */
    passWordType() {
      this.showPassword = !this.showPassword;
    },
    // 提交之前表单验证
    validate() {
      if (this.userData.username == "") {
        this.$toast("请输入账号");
        return false;
      } else if (this.userData.password == "") {
        this.$toast("请输入密码");
        return false;
      }
      return true;
    },
    // 登录
    async login() {
      let vm = this;
      // 提交验证
      if (!vm.validate("login")) {
        return false;
      }
      // 获取登录接口数据
  
      postAction(window._CONFIG['domianURL'] + '/sys/login', {
                        'username': vm.userData.username,
                        'password': vm.userData.password
                    }
                ).then(data => {
                    // 加载状态结束
                    if(data.code === 200){
                      localStorage.setItem("token",data.result.token);
                      localStorage.setItem("realname",data.result.userInfo.realname);
                      localStorage.setItem("userName",data.result.userInfo.username);
                      localStorage.setItem("phone",data.result.userInfo.phone);
                      localStorage.setItem("isLogin","true");
                      this.$router.push("/me");
                      
                    }
                    
                    
                })
      // const { data } = await login(vm.userData);
      // console.log(data);
    }
  },
  created() {},
  mounted() {}
};
</script>
<style lang="scss" scoped>
.pContainer{
  width: 100%;
  height: 100%;
  background-image: url("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1828833263,3743840884&fm=26&gp=0.jpg");
}
.container {
  
  @include container();
  .loginWarp {
    @include commonWarp();
    margin-top: 20px;
    .forgetPassword {
      font-size: $textSm;
      color: $colorMain;
      line-height: 24px;
      text-align: right;
    }
  }
}
</style>
