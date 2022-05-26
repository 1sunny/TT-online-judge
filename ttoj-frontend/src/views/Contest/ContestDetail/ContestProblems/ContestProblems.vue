<template>
  <div style="position:relative;
              padding: 20px;
             "
  >
    <el-row>
      <el-col :xs="24" :sm="18">
        <el-card class="box-card">
          <Spin size="large" fix v-if="problemsSpinShow"></Spin>
          <el-table :data="problems"
                    stripe
                    style="width: 100%"
          >
            <el-table-column
                prop="problemDisplayId"
                label="#"
                width="100px"
                align="center"/>
            <el-table-column
                prop="problemName"
                label="题目"
                class-name="problemNameColumn"
                align="center"
            >
              <template slot-scope="scope">
                <div style="display: flex;">
                  <el-button type="text" @click="openProblemTab(scope.row)">
                    {{ scope.row.problemName }}
                  </el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column
                prop="acRate"
                label="通过率"
                width="100px"
                align="center"/>
            <el-table-column prop="alreadyPassed" label="状态" width="100px" align="center">
              <template slot-scope="scope">
                <Icon v-if="scope.row.alreadyPassed" type="md-checkmark" color="#2DB55D"/>
                <Icon v-else type="md-remove" color="#BFBFBF"/>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="1" style="opacity: 0">
         1
      </el-col>
      <el-col :xs="24" :sm="5">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>比赛资料</span>
          </div>
          <el-link type="primary" disabled>比赛题解</el-link>
          <br>
          <el-link type="primary" disabled>比赛讨论</el-link>
        </el-card>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import {getProblemByContestIdAndDisplayId, getProblemsByContestId} from "../../../../api/oj/contestProblemApi";

export default {
  name: "contestProblems",
  data() {
    return {
      contestId: 0,
      problemsSpinShow: false,
      problems: []
    }
  },
  created() {
    this.contestId = this.$route.params.contestId
    this.openProblemDrawer()
  },
  methods: {
    openProblemTab(row) {
      this.$router.push(`/contest/${this.contestId}/contestProblems/${row.problemDisplayId}`)
    },
    openProblemDrawer() {
      this.problemsSpinShow = true
      console.log(this.contestId);
      getProblemsByContestId(this.contestId).then(res => {
        this.problemsSpinShow = false
        console.log(res);
        if (res.success) {
          let problems = res.data.problems
          for (let problem of problems) {
            problem.acRate = problem.acNum + '/' + problem.submitNum
          }
          this.problems = problems
        } else {
          this.errorNotify(res.message)
        }
        this.problemsSpinShow = false
      }).catch(err => {
        err = JSON.stringify(err)
        this.problemsSpinShow = false
        this.errorNotify(err)
      })
    },
  }
}
</script>

<style scoped>

</style>
