<template>
  <d1-page class="analysis-sql">
    <div class="d-flex mb-20">
      <d1-query-item label="自动刷新">
        <el-switch v-model="query.auto" @change="autoChange" />
      </d1-query-item>
    </div>
    <el-table :data="list" border>
      <el-table-column label="SQL" fixed="left">
        <template #default="scope">
          <el-tooltip popper-class="analysis-sql-tooltip" :content="scope.row.SQL" placement="bottom-start" effect="dark">
            <div class="sql" @click="detail(scope.row.ID)">
              {{scope.row.short}}
            </div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="ExecuteCount" label="执行数" width="80" />
      <el-table-column prop="TotalTime" label="总耗时(ms)" width="100" />
      <el-table-column prop="MaxTimespan" label="最慢(ms)" width="100" />
      <el-table-column prop="FetchRowCount" label="读取行数" width="100" />
      <el-table-column prop="ConcurrentMax" label="最大并发" width="100" />
    </el-table>
    <el-dialog v-model="dialog" title="详细信息" width="900px" :before-close="close" :destroy-on-close="true">
      <el-form label-width="80px">
        <el-form-item label="SQL语句">
          <el-input style="width:100% !important" type="textarea" v-model="entity.formattedSql" :rows="6" resize="none" readonly />
        </el-form-item>
        <el-form-item label="执行时间">
          <el-input style="width:240px !important" v-model="entity.LastTime" readonly />
        </el-form-item>
        <el-form-item label="语句解析">
          <table class="table table-bordered">
            <tr>
              <td class="tab-lab" width="100">数据表</td>
              <td style="line-height:1.5;padding:5px 10px">{{entity.parsedTable}}</td>
            </tr>
            <tr>
              <td class="tab-lab" width="100">数据字段</td>
              <td style="line-height:1.5;padding:5px 10px">{{entity.parsedFields}}</td>
            </tr>
            <tr>
              <td class="tab-lab" width="100">查询条件</td>
              <td style="line-height:1.5;padding:5px 10px">{{entity.parsedConditions}}</td>
            </tr>
            <tr>
              <td class="tab-lab" width="100">关联关系</td>
              <td style="line-height:1.5;padding:5px 10px">{{entity.parsedRelationships}}</td>
            </tr>
            <tr>
              <td class="tab-lab" width="100">排序条件</td>
              <td style="line-height:1.5;padding:5px 10px">{{entity.parsedOrderbycolumns}}</td>
            </tr>
          </table>
        </el-form-item>
      </el-form>
    </el-dialog>
  </d1-page>
</template>
<script>
export default {
  data() {
    return {
      dialog: false,
      query: {
        auto: false
      },
      list: [],
      entity: {},
      interval: null
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.get('druid/sql.json', {}, { origin: true }).then(data => {
        data.Content.forEach(item => {
          if (item.SQL.length > 50) {
            item.short = item.SQL.substr(0, 50) + '...';
          } else {
            item.short = item.SQL;
          }
        })
        this.list = data.Content.reverse();
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
    },
    detail(id) {
      this.dialog = true;
      let url = 'druid/sql-' + id + '.json';
      this.$axios.get(url, {}, { origin: true }).then(data => {
        this.entity = data.Content;
      })
    },
    close() {
      this.dialog = false;
    }
  }
}
</script>
<style lang="scss" scoped>
.analysis-sql {
  .sql {
    color: var(--el-color-primary);
    cursor: pointer;
    display: inline-block;
  }
  .tab-lab {
    background: #f5f7fa;
  }
}
</style>
<style lang="scss">
.analysis-sql-tooltip {
  width: 500px;
  line-height: 1.5;
}
</style>