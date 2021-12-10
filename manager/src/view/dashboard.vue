<template>
  <div class="dashboard">
    <div class="card d-flex">
      <div>
        <el-card class="h-100 bg" :body-style="{ padding: '0px',height:'100%' }" shadow="never">
          <div class="h-100 p-20 d-flex flex-column">
            <div class="card-title">
              <i class="icon el-icon-user"></i>
              <span>用户总数</span>
            </div>
            <div class="card-main flex-fluid">
              <div class="value pb-20">95690</div>
              <div class="detail d-flex">
                <div class="d-flex">
                  <div>注册</div>
                  <div class="ml-10"><span style="color:var(--el-color-success);margin-right:5px">&#9650;</span>512人</div>
                </div>
                <div class="d-flex ml-10">
                  <div>注销</div>
                  <div class="ml-10"><span style="color:var(--el-color-error);margin-right:5px">&#9660;</span>32人</div>
                </div>
              </div>
            </div>
            <div class="card-footer pt-10">
              增长率 12%
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card class="h-100 bg" :body-style="{ padding: '0px',height:'100%' }" shadow="never">
          <div class="h-100 p-20 d-flex flex-column">
            <div class="card-title">
              <i class="icon el-icon-user"></i>
              <span>销售金额</span>
            </div>
            <div class="card-main flex-fluid">
              <div class="value pb-20">￥480000</div>
              <div class="detail d-flex">
                <div class="d-flex">
                  <div>同比昨日</div>
                  <div class="ml-10"><span style="color:var(--el-color-success);margin-right:5px">&#9650;</span>￥1012</div>
                </div>
              </div>
            </div>
            <div class="card-footer pt-10">
              增长率 7.5%
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card class="h-100 bg" :body-style="{ padding: '0px',height:'100%' }" shadow="never">
          <div class="h-100 p-20 d-flex flex-column">
            <div class="card-title">
              <i class="icon el-icon-user"></i>
              <span>订单投诉</span>
            </div>
            <div class="card-main flex-fluid">
              <div class="value pb-20">12</div>
              <div class="detail d-flex">
                <div class="d-flex">
                  <div>已处理</div>
                  <div class="ml-10"><span style="color:var(--el-color-success)">10</span></div>
                </div>
                <div class="d-flex ml-10">
                  <div>未处理</div>
                  <div class="ml-10"><span style="color:var(--el-color-error)">0</span></div>
                </div>
              </div>
            </div>
            <div class="card-footer pt-10">
              增长率 -80.35%
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card class="h-100 bg" :body-style="{ padding: '0px',height:'100%' }" shadow="never">
          <div class="h-100 p-20 d-flex flex-column">
            <div class="card-title">
              <i class="icon el-icon-user"></i>
              <span>服务工单</span>
            </div>
            <div class="card-main flex-fluid">
              <div class="value pb-20">1369</div>
              <div class="detail d-flex">
                <div class="d-flex">
                  <div>已完成</div>
                  <div class="ml-10"><span style="color:var(--el-color-success)">915</span></div>
                </div>
                <div class="d-flex ml-10">
                  <div>未完成</div>
                  <div class="ml-10"><span style="color:var(--el-color-error)">213</span></div>
                </div>
              </div>
            </div>
            <div class="card-footer pt-10">
              增长率 19%
            </div>
          </div>
        </el-card>
      </div>
    </div>
    <div class="user d-flex mt-10">
      <div class="center">
        <el-card class="h-100" :body-style="{ padding: '0px' }" shadow="never">
          <div class="center-profle-title">
            <div class="face pt-20 d-flex justify-content-center align-items-center">
              <img src="../assets/image/default_face.jpg">
            </div>
            <div class="d-flex justify-content-center mt-20">
              <div>
                <el-button type="success" round size="small">个人中心</el-button>
              </div>
              <div style="width:10px"></div>
              <div>
                <el-button type="danger" round size="small">修改密码</el-button>
              </div>
            </div>
          </div>
          <div class="center-profle-main py-20 px-30">
            <div class="d-flex">
              <div class="mr-10">
                <i class="el-icon-user"></i>
              </div>
              <div>超级管理员</div>
            </div>
            <div class="d-flex mt-10">
              <div class="mr-10">
                <svg style="color:var(--el-text-color-secondary)" viewBox="0 0 1024 1024" width="16" height="16">
                  <path d="M402.1 730.3L286.5 845.9 170.9 730.3c-3.9-3.9-10.2-3.9-14.1 0-3.9 3.9-3.9 10.2 0 14.1L279.4 867c2 2 4.5 2.9 7.1 2.9s5.1-1 7.1-2.9l122.6-122.6c3.9-3.9 3.9-10.2 0-14.1-3.9-3.9-10.2-3.9-14.1 0z" p-id="969"></path>
                  <path d="M286.5 155C185.3 155 103 237.3 103 338.5c0 97.8 76.9 178 173.5 183.2v302.5c0 5.5 4.5 10 10 10s10-4.5 10-10V521.6C393 516.4 470 436.3 470 338.5 470 237.3 387.7 155 286.5 155z m0 346.9c-90.1 0-163.5-73.3-163.5-163.5S196.4 175 286.5 175 450 248.3 450 338.5s-73.4 163.4-163.5 163.4zM747.5 503.4V313.8h76.7c5.5 0 10-4.5 10-10s-4.5-10-10-10h-76.7V201c0-5.5-4.5-10-10-10s-10 4.5-10 10v92.8h-76.7c-5.5 0-10 4.5-10 10s4.5 10 10 10h76.7v189.6C631 508.6 554 588.8 554 686.6 554 787.7 636.3 870 737.5 870S921 787.7 921 686.5c-0.1-97.7-77-177.9-173.5-183.1z m-10 346.6C647.4 850 574 776.7 574 686.6s73.3-163.5 163.5-163.5S901 596.4 901 686.6 827.6 850 737.5 850z" p-id="970"></path>
                </svg>
              </div>
              <div>{{detail.gender==0?'未知':detail.gender==1?'男':'女'}}</div>
            </div>
            <div class="d-flex mt-10">
              <div class="mr-10">
                <i class="el-icon-postcard"></i>
              </div>
              <div>A9527</div>
            </div>
            <div class="d-flex mt-10">
              <div class="mr-10">
                <i style="transform:rotate(0deg)" class="iconfont icon-zuzhijigou"></i>
              </div>
              <div>研发部</div>
            </div>
          </div>
        </el-card>
      </div>
      <div class="area flex-fluid d-flex">
        <div class="map">
          <el-card class="h-100" shadow="never">
            <div id="line"></div>
          </el-card>
        </div>
        <div class="active">
          <el-card class="h-100" shadow="never">
            <div id="pie"></div>
          </el-card>
        </div>
      </div>
    </div>
    <div class="message mt-10 d-flex">
      <div>
        <el-card class="h-100" shadow="never">
          <div class="new">
            <div class="new-title">Nunumao V 0.0.1</div>
            <div class="new-main developer">
              <div class="d-flex">
                <div>文档地址：</div>
                <div><a href="https://thoughts.aliyun.com/sharespace/61775109996708001a43703e" target="_blank">开发文档</a></div>
              </div>
              <div class="d-flex mt-10">
                <div>代码仓库：</div>
                <div><a href="https://gitee.com/nunumao/nunumao.git" target="_blank">github</a></div>
                <div class="ml-10"><a href="https://gitee.com/nunumao/nunumao.git" target="_blank">gitee</a></div>
              </div>
              <div class="d-flex mt-10">
                <div>小程序：</div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card class="h-100" shadow="never">
          <div class="new">
            <div class="new-title">最新消息</div>
            <div class="new-main">
              <div v-for="item in 5" :key="item" class="new-item d-flex justify-content-between">
                <div>[私信]您有一条需要处理的工作任务</div>
                <div>2021-11-30 20:48:30</div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
      <div>
        <el-card class="h-100" shadow="never">
          <div class="new">
            <div class="new-title">技术文章</div>
            <div class="new-main">
              <div v-for="item in 5" :key="item" class="new-item d-flex justify-content-between">
                <div>物联网MQTT协议接入教程</div>
                <div>2021-11-30 20:48:30</div>
              </div>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts/core'
