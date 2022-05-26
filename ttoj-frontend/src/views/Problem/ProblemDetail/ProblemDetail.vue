<template>
  <div>
    <div class="hidden-sm-and-down"
         :style="{
                  height: 'calc(100vh - 46px - ' + (mode === 'contest' ? '40px' : '0px') + ')',
                  width: '100%',
                  overflow: 'auto'
                 }"
    >
      <Split v-model="split_h" style="height: 100%;width: 100%;">
        <div slot="left" style="height: 100%;
                                display: flex;
                                flex-direction: column;
                                padding-left: 20px;
                               "
        >
          <el-tabs v-model="activeName"
                   @tab-click="handleClick"
                   style="margin-bottom: 10px"
          >
            <el-tab-pane label="题目描述" name="description"/>
            <el-tab-pane v-if="mode !== 'contest'" label="评论" name="comments"/>
            <el-tab-pane v-if="mode !== 'contest'" label="题解" name="solutions"/>
            <el-tab-pane v-if="mode !== 'contest'" label="提交记录" name="submitRecord"/>
            <el-tab-pane v-if="mode === 'contest'" label="题目解释" name="explanation"/>
          </el-tabs>
          <router-view style="flex: 1 1 auto;overflow: auto;padding-bottom: 20px"/>
        </div>

        <div slot="right" style="height: 100%;
                                 width: 100%;
                                 display: flex;
                                 flex-direction: column;
                                 flex: 1 0 auto;
                                ">
          <Split v-model="split_v" mode="vertical" max="40px">

            <div slot="top" style="height: 100%;
                                   display: flex;
                                   flex-direction: column;
                                   flex: 1 0 auto;
                                "
            >
              <div style="line-height: 40px;
                          height: 40px;
                          background-color: #f7f8fa;
                          padding: 0 20px;
                       "
              >
                <el-select size="mini"
                           v-model="editor.cmOptions.mode"
                           placeholder="语言"
                           style="width: 100px"
                >
                  <el-option
                      v-for="item in languageOptions"
                      :key="item.index"
                      :label="item.label"
                      :value="item.value">
                  </el-option>
                </el-select>
              </div>
              <div style="flex-grow: 1;overflow: auto;padding-left: 8px;">
                <codemirror v-model="editor.code"
                            :options="editor.cmOptions"
                            @input="onCodeChange"
                            style="height: 100%;"
                />
              </div>
            </div>
            <div slot="bottom"
                 style="height: 100%;
                      display: flex;
                      flex-direction: column;
                     "
            >
              <el-tabs type="border-card" style="flex: 1 1 auto;
                                               padding-left: 8px;
                                              "
                       v-model="consoleActiveTabName"

              >
                <el-tab-pane style="height: 100%;">
                  <span slot="label">测试用例</span>
                  <codemirror v-model="testEditor.code"
                              :options="testEditor.cmOptions"
                              @input="onTestCaseChange"
                              style="height: 100%;"
                  />
                </el-tab-pane>
                <el-tab-pane style="height: 100%;">
                  <span slot="label" id="testResult"> 测试结果</span>
                  test
                </el-tab-pane>
                <el-tab-pane style="height: 100%;">
                  <span slot="label" id="submitResult">运行结果</span>
                  <div style="display: flex;
                          flex-direction: column;
                          padding: 15px
                         "
                  >
                    <div v-if="!auth">请登录后提交代码</div>
                    <div v-else-if="auth && judgeResult===null">提交之后，这里将会显示运行结果</div>
                    <div v-else :style="{
                            borderRadius: '5px',
                            display: 'flex',
                            flexDirection: 'column',
                            background: judgeResult.result === 'Accepted' ? '#eefaf7' : '#ffe7e7',
                            padding: '15px'
                            }
                           "
                    >
                      <div style="display: flex;
                                  justifyContent: flex-start;
                             "
                      >
                        <div :style="{fontWeight: 700,
                                  color: judgeResult.result === 'Accepted' ? '#32CA99' : '#EA0E07',
                                  marginRight: '20px'
                                 }"
                        >
                          {{ judgeResult.result }}
                        </div>
                        <div v-if="!!judgeResult.timeUse" style="color: #999999;
                                font-size: 14px;
                                margin-right: 20px;
                               "
                        >
                          运行时间: {{ judgeResult.timeUse }}ms
                        </div>
                        <div v-if="!!judgeResult.memoryUse" style="color: #999999;
                              font-size: 14px;
                              margin-right: 20px;
                             "
                        >
                          内存消耗: {{ judgeResult.memoryUse }}KB
                        </div>
                      </div>
                      <div v-if="judgeResult.result == 'Accepted'"
                           style="color: #32CA99;margin-top: 20px"
                      >
                        答案正确:恭喜！您提交的程序通过了所有的测试用例
                      </div>
                      <div v-else
                           style="color: #EA0E07;margin-top: 20px"
                      >
                    <span v-if="!!judgeResult.errorReason">
                      {{ judgeResult.errorReason }}
                    </span>
                        <span v-else>答案错误:您提交的程序没有通过所有的测试用例</span>
                      </div>
                    </div>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
          </Split>
          <div style="display: flex;justify-content: space-between;
                        padding: 15px;
                        height: 50px;
                        line-height: 50px;
                        z-index: 9;
                        background: #fff;
                        border-top: 1px solid #F2F3F5;
                       "
          >
            <div style="display: flex;align-items: center">
              <el-button type="text" @click="switchConsole">
                <span v-if="consoleOpen">关闭控制台</span>
                <span v-else>打开控制台</span>
              </el-button>
            </div>
            <div style="display: flex;align-items: center">
              <el-button type="success" plain size="small"
                         @click="runTest" disabled
              >测试运行
              </el-button>
              <el-button type="primary" plain size="small"
                         @click="submit"
                         :loading="submitLoading"
              >提交代码
              </el-button>
            </div>
          </div>
        </div>
      </Split>
    </div>

    <!--  非电脑屏幕  -->
    <div class="hidden-md-and-up"
         style="width: 100%"
    >
      <el-row :gutter="20" style="padding: 0px 0px 20px 20px;width: 100%">
        <el-col :span="24">
          <el-tabs v-model="activeName"
                   @tab-click="handleClick"
                   style="margin-bottom: 10px"
          >
            <el-tab-pane label="题目描述" name="description"/>
            <el-tab-pane v-if="mode !== 'contest'" label="评论" name="comments"/>
            <el-tab-pane v-if="mode !== 'contest'" label="题解" name="solutions"/>
            <el-tab-pane v-if="mode !== 'contest'" label="提交记录" name="submitRecord"/>
          </el-tabs>
          <router-view/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<!--TODO  keep alive  代码丢失-->
