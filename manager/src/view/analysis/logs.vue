<template>
  <d1-page :modal="true" :check="false" :apis="{prefix:'manager/system/actionLog/'}" permission="system/actionLog/">
    <!-- 列表 -->
    <template #tools>
      <div></div>
    </template>
    <template v-slot:column>
      <el-table-column type="expand" fixed="left">
        <template #default="scope">
          <div class="pl-50">
            <div class="d-flex align-items-center">
              <div class="pr-10">请求地址</div>
              <el-tag style="white-space:normal;height:auto">{{scope.row.url}}</el-tag>
            </div>
            <div class="mt-10 d-flex align-items-start pr-10">
              <div class="pr-10" style="margin-top:5px">请求参数</div>
              <div class="flex-fluid">
                <el-tag type="danger" style="white-space:normal;height:auto">{{scope.row.params}}</el-tag>
              </div>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="content" label="操作内容">
      </el-table-column>
      <el-table-column prop="userId" label="操作人ID" width="180">
      </el-table-column>
      <el-table-column prop="name" label="操作人姓名" width="100">
      </el-table-column>
      <el-table-column label="操作类型" width="80">
        <template #default="scope">
          <span style="color:var(--el-color-primary)" v-if="scope.row.action.toUpperCase() == 'GET'">查询数据</span>
          <span style="color:var(--el-color-success)" v-if="scope.row.action.toUpperCase() == 'POST'">新增数据</span>
          <span style="color:var(--el-color-warning)" v-if="scope.row.action.toUpperCase() == 'PUT'">编辑数据</span>
          <span style="color:var(--el-color-danger)" v-if="scope.row.action.toUpperCase() == 'DELETE'">删除数据</span>
        </template>
      </el-table-column>
      <el-table-column prop="ip" label="IP" width="120">
      </el-table-column>
      <el-table-column prop="execTime" label="耗时(ms)" width="80">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170">
      </el-table-column>
    </template>
    <template #action>
      <span></span>
    </template>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {

    }
  }
}
</script>