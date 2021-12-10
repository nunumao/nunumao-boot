<template>
  <d1-form-page :title="entity.id?'分组编辑':'分组新增'" v-model:entity="entity" :rules="rules" api-add="manager/groups/add" api-edit="manager/groups/edit" api-detail="manager/groups/detail" :before-action="beforeAction">
    <template v-slot:field>
      <el-form-item label="名称" prop="name">
        <el-input v-model="entity.name"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="entity.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
    </template>
  </d1-form-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        id: this.$route.query.id,
        status: 1
      },
      rules: {
        name: [{
          required: true,
          message: '请输入名称'
        }]
      }
    }
  },
  methods: {
    beforeAction() {

    }
  }
}
</script>