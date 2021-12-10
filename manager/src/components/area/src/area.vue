<template>
  <div class="d1-area d-flex">
    <div class="item" v-if="level >= 1">
      <el-select v-model="province" placeholder="请选择" @change="provinceChange">
        <el-option v-for="item in list.province" :key="item.id" :label="item.fullName" :value="item.id"></el-option>
      </el-select>
    </div>
    <div class="item" v-if="level >= 2 && select.city.length > 0">
      <el-select v-model="city" placeholder="请选择" @change="cityChange">
        <el-option v-for="item in select.city" :key="item.id" :label="item.fullName" :value="item.id"></el-option>
      </el-select>
    </div>
    <div class="item" v-if="level >= 3 && select.region.length > 0">
      <el-select v-model="region" placeholder="请选择" @change="regionChange">
        <el-option v-for="item in select.region" :key="item.id" :label="item.fullName" :value="item.id"></el-option>
      </el-select>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    api: {
      type: String,
      default: ''
    },
    level: {
      type: Number,
      default: 3
    },
    params: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      list: {},
      select: {
        city: [],
        region: []
      },
      province: '',
      city: '',
      region: '',
      // 编辑
      checked: []
    }
  },
  watch: {
    params: function (nv, ov) {
      if (nv != '') {
        let arr = [];
        arr[0] = nv.substring(0, 2) + '0000';
        arr[1] = nv.substring(0, 2) + nv.substring(2, 4) + '00';
        arr[2] = nv;
        this.checked = arr;
        this.init();
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.$axios.get(this.api, { level: 3 }).then(data => {
        this.list = data.result;
        if (this.params != '') {
          this.province = this.checked[0];
          this.city = this.checked[1];
          // 城市选中
          let patt = new RegExp('^' + this.checked[1].substring(0, 2) + '\\d+');
          this.select.city = [];
          this.city = this.checked[1];
          for (const item of this.list.city) {
            if (patt.test(item.id)) {
              this.select.city.push(item);
            }
          }
          // 区域选中
          let pattRegion = new RegExp(this.checked[2].substring(0, 4) + '\\d{2}');
          this.select.region = [];
          this.region = this.checked[2];
          for (const item of this.list.region) {
            if (pattRegion.test(item.id)) {
              this.select.region.push(item);
            }
          }
        }
      }).catch(e => { console.error('地区查询 --> ', e); })
    },
    provinceChange(value) {      
      let patt = new RegExp('^' + value.substring(0, 2) + '\\d+');
      this.select.city = [];
      this.city = '';
      this.region = '';
      for (const item of this.list.city) {
        if (patt.test(item.id)) {
          this.select.city.push(item);
        }
      }
      this.finish();
    },
    cityChange(value) {
      let patt = new RegExp(value.substring(0, 4) + '\\d{2}');
      this.select.region = [];
      this.region = '';
      for (const item of this.list.region) {
        if (patt.test(item.id)) {
          this.select.region.push(item);
        }
      }
      this.finish();
    },
    regionChange(value) {
      this.finish();
    },
    finish() {
      let result = [];
      if (this.province != '') {
        for (const item of this.list.province) {
          if (this.province == item.id) {
            result[0] = item;
            break;
          }
        }
      }
      if (this.province != '') {
        for (const item of this.list.city) {
          if (this.city == item.id) {
            result[1] = item;
            break;
          }
        }
      }
      if (this.region != '') {
        for (const item of this.list.region) {
          if (this.region == item.id) {
            result[2] = item;
            break;
          }
        }
      }
      this.$emit('result', result);
    }
  }
}
</script>
<style lang="scss" scoped>
.d1-area {
  .item {
    margin-right: 5px;
  }
}
</style>
