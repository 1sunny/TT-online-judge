<template>
  <el-container>
    <el-main style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
                    margin-right: 20px">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
          <el-tab-pane :label="username"><UserInfo/></el-tab-pane>
          <el-tab-pane label="最近提交"><UserRecentSubmissions/></el-tab-pane>
          <el-tab-pane label="比赛记录"><UserContestRecords/></el-tab-pane>
          <el-tab-pane label="题解"><UserSolutions/></el-tab-pane>
          <el-tab-pane label="笔记"><UserNotes/></el-tab-pane>
          <el-tab-pane label="设置"><UserSettings/></el-tab-pane>
        </el-tabs>
      </el-main>
    <el-aside width="250px">
      <HomeAside style="box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)"></HomeAside>
    </el-aside>
  </el-container>

</template>

<script>
import request from "@/utils/request";
import UserInfo from "@/views/User/UserProfile/UserInfo";
import UserRecentSubmissions from "@/views/User/UserProfile/UserRecentSubmissions";
import UserContestRecords from "@/views/User/UserProfile/UserContestRecords";
import UserSolutions from "@/views/User/UserProfile/UserSolutions";
import UserNotes from "@/views/User/UserProfile/UserNotes";
import UserSettings from "@/views/User/UserProfile/UserSettings";
import HomeAside from "@/views/Home/HomeAside";
import {mapGetters} from "vuex";

export default {
  name: "UserProfile",
  components:{
    UserInfo,
    UserRecentSubmissions,
    UserContestRecords,
    UserSolutions,
    UserNotes,
    UserSettings,
    HomeAside
  },
  computed:{
    ...mapGetters([
      'user',
      'userId',
      'username',
      'auth'
    ])
  },
  data(){
    return{
      activeName: this.username
    }
  },
  created() {
    console.log(this.user);
  },
  methods:{
    handleClick(){

    },
  }
}
</script>

<style scoped>

</style>
