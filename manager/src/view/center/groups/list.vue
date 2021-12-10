<template>
  <d1-page title="分组列表" :apis="{prefix:'manager/groups/'}" v-model:entity="entity" :rules="rules">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column prop="name" label="分组名称">
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <span style="color:#dc3545" v-if="scope.row.status == 0">禁用</span>
          <span style="color:#28a745" v-if="scope.row.status == 1">启用</span>
        </template>
      </el-table-column>
    </template>
    <!-- 表单 -->
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
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        name: null,
        status: 1
      },
      rules: {
        name: [{
          required: true,
          message: '请输入名称',
          trigger: 'blur'
        }]
      }
    }
  }
}
</script>