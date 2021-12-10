<template>
  <d1-page class="system-user" title="用户" :apis="{prefix:'manager/system/user/'}" permission="system/user/" v-model:entity="entity" :rules="rules" @add="add" @after-detail="afterDetail">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column prop="account" label="账号" width="200">
      </el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column prop="phone" label="手机号" width="120">
      </el-table-column>
      <el-table-column prop="lastLoginIp" label="最后登录IP" width="130">
      </el-table-column>
      <el-table-column prop="lastLoginTime" label="最后登录时间" width="170">
      </el-table-column>
      <el-table-column label="状态" width="60">
        <template #default="scope">
          <span style="color:#dc3545" v-if="scope.row.status == 0">禁用</span>
          <span style="color:#28a745" v-if="scope.row.status == 1">启用</span>
        </template>
      </el-table-column>
      <el-table-column prop="loginCount" label="详细信息" width="90">
        <template #default="scope">
          <el-button type="text" v-permission="'system/user/detail'" @click="showDetail(scope.row)">查看详情</el-button>
        </template>
      </el-table-column>
    </template>

    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="用户账号" prop="account">
        <el-input v-model="entity.account" :disabled="entity.id?true:false" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="用户姓名" prop="name">
        <el-input v-model="entity.name" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="用户身份" prop="name">
        <el-checkbox-group v-model="temp.role" @change="roleChange">
          <el-checkbox :label="1" disabled>普通用户</el-checkbox>
          <el-checkbox :label="2">商户用户</el-checkbox>
          <el-checkbox :label="4">员工用户</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="entity.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="登录密码" prop="password">
        <el-input v-model="entity.password" show-password :placeholder="entity.id?'无修改可留空':'请输入密码'"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="repassword">
        <el-input v-model="entity.repassword" show-password :placeholder="entity.id?'无修改可留空':'请输入确认密码'"></el-input>
      </el-form-item>
      <el-form-item label="工号" prop="workNo">
        <el-input v-model="entity.workNo" placeholder="请输入工号"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <d1-upload v-model:value="entity.avatar"></d1-upload>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <div class="email">
          <el-autocomplete v-model="entity.email" placeholder="请输入邮箱" :fetch-suggestions="emailSuggest" :trigger-on-focus="false"></el-autocomplete>
        </div>
      </el-form-item>
      <el-form-item label="所任职务" prop="position">
        <d1-position v-model:value="entity.position" api="manager/system/user"></d1-position>
      </el-form-item>
      <el-form-item label="角色分配" prop="groups">
        <d1-role v-model:value="entity.groups" api="manager/system/user"></d1-role>
      </el-form-item>
      <el-form-item label="部门分配" prop="depart">
        <d1-depart v-model:value="entity.depart" api="manager/system/user"></d1-depart>
      </el-form-item>
      <!-- <el-form-item label="部门身份" prop="leader">
        <el-radio-group v-model="entity.leader">
          <el-radio :label="0">员工</el-radio>
          <el-radio :label="1">领导</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="entity.leader == 1" label="负责部门" prop="depart">
        <el-input v-model="entity.depart" readonly placeholder="请选择部门">
          <template #suffix>
            <i class="iconfont icon-bumen icon-input-suffix"></i>
          </template>
        </el-input>
      </el-form-item> -->
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="entity.birthday" type="date" :editable="false" :clearable="true" placeholder="请选择生日" @change="birthdayChange">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="entity.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </template>

    <template #attach>
      <!-- 详情 -->
      <el-drawer title="详情信息" v-model="detailDrawer" direction="rtl" destroy-on-close size="550px" @opened="drawerOpend">
        <div class="user-detail d-flex flex-column">
          <div class="user-detail-info">
            <table class="table table-bordered">
              <tr>
                <td class="bg" width="100">用户账号</td>
                <td colspan="2">{{detail.account}}</td>
                <td width="100" align="center" rowspan="2">
                  <img v-if="!detail.avatar" class="face" src="../../assets/image/default_face.jpg">
                  <img v-if="detail.avatar" class="face" :src="$config.STATIC_PATH+detail.avatar">
                </td>
              </tr>
              <tr>
                <td class="bg">用户姓名</td>
                <td colspan="2">{{detail.name}}</td>
              </tr>
              <tr>
                <td class="bg">性别</td>
                <td>{{detail.gender==0?'未知':detail.gender==1?'男':'女'}}</td>
                <td class="bg" width="100">生日</td>
                <td width="155">{{detail.birthday || '未填写'}}</td>
              </tr>
              <tr>
                <td class="bg">联系方式</td>
                <td>{{detail.phone}}</td>
                <td class="bg">E-mail</td>
                <td width="155">{{detail.email}}</td>
              </tr>
              <tr>
                <td class="bg">登录次数</td>
                <td>{{detail.loginCount}}</td>
                <td class="bg">创建时间</td>
                <td width="160">{{detail.createTime}}</td>
              </tr>
              <tr>
                <td class="bg" width="100">最近登录</td>
                <td colspan="3">
                  <template v-if="detail.lastLoginTime && detail.lastLoginIp">
                    {{detail.lastLoginTime}} / {{detail.lastLoginIp}} ({{detail.lastLoginCity||'未知'}})
                  </template>
                  <template v-else>
                    从未登录
                  </template>
                </td>
              </tr>
            </table>
          </div>
          <div class="user-detail-dept mt-10">
            <table class="table table-role table-bordered">
              <tr>
                <td class="bg" width="100">工号</td>
                <td width="*">{{detail.workNo||'无'}}</td>
                <td class="bg" width="100">职务</td>
                <td width="*">无</td>
              </tr>
              <tr>
                <td class="bg" width="100">所属角色</td>
                <td width="*">{{detail.groupsName||'无'}}</td>
                <td class="bg" width="100">负责部门</td>
                <td width="*">无</td>
              </tr>
              <tr>
                <td class="bg" width="100">所属部门</td>
                <td colspan="3">{{(detail.departNames?detail.departNames.join('|'):'')||'无'}}</td>
              </tr>
            </table>
          </div>
          <div class="user-detail-log mt-10">
            <el-tabs type="border-card">
              <el-tab-pane label="用户活跃">
                <div id="line" class="line"></div>
              </el-tab-pane>
              <el-tab-pane label="行为日志">
                <div>
                  <el-table :data="log.records" border height="250">
                    <el-table-column prop="content" label="日志内容" />
                    <el-table-column prop="createTime" label="日志时间" width="170" />
                  </el-table>
                </div>
                <div v-if="log.total > 0" class="mt-10 d-flex justify-content-center">
                  <el-pagination layout="prev, pager, next" v-model:current-page="log.current" :total="log.total" @current-change="logPage"></el-pagination>
                </div>
              </el-tab-pane>
            </el-tabs>
            <!-- <el-scrollbar height="100%">
              <p class="item" v-for="item in 80">{{ item }}</p>
            </el-scrollbar> -->
          </div>
        </div>
      </el-drawer>
    </template>

  </d1-page>
