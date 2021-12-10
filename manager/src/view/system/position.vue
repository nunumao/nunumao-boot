<template>
  <d1-page :modal="true" title="职务" :apis="{prefix:'manager/system/position/'}" permission="system/position/" v-model:entity="entity" :rules="rules">
    <!-- 列表 -->
    <template v-slot:query="{query}">
      <div class="d-flex">
        <d1-query-item label="职务名称">
          <el-input v-model="query.name" prefix-icon="el-icon-search" :clearable="true" placeholder="请输入职务名称"></el-input>
        </d1-query-item>
      </div>
    </template>
    <template v-slot:query-more="{query}">
      <div class="d-flex">
        <d1-query-item label="职务编码">
          <el-input v-model="query.code" prefix-icon="el-icon-search" :clearable="true" placeholder="请输入职务编码"></el-input>
        </d1-query-item>
      </div>
    </template>
    <template v-slot:column>
      <el-table-column prop="code" label="职务编码" width="200">
      </el-table-column>
      <el-table-column prop="name" label="职务名称">
      </el-table-column>
      <el-table-column prop="postRank" label="职务等级" width="200">
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="职务编码" prop="code">
        <el-input v-model="entity.code" placeholder="请输入职务编码"></el-input>
      </el-form-item>
      <el-form-item label="职务名称" prop="name">
        <el-input v-model="entity.name" placeholder="请输入职务名称"></el-input>
      </el-form-item>
      <el-form-item label="职务等级" prop="postRank">
        <el-select v-model="entity.postRank" placeholder="请选择职务等级">
          <el-option :label="'员级'" :value="0">
          </el-option>
        </el-select>
      </el-form-item>
    </template>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        code: '',
        name: '',
        postRank: null
      },
      rules: {
        code: [{
          required: true,
          message: '请输入职务编码',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入职务名称',
          trigger: 'blur'
        }],
        postRank: [{
          required: true,
          message: '请选择职务级别',
          trigger: 'blur'
        }]
      }
    }
  }
}
</script>