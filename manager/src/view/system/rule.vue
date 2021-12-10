<template>
  <d1-page ref="page" class="system-rule" permission="system/rule/" @add="add">
    <template v-slot:search>
      <div></div>
    </template>
    <template v-slot:table>
      <el-table :data="list" border style="width: 100%" row-key="id" :tree-props="{children: 'child', hasChildren: true}">
        <el-table-column prop="name" label="名称">
        </el-table-column>
        <el-table-column label="类型" width="100">
          <template #default="scope">
            <span style="color:#007bff" v-if="scope.row.type == 0">模块</span>
            <span style="color:#dc3545" v-if="scope.row.type == 1">菜单</span>
            <span style="color:#28a745" v-if="scope.row.type == 2">操作</span>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="页面路径" width="200"></el-table-column>
        <el-table-column prop="sort" label="排序" width="80"></el-table-column>
        <el-table-column label="配置" width="100">
          <template #default="scope">
            <el-button type="text" v-permission="'system/rule/edit'" @click="openRule(scope.row.id)">数据规则</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="100">
          <template #default="scope">
            <el-button type="text" v-permission="'system/rule/edit'" @click="edit(scope.row.id)">编辑</el-button>
            <el-button type="text" v-permission="'system/rule/delete'" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-drawer v-model="dataRule.drawer" title="数据规则" direction="rtl" size="600px" :before-close="closeRule">
        <div>
          <div class=" mb-10">
            <el-button type="primary" @click="openDataDialog">新增</el-button>
          </div>
          <el-table :data="dataRule.list" border>
            <el-table-column prop="name" label="名称" width="130" />
            <el-table-column prop="field" label="字段" width="120" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="scope">
                {{['隐藏','等于'][scope.row.type]}}
              </template>
            </el-table-column>
            <el-table-column prop="value" label="值" />
            <el-table-column label="操作" width="100" fixed="right">
              <template #default="scope">
                <el-button type="text" @click="editDataRule(scope.row.id)">编辑</el-button>
                <el-button type="text" @click="deleteDataRule(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-drawer>
      <el-dialog title="数据规则" v-model="dataRule.dialog" width="600px">
        <div class="d-flex mb-10">
          <el-form ref="dataForm" :model="dataRule.entity" :rules="dataRule.rules" label-width="120px">
            <el-form-item label="规则名称" prop="name">
              <el-input v-model="dataRule.entity.name" placeholder="请输入规则名称"></el-input>
            </el-form-item>
            <el-form-item label="绑定接口" prop="apiId">
              <el-select v-model="dataRule.entity.apiId" style="width:250px !important" placeholder="请选择绑定接口">
                <el-option v-for="item in dataRule.apis" :key="item.id" :label="item.url" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="规则字段" prop="field">
              <el-input v-model="dataRule.entity.field" placeholder="请输入规则字段"></el-input>
            </el-form-item>
            <el-form-item label="条件类型" prop="type">
              <el-select v-model="dataRule.entity.type" placeholder="请选择条件类型">
                <el-option label="隐藏" :value="0"></el-option>
                <el-option label="等于" :value="1"></el-option>
                <el-option label="大于" :value="2"></el-option>
                <el-option label="大于等于" :value="3"></el-option>
                <el-option label="小于" :value="4"></el-option>
                <el-option label="小于等于" :value="5"></el-option>
                <el-option label="不等于" :value="6"></el-option>
                <el-option label="模糊匹配" :value="7"></el-option>
                <el-option label="区间" :value="8"></el-option>
                <el-option label="不在区间" :value="9"></el-option>
                <el-option label="在列表中" :value="10"></el-option>
                <el-option label="不在列表中" :value="11"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="dataRule.entity.type != 0" label="字段值" prop="value">
              <el-input v-model="dataRule.entity.value" placeholder="请输入字段值"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="dataSubmit">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </template>
    <!-- 表单 -->
    <template v-slot:form>
      <d1-page-title :title="title+'菜单'" @back="back"></d1-page-title>
      <el-form ref="form" :model="entity" :rules="rules" label-width="150px">
        <el-form-item label="菜单分类" prop="type">
          <el-radio-group v-model="entity.type" @change="typeChange" :disabled="entity.id?true:false">
            <el-radio :label="0">模块</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">操作</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="entity.type > 0" label="所属上级" prop="parentId">
          <el-select v-model="parent" value-key="id" @change="parantChange" placeholder="请选择上级">
            <template v-for="item in parentList" :key="item.id">
              <el-option :label="item.name" :value="{id:item.id,path:item.path}">
              </el-option>
              <template v-for="(jtem,jndex) in item.child" :key="jtem.id">
                <el-option :label="jtem.name" :value="{id:jtem.id,path:jtem.path}">
                  <div class="d-flex">
                    <div style="color:var(--el-border-color-base);font-family: 'Consolas,Monaco,Andale Mono,Ubuntu Mono,monospace'">
                      <template v-if="jtem.child.length>0">
                        {{ '├ ' }}
                      </template>
                      <template v-else>
                        {{(jndex == (item.child.length-1) ? '└ ':'├ ')}}
                      </template>
                    </div>
                    <div style="padding-left:5px">
                      {{jtem.name}}
                    </div>
                  </div>
                </el-option>
                <el-option v-for="(ktem,kndex) in jtem.child" :key="ktem.id" :label="ktem.name" :value="{id:ktem.id,path:ktem.path}">
                  <div class="d-flex">
                    <div style="color:var(--el-border-color-base);font-family: 'Consolas,Monaco,Andale Mono,Ubuntu Mono,monospace'">
                      <template v-if="jndex == (item.child.length-1)">
                        <template v-if="(jndex == (item.child.length-1)) && (kndex == (jtem.child.length-1))">
                          {{'└ └ '}}
                        </template>
                        <template v-else>
                          {{(kndex == (jtem.child.length-1) ? '│ └ ':'│ ├ ')}}
                        </template>
                      </template>
                      <template v-else>
                        {{(jndex == (item.child.length-1) ? '├ ':'│ ')}}
                        {{(kndex == (jtem.child.length-1) ? '└ ':'├ ')}}
                      </template>
                    </div>
                    <div style="padding-left:5px">
                      {{ktem.name}}
                    </div>
                  </div>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item :label="(entity.type == 2 ? '操作':'菜单')+'名称'" prop="name">
          <el-input v-model="entity.name" :placeholder="'请输入'+(entity.type == 2 ? '操作':'菜单')+'名称'"></el-input>
        </el-form-item>
        <el-form-item v-if="entity.type == 0" label="菜单图标" prop="icon">
          <div class="d-flex align-items-center">
            <el-input style="width:200px !important" v-model="entity.icon" prefix-icon="el-icon-search" readonly @click="iconDialog = true" placeholder="请选择图标"></el-input>
            <div class="h-100 d-flex align-items-center">
              <i style="margin-left:5px;font-size:24px" :class="entity.icon"></i>
            </div>
          </div>
        </el-form-item>
        <el-form-item :label="entity.type == 2 ? '操作标识' : '页面路径'" prop="tempPath">
          <el-input v-model="entity.tempPath" :placeholder="'请输入'+(entity.type == 2 ? '操作标识' : '页面路径')">
            <template v-if="parent.path" #prepend>{{parent.path}}</template>
          </el-input>
        </el-form-item>
        <el-form-item label="关联接口" prop="apis">
          <div>
            <el-switch v-model="entity.isApi" :active-value="1" :inactive-value="0"></el-switch>
          </div>
          <div v-if="entity.isApi == 1" class="mt-10">
            <table class="api-table table table-bordered">
              <thead>
                <tr>
                  <th width="60">类型</th>
                  <th>API</th>
                  <th width="90">操作</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in temp.apis" :key="item">
                  <td class="va">{{['查询','新增','编辑','删除'][item.type]}}</td>
                  <td class="va" style="color:var(--el-text-color-regular)">{{item.url}}</td>
                  <td class="va">
                    <a style="color:var(--el-color-primary)" href="javascript:;" @click="openApi(item)">编辑</a>
                    <a class="ml-10" style="color:var(--el-color-primary)" href="javascript:;" @click="delApi(item)">删除</a>
                  </td>
                </tr>
                <tr>
                  <td colspan="3"><a style="color:var(--el-color-primary)" href="javascript:;" @click="openApi(null)"><i class="el-icon-plus"></i> 增加接口</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="entity.sort" placeholder="请输入排序"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit">保存</el-button>
        </el-form-item>
      </el-form>
      <el-dialog title="选择图标" v-model="iconDialog" width="634px" :show-close="false">
        <div>
          <div class="icon-box d-flex flex-wrap">
            <div class="icon d-flex justify-content-center align-items-center" v-for="(item,index) in icons" :key="item" @click="iconSelect(item,index)">
              <i :class="item"></i>
            </div>
          </div>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="iconDialog = false">取 消</el-button>
            <el-button type="primary" @click="iconDialog = false">确 定</el-button>
          </span>
        </template>
      </el-dialog>
      <el-dialog title="添加接口" v-model="apiDialog" width="600px">
        <div class="d-flex mb-10">
          <el-select v-model="formApi.type" style="width:120px !important" class="mr-10" placeholder="类型">
            <el-option label="查询" :value="0"></el-option>
            <el-option label="新增" :value="1"></el-option>
            <el-option label="编辑" :value="2"></el-option>
            <el-option label="删除" :value="3"></el-option>
          </el-select>
          <el-input v-model="formApi.url" style="width:100% !important;margin-right:10px" placeholder="请输入接口 / 开头"></el-input>
          <el-button type="primary" @click="addApi">确 定</el-button>
        </div>
      </el-dialog>
    </template>
  </d1-page>
