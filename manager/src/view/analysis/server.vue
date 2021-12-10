<template>
  <div class="analysis-server">
    <div class="charts d-flex justify-content-between">
      <div style="padding-right:5px">
        <d1-page>
          <div id="cpu"></div>
        </d1-page>
      </div>
      <div style="padding-left:5px">
        <d1-page>
          <div id="mem"></div>
        </d1-page>
      </div>
    </div>
    <div class="list-info mt-10 d-flex">
      <div style="padding-right:5px">
        <d1-page>
          <div class="info-box">
            <div class="info-box-title">服务器信息</div>
            <div class="info-box-main">
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="success">os.name</el-tag>
                  </div>
                  <div class="intro">操作系统</div>
                </div>
                <div class="d-flex align-items-center">{{entity['os.name']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="success">os.arch</el-tag>
                  </div>
                  <div class="intro">系统架构</div>
                </div>
                <div class="d-flex align-items-center">{{entity['os.arch']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="success">system.cpu.count</el-tag>
                  </div>
                  <div class="intro">CPU核心数量</div>
                </div>
                <div class="d-flex align-items-center">{{entity['system.cpu.count']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="success">java.runtime.version</el-tag>
                  </div>
                  <div class="intro">Java 版本</div>
                </div>
                <div class="d-flex align-items-center">{{entity['java.runtime.version']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="success">pid</el-tag>
                  </div>
                  <div class="intro">进程ID号</div>
                </div>
                <div class="d-flex align-items-center">{{entity['pid']}}</div>
              </div>
            </div>
          </div>
        </d1-page>
      </div>
      <div style="padding-left:5px;padding-right:5px">
        <d1-page>
          <div class="info-box">
            <div class="info-box-title">JVM信息</div>
            <div class="info-box-main">
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag>jvm.memory.max</el-tag>
                  </div>
                  <div class="intro">JVM 最大内存</div>
                </div>
                <div class="d-flex align-items-center">{{entity['jvm.memory.max']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag>jvm.memory.committed</el-tag>
                  </div>
                  <div class="intro">JVM 可用内存</div>
                </div>
                <div class="d-flex align-items-center">{{entity['jvm.memory.committed']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag>jvm.memory.used</el-tag>
                  </div>
                  <div class="intro">JVM 已用内存</div>
                </div>
                <div class="d-flex align-items-center">{{entity['jvm.memory.used']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag>jvm.gc.pause.count</el-tag>
                  </div>
                  <div class="intro">系统启动以来 GC 次数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['jvm.gc.pause.count']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag>jvm.gc.pause.time</el-tag>
                  </div>
                  <div class="intro">系统启动以来 GC 次数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['jvm.gc.pause.time']}}</div>
              </div>
            </div>
          </div>
        </d1-page>
      </div>
      <div style="padding-left:5px">
        <d1-page>
          <div class="info-box">
            <div class="info-box-title">Tomcat信息</div>
            <div class="info-box-main">
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="warning">tomcat.sessions.created</el-tag>
                  </div>
                  <div class="intro">Tomcat 已创建 session 数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['tomcat.sessions.created']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="warning">tomcat.sessions.active.current</el-tag>
                  </div>
                  <div class="intro">Tomcat 当前活跃 session 数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['tomcat.sessions.active.current']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="warning">tomcat.sessions.active.max</el-tag>
                  </div>
                  <div class="intro">Tomcat 活跃 session 数峰值</div>
                </div>
                <div class="d-flex align-items-center">{{entity['tomcat.sessions.active.max']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="warning">tomcat.sessions.expired</el-tag>
                  </div>
                  <div class="intro">Tomcat 已过期 session 数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['tomcat.sessions.expired']}}</div>
              </div>
              <div class="info-item d-flex justify-content-between align-items-stretch py-10">
                <div>
                  <div class="name">
                    <el-tag type="warning">tomcat.sessions.rejected</el-tag>
                  </div>
                  <div class="intro">超过session 最大配置后，拒绝的 session 个数</div>
                </div>
                <div class="d-flex align-items-center">{{entity['tomcat.sessions.rejected']}}</div>
              </div>
            </div>
          </div>
        </d1-page>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts/core'
import { TitleComponent, TooltipComponent, GridComponent, LegendComponent } from 'echarts/components'
import { LineChart } from 'echarts/charts'
import { BarChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

export default {
  data() {
    return {
      entity: {
        'os.name': '',
        'os.arch': '',
        'system.cpu.count': '',
        'java.runtime.version': '',
        'jvm.memory.max': '',
        'jvm.memory.committed': '',
        'jvm.memory.used': '',
        'pid': '',
        'jvm.gc.pause.count': '',
        'jvm.gc.pause.time': '',
        'tomcat.sessions.created': '',
        'tomcat.sessions.expired': '',
        'tomcat.sessions.active.max': '',
        'tomcat.sessions.rejected': ''
      },
      interval: null
    }
  },
  mounted() {
    echarts.use([TitleComponent, TooltipComponent, GridComponent, LegendComponent, LineChart, BarChart, CanvasRenderer]);
    this.init();
    this.monitor();
  },
  methods: {
    async init() {
      echarts.dispose(document.getElementById("cpu"));
      echarts.dispose(document.getElementById("mem"));
      this.$axios.get('manager/system/server/info').then(data => {
        this.entity['os.name'] = data.result['os.name'];
        this.entity['os.arch'] = data.result['os.arch'];
        this.entity['java.runtime.version'] = data.result['java.runtime.version'];
        this.entity['pid'] = data.result['pid'];
      })
      this.loadValue('actuator/metrics/system.cpu.count').then(data => {
        this.entity['system.cpu.count'] = data.measurements[0].value + ' 核';
      })
      this.loadValue('actuator/metrics/jvm.memory.max').then(data => {
        this.entity['jvm.memory.max'] = Math.ceil(data.measurements[0].value / 1024 / 1024) + 'M';
      })
      this.loadValue('actuator/metrics/jvm.memory.committed').then(data => {
        this.entity['jvm.memory.committed'] = Math.ceil(data.measurements[0].value / 1024 / 1024) + 'M';
      })
      this.loadValue('actuator/metrics/jvm.gc.pause').then(data => {
        this.entity['jvm.gc.pause.count'] = data.measurements[0].value + ' 次';
        this.entity['jvm.gc.pause.time'] = data.measurements[1].value.toFixed(2) + ' 秒';
      })
      this.loadValue('actuator/metrics/tomcat.sessions.created').then(data => {
        this.entity['tomcat.sessions.created'] = data.measurements[0].value + ' 个';
      })
      this.loadValue('actuator/metrics/tomcat.sessions.expired').then(data => {
        this.entity['tomcat.sessions.expired'] = data.measurements[0].value + ' 个';
      })
      this.loadValue('actuator/metrics/tomcat.sessions.active.current').then(data => {
        this.entity['tomcat.sessions.active.current'] = data.measurements[0].value + ' 个';
      })
      this.loadValue('actuator/metrics/tomcat.sessions.active.max').then(data => {
        this.entity['tomcat.sessions.active.max'] = data.measurements[0].value + ' 个';
      })
      this.loadValue('actuator/metrics/tomcat.sessions.rejected').then(data => {
        this.entity['tomcat.sessions.rejected'] = data.measurements[0].value + ' 个';
      })
    },
    loadValue(api) {
      return this.$axios.get(api, {}, { origin: true });
    },
    monitor() {
      let option = {
        title: {
          text: 'CPU 使用率'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['系统', '进程']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: 100,
          axisLabel: {
            formatter: '{value} %'
          }
        },
        series: [
          {
            name: '系统',
            type: 'line',
            data: []
          },
          {
            name: '进程',
            type: 'line',
            data: []
          }
        ]
      }
      let charts = echarts.init(document.getElementById('cpu'))
      charts.setOption(option)

      let value = {
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        series: [
          {
            name: '系统',
            type: 'line',
            data: []
          },
          {
            name: '进程',
            type: 'line',
            data: []
          }
        ]
      }

      let menOption = {
        title: {
          text: '内存使用'
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} M'
          }
        },
        series: [
          {
            data: [],
            type: 'bar'
          }
        ]
      }
      let menCharts = echarts.init(document.getElementById('mem'))

      let loadData = () => {
        Promise.all([
          this.loadValue('actuator/metrics/system.cpu.usage'),
          this.loadValue('actuator/metrics/process.cpu.usage'),
          this.loadValue('actuator/metrics/jvm.memory.used')
        ]).then(data => {
          let sys = (data[0].measurements[0].value * 100).toFixed(2);
          let pro = (data[1].measurements[0].value * 100).toFixed(2);

          this.entity['jvm.memory.used'] = Math.ceil(data[2].measurements[0].value / 1024 / 1024) + 'M';

          value.xAxis.data.push(new Date().format('mm:ss'))
          value.series[0].data.push(sys)
          value.series[1].data.push(pro)

          menOption.xAxis.data.push(new Date().format('mm:ss'))
          menOption.series[0].data.push(Math.ceil(data[2].measurements[0].value / 1024 / 1024))

          if (value.xAxis.data.length > 6) {
            value.xAxis.data.shift();
            value.series[0].data.shift();
            value.series[1].data.shift();

            menOption.xAxis.data.shift();
            menOption.series[0].data.shift();
          }
          charts.setOption(value)
          menCharts.setOption(menOption)
        })
      }

      loadData();

      this.$utils.interval.setInterval(this, 'loopLoad', () => {
        loadData();
      }, 5000)

    }
  }
}
</script>
<style lang="scss" scoped>
.analysis-server {
  .charts {
    & > div {
      flex: 0 0 50%;
    }
    #cpu,
    #mem {
      width: 100%;
      height: 350px;
    }
  }
  .list-info {
    & > div {
      flex: 0 0 33.3%;
    }
    .info-box {
      &-title {
        padding: 5px 0 15px;
        border-bottom: solid 1px var(--el-border-color-base);
      }
      &-main {
        .info-item {
          border-bottom: solid 1px var(--el-border-color-base);
          .intro {
            margin-top: 5px;
            margin-left: 5px;
            font-size: 12px;
            color: var(--el-text-color-secondary);
          }
        }
      }
    }
  }
}
</style>