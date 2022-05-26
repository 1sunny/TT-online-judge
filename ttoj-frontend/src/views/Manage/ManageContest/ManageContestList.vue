<template>
  <div style="
           "
  >
    <div style="margin: 10px 20px">
      <el-input v-model="condition.name"
                placeholder="输入比赛名字"
                clearable
                style="width: 30%"
                size="small"
      >
      </el-input>
      <el-button icon="el-icon-search"
                 @click="getContestsByCondition"
                 size="small"
      >搜索
      </el-button>
    </div>
    <div style="position: relative;flex: 1 1 auto;overflow: auto">
      <Spin fix size="large" v-if="showSpin"></Spin>
      <el-table
          :data="contests"
          style="">
        <el-table-column type="expand">
          <template slot-scope="props">
            <div style="padding: 0 20px 20px 20px">
              <el-tooltip class="item" effect="dark" placement="top">
                <div slot="content">{{ $config.hint.contestAuthor }}</div>
                <span class="itemTitle">比赛出题人:</span>
              </el-tooltip>
              <br>
              <el-tag
                  :key="tag.userId"
                  v-for="tag in props.row.contestAuthors"
                  :closable="tag.username !== props.row.creatorName"
                  :disable-transitions="false"
                  @close="handleClose(props.row, tag)"
                  effect="plain"
                  style="margin-top: 10px"
              >
                {{ tag.username }}
              </el-tag>

              <el-autocomplete
                  size="small"
                  class="input-new-tag"
                  ref="saveTagInput"
                  v-if="contestAuthorInputVisible"
                  v-model="contestAuthorInputValue"
                  :fetch-suggestions="(str, cb) => querySearchAsync(str, cb, props.row)"
                  placeholder="请输入内容"
                  @select="(item) => handleSelect(item, props.row)"
                  style="width: 20%"
              ></el-autocomplete>

              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 增加出题人</el-button>
              <el-button plain size="small" @click="saveContestAuthors(props.row)">保存</el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column
            prop="name"
            label="名字"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="creatorName"
            label="创建者"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="status"
            label="状态"
            align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.status === -1">未开始</span>
            <span v-else-if="scope.row.status === 0">进行中</span>
            <span v-else>已结束</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="ruleType"
            label="比赛规则"
            align="center"
        >
          <template slot-scope="scope">
            <span v-if="scope.row.ruleType === 1">ACM</span>
            <span v-else-if="scope.row.ruleType === 2">OI</span>
            <span v-else-if="scope.row.ruleType === 3">IOI</span>
            <span v-else>未知</span>
          </template>
        </el-table-column>
        <el-table-column
            prop="createTime"
            label="创建时间"
            align="center"
        ></el-table-column>
        <el-table-column
            prop="visible"
            label="是否可见"
            align="center"
        >
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.visible"
                @change="changeContestVisibility(scope.row)"
            >
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="150px"
        >
          <template slot-scope="scope">
            <el-button @click="editContest(scope.row)" size="mini"
                       icon="el-icon-edit" plain
            >
            </el-button>
            <el-divider direction="vertical" style="height: 100%"/>
            <el-button @click="listContestProblems(scope.row)" size="mini" plain
            >
              <Icon type="md-list"/>
            </el-button>
            <el-divider direction="vertical" style="height: 100%"/>
            <el-popconfirm
                title="确定删除吗？"
                @confirm="deleteContest(scope.row)"
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
    <div class="block" style="margin: 10px 10px 10px 20px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :currentPage="condition.currentPage"
          :page-sizes="[10, 20]"
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

import {
  changeContestVisibility,
  deleteContestById,
  getContestAuthorUsersBySearch,
  getContestsByCondition, saveContestAuthors, updateContestAuthors
} from "../../../api/manage/ManageContestApi";

export default {
  name: "ManageContestList",
  data() {
    return {
      dynamicTags: ['标签一', '标签二', '标签三'],
      inputVisible: false,
      inputValue: '',
      contestAuthorInputVisible: false,
      contestAuthorInputValue: '',
      contests: [],
      condition: {
        currentPage: 1,
        total: 0,
        name: '',
        pageSize: 10,
      },
      showSpin: true,
      contestAuthorCandidate: [{value: '请输入昵称进行搜索'}],
      state: '',
      timeout:  null
    }
  },
  created() {
    this.getContestsByCondition()
  },
  mounted() {
  },
  methods: {
    querySearchAsync(queryString, cb, contest) {
      console.log(contest);
      if (!queryString){
        cb(this.contestAuthorCandidate);
        return
      }
      getContestAuthorUsersBySearch({username: queryString, contestId: contest.id}).then(res => {
        console.log(res);
        if (res.success){
          this.contestAuthorCandidate = res.data.contestAuthors.filter(item => contest.contestAuthors.indexOf(item.username) === -1)
          this.contestAuthorCandidate.forEach(item => item.value = item.username)
          cb(this.contestAuthorCandidate);
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err=>{
        this.errorNotify(err)
      })
    },
    handleSelect(user, contest) {
      console.log(user);
      if (!contest.contestAuthors.find(item => item.username === user.username)) {
        contest.contestAuthors.push({username: user.username, userId: user.userId});
      }
      this.contestAuthorInputVisible = false;
      this.contestAuthorInputValue = '';
    },
    saveContestAuthors(contest) {
      updateContestAuthors({contestId: contest.id,
                                                    contestAuthors: contest.contestAuthors.map(item =>  {
                                                      return {
                                                        authorId: item.userId,
                                                        username: item.username
                                                      }
                                                    })})
      .then(res=>{
        console.log(res);
        if(res.success){
          this.successNotify(res.message)
        }else{
          this.errorNotify(res.message)
        }
      })
    },
    listContestProblems(contest) {
      console.log(contest);
      this.$router.push(`/manage/contests/${contest.id}/problems`)
    },
    changeContestVisibility(contest) {
      let contestId = contest.id
      let visible = contest.visible
      changeContestVisibility(contestId, visible).then(res => {
        console.log(res);
        if (!res.success) {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    getContestsByCondition() {
      console.log(11);
      this.showSpin = true
      getContestsByCondition(this.condition).then(res => {
        console.log(res);
        this.showSpin = false
        if (res.success) {
          this.contests = res.data.contests
          this.condition.total = parseInt(res.data.total)
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.showSpin = false
        this.errorNotify(err)
      })
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getContestsByCondition()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getContestsByCondition()
    },
    editContest(contest) {
      this.$router.push(`/manage/contests/${contest.id}`)
    },
    deleteContest(row) {
      deleteContestById(row.id).then(res => {
        console.log(res);
        if (res.success) {
          this.successNotify(res.message)
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    handleClose(contest, tag) {
      contest.contestAuthors.splice(contest.contestAuthors.indexOf(tag), 1);
    },

    showInput() {
      this.contestAuthorInputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },
  }
  ,

}
</script>

<style scoped>
.el-button {
  margin-left: 3px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}

.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
