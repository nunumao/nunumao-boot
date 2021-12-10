<template>
  <d1-list-page ref="page" title="分组用户" api-list="manager/groupsUser/list" api-del="manager/groupsUser/delete">
    <template v-slot:action>
      <el-button type="primary" @click="add" :disabled="!conditon.gid">新增</el-button>
    </template>
    <template v-slot:search>
      <div class="d-flex">
        <el-select class="mr-3" v-model="conditon.gid" placeholder="请选择分组" @change="groupChange">
          <el-option v-for="item in groupsList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
        <el-input v-model="conditon.name" prefix-icon="el-icon-search" :clearable="true" placeholder="请输入查询条件"></el-input>
        <el-button style="margin-left:10px" @click="search">搜索</el-button>
      </div>
    </template>
    <div>
      <el-table :data="list.records" border style="width: 100%">
        <el-table-column prop="account" label="用户名称" width="250">
        </el-table-column>
        <el-table-column prop="name" label="用户名称">
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="scope">
            <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="list-footer d-flex justify-content-center">
      <el-pagination v-if="list.total > 0" v-model:current-page="list.current" :total="list.total" @current-change="page" layout="total, prev, pager, next, jumper">
      </el-pagination>
    </div>
    <template v-slot:attach>
      <el-dialog title="请选择用户" v-model="dialog" width="50%">
        <el-table :data="userList" border style="width: 100%">
          <el-table-column prop="name" label="姓名">
          </el-table-column>
          <el-table-column label="操作" width="80">
            <template #default="scope">
              <el-button size="mini" type="danger" @click="put(scope.row.id)">加入</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </template>
  </d1-list-page>
</template>
<script>
export default {
  data() {
    return {
      dialog: false,
      conditon: {
        name: '',
        gid: null
      },
      groupsList: [],
      userList: [],
      list: {
        records: [],
        current: 1,
        total: 0
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    async init() {
      let gData = await this.loadGroups();
      if (gData) {
        this.groupsList = gData.result;
      }
    },
    loadList() {
      this.$axios.get('manager/groupsUser/list', { gid: this.conditon.gid, page: this.list.current }).then(data => {
        this.list = data.result;
      }).catch(e => {
        this.clear();
      })
    },
    loadGroups() {
      return this.$axios.get('manager/groupsUser/groups');
    },
    loadUser() {
      return this.$axios.get('manager/groupsUser/user', { gid: this.conditon.gid });
    },
    search() {
      this.init();
    },
    groupChange() {
      this.loadList();
    },
    async add() {
      this.dialog = true;
      let data = await this.loadUser();
      if (data) {
        this.userList = data.result;
      }
    },
    put(uid) {
      this.$axios.post('manager/groupsUser/add', { gid: this.conditon.gid, uid: uid }).then(data => {
        this.$message.success(data.message);
        this.loadList();
      }).catch(e => {
        this.$message.error(e);
      })
      this.dialog = false;
    },
    del(id) {
      this.$axios.delete('manager/groupsUser/delete', { id: id }).then(data => {
        this.$message.success(data.message);
        this.loadList();
      }).catch(e => {
        this.$message.error(e);
      })
    },
    clear() {
      this.list.current = 1;
      this.list.total = 0;
      this.list.records = [];
    }
  }
}
</script>