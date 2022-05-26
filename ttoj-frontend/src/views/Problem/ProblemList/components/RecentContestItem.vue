<template>
<div style="width: 100%;
            text-align: center;
            padding: 10px;
            border-bottom: 1px solid var(--borderColor)
">
  <el-link type="primary" @click="goToContestDetail(contest.id)"
           style="font-size: 15px;
                 "
  >
    {{contest.name}}
  </el-link>
  <div v-if="contest.status !== 1" style="color: #777;margin: 0 20px">
    {{ contest.countDown }}
  </div>
  <div style="display: none">{{ myText }}</div>
</div>
</template>

<script>
import {getCountDown, getDurationSeconds} from "../../../../utils/time";

export default {
  name: "RecentContestItem",
  props:{
    contest:{
      type: Object,
      default: () => {}
    }
  },
  data(){
    return{
      myText: 0
    }
  },
  created() {
    this.countDown()
  },
  methods:{
    goToContestDetail(contestId){
      this.$emit('goToContestDetail', contestId)
    },
    countDown: function () {
      if (this.contest.status !== 1) {
        this.contest.countDown = getCountDown(this.contest.remainSecond)
        this.timer = setInterval(() => {
          this.contest.remainSecond = this.contest.remainSecond - 1
          this.contest.countDown = getCountDown(this.contest.remainSecond)
          this.myText = this.myText + 1
          if (this.contest.remainSecond <= 0) {
            if (this.contest.status === -1) {
              this.contest.status = 0
              let second = getDurationSeconds(this.contest.startTime, this.contest.endTime)
              console.log(second);
              this.contest.remainSecond = second
            } else if (this.contest.status === 0) {
              this.contest.status = 1
              clearInterval(this.timer)
            }
          }
        }, 1000)
      }
    },
  }
}
</script>

<style scoped>

</style>
