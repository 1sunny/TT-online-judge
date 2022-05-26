<template>
<div>
<!--  gutter: 卡片间距 -->
    <el-row :gutter="12">
      <el-col :span="6">
        <el-card shadow="always">
          <div class="sessionTitle">个人简介</div>
          <div style="">
            Expert {{user.username}}
          </div>
          <div class="singleItem">
            <i class="iconfont icon-score"></i>
            <span class="contentTitle">比赛积分:</span> {{user.rating}}
            (max. {{user.maxRating}})
          </div>
          <div class="singleItem">
            <i class="iconfont icon-dianzan"></i>
            <span class="contentTitle">获得点赞数:</span>{{user.contribution}}
          </div>
          <div class="singleItem">
            <i class="iconfont icon-wodeguanzhu"></i>
            <span class="contentTitle">关注他的:</span>{{user.friendsNum}} 个用户
          </div>

          <div class="singleItem">
            <i class="iconfont icon-youxiang"></i>
            <span class="contentTitle">邮箱:</span>{{user.email}}
          </div>
          <div class="singleItem">
            <i class="iconfont icon-zuijinfangwen"></i>
            <span class="contentTitle">最近访问:</span>{{user.lastLogin}}
          </div>
          <div class="singleItem">
            <i class="iconfont icon-zhuceshijian"></i>
            <span class="contentTitle">注册时间:</span>{{user.createTime}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always">
          <div style="display: flex;flex-direction: column;">
            <span class="contentTitle">解决问题</span>
            <b>{{user.acNum}}</b>
          </div>
          <div style="display: flex;">
            <div class="progressBox">
              <div class="difficulty" style="color: var(--veryEasy);">
                一眼
              </div>
              <div class="numCount">
                <span class="acceptedNum">1</span><span class="totalNum">/2</span>
              </div>
            </div>
            <div  class="progressBox">
              <div class="difficulty" style="color: var(--easy);">
                简单
              </div>
              <div class="numCount">
                <span class="acceptedNum">1</span><span class="totalNum">/2</span>
              </div>
            </div>
            <div  class="progressBox">
              <div class="difficulty" style="color: var(--medium);">
                中等
              </div>
              <div class="numCount">
                <span class="acceptedNum">1</span><span class="totalNum">/2</span>
              </div>
            </div>
            <div  class="progressBox">
              <div class="difficulty" style="color: var(--hard);">
                困难
              </div>
              <div class="numCount">
                <span class="acceptedNum">1</span><span class="totalNum">/2</span>
              </div>
            </div>
            <div  class="progressBox">
              <div class="difficulty" style="color: var(--veryHard)">
                地狱
              </div>
              <div class="numCount">
                <span class="acceptedNum">1</span><span class="totalNum">/2</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always">

        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="always">

        </el-card>
      </el-col>
    </el-row>
</div>
</template>

<script>


export default {
  name: "UserInfo",
  components:{

  },
  data(){
    return{
      user: {
        username: '111',
        email: '2944733883@qq.com',
        lastLogin: 1642779373127,
        createTime:1642779373127,
        rating: 1610,
        contribution:10,
        avatar:'/default-avatar.png',
        friendsNum:10,
        acNum:9},
      size: 20,
      color: '#409EFC'
    }
  },
  methods:{
  },
  created() {

  },
  mounted() {
  },

}
</script>

<style scoped>
.difficulty{
  font-size: 12px;
  line-height: 17px;
}
.numCount{
  margin-top: 2px;
  line-height: 14px;
}
.acceptedNum{
  color: rgba(33,40,53,0.75);
  font-size: 14px;
  font-weight: 500;
  position: relative;
}
.totalNum{
  color: rgba(52,61,73,0.3);
  font-weight: 500;
  font-size: 12px;
}
.progressBox{
  display: flex;
  flex-direction: column;
  flex: 1 1 auto;
}
.singleItem{
  display: flex;
  align-items: center;
  margin-top: 10px;
}
</style>
