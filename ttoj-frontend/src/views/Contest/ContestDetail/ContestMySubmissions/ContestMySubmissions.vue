<template>
  <div style="position:relative;
              padding: 20px;
           "
       v-if="auth"
  >
    <el-table
        :data="mySubmissions"
        stripe
        :default-sort="{prop: 'submitTime', order: 'descending'}"
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
    <Spin size="large" fix v-if="spinShow"></Spin>
    <div class="block" style="
                              padding: 10px 10px 10px 20px;
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
  <div v-else>
    请登录后查看
  </div>
</template>

<script>

import {getContestSubmissionByContestId} from "../../../../api/oj/contestSubmissionApi";
import {mapGetters} from "vuex";

export default {
  name: "contestMySubmissions",
  data() {
    return {
      contestId: 0,
      spinShow: false,
      mySubmissions: [],
      condition: {
        contestId: '',
        currentPage: 1, pageSize: 4, total: 0,
        username: ''
      }
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'username',
      'auth',
      'user',
      'showManageBtn'
    ]),
  },
  created() {
    console.log('my created');
    this.contestId = this.$route.params.contestId
    this.getContestMySubmissions()
  },
  methods: {
    getContestMySubmissions() {
      this.condition.contestId = this.contestId
      this.condition.username = this.username
      this.spinShow = true
      getContestSubmissionByContestId(this.condition).then(res => {
        console.log(res);
        this.spinShow = false
        if (res.success) {
          this.condition.total = parseInt(res.data.total)
          this.mySubmissions = res.data.contestSubmissions
        } else {
          // this.$notify({
          //   title: '失败',
          //   message
          // })
          this.errorNotify(res.message)
          this.$router.push({name: 'contestOverview'})
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.spinShow = false
        this.errorNotify(err)
      })
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getContestMySubmissions()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getContestMySubmissions()
    },
    filterHandler(value, row, column) {
      const property = column['property'];
      return row[property] === value;
    },
  },

}
</script>

<style scoped>

</style>
