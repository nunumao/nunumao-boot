<template>
  <div class="crm-product d-flex align-items-stretch">
    <div class="tree">
      <d1-page class="h-100">
        <div class="h-100 d-flex flex-column">
          <div class="category-title d-flex justify-content-between">
            <div class="">
              产品分类
            </div>
            <div>
              <el-button type="text" @click="openCategory">新增分类</el-button>
            </div>
          </div>
          <div class="tree-content p-10 mt-10 flex-fluid">
            <el-tree :data="category" :props="{children: 'child',label: 'name'}" :default-expand-all="true" :expand-on-click-node="false" @node-click="nodeClick">
              <template #default="{ node, data }">
                <span class="tree-node">
                  <span>{{ node.label }}</span>
                  <span>
                    <el-tooltip effect="dark" placement="right">
                      <template #content>
                        <div class="d-flex">
                          <div>
                            <el-button style="padding: 0 !important;min-height: auto !important" type="text" @click="categoryEdit(data)">编辑</el-button>
                          </div>
                          <div style="margin-left:5px">
                            <el-button style="padding: 0 !important;min-height: auto !important" type="text" @click="categoryDel(data)">删除</el-button>
                          </div>
                        </div>
                      </template>
                      <i class="el-icon-arrow-right"></i>
                    </el-tooltip>
                  </span>
                </span>
              </template>
            </el-tree>
          </div>
        </div>
      </d1-page>
      <el-dialog v-model="categoryDialog" title="分类管理" width="600px" destroy-on-close @close="closeCategory">
        <div class="mt-10">
          <el-form ref="cateForm" :model="categoryEntity" :rules="rulesCategory" label-width="100px">
            <el-form-item label="所属上级" prop="parentId">
              <el-select v-model="categoryEntity.parentId" placeholder="请选择上级">
                <template v-for="item in parent" :key="item.id">
                  <el-option :label="item.name" :value="item.id" :disabled="item.type == 0 && entity.type == 2">
                    <div class="d-flex">
                      <div v-if="item.level > 0" style="padding-right:5px;color:var(--el-border-color-base);font-family: 'Consolas,Monaco,Andale Mono,Ubuntu Mono,monospace'" class="bxfy">
                        <template v-for="i in (item.level-1)" :key="i">&boxv;</template>
                        {{item.end ? '└ ':'├ '}}
                      </div>
                      <div>
                        {{item.name}}
                      </div>
                    </div>
                  </el-option>
                </template>
              </el-select>
            </el-form-item>
            <el-form-item label="分类名称" prop="name">
              <el-input v-model="categoryEntity.name" placeholder="请输入分类名称"></el-input>
            </el-form-item>
            <el-form-item label="分类排序">
              <el-input v-model="categoryEntity.sort" placeholder="请输入分类排序"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="categorySave">保存</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-dialog>
    </div>
    <div class="flex-fluid pl-10">
      <d1-page ref="list" title="产品" :modal="true" :apis="{prefix:'manager/crm/product/'}" permission="crm/product/" v-model:entity="entity" :rules="rules" :before-list="beforeList" :before-add="beforeAdd" @add="openAdd">
        <!-- 列表 -->
        <template #tools>
          <div>
            <el-button type="primary" icon="el-icon-plus" v-permission="'crm/product/add'" :disabled="selectCategory?false:true" @click="add">新增</el-button>
          </div>
        </template>
        <template v-slot:column>
          <el-table-column prop="category" label="产品分类" width="160">
          </el-table-column>
          <el-table-column prop="name" label="产品名称">
          </el-table-column>
          <el-table-column label="销售价格" width="130">
            <template #default="scope">
              ￥{{scope.row.sellingPrice}}
            </template>
          </el-table-column>
          <el-table-column label="成本价格" width="130">
            <template #default="scope">
              ￥{{scope.row.costPrice}}
            </template>
          </el-table-column>
        </template>
        <!-- 表单 -->
        <template v-slot:field>
          <el-form-item label="产品分类" prop="code">
            <el-input v-model="entity.category" readonly></el-input>
          </el-form-item>
          <el-form-item label="产品编号" prop="serial">
            <el-input v-model="entity.serial" placeholder="请输入成本价格"></el-input>
          </el-form-item>
          <el-form-item label="产品名称" prop="name">
            <el-input v-model="entity.name" placeholder="请输入产品名称"></el-input>
          </el-form-item>
          <el-form-item label="成本价格" prop="costPrice">
            <el-input v-model="entity.costPrice" placeholder="请输入成本价格"></el-input>
          </el-form-item>
          <el-form-item label="销售价格" prop="sellingPrice">
            <el-input v-model="entity.sellingPrice" placeholder="请输入销售价格"></el-input>
          </el-form-item>
          <el-form-item label="规格单位" prop="unit">
            <el-input v-model="entity.unit" placeholder="请输入规格单位"></el-input>
          </el-form-item>
          <el-form-item label="产品状态" prop="status">
            <el-radio-group v-model="entity.status">
              <el-radio :label="1">在售</el-radio>
              <el-radio :label="0">停售</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="产品说明" prop="remark">
            <el-input v-model="entity.remark" type="textarea" placeholder="请输入产品说明" :autosize="{minRows: 4, maxRows: 4}" resize="none"></el-input>
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
      parent: [],
      category: [],
      categoryDialog: false,
      categoryEntity: {
        parentId: null,
        name: '',
        sort: ''
      },
      selectCategory: null,
      entity: {
        status: 1
      },
      rules: {
        serial: [{
          required: true,
          message: '请输入产品编号'
        }],
        name: [{
          required: true,
          message: '请输入产品名称'
        }],
        costPrice: [{
          required: true,
          message: '请输入成本价格'
        }],
        sellingPrice: [{
          required: true,
          message: '请输入销售价格'
        }],
        unit: [{
          required: true,
          message: '请输入规格单位'
        }]
      },
      rulesCategory: {
        parentId: [{
          required: true,
          message: '请选择上级分类'
        }],
        name: [{
          required: true,
          message: '请输入分类名称'
        }]
      }
    }
  },
  mounted() {
    this.loadCategory();
  },
  methods: {
    add() {
      this.$refs.list.add();
    },
    openAdd() {
      this.entity.category = this.selectCategory.name;
    },
    openCategory() {
      this.categoryDialog = true;
      this.loadParent();
    },
    closeCategory() {
      this.clearCategory();
      this.categoryDialog = false;
    },
    clearCategory() {
      this.categoryEntity = {
        parentId: null,
        name: '',
        sort: ''
      }
    },
    loadCategory() {
      this.$axios.get('manager/crm/product/categoryList').then(data => {
        this.category = data.result;
      }).catch(e => {
        this.category = []
      })
    },
    loadParent() {
      this.$axios.get('manager/crm/product/parent').then(data => {
        let temp = [];
        let loop = (list, level = 0) => {
          list.forEach((item, index) => {
            item.level = level;
            if (index == list.length - 1) {
              item.end = true;
            } else {
              item.end = false;
            }
            temp.push(item);
            if (Array.isArray(item.child)) {
              loop(item.child, level + 1);
              item.child = "";
            }
          })
        }
        loop(data.result);
        temp = [...[{ id: '0', name: '顶级分类' }], ...temp]
        this.parent = temp;
      }).catch(e => {
        this.parent = [{ id: '0', name: '顶级分类' }]
      })
    },
    categoryEdit(row) {
      this.loadParent();
      this.$axios.get('manager/crm/product/editCategory', { id: row.id }).then(data => {
        this.categoryEntity = data.result;
      }).catch(e => { })
      this.categoryDialog = true;
    },
    categorySave() {
      this.$refs['cateForm'].validate(valid => {
        if (valid) {
          if (this.categoryEntity.id) {
            this.$axios.put('manager/crm/product/editCategory', this.categoryEntity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadCategory();
                  this.categoryDialog = false;
                  this.$refs.list.init();
                }
              })
            }).catch(e => {
              this.$message.error(e);
            })
          } else {
            this.$axios.post('manager/crm/product/addCategory', this.categoryEntity).then(data => {
              this.$message({
                message: data.message,
                type: 'success',
                duration: 1000,
                onClose: () => {
                  this.loadCategory();
                  this.categoryDialog = false;
                  this.$refs.list.init();
                }
              })
            }).catch(e => {
              this.$message.error(e);
            })
          }
        }
      })
    },
    categoryDel(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$axios.delete('manager/crm/product/deleteCategory', { id: row.id }).then(data => {
          this.$message.success(data.message);
          this.loadCategory();
        }).catch(e => {
          this.$message.error(e);
        })
      }).catch(e => { })
    },
    nodeClick(data, node) {
      this.selectCategory = node.data;
      this.$refs.list.init();
    },
    beforeList(params) {
      if (this.selectCategory) {
        params.category = this.selectCategory.id;
      }
    },
    beforeAdd() {
      this.entity.categoryId = this.selectCategory.id;
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-product {
  .tree {
    flex: 0 0 300px;
    .tree-content {
      border: solid 1px var(--el-border-color-base);
    }
    .tree-node {
      flex: 1;
      display: flex;
      align-items: center;
      justify-content: space-between;
      font-size: 14px;
      padding-right: 8px;
    }
  }
}
</style>
<style lang="scss">
.crm-product {
  .category-title {
    .el-button {
      padding: 0 !important;
      min-height: auto !important;
    }
  }
}
</style>