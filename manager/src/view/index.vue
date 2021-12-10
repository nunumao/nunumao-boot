<template>
  <div class="h-100">
    <d1-layout class="index" :msg="msg" :profile="profile" @command="command" @notify="notify" @msg-click="msgClick"></d1-layout>
    <el-dialog v-model="dialog" title="修改密码" width="420px" :before-close="close">
      <el-form ref="form" :model="entity" :rules="rules" label-width="100px">
        <el-form-item label="旧的密码" prop="oldPwd">
          <el-input v-model="entity.oldPwd" show-password placeholder="请输入旧的密码"></el-input>
        </el-form-item>
        <el-form-item label="新的密码" prop="password">
          <el-input v-model="entity.password" show-password placeholder="请输入新的密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="repassword">
          <el-input v-model="entity.repassword" show-password placeholder="请输入确认密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-dialog v-model="msgDialog" title="消息详情" width="600px">
      <div class="d-flex mb-10">
        <div class="mr-10 lab">消息标题</div>
        <div>{{msgDetail.name}}</div>
      </div>
      <div class="d-flex mb-10">
        <div class="mr-10 lab">消息时间</div>
        <div>{{msgDetail.createTime}}</div>
      </div>
      <div class="d-flex mb-10">
        <div class="mr-10 lab">消息简介</div>
        <div>{{msgDetail.intro}}</div>
      </div>
      <div class="d-flex mb-10">
        <div class="mr-10 lab">消息内容</div>
        <div>{{msgDetail.content}}</div>
      </div>
    </el-dialog>
    <audio id="notify">
      <source src="../assets/audio/notify.mp3" type="audio/mp3">
    </audio>
  </div>
</template>
<script>
export default {
  data() {
    return {
      dialog: false,
      msgDialog: false,
      entity: {
        oldPwd: '',
        password: ''
      },
      profile: {

      },
      msg: [],
      msgDetail: {},
      rules: {
        oldPwd: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }],
        repassword: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (value !== this.entity.password) {
              callback(new Error('两次输入密码不一致'));
            } else {
              callback();
            }
          },
          trigger: 'blur'
        }]
      },
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.loadMessage();
      this.loadProfile();
    },
    loadProfile() {
      this.$axios.get('manager/system/profile/detail').then(data => {
        this.profile = {
          name: data.result.name,
          avatar: this.$config.STATIC_PATH + data.result.avatar
        };
      }).catch(e => { })
    },
    notify(data) {
      let audio = this.$el.querySelector('#notify');
      audio.play();
      let msg = JSON.parse(data);
      this.$notify({
        title: msg.name,
        message: msg.intro
      })
      this.loadMessage();
    },
    command(index) {      
      switch (index) {
        case 'account':
        case 'profile':
          this.$router.push({
            path: '/profile/info'
          })
          break;
        case 'password':
          this.dialog = true;
          break;
        case 'cache':
          this.$message.success('缓存已清空...')
          break;
      }
    },
    close() {
      this.dialog = false;
    },
    msgClick(msg) {
      this.openMessage(msg.id);
    },
    loadMessage() {
      this.$axios.get('manager/system/message/noRead').then(data => {
        this.msg = data.result;
      }).catch(e => {
        this.msg = []
      })
    },
    openMessage(id) {
      this.detail = {};
      this.msgDialog = true;
      this.$axios.get('manager/system/message/detail', { id: id }).then(data => {
        this.msgDetail = data.result;
        this.loadMessage();
      }).catch(e => { })
    },
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.$axios.put('manager/system/profile/password', this.entity).then(data => {
            this.$message({
              message: data.message,
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.dialog = false;
              }
            })
          }).catch(e => {
            this.$message.error(e)
          })
        }
      })
    }
  }
}
</script>