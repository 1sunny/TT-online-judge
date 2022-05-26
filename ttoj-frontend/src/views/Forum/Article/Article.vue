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
        <div style="position: relative;
                   ">
          <Spin size="large" fix v-if="articleShowSpin"></Spin>
          <el-card class="box-card"
                   style="margin-top: 10px;padding: 15px"
          >
            <div style="display: flex; justify-content: space-between">
              <div style="display: flex; align-items: center">
                <el-avatar :size="25" :src="article.authorAvatar" />
                <span style="margin-left: 10px">{{article.title}}</span>
              </div>
              <div>{{article.publicationTime}}</div>
            </div>
            <div style="margin-top: 20px;
                        font-size: 14px;
                        line-height: 24px;
                        color: rgba(89, 89, 89, 1);
                        word-break: break-word;
                        align-self: stretch;
                        -webkit-line-clamp: 1;
                        overflow: hidden;
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                       "
            >
              {{article.description}}
            </div>
            <div style="display: flex;
                        align-items: center;
                        justify-content: flex-start;
                        margin: 20px 0 0 0;
                       ">
              <div><i class="el-icon-thumb"></i> {{article.likes}}</div>
              <div style="margin-left: 20px"><i class="el-icon-view"></i> {{article.viewNum}}</div>
            </div>
          </el-card>
        </div>
        <el-card class="box-card"
                 style="margin-top: 10px;
                       "
        >
          <div style="display: flex; justify-content: space-between; align-items: center">
            <div style="margin-left: 20px">共 {{article.commentNum}} 条评论</div>
            <el-button @click="drawer = true" type="success" icon="el-icon-edit">发表评论</el-button>
          </div>
        </el-card>
        <div style="position: relative;
                    margin-top: 15px;
                   ">
          <Spin size="large" fix v-if="commentShowSpin"></Spin>
          <el-card v-for="(item, i) in article.comments"
                   class="box-card"
                   style="padding: 15px"
          >
            <div style="display: flex; justify-content: space-between; align-items: center">
              <div style="display: flex;align-items: center">
                <el-avatar :size="25" :src="item.authorAvatar" />
                <span style="margin-left: 10px">{{item.authorName}}</span>
              </div>
              <div>{{item.commentTime}}</div>
            </div>
            <div style="margin-top: 10px;
                        font-size: 14px;
                        line-height: 24px;
                        color: rgba(89, 89, 89, 1);
                       "
            >
              {{item.content}}
            </div>
            <div style="display: flex; justify-content: space-between">
              <div style="display: flex;
                        align-items: center;
                        justify-content: flex-start;
                        margin: 20px 0 0 0;
                       ">
                <div><i class="el-icon-thumb"></i> {{item.likes}}</div>
                <div style="margin-left: 20px"><i class="el-icon-chat-line-round"></i> {{item.commentNum}}</div>
              </div>
              <el-button @click="drawer = true" type="text" icon="el-icon-edit">回复</el-button>
            </div>

          </el-card>
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
        <ArticleAside/>
      </el-col>
    </el-row>
    <el-drawer
        title="发表评论"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose"
        :modal="false"
        size="45%"
        :show-close="false"
    >
      <div slot="title" style="margin-bottom: -32px">
        <div style="display: flex; align-items: center; justify-content: space-between">
          <el-button @click="drawer = false" size="small">取消</el-button>
          <div>
            <el-button @click="sendComment" type="success" icon="el-icon-edit">发表评论</el-button>
          </div>
        </div>
      </div>
      <mavon-editor v-model="drawerComment" style="height: 100%;"/>
    </el-drawer>
  </div>
</template>

<script>
import ArticleAside from "@/views/Forum/Article/ArticleAside";
export default {
  name: "Article",
  components: {ArticleAside},
  data(){
    return{
      drawer: false,
      direction: 'btt',
      drawerComment: '',
      article:{
        title: '春季来了',
        viewNum: 12,
        commentNum: 13,
        likes: 9,
        description: 'hahahaa',
        top: true,
        tags: ['c++', 'java'],
        authorId: 1,
        authorAvatar: 'https://img0.baidu.com/it/u=325674188,3280397254&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500',
        publicationTime: '2022/3/1',
        comments:[
          {
            authorName: 'onesunny',
            authorId: 1,
            content: '不错',
            authorAvatar: 'https://img0.baidu.com/it/u=325674188,3280397254&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500',
            likes: 1,
            commentNum: 2,
            commentTime: '2022/3/3'
          }
        ]
      },
      condition: {
        currentPage: 1,
        total: 0,
        pageSize: 20,
      },
      articleShowSpin: true,
      commentShowSpin: true,
    }
  },
  computed:{
    articleId: 0
  },
  created() {
    let articleId = this.$route.params.articleId
    this.articleId = articleId
    this.getArticleById()
    this.getCommentsByCondition()
  },
  methods:{
    sendComment(){

    },
    handleClose(done) {
      done()
    },
    getArticleById(){
      this.articleShowSpin = false
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getCommentsByCondition()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getCommentsByCondition()
    },
    getCommentsByCondition(){
      this.commentShowSpin = false
    }
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
