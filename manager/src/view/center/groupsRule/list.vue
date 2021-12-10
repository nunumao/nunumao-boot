<template>
  <d1-list-page class="groups-rule" title="权限配置">
    <template v-slot:action>
      <div class="d-flex">
        <el-select class="mr-3" v-model="conditon.gid" placeholder="请选择分组" @change="groupChange">
          <el-option v-for="item in groupsList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </div>
    </template>
    <template v-slot:search>
      <div></div>
    </template>
    <el-form ref="form" :model="entity" label-width="0px">
      <div class="rule-container d-flex flex-wrap">
        <div v-for="item in list" :key="item.id">
          <div class="rule-box">
            <div class="title d-flex">{{ item.name }}</div>
            <div class="main">
              <div class="list" v-for="jtem in item.child" :key="jtem.id">
                <div class="list-title">
                  <el-checkbox :indeterminate="jtem.indeterminate" v-model="jtem.all" @change="checkAll(jtem)">{{ jtem.name }}</el-checkbox>
                </div>
                <div class="list-item-box d-flex flex-wrap">
                  <div class="list-item" v-for="ktem in jtem.child" :key="ktem.id">
                    <el-checkbox-group v-model="jtem.select">
                      <el-checkbox :label="ktem.name" @change="ckChange(jtem,ktem)"></el-checkbox>
                    </el-checkbox-group>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <el-form-item v-if="conditon.gid" style="margin-top:30px">
        <el-button type="primary" @click="submit">保存</el-button>
      </el-form-item>
    </el-form>
  </d1-list-page>
</template>
<script>
export default {
  data() {
    return {
      conditon: {
        gid: null
      },
      entity: {
        value: []
      },
      groupsList: [],
      list: []
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    async init() {
      let gData = await this.loadGroups();
      if (gData) {
        this.groupsList = gData.result;
      }
    },
    async loadList() {
      let auth = await this.$axios.get('manager/groupsRule/rule', { id: this.conditon.gid }).catch(e => { });
      let authList = [];
      if (auth.result.length > 0) {
        auth.result.forEach(item => {
          authList.push(item.ruleId)
          this.entity.value.push(item.ruleId);
        })
      }
      this.$axios.get('manager/groupsRule/list', { gid: this.conditon.gid, page: this.list.current }).then(data => {
        this.list = data.result;
        this.list.forEach(item => {
          item.child.forEach(jtem => {
            jtem.all = false;
            jtem.select = [];
            jtem.indeterminate = false;
            if (authList.length > 0 && jtem.child.length > 0) {
              jtem.child.forEach(ktem => {
                if (authList.includes(ktem.id)) {
                  jtem.select.push(ktem.name);
                }
              });
              if (jtem.select.length > 0 && jtem.select.length < jtem.child.length) {
                jtem.indeterminate = true;
              }
              if (jtem.select.length == jtem.child.length) {
                jtem.indeterminate = false;
                jtem.all = true;
              }
              if (jtem.select.length <= 0) {
                jtem.indeterminate = false;
                jtem.all = false;
              }
            }
          });
        });
      }).catch(e => { })
    },
    loadGroups() {
      return this.$axios.get('manager/groupsRule/groups');
    },
    checkAll(data) {
      if (data.all) {
        data.select = [];
        for (const item of data.child) {
          if (!this.entity.value.includes(item.id)) {
            this.entity.value.push(item.id);
          }
          data.select.push(item.name);
        }
      } else {
        for (const item of data.child) {
          this.entity.value.remove(item.id);
        }
        data.select = [];
      }
      if (data.select.length > 0 && data.select.length < data.child.length) {
        data.indeterminate = true;
      }
      if (data.select.length == data.child.length) {
        data.indeterminate = false;
      }
    },
    ckChange(data, cur) {
      if (data.select.length > 0 && data.select.length < data.child.length) {
        data.indeterminate = true;
      }
      if (data.select.length == data.child.length) {
        data.indeterminate = false;
        data.all = true;
      }
      if (data.select.length <= 0) {
        data.indeterminate = false;
        data.all = false;
      }
      if (data.select.includes(cur.name)) {
        this.entity.value.push(cur.id);
      } else {
        this.entity.value.remove(cur.id);
      }
    },
    groupChange() {
      this.loadList();
    },
    submit() {
      let params = {
        id: this.conditon.gid,
        rule: JSON.stringify(this.entity.value)
      }
      this.$axios.put('manager/groupsRule/edit', params).then(data => {
        this.$message({
          message: data.message,
          type: 'success',
          duration: 1000,
          onClose: () => {
            // this.$router.back();
          }
        })
      }).catch(e => {
        this.$message.error(e)
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.groups-rule {
  .rule-container {
    margin: 0 -5px;
    & > div {
      width: 280px;
      padding: 0 5px;
    }
    .rule-box {
      border: solid 1px #ddd;
      .title {
        background: #ccc;
        padding: 10px 20px;
      }
      .main {
        padding: 0 0 0 30px;
        height: 300px;
        overflow-y: auto;
        .list {
          padding-top: 10px;
          .list-title {
            padding: 0 0 10px 0;
          }
          .list-item-box {
            padding: 0 0 0 10px;
            .list-item {
              padding: 0 10px 10px 0;
            }
          }
        }
      }
    }
  }
}
</style>