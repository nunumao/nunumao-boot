<template>
  <div class="system-depart h-100 d-flex align-items-stretch">
    <div class="depart-tree">
      <d1-page ref="page" :modal="true" :apis="{prefix:'manager/system/depart/'}" permission="system/depart/" v-model:entity="entity" :rules="rules" @after-detail="afterDetail" @after-add="afterAdd" @after-edit="afterEdit">
        <!-- 列表 -->
        <template #search>
          <div></div>
        </template>
        <template #table>
          <el-table :data="list" border style="width: 100%" row-key="id" default-expand-all :tree-props="{children: 'child', hasChildren: true}" highlight-current-row @current-change="currentChange" @back="back">
            <el-table-column prop="name" label="名称">
            </el-table-column>
            <el-table-column label="类型" width="100">
              <template #default="scope">
                <span style="color:var(--el-color-success)" v-if="scope.row.type == 0">公司</span>
                <span style="color:var(--el-color-warning)" v-if="scope.row.type == 1">部门</span>
                <span style="color:var(--el-color-danger)" v-if="scope.row.type == 2">岗位</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right" width="100">
              <template #default="scope">
                <el-button type="text" v-permission="'system/depart/edit'" @click.stop="edit(scope.row)">编辑</el-button>
                <el-button type="text" v-permission="'system/depart/delete'" @click.stop="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <!-- 表单 -->
        <template v-slot:field>
          <el-form-item label="机构类型" prop="type">
            <el-radio-group v-model="entity.type" @change="typeChange">
              <el-radio :label="0">公司</el-radio>
              <el-radio :label="1">部门</el-radio>
              <el-radio :label="2">岗位</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-if="entity.type > 0" label="所属上级" prop="parentId">
            <el-select v-model="entity.parentId" placeholder="请选择">
              <template v-for="item in parent" :key="item.id">
                <el-option :label="item.name" :value="item.id" :disabled="item.type == 0 && entity.type == 2">
                  <div class="d-flex">
                    <div v-if="item.level > 0" style="padding-right:5px;color:var(--el-border-color-base);font-family: 'Consolas,Monaco,Andale Mono,Ubuntu Mono,monospace'" class="bxfy">
                      <template v-for="i in (item.level-1)" :key="i">&boxv;</template>
                      <!-- <span :style="{'padding-left':((item.level - 1) * 5)+'px'}"></span> -->
                      {{item.end ? '└ ':'├ '}}
                    </div>
                    <div>
                      {{item.name}}
                    </div>
                  </div>
                </el-option>
              </template>
            </el-select>
          </el-form-item>
          <el-form-item :label="entity.type == 0 ? '公司名称' : entity.type == 1 ? '部门名称' : '岗位名称'" prop="name">
            <el-input v-model="entity.name"></el-input>
          </el-form-item>
          <el-form-item v-if="entity.type < 2" label="办公电话" prop="telephone">
            <el-input v-model="entity.telephone"></el-input>
          </el-form-item>
          <el-form-item v-if="entity.type == 0" label="所在区域" prop="area">
            <d1-area api="manager/system/depart/area"></d1-area>
          </el-form-item>
          <el-form-item v-if="entity.type == 0" label="详细地址" prop="address">
            <d1-map></d1-map>
          </el-form-item>
        </template>
      </d1-page>
    </div>
    <div class="depart-auth">
      <d1-page style="height:100%">
        <el-tabs style="height:100%" type="border-card">
          <el-tab-pane label="人员列表">
            <template v-if="current">
              <div class="mb-10">
                <el-button type="primary" icon="el-icon-plus" v-permission="'system/depart/user'" @click="addUser">添加人员</el-button>
              </div>
              <el-table :data="userList" border>
                <el-table-column prop="account" label="账号" width="150">
                </el-table-column>
                <el-table-column prop="name" label="姓名">
                </el-table-column>
                <el-table-column prop="phone" label="手机" width="120">
                </el-table-column>
                <el-table-column label="操作" fixed="right" width="55">
                  <template #default="scope">
                    <el-button type="text" v-permission="'system/depart/user'" @click="deleteUser(scope.row.id)">删除</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <d1-user v-model:dialog="userDialog" api="manager/system/depart/user" @result="selectUser"></d1-user>
            </template>
            <template v-else>
              <el-empty description="请选择部门"></el-empty>
            </template>
          </el-tab-pane>
          <el-tab-pane label="配置权限">
            <template v-if="permission.list.length > 0">
              <div class="permission d-flex justify-content-between align-items-stretch h-100">
                <div class="tree d-flex flex-column">
                  <el-scrollbar style="height:0px;flex:auto">
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
                    <el-button type="primary" v-permission="'system/depart/rule'" @click="submitPermission">保存</el-button>
                  </div>
                </div>
              </div>
            </template>
            <template v-else>
              <el-empty description="请选择部门或配置上级权限"></el-empty>
            </template>
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
      list: [],
      parent: [],
      current: null,
      currentParent: null,
      userDialog: false,
      userList: [],
      entity: {
        type: 0
      },
      rules: {
        account: [{
          required: true,
          message: '请输入账号'
        }, {
          validator: (rule, value, callback) => {
            this.$axios.get('manager/user/checkAccount', { account: value }).then(data => {
              callback();
            }).catch(e => {
              callback(new Error('账号已存在'));
            })
          },
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        }],
        password: [{
          required: this.$route.query.id ? false : true,
          message: '请输入密码',
          trigger: 'blur'
        }, {
          min: 6,
          max: 20,
          message: '密码长度为6~20位',
          trigger: 'blur'
        }],
        repassword: [{
          required: this.$route.query.id ? false : true,
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
      permission: {
        id: null,
        activeAction: '0',
        actionList: [],
        ruleList: [],
        list: [],
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
    this.reset();
    this.init();
  },
  methods: {
    init() {
      this.$axios.get('manager/system/depart/list').then(data => {
        this.list = data.result;
      }).catch(e => this.list = [])
    },
    edit(data) {
      this.$refs.page.edit(data.id);
    },
    afterDetail(data) {
      this.entity.type = data.type;
      this.typeChange();
    },
    afterAdd() {
      this.init();
    },
    afterEdit() {
      this.init();
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/system/depart/delete', { id }).then(data => {
          this.$message({
            message: data.message,
            type: 'success',
            duration: 1000,
            onClose: () => {
              this.init();
              // 删除项与选中相等
              if (id == this.current) {
                this.userList = [];
              }
            }
          })
        }).catch(e => {
          this.$message.error(e)
        })
      }).catch(e => { })
    },
    typeChange() {
      if (this.entity.type == 0) {
        return;
      }
      this.$axios.get('manager/system/depart/parent').then(data => {
        let temp = [];
        let loop = (list, level = 0) => {
          list.forEach((item, index) => {
            item.level = level;
            if (index == list.length - 1) {
              item.end = true;
            } else {
              item.end = false;
            }
            temp.push(item);
            if (Array.isArray(item.child)) {
              loop(item.child, level + 1);
              item.child = "";
            }
          })
        }
        loop(data.result);
        this.parent = temp;
      }).catch(e => { })
    },
    currentChange(row) {
      this.current = row.id;
      this.permission.id = row.id;
      this.currentParent = row.parentId;
      // 查询人员
      this.$axios.get('manager/system/departUser/list', { departId: row.id }).then(data => {
        this.userList = data.result;
      }).catch(e => {
        this.userList = [];
      })
      // 查询权限     
      this.clearPermission();
      this.permission.actionList = [];
      this.permission.ruleList = [];
      this.$axios.get('manager/system/departRule/list', { parentId: row.parentId }).then(async data => {
        let hasData = await this.$axios.get('manager/system/departRule/rule', { id: row.id });
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
      }).catch(e => {
        this.permission.list = [];
      })
    },
    clearPermission() {
      this.permission.activeAction = '0';
      this.permission.actionList = [];
      this.permission.list = [];
      this.permission.listItem = {
        select: []
      }
      this.permission.auth = []
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
        row.select = [];
        row.dataSelect = [];
      }
      this.permission.listItem = row;
      // 加载操作权限
      this.$axios.get('manager/system/departRule/action', { id: row.id, departId: this.currentParent }).then(data => {
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
      this.$axios.get('manager/system/departRule/dataRule', { id: row.id, departId: this.currentParent }).then(data => {
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
      this.$axios.put('manager/system/departRule/edit', { id: this.permission.id, rule: [...rule], data: [...dataRule] }).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
          }
        })
      }).catch(e => {
        this.$message.error(e.message);
      })
    },
    addUser() {
      this.userDialog = true;
    },
    selectUser(user) {
      let userId = user.map(item => item.id);
      this.$axios.post('manager/system/departUser/add', { departId: this.current, userId }).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.currentChange({ id: this.current });
          }
        })
      }).catch(e => {
        this.$message.error(e);
      })
    },
    deleteUser(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/system/systemDepartUser/delete', { id }).then(data => {
          this.$message({
            message: data.message,
            type: 'success',
            duration: 1000,
            onClose: () => {
              this.currentChange({ id: this.current });
            }
          })
        }).catch(e => {
          this.$message.error(e);
        })
      }).catch(e => { })
    },
    currentAction(action) {
      if (action == 'add') {
        // this.reset();
      }
    },
    dataRuleChange(value) {
      if (value.length > 0) {
        this.permission.listItem.checked = 1;
        this.loopCheckParent(this.permission.listItem);
      }
    },
    reset() {
      this.entity = {
        type: 0,
        name: ''
      }
    },
    back() {
      console.log('back');
      this.$refs.page.back();
    }
  }
}
</script>
<style lang="scss" scoped>
.system-depart {
  width: 100%;
  .depart-tree {
    flex: 1;
    margin-right: 5px;
  }
  .depart-auth {
    flex: 1;
    margin-left: 5px;
  }
  .permission {
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
.system-depart {
  .el-tabs--border-card {
    box-shadow: none !important;
  }
  .el-tabs__content {
    height: calc(100% - 39px);
  }
  .el-tab-pane {
    height: 100%;
  }
}
</style>