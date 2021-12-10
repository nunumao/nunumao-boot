<template>
  <div class="center-groups d-flex h-100" style="width:100%">
    <div class="flex-fluid">
      <d1-page title="角色" :apis="{prefix:'manager/system/groups/'}" permission="system/groups/" v-model:entity="entity" :rules="rules">
        <!-- 列表 -->
        <template v-slot:column>
          <el-table-column prop="name" label="角色名称">
          </el-table-column>
          <el-table-column label="状态" width="100">
            <template #default="scope">
              <span style="color:#dc3545" v-if="scope.row.status == 0">禁用</span>
              <span style="color:#28a745" v-if="scope.row.status == 1">启用</span>
            </template>
          </el-table-column>
          <el-table-column label="配置" width="100">
            <template #default="scope">
              <el-button type="text" @click="user(scope.row)">人员</el-button>
              <el-button type="text" @click="rule(scope.row)">权限</el-button>
            </template>
          </el-table-column>
        </template>
        <!-- 表单 -->
        <template v-slot:field>
          <el-form-item label="名称" prop="name">
            <el-input v-model="entity.name"></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="entity.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </template>
      </d1-page>
    </div>
    <div class="flex-fluid pl-10" v-if="showUser">
      <d1-page style="height:100%">
        <div class="h-100 d-flex flex-column">
          <div class="d-flex justify-content-end">
            <div class="close-user" @click="closeUser">
              <i class="el-icon-circle-close"></i>
            </div>
          </div>
          <div class="d-flex justify-content-between mb-10">
            <div>
              <el-button type="primary" icon="el-icon-search" v-permission="'system/groups/user'" @click="selectUser">增加用户</el-button>
            </div>
            <div class="d-flex">
              <div>
                <el-input v-model="userQuery.name" clearable placeholder="请输入查询条件"></el-input>
              </div>
              <div class="ml-10">
                <el-button type="primary" plain @click="userSearch">搜索</el-button>
              </div>
            </div>
          </div>
          <div class="flex-fluid">
            <el-table :data="userList.records" border>
              <el-table-column prop="account" label="账号" width="120">
              </el-table-column>
              <el-table-column prop="name" label="姓名">
              </el-table-column>
              <el-table-column prop="phone" label="手机" width="120">
              </el-table-column>
              <el-table-column fixed="right" label="操作" width="100">
                <template #default="scope">
                  <el-button type="text" v-permission="'system/groups/user'" @click="delUser(scope.row)">移除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <div v-if="userList.total > 0" class="mt-10 d-flex justify-content-center">
              <el-pagination layout="prev, pager, next" v-model:current-page="userList.current" :total="userList.total" @current-change="userPage"></el-pagination>
            </div>
          </div>
        </div>
      </d1-page>
    </div>
    <!-- 选择用户 -->
    <d1-user api="manager/system/groups/user-list" v-model:dialog="userDialog" @result="enterUser"></d1-user>
    <!-- 权限设置 -->
    <el-dialog custom-class="permission-dialog" title="权限配置" v-model="permissionDialog" :show-close="false" width="700px">
      <div class="permission d-flex justify-content-between">
        <div class="tree">
          <el-scrollbar>
            <div class="p-10">
              <div v-for="item in permission.list" :key="item.id">
                <div class="d-flex align-items-center">
                  <div>
                    <el-checkbox v-model="item.checked" :true-label="1" :false-label="0" @change="permissionChange(item,null)"></el-checkbox>
                  </div>
                  <div class="ml-10 pre-label" :style="{color: item.id == permission.listItem.id ? 'var(--el-color-primary)' : 'var(--el-text-color-regular)' }" @click="permissionAcion(item)">{{item.name}}</div>
                </div>
                <div class="ml-20" v-for="jtem in item.child" :key="jtem.id">
                  <div class="d-flex align-items-center">
                    <div>
                      <el-checkbox v-model="jtem.checked" :true-label="1" :false-label="0" @change="permissionChange(jtem,item)"></el-checkbox>
                    </div>
                    <div class="ml-10 pre-label" :style="{color: jtem.id == permission.listItem.id ? 'var(--el-color-primary)' : 'var(--el-text-color-regular)' }" @click="permissionAcion(jtem)">{{jtem.name}}</div>
                  </div>
                  <div class="ml-20" v-for="ktem in jtem.child" :key="ktem.id">
                    <div class="d-flex align-items-center">
                      <div>
                        <el-checkbox v-model="ktem.checked" :true-label="1" :false-label="0" @change="permissionChange(ktem,jtem)"></el-checkbox>
                      </div>
                      <div class="ml-10 pre-label" :style="{color: ktem.id == permission.listItem.id ? 'var(--el-color-primary)' : 'var(--el-text-color-regular)' }" @click="permissionAcion(ktem)">{{ktem.name}}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>
        <div class="action d-flex flex-column justify-content-between">
          <div class="action-main px-10 flex-fluid mb-10">
            <el-tabs v-model="permission.activeAction">
              <el-tab-pane label="操作权限">
                <el-empty v-if="permission.actionList.length<=0" :image-size="100" description="无可选操作权限"></el-empty>
                <el-checkbox-group v-model="permission.listItem.select" @change="permissionAcionChange">
                  <div class="d-flex flex-wrap action-check-group">
                    <div v-for="item in permission.actionList" :key="item.id">
                      <el-checkbox :label="item.id">{{item.name}}</el-checkbox>
                    </div>
                  </div>
                </el-checkbox-group>
              </el-tab-pane>
              <el-tab-pane label="数据权限">
                <el-empty v-if="permission.ruleList.length<=0" :image-size="100" description="无可选数据规则"></el-empty>
                <el-checkbox-group v-model="permission.listItem.dataSelect" @change="dataRuleChange">
                  <div class="d-flex flex-wrap action-check-group">
                    <div v-for="item in permission.ruleList" :key="item.id">
                      <el-checkbox :label="item.id">{{item.name}}</el-checkbox>
                    </div>
                  </div>
                </el-checkbox-group>
              </el-tab-pane>
            </el-tabs>
          </div>
          <div class="action-footer d-flex justify-content-end">
            <el-button @click="closeRule">取消</el-button>
            <el-button type="primary" v-permission="'system/groups/permission'" @click="submitPermission">确定</el-button>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      conditon: {
        search: ''
      },
      entity: {
        name: null,
        status: 1
      },
      rules: {
        name: [{
          required: true,
          message: '请输入名称',
          trigger: 'blur'
        }]
      },
      // --- 分组用户 ---------------
      showUser: false,
      userQuery: {
        id: null,
        name: ''
      },
      userList: {
        records: [],
        current: 1,
        total: 0
      },
      userDialog: false,
      // --- 权限 ------------------
      permissionDialog: false,
      permission: {
        id: null,
        activeAction: '0',
        actionList: [],
        list: [],
        ruleList: [],
        listItem: {
          select: [],
          dataSelect: []
        },
        auth: [],
        authData: []
      }
    }
  },
  mounted() {
  },
  methods: {
    async user(row) {
      this.userList = [];
      this.showUser = true;
      this.userQuery.id = row.id;
      this.loadUser();
    },
    userSearch() {
      this.loadUser();
    },
    userPage() {
      this.loadUser();
    },
    loadUser() {
      this.$axios.get('manager/system/groups/groupsUser', { id: this.userQuery.id, name: this.userQuery.name }).then(data => {
        this.userList = data.result;
      }).catch(e => {
        this.userList = [];
      });
    },
    closeUser() {
      this.showUser = false;
    },
    // 选择用户
    selectUser() {
      this.userDialog = true;
    },
    // 选择已有用户回调
    enterUser(user) {
      let params = {
        groups: this.userQuery.id,
        user: user.id
      }
      this.$axios.post('manager/system/groups/addGroupsUser', params).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.loadUser();
          }
        })
      }).catch(e => {
        this.$message.error(e)
      })
    },
    // --- 权限操作 ----------------------
    rule(row) {
      this.permission.id = row.id;
      this.permissionDialog = true;
      this.permission.actionList = [];
      this.permission.ruleList = [];
      this.$axios.get('manager/system/groupsRule/list').then(async data => {
        let hasData = await this.$axios.get('manager/system/groupsRule/rule', { id: row.id });
        let auth = [];
        let authData = [];
        if (hasData) {
          auth = hasData.result.rule;
          authData = hasData.result.data;
        }
        this.permission.auth = auth;
        this.permission.authData = authData;
        this.loopRebuild(data.result);
        this.permission.list = data.result;
      })
    },
    closeRule() {
      this.permission.id = null;
      this.permission.actionList = [];
      this.permission.list = [];
      this.permission.listItem = {
        select: [],
        dataSelect: []
      }
      this.permission.auth = [];
      this.permissionDialog = false;
    },
    // 递归重建树形数据 将父级设置到子级的 parent属性上
    loopRebuild(tree) {
      if (Array.isArray(tree)) {
        tree.forEach(vi => {
          vi.checked = 0;
          if (this.permission.auth.includes(vi.id)) {
            vi.checked = 1;
          }
          if (Array.isArray(vi.child)) {
            vi.child.forEach(vj => {
              vj.checked = 0;
              vj.parent = vi;
            })
          }
          this.loopRebuild(vi.child);
        })
      }
    },
    permissionChange(data, parent) {
      if (parent) {
        // 选中上级
        if (1 === data.checked) {
          parent.checked = 1;
        } else {
          data.select = [];
          data.dataSelect = [];
          // 检查下级的选中状态
          if (parent.child && Array.isArray(parent.child)) {
            if (parent.child.filter(v => v.checked == 1).length <= 0) {
              parent.checked = 0;
            }
          }
        }
      }
      if (0 === data.checked) {
        data.select = [];
        data.dataSelect = [];
      }
      // 递归选中下级
      this.loopCheckChild(data, data.checked);
      // 递归选中上级
      this.loopCheckParent(parent);
    },
    loopCheckChild(data, flag) {
      if (data.child && Array.isArray(data.child)) {
        data.child.forEach(value => {
          value.checked = flag;
          if (0 === flag) {
            value.select = [];
            value.dataSelect = [];
          }
          this.loopCheckChild(value, flag);
        });
      }
    },
    loopCheckParent(data) {
      if (!data || !data.parent) {
        return;
      }
      // 检查下级选中
      if (data.parent.child.filter(v => v.checked === 1).length > 0) {
        data.parent.checked = 1;
      } else {
        data.parent.checked = 0;
        data.parent.select = [];
        data.parent.dataSelect = [];
      }
      this.loopCheckParent(data.parent);
    },
    // 点击菜单权限
    permissionAcion(row) {
      if (!row.select || !row.dataSelect || row.checked == 0) {
        this.permission.auth = [];
        this.permission.authData = [];
        row.select = [];
        row.dataSelect = [];
      }
      this.permission.listItem = row;
      // 加载操作权限
      this.$axios.get('manager/system/groupsRule/action', { id: row.id }).then(data => {
        if (this.permission.listItem.checked === 1) {
          data.result.forEach(item => {
            if (this.permission.auth.includes(item.id)) {
              this.permission.listItem.select.push(item.id);
            }
          })
        }
        this.permission.actionList = data.result;
      }).catch(e => {
        this.permission.actionList = [];
      })
      // 加载数据规则
      this.$axios.get('manager/system/groupsRule/dataRule', { id: row.id }).then(data => {
        if (this.permission.listItem.checked === 1) {
          data.result.forEach(item => {
            if (this.permission.authData.includes(item.id)) {
              this.permission.listItem.dataSelect.push(item.id);
            }
          })
        }
        this.permission.ruleList = data.result;
      }).catch(e => {
        this.permission.ruleList = [];
      })
    },
    permissionAcionChange(value) {
      if (value.length > 0) {
        this.permission.listItem.checked = 1;
        this.loopCheckParent(this.permission.listItem);
      }
    },
    dataRuleChange(value) {
      if (value.length > 0) {
        this.permission.listItem.checked = 1;
        this.loopCheckParent(this.permission.listItem);
      }
    },
    // 权限保存
    submitPermission() {
      // 权限参数
      let rule = new Set();
      // 数据规则
      let dataRule = new Set();
      let loopCheck = list => {
        if (Array.isArray(list)) {
          list.forEach(vi => {
            if (vi.checked == 1) {
              rule.add(vi.id);
              if (Array.isArray(vi.select) && vi.select.length > 0) {
                vi.select.forEach(ai => {
                  rule.add(ai);
                })
              }
              if (Array.isArray(vi.dataSelect) && vi.dataSelect.length > 0) {
                vi.dataSelect.forEach(ai => {
                  dataRule.add(ai);
                })
              }
            }
            if (vi.child && Array.isArray(vi.child)) {
              loopCheck(vi.child);
            }
          })
        }
      }
      loopCheck(this.permission.list);
      this.$axios.put('manager/system/groupsRule/edit', { id: this.permission.id, rule: [...rule], data: [...dataRule] }).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.closeRule();
          }
        })
      }).catch(e => {
        this.$message.error(e);
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.center-groups {
  & > div {
    width: 0;
  }
  .close-user {
    height: 40px;
    cursor: pointer;
  }
  .permission {
    height: 350px;
    .tree {
      flex: 1;
      border: solid 1px #eee;
      .tree-top {
        border-bottom: solid 1px #eee;
      }
      .pre-label {
        cursor: pointer;
      }
    }
    .action {
      width: 240px;
      margin-left: 10px;
      &-main {
        border: solid 1px #eee;
        .action-check-group {
          & > div {
            flex: 0 0 50%;
          }
        }
      }
    }
  }
}
</style>
<style lang="scss">
.center-groups {
  .permission-dialog {
    .el-dialog__header {
      border-bottom: solid 1px #eee;
      padding: 10px 15px !important;
    }
    .el-dialog__body {
      padding: 10px 15px !important;
    }
  }
}
</style>