<script>
// import language js
import 'codemirror/mode/javascript/javascript.js'
import 'codemirror/mode/clike/clike.js'
// import theme style
import 'codemirror/theme/idea.css'
import ProblemDescription from "./ProblemDescription";
import {getProblemById} from "../../../api/oj/problemApi";
import {archiveJudge, contestSubmit} from "../../../api/oj/judgeApi";
import {mapGetters} from "vuex";
import {getProblemByContestIdAndDisplayId} from "../../../api/oj/contestProblemApi";
import ChatBox from "../../../components/ChatBox";
import ProblemExplanation from "../ProblemExplanation";

export default {
  name: "problemDetail",
  components: {ChatBox, ProblemDescription},
  props: {
    propTabName: {
      type: String,
      default: ''
    }
  },
  computed: {
    ...mapGetters([
      'user',
      'userId',
      'username',
      'auth'
    ])
  },
  data() {
    return {
      consoleActiveTabName: 'QA',
      msgList: [this.initialMsg],
      initialMsg: "如果你遇到相关问题(例如题目等),请对问题进行描述,出题人将会进行解答。\n" +
          "注意: 任何与题目做法(思路)相关问题不予回答或回复“拒绝回答”。",
      consoleOpen: true,
      submitLoading: false,
      languageOptions: [
        {value: 'text/x-c++src', label: 'C++'},
        {value: 'text/x-csrc', label: 'C'},
        {value: 'text/x-go', label: 'Golang'},
        {value: 'text/x-java', label: 'Java'},
        {value: 'text/x-python', label: 'Python2'},
        {value: 'text/x-python', label: 'Python3'}
      ],
      split_h: 0.5,
      split_v: 0.6,
      mode: '',
      editor: {
        code: '// code here',
        cmOptions: {
          tabSize: 4,
          mode: 'text/x-c++src',
          theme: 'idea',
          lineNumbers: true,
          line: true,
          styleActiveLine: true, // 高亮选中行
          foldGutter: true, // 块槽
          matchBrackets: true,
          indentWithTabs: true,
          smartIndent: true,
          // more CodeMirror options...
        },
      },
      testEditor: {
        code: '// code here',
        cmOptions: {
          tabSize: 4,
          mode: '',
          theme: 'idea',
          lineNumbers: true,
          line: true,
          styleActiveLine: true, // 高亮选中行
          foldGutter: false, // 块槽
          matchBrackets: false,
          indentWithTabs: true,
          smartIndent: false,
          // more CodeMirror options...
        }
      },
      problem: {
        id: 0,
        displayId: '',
        name: '加载中',
        description: '加载中',
        input: '加载中',
        output: '加载中',
        hint: '',
        level: '',
        timeLimit: 0,
        memoryLimit: 0,
        vote: 0,
        submitNum: 0,
        acNum: 0,
        authorName: '',
        source: '',
        ioMode: '',
        solutionNum: 0,
        commentNum: 0,
        sampleCase: [{input: '', output: ''}, {input: '', output: ''}]
      },
      judgeResult: null, // 测评结果
      activeName: 'description', // tabbar
      contestId: 0,
    }
  },
  created() {
    console.log(this.$route);
    let displayId = this.$route.params.problemDisplayId
    let contestId = this.$route.params.contestId
    this.contestId = contestId
    if ((!!displayId) && (!!contestId)) {
      this.mode = 'contest'
      this.getProblemByContestIdAndDisplayId(contestId, displayId)
    } else {
      console.log(this.$route.params);
      let problemId = this.$route.params.problemId
      if (!!problemId) {
        this.getProblemById(problemId)
      } else {
        this.errorNotify('problemId无效')
      }
    }
  },
  methods: {
    switchConsole() {
      this.split_v = this.consoleOpen ? 1 : 0.6
      this.consoleOpen = !this.consoleOpen
    },
    runTest() {
      document.getElementById('testResult').click()
      let testCase = this.testEditor.code
    },
    getProblemById(problemId) {
      getProblemById(problemId).then(res => {
        console.log(res);
        this.problem = res.data.problem
        console.log(this.problem);
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    getProblemByContestIdAndDisplayId(contestId, displayId) {
      getProblemByContestIdAndDisplayId(contestId, displayId).then(res => {
        console.log(res);
        console.log(contestId, displayId);
        console.log(res);
        if (res.success) {
          this.problem = res.data.problem
          this.problem.displayId = displayId
          console.log(this.problem);
        } else {
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.errorNotify(err)
      })
    },
    refresh() {
      this.getProblemByContestIdAndDisplayId(this.contestId, this.problem.displayId)
    },
    onTestCaseChange(code) {
      this.testEditor.code = code
    },
    // TODO bug --> 直接url进入, tabbar错误
    handleClick(tab, event) {
      console.log(tab, event);
      let name = tab.name
      console.log(name);
      console.log('/problems/' + this.problem.id + '/' + (name == 'description' ? '' : name));
      this.activeName = name
      this.$router.push('/problems/' + this.problem.id + '/' + (name == 'description' ? '' : name))
      console.log(tab.props, event)
    },
    onCodeChange(code) {
      this.code = code
    },
    submit() {
      document.getElementById('submitResult').click()
      if (!this.auth) {
        this.warnNotify('请登录后再提交代码')
      } else {
        this.submitLoading = true
        this.judgeResult = null
        if (this.mode === 'contest') {
          contestSubmit({
            contestId: this.$route.params.contestId,
            problemId: this.problem.id,
            problemDisplayId: this.problem.displayId,
            userId: this.userId,
            username: this.username,
            code: this.editor.code,
            language: "cpp", // TODO
            ip: ''
          }).then(res => {
            this.submitLoading = false
            console.log(res);
            this.submitLoading = false
            if (res.success === false) {
              this.judgeResult = {}
              this.judgeResult.result = '出错啦,提交失败'
              this.judgeResult.errorReason = res.message
              this.judgeResult.timeUse = null
              this.judgeResult.memoryUse = null
            } else {
              this.judgeResult = res.data
              console.log(this.judgeResult);
            }
          }).catch(err => {
            err = JSON.stringify(err)
            this.submitLoading = false
            this.errorNotify(err)
          })
        } else {
          archiveJudge({
            userId: this.userId,
            username: this.username,
            problemId: this.problem.id,
            problemName: this.problem.problemName,
            code: this.editor.code,
            language: "cpp", // TODO
            ip: ''
          }).then(res => {
            this.submitLoading = false
            console.log(res);
            this.judgeResult = res.data
          }).catch(err => {
            err = JSON.stringify(err)
            this.submitLoading = false
            this.errorNotify(err)
          })
        }
      }
    }
  }
}
</script>

<style>
.tab {
  color: var(--greyTextColor);
}

.tab::after {
  content: "";
  height: 100%;
  width: 1px;
  background: #F2F3F5;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: block;
  z-index: 1;
  right: 0px;
}

.active {
  background: #fff;
  color: var(--deepTextColor);
}

.tab-content {
  height: 100%;
  margin-left: 8px;
}

.ivu-split-trigger-horizontal {
  opacity: 0;
}

.ivu-split-trigger-vertical {
  width: 8px;
  color: #fcfdfd;
}

.ivu-split-trigger-vertical:hover {
  background: #d0d4db;
}

.el-tabs--border-card > .el-tabs__content {
  padding: 0 !important;
}

.el-tabs--border-card {
  box-shadow: none;
  -webkit-box-shadow: none;
  border: none;

}

.el-tabs {
  display: flex !important;
  flex-direction: column !important;
}

.el-tabs__content {
  flex: 1 1 auto !important;
}

.el-tabs--border-card > .el-tabs__header {
  background: #f7f8fa;
}
</style>
