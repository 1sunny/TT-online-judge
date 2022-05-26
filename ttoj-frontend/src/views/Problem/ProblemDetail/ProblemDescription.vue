<template>
  <div style="width: 100%;
              display: flex;
              flex-direction: column;
              color: var(--deepTextColor);
             ">

    <div style="
                color: rgba(33, 40, 53, 1);
                text-decoration: none;
                text-overflow: ellipsis;
                font-size: 15px;
                ">
      {{ problem.displayId }}. {{ problem.name }}
    </div>
    <div style="display: flex;
                align-items: center;
                color: rgba(132, 139, 150, 1);
                margin-top: 12px;
                font-size: 12px;
                line-height: 12px;
                user-select: none;
                padding: 0px 0px 16px;
                border-bottom: 1px solid rgba(229, 231, 235, 1);
               "
    >
      <span>难度: </span>
      <span style="margin-right: 15px;">{{ problem.level }}</span>
      <button style="
                    margin-right: 6px;
                    display: flex;
                    -webkit-box-align: center;
                    align-items: center;
                    padding: 0px 10px;
                    outline: 0px;
                    border: 0px;
                    line-height: 12px;
                    background: transparent;
                    cursor: pointer;
                    transition: color 0.18s ease-in-out 0s;
                    color: rgba(69, 77, 89, 1);
                    text-transform: none;
                    ">
        <span style="margin-right: 5px">{{ problem.vote }}</span>
        <Icon type="md-thumbs-up" />
      </button>
      <span>作者: {{problem.authorName}}</span>
    </div>
    <div style="padding: 1em 0">
      <MarkdownArea :content="problem.description" bgc="#fff"/>
    </div>
    <div class="itemTitle">输入描述</div>
    <div style="width: 100%;">
      <MarkdownArea :content="problem.input" bgc="#fff"/>
    </div>
    <div class="itemTitle">输出描述</div>
    <div class="itemContent">
      <MarkdownArea :content="problem.output" bgc="#fff"/>
    </div>
    <el-divider><i class="el-icon-apple"></i></el-divider>
    <div v-for="(item,i) in problem.sampleCase">
      <div class="itemTitle">示例 {{ i + 1 }}:</div>
      <div style="display: flex;justify-content: space-between">
        <el-card shadow="never" style="width: 48%">
          {{ item.input }}
        </el-card>
        <el-card shadow="never" style="width: 48%">
          {{ item.output }}
        </el-card>
      </div>
    </div>
    <el-collapse v-if="mode!=='contest'" style="margin-top: 20px"
                 v-model="activeNames" @change="handleChange"
    >
      <el-collapse-item title="显示提示" name="1">
        <div>{{problem.hint}}</div>
      </el-collapse-item>
      <el-collapse-item title="显示题目标签" name="showTags">
        <el-button type="text" v-for="(item) in problem.tags">
          {{item.name}}
        </el-button>
      </el-collapse-item>
    </el-collapse>
  </div>

</template>

<script>
import MarkdownArea from "@/components/MarkdownArea";
import {getProblemById} from "../../../api/oj/problemApi";
import {getProblemByContestIdAndDisplayId} from "../../../api/oj/contestProblemApi";

export default {
  name: "ProblemDescription",
  components: {
    MarkdownArea
  },
  props: {

  },
  data() {
    return {
      problem: {
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
        tags: []
      },
      activeNames: [],
      mode: ''
    }
  },
  created() {
    let contestId = this.$route.params.contestId
    if (!!contestId){
      this.mode = 'contest'
      let problemDisplayId = this.$route.params.problemDisplayId
      getProblemByContestIdAndDisplayId(contestId, problemDisplayId).then(res=>{
        console.log(res);
        if(res.success){
          this.problem = res.data.problem
          this.problem.sampleCase = JSON.parse(this.problem.sampleCase)
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    }else{
      let problemId = this.$route.params.problemId
      getProblemById(problemId).then(res => {
        console.log(res);
        if(res.success){
          this.problem = res.data.problem
          this.problem.sampleCase = JSON.parse(this.problem.sampleCase)
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    }
  },
  methods: {
    handleChange(active){

    }
  }
}
</script>

<style scoped>

</style>
