<template>
  <div class="d-flex flex-column">
    <div>
      <d1-page :padding="0">
        <div class="px-20 pt-10">
          <el-tabs>
            <el-tab-pane label="全部线索" name="first"></el-tab-pane>
            <el-tab-pane label="我的线索" name="second"></el-tab-pane>
            <el-tab-pane label="下属线索" name="third"></el-tab-pane>
            <el-tab-pane label="共享给我" name="fourth"></el-tab-pane>
          </el-tabs>
        </div>
        <div class="px-20 pb-10">
          <div class="d-flex">
            <div class="search-label font-size-14 d-flex align-items-center pr-20">线索状态</div>
            <div>
              <el-radio-group>
                <el-radio :label="0">不限</el-radio>
                <el-radio :label="1">待处理</el-radio>
                <el-radio :label="2">跟进中</el-radio>
                <el-radio :label="3">已关闭</el-radio>
                <el-radio :label="4">已转化</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="d-flex">
            <div class="search-label font-size-14 d-flex align-items-center pr-20">创建时间</div>
            <div>
              <el-radio-group>
                <el-radio :label="0">不限</el-radio>
                <el-radio :label="1">今天</el-radio>
                <el-radio :label="2">昨天</el-radio>
                <el-radio :label="3">本周</el-radio>
                <el-radio :label="4">上周</el-radio>
                <el-radio :label="5">本月</el-radio>
                <el-radio :label="6">上月</el-radio>
                <el-radio :label="7">自定义</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="d-flex">
            <div class="search-label font-size-14 d-flex align-items-center pr-20">更新时间</div>
            <div>
              <el-radio-group>
                <el-radio :label="0">不限</el-radio>
                <el-radio :label="1">今天</el-radio>
                <el-radio :label="2">昨天</el-radio>
                <el-radio :label="3">本周</el-radio>
                <el-radio :label="4">上周</el-radio>
                <el-radio :label="5">本月</el-radio>
                <el-radio :label="6">上月</el-radio>
                <el-radio :label="7">自定义</el-radio>
              </el-radio-group>
            </div>
          </div>
          <div class="d-flex">
            <div class="search-label font-size-14 d-flex align-items-center pr-20">归属于</div>
            <div>
              <d1-user></d1-user>
            </div>
          </div>
        </div>
      </d1-page>
    </div>
    <div class="mt-10">
      <d1-page :modal="true" title="客户" :apis="{prefix:'manager/crm/customer/'}" permission="crm/sell/customer" v-model:entity="entity" :rules="rules" @add="add" @edit="edit">
        <!-- 列表 -->
        <template v-slot:column>
          <el-table-column prop="serial" label="客户编号" width="140">
          </el-table-column>
          <el-table-column prop="fullName" label="客户名称">
          </el-table-column>
          <el-table-column prop="customer" label="联系人" width="140">
          </el-table-column>
          <el-table-column prop="customer" label="归属人" width="130">
          </el-table-column>
          <el-table-column prop="customer" label="最近跟进" width="180">
          </el-table-column>
          <el-table-column prop="customer" label="跟进时间" width="150">
          </el-table-column>
        </template>
        <!-- 表单 -->
        <template v-slot:field>
          <el-form-item label="客户编号" prop="serial">
            <el-input v-model="entity.serial" placeholder="请输入客户编号"></el-input>
          </el-form-item>
          <el-form-item label="客户名称" prop="fullName">
            <el-input v-model="entity.fullName" placeholder="公司名称/客户姓名"></el-input>
          </el-form-item>
          <el-form-item label="客户状态" prop="status">
            <el-select v-model="entity.status" placeholder="请选择客户状态">
              <el-option v-for="item in status" :key="item.value" :label="item.key" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户分级" prop="level">
            <el-select v-model="entity.level" placeholder="请选择客户分级">
              <el-option v-for="item in level" :key="item.value" :label="item.key" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="联系方式" prop="phone">
            <el-input v-model="entity.phone" placeholder="请输入联系方式"></el-input>
          </el-form-item>
          <el-form-item label="客户地址" prop="address">
            <el-input v-model="entity.address" placeholder="请输入客户地址"></el-input>
          </el-form-item>
          <el-form-item label="邮政编码" prop="email">
            <el-input v-model="entity.email" placeholder="请输入邮政编码"></el-input>
          </el-form-item>
          <el-form-item label="官方网站" prop="email">
            <el-input v-model="entity.email" placeholder="请输入官方网站"></el-input>
          </el-form-item>
          <el-form-item label="客户邮箱" prop="email">
            <el-input v-model="entity.email" placeholder="请输入客户邮箱"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="entity.remark" placeholder=""></el-input>
          </el-form-item>
        </template>
      </d1-page>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      entity: {
        fullName: '',
        serial: '',
        level: null,
        status: null
      },
      rules: {
        fullName: [{
          required: true,
          message: '请输入客户名称'
        }],
        serial: [{
          required: true,
          message: '请输入客户编号'
        }],
        level: [{
          required: true,
          message: '请选择客户分级'
        }],
        status: [{
          required: true,
          message: '请选择客户状态'
        }],
        serial: [{
          required: true,
          message: '请输入客户编号'
        }]
      },
      level: [],
      status: []
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
      this.$axios.get('manager/crm/customer/level').then(data => {
        this.level = data.result;
      })

      this.$axios.get('manager/crm/customer/status').then(data => {
        this.status = data.result;
      })
    }
  }
}
</script>
<style lang="scss" scoped>
</style>