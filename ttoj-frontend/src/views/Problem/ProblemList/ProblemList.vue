<template>
  <div id="problemListWrapper"
       style="width: 100%;
              margin-top: 20px;
              display: flex;
              justify-content: space-between
             "
  >
    <el-row style="width: 100%;">
      <el-col :xs="24" :sm="17"
              style="flex: 1 1 auto;
                    "
      >
        <div style="display: flex;align-items: center;">
          <el-button @click="openTagDrawer" plain size="mini">
            <Icon type="ios-attach" size="16"/>
            打开标签筛选器
          </el-button>
          <el-divider direction="vertical" style="height: 100%"/>
          <el-input v-model="condition.name"
                    placeholder="输入题目名字"
                    clearable
                    style="width: 30%"
                    size="small"
                    suffix-icon="el-icon-search"
                    @keyup.enter.native="getProblemsByCondition"
          >
          </el-input>
        </div>
        <div style="position: relative;
                    margin-top: 20px;
                   "
        >
          <Spin size="large" fix v-if="showSpin"></Spin>
          <el-table :data="problems" stripe
                    style=""
                    @row-click="goToDetail"
          >
            <!-- TODO 个人通过状态 , 搜索 , 分页 -->
            <el-table-column prop="alreadyPassed" label="状态" width="60px" align="center">
              <template slot-scope="scope">
                <Icon v-if="scope.row.alreadyPassed" type="md-checkmark" color="#2DB55D"/>
                <Icon v-else type="md-remove" color="#BFBFBF"/>
              </template>
            </el-table-column>
            <el-table-column prop="displayId" label="#" width="80px" align="center"/>
            <el-table-column prop="name" label="题目"/>
            <el-table-column prop="level" label="难度" width="90px" align="center">
              <template slot-scope="scope">
                <span :style="{color: $config.levelColor[scope.row.level]}">{{scope.row.level}}</span>
              </template>
            </el-table-column>
            <el-table-column prop="acRate" label="通过率" sortable width="90px" align="center"/>
            <el-table-column prop="vote" label="点赞量" sortable width="90px" align="center"/>
          </el-table>
        </div>
        <div class="block" style="margin: 10px 0 0 0;overflow: auto">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :currentPage="condition.currentPage"
              :page-sizes="[30, 50]"
              :page-size="condition.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="condition.total"
              background
          >
          </el-pagination>
        </div>
      </el-col>
      <el-col :xs="24" :sm="7" style="padding-left: 20px"
      >
        <ProblemListAside/>
      </el-col>
    </el-row>
    <el-drawer
        title="功能暂未开发"
        :visible.sync="tagDrawer"
        :with-header="false"
        size="85%"
    >
      <div class="department-outer" style="position: relative">
        <div class="zoom-box">
          <zoom-controller v-model="zoom" :min="20" :max="200"></zoom-controller>
        </div>
        <div class="view-box">
          <org-view
              v-if="problemTagData"
              :data="problemTagData"
              :zoom-handled="zoomHandled"
              @on-menu-click="handleMenuClick"
          ></org-view>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import ProblemListAside from "./ProblemListAside";
import {getProblemsByCondition} from "../../../api/oj/problemApi";
import OrgView from "../../org-tree/components/org-view";
import ZoomController from "../../org-tree/components/zoom-controller";
import '../../org-tree/index.less'
import {getProblemTag} from "../../../api/oj/ProblemTagApi";

const menuDic = {
  edit: '编辑部门',
  new: '新增子部门',
  delete: '删除部门'
}
export default {
  name: "ProblemList",
  components: {
    ProblemListAside,
    OrgView,
    ZoomController
  },
  computed: {
    zoomHandled() {
      return this.zoom / 100
    }
  },
  data() {
    return {
      problemTagData: null,
      zoom: 100,
      problems: [
        {
          id: 0,
          displayId: 0,
          name: '加载中',
          description: '加载中',
          input: '加载中',
          output: '加载中',
          hint: '加载中',
          level: '.',
          timeLimit: '.',
          memoryLimit: '.',
          vote: '.',
          submitNum: '.',
          acNum: '.',
          authorName: '.',
          source: '.',
          ioMode: '.',
          solutionNum: '.',
          commentNum: '.',
          sampleCase: '.',
          alreadyPassed: false
        }
      ],
      condition: {
        currentPage: 1,
        total: 0,
        authorName: '',
        name: '',
        pageSize: 30,
        visible: true
      },
      showSpin: true,
      tagDrawer: false
    }
  },
  created() {
    this.getProblemsByCondition()
  },
  methods: {
    goToDetail(problem) {
      console.log(problem);
      this.$router.push('/problems/' + problem.id)
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getProblemsByCondition()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getProblemsByCondition()
    },
    getProblemsByCondition() {
      this.showSpin = true
      getProblemsByCondition(this.condition).then(res => {
        console.log(res);
        if (res.success) {
          this.problems = res.data.problems
          for (let i = 0; i < this.problems.length; i++) {
            if (this.problems[i].submitNum == 0) {
              this.problems[i].acRate = '0.00%'
            } else {
              this.problems[i].acRate = (this.problems[i].acNum * 100 / this.problems[i].submitNum).toFixed(1) + '%'
            }
          }
          this.condition.total = parseInt(res.data.total)
        } else {
          this.errorNotify(res.message)
        }
        this.showSpin = false
      }).catch(err => {
        console.log(err);
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    openTagDrawer() {
      this.tagDrawer = true
      this.getProblemTagData()
    },
    setProblemTagData(data) {
      data.isRoot = true
      return data
    },
    handleMenuClick({data, key}) {
      this.$Message.success({
        duration: 5,
        content: `点击了《${data.label}》节点的'${menuDic[key]}'菜单`
      })
    },
    getProblemTagData() {
      getProblemTag().then(res => {
        console.log(res);
        if (res.success) {
          this.problemTagData = res.data.problemTagTree
          console.log(this.problemTagData);
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
@media screen and (min-width: 920px){
  #problemListWrapper{
    max-width: 1200px;
    margin: 0 auto;
  }
}
</style>
