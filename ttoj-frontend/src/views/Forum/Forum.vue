<template>
  <div id="forumWrapper"
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
<!--          <Spin size="large" fix v-if="showSpin"></Spin>-->
          <el-tabs v-model="activeName" @tab-click="handleTabClick">
            <el-tab-pane label="最热" name="hot"></el-tab-pane>
            <el-tab-pane label="最新" name="new"></el-tab-pane>
            <el-tab-pane label="推荐" name="recommend"></el-tab-pane>
          </el-tabs>
          <el-card v-for="(item, index) in articles"
                   class="box-card"
                   style="margin-top: 10px;padding: 15px"
          >
            <div style="display: flex;align-items: center">
              <el-avatar :size="25" :src="item.authorAvatar" />
              <a style="margin-left: 10px" @click="goToArticleDetail(item.id)">{{item.title}}</a>
            </div>
            <el-tag v-for="(tag,i) in item.tags"
                    type="info"
                    :style="{margin: '10px 0 10px ' + (i == 0 ? '0' : '10px')}"
            >
              {{tag}}
            </el-tag>
            <div style="
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
              {{item.description}}
            </div>
            <div style="display: flex;
                        align-items: center;
                        justify-content: flex-start;
                        margin: 20px 0 0 0;
                       ">
              <div><i class="el-icon-thumb"></i> {{item.likes}}</div>
              <div style="margin-left: 20px"><i class="el-icon-view"></i> {{item.viewNum}}</div>
              <div style="margin-left: 20px"><i class="el-icon-chat-line-round"></i> {{item.commentNum}}</div>
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
        <ForumAside/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import ForumAside from "@/views/Forum/ForumAside";

export default {
  name: "Forum",
  components: {
    ForumAside,
  },
  data() {
    return {
      articles: [
        {
          id: 1,
          title: '春季来了',
          viewNum: 12,
          commentNum: 13,
          likes: 9,
          description: 'hahahaa',
          top: true,
          tags: ['c++', 'java'],
          authorId: 1,
          authorAvatar: 'https://img0.baidu.com/it/u=325674188,3280397254&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500',
        },
        {
          id: 2,
          title: '春季来了',
          viewNum: 12,
          commentNum: 13,
          likes: 9,
          description: 'hahahaa',
          top: true,
          tags: ['c++', 'java'],
          authorId: 1,
          authorAvatar: 'https://img0.baidu.com/it/u=325674188,3280397254&fm=253&fmt=auto&app=138&f=JPEG?w=501&h=500',
        }
      ],
      activeName: 'hot',
      condition: {
        currentPage: 1,
        total: 0,
        authorName: '',
        title: '',
        pageSize: 30,
        visible: true
      },
      showSpin: true,
    }
  },
  created() {
    this.getArticleByCondition()
  },
  methods: {
    goToArticleDetail(id){
      this.$router.push(`/forum/article/${id}`)
    },
    handleTabClick(tab, event) {
      console.log(tab, event);
    },
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
      this.getArticleByCondition()
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
      this.getArticleByCondition()
    },
    getArticleByCondition(){
      this.showSpin = false
    }
  }
}
</script>

<style scoped>
@media screen and (min-width: 920px){
  #forumWrapper{
    max-width: 1200px;
    margin: 0 auto;
  }
}
a:hover, a:focus, a:active{
  color: rgba(64, 145, 255, 1);
  text-decoration: none;
}
a{
  font-size: 16px;
  line-height: 22px;
  font-weight: 500;
  color: rgba(0, 0, 0, 1);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
