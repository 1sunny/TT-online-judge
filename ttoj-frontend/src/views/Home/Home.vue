<template>
  <div id="homeWrapper"
       style="
             "
  >
    <el-row style="margin-top: 20px">
      <el-col :xs="24" :sm="17"
      >
        <div style=""
        >
          <el-carousel :interval="4000" type="card" height="200px">
            <el-carousel-item v-for="i in 4" :key="i">
<!--              <el-image fit="scale-down"-->
<!--                        :src="'/static/banner/img'+i+'.jpg'"-->
<!--                        style="height: 200px"-->
<!--              >-->

<!--              </el-image>-->
            </el-carousel-item>
          </el-carousel>
          <el-card class="box-card" style="margin-top: 20px">
            <div slot="header" class="clearfix">
              <span>公告</span>
            </div>
            <div v-for="(item,i) in announcements"
                 :key="i"
                 class="callout"
            >
              <div>
                <span>{{item.publisher}}</span>
                <span style="margin-left: 20px">发布时间: {{item.createTime}}</span>
              </div>
              <div style="margin-top: 20px">{{item.content}}</div>


            </div>
            <div class="block" style="margin: 10px 0 0 0;overflow: auto">
              <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :currentPage="condition.currentPage"
                  :page-size="condition.pageSize"
                  layout="total,  prev, pager, next"
                  :total="condition.total"
                  background
              >
              </el-pagination>
            </div>
          </el-card>

        </div>
      </el-col>
      <el-col :xs="24" :sm="7" style="padding-left: 20px"
      >
        <HomeAside/>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import HomeAside from "@/views/Home/HomeAside";
import Footer from "@/components/footer/Footer";

export default {
  name: "Home",
  components:{
    HomeAside,
    Footer
  },
  data(){
    return{
      announcements:[
        {
          publisher: 'root',
          createTime: '2022/4/22',
          content: 'Welcome To TTOJ'
        },
      ],
      condition: {
        currentPage: 1,
        total: 1,
        pageSize: 3,
        visible: true
      },
    }
  },
  created() {

  },
  methods:{
    handleSizeChange(pageSize) {
      this.condition.pageSize = pageSize
    },
    handleCurrentChange(currentPage) {
      this.condition.currentPage = currentPage
    },
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
@media screen and (min-width: 920px){
  #homeWrapper{
    max-width: 1200px;
    margin: 0 auto;
  }
}
</style>
