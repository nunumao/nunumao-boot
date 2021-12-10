<template>
  <div class="login h-100">
    <d1-login custom-class="login-frame" v-model:captcha="captcha" @refresh="refresh" @login="login"></d1-login>
  </div>
</template>
<script>
export default {
  data() {
    return {
      captcha: '',
      t: ''
    }
  },
  mounted() {
    this.refresh();
  },
  methods: {
    login(event) {
      let params = {
        account: event.account,
        password: event.password,
        grantType: "password",
        clientId: this.$config.CLIENT_ID,
        clientSecret: this.$config.CLIENT_SECRET,
        scope: "all",
        origin: this.t,
        code: event.code
      };
      this.$axios.post("oauth2/token", params).then(data => {
        let isAdmin = data.result.authorities.find(value => {
          return value === "ROLE_ADMIN";
        });
        if (isAdmin) {
          event.onSuccess(data.result.accessToken, data.result.refreshToken);
        } else {
          event.onError("登录的账户不存在");
        }
      }).catch(error => {
        event.onError(error);
      })
      // --- 开启通知声音播放 -----------------------
      // 浏览器禁止开发者滥用播放给用户制造噪音
      // 要求用户主动进行交互后，可以播放
      // 登录点击的时候，播放一个空声音，达到交互目的
      // 后续则可以自动播放声音了
      // document.createElement('audio').play();
      // -------------------------------------------
    },
    // 刷新验证码
    refresh() {
      this.t = 'client-' + Math.round(Math.random() * 1000) + new Date().getTime();
      this.$axios.get('manager/system/publish/captcha', { t: this.t }).then(data => {
        this.captcha = data.result;
      }).catch(e => {
        this.$message.error(e.message)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.login {
  .login-form {
    width: 300px;
    padding: 0 35px;
    border-left: solid 1px #eee;
  }
  .login-frame {
    background: #f0f2f5 url("../assets/image/login_bg.svg") no-repeat 50%;
    background-size: 100%;
  }
}
</style>