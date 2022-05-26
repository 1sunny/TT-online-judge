<template>
  <div style="height: 100%;
              padding: 20px;
              position: relative;
             "
  >
    <Spin size="large" fix v-if="spinShow"></Spin>
    <el-form ref="problem"
             :model="problem"
             :rules="problemRules"
             label-width="120px"
             label-position="top"
             inline
    >
      <el-row :gutter="10">
        <el-col :span="8">
          <el-form-item label="题目ID" prop="displayId">
            <el-input v-model="problem.displayId" ref="displayIdInput"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="题目名字" prop="name" style="width: 80%">
            <el-input v-model="problem.name"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="题目描述" prop="description" style="width: 100%;">
            <mavon-editor v-model="problem.description"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="输入格式" prop="input" style="width: 100%;">
            <mavon-editor v-model="problem.input"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="输出格式" prop="output" style="width: 100%;">
            <mavon-editor v-model="problem.output"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="题目提示" prop="hint" style="width: 100%;">
            <mavon-editor v-model="problem.hint"/>
          </el-form-item>
        </el-col>
      </el-row>
      <!--  sample -->
      <el-row :gutter="10">
        <el-col :span="24">
          <el-form-item label="题目样例" style="width: 100%" prop="sampleCase">
            <el-collapse v-model="activeTestCases">
              <el-collapse-item v-for="(item,index) in problem.sampleCase"
                                :key="index"
                                :name="index+''">
                <template slot="title">
                  <div style="display: flex;
                              justify-content: space-between;
                              width: 100%;
                              padding: 0 5px;
                             ">
                    <span>样例{{ index + 1 }}</span>
                    <div>
                      <el-button type="warning" plain size="mini"
                                 @click="deleteTestCase(index)"
                      >删除
                      </el-button>
                    </div>
                  </div>
                </template>
                <div style="display: flex;justify-content: space-between">
                  <el-input
                      v-model="item.input"
                      :rows="4"
                      type="textarea"
                      placeholder=""
                      style="width: 48%;"
                      maxlength="1000"
                      show-word-limit
                  />
                  <el-input
                      v-model="item.output"
                      :rows="4"
                      type="textarea"
                      placeholder=""
                      style="width: 48%;"
                      maxlength="1000"
                      show-word-limit
                  />
                </div>
              </el-collapse-item>
            </el-collapse>
            <el-button type="info" plain
                       style="width: 100%
                                         "
                       @click="addTestCase"
            >增加样例
            </el-button>
          </el-form-item>
          <div class="demo-block-control">
            i
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="10">
        <el-col :span="6">
          <el-form-item label="时间限制(ms)" prop="timeLimit">
            <el-input-number v-model="problem.timeLimit" :step="500"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="内存限制(MB)" prop="memoryLimit">
            <el-input-number v-model="problem.memoryLimit" :min="1" :max="528" :step="16"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="题目难度" prop="level">
            <el-select v-model:label="problem.level"
                       placeholder="请选择"
                       :default-first-option="true"
            >
              <el-option
                  v-for="item in levelOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="4" :offset="2">
          <!--     label 不会就是 form 的 name  -->
          <el-form-item label="是否可见" prop="visible">
            <el-switch v-model="problem.visible"/>
          </el-form-item>
        </el-col>
      </el-row>
      <!--   action   -->
      <el-row :gutter="10">
        <el-col :span="8">
          <el-form-item label="测试用例" prop="testCaseFileProp">
            <el-upload
                class="upload-demo"
                action="do not need"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                multiple
                :limit="1"
                :on-exceed="handleExceed"
                :file-list="fileList"
                method="post"
                :auto-upload="false"
                :on-change="uploadChange"
                ref="uploadTestCaseFile"
            >
              <el-button size="small" type="primary">点击上传</el-button>
              <template #tip>
                <div class="el-upload__tip">
                  请上传大小不超过100MB的 zip/rar 压缩文件
                </div>
              </template>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="可选语言" prop="languages">
            <el-checkbox-group v-model="problem.languages">
              <el-checkbox v-for="(item,index) in $config.supportLanguages"
                           :label="item.value"
                           name="languages"
                           :key="index"
              />
            </el-checkbox-group>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="题目标签" prop="tags">
            <el-tag
                v-for="tag in problemTags"
                :key="tag"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)"
            >
              {{ tag }}
            </el-tag>
            <el-input
                v-if="inputVisible"
                ref="saveTagInput"
                v-model="inputValue"
                class="input-new-tag"
                size="mini"
                @keyup.enter="handleInputConfirm"
                @blur="handleInputConfirm"
            >
            </el-input>
            <el-button v-else class="button-new-tag" size="small" @click="showInput"
            >+ New Tag
            </el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10" style="display: flex;align-items: center">
        <el-col :span="6">
          <el-form-item label="作者" style="width: 90%" prop="authorName">
            <el-input v-model="problem.authorName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="14">
          <el-form-item label="题目来源" style="width: 90%" prop="source">
            <el-input v-model="problem.source"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-button type="primary" @click="save" style="margin-bottom: 20px">保存题目</el-button>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {getProblemById, saveProblem, updateProblem} from "../../../api/manage/ManageProblemApi";
