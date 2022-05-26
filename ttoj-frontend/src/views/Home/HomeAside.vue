<template>
  <div>
    <RecentContestCard/>
    <UserInfoCard style="margin-top: 20px" />
    <RankListCard style="margin-top: 20px"/>
  </div>
</template>

<script>
import RecentContestCard from "@/views/Problem/ProblemList/RecentContestCard";
import RankListCard from "@/views/Problem/ProblemList/RankListCard";
import UserInfoCard from "@/views/Problem/ProblemList/UserInfoCard";

export default {
  name: "HomeAside",
  components:{
    RecentContestCard,
    RankListCard,
    UserInfoCard
  }
}
</script>

<style scoped>

</style>
