<template>
<div style="position: relative">
  <Spin size="large" fix v-if="spinShow"></Spin>
  <el-card class="box-card">
    <div slot="header" class="clearfix">
      <div class=" text-label-2 font-medium">
        <Icon type="md-medal" />
        最近比赛
      </div>
    </div>
    <div>
      <RecentContestItem v-for="(item, index) in contests" :key="index"
                         :contest="item" @goToContestDetail="goToContestDetail"/>
    </div>
  </el-card>
</div>
</template>

<script>
import {getRecentContest} from "../../../api/oj/contestApi";
import RecentContestItem from "./components/RecentContestItem";

export default {
  name: "RecentContestCard",
  components:{
    RecentContestItem
  },
  data(){
    return{
      contests:[],
      spinShow: false
    }
  },
  created(){
    this.spinShow = true
    getRecentContest(this.$config.recentContestDays).then(res=>{
      console.log(res);
      if(res.success){
        this.contests = res.data.contests
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
    goToContestDetail(contestId){
      this.$router.push(`/contest/${contestId}`)
    }
  }
}
</script>

<style scoped>

</style>
