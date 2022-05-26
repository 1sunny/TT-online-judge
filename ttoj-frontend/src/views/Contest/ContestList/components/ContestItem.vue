<template>
  <el-card shadow="never"
           :body-style="bodyStyle"
           style="
                 "
  >
    <div style="display: flex;
                align-items: center;
                justify-content: space-between;
               ">
      <el-image fit="'contain'"
                :src="$config.baseUrl+contest.cover"
                style="vertical-align: center;
                        height: 50px;
                        width: 50px;"
      />
      <div style="display: flex;
                  justify-content: space-between;
                  align-items: center;
                  margin-left: 10px;
                  width: 100%;
                 "
      >
        <div>
            <span class="contestName"
                  @click="toContestDetail()"
            >{{ contest.name }}</span>
          <div v-if="contest.status===-1"
               class="match-status"
               style="background: #4ea3fa;
                        border-color: #4ea3fa;
                       "
          >未开始
          </div>
          <div v-else-if="contest.status===0" class="match-status">
            比赛中
          </div>
          <div v-else
               class="match-status"
               style="background: #9b9b9b;
                        border-color: #9b9b9b;
                       "
          >已结束
          </div>
          <div class="contentTitle hidden-xs-only" style="margin-top: 5px">
            比赛时间: {{ contest.startTime }} - {{ contest.endTime }}(时长:{{ contest.duration }})
          </div>
          <div class="contentTitle hidden-xs-only" style="margin-top: 5px">
            参与人数: {{ contest.registeredNum }}
          </div>
        </div>
        <div class="hidden-xs-only" v-if="contest.status !== 1" style="color: #777;margin: 0 20px">
          {{ contest.countDown }}
        </div>
        <el-button type="success" plain size="small" @click="toContestDetail()">
          进入比赛
        </el-button>
      </div>
    </div>
    <div style="display: none">{{ myText }}</div>
  </el-card>

</template>

<script>
import {getCountDown, getDurationSeconds} from "../../../../utils/time";

export default {
  name: "ContestItem",
  props: {
    contest: {
      type: Object,
      default: {}
    }
  },
  created() {
    this.countDown()
    console.log(this.$props.contest);
  },
  data() {
    return {
      bodyStyle: {
        padding: '10px'
      },
      myText: 0
    }
  },
  methods: {
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
    toContestDetail() {
      console.log(this.$props.contest);
      this.$router.push('/contest/' + this.$props.contest.id)
    }
  },
  beforeDestroy() {
    clearInterval(this.timer)
  }
}
</script>

<style scoped>
.match-status {
  position: relative;
  display: inline-block;
  background: #25bb9b;
  border-color: #25bb9b;
  color: #fff;
  font-size: 12px;
  height: 20px;
  line-height: 20px;
  margin: 0 5px;
  padding: 0 5px;
  text-align: center;
}

.match-status:after {
  border-style: solid;
  border-color: inherit;
  border-width: 10px 8px;
  position: absolute;
  content: "";
  bottom: 0;
  right: -11px;
  border-right-color: transparent;
}

el-card:hover {
  background-color: #f6f9fa;
}

.contestName:hover {
  color: #409eff;
  cursor: pointer;
}
</style>
<!--
hover: #f6f9fa

.match-signup {
    background: #4ea3fa;
    border-color: #4ea3fa;
}
.match-end-tag {
    background: #9b9b9b;
    border-color: #9b9b9b;
}
-->
