<template>
  <div class="login">
    <div class="main">
      <div class="logoContainer">
        <div class="logo"><img src="@/assets/img/logo.png" alt="" /></div>
        <div class="name">BUDIN</div>
      </div>
      <div
        class="mainBox"
        :class="activeName == 'first' ? '' : 'mainBoxRegistered'"
      >
        <el-tabs
          v-model="activeName"
          type="card"
          @tab-click="handleClick"
          stretch
        >
          <el-tab-pane label="登录" name="first">
            <div class="loginInput">
              <el-form ref="form" :model="login" label-width="80px">
                <el-form-item>
                  <el-input
                    v-model="login.account"
                    placeholder="请输入用户名"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="login.password"
                    type="password"
                    placeholder="请输入密码"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="onSubmit">登录</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="注册" name="second">
            <div class="registeredInput">
              <el-form ref="form" :model="login" label-width="80px">
                <el-form-item>
                  <el-input
                    v-model="registered.userAccount"
                    placeholder="请输入用户名"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="registered.email"
                    placeholder="请输入邮箱"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="registered.password"
                    placeholder="请输入密码"
                    type="password"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="rePassword"
                    placeholder="请确认密码"
                    type="password"
                  ></el-input>
                </el-form-item>
                <el-form-item>
                  <el-input
                    v-model="registered.userNickname"
                    placeholder="请输入昵称"
                  ></el-input>
                </el-form-item>
                <el-form-item class="codeContainer">
                  <el-input
                    v-model="registered.code"
                    placeholder="请输入验证码"
                  ></el-input>
                  <div class="codeButtonContainer">
                    <el-button
                      size="mini"
                      class="getcode"
                      v-if="!isCountDownShow"
                      @click="getCode"
                      >获取验证码</el-button
                    >
                    <div class="countDown" v-else>{{ countDownSecond }} s</div>
                  </div>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="clickRegistered"
                    >注册</el-button
                  >
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
// 倒计时名称
let timer;

export default {
  name: "Login",
  data() {
    return {
      login: {
        account: "",
        password: "",
      },
      registered: {
        userAccount:"",
        email: "",
        password: "",
        code: "",
        userNickname: "",
        avatar: null,
      },
      rePassword:"",

      activeName: "first",
      // 倒计时秒数
      countDownSecond: 60,
      // 是否显示秒数
      isCountDownShow: false,
    };
  },
  methods: {
    //   点击登录的回调
    async onSubmit() {
      let res = await this.$request(
        "/center/user/login",
        this.login,
        "post",
        "params",
        "formdata"
      );
      console.log(res);
      if (res.status == 200 && res.data.success) {
        // 登陆成功
        // 将用户信息保存至vuex
        this.$store.commit("updateUserInfo", res.data.data.budinUserInfoVO);

        //将返回的用户信息保存至localstorage中
        window.localStorage.setItem(
          "userInfo",
          JSON.stringify(res.data.data.budinUserInfoVO)
        );

        // 将token存入本地
        window.localStorage.setItem("token", res.data.data.token);

        //   跳转至主界面
        this.$router.push("/index");
      } else if (res.status == 200 && !res.data.success) {
        this.$message.warning("登录失败,账号或密码错误!");
      }
    },

    handleClick(e) {
      console.log(e.name);
    },

    // 获取验证码
    async getCode() {
      this.isCountDownShow = true;
      

      let res = await this.$request(
        "/center/user/send/code",
        {email: `${this.registered.email}`},
        "post",
        "params",
        "application"
      );
      console.log(res);
      if (res.data.success) {
        this.$message.success("验证码已发送!");
        this.startCountDown();
      }else{
        this.$message.error(res.data.message);
      }
    },

    // 倒计时
    startCountDown() {
      this.countDownSecond = 60;
      timer = setInterval(() => {
        this.countDownSecond--;
        if (this.countDownSecond == 0) {
          clearInterval(timer);
          this.isCountDownShow = false;
        }
      }, 1000);
    },
    // 点击注册的回调
    async clickRegistered() {
      if(this.registered.password!=this.rePassword) {
        this.$message.error("密码不一致!");
        return;
      }
      let res = await this.$request(
        "/center/user/register",
        this.registered,
        "post",
        "params"
      );
      console.log(res);
      // 如果注册成功，清空所有数据并跳转至登录界面，自动填写账号
      if (res.data.success) {
        this.$message.success("注册成功!");
        this.login.account = this.registered.userAccount;
        this.activeName = "first";
        this.registered = {
          userAccount:"",
          email: "",
          password: "",
          code: "",
          userNickname: "",
          avatar: null,
        };
        this.rePassword="";
      } else {
        this.$message.error("注册失败,请稍后重试!");
      }
    },
  },
};
</script>

<style scoped>
.login {
  background-color: #ecefff;
  height: 100vh;
}

.logoContainer {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  justify-content: center;
}

.logo {
  width: 50px;
}

.logo img {
  width: 100%;
}

.name {
  color: #4b49c9;
  font-size: 20px;
  letter-spacing: 4px;
  font-weight: bold;
  margin-left: 7px;
}

.main {
  width: 350px;
  height: 400px;
  position: absolute;
  right: 40%;
  top: 15vh;
}

.mainBox {
  width: 350px;
  background-color: #fff;
  height: 300px;
  border-radius: 10px;
  overflow: hidden;
}

.mainBoxRegistered {
  height: 550px;
}

.el-form /deep/ .el-form-item__content {
  margin: 0 !important;
  padding: 0 20px;
}

.el-input /deep/ input {
  border-radius: 10px;
}

.loginInput {
  margin-top: 20px;
}

.el-tabs /deep/ .is-active,
.el-tabs /deep/ div:hover {
  color: #595bb3;
}

.el-tabs /deep/ .is-active {
  background-color: #fff;
}

.el-tabs /deep/ .el-tabs__item {
  border: none !important;
  font-size: 18px;
  height: 50px;
  line-height: 50px;
}

.el-tabs /deep/ .el-tabs__nav {
  border: none;
}

.el-tabs /deep/ .el-tabs__nav-scroll {
  background-color: #f5f5f6;
}

.el-input /deep/ .el-input__inner {
  height: 48px;
  font-size: 15px;
}

.el-button {
  width: 100%;
  background-color: #6c6dbb;
  border: none;
  border-radius: 10px;
  margin-top: 10px;
  height: 45px;
  font-size: 15px;
}

.el-button:hover {
  background-color: #595bb3;
}

.codeContainer {
  position: relative;
}

.codeButtonContainer {
  position: absolute;
  top: 50%;
  right: 30px;
  transform: translateY(-50%);
}

.getcode {
  background-color: #6c6dbb;
  color: white;
  height: 35px;
  margin: 0;
}

.countDown {
  color: rgb(141, 141, 141);
}
</style>
