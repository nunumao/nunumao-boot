<template>
  <d1-page>
    <div class="d-flex mb-20">
      <d1-query-item label="自动刷新">
        <el-switch v-model="query.auto" @change="autoChange" />
      </d1-query-item>
    </div>
    <el-table :data="list" border>
      <el-table-column prop="request.uri" label="URI" fixed="left" />
      <el-table-column label="请求类型" width="80">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.request.method == 'GET'">GET</el-tag>
          <el-tag v-if="scope.row.request.method == 'POST'">POST</el-tag>
          <el-tag type="warning" v-if="scope.row.request.method == 'PUT'">PUT</el-tag>
          <el-tag type="danger" v-if="scope.row.request.method == 'DELETE'">DELETE</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="response.status" label="响应状态" width="80">
        <template #default="scope">
          <el-tag type="success" v-if="/^2+/.test(scope.row.response.status)">{{scope.row.response.status}}</el-tag>
          <el-tag type="danger" v-if="!(/^2+/.test(scope.row.response.status))">{{scope.row.response.status}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="timeTaken" label="请求耗时" width="80" />
      <el-table-column prop="timestamp" label="时间" width="170" />
    </el-table>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      query: {
        auto: false
      },
      list: [],
      interval: null
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.get('actuator/httptrace', {}, { origin: true }).then(data => {
        data.traces.forEach(item => {
          item.timestamp = new Date(item.timestamp).format('YYYY-MM-DD HH:mm:ss')
        })
        this.list = data.traces.reverse();
      })
    },
    autoChange(flag) {
      if (flag) {
        this.$utils.interval.setInterval(this, 'loopLoad', () => {
          this.init();
        }, 5000)
      } else {
        this.$utils.interval.clearInterval(this, 'loopLoad');
      }
    }
  }
}
</script>