import {mapGetters} from "vuex";

export default {
  name: "ManageProblemEditor",
  components: {},
  data() {
    let validateTimeLimit = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入时间限制'));
      } else {
        let limit = parseInt(value)
        if (limit >= 1 && limit <= 10000){
          callback();
        }else{
          callback(new Error('时间限制的范围是1-10000'));
        }
      }
    };
    let validateMemoryLimit = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入空间限制'));
      } else {
        let limit = parseInt(value)
        if (limit >= 1 && limit <= 1024){
          callback();
        }else{
          callback(new Error('空间限制的范围是1-1024'));
        }
      }
    };
    let validateTestCaseFile = (rule, value, callback) => {
      if (this.problemId == -1 && this.$refs.uploadTestCaseFile.uploadFiles.length == 0){
        callback(new Error('请上传测试文件'));
      }else{
        callback();
      }
    };
    return {
      spinShow: false,
      problemId: -1,
      problem: {
        displayId: '',
        name: '',
        description: '',
        input: '',
        output: '',
        hint: '',
        timeLimit: 1000,
        memoryLimit: 256,
        visible: true,
        languages: this.$config.supportLanguages.map(x => x.value),
        authorName: this.username,
        authorId: this.userId,
        source: 'TTOJ problem',
        sampleCase: [],
      },
      // 难度选择
      levelOptions: [
        {
          value: '一眼',
          label: '一眼',
        },
        {
          value: '简单',
          label: '简单',
        },
        {
          value: '中等',
          label: '中等',
        },
        {
          value: '困难',
          label: '困难',
        },
        {
          value: '地狱',
          label: '地狱',
        },
      ],
      // 题目标签
      problemTags: [],
      // tag
      inputVisible: false,
      // tag
      inputValue: '',
      // 样例
      activeTestCases: [],
      file: null,
      fileList: [],
      problemRules: {
        displayId: [
          {
            required: true,
            message: '请填写题目ID',
            trigger: 'blur'
          },
          {
            min: 4, max: 10, message: '长度为4-10'
          }
        ],
        name: [
          {
            required: true,
            message: '请填写题目名称',
            trigger: 'blur'
          },
          {
            min: 1, max: 30, message: '长度为1-30'
          }
        ],
        description: [
          {
            required: true,
            message: '请填写题面描述',
            trigger: 'blur'
          }
        ],
        input: [
          {
            required: true,
            message: '请填写输入描述',
            trigger: 'blur'
          }
        ],
        output: [
          {
            required: true,
            message: '请填写输出描述',
            trigger: 'blur'
          }
        ],
        timeLimit: [
          {validator: validateTimeLimit, trigger: 'blur'},
        ],
        memoryLimit: [
          {validator: validateMemoryLimit, trigger: 'blur'},
        ],
        visible: [
          {
            required: true,
            message: '请填写题目是否可见',
            trigger: 'blur'
          }
        ],
        languages: [
          {
            type: 'array',
            required: true,
            message: '请至少选择一个可以提交的语言',
            trigger: 'blur'
          }
        ],
        authorName: [
          {
            required: true,
            message: '请填写题目作者',
            trigger: 'blur'
          },
          {
            min: 1, max: 50, message: '长度为1-50'
          }
        ],
        source: [
          {
            required: true,
            message: '请填写题目来源',
            trigger: 'blur'
          },
          {
            min: 1, max: 100, message: '长度为1-100'
          }
        ],
        testCaseFileProp: [
          {validator: validateTestCaseFile, trigger: 'blur'},
        ],
        level: [
          {
            required: true,
            message: '请选择题目难度',
            trigger: 'blur'
          }
        ],
        sampleCase: [
          {
            type: 'array',
            required: true,
            message: '请至少添加一个样例',
            trigger: 'blur'
          }
        ]
      },
    }
  },
  computed:{
    ...mapGetters([
      'userId',
      'username',
      'auth',
      'user',
      'showManageBtn'
    ]),
  },
  mounted(){
    this.$refs.displayIdInput.focus()
  },
  created() {
    let problemId = this.$route.params.problemId
    if (!!problemId){
      this.problemId = problemId
      this.spinShow = true
      getProblemById(problemId).then(res=>{
        this.showSpin = false
        if (res.success){
          let problem = res.data.problem
          problem.sampleCase = JSON.parse(problem.sampleCase)
          problem.languages = JSON.parse(problem.languages)
          console.log(problem);
          this.problem = problem
          for (let i = 0; i < this.problem.sampleCase.length; i++) {
            this.activeTestCases.push(i.toString())
          }
        }else{
          this.errorNotify(res.message)
        }
      }).catch(err => {
        err = JSON.stringify(err)
        this.showSpin = false
        this.errorNotify(err)
      })
    }
  },
  methods: {
    deleteTestCase(index) {
      console.log(this.problem.sampleCase, index);
      this.problem.sampleCase.splice(index, 1)
    },
    addTestCase() {
      this.problem.sampleCase.push({input: '', output: ''})
      this.activeTestCases.push(this.activeTestCases.length.toString())
    },
    uploadChange(file, fileList) {
      this.file = fileList[0].raw;
    },
    // 保存题面
    save() {
      this.$refs['problem'].validate(valid => {
        if (valid) {
          let uploadProblem = JSON.parse(JSON.stringify(this.problem))
          // tableData -> 样例
          uploadProblem.sampleCase = JSON.stringify(uploadProblem.sampleCase)
          // dynamicTags -> 标签
          //this.form.tag = JSON.stringify(this.dynamicTags) TODO
          uploadProblem.languages = JSON.stringify(uploadProblem.languages)
          const formData = new FormData()
          console.log(uploadProblem)
          uploadProblem = JSON.stringify(uploadProblem)
          formData.append("adminProblemSaveParams", uploadProblem)
          if (this.file){
            formData.append("testcase", this.file)
          }else{
            formData.append("testcase", null)
          }
          console.log(formData.get("adminProblemSaveParams"));
          console.log(formData.get("testcase"));
          console.log(this.problem);
          let config = {
            headers: {
              "Content-Type": "multipart/form-data"
            }
          }
          console.log(this.problem);
          console.log(this.problemId);
          if (!!this.problemId && this.problemId !== -1){
            updateProblem(formData, config).then(res => {
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
          }else{
            saveProblem(formData, config).then(res => {
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
          }
          console.log(this.problem);
        } else {
          this.warnNotify('参数不合法,请重试')
        }
      })
    },
    // 测试用例删除
    deleteRow(index, rows) {
      rows.splice(index, 1)
    },
    // tag
    handleClose(tag) {
      this.problemTags.splice(this.problemTags.indexOf(tag), 1)
    },
    // tag
    showInput() {
      this.inputVisible = true
      this.$nextTick((_) => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    // tag
    handleInputConfirm() {
      const inputValue = this.inputValue
      if (inputValue) {
        this.problemTags.push(inputValue)
      }
      this.inputVisible = false
      this.inputValue = ''
    },
    // file
    handlePreview() {

    },
    // file
    handleExceed() {

    },
    // file
    beforeRemove() {

    },
    // file
    handleRemove() {

    }
  }
}
</script>

<style scoped>
.el-tag {
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
