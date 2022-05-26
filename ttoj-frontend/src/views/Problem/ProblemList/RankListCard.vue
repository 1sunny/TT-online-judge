<template>
  <div style="border: 1px solid #F2F3F5;"
       class="rounded-lg"
  >
    <el-tabs v-model="activeName"
             style="padding: 0 5px"
             stretch
             @tab-click="handleClick"
    >
      <el-tab-pane label="Rating榜" name="rating">
      </el-tab-pane>
      <el-tab-pane label="题目榜" lazy name="acNum">
      </el-tab-pane>
      <el-tab-pane label="贡献榜" lazy name="contribution">
      </el-tab-pane>
    </el-tabs>
    <div style="position: relative">
      <Spin size="large" fix v-if="spinShow"></Spin>
      <el-table
          :data="rankList[activeName]"
          style="width: 100%"
          stripe
      >
        <el-table-column
            type="index"
            label="#"
            width="70px"
            align="center"
        >
        </el-table-column>
        <el-table-column
            prop="username"
            label="昵称"
            align="center"
        >
        </el-table-column>
        <el-table-column
            :prop="activeName"
            :label="rankLabel[activeName]"
            width="70px"
            align="center"
        >
        </el-table-column>
      </el-table>
    </div>

  </div>
</template>

<script>
import {getRankListByCondition} from "../../../api/oj/userApi";

export default {
  name: "RankListCard",
  data() {
    return {
      activeName: 'rating',
      rankLabel: {
        'rating': 'Rating',
        'acNum': '过题数',
        'contribution': '贡献'
      },
      rankList: {
        'rating': null,
        'acNum': null,
        'contribution': null
      },
      spinShow: false,
      condition: {
        currentPage: 1,
        pageSize: 10,
        type: this.activeName
      }
    }
  },
  mounted() {
    this.condition.type = this.activeName
    console.log(this.condition);
    this.spinShow = true
    getRankListByCondition(this.condition).then(res => {
      console.log(res);
      if(res.success){
        this.rankList[this.condition.type] = res.data.rankList
      }else{
        this.errorNotify(res.message)
      }
      this.spinShow = false
    }).catch(err => {
      err = JSON.stringify(err)
      this.errorNotify(err)
    })
  },
  methods:{
    handleClick(tab, event){
      let name = tab.name
      if (this.rankList[name] == null){
        this.spinShow = true
        this.condition.type = name === 'acNum' ? 'ac_num' : name
        getRankListByCondition(this.condition).then(res => {
          console.log(res);
          if(res.success){
            this.rankList[name] = res.data.rankList
          }else{
            this.errorNotify(res.message)
          }
          this.spinShow = false
        }).catch(err => {
          err = JSON.stringify(err)
          this.errorNotify(err)
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
