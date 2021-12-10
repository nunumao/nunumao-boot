<template>
  <div class="d1-components-suggest">
    <el-input v-model="current.name" readonly :placeholder="'请选择'+placeholder" @click="open" />
    <el-dialog v-model="dialog" :title="'选择'+placeholder" width="600px" destroy-on-close :before-close="close">
      <div>
        <div class="d-flex">
          <div class="search">
            <el-input v-model="query.search" size="small" clearable placeholder="请输入查询条件" />
          </div>
          <div class="ml-10">
            <el-button type="primary" size="small" plain @click="search">搜索</el-button>
          </div>
        </div>
        <div class="mt-10">
          <el-table :data="list.records" border>
            <el-table-column v-for="item in column" :key="item" :prop="item.prop" :label="item.label" />
            <el-table-column label="操作" width="60">
              <template #default="scope">
                <el-button type="text" @click="select(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div v-if="list.total > 0" class="mt-10 d-flex justify-content-center">
            <el-pagination layout="prev, pager, next" v-model:current-page="list.current" :total="list.total" @current-change="page"></el-pagination>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default {
  props: {
    api: {
      type: String,
      required: true
    },
    field: {
      type: String,
      default: 'name'
    },
    value: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    column: {
      type: Array,
      default: [{ label: '名称', prop: 'name' }]
    }
  },
  data() {
    return {
      dialog: false,
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
      if (v != this.current.id) {
        this.loadDetail();
      }
    }
  },
  methods: {
    loadList() {
      this.$axios.get(this.api, { action: 'list', search: this.query.search, page: this.list.current }).then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = {
          records: [],
          current: 1,
          total: 0
        }
      })
    },
    loadDetail() {
      this.$axios.get(this.api, { action: 'detail', id: this.value }).then(data => {
        this.current.id = data.result.id;
        this.current.name = data.result[this.field];
      }).catch(e => { })
    },
    open() {
      this.dialog = true;
      this.loadList();
    },
    page() {
      this.loadList();
    },
    search() {
      this.loadList();
    },
    close() {
      this.dialog = false;
    },
    select(data) {
      this.current.id = data.id;
      this.current.name = data[this.field];
      this.$emit('update:value', data.id);
      this.close();
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
<style lang="scss">
.d1-components-suggest {
  .el-dialog__header {
    padding: 10px 20px !important;
    border-bottom: solid 1px var(--el-border-color-base);
    line-height: 20px !important;
    .el-dialog__title {
      font-size: 16px !important;
    }
    .el-dialog__headerbtn {
      top: 10px !important;
    }
  }
  .el-dialog__body {
    padding: 10px 20px !important;
  }
  th,
  td,
  tr {
    line-height: normal !important;
  }
  .search {
    .el-input__inner {
      border: var(--el-input-border) !important;
    }
  }
}
</style>