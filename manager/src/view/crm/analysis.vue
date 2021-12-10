<template>
  <div class="crm-analysis d-flex align-items-stretch h-100">
    <div class="crm-analysis-report">
      <d1-page :padding="0">
        <div>
          <div class="report-title px-20 py-10">销售简报</div>
          <div class="px-20 pt-10">
            <el-radio-group v-model="query.type">
              <el-radio :label="0">快捷查询</el-radio>
              <el-radio :label="1">时间查询</el-radio>
            </el-radio-group>
          </div>
          <div class="report-search px-20 d-flex flex-wrap">
            <el-select v-if="query.type == 0" style="width:100% !important" placeholder="Select">
              <el-option label="本月" value="0"></el-option>
              <el-option label="上月" value="1"></el-option>
              <el-option label="本周" value="2"></el-option>
              <el-option label="上周" value="3"></el-option>
            </el-select>
            <el-date-picker v-if="query.type == 1" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
            </el-date-picker>
          </div>
          <div class="px-20 pt-10">
            <div class="list-item">
              <div class="title">销售转化</div>
              <div class="text-right">
                <span style="color:#82939e">0</span><span class="ml-10">个</span>
              </div>
            </div>
            <div class="list-item">
              <div class="title">新增客户</div>
              <div class="text-right">
                <span style="color:#3497DB">0</span><span class="ml-10">个</span>
              </div>
            </div>
            <div class="list-item">
              <div class="title">新增商机</div>
              <div class="text-right">
                <span style="color:#7CB81C">0</span><span class="ml-10">个</span>
              </div>
            </div>
            <div class="list-item">
              <div class="title">新增合同</div>
              <div class="text-right">
                <span style="color:#E26F50">0</span><span class="ml-10">个</span>
              </div>
            </div>
            <div class="list-item">
              <div class="title">跟进客户</div>
              <div class="text-right">
                <span style="color:#83a3c8">0</span><span class="ml-10">个</span>
              </div>
            </div>
          </div>
        </div>
      </d1-page>
    </div>
    <div class="flex-fluid ml-10 d-flex flex-column">
      <div class="crm-analysis-time">
        <d1-page :padding="0">
          <div class="report-title px-20 py-10">业绩目标</div>
          <div class="p-20">
            <div>
              <!-- 月数据 -->
              <div class="d-flex flex-wrap month-content pb-10">
                <div class="time-item mb-20" :class="{'no-border':item%4==0}" v-for="item in 12" :key="item">
                  <el-tooltip effect="dark" placement="bottom">
                    <template #content>
                      <div style="color:#3497DB">0%</div>
                      <div class="text-white">{{item}}月 完成: 0</div>
                      <div class="text-white">{{item}}月 目标: 0</div>
                    </template>
                    <div>
                      <div class="d-flex justify-content-between pb-10">
                        <div>{{ item }}月</div>
                        <div>
                          <span style="color:#3497DB">0</span>
                          <span class="mx-10">/</span>
                          <span>0</span>
                        </div>
                      </div>
                      <el-progress :percentage="50" :show-text="false"></el-progress>
                    </div>
                  </el-tooltip>
                </div>
              </div>
              <!-- 季数据 -->
              <div class="d-flex quarter py-20">
                <div class="quarter-item mb-10" v-for="item in 4" :key="item">
                  <el-tooltip effect="dark" placement="bottom">
                    <template #content> 多行信息<br />第二行信息 </template>
                    <div>
                      <div class="d-flex justify-content-between pb-10">
                        <div>Q{{ item }}</div>
                        <div>
                          <span style="color:#7CB81C">0</span>
                          <span class="mx-10">/</span>
                          <span>0</span>
                        </div>
                      </div>
                      <el-progress :percentage="50" :show-text="false"></el-progress>
                    </div>
                  </el-tooltip>
                </div>
              </div>
              <!-- 年数据 -->
              <div class="year py-20">
                <el-tooltip effect="dark" placement="bottom">
                  <template #content> 多行信息<br />第二行信息 </template>
                  <div>
                    <div class="d-flex justify-content-between pb-10">
                      <div>2021年</div>
                      <div>
                        <span style="color:#EE8C0C">0</span>
                        <span class="mx-10">/</span>
                        <span>0</span>
                      </div>
                    </div>
                    <el-progress :percentage="50" :show-text="false"></el-progress>
                  </div>
                </el-tooltip>
              </div>
            </div>
          </div>
        </d1-page>
      </div>
      <div class="mt-10">
        <d1-page :padding="0">
          <div class="report-title px-20 py-10">销售漏斗</div>
          <div>
            <div id="funnel" class="px-20"></div>
          </div>
        </d1-page>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts/core';
import { FunnelChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
echarts.use([FunnelChart, CanvasRenderer]);

export default {
  data() {
    return {
      query: {
        type: 0
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      let funnelChart = echarts.init(document.getElementById('funnel'));
      let option = {
        series: [
          {
            name: 'Expected',
            type: 'funnel',
            left: '0%',
            width: '100%',
            label: {
              formatter: '{b}Expected'
            },
            labelLine: {
              show: false
            },
            itemStyle: {
              opacity: 0.7
            },
            emphasis: {
              label: {
                position: 'inside',
                formatter: '{b}Expected: {c}%'
              }
            },
            data: [
              { value: 60, name: 'Visit' },
              { value: 40, name: 'Inquiry' },
              { value: 20, name: 'Order' },
              { value: 80, name: 'Click' },
              { value: 100, name: 'Show' }
            ]
          }
        ]
      };

      option && funnelChart.setOption(option);
    }
  }
}
</script>
<style lang="scss" scoped>
.crm-analysis {
  #funnel {
    width: 350px;
    height: 400px;
  }
  .month-content {
    margin: 0 -20px;
  }
  &-report {
    width: 280px;
    .title {
      color: #b2c0d1;
      padding-bottom: 10px;
    }
    .list-item {
      border-bottom: solid 1px #ddd;
      padding-bottom: 10px;
      padding-top: 10px;
    }
  }
  .report-title {
    border-bottom: solid 1px #ddd;
  }

  .year {
    border-top: solid 1px #ddd;
  }
  &-time {
    .time-item {
      flex: 0 0 25%;
      position: relative;
      padding: 0 20px;
      &:after {
        position: absolute;
        content: " ";
        right: 0;
        top: 0;
        border-right: solid 1px #ddd;
        height: 100%;
      }
    }
    .no-border {
      &:after {
        border-right: 0;
      }
    }
    .quarter {
      border-top: solid 1px #ddd;
      margin: 0 -20px;
    }
    .quarter-item {
      flex: 0 0 25%;
      padding: 0 20px;
    }
  }
}
</style>
<style lang="scss">
.crm-analysis {
  .el-range-separator {
    width: 30px;
  }
}
</style>