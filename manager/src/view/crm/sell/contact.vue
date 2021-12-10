<template>
  <d1-page class="crm-contact" title="联系人" :apis="{prefix:'manager/crm/contact/'}" permission="crm/sell/contact/" v-model:entity="entity" :rules="rules" :before-add="beforeAdd" :before-edit="beforeEdit" @reset="reset" @after-detail="afterDetail">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column prop="name" label="联系人">
      </el-table-column>
      <el-table-column prop="company" label="客户" width="250">
      </el-table-column>
      <el-table-column prop="phone" label="手机" width="150">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="180">
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="所属用户" prop="userId">
        <el-input v-model="temp.user" placeholder="请选择所属用户" readonly @click="openUser"></el-input>
      </el-form-item>
      <el-form-item label="编号" prop="serial">
        <el-input v-model="entity.serial" placeholder="请输入编号"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="entity.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-radio-group v-model="entity.gender">
          <el-radio :label="0">未知</el-radio>
          <el-radio :label="1">先生</el-radio>
          <el-radio :label="2">女士</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="关联客户" prop="customerId">
        <crm-customer api="manager/crm/contact/customer" v-model:value="temp.customer" placeholder="关联客户"></crm-customer>
      </el-form-item>
      <el-form-item label="联系方式" prop="telephone">
        <div style="width:500px">
          <table class="link-table table table-bordered">
            <thead>
              <tr>
                <th width="100">类型</th>
                <th>数据</th>
                <th width="60">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in temp.link" :key="item">
                <td>
                  <el-select size="small" v-model="item.type">
                    <el-option v-for="(jtem,jndex) in ['手机', '微信', '地址', '电话', 'QQ', '邮箱']" :key="jndex" :label="jtem" :value="jndex">
                    </el-option>
                  </el-select>
                </td>
                <td style="color:var(--el-text-color-regular)">
                  <el-input v-model="item.dataValue" style="width:100% !important" size="small" placeholder="请输入数据值" />
                </td>
                <td>
                  <a style="color:var(--el-color-primary)" href="javascript:;" @click="delLink(item)">删除</a>
                </td>
              </tr>
              <tr>
                <td colspan="3"><a style="color:var(--el-color-primary)" href="javascript:;" @click="addLink"><i class="el-icon-plus"></i> 增加一项</a></td>
              </tr>
            </tbody>
          </table>
        </div>
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker v-model="entity.birthday" type="date" placeholder="请选择日期" :editable="false">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="爱好" prop="hobby">
        <el-input v-model="entity.hobby" placeholder="请输入联系人爱好"></el-input>
      </el-form-item>
      <el-form-item label="职务" prop="position">
        <el-input v-model="entity.position" placeholder="请输入联系人职务"></el-input>
      </el-form-item>
      <el-form-item label="部门" prop="department">
        <el-input v-model="entity.department" placeholder="请输入联系人部门"></el-input>
      </el-form-item>
      <el-form-item label="角色关系" prop="role">
        <el-select v-model="entity.role" placeholder="请选择角色关系">
          <el-option v-for="(item,index) in ['经办人','决策人','使用人','意见影响人','商务决策']" :key="index" :label="item" :value="index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="亲密程度" prop="relation">
        <el-select v-model="entity.relation" placeholder="请选择亲密程度">
          <el-option v-for="(item,index) in ['初相识','一般关系','朋友关系','好友关系']" :key="index" :label="item" :value="index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" placeholder="请输入备注" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
      </el-form-item>
    </template>
    <template #attach>
      <d1-user api="manager/crm/contact/user-list" v-model:dialog="userDialog" @result="enterUser"></d1-user>
    </template>
  </d1-page>
</template>
<script>
import customer from '../components/customer.vue'
export default {
  components: {
    crmCustomer: customer
  },
  data() {
    return {
      userDialog: false,
      temp: {
        link: [],
        customer: {},
        user: ''
      },
      entity: {
        userId: '',
        gender: 0,
        status: 0
      },
      rules: {
        userId: [{
          required: true,
          message: '请输入选择所属用户'
        }],
        name: [{
          required: true,
          message: '请输入联系人姓名'
        }],
        serial: [{
          required: true,
          message: '请输入联系人编号'
        }]
      }
    }
  },
  methods: {
    openUser() {
      this.userDialog = true;
    },
    enterUser(data) {
      this.temp.user = data.name;
      this.entity.userId = data.id;
    },
    addLink() {
      this.temp.link.push({
        id: '',
        type: 0,
        dataValue: ''
      })
    },
    delLink(data) {
      this.temp.link.remove(data);
    },
    beforeAdd() {
      this.beforeAction();
    },
    beforeEdit() {
      this.beforeAction();
    },
    beforeAction() {
      this.entity.customerId = this.temp.customer.id;
      if (this.entity.birthday instanceof Date) {
        this.entity.birthday = this.entity.birthday.format('YYYY-MM-DD');
      }
      this.entity.link = JSON.stringify(this.temp.link);
    },
    afterDetail(data) {
      this.temp.user = data.user;
      this.temp.customer.id = data.customerId;
      this.temp.customer.name = data.customer;
      this.temp.link = data.linkList;
    },
    reset() {
      this.temp = {
        link: [],
        customer: {},
        user: ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-contact {
  .link-table {
    td {
      padding: 5px;
    }
  }
}
</style>