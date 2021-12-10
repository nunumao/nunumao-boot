<template>
  <d1-page :modal="true" title="线索" :apis="{prefix:'manager/crm/lead/'}" permission="crm/sell/lead/" v-model:entity="entity" :rules="rules">
    <!-- 列表 -->
    <template v-slot:query="{query}">
      <div class="d-flex">
        <d1-query-item label="线索类型">
          <el-select style="width:150px !important" v-model="query.type" placeholder="请选择线索类型">
            <el-option label="全部线索" :value="0"></el-option>
            <el-option label="我的线索" :value="1"></el-option>
            <el-option label="下属线索" :value="2"></el-option>
            <el-option label="共享给我" :value="3"></el-option>
          </el-select>
        </d1-query-item>
        <d1-query-item label="线索状态">
          <el-select style="width:150px !important" v-model="query.status" placeholder="请选择线索状态">
            <el-option label="不限" :value="0"></el-option>
            <el-option label="待处理" :value="1"></el-option>
            <el-option label="跟进中" :value="2"></el-option>
            <el-option label="已关闭" :value="3"></el-option>
            <el-option label="已转化" :value="4"></el-option>
          </el-select>
        </d1-query-item>
      </div>
    </template>
    <template v-slot:query-more="{query}">
      <div class="d-flex">
        <d1-query-item label="创建时间">
          <el-select style="width:150px !important" v-model="query.create" placeholder="请选择创建时间">
            <el-option label="不限" :value="0"></el-option>
            <el-option label="今天" :value="1"></el-option>
            <el-option label="昨天" :value="2"></el-option>
            <el-option label="本周" :value="3"></el-option>
            <el-option label="上周" :value="5"></el-option>
            <el-option label="本月" :value="6"></el-option>
            <el-option label="上月" :value="7"></el-option>
            <el-option label="自定义" :value="8"></el-option>
          </el-select>
        </d1-query-item>
        <d1-query-item label="更新时间">
          <el-select style="width:150px !important" v-model="query.update" placeholder="请选择更新时间">
            <el-option label="不限" :value="0"></el-option>
            <el-option label="今天" :value="1"></el-option>
            <el-option label="昨天" :value="2"></el-option>
            <el-option label="本周" :value="3"></el-option>
            <el-option label="上周" :value="5"></el-option>
            <el-option label="本月" :value="6"></el-option>
            <el-option label="上月" :value="7"></el-option>
            <el-option label="自定义" :value="8"></el-option>
          </el-select>
        </d1-query-item>
      </div>
    </template>
    <template v-slot:column>
      <el-table-column prop="customer" label="客户名称">
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <span style="color:#dc3545" v-if="scope.row.status == 0">待处理</span>
          <span style="color:#dc3545" v-if="scope.row.status == 1">跟进中</span>
          <span style="color:#28a745" v-if="scope.row.status == 2">已关闭</span>
        </template>
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="客户名称" prop="contact">
        <el-input v-model="entity.contact" placeholder="请输入客户名称"></el-input>
      </el-form-item>
      <el-form-item label="客户公司" prop="customer">
        <el-input v-model="entity.customer" placeholder="请输入客户公司"></el-input>
      </el-form-item>
      <el-form-item label="客户手机" prop="phone">
        <el-input v-model="entity.phone" placeholder="请输入客户手机"></el-input>
      </el-form-item>
      <el-form-item label="办公电话" prop="telephone">
        <el-input v-model="entity.telephone" placeholder="请输入办公电话"></el-input>
      </el-form-item>
      <el-form-item label="客户邮箱" prop="email">
        <el-input v-model="entity.email" placeholder="请输入客户邮箱"></el-input>
      </el-form-item>
      <el-form-item label="客户地址" prop="address">
        <el-input v-model="entity.address" placeholder="请输入客户地址"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="entity.status">
          <el-radio :label="0">待处理</el-radio>
          <el-radio :label="1">跟进中</el-radio>
          <el-radio :label="2">已关闭</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entity.remark" placeholder=""></el-input>
      </el-form-item>
    </template>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        status: 0
      },
      rules: {
        contact: [{
          required: true,
          message: '请输入客户名称'
        }],
        customer: [{
          required: true,
          message: '请输入客户公司'
        }]
      }
    }
  }
}
</script>
<style lang="scss" scoped>
</style>