</template>
<script>
import * as echarts from 'echarts/core'
import { GridComponent, LegendComponent } from 'echarts/components'
import { BarChart, LineChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

export default {
  data() {
    return {
      detailDrawer: false,
      entity: {
        name: null,
        role: [1],
        avatar: null,
        position: '',
        depart: [],
        leader: 0,
        status: 1
      },
      temp: {
        role: [1]
      },
      detail: {
        avatar: ''
      },
      log: {
        records: [],
        current: 1,
        total: 0
      },
      emailSuggest: (query, cb) => {
        query = query.replace(/@\S*/, '');
        let provides = [
          '@qq.com',
          '@163.com',
          '@gmail.com',
          '@sina.com',
          '@189.com',
          '@126.com'
        ];
        let result = provides.map(v => {
          let email = query + v;
          return { value: email };
        });
        cb(result);
      },
      pageAction: true,
      rules: {
        account: [{
          required: true,
          message: '请输入账号'
        }, {
          pattern: /^[a-zA-Z0-9]+$/,
          message: '格式为数字或字母',
          trigger: 'blur'
        }, {
          validator: (rule, value, callback) => {
            if (this.entity.id) {
              callback();
            } else {
              this.$axios.get('manager/system/user/checkAccount', { account: value }).then(data => {
                callback();
              }).catch(e => {
                callback(new Error('账号已存在'));
              })
            }
          },
          trigger: 'blur'
        }],
        phone: [{
          required: true,
          message: '请输入手机号',
          trigger: 'blur'
        }, {
          pattern: /^1\d{10}$/,
          message: '手机号格式错误',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入姓名',
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
        }],
        workNo: [{
          required: true,
          message: '请输入工号',
          trigger: 'blur'
        }, {
          pattern: /^[a-zA-Z0-9]+$/,
          message: '格式为数字或字母',
          trigger: 'blur'
        }],
        // 邮箱
        email: [{
          type: 'email',
          message: '邮箱格式错误'
        }]
      }
    }
  },
  mounted() {
    echarts.use(
      [GridComponent, LegendComponent, BarChart, LineChart, CanvasRenderer]
    );
  },
  methods: {
    add() {
      this.rules.password[0].required = true
      this.rules.repassword[0].required = true
      this.temp.role = [1];
    },
    afterDetail() {
      this.rules.password[0].required = false
      this.rules.repassword[0].required = false

      let binary = this.entity.role.toString(2);
      let arr = [];
      for (let i = 0; i < binary.length; i++) {
        let r = this.entity.role & (0x01 << i);
        if (r != 0) {
          arr.push(r);
        }
      }
      this.temp.role = arr;
      this.entity.role = arr;

      if (!this.entity.chief) {
        this.entity.leader = 0;
      }

    },
    roleChange(data) {
      this.entity.role = data;
    },
    birthdayChange(date) {
      this.entity.birthday = date.format('YYYY-MM-DD');
    },
    drawerOpend() {
      // echarts.dispose(document.getElementById("line"));
      let option = {
        grid: {
          left: 0,
          right: 0,
          bottom: 0,
          containLabel: true
        },
        legend: {
          data: ['登录次数', '在线时长']
        },
        xAxis: [
          {
            type: 'category',
            data: ['9-10', '9-11', '9-12', '9-13', '9-14', '9-15', '9-16'],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '登录次数',
            min: 0,
            max: 20,
            interval: 2,
            axisLabel: {
              formatter: '{value} 次'
            }
          },
          {
            type: 'value',
            name: '在线时长',
            min: 0,
            max: 10,
            interval: 2,
            axisLabel: {
              formatter: '{value} 小时'
            }
          }
        ],
        series: [
          {
            name: '登录次数',
            type: 'bar',
            data: [10, 5, 7, 13, 6, 2, 11]
          },
          {
            name: '在线时长',
            type: 'line',
            yAxisIndex: 1,
            data: [1, 5, 3, 4, 7, 9, 2]
          }
        ]
      };

      echarts.init(document.getElementById("line")).setOption(option);
    },
    showDetail(row) {
      this.detail = {
        avatar: ''
      };
      this.$axios.get('manager/system/user/detail', { id: row.id }).then(data => {
        if (data.result.birthday) {
          data.result.birthday = new Date(data.result.birthday).format('YYYY年MM月DD日');
        }
        this.detail = data.result;
        // 查询 IP 对应城市
        this.$axios.get('manager/system/user/cityAtIp', { ip: data.result.lastLoginIp }).then(city => {
          this.detail.lastLoginCity = city.result;
        }).catch(ex => { })
        // 查询用户行为日志
        this.loadLog(row.id);
      }).then(e => { })
      this.detailDrawer = true;
    },
    loadLog(id) {
      this.$axios.get('manager/system/user/log', { id: id, page: this.log.current }).then(log => {
        this.log = log.result;
      }).catch(ex => {
        this.log = {
          records: [],
          current: 1,
          size: 0
        }
      })
    },
    logPage() {
      this.loadLog(this.detail.id);
    }
  }
}
</script>
<style lang="scss" scoped>
.system-user {
  .user-detail {
    // border: solid 1px #eee;
    height: calc(100vh - 90px);
    table {
      &.table-role {
        width: 100%;
        table-layout: fixed;
      }
      td {
        padding: 10px;
        font-size: 14px;
        color: var(--el-text-color-regular);
        &.bg {
          background: #f5f7fa;
        }
        .face {
          $size: 58px;
          width: $size;
          height: $size;
          border-radius: 2px;
        }
      }
    }
    .line {
      width: 100%;
      height: 250px;
    }
  }
}
</style>
<style lang="scss">
.system-user {
  .user-detail {
    .el-tabs--border-card {
      box-shadow: none !important;
    }
  }
  .email {
    $width: 250px;
    width: $width;
    .el-input,
    .el-autocomplete {
      width: $width !important;
    }
  }
}
</style>