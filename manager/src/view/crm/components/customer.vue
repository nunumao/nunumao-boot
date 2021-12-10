<template>
  <div class="crm-components-customer">
    <el-input v-model="value.name" readonly placeholder="请选择客户" @click="open" />
    <el-dialog v-model="dialog" title="关联客户" width="600px" destroy-on-close :before-close="close">
      <div>
        <div class="d-flex">
          <div>
            <el-input v-model="query.search" size="small" clearable placeholder="客户姓名/客户编号" />
          </div>
          <div class="ml-10">
            <el-button type="primary" size="small" plain @click="search">搜索</el-button>
          </div>
        </div>
        <div class="mt-10">
          <el-table :data="list.records" border>
            <el-table-column prop="fullName" label="客户名称" />
            <el-table-column prop="userId" label="所属用户" width="180" />
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
    value: {
      type: Object,
      default: {
        id: '',
        name: ''
      }
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
      }
    }
  },
  methods: {
    loadList() {
      this.$axios.get(this.api, { search: this.query.search, page: this.list.current }).then(data => {
        this.list = data.result;
      }).catch(e => {
        this.list = {
          records: [],
          current: 1,
          total: 0
        }
      })
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
      this.$emit('update:value', { id: data.id, name: data.fullName });
      this.$emit('result', { id: data.id, name: data.fullName });
      this.close();
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
<style lang="scss">
.crm-components-customer {
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
}
</style>