<template>
  <d1-page :modal="true" title="跟进" :apis="{prefix:'manager/crm/event/'}" permission="crm/sell/event/" v-model:entity="entity" :rules="rules">
    <!-- 列表 -->
    <template #tools>
      <div></div>
    </template>
    <template v-slot:column>
      <el-table-column prop="content" label="跟进记录">
      </el-table-column>
      <el-table-column prop="user" label="所属用户">
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
      <el-form-item label="跟进记录" prop="content">
        <el-input v-model="entity.content" type="textarea" placeholder="请输入跟进记录" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
      </el-form-item>
      <el-form-item label="所属客户" prop="customer">
        <crm-customer api="manager/crm/chance/customer" v-model:value="temp.customer" placeholder="关联客户"></crm-customer>
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
import customer from '../components/customer.vue'
import contact from '../components/contact.vue'
export default {
  components: {
    crmCustomer: customer,
    crmContact: contact
  },
  data() {
    return {
      temp: {
        customer: {},
        contact: {},
        user: ''
      },
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