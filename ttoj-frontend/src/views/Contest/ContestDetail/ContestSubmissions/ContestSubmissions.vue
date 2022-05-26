<template>
  <div style="position:relative;
              padding: 20px;
             "
  >
    <Spin size="large" fix v-if="spinShow"></Spin>
    <el-table
        ref="submissionsFilterTable"
        :data="submissions"
        stripe
        :default-sort = "{prop: 'submitTime', order: 'descending'}"
        style="
              "
    >
      <el-table-column
          prop="username"
          label="ID"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="username"
          label="用户名"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="problemDisplayId"
          label="题号"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="result"
          label="运行结果"
          width="140px"
          :filters="$config.judgeResult.map(item => {
                       return { text: item.title, value: item.value }
                    })
                   "
          :filter-method="filterHandler"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="timeUse"
          label="运行时间(ms)"
          width="150px"
          sortable
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="memoryUse"
          label="使用内存(KB)"
          width="150px"
          sortable
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="codeLength"
          label="代码长度(B)"
          width="140px"
          sortable
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="language"
          label="使用语言"
          width="120px"
          :filters="$config.supportLanguages.map(item => {
                       return { text: item.title, value: item.value }
                    })
                   "
          :filter-method="filterHandler"
          align="center"
      >
      </el-table-column>
      <el-table-column
          prop="submitTime"
          label="提交时间"
          width="160px"
          sortable
          align="center"
      >
      </el-table-column>
    </el-table>
    <div class="block" style="
                              padding: 10px 10px 10px 20px;
                              overflow: auto;
                             "
    >
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :currentPage="condition.currentPage"
          :page-sizes="[4, 30]"
          :page-size="condition.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="condition.total"
          background
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import request from "../../../../utils/request";
import {getContestSubmissionByContestId} from "../../../../api/oj/contestSubmissionApi";

export default {
  name: "contestSubmissions",
  data(){
    return{
      contestId: 0,
      submissions: [],
      spinShow: true,
      condition:{
        contestId: '',
        currentPage: 1, pageSize:4, total: 0,
        language: '',
        result: '',
        problemDisplayId: '',
        username: ''
      }
    }
  },
  created() {
    console.log('submissions created');
    this.contestId = this.$route.params.contestId
    this.getContestSubmissions()
  },
  methods:{
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getContestSubmissions()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getContestSubmissions()
    },
    refresh(){
      this.getContestSubmissions()
    },
    getContestSubmissions(){
      this.spinShow = true
      this.condition.contestId = this.contestId
      getContestSubmissionByContestId(this.condition).then(res => {
        console.log(res);
        this.spinShow = false
        if (res.success){
          this.condition.total = parseInt(res.data.total)
          this.submissions = res.data.contestSubmissions
        }else{
          this.errorNotify(res.message)
          this.$router.push({name: 'contestOverview'})
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.spinShow = false
        this.errorNotify(err)
      })
    },
    clearFilter() {
      this.$refs.submissionsFilterTable.clearFilter();
    },
  },
}
</script>

<style scoped>

</style>
