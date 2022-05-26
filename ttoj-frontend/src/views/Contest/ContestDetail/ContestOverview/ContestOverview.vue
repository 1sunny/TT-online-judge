<template>
  <div style="position: relative;
              padding: 20px;
             "
  >
    <Spin size="large" fix v-if="spinShow"></Spin>
    <div style="
            ">
      <div style="display: flex;
                  justify-content: space-between;
                  align-items: center
                 "
      >
        <div style="font-weight: 600;font-size: 24px">
          {{ contest.name }}
        </div>
        <div>
          比赛时间: {{ contest.startTime }} - {{ contest.endTime }} (时长:{{ contest.duration }})
        </div>
      </div>
      <div style="display: flex;
                  flex-direction: column;
                  align-items: center;
                  margin-top: 40px;
                 "
      >
        <div v-if="this.contest.status === -1">
          比赛倒计时
        </div>
        <div v-else-if="this.contest.status === 0">
          比赛剩余时间
        </div>
        <div v-else-if="this.contest.status === 1">
          比赛已结束
        </div>
        <div v-if="this.contest.status !== 1" style="color: #777;font-size: 40px">
          {{ contest.countDown }}
        </div>
        <!--  离谱,必须要有这个才行  -->
        <div style="display: none">{{ myText }}</div>
      </div>
      <div style="
                  margin: 20px 0;
                 "
      >
        <h3>比赛信息</h3>
        <div class="callout" style="
                                    margin-bottom: 10px;
                                   "
        >
          {{ contest.description }}
        </div>
        <h3>比赛规则</h3>
        <div class="callout" style="">
          emmm
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import {getContestByContestId} from "../../../../api/oj/contestApi";
import {getCountDown, getDurationSeconds, getDurationString} from "../../../../utils/time";
import {mapGetters} from "vuex";

export default {
  inject: ['reload'],
  name: "contestOverview",
  props: {
    propTabName: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      contest: {},
      contestId: 0,
      myText: 0,
      tabName: '',
      spinShow: true
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'username',
      'auth',
      'user'
    ])
  },
  created() {
    console.log('overview created');
    this.tabName = this.$props.propTabName
    this.contestId = this.$route.params.contestId
    this.getContestById(this.contestId)
  },
  methods: {
    refresh() {
      this.getContestById(this.contestId)
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
              this.reload();
            }
          }
        }, 1000)
      }
    },
    getContestById(contestId) {
      this.spinShow = true
      getContestByContestId(contestId).then(res => {
        console.log(res);
        this.spinShow = false
        if(res.success){
          let contest = res.data.contest
          contest.duration = getDurationString(contest.startTime, contest.endTime)
          this.contest = contest
          console.log(contest);
          this.countDown()
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.spinShow = false
        this.errorNotify(err)
      })
    },
  },
  beforeDestroy() {
    clearInterval(this.timer)
  }
}
</script>

<style scoped>

</style>
