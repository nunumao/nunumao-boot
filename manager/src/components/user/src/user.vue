<template>
  <div class="d1-components-user">
    <el-dialog title="选择用户" v-model="dialog" width="800px" :show-close="false" destroy-on-close @close="close">
      <div>
        <div class="d-flex justify-content-between mb-10">
          <div>
            <el-radio-group v-model="type" @change="typeChange">
              <el-radio :label="0">不限</el-radio>
              <el-radio :label="1">部门/岗位</el-radio>
            </el-radio-group>
          </div>
          <div class="d-flex">
            <div>
              <el-input v-model="query.search" clearable placeholder="请输入查询条件"></el-input>
            </div>
            <div class="ml-10">
              <el-button type="primary" plain @click="search">搜索</el-button>
            </div>
          </div>
        </div>
        <div class="main-box d-flex">
          <div class="depart-tree py-10" v-if="type == 1">
            <el-tree :data="depart" :props="{children: 'child',label: 'name'}" :default-expand-all="true" @node-click="nodeClick"></el-tree>
          </div>
          <div class="main-table" :style="{'margin-left':type>0?'5px':'0'}">
            <template v-if="multi">
              <el-checkbox-group v-model="selection.multi">
                <el-table border :data="list.records" height="300" @row-click="rowClick">
                  <el-table-column type="expand">
                    <template #default="scope">
                      <div class="p-10 d-flex">
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">性别</div>
                          <div>
                            <el-tag type="success">
                              <span v-if="scope.row.gender == 0">未知</span>
                              <span v-if="scope.row.gender == 1">男</span>
                              <span v-if="scope.row.gender == 2">女</span>
                            </el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">工号</div>
                          <div>
                            <el-tag>{{scope.row.workNo||'无'}}</el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">邮箱</div>
                          <div>
                            <el-tag>{{scope.row.email||'无'}}</el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">创建时间</div>
                          <div>
                            <el-tag type="info">{{scope.row.createTime||'无'}}</el-tag>
                          </div>
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="account" label="账号" width="120"></el-table-column>
                  <el-table-column prop="name" label="姓名"></el-table-column>
                  <el-table-column prop="nickName" label="昵称"></el-table-column>
                  <el-table-column prop="phone" label="联系方式" width="120"></el-table-column>
                  <el-table-column type="selection" width="55">
                    <template #default="scope">
                      <el-checkbox :label="scope.row.id">&nbsp;</el-checkbox>
                    </template>
                  </el-table-column>
                </el-table>
              </el-checkbox-group>
            </template>
            <template v-else>
              <el-radio-group v-model="selection.single">
                <el-table border :data="list.records" height="300" @row-click="rowClick">
                  <el-table-column type="expand">
                    <template #default="scope">
                      <div class="p-10 d-flex">
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">性别</div>
                          <div>
                            <el-tag type="success">
                              <span v-if="scope.row.gender == 0">未知</span>
                              <span v-if="scope.row.gender == 1">男</span>
                              <span v-if="scope.row.gender == 2">女</span>
                            </el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">工号</div>
                          <div>
                            <el-tag>{{scope.row.workNo||'无'}}</el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">邮箱</div>
                          <div>
                            <el-tag>{{scope.row.email||'无'}}</el-tag>
                          </div>
                        </div>
                        <div class="d-flex align-items-center mr-10">
                          <div class="pr-10">创建时间</div>
                          <div>
                            <el-tag type="info">{{scope.row.createTime||'无'}}</el-tag>
                          </div>
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="account" label="账号" width="120"></el-table-column>
                  <el-table-column prop="name" label="姓名"></el-table-column>
                  <el-table-column prop="nickName" label="昵称"></el-table-column>
                  <el-table-column prop="phone" label="联系方式" width="120"></el-table-column>
                  <el-table-column type="selection" label="操作" width="55">
                    <template #default="scope">
                      <el-radio :label="scope.row.id">&nbsp;</el-radio>
                    </template>
                  </el-table-column>
                </el-table>
              </el-radio-group>
            </template>

            <div class="mt-10 d-flex justify-content-between">
              <div>
                <el-pagination v-if="list.total > 0" v-model:current-page="list.current" :total="list.total" @current-change="page" layout="prev, pager, next">
                </el-pagination>
              </div>
              <div>
                <el-button @click="close">取 消</el-button>
                <el-button type="primary" @click="select">确 定</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    dialog: {
      type: Boolean,
      default: false
    },
    api: {
      type: String,
      required: true
    },
    multi: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      query: {
        search: ''
      },
      list: {
        records: [],
        current: 1,
        total: 0
      },
      depart: [],
      curDepart: '',
      selection: {
        result: null,
        multi: [],
        single: ''
      },
      type: 0
    }
  },
  watch: {
    dialog: function (v) {
      if (v) {
        this.reset();
        this.init();
      }
    }
  },
  mounted() {
    this.reset();
  },
  methods: {
    async init() {
      this.loadList();
    },
    loadList() {
      let param = {
        search: this.query.search,
        type: this.type,
        page: this.list.current,
        depart: this.curDepart
      }
      this.$axios.get(this.api, param).then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = {
          records: [],
          current: 1,
          total: 0
        }
      });
    },
    search() {
      this.list.current = 1;
      this.loadList();
    },
    page() {
      this.loadList();
    },
    typeChange(value) {
      this.clear();
      if (value > 0) {
        if (value == 1) {
          this.$axios.get(this.api, { action: 1 }).then(data => {
            this.depart = data.result
          }).catch(e => { })
        }
      } else {
        this.init();
      }
    },
    rowClick(value, column, event) {
      event.preventDefault();
      if (this.multi) {
        if (this.selection.multi.includes(value.id)) {
          this.selection.result.remove(value)
          this.selection.multi.remove(value.id)
        } else {
          this.selection.result.push(value)
          this.selection.multi.push(value.id)
        }
      } else {
        this.selection.result = value
        this.selection.single = value.id
      }
    },
    nodeClick(data) {
      this.type = 1;
      this.curDepart = data.id;
      this.loadList();
    },
    select() {
      this.$emit('result', this.selection.result);
      this.$emit('update:dialog', false);
    },
    close() {
      this.$emit('update:dialog', false);
    },
    reset() {
      this.query.search = '';
      this.type = 0;
      this.clear();
    },
    clear() {
      this.selection = {
        result: null,
        multi: [],
        single: ''
      }
      this.selection.result = this.multi ? [] : {}
      this.list = {
        records: [],
        current: 1,
        total: 0
      }
      this.depart = []
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-components-user {
  .main-box {
    flex: auto;
    overflow-x: hidden;
  }
  .depart-tree {
    width: 175px;
    margin-right: 5px;
    border: solid 1px #eee;
  }
  .main-table {
    width: 0;
    flex: 1;
  }
}
</style>
<style lang="scss">
.d1-components-user {
  .el-input__inner {
    border: var(--el-input-border) !important;
  }
  .el-table {
    .el-checkbox__label {
      display: none;
    }
    .el-table-column--selection {
      .cell {
        display: flex;
        justify-content: center;
        height: 36px;
      }
    }
    .cell {
      line-height: 23px !important;
      .el-radio__label {
        display: none;
      }
    }
    th {
      label.el-checkbox {
        display: none;
      }
    }
  }
  .el-table__cell {
    padding: 5px 0 !important;
  }
  .el-dialog__header {
    border-bottom: solid 1px #eee;
    padding: 10px 15px !important;
    line-height: normal !important;
    .el-dialog__title {
      font-size: 16px !important;
    }
  }
  .el-dialog__body {
    padding: 10px 15px !important;
  }
  .el-button--text {
    font-size: 14px !important;
  }
}
</style>