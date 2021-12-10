<template>
  <d1-page class="crm-case" title="方案" :apis="{prefix:'manager/crm/case/'}" permission="crm/setting/case/" v-model:entity="entity" :rules="rules" @after-entity="afterEntity" @add="add" @edit="edit">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column prop="name" label="方案名称">
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="方案名称" prop="name">
        <el-input v-model="entity.name" placeholder="请输入方案名称"></el-input>
      </el-form-item>
      <el-form-item label="方案内容" prop="content">
        <div>
          <table class="content-table table table-bordered">
            <thead>
              <tr>
                <th>方案项目</th>
                <th width="60">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(item,index) in temp.content" :key="index">
                <td style="padding:8px 10px">
                  <el-input style="width:100% !important" v-model="item.value" placeholder="请输入项目内容" />
                </td>
                <td style="vertical-align:middle">
                  <a style="color:var(--el-color-primary)" href="javascript:;" @click="delContent(item)">移除</a>
                </td>
              </tr>
              <tr>
                <td colspan="3"><a style="color:var(--el-color-primary)" href="javascript:;" @click="addContent"><i class="el-icon-plus"></i> 增加一项</a></td>
              </tr>
            </tbody>
          </table>
        </div>
      </el-form-item>
    </template>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      temp: {
        content: []
      },
      entity: {
        name: '',
        conetnt: ''
      },
      rules: {
        name: [{
          required: true,
          message: '请输入方案名称',
          trigger: 'blur'
        }],
        content: [{
          required: true,
          message: '请输入方案名称',
          trigger: 'blur'
        }]
      }
    }
  },
  watch: {
    'temp.content': {
      handler(v, o) {
        let temp = [];
        v.forEach(item => {
          if (item.value) {
            temp.push(item.value)
          }
        })
        this.entity.content = temp.join(',')
      },
      deep: true
    }
  },
  methods: {
    add() {
      this.beforeAction();
    },
    edit() {
      this.beforeAction();
    },
    beforeAction() {
      this.temp.content = [];
    },
    addContent() {
      this.temp.content.push({ value: '' })
    },
    delContent(item) {
      this.temp.content.remove(item)
    },
    afterEntity(data) {
      let arr = data.content.split(',');
      arr.forEach(item => {
        this.temp.content.push({ value: item })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-case {
  .content-table {
    width: 600px;
  }
}
</style>