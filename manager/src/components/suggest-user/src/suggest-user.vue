<template>
  <div class="d1-components-suggest">
    <el-input v-model="current.name" readonly placeholder="请选择用户" @click="open" />
    <d1-user :api="api+'user-list'" v-model:dialog="dialog" @result="result"></d1-user>
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
      type: String,
      default: ''
    }
  },
  data() {
    return {
      dialog: false,
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
    loadDetail() {
      this.$axios.get(this.api + 'user-detail', { id: this.value }).then(data => {
        this.current.id = data.result.id;
        this.current.name = data.result.name;
      }).catch(e => { })
    },
    open() {
      this.dialog = true;
    },
    close() {
      this.dialog = false;
    },
    result(data) {
      this.current.id = data.id;
      this.current.name = data.name;
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