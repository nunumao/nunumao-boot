<template>
  <d1-page class="profile-message" :apis="{prefix:'manager/system/message/'}">
    <template #tools>
      <div></div>
    </template>
    <template v-slot:column>
      <el-table-column label="类型" width="80">
        <template #default="scope">
          {{['通知','私信'][scope.row.type]}}
        </template>
      </el-table-column>
      <el-table-column label="消息" width="200">
        <template #default="scope">
          <span style="color:var(--el-color-primary);cursor: pointer" @click="openDetail(scope.row)">{{scope.row.name}}</span>
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="概要">
      </el-table-column>
      <el-table-column prop="createTime" label="时间" width="180">
      </el-table-column>
      <el-table-column label="已读" width="80">
        <template #default="scope">
          <span :style="{color:scope.row.isRead == 0 ? 'var(--el-color-danger)' :'var(--el-color-success)'}">{{['未读','已读'][scope.row.isRead]}}</span>
        </template>
      </el-table-column>
    </template>
    <template #action>
      <div></div>
    </template>
    <template #batch>
      <el-button size="mini" @click="read">全部已读</el-button>
    </template>
    <template #attach>
      <el-dialog v-model="dialog" title="消息详情" width="600px">
        <div class="d-flex mb-10">
          <div class="mr-10 lab">消息标题</div>
          <div>{{detail.name}}</div>
        </div>
        <div class="d-flex mb-10">
          <div class="mr-10 lab">消息时间</div>
          <div>{{detail.createTime}}</div>
        </div>
        <div class="d-flex mb-10">
          <div class="mr-10 lab">消息简介</div>
          <div>{{detail.intro}}</div>
        </div>
        <div class="d-flex mb-10">
          <div class="mr-10 lab">消息内容</div>
          <div>{{detail.content}}</div>
        </div>
      </el-dialog>
    </template>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      dialog: false,
      detail: {}
    }
  },
  methods: {
    read() {

    },
    openDetail(row) {
      this.detail = {};
      this.dialog = true;
      this.$axios.get('manager/system/message/detail', { id: row.id }).then(data => {
        row.isRead = 1;
        this.detail = data.result;
      }).catch(e => { })
    }
  }
}
</script>
<style lang="scss" scoped>
.profile-message {
  .lab {
    flex: 0 0 60px;
  }
}
</style>