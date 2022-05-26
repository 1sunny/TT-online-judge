<template>
<div>
  <RecentContestCard/>
  <UserInfoCard style="margin-top: 20px" />
  <RankListCard style="margin-top: 20px"/>
</div>
</template>

<script>
import RecentContestCard from "./RecentContestCard";
import RankListCard from "./RankListCard";
import UserInfoCard from "./UserInfoCard";

export default {
  name: "ProblemListAside",
  components:{
    RecentContestCard,
    RankListCard,
    UserInfoCard
  }
}
</script>

<style scoped>

</style>
