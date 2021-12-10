<template>
  <div class="profile h-100 d-flex justify-content-between align-items-stretch">
    <div class="profile-info">
      <d1-page>
        <div class="d-flex justify-content-center py-30">
          <el-image style="width: 100px; height: 100px;border-radius: 50%" fit="cover" src="">
            <template #placeholder>
              <img src="../../assets/image/default_face.jpg">
            </template>
            <template #error>
              <img style="width: 100px; height: 100px;border-radius: 50%" src="../../assets/image/default_face.jpg">
            </template>
          </el-image>
        </div>
        <div class="px-30">
          <div class="d-flex">
            <div class="mr-10">
              <i class="el-icon-user"></i>
            </div>
            <div>{{detail.name}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <svg style="color:var(--el-text-color-secondary)" viewBox="0 0 1024 1024" width="16" height="16">
                <path d="M402.1 730.3L286.5 845.9 170.9 730.3c-3.9-3.9-10.2-3.9-14.1 0-3.9 3.9-3.9 10.2 0 14.1L279.4 867c2 2 4.5 2.9 7.1 2.9s5.1-1 7.1-2.9l122.6-122.6c3.9-3.9 3.9-10.2 0-14.1-3.9-3.9-10.2-3.9-14.1 0z" p-id="969"></path>
                <path d="M286.5 155C185.3 155 103 237.3 103 338.5c0 97.8 76.9 178 173.5 183.2v302.5c0 5.5 4.5 10 10 10s10-4.5 10-10V521.6C393 516.4 470 436.3 470 338.5 470 237.3 387.7 155 286.5 155z m0 346.9c-90.1 0-163.5-73.3-163.5-163.5S196.4 175 286.5 175 450 248.3 450 338.5s-73.4 163.4-163.5 163.4zM747.5 503.4V313.8h76.7c5.5 0 10-4.5 10-10s-4.5-10-10-10h-76.7V201c0-5.5-4.5-10-10-10s-10 4.5-10 10v92.8h-76.7c-5.5 0-10 4.5-10 10s4.5 10 10 10h76.7v189.6C631 508.6 554 588.8 554 686.6 554 787.7 636.3 870 737.5 870S921 787.7 921 686.5c-0.1-97.7-77-177.9-173.5-183.1z m-10 346.6C647.4 850 574 776.7 574 686.6s73.3-163.5 163.5-163.5S901 596.4 901 686.6 827.6 850 737.5 850z" p-id="970"></path>
              </svg>
            </div>
            <div>{{detail.gender==0?'未知':detail.gender==1?'男':'女'}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i class="el-icon-postcard"></i>
            </div>
            <div>{{detail.workNo}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i class="el-icon-mobile-phone"></i>
            </div>
            <div>{{detail.phone}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i class="el-icon-message"></i>
            </div>
            <div>{{detail.email||'未填写'}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i class="el-icon-magic-stick"></i>
            </div>
            <div>{{detail.birthday||'保密~'}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i style="transform:rotate(0deg)" class="iconfont icon-zuzhijigou"></i>
            </div>
            <div>{{detail.departShow}}</div>
          </div>
          <div class="d-flex mt-10">
            <div class="mr-10">
              <i class="el-icon-location-information"></i>
            </div>
            <div>广西 • 南宁</div>
          </div>
        </div>
        <div class="pt-30">
          <el-divider>标签</el-divider>
          <div>
            <el-tag>标签一</el-tag>
            <el-tag type="success">技术大神</el-tag>
            <el-tag type="info">积极员工</el-tag>
            <el-tag type="warning">标签四</el-tag>
            <el-tag type="danger">标签五</el-tag>
          </div>
        </div>
      </d1-page>
    </div>
    <div class="profile-main flex-fluid">
      <d1-page>
        <el-tabs v-model="active">
          <el-tab-pane label="我的资料" name="0">
            <div class="pb-10"></div>
            <el-form ref="form" :model="entity" :rules="rules" label-width="100px">
              <el-form-item label="账号">
                <el-input v-model="entity.account" readonly></el-input>
              </el-form-item>
              <el-form-item label="姓名" prop="name">
                <el-input v-model="entity.name" placeholder="请输入姓名"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-radio-group v-model="entity.gender">
                  <el-radio :label="1">男</el-radio>
                  <el-radio :label="2">女</el-radio>
                  <el-radio :label="0">保密</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="头像" prop="avatar">
                <d1-upload v-model:value="entity.avatar"></d1-upload>
              </el-form-item>
              <el-form-item label="工号">
                <el-input v-model="entity.workNo" readonly></el-input>
              </el-form-item>
              <el-form-item label="所属角色" prop="password">
                <el-input v-model="entity.groups" readonly></el-input>
              </el-form-item>
              <el-form-item label="所属部门" prop="repassword">
                <el-input v-model="entity.departShow" readonly></el-input>
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <div class="email">
                  <el-autocomplete v-model="entity.email" placeholder="请输入邮箱" :fetch-suggestions="emailSuggest" :trigger-on-focus="false"></el-autocomplete>
                </div>
              </el-form-item>
              <el-form-item label="生日" prop="birthday">
                <el-date-picker v-model="entity.birthday" type="date" :editable="false" :clearable="true" placeholder="请选择生日" @change="birthdayChange">
                </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" v-permission="'profile/info/edit'" @click="submit">保存</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="账号安全" name="1">
            <div class="pb-10"></div>
            <el-form ref="formSec" :model="security" :rules="rules" label-width="100px">
              <el-form-item label="旧的密码" prop="oldPwd">
                <el-input v-model="security.oldPwd" show-password placeholder="无修改可留空"></el-input>
              </el-form-item>
              <el-form-item label="新的密码" prop="password">
                <el-input v-model="security.password" show-password placeholder="无修改可留空"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="repassword">
                <el-input v-model="security.repassword" show-password placeholder="无修改可留空"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="phone">
                <el-input v-model="security.phone" placeholder="请输入手机号"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitPwd">保存</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="我的权限" name="2">
            <div>
              <el-table :data="permission" border style="width: 100%" row-key="id" :tree-props="{children: 'child', hasChildren: true}">
                <el-table-column prop="name" label="名称">
                </el-table-column>
                <el-table-column label="类型" width="120">
                  <template #default="scope">
                    <span style="color:#007bff" v-if="scope.row.type == 0">模块</span>
                    <span style="color:#dc3545" v-if="scope.row.type == 1">菜单</span>
                    <span style="color:#28a745" v-if="scope.row.type == 2">操作</span>
                  </template>
                </el-table-column>
                <el-table-column label="状态" width="80">
                  <template #default="scope">
                    <el-tag type="success" v-if="scope.row.id">√</el-tag>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-tab-pane>
          <el-tab-pane label="操作日志" name="3">
            <div>
              <el-table :data="log.records" border>
                <el-table-column type="expand" fixed="left">
                  <template #default="scope">
                    <div class="pl-50">
                      <div class="d-flex align-items-center">
                        <div class="pr-10">请求地址</div>
                        <el-tag style="white-space:normal;height:auto">{{scope.row.url}}</el-tag>
                      </div>
                      <div class="mt-10 d-flex align-items-start pr-10">
                        <div class="pr-10" style="margin-top:5px">请求参数</div>
                        <div class="flex-fluid">
                          <el-tag type="danger" style="white-space:normal;height:auto">{{scope.row.params}}</el-tag>
                        </div>
                      </div>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column prop="content" label="操作内容">
                </el-table-column>
                <el-table-column label="操作类型" width="80">
                  <template #default="scope">
                    <span style="color:var(--el-color-primary)" v-if="scope.row.action.toUpperCase() == 'GET'">查询数据</span>
                    <span style="color:var(--el-color-success)" v-if="scope.row.action.toUpperCase() == 'POST'">新增数据</span>
                    <span style="color:var(--el-color-warning)" v-if="scope.row.action.toUpperCase() == 'PUT'">编辑数据</span>
                    <span style="color:var(--el-color-danger)" v-if="scope.row.action.toUpperCase() == 'DELETE'">删除数据</span>
                  </template>
                </el-table-column>
                <el-table-column prop="ip" label="IP" width="120">
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="170">
                </el-table-column>
              </el-table>
              <div v-if="log.total > 0" class="d-flex justify-content-end mt-10">
                <div>
                  <el-pagination v-model:current-page="log.current" v-model:page-size="log.size" :total="log.total" @current-change="page" @size-change="size" layout="total, sizes, prev, pager, next, jumper">
                  </el-pagination>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </d1-page>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      active: '0',
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
      entity: {
        avatar: '',
        gender: 0
      },
      security: {
        phone: ''
      },
      detail: {

      },
      rules: {
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }],
        oldPwd: [{
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }],
        password: [{
          required: false,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }],
        repassword: [{
          required: false,
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
        // 邮箱
        email: [{
          type: 'email',
          message: '邮箱格式错误'
        }]
      },
      permission: [],
      log: {
        records: [],
        current: 1,
        size: 10
      }
    }
  },
  watch: {
    'security.oldPwd': function (v) {
      if (v) {
        this.rules.password[0].required = true;
        this.rules.repassword[0].required = true;
      } else {
        this.rules.password[0].required = false;
        this.rules.repassword[0].required = false;
      }
    },
    'security.password': function (v) {
      if (v) {
        this.rules.password[0].required = true;
        this.rules.repassword[0].required = true;
      } else {
        this.rules.password[0].required = false;
        this.rules.repassword[0].required = false;
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.loadDetail();
      this.loadLog();
      this.loadPermission();
    },
    loadDetail() {
      this.$axios.get('manager/system/profile/detail').then(data => {
        data.result.groups = data.result.groups || '无角色';
        data.result.departShow = data.result.depart.length > 0 ? data.result.depart.join('|') : '无部门';
        this.detail = Object.assign({}, data.result);
        this.security.phone = this.detail.phone;
        this.entity = data.result;
      }).catch(e => { })
    },
    birthdayChange(date) {
      this.entity.birthday = date.format('YYYY-MM-DD');
    },
    loadLog() {
      this.$axios.get('manager/system/profile/log', { page: this.log.current, size: this.log.size }).then(data => {
        this.log = data.result;
      }).catch(e => { })
    },
    loadPermission() {
      this.$axios.get('manager/system/profile/permission').then(data => {
        this.permission = data.result;
      })
    },
    page() {
      this.loadLog();
    },
    size() {
      this.loadLog();
    },
    submit() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.$axios.put('manager/system/profile/edit', this.entity).then(data => {
            this.$message({
              message: data.message,
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.loadDetail();
              }
            })
          }).catch(e => {
            this.$message.error(e)
          })
        }
      })
    },
    submitPwd() {
      this.$refs['formSec'].validate(valid => {
        if (valid) {
          this.$axios.put('manager/system/profile/password', this.security).then(data => {
            this.$message({
              message: data.message,
              type: 'success',
              duration: 1000,
              onClose: () => {
                this.loadDetail();
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
<style lang="scss" scoped>
.profile {
  &-info {
    margin-right: 5px;
    width: 260px;
  }
  &-main {
    margin-left: 5px;
    width: 0;
  }
}
</style>
<style lang="scss">
.profile {
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