import { GridComponent, LegendComponent } from 'echarts/components'
import { BarChart, LineChart, PieChart } from 'echarts/charts'
import { CanvasRenderer } from 'echarts/renderers'

export default {
  data() {
    return {
      p1: null,
      show: false,
      list: [],
      detail: {
        gender: 0
      }
    }
  },
  mounted() {
    echarts.use(
      [GridComponent, LegendComponent, BarChart, LineChart, PieChart, CanvasRenderer]
    );
    this.init();
  },
  methods: {
    init() {
      this.line();
      this.pie();
    },
    line() {
      let option = {
        grid: {
          left: 0,
          right: 0,
          bottom: 0,
          containLabel: true
        },
        legend: {
          data: ['登录次数', '在线时长']
        },
        xAxis: [
          {
            type: 'category',
            data: ['9-10', '9-11', '9-12', '9-13', '9-14', '9-15', '9-16'],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '登录次数',
            min: 0,
            max: 20,
            interval: 2,
            axisLabel: {
              formatter: '{value} 次'
            }
          },
          {
            type: 'value',
            name: '在线时长',
            min: 0,
            max: 10,
            interval: 2,
            axisLabel: {
              formatter: '{value} 小时'
            }
          }
        ],
        series: [
          {
            name: '登录次数',
            type: 'bar',
            data: [10, 5, 7, 13, 6, 2, 11]
          },
          {
            name: '在线时长',
            type: 'line',
            yAxisIndex: 1,
            data: [1, 5, 3, 4, 7, 9, 2]
          }
        ]
      };

      echarts.init(document.getElementById("line")).setOption(option);
    },
    pie() {
      let option = {
        grid: {
          left: 0,
          right: 0,
          bottom: 0,
          containLabel: true
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: '未付订单' },
              { value: 735, name: '已付订单' },
              { value: 580, name: '退款订单' },
              { value: 484, name: '售后订单' },
              { value: 300, name: '完结订单' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      echarts.init(document.getElementById("pie")).setOption(option);
    }
  }
}
</script>
<style lang="scss" scoped>
.dashboard {
  .card {
    margin: 0 -5px;
    & > div {
      flex: 0 0 25%;
      padding: 0 5px;
    }
    &-title {
      color: var(--el-text-color-placeholder);
    }
    .icon {
      margin-right: 5px;
    }
    &-main {
      padding: 10px 0;
      .value {
        font-size: 30px;
      }
    }
    &-footer {
      border-top: solid 1px var(--el-border-color-light);
    }
  }
  .user {
    margin: 0 -5px;
    #line,
    #pie {
      width: 100%;
      height: 310px;
    }
    .center {
      flex: 0 0 20%;
      padding: 0 5px;
      &-profle-title {
        background: url("../assets/image/profile_bg.png");
        height: 160px;
        background-size: 100% auto;
        border-radius: var(--el-card-border-radius) var(--el-card-border-radius)
          0 0;
        .face {
          img {
            display: block;
            $size: 64px;
            width: $size;
            height: $size;
            border-radius: 50%;
          }
        }
      }
      &-profle-main {
        & > div {
          border-bottom: var(--el-border-base);
          padding-bottom: 10px;
          &:last-child {
            border: none;
          }
        }
      }
    }
    .area {
      padding: 0 5px;
      & > div {
        flex: 0 0 50%;
      }
      .map {
        padding-right: 5px;
      }
      .active {
        padding-left: 5px;
      }
    }
  }
  .message {
    margin: 0 -5px;
    & > div {
      padding: 0 5px;
      flex: 0 0 33.3%;
      .new {
        &-title {
          border-bottom: var(--el-border-base);
          padding-bottom: 10px;
          margin-bottom: 10px;
        }
        &-item {
          padding-bottom: 10px;
        }
      }
      .developer {
        a {
          color: #409eff;
        }
      }
    }
  }
}
</style>