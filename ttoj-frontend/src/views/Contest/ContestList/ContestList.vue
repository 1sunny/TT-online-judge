<template>
  <div id="contestListWrapper"
       style="
             "
  >
    <el-row>
      <el-col :xs="24" :sm="24">
        <el-input v-model="condition.name"
                  placeholder="输入比赛名字"
                  clearable
                  style="width: 40%"
                  size="small"
                  suffix-icon="el-icon-search"
                  @keyup.enter.native="getContestList"
        >
        </el-input>
        <div style="
                  overflow: auto;
                  position: relative;
                 "
        >
          <Spin size="large" fix v-if="spinShow"></Spin>
          <ContestItem v-for="(item,index) in contests"
                       :key="item.id"
                       :contest="item"
                       style="margin-top: 10px"
          >
          </ContestItem>
        </div>
        <div class="block" style="padding: 10px 0px 10px 0px;
                                overflow: auto
                               "
        >
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :currentPage="condition.currentPage"
              :page-sizes="[15, 30]"
              :page-size="condition.pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="condition.total"
              background
          >
          </el-pagination>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ContestAside from "./components/ContestAside";
import ContestItem from "./components/ContestItem";
import {getContestsByCondition} from "../../../api/oj/contestApi";
import {getDurationString} from "../../../utils/time";

export default {
  name: "ContestList",
  components: {
    ContestAside,
    ContestItem
  },
  created() {
    this.getContestList()
  },
  data() {
    return {
      contests: [
        {
          id: 0,
          name: '加载中',
          description: '加载中',
          cover: 'default-contestCover.png',
          startTime: 0,
          endTime: 0,
          ruleType: 'ACM',
          registeredNum: 0
        }
      ],
      spinShow: true,
      condition: {
        currentPage: 1, pageSize: 15, visible: true, total: 0, name: ''
      }
    }
  },
  methods: {
    getContestList() {
      this.spinShow = true
      getContestsByCondition(this.condition).then(res => {
        console.log(res);
        let contests = res.data.contests
        for (let i in contests) {
          console.log(contests[i].startTime, contests[i].endTime);
          contests[i].duration = getDurationString(contests[i].startTime, contests[i].endTime)
        }
        this.condition.total = parseInt(res.data.total)
        this.contests = contests
        this.spinShow = false
      }).catch(err => {
        err = JSON.stringify(err)
        this.spinShow = false
        this.errorNotify(err)
      })
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getContestList()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getContestList()
    },
  }
}
</script>

<style scoped>
@media screen and (min-width: 920px) {
  #contestListWrapper {
    max-width: 1200px;
    margin: 20px auto 20px auto;
  }
}
</style>
