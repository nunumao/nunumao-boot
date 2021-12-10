<template>
  <d1-page class="crm-chance" ref="page" title="商机" :apis="{prefix:'manager/crm/chance/'}" permission="crm/sell/chance/" v-model:entity="entity" :rules="rules" :before-add="beforeAdd" :before-edit="beforeEdit" @reset="reset" @after-entity="afterEntity">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column prop="serial" label="商机编号" width="130">
      </el-table-column>
      <el-table-column label="商机">
        <template #default="scope">
          <span style="color:var(--el-color-primary);cursor: pointer" @click="openDetail(scope.row.id)">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="customer" label="客户" width="240">
      </el-table-column>
      <el-table-column prop="user" label="归属人" width="130">
      </el-table-column>
      <el-table-column label="预计收入" width="120">
        <template #default="scope">
          ￥{{scope.row.expectIncome}}
        </template>
      </el-table-column>
      <el-table-column label="进度" width="120">
        <template #default="scope">
          {{['前期接触','机会评估','需求分析','方案提供','多方选择/评估'][scope.row.progress]}}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80">
        <template #default="scope">
          {{['执行中','已成交','已失败'][scope.row.status]}}
        </template>
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="所属用户" prop="userId">
        <el-input v-model="temp.user" placeholder="请选择所属用户" readonly @click="openUser"></el-input>
      </el-form-item>
      <el-form-item label="商机编号" prop="serial">
        <el-input v-model="entity.serial" placeholder="请输入商机编号"></el-input>
      </el-form-item>
      <el-form-item label="商机名称" prop="name">
        <el-input v-model="entity.name" placeholder="请输入商机名称"></el-input>
      </el-form-item>
      <el-form-item label="关联客户" prop="customerId">
        <crm-customer api="manager/crm/chance/customer" v-model:value="temp.customer" placeholder="关联客户"></crm-customer>
      </el-form-item>
      <el-form-item label="关联联系人" prop="contactId">
        <crm-contact :api="contactApi" v-model:value="temp.contact" placeholder="关联联系人"></crm-contact>
      </el-form-item>
      <el-form-item label="预期收入" prop="expectIncome">
        <el-input v-model="entity.expectIncome" placeholder="请输入预期收入">
          <template #prepend>￥</template>
        </el-input>
      </el-form-item>
      <el-form-item label="预期成交时间" prop="expectDate">
        <el-date-picker v-model="entity.expectDate" type="date" placeholder="请选择日期" :editable="false">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="成交机率(%)" prop="probability">
        <div style="width:300px">
          <el-slider v-model="entity.probability" :step="10" show-stops></el-slider>
        </div>
      </el-form-item>
      <el-form-item label="商机进度" prop="progress">
        <el-select v-model="entity.progress" placeholder="请选择商机进度">
          <el-option v-for="(item,index) in ['前期接触','机会评估','需求分析','方案提供','多方选择/评估']" :key="index" :label="item" :value="index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" placeholder="请输入备注" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
      </el-form-item>
    </template>
    <template #attach>
      <d1-user api="manager/crm/chance/user-list" v-model:dialog="userDialog" @result="enterUser"></d1-user>
    </template>
    <template #detail>
      <div class="detail">
        <div class="title p-10">
          <div class="d-flex align-items-end">
            <div class="name">{{detail.name}}</div>
            <div class="ml-10">
              <el-tag type="success" size="mini">{{['前期接触','机会评估','需求分析','方案提供','多方选择/评估'][detail.progress]}}</el-tag>
            </div>
          </div>
          <div class="info mt-20">
            <el-descriptions :border="true">
              <el-descriptions-item label="商家编号">{{detail.serial}}</el-descriptions-item>
              <el-descriptions-item label="所属用户">{{detail.user}}</el-descriptions-item>
              <el-descriptions-item label="共享人数">0 人</el-descriptions-item>
              <el-descriptions-item label="所属客户">{{detail.customer}}</el-descriptions-item>
              <el-descriptions-item label="联系人">{{detail.contact}}</el-descriptions-item>
              <el-descriptions-item label="预计成交日">{{detail.expectDate}}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <div class="mt-20">
          <el-tabs v-model="tabActive" type="border-card" @tab-click="tabClick">
            <el-tab-pane label="商机概况" name="0">
              <div class="summary">
                <div class="d-flex p-10 align-items-center">
                  <div class="d-flex expect-income px-20">
                    <span>预期收入</span>
                    <span style="color:var(--el-color-warning)">￥{{detail.expectIncome}}</span>
                  </div>
                  <div>
                    <el-button type="primary" plain @click="openClose">关闭商机</el-button>
                  </div>
                </div>
                <div class="summary-bottom d-flex p-10">
                  <div class="space">
                    <div class="d-flex ex">
                      <div class="flex-fluid">
                        <el-slider v-model="detail.probability" :step="10" show-stops @change="editDetailByProbability"></el-slider>
                      </div>
                      <div class="percentage d-flex justify-content-center align-items-center">{{detail.probability}}%</div>
                    </div>
                    <div class="lab">成交机率</div>
                  </div>
                  <div class="space">
                    <div class="ex d-flex align-items-center">
                      <div>{{detail.expectDate}}</div>
                      <div class="ml-10">
                        <span v-if="detail.surplus>=0" style="color: var(--el-color-warning)">剩余 {{detail.surplus}} 天</span>
                        <span v-else style="color: var(--el-color-danger)">超时 {{Math.abs(detail.surplus)}} 天</span>
                      </div>
                    </div>
                    <div class="lab">预计成交日</div>
                  </div>
                  <div>
                    <div class="ex">
                      <el-select v-model="detail.progress" placeholder="请选择商机进度" @change="editDetailByProgress">
                        <el-option v-for="(item,index) in ['前期接触','机会评估','需求分析','方案提供','多方选择/评估']" :key="index" :label="item" :value="index">
                        </el-option>
                      </el-select>
                    </div>
                    <div class="lab">商机阶段</div>
                  </div>
                </div>
              </div>
              <div class="mt-20">
                <div>
                  <el-button type="primary" @click="openSign">添加签单条件</el-button>
                  <el-button type="primary">导入签单方案</el-button>
                </div>
                <div class="mt-10">
                  <el-table :data="detail.sign" border>
                    <el-table-column type="index" label="#" width="60" />
                    <el-table-column prop="content" label="签单条件" />
                    <el-table-column label="条件达成" width="100">
                      <template #default="scope">
                        <el-switch v-model="scope.row.status" :active-value="1" :inactive-value="0" @change="signChange(scope.row)" />
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" width="80">
                      <template #default="scope">
                        <el-button type="text" @click="delSign(scope.row)">移除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="联系人" name="1">
              <div>
                <div>
                  <el-button type="primary" @click="openEvent">关联联系人</el-button>
                </div>
                <div class="mt-10">
                  <el-table :data="eventList" border style="width: 100%">
                    <el-table-column type="index" />
                    <el-table-column prop="content" label="记录内容" />
                    <el-table-column prop="createTime" label="所属客户" width="180" />
                    <el-table-column prop="createTime" label="事件时间" width="180" />
                    <el-table-column label="操作" width="100"></el-table-column>
                  </el-table>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="跟进记录" name="2">
              <div>
                <div>
                  <el-button type="primary" @click="openEvent">新增记录</el-button>
                </div>
                <div class="mt-10">
                  <el-table :data="eventList" border style="width: 100%">
                    <el-table-column type="index" />
                    <el-table-column prop="content" label="记录内容" />
                    <el-table-column prop="content" label="跟进人" width="100" />
                    <el-table-column label="跟进渠道" width="100">
                      <template #default="scope">
                        {{['电话联系','上门洽谈','线上沟通','公司会谈','商务接待','邮件沟通'][scope.row.location]}}
                      </template>
                    </el-table-column>
                    <el-table-column label="跟进结果" width="120">
                      <template #default="scope">
                        {{['客户不满意','客户满意','客户无表示','未联系上客户'][scope.row.result]}}
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                      <template #default="scope">
                        <el-button type="text" @click="editEevent(scope.row.id)">编辑</el-button>
                        <el-button type="text" @click="del(scope.row.id)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </el-tab-pane>
            <el-tab-pane label="关联产品" name="3">Task</el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <!-- 关闭弹窗 -->
      <el-dialog v-model="closeDialog" title="成交数据" width="600px" destroy-on-close>
        <div class="pt-10">
          <el-form ref="closeForm" :model="closeEntity" label-width="80px">
            <el-form-item label="状态">
              <el-radio-group v-model="closeEntity.status">
                <el-radio :label="1">成功</el-radio>
                <el-radio :label="2">失败</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="实际收入">
              <el-input v-model="closeEntity.actualIncome" placeholder="请输入实际收入">
                <template #prepend>￥</template>
              </el-input>
            </el-form-item>
            <el-form-item label="成交时间">
              <el-date-picker v-model="closeEntity.actualDate" type="date" placeholder="请选择日期" :editable="false">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="转为合同">
              <el-switch v-model="closeEntity.contract" active-color="#13ce66" inactive-color="#ff4949" />
            </el-form-item>
            <el-form-item v-if="closeEntity.contract" label="合同标题">
              <el-input v-model="closeEntity.name" placeholder="请输入合同标题"></el-input>
            </el-form-item>
            <el-form-item v-if="closeEntity.contract" label="合同客户">
              <el-input v-model="closeEntity.name" placeholder="请输入合同客户"></el-input>
            </el-form-item>
            <el-form-item v-if="closeEntity.contract" label="合同金额">
              <el-input v-model="closeEntity.name" placeholder="请输入合同金额"></el-input>
            </el-form-item>
            <el-form-item v-if="closeEntity.contract" label="签订日期">
              <el-date-picker v-model="closeEntity.actualDate" type="date" placeholder="请选择日期" :editable="false">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 条件弹窗 -->
      <el-dialog v-model="signDialog" title="签单条件" width="600px" destroy-on-close>
        <div class="pt-10">
          <el-form ref="signForm" :model="sign" label-width="80px">
            <el-form-item label="条件内容">
              <el-input v-model="sign.content" type="textarea" placeholder="请输入条件内容" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="addSin">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <!-- 跟进事件 -->
      <el-dialog v-model="eventDialog" title="跟进事件" width="600px" destroy-on-close>
        <div class="pt-10">
          <el-form ref="signForm" :model="eventEntity" label-width="100px">
            <el-form-item label="所属客户" v-if="detail.customerId">
              <el-input v-model="detail.customer" readonly></el-input>
            </el-form-item>
            <el-form-item label="关联联系人" prop="contactId" v-if="detail.customerId">
              <el-select v-model="eventEntity.contactId" placeholder="请选择联系人">
                <el-option v-for="item in eventContactList" :key="item.id" :label="item.name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="事件内容" prop="content">
              <el-input v-model="eventEntity.content" type="textarea" placeholder="请输入事件内容" :autosize="{minRows: 5, maxRows: 5}" resize="none"></el-input>
            </el-form-item>
            <el-form-item label="事件渠道" prop="location">
              <el-select v-model="eventEntity.location" placeholder="请选择事件渠道">
                <el-option v-for="(item,index) in ['电话联系','上门洽谈','线上沟通','公司会谈','商务接待','邮件沟通']" :key="index" :label="item" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="事件结果" prop="result">
              <el-select v-model="eventEntity.result" placeholder="请选择事件结果">
                <el-option v-for="(item,index) in ['客户不满意','客户满意','客户无表示','未联系上客户']" :key="index" :label="item" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="事件时间" prop="eventDate">
              <el-date-picker v-model="eventEntity.eventDate" type="date" placeholder="请选择日期" :editable="false">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="eventSave">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
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
      contactApi: 'manager/crm/chance/contact?customer=',
      userDialog: false,
      temp: {
        customer: {},
        contact: {},
        user: ''
      },
      entity: {
        status: 0
      },
      detail: {
        surplus: 0,
        sign: []
      },
      closeDialog: false,
      closeEntity: {
        status: 1,
        contract: false
      },
      signDialog: false,
      sign: {
        content: ''
      },
      rules: {
        userId: [{
          required: true,
          message: '请选择所属用户'
        }],
        serial: [{
          required: true,
          message: '请输入商机编号'
        }],
        name: [{
          required: true,
          message: '请输入客户名称'
        }],
        customer: [{
          required: true,
          message: '请输入客户公司'
        }],
        expectIncome: [{
          required: true,
          message: '请输入预期收入'
        }],
        expectTime: [{
          required: true,
          message: '请选择预期时间'
        }]
      },
      eventDialog: false,
      eventEntity: {
        customer: '无关联客户',
        eventDate: new Date()
      },
      eventContactList: [],
      eventList: [],
      tabActive: '0'
    }
  },
  watch: {
    'temp.customer': function (v) {
      this.contactApi = this.contactApi + v.id;
    }
  },
  mounted() {
    // console.log(this.__PAGE_URL);
    // 回退

  },
  methods: {
    openUser() {
      this.userDialog = true;
    },
    enterUser(data) {
      this.temp.user = data.name;
      this.entity.userId = data.id;
    },
    beforeAdd() {
      this.beforeAction();
    },
    beforeEdit() {
      this.beforeAction();
    },
    beforeAction() {
      this.entity.customerId = this.temp.customer.id;
      this.entity.contactId = this.temp.contact.id;
      if (this.entity.expectDate instanceof Date) {
        this.entity.expectDate = this.entity.expectDate.format('YYYY-MM-DD')
      }
    },
    afterEntity(data) {
      this.temp.user = data.user;
      this.temp.customer.id = data.customerId;
      this.temp.customer.name = data.customer;
      this.temp.contact.id = data.contactId;
      this.temp.contact.name = data.contact;
    },
    reset() {
      this.temp = {
        customer: {},
        contact: {},
        user: ''
      }
    },
    openDetail(id) {
      this.$refs.page.showDetail();
      this.detail = {
        surplus: 0
      };
      this.$axios.get('manager/crm/chance/detail', { id: id }).then(data => {
        this.detail = data.result;
        this.detail.surplus = new Date().diff(data.result.expectDate, 'd');
        this.loadSign(id);
      }).catch(e => { })
    },
    loadSign(id) {
      this.$axios.get('manager/crm/chance/signList', { id: id }).then(signData => {
        this.detail.sign = signData.result;
      }).catch(e => {
        this.detail.sign = [];
      })
    },
    openClose() {
      this.closeDialog = true;
    },
    openSign() {
      this.sign.content = '';
      this.signDialog = true;
    },
    addSin() {
      if (this.sign.content != '') {
        let sign = {
          content: this.sign.content,
          status: 0
        }
        this.$axios.post('manager/crm/chance/addSign', { id: this.detail.id, condition: JSON.stringify([sign]) }).then(data => {
          this.signDialog = false;
          this.detail.sign.push(sign)
        }).catch(e => {
          this.$message.error(e)
        })
      } else {
        this.signDialog = false;
      }
    },
    signChange(val) {
      this.$axios.put('manager/crm/chance/editSign', { id: val.id, status: val.status }).then(data => {

      }).catch(e => {
        this.$message.error(e)
      })
    },
    delSign(data) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/crm/chance/deleteSign', { id: data.id }).then(data => {
          this.loadSign(this.detail.id);
        }).catch(e => {
          this.$message.error(e)
        })
      }).catch(e => { })
    },
    editDetailByProgress(v) {
      this.$axios.put('manager/crm/chance/editDetail', { id: this.detail.id, progress: v }).then(data => {

      }).catch(e => { })
    },
    editDetailByProbability(v) {
      this.$axios.put('manager/crm/chance/editDetail', { id: this.detail.id, probability: v }).then(data => {

      }).catch(e => { })
    },
    tabClick() {
      switch (this.tabActive) {
        case '2':
          this.loadEvent();
          break;
      }
    },
    openEvent() {
      this.eventDialog = true;
      this.loadEventContact();
    },
    loadEventContact() {
      this.$axios.get('manager/crm/chance/contact', { customer: this.detail.customerId }).then(data => {
        this.eventContactList = data.result;
      }).catch(e => { })
    },
    loadEvent() {
      this.$axios.get('manager/crm/chance/eventList', { chanceId: this.detail.id, page: 1 }).then(data => {
        this.eventList = data.result;
      }).catch(e => { })
    },
    editEevent(id) {
      this.eventDialog = true;
      this.loadEventContact();
      this.$axios.get('manager/crm/chance/editEevent', { id, id }).then(data => {
        data.result.eventDate = new Date(data.result.eventDate)
        this.eventEntity = data.result;
      }).catch(e => { })
    },
    eventSave() {
      this.eventEntity.chanceId = this.detail.id;
      this.eventEntity.customerId = this.detail.customerId;
      this.eventEntity.contactId = this.detail.contactId;
      if (this.eventEntity.eventDate) {
        this.eventEntity.eventDate = this.eventEntity.eventDate.format('YYYY-MM-DD');
      }
      if (this.eventEntity.id) {
        this.$axios.put('manager/crm/chance/editEvent', this.eventEntity).then(data => {
          this.$message({
            message: data.message,
            type: 'success',
            duration: 1000,
            onClose: () => {
              this.eventDialog = false;
            }
          })
          this.loadEvent();
        }).catch(e => {
          this.$message.error(e)
        })
      } else {
        this.$axios.post('manager/crm/chance/addEvent', this.eventEntity).then(data => {
          this.$message({
            message: data.message,
            type: 'success',
            duration: 1000,
            onClose: () => {
              this.eventDialog = false;
            }
          })
          this.loadEvent();
        }).catch(e => {
          this.$message.error(e)
        })
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-chance {
  .detail {
    .title {
      border: solid 1px var(--el-border-color-base);
      .name {
        font-size: 18px;
      }
    }
    .summary {
      border: solid 1px var(--el-border-color-base);
      .space {
        border-right: solid 1px var(--el-border-color-base);
      }
      .expect-income {
        font-size: 18px;
      }
      &-bottom {
        border-top: solid 1px var(--el-border-color-base);
        & > div {
          flex: 0 0 280px;
          padding: 0 20px;
          .ex {
            height: 38px;
          }
          .lab {
            margin-top: 5px;
            color: var(--el-text-color-placeholder);
          }
          .percentage {
            width: 40px;
          }
        }
      }
    }
  }
}
</style>
<style lang="scss">
.crm-chance {
  .el-tabs--border-card {
    box-shadow: none !important;
  }
  .info {
    .el-descriptions__label {
      width: 150px;
    }
  }
}
</style>