<template>
  <d1-page ref="page" class="crm-contract" title="合同" :apis="{prefix:'manager/crm/contract/'}" permission="crm/finance/contract/" v-model:entity="entity" :rules="rules" :before-add="beforeAdd" :before-edit="beforeEdit" @reset="reset" @after-entity="afterEntity">
    <!-- 列表 -->
    <template v-slot:column>
      <el-table-column label="合同名称">
        <template #default="scope">
          <span style="color:var(--el-color-primary);cursor: pointer" @click="openDetail(scope.row.id)">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="customer" label="合同客户" width="200">
      </el-table-column>
      <el-table-column prop="user" label="所属用户" width="100">
      </el-table-column>
      <el-table-column label="已收款" width="160">
        <template #default="scope">
          ￥{{scope.row.collectPrice||0}}<span style="color:var(--el-color-danger)">({{scope.row.collectPrice/scope.row.totalPrice*100}}%)</span>
        </template>
      </el-table-column>
      <el-table-column label="合同金额" width="120">
        <template #default="scope">
          ￥{{scope.row.totalPrice||0}}
        </template>
      </el-table-column>
    </template>
    <!-- 表单 -->
    <template v-slot:field>
      <el-form-item label="所属用户" prop="userId">
        <d1-suggest-user api="manager/crm/contact/" v-model:value="entity.userId"></d1-suggest-user>
      </el-form-item>
      <el-form-item label="合同编号" prop="serial">
        <el-input v-model="entity.serial" placeholder="请输入合同编号"></el-input>
      </el-form-item>
      <el-form-item label="合同名称" prop="name">
        <el-input v-model="entity.name" placeholder="请输入合同名称"></el-input>
      </el-form-item>
      <el-form-item label="关联客户" prop="customerId">
        <d1-suggest api="manager/crm/contract/customer" v-model:value="entity.customerId" :column="[{ label: '名称', prop: 'fullName' }]" field="fullName" placeholder="客户"></d1-suggest>
      </el-form-item>
      <el-form-item label="关联商机" prop="chanceId">
        <d1-suggest api="manager/crm/contract/chance" v-model:value="entity.chanceId" placeholder="商机"></d1-suggest>
      </el-form-item>
      <el-form-item label="合同类型" prop="type">
        <el-select v-model="entity.type" placeholder="请选择合同类型">
          <el-option v-for="(item,index) in ['产品合同','服务合同','实施合同','续费合同','代理合同']" :key="index" :label="item" :value="index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="签订日期" prop="signDate">
        <el-date-picker v-model="entity.signDate" type="date" placeholder="请选择日期" :editable="false">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="合同期限" prop="expireDate">
        <el-date-picker v-model="entity.expireDate" type="date" placeholder="留空为永久合同" :editable="false">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单明细" prop="order">
        <div class="order-field">
          <table class="order-table table table-bordered">
            <thead>
              <tr>
                <th>产品或服务</th>
                <th width="110">标准价</th>
                <th width="110">成交价</th>
                <th width="90">数量</th>
                <th width="80">单位</th>
                <th width="60">操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in temp.orderList" :key="item">
                <td>
                  <el-input :class="{validate:item.validate[0]}" style="width:100% !important" v-model="item.name" placeholder="请输入产品或服务">
                    <template #suffix>
                      <div class="order-search-icon">
                        <i class="el-icon-search"></i>
                      </div>
                    </template>
                  </el-input>
                </td>
                <td>
                  <el-input :class="{validate:item.validate[1]}" v-model="item.originPrice" />
                </td>
                <td>
                  <el-input :class="{validate:item.validate[2]}" v-model="item.price" @change="computeTotal" />
                </td>
                <td>
                  <el-input :class="{validate:item.validate[3]}" v-model="item.quantity" @change="computeTotal" />
                </td>
                <td>
                  <el-input :class="{validate:item.validate[4]}" v-model="item.unit" />
                </td>
                <td>
                  <a style="color:var(--el-color-primary)" href="javascript:;" @click="delOrder(item)">删除</a>
                </td>
              </tr>
              <tr>
                <td colspan="4" class="add"><a style="color:var(--el-color-primary)" href="javascript:;" @click="addOrder()"><i class="el-icon-plus"></i> 增加一项</a></td>
                <td colspan="2" class="text-right total">合计:￥{{temp.total}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </el-form-item>
      <el-form-item label="商务费用" prop="rebatePrice">
        <el-input v-model="entity.rebatePrice" placeholder="请输入商务费用"></el-input>
      </el-form-item>
      <el-form-item label="合同金额" prop="totalPrice">
        <el-input v-model="entity.totalPrice" placeholder="请输入合同金额"></el-input>
      </el-form-item>
      <el-form-item label="合同状态" prop="status">
        <el-select v-model="entity.status" placeholder="请选择合同状态">
          <el-option v-for="(item,index) in ['未执行','执行中','正常结束','意外终止']" :key="index" :label="item" :value="index">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="合同备注" prop="remark">
        <el-input v-model="entity.remark" type="textarea" placeholder="请输入合同备注" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
      </el-form-item>
    </template>
    <template #detail>
      <div class="detail">
        <div class="title p-10">
          <div class="d-flex align-items-end">
            <div class="name">{{detail.name}}</div>
            <div class="ml-10">
              <el-tag type="success" size="mini">{{['产品合同','服务合同','实施合同','续费合同','代理合同'][detail.type]}}</el-tag>
            </div>
          </div>
          <div class="info mt-20">
            <el-descriptions :border="true">
              <el-descriptions-item label="商家编号">{{detail.serial}}</el-descriptions-item>
              <el-descriptions-item label="所属用户">{{detail.user}}</el-descriptions-item>
              <el-descriptions-item label="共享人数">0 人</el-descriptions-item>
              <el-descriptions-item label="所属客户">{{detail.customer}}</el-descriptions-item>
              <el-descriptions-item label="所属商机">{{detail.chance}}</el-descriptions-item>
              <el-descriptions-item label="合同金额">￥{{detail.totalPrice||0}}</el-descriptions-item>
              <el-descriptions-item label="销售费用">￥{{detail.rebatePrice||0}}</el-descriptions-item>
              <el-descriptions-item label="合同签订">{{detail.signDate}}</el-descriptions-item>
              <el-descriptions-item label="合同期限">{{detail.expireDate}}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
        <div class="mt-20">
          <el-tabs type="border-card">
            <el-tab-pane label="收款记录">
              <div class="summary">
                <div class="d-flex p-10 align-items-center">
                  <div class="d-flex expect-income px-20">
                    <span class="mr-10">合同金额</span>
                    <span style="color:var(--el-color-warning)">￥{{detail.totalPrice}}</span>
                  </div>
                  <div>
                    <el-button type="primary" plain @click="openClose">结束合同</el-button>
                  </div>
                </div>
                <div class="summary-bottom p-10">
                  <div class="d-flex">
                    <div class="flex-fluid">
                      <div class="priced">￥{{detail.priced||0}}</div>
                      <div class="lab">已收款</div>
                    </div>
                    <div class="flex-fluid">
                      <div class="text-center perc">{{detail.percentage||0}}%</div>
                      <div class="lab text-center">收款进度</div>
                    </div>
                    <div class="flex-fluid">
                      <div class="text-right pricew">￥{{detail.surplusPrice}}</div>
                      <div class="lab text-right">待收款</div>
                    </div>
                  </div>
                  <div class="mt-10 pb-10">
                    <el-progress :text-inside="true" :stroke-width="24" :percentage="detail.percentage||0" status="success" />
                  </div>
                </div>
              </div>
              <div class="mt-20">
                <div>
                  <el-button type="primary" @click="openReceipt">添加收款</el-button>
                </div>
                <div class="mt-10">
                  <el-table :data="detail.receiptList" border>
                    <el-table-column type="index" label="#" width="60" />
                    <el-table-column prop="priceDate" label="收款时间" width="120">
                    </el-table-column>
                    <el-table-column prop="price" label="收款金额" />
                    <el-table-column label="收款方式" width="140">
                      <template #default="scope">
                        {{['银行转账','微信支付','支付宝','网银转账','现金','其他'][scope.row.payType]}}
                      </template>
                    </el-table-column>
                    <el-table-column label="票据类型" width="100">
                      <template #default="scope">
                        {{['发票','收据','其他'][scope.row.billType]}}
                      </template>
                    </el-table-column>
                    <el-table-column label="操作" width="100">
                      <template #default="scope">
                        <el-button type="text" @click="openReceipt(scope.row.id)">编辑</el-button>
                        <el-button type="text" @click="delReceipt(scope.row.id)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>
      <el-dialog v-model="closeDialog" title="成交数据" width="600px" destroy-on-close>
        <div class="pt-10">
          <el-form ref="closeForm" :model="closeEntity" label-width="80px">
            <el-form-item label="合同状态" prop="status">
              <el-select v-model="closeEntity.status" placeholder="请选择合同状态">
                <el-option v-for="(item,index) in ['未执行','执行中','正常结束','意外终止']" :key="index" :label="item" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
      <el-dialog v-model="receiptDialog" title="收款数据" width="600px" destroy-on-close>
        <div class="pt-10">
          <el-form ref="receiptForm" :model="receiptEntity" :rules="receiptRules" label-width="80px">
            <el-form-item label="收款用户" prop="userId">
              <d1-suggest-user api="manager/crm/receipt/" v-model:value="receiptEntity.userId"></d1-suggest-user>
            </el-form-item>
            <el-form-item label="收款金额" prop="price">
              <el-input v-model="receiptEntity.price" placeholder="请输入收款金额">
                <template #prepend>￥</template>
              </el-input>
            </el-form-item>
            <el-form-item label="收款时间" prop="priceDate">
              <el-date-picker v-model="receiptEntity.priceDate" type="date" placeholder="请选择日期" :editable="false">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="收款方式" prop="payType">
              <el-select v-model="receiptEntity.payType" placeholder="请选择收款方式">
                <el-option v-for="(item,index) in ['银行转账','微信支付','支付宝','网银转账','现金','其他']" :key="index" :label="item" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="票据类型" prop="billType">
              <el-select v-model="receiptEntity.billType" placeholder="请选择票据类型">
                <el-option v-for="(item,index) in ['发票','收据','其他']" :key="index" :label="item" :value="index">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-if="receiptEntity.billType == 0" label="票据编号" prop="billSerial">
              <el-input v-model="receiptEntity.billSerial" placeholder="请输入票据编号">
              </el-input>
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="receiptEntity.remark" type="textarea" placeholder="请输入收款备注" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveReceipt">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </template>
  </d1-page>
</template>
<script>
import customer from '../components/customer.vue'
import chance from '../components/chance.vue'
export default {
  components: {
    crmCustomer: customer,
    crmChance: chance
  },
  data() {
    return {
      userDialog: false,
      receiptDialog: false,
      temp: {
        user: "",
        customer: {},
        chance: {},
        orderList: [],
        total: 0
      },
      entity: {
        type: null,
        orderList: []
      },
      detail: {

      },
      receiptEntity: {
        priceDate: null,
        payType: null
      },
      closeEntity: {
        status: null
      },
      rules: {
        userId: [{
          required: true,
          message: '请选择所属用户'
        }],
        serial: [{
          required: true,
          message: '请输入合同编号'
        }],
        name: [{
          required: true,
          message: '请输入合同名称'
        }],
        customerId: [{
          required: true,
          message: '请选择关联客户'
        }],
        chanceId: [{
          required: true,
          message: '请选择关联商机'
        }],
        type: [{
          required: true,
          message: '请选择合同类型'
        }],
        signDate: [{
          required: true,
          message: '请选择合同类型'
        }],
        rebatePrice: [{
          required: true,
          message: '请输入商务费用'
        }],
        totalPrice: [{
          required: true,
          message: '请输入合同金额'
        }],
        status: [{
          required: true,
          message: '请选择合同状态'
        }],
        order: [{
          validator: (rule, value, callback) => {
            const filed = ['name', 'originPrice', 'price', 'quantity', 'unit'];
            let required = data => {
              return data ? true : false;
            }
            let success = true;
            for (let item of this.temp.orderList) {
              let i = -1;
              for (let key in item) {
                if (filed.includes(key) && typeof (item[key]) != 'undefined') {
                  let flag = required(item[key]);
                  console.log('验证', key, flag);
                  i++;
                  item.validate[i] = flag;
                  success &= flag;
                }
              }
            }
            if (success) {
              callback();
            } else {
              callback(new Error(' '));
            }
          },
          trigger: 'blur'
        }]
      },
      receiptRules: {
        userId: [{
          required: true,
          message: '请选择收款用户'
        }],
        price: [{
          required: true,
          message: '请输入收款金额'
        }],
        priceDate: [{
          required: true,
          message: '请选择收款时间'
        }],
        payType: [{
          required: true,
          message: '请选择收款类型'
        }],
        billType: [{
          required: true,
          message: '请选择票据类型'
        }],
        billSerial: [{
          required: true,
          message: '请选择票据编号'
        }]
      }
    }
  },
  watch: {
    'temp.customer': function (v) {
      if (v) {
        this.entity.customerId = v.id;
      }
    },
    'temp.chance': function (v) {
      if (v) {
        this.entity.chanceId = v.id;
      }
    }
  },
  methods: {
    beforeAdd() {
      this.beforeAction();
    },
    beforeEdit() {
      this.beforeAction();
    },
    beforeAction() {
      if (this.entity.signDate instanceof Date) {
        this.entity.signDate = this.entity.signDate.format('YYYY-MM-DD')
      }
      if (this.entity.expireDate instanceof Date) {
        this.entity.expireDate = this.entity.expireDate.format('YYYY-MM-DD')
      }
      this.temp.orderList.forEach(item => {
        let price = parseFloat(item.price) || 0;
        let quantity = parseFloat(item.quantity) || 0;
        item.totalPrice = price * quantity;
      })
      this.entity.orderList = JSON.stringify(this.temp.orderList);
    },
    openUser() {
      this.userDialog = true;
    },
    enterUser(data) {
      this.temp.user = data.name;
      this.entity.userId = data.id;
    },
    reset() {
      this.temp = {
        user: "",
        customer: {},
        chance: {},
        orderList: [],
        total: 0
      }
    },
    addOrder() {
      this.temp.orderList.push({ name: '', originPrice: '', price: '', quantity: '', unit: '', validate: [] })
    },
    delOrder(data) {
      this.temp.orderList.remove(data);
    },
    afterEntity(data) {
      if (Array.isArray(data.orderList)) {
        this.temp.orderList = data.orderList;
        this.temp.orderList.forEach(item => {
          item.validate = [];
        })
      }
      this.computeTotal();
    },
    computeTotal() {
      let total = 0;
      this.temp.orderList.forEach(item => {
        let price = parseFloat(item.price) || 0;
        let quantity = parseFloat(item.quantity) || 0;
        item.totalPrice = price * quantity;
        total += item.totalPrice;
      })
      this.temp.total = total;
    },
    openDetail(id) {
      this.$refs.page.showDetail();
      this.loadDetail(id);
    },
    loadDetail(id) {
      this.$axios.get('manager/crm/contract/detail', { id: id }).then(data => {
        data.result.priced = 0;
        if (data.result.receiptList && Array.isArray(data.result.receiptList)) {
          data.result.receiptList.forEach(item => {
            data.result.priced += item.price;
          })
        }
        data.result.surplusPrice = data.result.totalPrice - data.result.rebatePrice - data.result.priced;
        data.result.percentage = Math.floor(data.result.priced / (data.result.totalPrice - data.result.rebatePrice) * 100);
        this.detail = data.result;
      }).catch(e => { })
    },
    openClose() {

    },
    openReceipt(id) {
      this.receiptDialog = true;
      this.receiptEntity = {
        priceDate: null,
        payType: null
      }
      if (id) {
        this.$axios.get('manager/crm/receipt/edit', { id }).then(data => {
          this.receiptEntity = data.result;
        }).catch(e => { })
      }
    },
    closeReceipt() {
      this.receiptDialog = false;
    },
    saveReceipt() {
      this.$refs['receiptForm'].validate(valid => {
        if (valid) {
          this.receiptEntity.contractId = this.detail.id;
          if (this.receiptEntity.priceDate instanceof Date) {
            this.receiptEntity.priceDate = this.receiptEntity.priceDate.format('YYYY-MM-DD');
          }
          if (this.receiptEntity.id) {
            this.$axios.put('manager/crm/receipt/edit', this.receiptEntity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadDetail(this.detail.id);
                  this.closeReceipt();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          } else {
            this.$axios.post('manager/crm/receipt/add', this.receiptEntity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadDetail(this.detail.id);
                  this.closeReceipt();
                }
              })
            }).catch(e => {
              this.$message.error(e)
            })
          }
        } else {
          console.log('验证未通过');
        }
      })
    },
    delReceipt(id) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/crm/receipt/delete', { id }).then(data => {
          this.$message.success(data.message);
          this.loadDetail(this.detail.id);
        }).catch(e => {
          this.$message.error(e);
        })
      }).catch(e => { })
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-contract {
  .order-field {
    width: 800px;
    .validate {
      input {
        border: var(--el-input-border) !important;
      }
    }
    .order-table {
      tr {
        td {
          padding: 10px;
        }
        &:last-child {
          td {
            padding: 5px 10px;
          }
        }
      }
      .order-search-icon {
        padding: 0 5px;
        cursor: pointer;
      }
      .add {
        border-right: none !important;
      }
      .total {
        border: none !important;
        color: var(--el-color-danger);
      }
    }
  }
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
          padding: 0 20px;
          .ex {
            height: 38px;
          }
          .priced {
            color: var(--el-color-warning);
            font-size: 18px;
          }
          .pricew {
            color: var(--el-color-danger);
            font-size: 18px;
          }
          .perc {
            font-size: 18px;
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
.crm-contract {
  .order-field {
    .validate {
      input {
        border: var(--el-input-border) !important;
      }
    }
  }
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