<template>
  <div class="d1-components-position">
    <el-input v-model="show" readonly placeholder="请选择职务" @click="open">
      <template #suffix>
        <i class="iconfont icon-gangweitubiao icon-input-suffix"></i>
      </template>
    </el-input>
    <el-dialog title="选择职务" v-model="dialog" width="800px" :show-close="false" destroy-on-close @close="close">
      <div>
        <div class="d-flex mb-10">
          <div>
            <el-input v-model="query.search" clearable placeholder="请输入查询条件"></el-input>
          </div>
          <div class="ml-10">
            <el-button type="primary" plain @click="search">搜索</el-button>
          </div>
        </div>
        <div class="main-box">
          <el-radio-group v-model="current.id">
            <el-table border :data="list.records" height="300" @current-change="currentChange">
              <el-table-column width="40">
                <template #default="scope">
                  <el-radio :label="scope.row.id">&nbsp;</el-radio>
                </template>
              </el-table-column>
              <el-table-column prop="code" label="编码" width="140"></el-table-column>
              <el-table-column prop="name" label="名称"></el-table-column>
              <el-table-column prop="postRank" label="职级" width="120"></el-table-column>
            </el-table>
          </el-radio-group>
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
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: String,
      default: ''
    },
    api: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dialog: false,
      show: '',
      query: {
        search: ''
      },
      list: {
        records: [],
        current: 1,
        total: 0
      },
      current: {
        id: '',
        name: ''
      }
    }
  },
  watch: {
    value: function (v) {
      if (v) {
        this.$axios.get(this.api + '/position-detail', { id: v }).then(data => {
          this.current.id = data.result.id;
          this.current.name = this.show = data.result.name;
        }).catch(e => { })
      }
    }
  },
  mounted() {

  },
  methods: {
    loadList() {
      this.$axios.get(this.api + '/position-list', this.query).then(data => {
        this.list = data.result;
        let is = data.result.records.filter(item => item.id == this.value)[0];
        if (is) {
          this.current = is;
        }
      }).catch(e => { });
    },
    currentChange(data) {
      this.current = data;
    },
    async open() {
      this.reset();
      this.loadList();
      this.dialog = true;
    },
    close() {
      this.dialog = false;
    },
    page() {
      this.loadList();
    },
    search() {
      this.loadList();
    },
    select() {
      this.dialog = false;
      this.show = this.current.name;
      this.$emit('update:value', this.current.id);
    },
    reset() {
      this.current = {
        id: '',
        name: ''
      }
    }
  }
}
</script>
<style lang="scss">
.d1-components-position {
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