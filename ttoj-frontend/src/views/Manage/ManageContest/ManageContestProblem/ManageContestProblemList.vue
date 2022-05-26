<template>
  <div style=""
  >
    <div style="margin: 10px 20px">
      <el-input v-model="condition.authorName"
                placeholder="输入作者名字"
                clearable
                style="width: 30%"
                size="small"
      >
      </el-input>
      <el-divider direction="vertical" style="height: 100%"/>
      <el-input v-model="condition.problemName"
                placeholder="输入题目名字"
                clearable
                style="width: 30%"
                size="small"
      >
      </el-input>
      <el-divider direction="vertical" style="height: 100%"/>
      <el-button icon="el-icon-search"
                 @click="getContestProblemsByCondition"
                 size="small"
      >搜索
      </el-button>
    </div>
    <div style="position: relative;flex: 1 1 auto;overflow: auto">
      <Spin fix size="large" v-if="showSpin"></Spin>
      <el-table
          :data="problems"
          style="">
        <el-table-column
            fixed
            prop="problemDisplayId"
            label="显示ID"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="problemName"
            label="名字"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="authorName"
            label="作者"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="visible"
            label="比赛中是否可见"
            align="center"
        >
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.visible"
                @change="updateContestProblemVisibility(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="130px"
        >
          <template slot-scope="scope">
            <el-button @click="editContestProblemDisplayId(scope.row)" size="mini"
                       icon="el-icon-edit" type="info" plain
            >
            </el-button>
            <el-divider direction="vertical" style="height: 100%"/>
            <el-tooltip class="item" effect="dark" content="下载题目测试文件" placement="top-start">
              <el-button @click="downloadTestCase(scope.row)" size="mini"
                         icon="el-icon-download"
                         type="primary" plain
              >
              </el-button>
            </el-tooltip>
            <el-divider direction="vertical" style="height: 100%"/>
            <el-popconfirm
                title="确定删除吗？"
                @confirm="deleteProblem(scope.row)"
            >
              <el-button
                  slot="reference"
                  size="mini" icon="el-icon-delete" type="danger" plain
              >
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 10px;
               "
    >
      <el-button plain icon="el-icon-circle-plus-outline" size="small" @click="openArchiveProblems">添加题目</el-button>
      <div class="block" style="margin: 10px 10px 10px 20px">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :currentPage="condition.currentPage"
            :page-sizes="[3, 5]"
            :page-size="condition.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="condition.total"
            background
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog
        title="请从题库中选择题目"
        :visible.sync="dialogVisible"
        width="80%"
    >
      <el-input
          placeholder="请输入题目名字"
          v-model="searchCondition.problemName"
          clearable
          @keyup.enter.native="searchProblemsByCondition"
          style="width: 45%"
      >
      </el-input>
      <el-divider direction="vertical" style="height: 100%"/>
      <el-input
          placeholder="请输入作者昵称"
          v-model="searchCondition.authorName"
          clearable
          @keyup.enter.native="searchProblemsByCondition"
          style="width: 45%"
      >
      </el-input>
      <div style="position: relative;">
        <Spin fix size="large" v-if="searchShowSpin"></Spin>
        <el-table
            :data="archiveProblems"
            style="">
          <el-table-column
              fixed
              prop="displayId"
              label="显示ID"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="name"
              label="名字"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="authorName"
              label="作者"
              align="center"
          ></el-table-column>
          <el-table-column
              prop="visible"
              label="题库中是否可见"
              align="center"
          >
            <template slot-scope="scope">
              <el-switch
                  v-model="scope.row.visible"
                  @change="updateProblemVisibility(scope.row)"
              >
              </el-switch>
            </template>
          </el-table-column>
          <el-table-column
              fixed="right"
              label="操作"
              align="center"
              min-width="130px"
          >
            <template slot-scope="scope">
              <el-button @click="addContestProblem(scope.row)" size="mini"
                         icon="el-icon-folder-add" type="info" plain
              >
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="block" style="margin: 10px 10px 10px 20px">
          <el-pagination
              @size-change="handleSearchSizeChange"
              @current-change="handleSearchCurrentChange"
              :currentPage="searchCondition.currentPage"
              :page-sizes="[10, 20]"
              :page-size="searchCondition.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="searchCondition.total"
              background
          >
          </el-pagination>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
        title="请输入题目在比赛中显示ID"
        :visible.sync="displayIdEditorVisible"
        width="30%"
    >
      <el-input v-model="displayIdInput" placeholder="请输入题目在比赛中显示ID"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="displayIdEditorVisible = false">取 消</el-button>
        <el-button type="primary" @click="closeDisplayIdEditor">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  addContestProblemById, updateContestProblem,
  deleteContestProblemByProblemId,
  getContestProblemByCondition, searchProblemsFromArchiveByCondition
} from "@/api/manage/ManageContestProblemApi";
import {updateProblemVisibility} from "@/api/manage/ManageProblemApi";

