<template>
  <div class="exception h-100 bg-white">
    <div class="img"><img src="https://gw.alipayobjects.com/zos/rmsportal/KpnpchXsobRgLElEozzI.svg"></div>
    <div class="content">
      <h1>404</h1>
      <div class="desc">抱歉，你访问的页面不存在或无权访问</div>
      <div class="action">
        <el-button type="primary" @click="back">返回上页</el-button>
      </div>
    </div>
  </div>
</template>
<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  methods: {
    back() {
      this.$router.go(-1);
    },
  },
});
</script>
<style lang="scss" scoped>
.exception {
  min-height: 500px;
  -ms-flex-align: center;
  align-items: center;
  text-align: center;
  padding-top: 150px;
  .img {
    display: inline-block;
    padding-right: 52px;
    zoom: 1;

    .exception .img img {
      height: 360px;
      max-width: 430px;
    }
  }
  .content {
    display: inline-block;
    -ms-flex: auto;
    flex: auto;
    h1 {
      color: #434e59;
      font-size: 72px;
      font-weight: 600;
      line-height: 72px;
      margin-bottom: 24px;
    }
    .desc {
      color: rgba(0, 0, 0, 0.45);
      font-size: 20px;
      line-height: 28px;
      margin-bottom: 16px;
    }
  }
}

.mobile {
  .exception {
    margin-top: 30px;
    .img {
      padding-right: unset;
      img {
        height: 40%;
        max-width: 80%;
      }
    }
  }
}
</style>