<template>
  <div style="
            background-color: #212121;
            color: #EFF1F6BF;
            margin-top: 100px;
            width: 100%;
           "
  >
    <div id="footerWrapper">
      <div style="display: flex;
              justify-content: space-around;
              padding: 10px 0 20px 0 ;
             "
      >
        <div style="display: flex;flex-direction: column;align-items: start">
          <h4 style="color: #fff;font-weight: 400;margin-bottom: 10px">
            <Icon type="logo-github"/>
            Github
          </h4>
          <el-link href="" :underline="false" disabled>TTOJ</el-link>
        </div>
        <div style="display: flex;flex-direction: column;align-items: start">
          <h4 style="color: #fff;font-weight: 400;margin-bottom: 10px">
            <Icon type="md-help"/>
            帮助
          </h4>
          <el-link :underline="false" target="_blank" disabled>问题反馈</el-link>
          <el-link :underline="false" target="_blank" disabled>使用说明</el-link>
        </div>
      </div>
      <div style="border-top: 1px solid hsla(0,0%,100%,.25);
              text-align: center;
              padding:10px 0;
             "
      >
        <el-link href="http://beian.miit.gov.cn/" target="_blank">蜀ICP备2022011082号</el-link>
        <div>Developed by onesunny</div>
        <div>2022-2022 © TTOJ</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
@media screen and (min-width: 920px) {
  #footerWrapper {
    max-width: 1200px !important;
    margin: 0 auto;
  }
}

</style>