export default {
  name: "ManageContestProblemList",
  data() {
    return {
      problems: [],
      condition: {
        currentPage: 1,
        total: 0,
        authorName: '',
        problemName: '',
        pageSize: 3,
        contestId: this.$route.params.contestId
      },
      searchCondition:{
        currentPage: 1,
        total: 0,
        authorName: '',
        problemName: '',
        pageSize: 3,
        contestId: this.$route.params.contestId
      },
      showSpin: true,
      dialogVisible: false,
      searchProblemName: '',
      archiveProblems: [],
      searchShowSpin: false,
      displayIdEditorVisible: false,
      displayIdInput: '',
      currentEditorProblemId: -1
    }
  },
  created() {
    this.getContestProblemsByCondition()
  },
  methods: {
    closeDisplayIdEditor(){
      updateContestProblem({
        contestId: this.$route.params.contestId,
        problemId: this.currentEditorProblemId,
        problemDisplayId: this.displayIdInput
      }).then(res => {
        console.log(res);
        this.displayIdEditorVisible = false
        if(res.success){
          this.successNotify(res.message)
          this.getContestProblemsByCondition()
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        this.errorNotify(err)
      })
    },
    updateProblemVisibility(problem) {
      let problemId = problem.id
      let visible = problem.visible

      updateProblemVisibility(problemId, visible).then(res => {
        console.log(res);
        if (res.success) {
          this.successNotify(res.message)
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    searchProblemsByCondition(){
      this.searchShowSpin = true
      searchProblemsFromArchiveByCondition(this.searchCondition).then(res => {
        this.searchShowSpin = false
        console.log(res);
        if(res.success){
          this.archiveProblems = res.data.problems
          this.searchCondition.total = parseInt(res.data.total)
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        this.searchShowSpin = false
        this.errorNotify(err)
      })
    },
    openArchiveProblems(){
      this.dialogVisible = true
      this.searchProblemsByCondition()
    },

    addContestProblem(row){
      if (this.problems.find(item => item.problemId === row.id) !== undefined){
        this.warnNotify('比赛已经包含题目了')
      }else if (this.problems.find(item => item.problemDisplayId === row.displayId) !== undefined){
        this.warnNotify('显示ID和比赛题目重复,请修改比赛中题目显示ID')
      }else{
        addContestProblemById({
          contestId: this.$route.params.contestId,
          problemId: row.id,
          problemName: row.name,
          authorName: row.authorName,
          problemDisplayId: row.displayId
        }).then(res => {
          console.log(res);
          if(res.success){
            this.successNotify(res.message)
            this.getContestProblemsByCondition()
          }else{
            this.errorNotify(res.message)
          }
        }).catch(err => {
          this.errorNotify(err)
        })
      }
    },
    updateContestProblemVisibility(row) {
      let problemId = row.problemId
      let visible = row.visible
      let contestId = this.$route.params.contestId
      updateContestProblem({contestId, problemId, visible}).then(res => {
        console.log(res);
        if (res.success) {
          this.successNotify(res.message)
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        console.log(err);
      })
    },
    getContestProblemsByCondition() {
      this.showSpin = true
      getContestProblemByCondition(this.condition).then(res => {
        this.showSpin = false
        console.log(res);
        if (res.success) {
          this.problems = res.data.contestProblems
          this.condition.total = parseInt(res.data.total)
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        this.showSpin = false
        this.errorNotify(err)
      })
    },
    handleSearchSizeChange(pageSize) {
      this.searchCondition.pageSize = pageSize
      this.searchProblemsByCondition()
    },
    handleSearchCurrentChange(currentPage) {
      this.searchCondition.currentPage = currentPage
      this.searchProblemsByCondition()
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getContestProblemsByCondition()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getContestProblemsByCondition()
    },
    downloadTestCase() {

    },
    editContestProblemDisplayId(row) {
      this.displayIdInput = row.problemDisplayId
      this.currentEditorProblemId = row.problemId
      this.displayIdEditorVisible = true
    },
    deleteProblem(row) {
      deleteContestProblemByProblemId({
        contestId: this.$route.params.contestId,
        problemId: row.problemId
      }).then(res => {
        console.log(res);
        if (res.success) {
          this.successNotify(res.message)
          this.getContestProblemsByCondition()
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
  }
}
</script>

<style scoped>

</style>
