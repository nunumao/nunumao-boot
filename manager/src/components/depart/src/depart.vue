<template>
  <div class="d1-components-depart">
    <div class="el-select">
      <div class="select-trigger">
        <div class="el-select__tags">
          <span>
            <span class="el-tag el-tag--info el-tag--small el-tag--light" v-for="item in current" :key="item.id">
              <span class="el-select__tags-text">{{item.name}}</span>
              <i class="el-tag__close el-icon-close" @click.stop="remove(item)"></i>
            </span>
          </span>
        </div>
        <div class="el-input el-input--suffix">
          <input class="el-input__inner" :style="{width:width}" type="text" readonly :placeholder="current.length > 0 ? '':'请选择部门'" @click="open">
          <span class="el-input__suffix" @click="open">
            <span class="el-input__suffix-inner">
              <i style="transform:rotate(0deg)" class="el-select__caret el-input__icon iconfont icon-zuzhijigou icon-input-suffix"></i>
            </span>
          </span>
        </div>
      </div>
    </div>
    <el-dialog title="选择部门" v-model="dialog" width="800px" :show-close="false" destroy-on-close @close="close">
      <div>
        <template v-if="multi">
          <el-checkbox-group v-model="temp">
            <el-table border :data="list" height="300" default-expand-all row-key="id" :tree-props="{children: 'child', hasChildren: true}" @current-change="currentChange">
              <el-table-column type="selection" width="55">
                <template #default="scope">
                  <el-checkbox :label="scope.row.id">&nbsp;</el-checkbox>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="名称"></el-table-column>
            </el-table>
          </el-checkbox-group>
        </template>
        <template v-else>
          <el-radio-group v-model="current">
            <el-table border :data="list" height="300" default-expand-all row-key="id" :tree-props="{children: 'child', hasChildren: true}" @current-change="currentChange">
              <el-table-column type="selection" width="55">
                <template #default="scope">
                  <el-radio :label="scope.row.id">&nbsp;</el-radio>
                </template>
              </el-table-column>
              <el-table-column prop="name" label="名称"></el-table-column>
            </el-table>
          </el-radio-group>
        </template>
        <div class="mt-10 d-flex justify-content-between">
          <div>
          </div>
          <div>
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="enter">确 定</el-button>
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
      type: [String, Array],
      required: true
    },
    api: {
      type: String,
      default: ''
    },
    multi: {
      type: Boolean,
      default: true
    },
    width: {
      type: String,
      default: 'auto'
    }
  },
  data() {
    return {
      dialog: false,
      show: '',
      list: [],
      current: [],
      select: [],
      temp: []
    }
  },
  watch: {
    value: function (v) {
      if (v && Array.isArray(v) && v.length > 0) {
        this.$axios.get(this.api + '/depart-detail', { id: v.join(',') }).then(data => {
          this.current = [...new Set(data.result)];
          this.temp = data.result.map(item => item.id);
          this.select = data.result;
        }).catch(e => { })
      }
    }
  },
  methods: {
    loadList() {
      this.$axios.get(this.api + '/depart-list').then(data => {
        this.list = data.result;
      }).catch(e => { });
    },
    currentChange(data) {
      if (this.multi) {
        if (this.temp.includes(data.id)) {
          this.temp.remove(data.id);
          this.select.remove(data);
        } else {
          this.temp.push(data.id);
          this.select.push(data);
          this.select = [...new Set(this.select)];
        }
      } else {
        this.select = data;
      }
    },
    async open() {
      this.loadList();
      this.dialog = true;
    },
    close() {
      if (this.multi) {
        this.temp = this.value;
        let arr = [];
        let loop = list => {
          list.forEach(item => {
            if (this.value.includes(item.id)) {
              arr.push(item);
            }
            if (Array.isArray(item.child)) {
              loop(item.child);
            }
          });
        }
        loop(this.list);
        this.select = arr;
        this.temp = arr.map(item => item.id);
      }
      this.dialog = false;
    },
    remove(data) {
      this.current.remove(data);
      this.select.remove(data);
      this.temp.remove(data.id);
      this.resizeHeight();
    },
    resizeHeight() {
      this.$nextTick(() => {
        let height = 36;
        if (this.select.length > 0) {
          let tagHeight = this.$el.querySelector('.el-select__tags').offsetHeight;
          height = tagHeight + 8;
        }
        this.$el.querySelector('.el-input__inner').style.height = height + 'px';
      })
    },
    enter() {
      this.dialog = false;
      if (this.multi) {
        this.current = this.select;
        this.$emit('update:value', this.temp);
      } else {
        this.$emit('result', this.select[0] || null);
      }
      this.resizeHeight();
    },
    reset() {
      this.current = null;
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-components-depart {
  .el-select {
    line-height: 36px !important;
  }
  .el-input__inner {
    height: 36px;
  }
  .el-select__tags {
    left: 0px;
    right: 30px;
  }
}
</style>
<style lang="scss">
.d1-components-depart {
  .el-table {
    .el-checkbox__label {
      display: none;
    }
    .el-table-column--selection {
      .cell {
        display: flex;
        justify-content: center;
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