</template>
<script>
import icon from './icon'
export default {
  data() {
    return {
      list: [],
      iconDialog: false,
      apiDialog: false,
      temp: {
        apis: []
      },
      entity: {
        parentId: null,
        type: 0,
        tempPath: '',
        tempApis: []
      },
      icons: icon,
      parent: {
        id: '',
        path: ''
      },
      path: '',
      parentList: [],
      childList: [],
      rules: {
        name: [{
          required: true,
          message: '请输入名称'
        }],
        parentId: [{
          required: true,
          message: '请选择上级'
        }],
        tempPath: [{
          required: true,
          message: () => {
            return '请输入' + (this.entity.type == 2 ? '标识' : '路径')
          }
        }],
        url: [{
          required: true,
          message: '请输入URL'
        }]
      },
      formApi: {
        id: '',
        type: 0,
        url: ''
      },
      dataRule: {
        drawer: false,
        dialog: false,
        currentRule: '',
        apis: [],
        list: [],
        entity: {
          name: '',
          apiId: null,
          field: '',
          type: null,
          value: ''
        },
        rules: {
          name: [{
            required: true,
            message: '请输入规则名称'
          }],
          apiId: [{
            required: true,
            message: '请选择绑定接口'
          }],
          field: [{
            required: true,
            message: '请输入规则字段'
          }],
          type: [{
            required: true,
            message: '请选择条件类型'
          }],
          value: [{
            required: true,
            message: '请输入字段值'
          }]
        }
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.reset(0);
      this.$axios.get('manager/system/rule/list').then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = [];
      })
    },
    loadParent(type = 0) {
      return this.$axios.get('manager/system/rule/parent', { type }).catch(e => { });
    },
    back() {
      this.reset(0);
      this.$refs.page.back();
    },
    add() {
      this.title = '新增';
    },
    edit(id) {
      this.title = '编辑';
      this.$refs.page.edit()
      this.$axios.get('manager/system/rule/edit', { id: id }).then(async data => {
        let parentData = await this.loadParent();
        if (parentData) {
          this.parentList = parentData.result;
        }
        this.parent.id = data.result.parentId;
        let pathArr = data.result.path.split('/');
        let last = pathArr.pop();
        let parentPath = '';
        if (pathArr.length > 0) {
          parentPath = pathArr.join('/') + '/';
        }
        this.parent.path = parentPath;
        this.entity = data.result;
        this.entity.tempPath = last;
        this.temp.apis = this.entity.apiList;
        if (this.temp.apis.length > 0) {
          this.entity.isApi = 1;
        }
      }).catch(e => { })
    },
    async typeChange(type) {
      this.parentList = [];
      this.reset(type);

      if (type > 0) {
        let p = type == 1 ? 0 : 1;
        let parentData = await this.loadParent(p);
        if (parentData) {
          this.parentList = parentData.result;
        }
      }
    },
    parantChange(data) {
      if (this.entity.type > 0) {
        this.entity.parentId = data.id;
        this.parent.path = data.path + '/';
      }
    },
    iconSelect(item, index) {
      // 清空选中
      this.$el.querySelectorAll('.icon-box .icon.active').forEach(item => {
        item.classList.remove('active')
      })
      // 设置选中
      this.$el.querySelectorAll('.icon-box .icon')[index].classList.add('active');
      this.entity.icon = item;
    },
    openApi(data) {
      if (!data) {
        data = { id: '', type: 0, url: '' };
      }
      this.formApi = data;
      this.apiDialog = true;
    },
    addApi() {
      if (this.formApi.url.length > 0) {
        if (this.formApi.id) {

        } else {
          this.temp.apis.push({
            type: this.formApi.type,
            url: this.formApi.url
          });
        }
      }
      this.apiDialog = false;
    },
    delApi(data) {
      this.$confirm('此操作将永久删除该接口, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        this.temp.apis.remove(data);
        console.log(this.temp.apis);
      }).catch(() => { })
    },
    submit() {
      // 参数设置
      this.entity.parentId = this.parent.id;
      this.entity.path = this.parent.path + this.entity.tempPath;
      if (this.temp.apis.length > 0) {
        this.entity.apis = JSON.stringify(this.temp.apis);
      }

      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.entity.id != null) {
            this.$axios.put('manager/system/rule/edit', this.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.$refs.page.back()
                  this.init();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          } else {
            this.$axios.post('manager/system/rule/add', this.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.$refs.page.back()
                  this.init();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          }
        }
      })
    },
    del(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/system/rule/delete', { id }).then(data => {
          this.$message.success(data.message);
          this.init();
        }).catch(e => {
          this.$message.error(e);
        })
      })
    },
    reset(type) {
      this.parent = {
        id: '',
        path: ''
      }
      this.entity = {
        type,
        parentId: null,
        name: '',
        page: 0,
        menu: 0,
        path: '',
        apis: '',
        sort: ''
      }
      this.temp = {
        apis: []
      }
    },
    openRule(id) {
      this.dataRule.drawer = true;
      this.dataRule.currentRule = id;
      this.loadRuleData();
    },
    loadRuleData() {
      this.$axios.get('manager/system/rule/dataRuleList', { ruleId: this.dataRule.currentRule }).then(data => {
        this.dataRule.list = data.result;
      }).catch(e => {
        this.dataRule.list = [];
      })
    },
    closeRule() {
      this.dataRule.drawer = false;
    },
    openDataDialog() {
      this.dataRule.dialog = true;
      this.loadRuleApi();
    },
    editDataRule(id) {
      this.dataRule.dialog = true;
      this.$axios.get('manager/system/rule/dataRuleDetail', { id: id }).then(data => {
        this.loadRuleApi();
        this.dataRule.entity = data.result;
      }).catch(e => { })
    },
    loadRuleApi() {
      this.$axios.get('manager/system/rule/apis', { ruleId: this.dataRule.currentRule }).then(data => {
        this.dataRule.apis = data.result;
      }).catch(e => { })
    },
    dataSubmit() {
      this.$refs['dataForm'].validate(valid => {
        if (valid) {
          if (this.dataRule.entity.id) {
            this.$axios.put('manager/system/rule/dataRuleEdit', this.dataRule.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadRuleData();
                  this.dataRule.dialog = false;
                }
              })
            }).catch(e => {
              this.$message.error(e);
            })
          } else {
            this.dataRule.entity.ruleId = this.dataRule.currentRule;
            this.$axios.post('manager/system/rule/dataRuleAdd', this.dataRule.entity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadRuleData();
                  this.dataRule.dialog = false;
                }
              })
            }).catch(e => {
              this.$message.error(e);
            })
          }
        }
      })
    },
    deleteDataRule(id) {
      this.$axios.delete('manager/system/rule/dataRuleDelete', { id: id }).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
            this.loadRuleData();
            this.dataRule.dialog = false;
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
.system-rule {
  .icon-box {
    height: 300px;
    overflow-y: auto;
    border: solid 1px #ddd;
    .icon {
      $size: 48px;
      width: $size;
      height: $size;
      cursor: pointer;
      i {
        font-size: 20px;
      }
      &.active {
        background: var(--el-color-primary);
        color: white;
      }
    }
  }
}
.api-table {
  width: 500px;
}
.rule-table {
  width: 600px;
}
</style>
<style lang="scss">
.system-rule {
  .el-dialog__body {
    padding: 10px 20px;
  }
}
</style>