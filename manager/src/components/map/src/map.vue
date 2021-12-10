<template>
  <div class="d1-map">
    <el-input v-model="address" :clearable="true" readonly placeholder="请输入地址">
      <template #append>
        <el-popover placement="top" :width="400" trigger="click">
          <template #reference>
            <el-button icon="el-icon-location-information"></el-button>
          </template>
          <div class="map-box">
            <div id="allmap" :style="{width:'100%',height:height + 'px'}"></div>
            <div class="auto">
              <el-input id="c2b-bmap-suggest-id" size="mini" v-model="suggest" :clearable="true" placeholder="关键字搜索" style="width: 220px !important"></el-input>
            </div>
          </div>
        </el-popover>
      </template>
    </el-input>
  </div>
</template>

<script>
export default {
  data() {
    return {
      map: null,
      geo: null,
      ac: null,
      ak: 'rpQsjc106pGsMqDuV0NvrEt3Wb30lOBy',
      geolocation: null,
      address: '',
      lng: '108.318406',
      lat: '22.850103',
      suggest: ''
    }
  },
  props: {
    width: {
      type: Number,
      default: 650
    },
    height: {
      type: Number,
      default: 250
    },
    level: {
      type: Number,
      default: 16
    },
    autocomplete: {
      type: Boolean,
      default: false
    },
    // 显示经纬度
    showLngLat: {
      type: Boolean,
      default: false
    },
    // 显示经纬度
    value: {
      type: Array,
      default: []
    }
  },
  watch: {
    value: function (nv, ov) {
      if (nv[0] && nv[1] && nv[2]) {
        this.address = nv[0];
        this.lat = nv[1];
        this.lng = nv[2];
        this.moveAndMarker();
      }
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.loadSDK(this.ak).then(bmap => {
        // 创建地图
        this.createMap();
        // 创建地图控件
        this.createUI();
        // 标记并移动
        this.moveAndMarker();
        // 点击事件
        this.click();
        // 开启自动完成
        this.auto();
      });
    },
    loadSDK(ak) {
      return new Promise((resolve, reject) => {
        // 已经加载过了
        if (window.BMap !== undefined && window.BMap != null) {
          resolve(BMap);
          return;
        }
        let script = document.createElement("script");
        script.type = "text/javascript";
        script.src = '//api.map.baidu.com/api?v=3.0&ak=' + ak + '&callback=initBMap';
        script.onerror = () => {
          console.error('地图加载失败...');
          reject();
        };
        document.body.appendChild(script);
        window.initBMap = () => {
          resolve(BMap);
        }
      });
    },
    moveAndMarker() {
      this.map.clearOverlays();
      let point = new BMap.Point(this.lng, this.lat);
      // 创建标注
      let marker = new BMap.Marker(point);
      this.map.addOverlay(marker);
      this.map.centerAndZoom(point, this.level);
      setTimeout(() => {
        this.map.panTo(point, true);
      }, 1000);
    },
    createMap() {
      this.map = new BMap.Map("allmap", {
        enableMapClick: false
      });
    },
    createUI() {
      // 添加控件
      this.map.addControl(new BMap.NavigationControl());
      // 开启鼠标滚轮缩放
      this.map.enableScrollWheelZoom(true);
    },
    click() {
      // 单击添加marker
      this.map.addEventListener("click", (e) => {
        this.map.clearOverlays();
        // 创建标注
        let marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));
        this.map.addOverlay(marker);
        // 显示经纬度
        this.lng = e.point.lng;
        this.lat = e.point.lat;
        // 逆向解析地址
        this.doGeo(e.point.lng, e.point.lat);
      });
    },
    doGeo(lng, lat) {
      // 初始化逆向解析类
      this.geo = new BMap.Geocoder();
      this.geolocation = new BMap.Geolocation();

      let pt = new BMap.Point(lng, lat);
      this.geo.getLocation(pt, res => {
        let addComp = res.addressComponents;
        let showAddress = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
        this.ac.setInputValue(showAddress);
        this.address = showAddress;
        this.$emit('result', {
          lng: lng + '',
          lat: lat + '',
          pro: addComp.province,
          city: addComp.city,
          district: addComp.district,
          street: addComp.street,
          streetNumber: addComp.streetNumber,
          address: showAddress
        });
      });
    },
    auto() {
      //建立一个自动完成的对象
      this.ac = new BMap.Autocomplete({
        "input": "c2b-bmap-suggest-id",
        "location": this.map
      });
      // 鼠标点击下拉列表后的事件
      this.ac.addEventListener("onconfirm", (e) => {

        this.map.clearOverlays();

        const _value = e.item.value;
        const address = _value.province + _value.city + _value.district + _value.street + _value.business;

        // 智能搜索
        const local = new BMap.LocalSearch(this.map, {
          onSearchComplete: () => {
            const pp = local.getResults().getPoi(0).point;
            this.lng = pp.lng;
            this.lat = pp.lat;
            this.address = address;
            this.map.centerAndZoom(pp, 18);
            this.map.addOverlay(new BMap.Marker(pp));

            // 通知父组件
            this.$emit('result', {
              lng: this.lng + "",
              lat: this.lat + "",
              pro: _value.province,
              city: _value.city,
              district: _value.district,
              street: _value.street,
              streetNumber: _value.streetNumber,
              address: address
            });

          }
        });

        local.search(address);

      });
    }
  }
}
</script>

<style lang="scss" scoped>
.map-box {
  position: relative;
  .auto {
    position: absolute;
    top: 5px;
    right: 5px;
  }
}
#allmap {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}
</style>
<style lang="scss">
.tangram-suggestion-main {
  z-index: 9999;
}
</style>
