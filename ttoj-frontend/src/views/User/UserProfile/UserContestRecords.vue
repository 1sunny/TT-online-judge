<template>
<!--  <div>-->
<!--    <el-table :data="submitRecordsData" stripe style="width: 100%">-->
<!--      <el-table-column prop="id" label="#" width="180" />-->
<!--      <el-table-column prop="username" label="提交时间" width="180" />-->
<!--      <el-table-column prop="problem" label="题目" />-->
<!--      <el-table-column prop="language" label="语言" />-->
<!--      <el-table-column prop="result" label="结果" />-->
<!--      <el-table-column prop="timeUse" label="运行时间" />-->
<!--      <el-table-column prop="memoryUse" label="运行空间" />-->
<!--    </el-table>-->
<!--    <el-pagination layout="prev, pager, next" :total="50"> </el-pagination>-->
<!--  </div>-->
</template>

<script>
export default {
  name: "ContestRecords",
  data(){
    return{
      contestRecordsData: [
        {
          id: 1,
          userId: 1,
          username: '111',
          problemId: 1,
          problem: 'a+b',
          createTime: 123123,
          code: 'return a+b',
          result: 'accepted',
          language: 'c++',
          timeUse: 123,
          memoryUse: 342432
        },
      ]
    }
  },
  created() {
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>
